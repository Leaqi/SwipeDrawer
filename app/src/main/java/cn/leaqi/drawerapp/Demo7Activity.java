package cn.leaqi.drawerapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import cn.leaqi.drawerapp.Views.TopBar;
import cn.leaqi.drawerapp.Views.UserInfo;
import cn.leaqi.drawerapp.Views.UserMenu;
import cn.leaqi.drawer.SwipeDrawer;
import cn.leaqi.drawer.OnDrawerState;

public class Demo7Activity extends Activity {

    TopBar topBar = null;
    SwipeDrawer rootDrawer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo7);
        rootDrawer = findViewById(R.id.rootDrawer);
        topBar = new TopBar(this); // 头部操作类
        topBar.setTitle("边缘拖拽"); // 头部标题
        new UserMenu(this); // 左侧边栏用户菜单操作类
        new UserInfo(this); // 右侧边栏用户菜单操作类
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

    // 设置头部左边图标并设置点击事件
    private void AppInit() {
        topBar.showLeft(R.mipmap.icon_menu, new TopBar.OnTopClickListener() {
            @Override
            public void onClick(View view) {
                rootDrawer.toggleDrawer(SwipeDrawer.DIRECTION_LEFT); // 点击打开或关闭Left方向
            }
        });
        // 设置头部右边图标并设置点击事件
        topBar.showRight(R.mipmap.icon_menu, new TopBar.OnTopClickListener() {
            @Override
            public void onClick(View view) {
                rootDrawer.toggleDrawer(SwipeDrawer.DIRECTION_RIGHT); // 点击打开或关闭Right方向
            }
        });

        // 监听 SwipeDrawer 状态
        rootDrawer.setOnDrawerState(new OnDrawerState() {
            @Override
            public void onStart(int type) { }
            @Override
            public void onMove(int type, float progress) { }
            @Override
            public void onOpen(int type) { // 打开
                if (type == SwipeDrawer.DIRECTION_LEFT) { // 打开了Left方向
                    topBar.setLeftIcon(R.mipmap.icon_close); // 设置头部左边图标
                }else if (type == SwipeDrawer.DIRECTION_RIGHT) { // 打开了Right方向
                    topBar.setRightIcon(R.mipmap.icon_close); // 设置头部右边图标
                }
            }
            @Override
            public void onClose(int type) { // 关闭
                if (type == SwipeDrawer.DIRECTION_LEFT) { // 关闭了Left方向
                    topBar.setLeftIcon(R.mipmap.icon_menu); // 设置头部左边图标
                }else if (type == SwipeDrawer.DIRECTION_RIGHT) { // 关闭了Right方向
                    topBar.setRightIcon(R.mipmap.icon_menu); // 设置头部右边图标
                }
            }
            @Override
            public void onCancel(int type) { }
        });

    }

}
