package cn.leaqi.drawerapp;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import cn.leaqi.drawerapp.Utils.Common;
import cn.leaqi.drawerapp.Views.TopBar;
import cn.leaqi.drawer.SwipeDrawer;
import cn.leaqi.drawer.OnDrawerChange;

import java.util.ArrayList;
import java.util.List;

public class Demo13Activity extends Activity {

    TopBar topBar = null;
    private List<String> listData;
    private ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo13);
        topBar = new TopBar(this); // 头部操作类
        topBar.setTitle("GridView 下拉刷新"); // 头部标题
        topBar.showLeftBack(); // 显示头部返回按钮
        AppInit();
    }

    public void AppInit() {
        final SwipeDrawer mainDrawer = findViewById(R.id.mainDrawer);
        final View gesture = findViewById(R.id.gesture); // 方向引导布局
        final ImageView reTopIcon = findViewById(R.id.reTopIcon);
        final ImageView reBottomIcon = findViewById(R.id.reBottomIcon);


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
                SetList(0);
                topBar.getTopRightIcon().clearAnimation();
                topBar.getTopRightIcon().setRotation(0);
                reTopIcon.clearAnimation();
                reTopIcon.setRotation(0);
                reTopIcon.setScaleX(1);
                reTopIcon.setScaleY(1);
                // 0.3秒后关闭
                reTopIcon.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mainDrawer.closeDrawer();
                    }
                }, 300);
            }
            private void bottomOver() { // 加载完毕
                // 显示加载完成状态
                SetList(10);
                topBar.getTopRightIcon().clearAnimation();
                topBar.getTopRightIcon().setRotation(0);
                reBottomIcon.clearAnimation();
                reBottomIcon.setRotation(0);
                reBottomIcon.setScaleX(1);
                reBottomIcon.setScaleY(1);
                // 0.3秒后关闭
                reBottomIcon.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mainDrawer.closeDrawer();
                    }
                }, 300);
            }
            @Override
            public void onChange(final SwipeDrawer view, int state, float progress) {
                boolean isTop = view.getDirection() == SwipeDrawer.DIRECTION_TOP;
                boolean isBottom = view.getDirection() == SwipeDrawer.DIRECTION_BOTTOM;
                switch (state) {
                    case SwipeDrawer.STATE_START: // 拖拽开始
                    case SwipeDrawer.STATE_CALL_OPEN: // 调用 openDrawer 方法打开
                        Common.animHide(gesture, 200); // 隐藏方向引导
                        break;
                    case SwipeDrawer.STATE_PROGRESS : // 移动，progress 获取进度
                        if (!view.getShow()) {
                            if (progress > 1) progress = 1; // 限制进度最大1倍
                            if (view.getIntercept()) { // 手动拖拽
                                topBar.getTopRightIcon().setRotation(progress * 360); // 头部右边图标根据进度旋转
                            }
                            if (isTop) {
                                // 顶部刷新图标根据进度放大缩小
                                reTopIcon.setScaleX(progress);
                                reTopIcon.setScaleY(progress);
                                reTopIcon.setRotation(progress * 360); // 顶部刷新图标根据进度旋转
                            } else if (isBottom) {
                                // 底部加载图标根据进度放大缩小
                                reBottomIcon.setScaleX(progress);
                                reBottomIcon.setScaleY(progress);
                                reBottomIcon.setRotation(progress * 360); // 底部加载图标根据进度旋转
                            }
                        }
                        break;
                    case SwipeDrawer.STATE_OPEN : // 打开
                        Common.animRotate(topBar.getTopRightIcon(), 600);
                        if (isTop) {
                            Common.animRotate(reTopIcon, 800); // 顶部刷新图标旋转动画
                            // 1.5秒后结束刷新
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (view.getShow()) {
                                        topOver();
                                    }
                                }
                            }, 1500);
                        } else if (isBottom) {
                            Common.animRotate(reBottomIcon, 800); // 底部加载图标旋转动画
                            // 1.5秒后结束加载
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (view.getShow()) {
                                        bottomOver();
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
                                reTopIcon.setScaleX(1);
                                reTopIcon.setScaleY(1);
                            } else if (isBottom) {
                                // 加载完毕初始化布局状态
                                reBottomIcon.clearAnimation();
                                reBottomIcon.setRotation(0);
                                reBottomIcon.setScaleX(1);
                                reBottomIcon.setScaleY(1);
                            }
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
                listData.add("GridView : " + (i + 1));
            }
            listAdapter.notifyDataSetChanged();
        } else {
            listData.clear();
            listAdapter.notifyDataSetChanged();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 40; i++) {
                        listData.add("GridView : " + (i + 1));
                    }
                    listAdapter.notifyDataSetChanged();
                }
            }, 100);
        }
    }

    /**
     * 给 GridView 填充数据
     */
    private void ListData() {
        final GridView mainList = findViewById(R.id.mainList);
        listData = new ArrayList<String>() {{
            for (int i = 0; i < 40; i++) {
                add("GridView : " + (i + 1));
            }
        }};
        listAdapter = new ListAdapter(this, R.layout.grid_text, listData);
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
