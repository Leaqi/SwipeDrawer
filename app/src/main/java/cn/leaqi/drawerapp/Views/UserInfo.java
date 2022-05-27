package cn.leaqi.drawerapp.Views;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import cn.leaqi.drawerapp.R;
import cn.leaqi.drawerapp.Utils.Common;
import cn.leaqi.drawer.SwipeDrawer;
import cn.leaqi.drawer.OnDrawerState;

/**
 * 右侧边栏用户菜单操作类
 */
public class UserInfo implements View.OnClickListener {
    private Context mContext;
    private Activity mActivity;


    public UserInfo(Context context) {
        mContext = context;
        mActivity = ((Activity)mContext);
        AppInit();
    }

    private void AppInit() {
        final SwipeDrawer userInfo = mActivity.findViewById(R.id.userInfo);
        final View infoTopIcon = mActivity.findViewById(R.id.infoTopIcon);
        final View infoTopTitle = mActivity.findViewById(R.id.infoTopTitle);
        final ViewGroup listItem1 = mActivity.findViewById(R.id.listItem1);
        final ViewGroup listItem2 = mActivity.findViewById(R.id.listItem2);
        final ViewGroup listItem3 = mActivity.findViewById(R.id.listItem3);

        userInfo.setOnDrawerState(new OnDrawerState() {
            @Override
            public void onStart(int type) { }
            @Override
            public void onMove(int type, float progress) {
                if (type == SwipeDrawer.DIRECTION_TOP) {
                    infoTopIcon.setRotation(progress * 360);
                    progress *= 0.2;
                    infoTopIcon.setScaleX(1 + progress);
                    infoTopIcon.setScaleY(1 + progress);
                    infoTopTitle.setScaleX(1 + progress);
                    infoTopTitle.setScaleY(1 + progress);
                }
            }
            @Override
            public void onOpen(int type) { }
            @Override
            public void onClose(int type) { }
            @Override
            public void onCancel(int type) { }
        });

        for (int i = 0; i < listItem1.getChildCount(); i++) {
            View view = listItem1.getChildAt(i);
            view.setOnClickListener(this);
        }
        for (int i = 0; i < listItem2.getChildCount(); i++) {
            View view = listItem2.getChildAt(i);
            view.setOnClickListener(this);
        }
        for (int i = 0; i < listItem3.getChildCount(); i++) {
            View view = listItem3.getChildAt(i);
            view.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        String getText = (String) view.getTag();
        Common.showToast(getText);
    }
}
