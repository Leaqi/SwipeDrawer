package cn.leaqi.drawerapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import cn.leaqi.drawer.AnimThread;
import cn.leaqi.drawerapp.Utils.Cache;
import cn.leaqi.drawerapp.Utils.Common;
import cn.leaqi.drawerapp.Views.TopBar;
import cn.leaqi.drawerapp.Views.UserInfo;
import cn.leaqi.drawerapp.Views.UserMenu;
import cn.leaqi.drawer.SwipeDrawer;
import cn.leaqi.drawer.OnDrawerState;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    TopBar topBar = null;
    SwipeDrawer rootDrawer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        topBar = new TopBar(this); // 头部操作类
        rootDrawer = findViewById(R.id.rootDrawer);
        new UserMenu(this); // 左侧边栏用户菜单操作类
        new UserInfo(this); // 右侧边栏用户菜单操作类
        AppInit();
        ListData();
        Cache.clearCache();
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

    public void AppInit() {
        final View mask = findViewById(R.id.mask); // 自定义遮罩
        final TextView modeDrawer = findViewById(R.id.modeDrawer); // 抽屉模式按钮
        final TextView modeCover = findViewById(R.id.modeCover); // 覆盖模式按钮
        final TextView modeFixed = findViewById(R.id.modeFixed); // 固定模式按钮

        modeDrawer.setSelected(true); // 默认选中抽屉模式按钮
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int isSet = -1;
                if (view == modeDrawer && rootDrawer.getMode() != SwipeDrawer.MODE_DRAWER) { // 被点击的是 抽屉模式按钮 且当前状态非抽屉模式
                    rootDrawer.setMode(SwipeDrawer.MODE_DRAWER); // 设置模式为抽屉模式
                    isSet = rootDrawer.getMode() == SwipeDrawer.MODE_DRAWER ? 1 : 0; // 判断是否设置成功
                } else if (view == modeCover && rootDrawer.getMode() != SwipeDrawer.MODE_COVER) { // 被点击的是 覆盖模式按钮 且当前状态非覆盖模式
                    rootDrawer.setMode(SwipeDrawer.MODE_COVER); // 设置模式为覆盖模式
                    isSet = rootDrawer.getMode() == SwipeDrawer.MODE_COVER ? 1 : 0; // 判断是否设置成功
                } else if (view == modeFixed && rootDrawer.getMode() != SwipeDrawer.MODE_FIXED) { // 被点击的是 固定模式按钮 且当前状态非固定模式
                    rootDrawer.setMode(SwipeDrawer.MODE_FIXED); // 设置模式为固定模式
                    isSet = rootDrawer.getMode() == SwipeDrawer.MODE_FIXED ? 1 : 0; // 判断是否设置成功
                }
                if (isSet == 1) { // 设置成功
                    // 更新选中按钮
                    modeFixed.setSelected(view == modeFixed);
                    modeCover.setSelected(view == modeCover);
                    modeDrawer.setSelected(view == modeDrawer);
                    Common.showToast("切换成功");
                } else if (isSet == 0) {
                    Common.showToast("切换失败");
                }
            }
        };
        // 绑定按钮点击事件
        modeDrawer.setOnClickListener(onClickListener);
        modeCover.setOnClickListener(onClickListener);
        modeFixed.setOnClickListener(onClickListener);

        // 设置头部左边图标并设置点击事件
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
            public void onMove(int type, float progress) {
                mask.setAlpha(progress); // 根据打开进度设置遮罩透明度
                if (rootDrawer.getMode() == SwipeDrawer.MODE_DRAWER && type == SwipeDrawer.DIRECTION_LEFT) { // 模式为 抽屉模式，且操作的是left方向
                    progress *= 0.15; // 给进度打个折
                    rootDrawer.setMainScale(1 - progress); // 根据打开进度缩放主布局
                }
            }
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

    /**
     * 给 GridView 填充数据
     */
    private void ListData() {
        final GridView mainList = findViewById(R.id.mainList);
        final List<BtnBean> listData = new ArrayList<BtnBean>(){{
            add(new BtnBean("ListView", "Demo1", "#3F51B5"));
            add(new BtnBean("RecyclerView", "Demo2", "#059C8E"));
            add(new BtnBean("GridView", "Demo3", "#A8BB00"));
            add(new BtnBean("ScrollView", "Demo4", "#FF5722"));
            add(new BtnBean("ViewPager", "Demo5", "#0DAC14"));
            add(new BtnBean("多层嵌套", "Demo6", "#B328CA"));
            add(new BtnBean("边缘拖拽", "Demo7", "#E91E63"));
            add(new BtnBean("底部弹出", "Demo8", "#FF9800"));
            add(new BtnBean("顶部弹出", "Demo9", "#4D12F7"));
            add(new BtnBean("短视频布局", "Demo10", "#161722"));
            add(new BtnBean("ListView 下拉刷新", "Demo11", "#23BB5B"));
            add(new BtnBean("RecyclerView 下拉刷新", "Demo12", "#AE4DD1"));
            add(new BtnBean("GridView 下拉刷新", "Demo13", "#447CCF"));
            add(new BtnBean("ScrollView 下拉刷新", "Demo14", "#20B1AC"));
            add(new BtnBean("地图位置抽屉", "Demo15", "#5f2abd"));
            add(new BtnBean("桌面抽屉", "Demo16", "#4b9d22"));
        }};
        final ListAdapter listAdapter = new ListAdapter(this, R.layout.list_btn, listData);
        mainList.setAdapter(listAdapter);
    }

    private class BtnBean {
        String text; // 显示文本
        String link; // 打开链接
        int color; // 背景颜色
        BtnBean(String t, String l, String c) {
            text = t;
            link = l;
            color = Color.parseColor(c);
        }
    }

    private class ListAdapter extends ArrayAdapter<BtnBean> implements View.OnClickListener {
        private int layout;
        private LayoutInflater inflater;
        private List<BtnBean> list;
        private int TagString = R.attr.TagString;

        private ListAdapter(Context context, int resource, List<BtnBean> objects) {
            super(context, resource, objects);
            layout = resource;
            inflater = LayoutInflater.from(context);
            list = objects;
        }

        @Override
        public void onClick(View view) {
            String link = (String) view.getTag(TagString);
            openActivity(link);
        }

        @NonNull
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            View view;
            ViewHolder viewHolder;
            if (convertView == null) {
                view = inflater.inflate(layout, null);
                viewHolder = new ViewHolder();
                viewHolder.btnLeft = view.findViewById(R.id.btnLeft);
                viewHolder.btnRight = view.findViewById(R.id.btnRight);
                viewHolder.btnTop = view.findViewById(R.id.btnTop);
                viewHolder.btnBottom = view.findViewById(R.id.btnBottom);
                viewHolder.btnText = view.findViewById(R.id.btnText);
                view.setTag(viewHolder);
                view.setOnClickListener(this);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }
            BtnBean item = list.get(position);
            viewHolder.btnLeft.setText(item.link);
            viewHolder.btnRight.setText(item.link);
            viewHolder.btnTop.setText(item.link);
            viewHolder.btnBottom.setText(item.link);
            viewHolder.btnText.setText(item.text);
            viewHolder.btnText.setBackgroundColor(item.color);
            view.setTag(TagString, item.link);
            return view;
        }

        private class ViewHolder {
            TextView btnLeft;
            TextView btnRight;
            TextView btnTop;
            TextView btnBottom;
            TextView btnText;
        }
    }

    /**
     * 打开 Activity
     * @param link 链接
     */
    public void openActivity(String link) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("drawer://leaqi.cn/" + link));
        MainActivity.this.startActivity(intent);
    }

}
