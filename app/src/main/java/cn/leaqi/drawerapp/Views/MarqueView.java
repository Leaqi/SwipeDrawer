package cn.leaqi.drawerapp.Views;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * TextView 文本滚动
 */
public class MarqueView extends AppCompatTextView {

    private boolean isFocused = false;

    public MarqueView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Init();
    }

    public MarqueView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Init();
    }

    public MarqueView(Context context) {
        super(context);
        Init();
    }

    public void Init() {
        setSingleLine(true);
        setEllipsize(TextUtils.TruncateAt.MARQUEE);
    }

    public void setFocused(boolean focused) {
        isFocused = focused;
        requestLayout();
    }

    @Override
    public boolean isFocused() {
        return isFocused;
    }
}