package cn.leaqi.drawerapp.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * GridView 嵌套滚动
 */
public class NewGridView extends GridView {

    public NewGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NewGridView(Context context) {
        super(context);
    }

    public NewGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}