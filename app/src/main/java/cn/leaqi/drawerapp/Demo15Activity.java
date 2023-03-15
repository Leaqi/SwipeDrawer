package cn.leaqi.drawerapp;


import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import cn.leaqi.drawer.OnDrawerState;
import cn.leaqi.drawer.SwipeDrawer;
import cn.leaqi.drawerapp.Utils.Common;
import cn.leaqi.drawerapp.Views.TopBar;

public class Demo15Activity extends Activity {
    TopBar topBar = null;
    SwipeDrawer rootDrawer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo15);
        rootDrawer = findViewById(R.id.rootDrawer);
        topBar = new TopBar(this); // 头部操作类
        topBar.setTitle("");
        topBar.showLeftBack(true);
        topBar.setNavigationBar();
        topBar.setNavigationColor();
        AppInit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) { // 监听返回键
            if (rootDrawer.getShow()) { // 如果是打开状态
                rootDrawer.closeDrawer(); // 关闭
                return true; // 拦截
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void AppInit() {
        final ScrollView subScroll = findViewById(R.id.subScroll);
        final View moreList = findViewById(R.id.moreList);
        final View titleList = findViewById(R.id.titleList);
        final View bottomList = findViewById(R.id.bottomList);
        final ImageView subIcon = findViewById(R.id.subIcon);

        // 监听 SwipeDrawer 状态
        rootDrawer.setOnDrawerState(new OnDrawerState() {
            @Override
            public void onStart(int type) {}
            @Override
            public void onMove(int type, float progress) {
                // 根据进度设置布局透明度
                moreList.setAlpha(progress);
                titleList.setAlpha(progress);
                // 根据进度设置布局高度
                Common.setHeight(moreList, 135 * progress);
                Common.setHeight(titleList, 40 * progress);
            }
            @Override
            public void onOpen(int type) {
                subIcon.setImageResource(R.mipmap.btn_bottom); //更改图标
                Common.animShow(bottomList, 1000); //显示动画
            }
            @Override
            public void onClose(int type) {
                subIcon.setImageResource(R.mipmap.btn_mid); //更改图标
                subScroll.smoothScrollTo(0, 0);
                Common.animHide(bottomList, 1000); //隐藏动画
            }
            @Override
            public void onCancel(int type) {}
        });
        //点击图标关闭
        subIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rootDrawer.getShow()) {
                    rootDrawer.closeDrawer(SwipeDrawer.DIRECTION_BOTTOM);
                }
            }
        });
    }

    public void showTag(View view) {
        Common.showToast(view.getTag().toString());
    }

    public void showText(View view) {
        Common.showToast(((TextView)view).getText().toString());
    }

}
