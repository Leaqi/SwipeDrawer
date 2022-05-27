package cn.leaqi.drawerapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import cn.leaqi.drawerapp.Views.TopBar;
import cn.leaqi.drawer.SwipeDrawer;

import java.util.ArrayList;
import java.util.List;

public class Demo5Activity extends Activity {

    TopBar topBar = null;
    SwipeDrawer mainDrawer = null;
    ViewPager mainView = null;
    BottomNavigationView menuView = null;
    List<View> viewList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo5);
        topBar = new TopBar(this); // 头部操作类
        topBar.setTitle("ViewPager"); // 头部标题
        topBar.showLeftBack(); // 显示头部返回按钮
        mainDrawer = findViewById(R.id.mainDrawer);
        mainView = findViewById(R.id.mainView);
        menuView = findViewById(R.id.menuView);
        viewList = new ArrayList<>();
        AppInit();
    }

    private void AppInit() {
        showPage(0);

        createPage(1);
        createPage(2);
        createPage(3);
        createPage(4);

        mainView.setAdapter(new ListPageAdapter(viewList));
        // Page切换监听
        mainView.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }
            @Override
            public void onPageSelected(int position) {
                menuView.getMenu().getItem(position).setChecked(true); // 选中导航菜单
                showPage(position); // 更新文本
            }
            @Override
            public void onPageScrollStateChanged(int state) { }
        });
        // 导航菜单点击监听
        menuView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (mainDrawer.getShow()) { // 如果最外层 SwipeDrawer 是打开状态
                    mainDrawer.closeDrawer(); // 关闭
                }
                mainView.setCurrentItem(item.getOrder()); // Page切换
                return true;
            }
        });
    }

    // 创建Page
    private void createPage(int position) {
        View page = LayoutInflater.from(this).inflate(R.layout.view_page, null);
        TextView mainView = page.findViewById(R.id.Main);
        TextView topView = page.findViewById(R.id.Top);
        TextView bottomView = page.findViewById(R.id.Bottom);
        mainView.setText(("ViewPager\n\nPage" + position));
        topView.setText(("ViewPager\n\nPage" + position + " - Top"));
        bottomView.setText(("ViewPager\n\nPage" + position + " - Bottom"));
        viewList.add(page);
        menuView.getMenu().add(0, position - 1, position - 1,"Page" + position).setIcon(R.drawable.menu_item);
    }

    // 更新头部文本
    private void showPage(int position) {
        topBar.setRightText("Page" + (position + 1));
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

}
