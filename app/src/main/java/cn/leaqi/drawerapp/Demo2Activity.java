package cn.leaqi.drawerapp;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.leaqi.drawerapp.Utils.Common;
import cn.leaqi.drawerapp.Views.TopBar;
import cn.leaqi.drawer.DrawerHolder;
import cn.leaqi.drawer.SwipeDrawer;
import cn.leaqi.drawer.OnDrawerState;

import java.util.ArrayList;
import java.util.List;

public class Demo2Activity extends Activity {

    TopBar topBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo2);
        topBar = new TopBar(this); // 头部操作类
        topBar.setTitle("RecyclerView"); // 头部标题
        topBar.showLeftBack(); // 显示头部返回按钮
        AppInit();
    }

    public void AppInit() {
        final SwipeDrawer mainDrawer = findViewById(R.id.mainDrawer);
        final View gesture = findViewById(R.id.gesture); // 方向引导布局
        // 监听 SwipeDrawer 状态
        mainDrawer.setOnDrawerState(new OnDrawerState() {
            @Override
            public void onStart(int type) { // 拖拽开始
                Common.animHide(gesture, 200); // 隐藏方向引导
            }
            @Override
            public void onMove(int type, float progress) { }
            @Override
            public void onOpen(int type) { }
            @Override
            public void onClose(int type) { // 关闭
                Common.animShow(gesture, 200); // 显示方向引导
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
        ListData();
    }

    /**
     * 给 ListView 填充数据
     */
    private void ListData() {
        final RecyclerView mainList = findViewById(R.id.mainList);
        final List<String> listData = new ArrayList<String>(){{
            for (int i = 0; i < 30; i++) {
                add("RecyclerView : " + (i + 1));
            }
        }};
        final ListAdapter listAdapter = new ListAdapter(this, R.layout.list_item, listData);
        // 绑定 item 点击事件
        listAdapter.setOnItemClick(new OnItemClick() {
            @Override
            public void onClick(SwipeDrawer swipeDrawer, int position, int id) {
                switch (id) {
                    case R.id.itemMain : // 点击了主布局
                        Common.showToast(listData.get(position));
                        break;
                    case R.id.itemDing : // 点击了 Left 置顶按钮
                        swipeDrawer.closeDrawer(false); // 非动画关闭当前 item
                        String getText = listData.get(position);
                        listData.remove(position); // 移除 item
                        listData.add(0, getText); // 添加到顶部
                        // 更新 list
                        listAdapter.notifyItemRemoved(position);
                        listAdapter.notifyItemRangeInserted(0, 1);
                        break;
                    case R.id.itemDel : // 点击了 Right 删除按钮
                        swipeDrawer.closeDrawer(false); // 非动画关闭当前 item
                        listData.remove(position); // 删除 item
                        listAdapter.notifyItemRemoved(position); // 更新 list
                        break;
                    case R.id.itemClose : // 点击了 Right 关闭按钮
                        swipeDrawer.closeDrawer(true); // 关闭当前 item
                        break;
                }
            }
        });
        mainList.setLayoutManager(new LinearLayoutManager(this));
        mainList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mainList.setAdapter(listAdapter);
        // 自定义头部右边文本并设置点击事件
        final String autoText = "✔ 自动关闭";
        final String shutText = "✘ 自动关闭";
        topBar.setRightText(autoText, new TopBar.OnTopClickListener() {
            @Override
            public void onClick(View view) {
                String getText = ((TextView) view).getText().toString();
                if (getText.equals(autoText)) {
                    topBar.setRightText(shutText); // 更新文本
                    listAdapter.setAutoClose(false);  // 取消自动关闭
                } else {
                    topBar.setRightText(autoText); // 更新文本
                    listAdapter.setAutoClose(true); // 设置自动关闭
                }
            }
        });
    }

    private interface OnItemClick {
        void onClick(SwipeDrawer swipeDrawer, int position, int id);
    }

    private class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {
        private int layout;
        private LayoutInflater inflater;
        private List<String> list;
        private OnItemClick onItemClick = null;
        private boolean autoClose = true;

        // 创建 DrawerHolder 对象，非自动关闭的情况保存 item 状态使用
        private DrawerHolder drawerHolder = new DrawerHolder();

        private ListAdapter(Context context, int resource, List<String> objects) {
            layout = resource;
            inflater = LayoutInflater.from(context);
            list = objects;
            notifyDataSetChanged();
        }

        private void setOnItemClick(OnItemClick click) {
            onItemClick = click;
        }

        private void setAutoClose(boolean bool) {
            autoClose = bool;
            drawerHolder.clearHolder(); // 清除已保存状态并关闭所有已打开 item
            notifyDataSetChanged(); // 更新 list
        }

        @NonNull
        @Override
        public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View addView = inflater.inflate(layout, parent, false);
            return new ListViewHolder(addView);
        }

        @Override
        public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
            holder.item.setAutoClose(autoClose); // 设置是否自动关闭
            holder.title.setText(list.get(position));

            // bindHolder 的第一个参数传递 item 的 SwipeDrawer 布局
            // bindHolder 的第二个参数传递唯一对象
            drawerHolder.bindHolder(holder.item, list.get(position));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            SwipeDrawer item;
            View itemMain;
            View itemDing;
            View itemDel;
            View itemClose;
            TextView title;

            private ListViewHolder(View view) {
                super(view);
                item = (SwipeDrawer) view;
                title = view.findViewById(R.id.item_title);
                itemMain = view.findViewById(R.id.itemMain);
                itemDing = view.findViewById(R.id.itemDing);
                itemDel = view.findViewById(R.id.itemDel);
                itemClose = view.findViewById(R.id.itemClose);
                itemMain.setOnClickListener(this);
                itemDing.setOnClickListener(this);
                itemDel.setOnClickListener(this);
                itemClose.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                int id = view.getId();
                int position = getAdapterPosition();
                if (onItemClick != null) {
                    onItemClick.onClick(item, position, id);
                }
            }

        }

    }

}
