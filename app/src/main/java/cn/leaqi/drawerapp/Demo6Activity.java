package cn.leaqi.drawerapp;

import android.app.Activity;
import android.os.Bundle;

import cn.leaqi.drawerapp.Views.TopBar;

public class Demo6Activity extends Activity {

    TopBar topBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo6);
        topBar = new TopBar(this); // 头部操作类
        topBar.setTitle("多层嵌套"); // 头部标题
        topBar.showLeftBack(); // 显示头部返回按钮
    }
}
