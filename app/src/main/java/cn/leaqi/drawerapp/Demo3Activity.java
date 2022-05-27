package cn.leaqi.drawerapp;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import cn.leaqi.drawerapp.Utils.Common;
import cn.leaqi.drawerapp.Views.TopBar;
import cn.leaqi.drawer.DrawerHolder;
import cn.leaqi.drawer.SwipeDrawer;
import cn.leaqi.drawer.OnDrawerState;

import java.util.ArrayList;
import java.util.List;

public class Demo3Activity extends Activity {

    TopBar topBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo3);
        topBar = new TopBar(this); // 头部操作类
        topBar.setTitle("GridView"); // 头部标题
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
        final GridView mainList = findViewById(R.id.mainList);
        final List<String> listData = new ArrayList<String>() {{
            for (int i = 0; i < 50; i++) {
                add("GridView : " + (i + 1));
            }
        }};
        final ListAdapter listAdapter = new ListAdapter(this, R.layout.grid_item, listData);
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
                        listData.remove(position); // 删除 item
                        listData.add(0, getText); // 添加到顶部
                        listAdapter.notifyDataSetChanged(); // 更新 list
                        break;
                    case R.id.itemDel : // 点击了 Right 删除按钮
                        swipeDrawer.closeDrawer(false); // 非动画关闭当前 item
                        listData.remove(position); // 删除 item
                        listAdapter.notifyDataSetChanged(); // 更新 list
                        break;
                    case R.id.itemClose : // 点击了 Right 关闭按钮
                        swipeDrawer.closeDrawer(true); // 关闭当前 item
                        break;
                }
            }
        });
        mainList.setAdapter(listAdapter);
        final String autoText = "✔ 自动关闭";
        final String shutText = "✘ 自动关闭";
        // 自定义头部右边文本并设置点击事件
        topBar.setRightText(shutText, new TopBar.OnTopClickListener() {
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

    private class ListAdapter extends ArrayAdapter<String> implements View.OnClickListener {
        private int layout;
        private LayoutInflater inflater;
        private List<String> list;
        private OnItemClick onItemClick = null;
        private boolean autoClose = false;

        // 创建 DrawerHolder 对象，非自动关闭的情况保存 item 状态使用
        private DrawerHolder drawerHolder = new DrawerHolder();

        private ListAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            layout = resource;
            inflater = LayoutInflater.from(context);
            list = objects;
        }

        private void setOnItemClick(OnItemClick click) {
            onItemClick = click;
        }

        private void setAutoClose(boolean bool) {
            autoClose = bool;
            drawerHolder.clearHolder(); // 清除已保存状态并关闭所有已打开 item
            notifyDataSetChanged(); // 更新 list
        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            int position = (int) view.getTag();
            SwipeDrawer swipeDrawer = SwipeDrawer.getParentDrawer(view);
            if (swipeDrawer == null) return;
            if (onItemClick != null) { // 点击事件回调
                onItemClick.onClick(swipeDrawer, position, id);
            }
        }

        @NonNull
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            View view;
            ViewHolder viewHolder;
            if (convertView == null) {
                view = inflater.inflate(layout, null);
                viewHolder = new ViewHolder();
                viewHolder.title = view.findViewById(R.id.item_title);
                viewHolder.itemMain = view.findViewById(R.id.itemMain);
                viewHolder.itemDing = view.findViewById(R.id.itemDing);
                viewHolder.itemDel = view.findViewById(R.id.itemDel);
                viewHolder.itemClose = view.findViewById(R.id.itemClose);
                viewHolder.itemMain.setOnClickListener(this);
                viewHolder.itemDing.setOnClickListener(this);
                viewHolder.itemDel.setOnClickListener(this);
                viewHolder.itemClose.setOnClickListener(this);
                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }
            SwipeDrawer swipeDrawer = ((SwipeDrawer) view);
            swipeDrawer.setAutoClose(autoClose); // 设置是否自动关闭
            viewHolder.itemMain.setTag(position);
            viewHolder.itemDing.setTag(position);
            viewHolder.itemDel.setTag(position);
            viewHolder.itemClose.setTag(position);
            viewHolder.title.setText(list.get(position));

            // bindHolder 的第一个参数传递 item 的 SwipeDrawer 布局
            // bindHolder 的第二个参数传递唯一对象
            drawerHolder.bindHolder(swipeDrawer, list.get(position));
            return view;
        }

        private class ViewHolder {
            View itemMain;
            View itemDing;
            View itemDel;
            View itemClose;
            TextView title;
        }
    }

}
