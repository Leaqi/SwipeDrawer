package cn.leaqi.drawerapp.Views;

import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.tabs.TabLayout;
import cn.leaqi.drawerapp.R;
import cn.leaqi.drawerapp.Utils.Common;
import cn.leaqi.drawerapp.Utils.Config;
import cn.leaqi.drawer.SwipeDrawer;
import cn.leaqi.drawer.OnDrawerState;

import java.util.ArrayList;
import java.util.List;

/**
 * 视频布局用户主页类
 */
public class UserHome implements View.OnClickListener {
    private Context mContext;
    private Activity mActivity;
    private SwipeDrawer homeDrawer;
    private SwipeDrawer infoDrawer;
    private TabLayout homeTab;
    private ScrollView homeScroll;
    private ImageView homeBg;
    private ImageView homeIcon;
    private TextView homeName;
    private TextView homeId;
    private TextView homeText;
    private TextView homeAge;
    private ImageView homeSex;
    private TextView homeCity;
    private TextView numPraise;
    private TextView numFollow;
    private TextView numFans;
    private View homeFollow;
    private View homeCancel;

    private List<String> tabList = new ArrayList<String>(){{
        add("作品");
        add("喜欢");
    }};

    private List<Config.VideoBean> listData = new ArrayList<>();
    private ListAdapter listAdapter = null;

    private Config.UserBean userInfo;

    public UserHome(Context context) {
        mContext = context;
        mActivity = ((Activity)mContext);
        homeDrawer = mActivity.findViewById(R.id.homeDrawer);
        infoDrawer = mActivity.findViewById(R.id.infoDrawer);
        homeTab = mActivity.findViewById(R.id.homeTab);
        homeScroll = mActivity.findViewById(R.id.homeScroll);
        homeBg = mActivity.findViewById(R.id.homeBg);
        homeIcon = mActivity.findViewById(R.id.homeIcon);
        homeName = mActivity.findViewById(R.id.homeName);
        homeId = mActivity.findViewById(R.id.homeId);
        homeText = mActivity.findViewById(R.id.homeText);
        homeAge = mActivity.findViewById(R.id.homeAge);
        homeSex = mActivity.findViewById(R.id.homeSex);
        homeCity = mActivity.findViewById(R.id.homeCity);
        numPraise = mActivity.findViewById(R.id.numPraise);
        numFollow = mActivity.findViewById(R.id.numFollow);
        numFans = mActivity.findViewById(R.id.numFans);
        homeFollow = mActivity.findViewById(R.id.homeFollow);
        homeCancel = mActivity.findViewById(R.id.homeCancel);
        AppInit();
        ListData();
    }

    @Override
    public void onClick(View view) {
        int getId = view.getId();
        switch (getId) {
            case R.id.homeBack : // 点击左上角返回
                Common.sendKeyCode(KeyEvent.KEYCODE_BACK);
                break;
            case R.id.homeSet : // 点击右上角设置
                homeDrawer.openDrawer(SwipeDrawer.DIRECTION_BOTTOM);
                break;
            case R.id.homeClose : // 点击设置面板关闭按钮
                homeDrawer.closeDrawer();
                break;
            case R.id.homeFollow : // 点击关注
                if (userInfo != null) {
                    userInfo.isFollow = true;
                    homeCancel.setVisibility(View.VISIBLE);
                    homeFollow.setVisibility(View.GONE);
                    Common.showToast("已关注");
                }
                break;
            case R.id.homeCancel : // 点击取消关注
                if (userInfo != null) {
                    userInfo.isFollow = false;
                    homeCancel.setVisibility(View.GONE);
                    homeFollow.setVisibility(View.VISIBLE);
                    Common.showToast("取消关注");
                }
                break;
            case R.id.homeShare : // 点击设置面板分享
                Common.showToast("分享");
                homeDrawer.closeDrawer();
                break;
            case R.id.homeMsg : // 点击设置面板聊天
                Common.showToast("聊天");
                homeDrawer.closeDrawer();
                break;
            case R.id.homeWarn : // 点击设置面板举报
                Common.showToast("举报");
                homeDrawer.closeDrawer();
                break;
            case R.id.homeBlack : // 点击设置面板举报
                Common.showToast("拉黑");
                homeDrawer.closeDrawer();
                break;
        }
    }

