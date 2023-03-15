package cn.leaqi.drawerapp;


import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import cn.leaqi.drawer.OnDrawerChange;
import cn.leaqi.drawer.OnDrawerSwitch;
import cn.leaqi.drawer.SwipeDrawer;
import cn.leaqi.drawerapp.Utils.Common;
import cn.leaqi.drawerapp.Views.NewScrollView;
import cn.leaqi.drawerapp.Views.TopBar;

public class Demo16Activity extends Activity {
    TopBar topBar = null;
    SwipeDrawer rootDrawer = null;
    List<String> listData = null;
    List<View> viewList = null;
    int deskWidth = 0;
    int deskHeight = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo16);
        rootDrawer = findViewById(R.id.rootDrawer);
        topBar = new TopBar(this); // 头部操作类
        topBar.setTitle("");
        topBar.showLeftBack();
        topBar.setNavigationBar();
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
        final View deskList = findViewById(R.id.deskList);
        final View deskMain = findViewById(R.id.deskMain);
        final ViewPager mainView = findViewById(R.id.mainView);
        final GridView mainList = findViewById(R.id.mainList);
        final NewScrollView mainScroll = findViewById(R.id.mainScroll);
        final CardView soLayout = findViewById(R.id.soLayout);
        final View soBorder = findViewById(R.id.soBorder);
        final View iconUp = findViewById(R.id.iconUp);
        final View leftBar = findViewById(R.id.leftBar);
        final View rightBar = findViewById(R.id.rightBar);
        final ScrollView barScroll = findViewById(R.id.barScroll);
        deskMain.post(new Runnable() {
            @Override
            public void run() {
                deskWidth = rootDrawer.getMeasuredWidth();
                deskHeight = rootDrawer.getMeasuredHeight();
            }
        });
        // 监听 SwipeDrawer 改变
        rootDrawer.setOnDrawerChange(new OnDrawerChange() {
            @Override
            public void onChange(SwipeDrawer view, int state, float progress) {
                boolean isLeft = view.getDirection() == SwipeDrawer.DIRECTION_LEFT;
                boolean isBottom = view.getDirection() == SwipeDrawer.DIRECTION_BOTTOM;
                switch (state) {
                    case SwipeDrawer.STATE_START: // 拖拽开始
                    case SwipeDrawer.STATE_OPEN : // 打开
                    case SwipeDrawer.STATE_CALL_OPEN: // 调用 openDrawer 方法打开
                        if (isLeft) {
                            rootDrawer.setMaskColor(Color.TRANSPARENT); // 设置遮罩透明
                        }
                        if (isBottom) {
                            deskMain.setVisibility(View.VISIBLE);
                            rootDrawer.setMaskColor(Color.parseColor("#80FFFFFF")); // 设置遮罩颜色
                        }
                        break;
                    case SwipeDrawer.STATE_PROGRESS : // 移动，progress 获取进度
                        int setHeight = (topBar.getHorizontal() ? deskWidth : deskHeight) - Common.dipToPx(getBaseContext(), 90);
                        if (isBottom) {
                            // 根据进度设置布局透明度
                            deskList.setAlpha(1 - progress);
                            deskMain.setAlpha(progress);
                            // 根据进度设置箭头位置及旋转
                            iconUp.setRotation(progress * 180);
                            iconUp.setTranslationY(-setHeight * progress);
                        }
                        break;
                    case SwipeDrawer.STATE_ANIM_OVER: // 动画执行完毕
                        if (isBottom) {
                            if (rootDrawer.getShow()) {
                                deskMain.setVisibility(View.VISIBLE);
                            } else {
                                mainScroll.scrollTo(0, 0); // mainScroll滚动到顶部
                                deskMain.setVisibility(View.GONE);
                            }
                        }
                        if (isLeft && !rootDrawer.getShow()) {
                            barScroll.scrollTo(0, 0); // barScroll滚动到顶部
                        }
                        break;
                }
            }
        });
        // 监听 SwipeDrawer 开关
        rootDrawer.setOnDrawerSwitch(new OnDrawerSwitch() {
            @Override
            public void onOpen(SwipeDrawer view) {
                if (view.getDirection() == SwipeDrawer.DIRECTION_LEFT) { // 显示隐藏箭头
                    leftBar.setVisibility(View.VISIBLE);
                    rightBar.setVisibility(View.GONE);
                }
            }
            @Override
            public void onClose(SwipeDrawer view) {
                if (view.getDirection() == SwipeDrawer.DIRECTION_LEFT) { // 显示隐藏箭头
                    leftBar.setVisibility(View.GONE);
                    rightBar.setVisibility(View.VISIBLE);
                }
            }
        });
        // 监听滚动
        mainScroll.setOnScrollListener(new NewScrollView.OnScrollListener() {
            private boolean isCheck = false; // 记录状态避免重复执行
            @Override
            public void onScroll(int height, int scrollY, int viewHeight) {
                if(scrollY <= 0) { // 滚动到头部
                    if (isCheck) {
                        soLayout.setCardElevation(0); // 隐藏阴影
                        soBorder.setAlpha(1); // 显示边框
                        isCheck = false;
                        Common.hideInput(Demo16Activity.this); // 关闭输入法
                    }
                }  else if (!isCheck) {
                    soLayout.setCardElevation(10); // 显示阴影
                    soBorder.setAlpha(0); // 隐藏边框
                    isCheck = true;
                    Common.hideInput(Demo16Activity.this); // 关闭输入法
                }
            }
        });
        // 点击左箭头关闭
        leftBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootDrawer.closeDrawer(SwipeDrawer.DIRECTION_LEFT);
            }
        });
        // 点击右箭头开启
        rightBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootDrawer.openDrawer(SwipeDrawer.DIRECTION_LEFT);
            }
        });
        ListView(mainView);
        ListData(mainList);
    }

    /**
     * 给 ViewPager 填充数据
     */
    private void ListView(ViewPager mainView) {
        viewList = new ArrayList<>();
        createPage(1, R.mipmap.desk1);
        createPage(2, R.mipmap.desk2);
        createPage(3, R.mipmap.desk3);
        createPage(4, R.mipmap.desk4);
        mainView.setAdapter(new ListPageAdapter(viewList));
    }

    // 创建Page
    private void createPage(int position, int bg) {
        View page = LayoutInflater.from(this).inflate(R.layout.desk_page, null);
        ImageView pageBg = page.findViewById(R.id.pageBg);
        TextView pageTxt = page.findViewById(R.id.pageTxt);
        pageBg.setImageResource(bg);
        pageTxt.setText(("ViewPager\n\nPage" + position));
        viewList.add(page);
    }

    /**
     * 给 GridView 填充数据
     */
    private void ListData(GridView mainList) {
        listData = new ArrayList<String>() {{
            for (int i = 0; i < 80; i++) {
                add("App" + (i + 1));
            }
        }};
        ListAdapter listAdapter = new ListAdapter(this, R.layout.grid_icon, listData);
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

    private class ListPageAdapter extends PagerAdapter {
        List<View> list;

        public ListPageAdapter(List<View> l) {
            list = l;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(list.get(position));
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(list.get(position));
            return list.get(position);
        }
    }

    public void showTag(View view) {
        Common.showToast(view.getTag().toString());
    }

    public void showText(View view) {
        Common.showToast(((TextView)view).getText().toString());
    }

}
