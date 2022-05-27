package cn.leaqi.drawerapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.SeekBar;

import cn.leaqi.drawerapp.Utils.Common;
import cn.leaqi.drawerapp.Views.TopBar;
import cn.leaqi.drawer.SwipeDrawer;

public class Demo8Activity extends Activity {
    TopBar topBar = null;
    SwipeDrawer rootDrawer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo8);
        rootDrawer = findViewById(R.id.rootDrawer);
        topBar = new TopBar(this); // 头部操作类
        topBar.setTitle("底部弹出"); // 头部标题
        topBar.setRightText("滑动到底部或者点击➜"); // 显示头部右边文本
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
        final View bottomLayout = findViewById(R.id.bottomLayout);
        final int bottomTop = bottomLayout.getPaddingTop(); // 获取顶部距离
        final SeekBar seekBar = findViewById(R.id.seekBar);
        final View close = findViewById(R.id.close); // 关闭按钮

        // 设置头部右边图标并设置点击事件
        topBar.showRight(R.mipmap.icon_menu, new TopBar.OnTopClickListener() {
            @Override
            public void onClick(View view) {
                rootDrawer.toggleDrawer(SwipeDrawer.DIRECTION_BOTTOM); // 点击打开或关闭Bottom方向
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                bottomLayout.setPadding(0, bottomTop + i,0 , 0); // 设置顶部距离
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        // 关闭按钮点击事件
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rootDrawer.getShow()) {
                    rootDrawer.closeDrawer(); // 关闭
                }
            }
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