    private void AppInit() {
        final View homeBg = mActivity.findViewById(R.id.homeBg);
        final View homeBack = mActivity.findViewById(R.id.homeBack);
        final View homeSet = mActivity.findViewById(R.id.homeSet);
        final View homeClose = mActivity.findViewById(R.id.homeClose);
        final View homeShare = mActivity.findViewById(R.id.homeShare);
        final View homeMsg = mActivity.findViewById(R.id.homeMsg);
        final View homeWarn = mActivity.findViewById(R.id.homeWarn);
        final View homeBlack = mActivity.findViewById(R.id.homeBlack);
        homeFollow.setOnClickListener(this);
        homeCancel.setOnClickListener(this);
        homeBack.setOnClickListener(this);
        homeSet.setOnClickListener(this);
        homeClose.setOnClickListener(this);
        homeShare.setOnClickListener(this);
        homeMsg.setOnClickListener(this);
        homeWarn.setOnClickListener(this);
        homeBlack.setOnClickListener(this);
        createTab();

        infoDrawer.setOnDrawerState(new OnDrawerState() {
            @Override
            public void onStart(int type) { }
            @Override
            public void onMove(int type, float progress) {
                // 主页顶部下拉背景放大缩小
                progress *= 0.5;
                homeBg.setScaleX(1 + progress);
                homeBg.setScaleY(1 + progress);
            }
            @Override
            public void onOpen(int type) { }
            @Override
            public void onClose(int type) { }
            @Override
            public void onCancel(int type) { }
        });
    }

    // 更新主页数据
    public void upHome(Config.UserBean info) {
        homeCancel.setVisibility(info.isFollow ? View.VISIBLE : View.GONE);
        homeFollow.setVisibility(info.isFollow ? View.GONE : View.VISIBLE);
        if (userInfo != info) {
            userInfo = info;
            homeScroll.scrollTo(0, 0); // scroll 回到顶部
            homeName.setText(userInfo.name); // 用户名称
            homeId.setText(("账号: " + (100000 + userInfo.id))); // 用户ID
            homeText.setText(userInfo.text); // 用户简介
            homeAge.setText((userInfo.age + "岁")); // 用户年龄
            homeCity.setText(userInfo.city); // 用户城市
            numPraise.setText(userInfo.getPraise()); // 获赞数量
            numFollow.setText(userInfo.getFollow()); // 关注数量
            numFans.setText(userInfo.getFans()); // 粉丝数量
            homeTab.getTabAt(0).select();
            // 设置用户性别图标
            if (userInfo.sex > 0) {
                homeSex.setVisibility(View.VISIBLE);
                homeSex.setImageResource(userInfo.sex == 1 ? R.mipmap.sex_1 : R.mipmap.sex_2);
            } else {
                homeSex.setVisibility(View.GONE);
            }
            Common.setHttpImage(homeBg, userInfo.bg); // 用户主页背景图
            Common.setHttpImage(homeIcon, userInfo.icon, R.mipmap.icon_user); // 用户头像
            SetList();
        }
    }

    private void createTab() {
        for (int i = 0; i < tabList.size(); i++) {
            homeTab.addTab(homeTab.newTab().setText(tabList.get(i)));
        }
        homeTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) { }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }
            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }

    private void SetList() {
        listData.clear();
        listData.addAll(Config.UserVideo.get(userInfo.id));
        homeTab.getTabAt(0).setText(tabList.get(0) + (listData.size() > 0 ? " " + listData.size() : ""));
        listAdapter.notifyDataSetChanged();
    }

    private void ListData() {
        final GridView homeGrid = mActivity.findViewById(R.id.homeGrid);
        listAdapter = new ListAdapter(mActivity, R.layout.home_item, listData);
        homeGrid.setAdapter(listAdapter);
    }

    private class ListAdapter extends ArrayAdapter<Config.VideoBean>{
        private int layout;
        private LayoutInflater inflater;
        private List<Config.VideoBean> list;

        private ListAdapter(Context context, int resource, List<Config.VideoBean> objects) {
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
                viewHolder.itemImg = view.findViewById(R.id.itemImg);
                viewHolder.title = view.findViewById(R.id.itemTitle);
                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }
            Config.VideoBean item = list.get(position);
            viewHolder.title.setText(item.getFavCount());
            Common.setHttpImage(viewHolder.itemImg, item.img);
            return view;
        }

        private class ViewHolder {
            ImageView itemImg;
            TextView title;
        }
    }

}
