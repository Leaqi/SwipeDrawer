package cn.leaqi.drawerapp.Views;

import android.content.Context;
import android.content.res.TypedArray;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.SeekBar;
import android.widget.VideoView;

import cn.leaqi.drawerapp.R;
import cn.leaqi.drawer.SwipeDrawer;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义VideoView视频播放器
 */
public class VideoPlay extends VideoView implements MediaPlayer.OnErrorListener,View.OnClickListener {

    private static final List<VideoPlay> lastPlay = new ArrayList<>();

    private boolean init = false;
    private boolean drag = false;

    private boolean isPlay = false;

    private int loadId = -1;
    private int playId = -1;
    private int pauseId = -1;
    private int seekId = -1;
    private View loadView = null;
    private View playView = null;
    private View pauseView = null;
    private SeekBar seekView = null;
    private OnPlayState onPlayState = null;

    public interface OnPlayState {
        void onStart(VideoPlay view);
        void onPlay(VideoPlay view);
        void onStop(VideoPlay view);
        void onError(VideoPlay view);
    }

    public VideoPlay(Context context) {
        super(context);
        Init(context, null);
    }

    public VideoPlay(Context context, AttributeSet attrs) {
        super(context, attrs);
        Init(context, attrs);
    }

    public VideoPlay(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Init(context, attrs);
    }

