package cn.leaqi.drawerapp.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.CycleInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

public class Common {

    /**
     * dp转px
     * @param context 上下文
     * @param dpValue dp
     * @return px
     */
    public static int dipToPx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px转dp
     * @param context 上下文
     * @param pxValue px
     * @return dp
     */
    public static int pxToDip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 设置随机背景颜色，随机值越低颜色越深
     * @param view View
     */
    public static void setRndBg(View view){
        Random random = new Random();
        int color = Color.rgb(random.nextInt(180), random.nextInt(180), random.nextInt(180));
        view.setBackgroundColor(color);
    }

    /**
     * 设置随机文本颜色，随机值越低颜色越深
     * @param view TextView
     */
    public static void setRndColor(TextView view){
        Random random = new Random();
        int color = Color.rgb(random.nextInt(180), random.nextInt(180), random.nextInt(180));
        view.setTextColor(color);
    }

    /**
     * 显示动画
     * @param view View
     * @param ms 动画毫秒
     */
    public static void animShow(View view, int ms){
        if(view.getVisibility() == View.VISIBLE) return;
        AlphaAnimation animation = new AlphaAnimation(0f, 1f);
        animation.setDuration(ms);
        view.clearAnimation();
        view.startAnimation(animation);
        view.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏动画
     * @param view View
     * @param ms 动画毫秒
     */
    public static void animHide(View view, int ms){
        if(view.getVisibility() == View.GONE) return;
        AlphaAnimation animation = new AlphaAnimation(1f, 0f);
        animation.setDuration(ms);
        view.clearAnimation();
        view.startAnimation(animation);
        view.setVisibility(View.GONE);
    }

    /**
     * 显示隐藏动画
     * @param view View
     * @param ms 动画毫秒
     */
    public static void scaleHide(final View view, final int ms){
        ScaleAnimation scale = new ScaleAnimation(0.5f, 1f, 0.5f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scale.setDuration(ms);
        view.clearAnimation();
        view.startAnimation(scale);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                ScaleAnimation scale = new ScaleAnimation(1f, 0.5f, 1f, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                scale.setDuration(ms);
                scale.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        view.setVisibility(View.GONE);
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                view.clearAnimation();
                view.startAnimation(scale);
            }
        },ms + 1500);
    }

    /**
     * 设置旋转动画
     * @param view View
     * @param ms 动画毫秒
     */
    public static void animRotate(View view, int ms){
        Animation animation = new RotateAnimation(
                0f, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatMode(Animation.RESTART);
        animation.setDuration(ms);
        animation.setRepeatCount(-1);
        animation.setFillAfter(true);
        view.clearAnimation();
        view.startAnimation(animation);
    }

    /**
     * 设置平移动画
     * @param view View
     * @param ms 动画毫秒
     */
    public static void animTranslate(View view, float fromX, float toX, float fromY, float toY, int ms){
        Animation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, fromX,
                Animation.RELATIVE_TO_SELF, toX,
                Animation.RELATIVE_TO_SELF, fromY,
                Animation.RELATIVE_TO_SELF, toY
        );
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatMode(Animation.RESTART);
        animation.setDuration(ms);
        animation.setRepeatCount(-1);
        animation.setFillAfter(true);
        view.clearAnimation();
        view.startAnimation(animation);
    }

    /**
     * 抖动动画
     * @param view View
     * @param to 偏移量
     * @param cycles 次数
     * @param ms 动画毫秒
     */
    public static void animShake(View view, float to, int cycles, int ms) {
        RotateAnimation rotateAnim = new RotateAnimation(0, to, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnim.setInterpolator(new CycleInterpolator(cycles));
        rotateAnim.setRepeatCount(-1);
        rotateAnim.setDuration(ms);
        view.clearAnimation();
        view.startAnimation(rotateAnim);
    }

    /**
     * 动画
     * @param view View
     * @param ms 动画毫秒
     */
    public static void animScale(View view, int ms){
        AnimationSet animSet = new AnimationSet(false);
        ScaleAnimation scale = new ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        RotateAnimation rotate = new RotateAnimation(0f, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        AlphaAnimation alpha = new AlphaAnimation(0f, 1f);
        animSet.addAnimation(scale);
        animSet.addAnimation(rotate);
        animSet.addAnimation(alpha);
        animSet.setDuration(ms);
        animSet.setFillAfter(true);
        view.clearAnimation();
        view.startAnimation(animSet);
    }

    /**
     * 弹出Toast
     * @param text 显示文本
     */
    public static void showToast(String text) {
        Toast.makeText(AppData.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 设置View高度
     * @param view View
     * @param dp Dip
     */
    public static void setHeight(View view, float dp) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = dipToPx(AppData.getContext(), dp);
        view.setLayoutParams(params);
    }

    /**
     * 模拟按键
     * @param keyCode keyCode
     */
    public static void sendKeyCode(int keyCode) {
        try {
            String keyCommand = "input keyevent " + keyCode;
            Runtime.getRuntime().exec(keyCommand);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 收起软键盘
     * @param context 上下文
     */
    public static void hideInput(Context context) {
        try {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null && imm.isActive()) {
                imm.hideSoftInputFromWindow(((Activity) context).getWindow().getDecorView().getWindowToken(), 0);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 格式化数字
     * @param num 数字
     * @return w
     */
    public static String ParseNum(int num) {
        try {
            int setScale = 0;
            float setNum = num;
            String setExt = "";
            if (setNum > 10000) {
                setScale = 2;
                setNum = setNum / 10000;
                setExt = "w";
            }
            if (setScale > 0) {
                BigDecimal bigDecimal = new BigDecimal(setNum);
                return bigDecimal.setScale(setScale, BigDecimal.ROUND_HALF_UP).doubleValue() + setExt;
            } else {
                return String.valueOf(num);
            }
        }catch (Exception e){
            return String.valueOf(num);
        }
    }

    /**
     * 加载网络图片并缓存
     * @param img ImageView
     * @param url 图片地址
     */
    public static void setHttpImage(final ImageView img, final String url) {
        setHttpImage(img, url, -1);
    }

    /**
     * 加载网络图片并缓存
     * @param img ImageView
     * @param url 图片地址
     * @param resId // 默认图片
     */
    public static void setHttpImage(final ImageView img, final String url, final int resId) {
        final File cacheFile = Cache.getCacheFile(url);
        if (Cache.showImage(img, cacheFile)) return;
        if (resId != -1) {
            img.setImageResource(resId);
        }
        if (url.length() == 0) return;
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    URL imgUrl = new URL(url);
                    HttpURLConnection http = (HttpURLConnection) imgUrl.openConnection();
                    http.setDoInput(true);
                    http.connect();
                    InputStream stream = http.getInputStream();
                    final Bitmap bitmap = BitmapFactory.decodeStream(stream);
                    img.post(new Runnable() {
                        @Override
                        public void run() {
                            img.setImageBitmap(bitmap);
                        }
                    });
                    Cache.fileWrite(cacheFile, bitmap);
                    stream.close();
                } catch (Exception e) {
                    //System.out.println("setHttpImage : " + url);
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
