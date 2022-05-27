package cn.leaqi.drawerapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import cn.leaqi.drawerapp.Utils.Common;
import cn.leaqi.drawerapp.Views.TopBar;
import cn.leaqi.drawer.SwipeDrawer;
import cn.leaqi.drawer.OnDrawerState;

public class Demo9Activity extends Activity {
    TopBar topBar = null;
    SwipeDrawer rootDrawer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo9);
        rootDrawer = findViewById(R.id.rootDrawer);
        topBar = new TopBar(this); // 头部操作类
        topBar.setTitle("顶部弹出"); // 头部标题
        AppInit();
        ListData();
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
        final View mask = findViewById(R.id.mask); // 自定义主布局遮罩
        final View topMask  = findViewById(R.id.topMask); // 自定义Top布局遮罩
        final GridView gridView = findViewById(R.id.gridView); // Top布局GridView
        final View topLayout = findViewById(R.id.topLayout);
        topLayout.post(new Runnable() {
            @Override
            public void run() {
                // 更新高度
                ViewGroup.LayoutParams getLp = topLayout.getLayoutParams();
                getLp.height = rootDrawer.getMeasuredHeight() - topBar.getHeight();
                topLayout.setLayoutParams(getLp);
                rootDrawer.requestLayout();
            }
        });

        // 设置头部右边图标并设置点击事件
        topBar.showRight(R.mipmap.icon_menu, new TopBar.OnTopClickListener() {
            @Override
            public void onClick(View view) {
                rootDrawer.toggleDrawer(SwipeDrawer.DIRECTION_TOP); // 点击打开或关闭Top方向
            }
        });

        // 监听 SwipeDrawer 状态
        rootDrawer.setOnDrawerState(new OnDrawerState() {
            private boolean radiusBar = false;
            @Override
            public void onStart(int type) { }
            @Override
            public void onMove(int type, float progress) { // 移动时
                mask.setAlpha(progress); // 根据打开进度设置主布局遮罩透明度
                topMask.setAlpha(1 - progress); // 根据打开进度设置Top布局遮罩透明度
                gridView.setAlpha(progress); // 根据打开进度设置 GridView 透明度
                // 根据打开进度设置 GridView 的缩放
                gridView.setScaleX(progress);
                gridView.setScaleY(progress);
                // 根据打开进度设置头部透明度
                topBar.setBarAlpha(rootDrawer.getShow() ? progress * 0.7f : 1 - progress);
                // 处理头部圆角
                if (!rootDrawer.getShow()) {
                    if (progress == 0) {
                        topBar.normalBar();
                        radiusBar = false;
                    } else {
                        if (!radiusBar) {
                            topBar.radiusBar();
                            radiusBar = true;
                        }
                    }
                }
            }
            @Override
            public void onOpen(int type) { // 打开
                if (type == SwipeDrawer.DIRECTION_TOP) { // 打开了Top方向
                    topBar.setRightIcon(R.mipmap.icon_close); // 设置头部右边图标
                    topBar.radiusBar();
                    radiusBar = true;
                }
            }
            @Override
            public void onClose(int type) { // 关闭
                if (type == SwipeDrawer.DIRECTION_TOP) { // 关闭了Top方向
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
        final GridView gridView = findViewById(R.id.gridView);
        final String[] dataList = {"视频","问答","漫画","财经","娱乐","科技","国际","军事","直播","情感","租赁","装修","音乐","航空","股票","NBA","短视频","房产","数码","手机","游戏","历史","电影","星座","旅游","图片","教育","育儿","文学","明星","科普","综艺","天气","美容","服饰","影视","美食","健康","搞笑","体育","预约","养生","时尚","公益","汽车","文化","艺术","金融","网游","电竞","商业","中超"};
        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.btn_item, dataList);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Common.showToast(dataList[i]);
            }
        });

    }

}
