package cn.leaqi.drawerapp;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import cn.leaqi.drawerapp.Utils.Common;
import cn.leaqi.drawerapp.Views.TopBar;
import cn.leaqi.drawer.SwipeDrawer;
import cn.leaqi.drawer.OnDrawerChange;

public class Demo14Activity extends Activity {

    TopBar topBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo14);
        topBar = new TopBar(this); // 头部操作类
        topBar.setTitle("ScrollView 下拉刷新"); // 头部标题
        topBar.showLeftBack(); // 显示头部返回按钮
        AppInit();
    }

    public void AppInit() {
        final SwipeDrawer mainDrawer = findViewById(R.id.mainDrawer);
        final ScrollView mainList = findViewById(R.id.mainList);
        final View gesture = findViewById(R.id.gesture); // 方向引导布局
        final ImageView reTopIcon = findViewById(R.id.reTopIcon);
        final TextView reTopText = findViewById(R.id.reTopText);


        // 设置头部右边图标并设置点击事件
        topBar.showRight(R.mipmap.icon_re, new TopBar.OnTopClickListener() {
            @Override
            public void onClick(View view) {
                mainDrawer.openDrawer(SwipeDrawer.DIRECTION_TOP); // 点击开始刷新
            }
        });

        // 监听 SwipeDrawer 改变
        mainDrawer.setOnDrawerChange(new OnDrawerChange() {
            private void topOver() { // 刷新完毕
                // 显示刷新完成状态
                mainList.smoothScrollTo(0, 0);
                topBar.getTopRightIcon().clearAnimation();
                topBar.getTopRightIcon().setRotation(0);
                reTopIcon.clearAnimation();
                reTopIcon.setRotation(0);
                reTopIcon.setImageResource(R.mipmap.icon_over);
                reTopText.setText("刷新完成");
                // 0.6秒后关闭
                reTopText.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mainDrawer.closeDrawer();
                    }
                }, 600);
            }
            @Override
            public void onChange(final SwipeDrawer view, int state, float progress) {
                boolean isTop = view.getDirection() == SwipeDrawer.DIRECTION_TOP;
                switch (state) {
                    case SwipeDrawer.STATE_START: // 拖拽开始
                    case SwipeDrawer.STATE_CALL_OPEN: // 调用 openDrawer 方法打开
                        Common.animHide(gesture, 200); // 隐藏方向引导
                        break;
                    case SwipeDrawer.STATE_PROGRESS : // 移动，progress 获取进度
                        if (!view.getShow() && view.getIntercept()) { // 非开启状态，且是手动拖拽
                            if (progress > 2) progress = 2; // 限制进度最大2倍
                            topBar.getTopRightIcon().setRotation(progress * 360); // 头部右边图标根据进度旋转
                            if (isTop) {
                                reTopIcon.setRotation(progress * 360); // 顶部刷新图标根据进度旋转
                            }
                        }
                        break;
                    case SwipeDrawer.STATE_OPEN : // 打开
                        Common.animRotate(topBar.getTopRightIcon(), 600); // 头部右边图标旋转动画
                        if (isTop) {
                            reTopText.setText("正在刷新");
                            Common.animRotate(reTopIcon, 600); // 顶部刷新图标旋转动画
                            // 1.5秒后结束刷新
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (view.getShow()) {
                                        topOver();
                                    }
                                }
                            }, 1500);
                        }
                        break;
                    case SwipeDrawer.STATE_ANIM_OVER: // 动画执行完毕
                        if (view.getShow()) {
                            Common.animHide(gesture, 200); // 隐藏方向引导
                        } else {
                            Common.animShow(gesture, 200); // 显示方向引导
                            topBar.getTopRightIcon().clearAnimation(); // 清除头部右边图标动画
                            topBar.getTopRightIcon().setRotation(0); // 回正头部右边图标方向
                            if (isTop) {
                                // 刷新完毕初始化布局状态
                                reTopIcon.clearAnimation();
                                reTopIcon.setRotation(0);
                                reTopIcon.setImageResource(R.mipmap.icon_load);
                            }
                        }
                        break;
                    case SwipeDrawer.STATE_DRAG_INTO : // 拖拽超过 shrinkRange 距离
                        if (!view.getShow()) {
                            if (isTop) {
                                reTopText.setText("松开刷新");
                            }
                        }
                        break;
                    case SwipeDrawer.STATE_DRAG_OUT : // 拖拽未超过 shrinkRange 距离
                        if (!view.getShow()) {
                            if (isTop) {
                                reTopText.setText("下拉刷新");
                            }
                        }
                        break;
                }
            }
        });

    }

}
