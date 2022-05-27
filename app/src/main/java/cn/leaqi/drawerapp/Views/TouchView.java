package cn.leaqi.drawerapp.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * 布局触摸缩小
 */
public class TouchView extends LinearLayout {

    public TouchView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public TouchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TouchView(Context context) {
        super(context);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        setClickable(true);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            setScaleX(0.8f);
            setScaleY(0.8f);
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                setScaleX(1f);
                setScaleY(1f);
        }
        return super.onTouchEvent(ev);
    }

}
