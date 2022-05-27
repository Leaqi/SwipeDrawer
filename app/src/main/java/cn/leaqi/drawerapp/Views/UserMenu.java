package cn.leaqi.drawerapp.Views;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import cn.leaqi.drawerapp.R;
import cn.leaqi.drawerapp.Utils.Common;

/**
 * 左侧边栏用户菜单操作类
 */
public class UserMenu implements View.OnClickListener {
    private Context mContext;
    private Activity mActivity;


    public UserMenu(Context context) {
        mContext = context;
        mActivity = ((Activity)mContext);
        AppInit();
    }

    private void AppInit() {
        final ViewGroup listItem = mActivity.findViewById(R.id.listItem);

        for (int i = 0; i < listItem.getChildCount(); i++) {
            View view = listItem.getChildAt(i);
            view.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        String getText = (String) view.getTag();
        Common.showToast(getText);
    }
}
