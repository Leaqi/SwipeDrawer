package cn.leaqi.drawerapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import cn.leaqi.drawerapp.Utils.Common;
import cn.leaqi.drawerapp.Utils.Config;
import cn.leaqi.drawerapp.Views.MarqueView;
import cn.leaqi.drawerapp.Views.TopBar;
import cn.leaqi.drawerapp.Views.UserComment;
import cn.leaqi.drawerapp.Views.UserHome;
import cn.leaqi.drawerapp.Views.UserMenu;
import cn.leaqi.drawerapp.Views.UserShare;
import cn.leaqi.drawerapp.Views.VideoPlay;
import cn.leaqi.drawer.SwipeDrawer;
import cn.leaqi.drawer.OnDrawerChange;
import cn.leaqi.drawer.OnDrawerState;
import cn.leaqi.drawer.OnDrawerSwitch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Demo10Activity extends Activity implements View.OnClickListener, OnDrawerState, OnDrawerSwitch {

    TopBar topBar = null;
    SwipeDrawer lastDrawer = null;
    SwipeDrawer rootDrawer = null;
    ViewPager mainView = null;
    List<View> viewList = null;
    TabLayout tabLayout = null;
    View topTab = null;
    View topSet = null;
    View topSo = null;
    View topLoad = null;
    EditText soVal = null;
    UserHome userHome;
    List<String> tabList = new ArrayList<String>(){{
        add("同城");
        add("关注");
        add("推荐");
    }};
    List<ViewPager2> viewPagerList = new ArrayList<>();
    List<VideoPageAdapter> viewAdapterList = new ArrayList<>();

    RecyclerView topList = null;
    List<String> topListData = null;
    TopListAdapter topListAdapter = null;

    int tabIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo10);
        topBar = new TopBar(this, false); // 头部操作类
        rootDrawer = findViewById(R.id.rootDrawer);
        mainView = findViewById(R.id.mainView);
        tabLayout = findViewById(R.id.tabLayout);
        topTab = findViewById(R.id.topTab);
        topSet = findViewById(R.id.topSet);
        topSo = findViewById(R.id.topSo);
        soVal = findViewById(R.id.soVal);
        topLoad = findViewById(R.id.topLoad);
        viewList = new ArrayList<>();
        userHome = new UserHome(this); // 用户主页类
        new UserMenu(this); // 左侧边栏用户菜单操作类
        new UserShare(this); // 用户分享类
        topBar.setNavigationBar();
        AppInit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) { // 监听返回键
            if (closeLast()) { // 如果是打开状态则关闭
                return true; // 拦截
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onStart() {
        super.onStart();
        playVideo(true);
    }

    @Override
    protected void onStop() {
        playVideo(false);
        super.onStop();
    }

    @Override
    public void onClick(View view) {
        int getId = view.getId();
        switch (getId) {
            case R.id.shareClose : // 视频分享关闭按钮
            case R.id.soBack : // 搜索顶部返回按钮
                rootDrawer.closeDrawer(); // 关闭
                break;
            case R.id.topSet : // 顶部左边设置按钮
                rootDrawer.openDrawer(SwipeDrawer.DIRECTION_LEFT); // 打开Left方向
                break;
            case R.id.topSo : // 顶部右边搜索按钮
                rootDrawer.openDrawer(SwipeDrawer.DIRECTION_TOP); // 打开Top方向
                break;
            case R.id.soCode : // 搜索扫码按钮
                Common.showToast("扫码");
                break;
            case R.id.soBtn : // 搜索提交按钮
                // 提交搜索词至搜索记录 list
                String getText = soVal.getText().toString();
                if (getText.length() > 0) {
                    Common.hideInput(this);
                    topListData.add(0, getText);
                    topListAdapter.notifyItemInserted(0);
                    topList.scrollToPosition(0);
                    soVal.setText("");
                }
                break;
        }
    }

    @Override
    public void onStart(int type) { }

    @Override
    public void onMove(int type, float progress) {
        topTab.setAlpha(1 - progress);
    }

    @Override
    public void onOpen(int type) { }

    @Override
    public void onClose(int type) { }

    @Override
    public void onCancel(int type) { }

    @Override
    public void onOpen(SwipeDrawer view) { // 打开
        closeLast();
        lastDrawer = view;
        if (view == rootDrawer) { // 如果是最外层 SwipeDrawer 则暂停视频
            playVideo(false);
        }
    }

    @Override
    public void onClose(SwipeDrawer view) { // 关闭
        lastDrawer = null;
        if (view == rootDrawer) { // 如果是最外层 SwipeDrawer 则播放视频
            playVideo(true);
            soVal.setText("");
        }
        Common.hideInput(this);
    }

    private void AppInit() {
        final View userTop = findViewById(R.id.userTop);
        final View topLayout = findViewById(R.id.topLayout);
        // 空出状态栏高度
        topTab.setPadding(0, topBar.getStatusHeight(), 0, 0);
        userTop.setPadding(0, topBar.getStatusHeight(), 0, 0);
        topLayout.setPadding(0, topBar.getStatusHeight(), 0, 0);
        // 绑定按钮点击事件
        findViewById(R.id.shareClose).setOnClickListener(this);
        findViewById(R.id.soBack).setOnClickListener(this);
        findViewById(R.id.soCode).setOnClickListener(this);
        findViewById(R.id.soBtn).setOnClickListener(this);
        topSet.setOnClickListener(this);
        topSo.setOnClickListener(this);

        createPage(0); // 同城Page
        createPage(1); // 关注Page
        createPage(2); // 推荐Page

        tabLayout.setupWithViewPager(mainView); // tab绑定ViewPager

        mainView.setOffscreenPageLimit(3);
        mainView.setAdapter(new ListPageAdapter(viewList));
        mainView.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }
            @Override
            public void onPageSelected(int position) {
                tabLayout.getTabAt(position).select();
                closeLast();
                playVideo(true); // ViewPager切换后播放视频
            }
            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_DRAGGING) { // ViewPager切换时
                    VideoPlay.setViewAlpha(0); // 隐藏视频控制器
                    setViewAlpha(0.2f); // 主体内容透明
                } else {  // ViewPager切换完毕
                    VideoPlay.setViewAlpha(1); // 显示视频控制器
                    setViewAlpha(1f); // 恢复主体内容透明
                }
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) { }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }
            @Override
            public void onTabReselected(TabLayout.Tab tab) { // 点击tab按钮
                if (tabIndex == tab.getPosition()) { // 如果点击是当前位置按钮则执行刷新
                    topSet.setVisibility(View.GONE); // 隐藏设置按钮
                    topSo.setVisibility(View.GONE); // 隐藏搜索按钮
                    topLoad.setVisibility(View.VISIBLE); // 显示刷新布局
                    ((AnimationDrawable) topLoad.getBackground()).start(); // 执行刷新动画
                    // 动画延迟1.5秒
                    topLoad.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // 刷新更新数据
                            viewAdapterList.get(tabIndex).randData();
                            viewPagerList.get(tabIndex).setAdapter(viewAdapterList.get(tabIndex));
                            topSet.setVisibility(View.VISIBLE);
                            topSo.setVisibility(View.VISIBLE);
                            topLoad.setVisibility(View.GONE);
                            ((AnimationDrawable) topLoad.getBackground()).stop();
                        }
                    }, 1500);
                }
                tabIndex = tab.getPosition();
            }
        });

        // 监听 SwipeDrawer 状态
        rootDrawer.setOnDrawerState(this);
        rootDrawer.setOnDrawerSwitch(this);
        tabLayout.getTabAt(2).select(); // 默认显示推荐tab
        mainView.setCurrentItem(2); // 默认显示推荐Page

        TopListData();
    }

    // 创建Page
    private void createPage(final int index) {
        final View page = LayoutInflater.from(this).inflate(R.layout.video_page, null);
        final SwipeDrawer loadDrawer = page.findViewById(R.id.loadDrawer);
        final ImageView reTopIcon = page.findViewById(R.id.reTopIcon);
        final ViewPager2 mainPage = page.findViewById(R.id.mainPage);
        final List<ItemBean> mainList = new ArrayList<ItemBean>(){{
            for(int i = 0; i < Config.VideoList.size(); i++) {
                int key = Config.VideoList.keyAt(i);
                Config.UserBean user = Config.UserList.get(key);
                Config.VideoBean video = Config.VideoList.get(key);
                Config.MusicBean music = Config.MusicList.get(key);
                add(new ItemBean(user, video, music));
            }
        }};
        Collections.shuffle(mainList);
        final VideoPageAdapter adapter = new VideoPageAdapter(mainList, index);
        mainPage.setOffscreenPageLimit(3);
        mainPage.setAdapter(adapter);


        loadDrawer.setOnDrawerChange(new OnDrawerChange() {
            private void topOver() {
                // 当前Page下拉刷新完毕
                adapter.randData();
                mainPage.setAdapter(adapter);
                ((AnimationDrawable) reTopIcon.getBackground()).stop();
                reTopIcon.setScaleX(1);
                reTopIcon.setScaleY(1);
                // 0.3秒后关闭下拉属性布局
                reTopIcon.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadDrawer.closeDrawer();
                    }
                }, 300);
            }
            @Override
            public void onChange(final SwipeDrawer view, int state, float progress) {
                boolean isTop = view.getDirection() == SwipeDrawer.DIRECTION_TOP;
                switch (state) {
                    case SwipeDrawer.STATE_PROGRESS :
                        if (!view.getShow()) {
                            if (progress > 1) progress = 1;
                            if (isTop) {
                                topTab.setAlpha(1 - progress); // 根据下拉进度设置头部透明度
                                // 根据下拉进度放大缩小刷新图标
                                reTopIcon.setScaleX(progress);
                                reTopIcon.setScaleY(progress);
                            }
                        }
                        break;
                    case SwipeDrawer.STATE_OPEN :
                        if (isTop) {
                            // 打开后执行动画
                            ((AnimationDrawable) reTopIcon.getBackground()).start();
                            // 1.5秒后执行关闭
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (view.getShow()) {
                                        topOver();
                                    }
                                }
                            }, 1500);
                        }
                        break;
                    case SwipeDrawer.STATE_ANIM_OVER:
                        if (!view.getShow()) {
                            if (isTop) {
                                // 刷新完毕初始化布局状态
                                ((AnimationDrawable) reTopIcon.getBackground()).stop();
                                reTopIcon.setScaleX(1);
                                reTopIcon.setScaleY(1);
                            }
                        }
                        break;
                }
            }
        });

        mainPage.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                adapter.playVideo(position); // 播放当前位置视频
            }
            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager2.SCROLL_STATE_DRAGGING) { // ViewPager2切换时
                    VideoPlay.setViewAlpha(0); // 隐藏视频控制器
                    setViewAlpha(0.2f); // 主体内容透明
                } else {  // ViewPager2切换完毕
                    VideoPlay.setViewAlpha(1); // 显示视频控制器
                    setViewAlpha(1f); // 恢复主体内容透明
                }
            }
        });
        viewAdapterList.add(adapter);
        viewPagerList.add(mainPage);
        viewList.add(page);
    }

    // 控制视频播放暂停
    private void playVideo(boolean play) {
        VideoPageAdapter adapter;
        for (ViewPager2 view : viewPagerList) {
            adapter = (VideoPageAdapter) view.getAdapter();
            if (adapter != null) {
                if (play) {
                    adapter.playVideo(view.getCurrentItem());
                } else {
                    adapter.stopAll();
                }
            }
        }
    }

    // 控制主体内容透明度及音乐旋转与滚动
    private void setViewAlpha(float alpha) {
        if (lastHolder.size() > 0) {
            for (VideoPageAdapter.ListViewHolder holder : lastHolder) {
                holder.infoBox.setAlpha(alpha);
                holder.btnBox.setAlpha(alpha);
                if (alpha >= 1 && holder.itemVideo.isPlaying()) {
                    Common.animRotate(holder.itemMusicIcon, 2000);
                    holder.itemMusicText.setFocused(true);
                } else {
                    holder.itemMusicIcon.clearAnimation();
                    holder.itemMusicText.setFocused(false);
                }
            }
        }
    }

    // 关闭最后一次打开的SwipeDrawer
    private boolean closeLast() {
        if (lastDrawer != null) {
            if (lastDrawer.getShow()) {
                lastDrawer.closeDrawer();
                return true;
            }
        }
        return false;
    }

    private class ListPageAdapter extends PagerAdapter {
        private List<View> list;

        private ListPageAdapter(List<View> l) {
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

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return tabList.get(position);
        }
    }

    private class ItemBean {
        Config.UserBean user; // 用户类
        Config.VideoBean video; // 视频类
        Config.MusicBean music; // 音乐类
        private ItemBean(Config.UserBean user, Config.VideoBean video, Config.MusicBean music) {
            this.user = user;
            this.video = video;
            this.music = music;
        }
    }

    private final List<VideoPageAdapter.ListViewHolder> lastHolder = new ArrayList<>();

    private class VideoPageAdapter extends RecyclerView.Adapter<VideoPageAdapter.ListViewHolder> implements VideoPlay.OnPlayState {

        private List<ItemBean> list;
        private int index = -1;
        private Map<Integer, ListViewHolder> playList = new HashMap<>();

        private VideoPageAdapter(List<ItemBean> l, int i) {
            list = l;
            index = i;
        }

        private void randData() {
            Collections.shuffle(list);
        }

        private void playVideo(int position) {
            stopAll();
            ListViewHolder holder = playList.get(position);
            if (holder != null && index == mainView.getCurrentItem()) {
                holder.itemVideo.start();
                ItemBean item = list.get(position);
                userHome.upHome(item.user); // 更新用户主页为当前用户
                // 是否已经关注
                if (item.user.isFollow) {
                    holder.iconFollow.setVisibility(View.GONE);
                } else {
                    holder.iconFollow.setImageResource(R.mipmap.follow_add);
                    holder.iconFollow.setVisibility(View.VISIBLE);
                }
            }
        }

        private void stopAll() {
            for (Map.Entry<Integer, ListViewHolder> val : playList.entrySet()) {
                ListViewHolder item = val.getValue();
                item.itemVideo.pause();
            }
        }

        @Override
        public void onStart(VideoPlay view) { }

        @Override
        public void onPlay(VideoPlay view) {
            final ListViewHolder holder = (ListViewHolder) view.getTag();
            if (holder != null) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 视频开始播放后音乐旋转滚动
                        Common.animRotate(holder.itemMusicIcon, 2000);
                        holder.itemMusicText.setFocused(true);
                        holder.itemVideo.setAlpha(1f);
                        holder.itemImg.setVisibility(View.GONE);
                    }
                }, 300);
            }
        }

        @Override
        public void onStop(VideoPlay view) {
            ListViewHolder holder = (ListViewHolder) view.getTag();
            if (holder != null) {
                // 视频暂停后音乐旋转滚动停止
                holder.itemMusicIcon.clearAnimation();
                holder.itemMusicText.setFocused(false);
            }
        }

        @Override
        public void onError(VideoPlay view) { }

        @NonNull
        @Override
        public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(Demo10Activity.this).inflate(R.layout.video_item, parent, false);
            return new ListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
            ItemBean item = list.get(position);
            holder.itemTitle.setText(("@" + item.user.name)); // 视频标题
            holder.itemText.setText(item.video.text); // 视频描述
            holder.iconFavCount.setText(item.video.getFavCount()); // 点赞数量
            holder.iconSayCount.setText(item.video.getSayCount()); // 评论数量
            holder.iconShareCount.setText(item.video.getShareCount()); // 分享数量
            holder.itemMusicText.setText(item.music.text); // 音乐名称
            Common.setHttpImage(holder.itemImg, item.video.img); // 默认视频背景
            Common.setHttpImage(holder.itemIcon, item.user.icon, R.mipmap.icon_user); // 用户头像
            Common.setHttpImage(holder.iconMusic, item.music.icon, R.mipmap.icon); // 音乐图标
            holder.iconFav.setImageResource(item.video.isFav ? R.mipmap.icon_fav_cur : R.mipmap.icon_fav); // 是否点赞
            holder.iconFollow.setVisibility(item.user.isFollow ? View.GONE : View.VISIBLE); // 是都关注

            playList.put(position, holder);
            holder.itemVideo.setTag(holder);
            holder.itemVideo.setVideoPath(item.video.src);
            holder.itemVideo.setOnPlayState(this);
            holder.userComment.setData(); // 设置评论数据
        }

        @Override
        public void onViewAttachedToWindow(@NonNull ListViewHolder holder) {
            super.onViewAttachedToWindow(holder);
            lastHolder.add(holder);
        }

        @Override
        public void onViewDetachedFromWindow(@NonNull ListViewHolder holder) {
            super.onViewDetachedFromWindow(holder);
            lastHolder.remove(holder);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            private SwipeDrawer itemDrawer;
            private ImageView itemImg;
            private View itemVideoBox;
            private VideoPlay itemVideo;
            private ImageView itemIcon;
            private ImageView iconFollow;
            private View itemFav;
            private ImageView iconFav;
            private View itemSay;
            private TextView iconFavCount;
            private TextView iconSayCount;
            private TextView iconShareCount;
            private View sayClose;
            private View itemShare;
            private TextView itemTitle;
            private TextView itemText;
            private View itemMusicIcon;
            private ImageView iconMusic;
            private MarqueView itemMusicText;

            private View infoBox;
            private View btnBox;

            private UserComment userComment;

            private ListViewHolder(@NonNull View itemView) {
                super(itemView);
                itemDrawer = itemView.findViewById(R.id.itemDrawer);
                itemImg = itemView.findViewById(R.id.itemImg);
                itemVideoBox = itemView.findViewById(R.id.itemVideoBox);
                itemVideo = itemView.findViewById(R.id.itemVideo);
                itemIcon = itemView.findViewById(R.id.itemIcon);
                iconFollow = itemView.findViewById(R.id.iconFollow);
                itemFav = itemView.findViewById(R.id.itemFav);
                iconFav = itemView.findViewById(R.id.iconFav);
                itemSay = itemView.findViewById(R.id.itemSay);
                iconFavCount = itemView.findViewById(R.id.iconFavCount);
                iconSayCount = itemView.findViewById(R.id.iconSayCount);
                iconShareCount = itemView.findViewById(R.id.iconShareCount);
                sayClose = itemView.findViewById(R.id.sayClose);
                itemShare = itemView.findViewById(R.id.itemShare);
                itemTitle = itemView.findViewById(R.id.itemTitle);
                itemText = itemView.findViewById(R.id.itemText);
                itemMusicIcon = itemView.findViewById(R.id.itemMusicIcon);
                itemMusicText = itemView.findViewById(R.id.itemMusicText);
                iconMusic = itemView.findViewById(R.id.iconMusic);
                infoBox = itemView.findViewById(R.id.infoBox);
                btnBox = itemView.findViewById(R.id.btnBox);
                userComment = new UserComment(Demo10Activity.this, itemView);
                itemTitle.setOnClickListener(this);
                itemIcon.setOnClickListener(this);
                iconFollow.setOnClickListener(this);
                itemFav.setOnClickListener(this);
                itemSay.setOnClickListener(this);
                sayClose.setOnClickListener(this);
                itemShare.setOnClickListener(this);
                itemDrawer.setOnDrawerState(Demo10Activity.this);
                itemDrawer.setOnDrawerSwitch(Demo10Activity.this);
            }

            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();
                ItemBean item = list.get(position);
                if (view == itemSay) { // 点击打开评论
                    itemDrawer.openDrawer(SwipeDrawer.DIRECTION_BOTTOM); // 打开Item下SwipeDrawer Bottom方向评论
                } else if (view == sayClose) { // 点击关闭评论
                    itemDrawer.closeDrawer(); // 关闭Item下SwipeDrawer Bottom方向
                } else if (view == iconFollow) { // 点击关注
                    if (item.user.isFollow) return;
                    item.user.isFollow = true;
                    // 关注加号的动画
                    iconFollow.setImageResource(R.mipmap.follow_ok);
                    Common.scaleHide(iconFollow, 150);
                    userHome.upHome(item.user); // 关注后更新用户主页
                } else if (view == itemFav) { // 点击点赞
                    // 点赞与取消点赞的操作
                    item.video.isFav = !item.video.isFav;
                    item.video.favCount = item.video.isFav ? item.video.favCount + 1 : item.video.favCount - 1;
                    iconFav.setImageResource(item.video.isFav ? R.mipmap.icon_fav_cur : R.mipmap.icon_fav);
                    iconFavCount.setText(item.video.getFavCount());
                } else if (view == itemShare) { // 点击分享
                    rootDrawer.openDrawer(SwipeDrawer.DIRECTION_BOTTOM); // 打开最外层SwipeDrawer Bottom方向分享
                } else if (view == itemIcon || view == itemTitle) { // 点击用户头像或用户名称
                    rootDrawer.openDrawer(SwipeDrawer.DIRECTION_RIGHT);  // 打开最外层SwipeDrawer Right方向用户主页
                }
            }

        }
    }

    // 顶部搜索list
    private void TopListData() {
        topList = findViewById(R.id.topList);
        topListData = new ArrayList<String>(){{
            add("DrawerApp");
            add("SwipeDrawer");
        }};
        topListAdapter = new TopListAdapter(this, R.layout.item_so, topListData);
        topList.setLayoutManager(new LinearLayoutManager(this));
        topList.setAdapter(topListAdapter);
    }

    private class TopListAdapter extends RecyclerView.Adapter<TopListAdapter.ListViewHolder> {
        private int layout;
        private LayoutInflater inflater;
        private List<String> list;

        private TopListAdapter(Context context, int resource, List<String> objects) {
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
            View del;

            private ListViewHolder(View view) {
                super(view);
                title = view.findViewById(R.id.item_title);
                del = view.findViewById(R.id.item_del);
                title.setOnClickListener(this);
                del.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                if (view == title) {
                    Common.showToast(list.get(getAdapterPosition()));
                } else if (view == del) {
                    topListData.remove(getAdapterPosition());
                    topListAdapter.notifyItemRemoved(getAdapterPosition());
                }
            }
        }

    }

}
