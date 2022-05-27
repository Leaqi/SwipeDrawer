package cn.leaqi.drawerapp.Views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import cn.leaqi.drawerapp.R;
import cn.leaqi.drawerapp.Utils.Common;

/**
 * 布局头部处理类
 */
public class TopBar {
    private Context mContext;
    private Activity mActivity;

    private int statusHeight = 0;
    private View topBar;
    private View topStatus;
    private View topHead;
    private TextView topText;
    private View topLeft;
    private View topRight;
    private View topLeftIcon;
    private View topRightIcon;
    private TextView topRightText;

    public interface OnTopClickListener{
        void onClick(View view);
    }

    public TopBar(Context context) {
        mContext = context;
        mActivity = ((Activity)mContext);
        topBar = mActivity.findViewById(R.id.top_bar);
        topStatus = mActivity.findViewById(R.id.top_status);
        topHead = mActivity.findViewById(R.id.top_head);
        topText = mActivity.findViewById(R.id.top_text);
        topLeft = mActivity.findViewById(R.id.top_left);
        topRight = mActivity.findViewById(R.id.top_right);
        topLeftIcon = mActivity.findViewById(R.id.top_left_icon);
        topRightIcon = mActivity.findViewById(R.id.top_right_icon);
        topRightText = mActivity.findViewById(R.id.top_right_text);
        setStatusBar(true);
    }

    public TopBar(Context context, boolean isStatus) {
        mContext = context;
        mActivity = ((Activity)mContext);
        if (isStatus) {
            topStatus = mActivity.findViewById(R.id.top_status);
        }
        setStatusBar(isStatus);
    }

    private void setStatusBar(boolean isStatus){
        try {
            Window window = mActivity.getWindow();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                setStatusTop(isStatus);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                setStatusTop(isStatus);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setNavigationBar() {
        Window window = mActivity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setNavigationBarColor(Color.BLACK);
        }
    }

    private void setStatusTop(boolean isStatus){
        try {
            int resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                statusHeight = mContext.getResources().getDimensionPixelSize(resourceId);
            }
            if (statusHeight == 0) {
                statusHeight = Common.dipToPx(mContext, 25);
            }
            //System.out.println("StatusHeight : " + statusHeight + " - SDK_INT : " + Build.VERSION.SDK_INT);
            if (isStatus && topStatus != null) {
                ViewGroup.LayoutParams params = topStatus.getLayoutParams();
                params.height = statusHeight;
                topStatus.setLayoutParams(params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTitle(String title){
        topText.setText(title);
    }

    public void setLeftIcon(int image) {
        setLeftIcon(image, true);
    }

    public void setLeftIcon(int image, boolean isAnim) {
        topLeftIcon.setBackgroundResource(image);
        if (isAnim) Common.animScale(topLeftIcon, 200);
    }

    public void setRightIcon(int image) {
        setRightIcon(image, true);
    }

    public void setRightIcon(int image, boolean isAnim) {
        topRightIcon.setBackgroundResource(image);
        if (isAnim) Common.animScale(topRightIcon, 200);
    }

    public void showLeft(int image, final OnTopClickListener listener){
        setLeftIcon(image, false);
        if (topLeft.getVisibility() == View.GONE) {
            topLeft.setVisibility(View.VISIBLE);
        }
        if(listener != null){
            topLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(view);
                }
            });
        }
    }

    public void showRight(int image, final OnTopClickListener listener){
        setRightIcon(image, false);
        if (topRight.getVisibility() == View.GONE) {
            topRight.setVisibility(View.VISIBLE);
        }
        if(listener != null){
            topRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        listener.onClick(view);
                }
            });
        }
    }

    public void setRightText(String text){
        setRightText(text, null);
    }

    public void setRightText(String text, final OnTopClickListener listener){
        if (topRightText.getVisibility() == View.GONE) {
            topRightText.setVisibility(View.VISIBLE);
        }
        topRightText.setText(text);
        if(listener != null){
            topRightText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(view);
                }
            });
        }
    }

    public void showLeftBack() {
        showLeft(R.mipmap.icon_back, new TopBar.OnTopClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.finish();
            }
        });
    }

    public void radiusBar() {
        topHead.setBackgroundResource(R.drawable.radius_status);
        if (topStatus.getVisibility() == View.VISIBLE) {
            topStatus.setVisibility(View.INVISIBLE);
        }
    }

    public void normalBar() {
        topHead.setBackgroundResource(R.color.colorPrimary);
        if (topStatus.getVisibility() == View.INVISIBLE) {
            topStatus.setVisibility(View.VISIBLE);
        }
    }

    public void setBarAlpha(float alpha) {
        topBar.setAlpha(alpha);
    }

    public int getWidth() {
        return topBar.getMeasuredWidth();
    }

    public int getHeight() {
        return topBar.getMeasuredHeight();
    }

    public int getStatusHeight() {
        return statusHeight;
    }

    public View getTopLeftIcon() {
        return topLeftIcon;
    }

    public View getTopRightIcon() {
        return topRightIcon;
    }
}
