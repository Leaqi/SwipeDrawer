package cn.leaqi.drawerapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import cn.leaqi.drawerapp.Utils.Common;
import cn.leaqi.drawerapp.Views.TopBar;
import cn.leaqi.drawer.SwipeDrawer;
import cn.leaqi.drawer.OnDrawerState;

public class Demo4Activity extends Activity {

    TopBar topBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo4);
        topBar = new TopBar(this); // 头部操作类
        topBar.setTitle("ScrollView"); // 头部标题
        topBar.showLeftBack(); // 显示头部返回按钮
        AppInit();
    }

    public void AppInit() {
        final SwipeDrawer mainDrawer = findViewById(R.id.mainDrawer);
        final View gesture = findViewById(R.id.gesture); // 方向引导布局

        // 设置头部右边图标并设置点击事件
        topBar.showRight(R.mipmap.icon_menu, new TopBar.OnTopClickListener() {
            @Override
            public void onClick(View view) {
                mainDrawer.toggleDrawer(SwipeDrawer.DIRECTION_RIGHT); // 点击打开或关闭Right方向
            }
        });

        // 监听 SwipeDrawer 状态
        mainDrawer.setOnDrawerState(new OnDrawerState() {
            @Override
            public void onStart(int type) { // 拖拽开始
                Common.animHide(gesture, 200); // 隐藏方向引导
            }
            @Override
            public void onMove(int type, float progress) { }
            @Override
            public void onOpen(int type) { // 打开
                if (type == SwipeDrawer.DIRECTION_RIGHT) { // 打开了Right方向
                    topBar.setRightIcon(R.mipmap.icon_close); // 设置头部右边图标
                }
            }
            @Override
            public void onClose(int type) { // 关闭
                Common.animShow(gesture, 200); // 显示方向引导
                if (type == SwipeDrawer.DIRECTION_RIGHT) { // 关闭了Right方向
                    topBar.setRightIcon(R.mipmap.icon_menu); // 设置头部右边图标
                }
            }
            @Override
            public void onCancel(int type) { // 取消操作
                if (mainDrawer.getShow()) { // 判断是否打开
                    Common.animHide(gesture, 200); // 隐藏方向引导
                } else {
                    Common.animShow(gesture, 200); // 显示方向引导
                }
            }
        });
    }

}
