package cn.leaqi.drawerapp.Views;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import cn.leaqi.drawerapp.R;
import cn.leaqi.drawerapp.Utils.Common;
import cn.leaqi.drawer.SwipeDrawer;

/**
 * 视频布局用户分享类
 */
public class UserShare implements View.OnClickListener {
    private Context mContext;
    private Activity mActivity;
    private SwipeDrawer rootDrawer;

    public UserShare(Context context) {
        mContext = context;
        mActivity = ((Activity)mContext);
        rootDrawer = mActivity.findViewById(R.id.rootDrawer);
        AppInit();
    }

    private void AppInit() {
        final ViewGroup shareBox1 = mActivity.findViewById(R.id.shareBox1);
        final ViewGroup shareBox2 = mActivity.findViewById(R.id.shareBox2);
        for (int i = 0; i < shareBox1.getChildCount(); i++) {
            View view = shareBox1.getChildAt(i);
            view.setOnClickListener(this);
        }
        for (int i = 0; i < shareBox2.getChildCount(); i++) {
            View view = shareBox2.getChildAt(i);
            view.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        String getText = (String) view.getTag();
        Common.showToast(getText);
        rootDrawer.closeDrawer();
    }
}