    private void Init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            final TypedArray attrArr = context.obtainStyledAttributes(attrs, R.styleable.VideoPlay);
            playId = attrArr.getResourceId(R.styleable.VideoPlay_playId, playId);
            pauseId = attrArr.getResourceId(R.styleable.VideoPlay_pauseId, pauseId);
            seekId = attrArr.getResourceId(R.styleable.VideoPlay_seekId, seekId);
            loadId = attrArr.getResourceId(R.styleable.VideoPlay_loadId, loadId);
            attrArr.recycle();
        }
        setOnErrorListener(this);
        setOnClickListener(this);
        setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                isPlay = true;
                loadAnim(false);
                if (onPlayState != null) onPlayState.onPlay(VideoPlay.this);
            }
        });
        setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
                mediaPlayer.setLooping(true);
            }
        });
    }

    public void setOnPlayState(OnPlayState call) {
        onPlayState = call;
    }

    private void upProgress() {
        final int duration = getDuration();
        if (seekView != null) {
            seekView.setMax(duration);
            new Thread() {
                @Override
                public void run() {
                    try {
                        while (isPlaying() && !drag) {
                            /*int current = getCurrentPosition();
                            int progress = Math.round(((float) current / duration) * 100);
                            System.out.println("current : " + current + " - " + progress);*/
                            seekView.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (drag) return;
                                    seekView.setProgress(getCurrentPosition());
                                }
                            });
                            sleep(500);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }

    private void upViews(boolean play) {
        if (play) {
            if(playView != null) playView.setVisibility(GONE);
            if(pauseView != null) pauseView.setVisibility(VISIBLE);
        } else {
            if(playView != null) playView.setVisibility(VISIBLE);
            if(pauseView != null) pauseView.setVisibility(GONE);
        }
    }

    private void loadAnim(boolean bool) {
        if (loadView != null) {
            if (bool) {
                ScaleAnimation scale = new ScaleAnimation(0.3f, 1f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                scale.setRepeatCount(-1);
                AlphaAnimation alpha = new AlphaAnimation(1f, 0.3f);
                alpha.setRepeatCount(-1);
                AnimationSet anim = new AnimationSet(true);
                anim.addAnimation(scale);
                anim.addAnimation(alpha);
                anim.setDuration(300);
                loadView.startAnimation(anim);
                loadView.setVisibility(VISIBLE);
            } else {
                loadView.clearAnimation();
                loadView.setVisibility(GONE);
            }
        }
        if (seekView != null) {
            if (bool) {
                seekView.setVisibility(GONE);
            } else {
                seekView.setVisibility(VISIBLE);
            }
        }
    }

    public static void setViewAlpha(float alpha) {
        if (lastPlay.size() > 0) {
            for (VideoPlay view : lastPlay) {
                if (view.seekView != null) {
                    view.seekView.setAlpha(alpha);
                }
                if (view.playView != null) {
                    view.playView.setAlpha(alpha);
                }
                if (view.pauseView != null) {
                    view.pauseView.setAlpha(alpha);
                }
            }
        }
    }

    @Override
    public void start() {
        if (lastPlay.indexOf(this) == -1) lastPlay.add(this);
        super.start();
        upProgress();
        upViews(true);
        if (isPlay) {
            if (onPlayState != null) onPlayState.onPlay(this);
        } else {
            loadAnim(true);
            if (onPlayState != null) onPlayState.onStart(this);
        }
    }

    @Override
    public void pause() {
        super.pause();
        upViews(false);
        if (onPlayState != null) onPlayState.onStop(this);
    }

    @Override
    public void resume() {
        if (lastPlay.indexOf(this) == -1) lastPlay.add(this);
        super.resume();
        upProgress();
        upViews(true);
        if (onPlayState != null) onPlayState.onPlay(this);
    }

    @Override
    public void stopPlayback() {
        super.stopPlayback();
    }

    @Override
    public void onClick(View view) {
        if (view == pauseView) {
            if (isPlaying()) {
                pause();
            }
        } else if (view == playView) {
            start();
        }
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        System.out.println("MediaPlayer : " + mediaPlayer + " - " + i + " - " + i1);
        if (onPlayState != null) onPlayState.onPlay(this);
        return true;
    }

    /*@Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(0, widthMeasureSpec);
        int height = getDefaultSize(0, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }*/

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (!init) {
            View parent = (View) getParent();
            if (parent != null) {
                View view;
                if (playId != -1) {
                    view = parent.findViewById(playId);
                    if (view != null) {
                        playView = view;
                        playView.setOnClickListener(this);
                    }
                }
                if (pauseId != -1) {
                    view = parent.findViewById(pauseId);
                    if (view != null) {
                        pauseView = view;
                        pauseView.setVisibility(VISIBLE);
                        pauseView.setOnClickListener(this);
                    }
                }
                if (loadId != -1) {
                    view = parent.findViewById(loadId);
                    if (view != null) {
                        loadView = view;
                        loadAnim(true);
                    }
                }
                if (seekId != -1) {
                    view = parent.findViewById(seekId);
                    if (view instanceof SeekBar) {
                        seekView = (SeekBar) view;
                        seekView.setVisibility(GONE);
                        seekView.setOnTouchListener(new OnTouchListener() {
                            @Override
                            public boolean onTouch(View view, MotionEvent ev) {
                                switch (ev.getAction()) {
                                    case MotionEvent.ACTION_DOWN:
                                        SwipeDrawer.setAllIntercept(true);
                                        break;
                                    case MotionEvent.ACTION_UP:
                                    case MotionEvent.ACTION_CANCEL:
                                        SwipeDrawer.setAllIntercept(false);
                                        break;
                                }
                                return false;
                            }
                        });
                        seekView.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                            @Override
                            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                                if (b) {
                                    seekTo(i);
                                }
                            }

                            @Override
                            public void onStartTrackingTouch(final SeekBar seekBar) {
                                drag = true;
                                seekBar.setScaleX(2f);
                                seekBar.setScaleY(2f);
                            }

                            @Override
                            public void onStopTrackingTouch(final SeekBar seekBar) {
                                drag = false;
                                seekBar.setScaleX(1f);
                                seekBar.setScaleY(1f);
                                upProgress();
                            }
                        });
                    }
                }
            }
            init = true;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        if (lastPlay.indexOf(this) >= 0) lastPlay.remove(this);
        super.onDetachedFromWindow();
    }
}
