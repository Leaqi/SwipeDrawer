package cn.leaqi.drawerapp;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cn.leaqi.drawerapp.Utils.Common;
import cn.leaqi.drawerapp.Views.TopBar;
import cn.leaqi.drawer.SwipeDrawer;
import cn.leaqi.drawer.OnDrawerChange;

import java.util.ArrayList;
import java.util.List;

public class Demo12Activity extends Activity {

    TopBar topBar = null;
    private List<String> listData;
    private ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo12);
        topBar = new TopBar(this); // 头部操作类
        topBar.setTitle("RecyclerView 下拉刷新"); // 头部标题
        topBar.showLeftBack(); // 显示头部返回按钮
        AppInit();
    }

    public void AppInit() {
        final SwipeDrawer mainDrawer = findViewById(R.id.mainDrawer);
        final View gesture = findViewById(R.id.gesture); // 方向引导布局
        final ImageView reTopIcon = findViewById(R.id.reTopIcon);
        final TextView reTopText = findViewById(R.id.reTopText);
        final ImageView reBottomIcon = findViewById(R.id.reBottomIcon);
        final TextView reBottomText = findViewById(R.id.reBottomText);


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
                reTopIcon.setVisibility(View.GONE);
                reTopText.setText("刷新完成");
                // 0.6秒后关闭
                reTopText.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mainDrawer.closeDrawer();
                    }
                }, 600);
            }
            private void bottomOver() { // 加载完毕
                // 显示加载完成状态
                SetList(10);
                topBar.getTopRightIcon().clearAnimation();
                topBar.getTopRightIcon().setRotation(0);
                reBottomIcon.clearAnimation();
                reBottomIcon.setRotation(0);
                reBottomIcon.setVisibility(View.GONE);
                reBottomText.setText("加载完成");
                // 0.6秒后关闭
                reBottomText.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mainDrawer.closeDrawer();
                    }
                }, 600);
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
                        if (!view.getShow() && view.getIntercept()) { // 非开启状态，且是手动拖拽
                            if (progress > 2) progress = 2; // 限制进度最大2倍
                            topBar.getTopRightIcon().setRotation(progress * 360); // 头部右边图标根据进度旋转
                            if (progress > 1) progress = 1;
                            if (isTop) {
                                reTopIcon.setRotation(progress * 360); // 顶部刷新图标根据进度旋转
                            } else if (isBottom) {
                                reBottomIcon.setRotation(progress * 360); // 底部加载图标根据进度旋转
                            }
                        }
                        break;
                    case SwipeDrawer.STATE_OPEN : // 打开
                        Common.animRotate(topBar.getTopRightIcon(), 600); // 头部右边图标旋转动画
                        if (isTop) {
                            reTopText.setText("正在刷新");
                            reTopIcon.setImageResource(R.mipmap.icon_more);
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
                            reBottomText.setText("正在加载");
                            reBottomIcon.setImageResource(R.mipmap.icon_more);
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
                                reTopIcon.setVisibility(View.VISIBLE);
                                reTopIcon.setImageResource(R.mipmap.icon_down);
                            } else if (isBottom) {
                                // 加载完毕初始化布局状态
                                reBottomIcon.clearAnimation();
                                reBottomIcon.setRotation(0);
                                reBottomIcon.setVisibility(View.VISIBLE);
                                reBottomIcon.setImageResource(R.mipmap.icon_up);
                            }
                        }
                        break;
                    case SwipeDrawer.STATE_DRAG_INTO : // 拖拽超过 shrinkRange 距离
                        if (!view.getShow()) {
                            if (isTop) {
                                reTopText.setText("松开刷新");
                            } else if (isBottom) {
                                reBottomText.setText("松开加载");
                            }
                        }
                        break;
                    case SwipeDrawer.STATE_DRAG_OUT : // 拖拽未超过 shrinkRange 距离
                        if (!view.getShow()) {
                            if (isTop) {
                                reTopText.setText("下拉刷新");
                            } else if (isBottom) {
                                reBottomText.setText("上拉加载");
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
                listData.add("RecyclerView : " + (i + 1));
            }
            listAdapter.notifyDataSetChanged();
        } else {
            listData.clear();
            listAdapter.notifyDataSetChanged();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 20; i++) {
                        listData.add("RecyclerView : " + (i + 1));
                    }
                    listAdapter.notifyDataSetChanged();
                }
            }, 100);
        }
    }

    /**
     * 给 RecyclerView 填充数据
     */
    private void ListData() {
        final RecyclerView mainList = findViewById(R.id.mainList);
        listData = new ArrayList<String>(){{
            for (int i = 0; i < 20; i++) {
                add("RecyclerView : " + (i + 1));
            }
        }};
        listAdapter = new ListAdapter(this, R.layout.list_icon, listData);
        mainList.setLayoutManager(new LinearLayoutManager(this));
        mainList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mainList.setAdapter(listAdapter);
    }

    private class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {
        private int layout;
        private LayoutInflater inflater;
        private List<String> list;

        private ListAdapter(Context context, int resource, List<String> objects) {
            layout = resource;
            inflater = LayoutInflater.from(context);
            list = objects;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View addView = inflater.inflate(layout, parent, false);
            return new ListViewHolder(addView);
        }

        @Override
        public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
            holder.title.setText(list.get(position));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView title;

            private ListViewHolder(View view) {
                super(view);
                title = view.findViewById(R.id.item_title);
                view.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                Common.showToast(list.get(getAdapterPosition()));
            }
        }

    }

}
