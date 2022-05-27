package cn.leaqi.drawerapp;

import androidx.annotation.NonNull;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import cn.leaqi.drawerapp.Utils.Common;
import cn.leaqi.drawerapp.Views.TopBar;
import cn.leaqi.drawer.SwipeDrawer;
import cn.leaqi.drawer.OnDrawerChange;

import java.util.ArrayList;
import java.util.List;

public class Demo11Activity extends Activity {

    TopBar topBar = null;
    private List<String> listData;
    private ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo11);
        topBar = new TopBar(this); // 头部操作类
        topBar.setTitle("ListView 下拉刷新"); // 头部标题
        topBar.showLeftBack(); // 显示头部返回按钮
        AppInit();
    }

    public void AppInit() {
        final SwipeDrawer mainDrawer = findViewById(R.id.mainDrawer);
        final View gesture = findViewById(R.id.gesture); // 方向引导布局
        final View loadBg1 = findViewById(R.id.loadBg1); // 背景1
        final View loadBg2 = findViewById(R.id.loadBg2); // 背景2
        final View loadMain = findViewById(R.id.loadMain); // 骑行区域
        final View loadWheel1 = findViewById(R.id.loadWheel1); // 轮子1
        final View loadWheel2 = findViewById(R.id.loadWheel2); // 轮子1
        final View loadSun = findViewById(R.id.loadSun); // 太阳
        final ImageView reBottomIcon = findViewById(R.id.reBottomIcon); // 底部加载图标


        // 设置头部右边图标并设置点击事件
        topBar.showRight(R.mipmap.icon_re, new TopBar.OnTopClickListener() {
            @Override
            public void onClick(View view) {
                mainDrawer.openDrawer(SwipeDrawer.DIRECTION_TOP); // 点击开始刷新
            }
        });

        // 监听 SwipeDrawer 改变
        mainDrawer.setOnDrawerChange(new OnDrawerChange() {
            private boolean isClose = false;
            private void topOver() { // 刷新完毕
                // 显示刷新完成状态
                isClose = true;
                SetList(0);
                topBar.getTopRightIcon().clearAnimation();
                topBar.getTopRightIcon().setRotation(0);
                // 0.5秒后关闭
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mainDrawer.closeDrawer();
                    }
                }, 500);
            }
            private void bottomOver() { // 加载完毕
                // 显示加载完成状态
                isClose = true;
                SetList(10);
                topBar.getTopRightIcon().clearAnimation();
                topBar.getTopRightIcon().setRotation(0);
                reBottomIcon.clearAnimation();
                reBottomIcon.setScaleX(1);
                reBottomIcon.setScaleY(1);
                reBottomIcon.setImageResource(R.mipmap.icon_complete);
                // 0.5秒后关闭
                reBottomIcon.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mainDrawer.closeDrawer();
                    }
                }, 500);
            }
            @Override
            public void onChange(final SwipeDrawer view, int state, float progress) {
                boolean isTop = view.getDirection() == SwipeDrawer.DIRECTION_TOP;
                boolean isBottom = view.getDirection() == SwipeDrawer.DIRECTION_BOTTOM;
                int setWidth = mainDrawer.getMeasuredWidth() / 2 - loadMain.getMeasuredWidth() / 2;
                int bgWidth = loadBg1.getMeasuredWidth();
                switch (state) {
                    case SwipeDrawer.STATE_START: // 拖拽开始
                    case SwipeDrawer.STATE_CALL_OPEN: // 调用 openDrawer 方法打开
                        Common.animHide(gesture, 200); // 隐藏方向引导
                        break;
                    case SwipeDrawer.STATE_PROGRESS : // 移动，progress 获取进度
                        if (progress > 2) progress = 2; // 限制进度最大2倍
                        if (!view.getShow() && view.getIntercept()) { // 非开启状态，且是手动拖拽
                            topBar.getTopRightIcon().setRotation(progress * 360); // 头部右边图标根据进度旋转
                        }
                        if (progress > 1) progress = 1f;
                        if (isTop) {
                            if (isClose) {
                                progress = 2 - progress;
                                loadMain.setX(progress * setWidth); // 骑行区域根据进度前进后退
                            } else if (!view.getShow()) {
                                loadMain.setX(progress * setWidth); // 骑行区域根据进度前进后退
                                loadBg1.setX(-(progress * bgWidth)); // 背景1无缝滚动
                                loadBg2.setX(bgWidth + -(progress * bgWidth)); // 背景2无缝滚动
                                loadWheel1.setRotation(progress * 1800); // 轮子1转动
                                loadWheel2.setRotation(progress * 1800); // 轮子2转动
                                loadSun.setRotation(progress * 360); // 太阳转动
                            }
                        } else if (isBottom) {
                            // 底部加载图标根据进度放大缩小
                            reBottomIcon.setScaleX(progress);
                            reBottomIcon.setScaleY(progress);
                        }
                        break;
                    case SwipeDrawer.STATE_OPEN : // 打开
                        Common.animRotate(topBar.getTopRightIcon(), 600); // 头部右边图标旋转动画
                        if (isTop) {
                            loadMain.setX(setWidth);
                            loadBg1.setX(0);
                            loadBg2.setX(bgWidth);
                            Common.animShake(loadMain, 0.2f ,6 , 2000); // 骑行区域抖动动画
                            Common.animTranslate(loadBg1, 0, -1, 0, 0, 3000); // 背景1无缝滚动动画
                            Common.animTranslate(loadBg2, 0, -1, 0, 0, 3000); // 背景2无缝滚动动画
                            Common.animRotate(loadWheel1, 500); // 轮子1旋转动画
                            Common.animRotate(loadWheel2, 500); // 轮子2旋转动画
                            Common.animRotate(loadSun, 2000); // 太阳旋转动画
                            // 2秒后结束刷新
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (view.getShow()) {
                                        topOver();
                                    }
                                }
                            }, 2000);
                        } else if (isBottom) {
                            Common.animShake(reBottomIcon, 8, 8, 1600); // 底部加载图标旋转动画
                            // 2秒后结束加载
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (view.getShow()) {
                                        bottomOver();
                                    }
                                }
                            }, 2000);
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
                                loadMain.clearAnimation();
                                loadBg1.clearAnimation();
                                loadBg2.clearAnimation();
                                loadWheel1.clearAnimation();
                                loadWheel2.clearAnimation();
                                loadSun.clearAnimation();
                                loadMain.setX(0);
                                loadBg1.setX(0);
                                loadBg2.setX(0);
                                loadWheel1.setRotation(0);
                                loadWheel2.setRotation(0);
                                loadSun.setRotation(0);
                            } else if (isBottom) {
                                // 加载完毕初始化布局状态
                                reBottomIcon.clearAnimation();
                                reBottomIcon.setScaleX(1);
                                reBottomIcon.setScaleY(1);
                                reBottomIcon.setImageResource(R.mipmap.icon_machine);
                            }
                            isClose = false;
                        }
                        break;
                }
            }
        });

        ListData();
    }

    /**
     * 更新 list 数据
     * @param num 更新条数
     */
    private void SetList(int num) {
        if (num > 0) {
            int sNum = listData.size();
            int eNum = listData.size() + num;
            for (int i = sNum; i < eNum; i++) {
                listData.add("ListView : " + (i + 1));
            }
            listAdapter.notifyDataSetChanged();
        } else {
            listData.clear();
            listAdapter.notifyDataSetChanged();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 20; i++) {
                        listData.add("ListView : " + (i + 1));
                    }
                    listAdapter.notifyDataSetChanged();
                }
            }, 100);
        }
    }

    /**
     * 给 ListView 填充数据
     */
    private void ListData() {
        final ListView mainList = findViewById(R.id.mainList);
        listData = new ArrayList<String>(){{
            for (int i = 0; i < 20; i++) {
                add("ListView : " + (i + 1));
            }
        }};
        listAdapter = new ListAdapter(this, R.layout.list_icon, listData);
        mainList.setAdapter(listAdapter);
        mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Common.showToast(listData.get(i));
            }
        });
    }

    private class ListAdapter extends ArrayAdapter<String> {
        private int layout;
        private LayoutInflater inflater;
        private List<String> list;

        private ListAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            layout = resource;
            inflater = LayoutInflater.from(context);
            list = objects;
        }

        @NonNull
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            View view;
            ViewHolder viewHolder;
            if (convertView == null) {
                view = inflater.inflate(layout, null);
                viewHolder = new ViewHolder();
                viewHolder.title = view.findViewById(R.id.item_title);
                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.title.setText(list.get(position));
            return view;
        }

        private class ViewHolder {
            TextView title;
        }
    }

}
