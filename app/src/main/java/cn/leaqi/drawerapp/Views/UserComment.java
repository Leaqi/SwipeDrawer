package cn.leaqi.drawerapp.Views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import cn.leaqi.drawerapp.R;
import cn.leaqi.drawerapp.Utils.Common;
import cn.leaqi.drawerapp.Utils.Config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 视频布局用户评论类
 */
public class UserComment implements View.OnClickListener {
    private Context mContext;
    private RecyclerView mainList;
    private ListAdapter listAdapter;
    private List<ItemBean> listData = new ArrayList<>();

    private View parent;
    private View itemSend;
    private View sendIcon;
    private EditText sendVal;
    private View sendAt;
    private View sendBq;
    private View sendSubmit;

    private int keyboardHeight = 0;

    public UserComment(Context context, View view) {
        mContext = context;
        parent = view;
        mainList = parent.findViewById(R.id.itemComment);
        itemSend = parent.findViewById(R.id.itemSend);
        sendIcon = parent.findViewById(R.id.send_icon);
        sendVal = parent.findViewById(R.id.send_val);
        sendAt = parent.findViewById(R.id.send_at);
        sendBq = parent.findViewById(R.id.send_bq);
        sendSubmit = parent.findViewById(R.id.send_submit);
        sendIcon.setOnClickListener(this);
        sendAt.setOnClickListener(this);
        sendBq.setOnClickListener(this);
        sendSubmit.setOnClickListener(this);
        AppInit();
    }

    @Override
    public void onClick(View view) {
        if (view == sendIcon) {
            Common.showToast("拍视频");
        } else if (view == sendAt) {
            Common.showToast("艾特");
        } else if (view == sendBq) {
            Common.showToast("表情");
        } else if (view == sendSubmit) {
            // 发布评论
            final String getVal = sendVal.getText().toString();
            if (getVal.length() > 0) {
                Common.showToast("发布成功");
                Common.hideInput(mContext);
                sendVal.setText("");
                listData.add(0, new ItemBean(getVal));
                listAdapter.notifyItemInserted(0);
                mainList.scrollToPosition(0);
            }
        }
    }

    private void AppInit() {
        listAdapter = new ListAdapter(mContext, R.layout.comment_item, listData);
        mainList.setLayoutManager(new LinearLayoutManager(mContext));
        mainList.setAdapter(listAdapter);

        sendVal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() > 0) {
                    sendSubmit.setVisibility(View.VISIBLE);
                } else {
                    sendSubmit.setVisibility(View.GONE);
                }
            }
        });

        parent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                parent.getWindowVisibleDisplayFrame(rect);
                int getHeight = parent.getHeight() - rect.bottom;
                if (keyboardHeight != getHeight) {
                    keyboardHeight = getHeight;
                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) itemSend.getLayoutParams();
                    lp.bottomMargin = getHeight;
                    itemSend.setLayoutParams(lp);
                }
            }
        });
    }

    public void setData() {
        for (int i = 1; i < Config.CommentList.size() + 1; i++) {
            Config.CommentBean item = Config.CommentList.get(i);
            listData.add(new ItemBean(item.user.icon, item.user.name, item.text, item.time, item.favCount));
        }
        Collections.shuffle(listData);
        listAdapter.notifyDataSetChanged();
    }

    private class ItemBean {
        int src = -1;
        String icon;
        String name;
        String text;
        String time;
        int dig;
        boolean isDig = false;

        private ItemBean(String i, String n, String s, String t, int d) {
            icon = i;
            name = n;
            text = s;
            time = t;
            dig = d;
        }

        private ItemBean(String t) {
            src = R.mipmap.icon;
            name = "我";
            text = t;
            time = "刚刚";
            dig = 0;
        }

        private String getDigParse() {
            return Common.ParseNum(dig);
        }
    }

    private class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {
        private int layout;
        private LayoutInflater inflater;
        private List<ItemBean> list;


        private ListAdapter(Context context, int resource, List<ItemBean> objects) {
            layout = resource;
            inflater = LayoutInflater.from(context);
            list = objects;
        }

        @NonNull
        @Override
        public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View addView = inflater.inflate(layout, parent, false);
            return new ListViewHolder(addView);
        }

        @Override
        public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
            ItemBean item = list.get(position);
            if (item.src != -1) {
                // 新增的评论使用默认头像
                holder.itemIcon.setImageResource(item.src);
            } else {
                // 加载评论用户头像
                Common.setHttpImage(holder.itemIcon, item.icon, R.mipmap.icon_user);
            }
            holder.itemTitle.setText(item.name); // 用户名称
            holder.itemText.setText(item.text); // 评论内容
            holder.itemTime.setText(item.time); // 评论时间
            // 评论点赞
            holder.itemDigIcon.setImageResource(item.isDig ? R.mipmap.icon_dig_cur : R.mipmap.icon_dig_def);
            holder.itemDigNum.setVisibility(item.dig == 0 ? View.GONE : View.VISIBLE);
            holder.itemDigNum.setTextColor(Color.parseColor(item.isDig ? "#FC2E5D" : "#888888"));
            holder.itemDigNum.setText(item.getDigParse());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            ImageView itemIcon;
            TextView itemTitle;
            TextView itemText;
            TextView itemTime;
            View itemDigBox;
            ImageView itemDigIcon;
            TextView itemDigNum;

            private ListViewHolder(View view) {
                super(view);
                itemIcon = view.findViewById(R.id.item_icon);
                itemTitle = view.findViewById(R.id.item_title);
                itemText = view.findViewById(R.id.item_text);
                itemTime = view.findViewById(R.id.item_time);
                itemDigBox = view.findViewById(R.id.item_dig_box);
                itemDigIcon = view.findViewById(R.id.item_dig_icon);
                itemDigNum = view.findViewById(R.id.item_dig_num);
                itemIcon.setOnClickListener(this);
                itemTitle.setOnClickListener(this);
                itemDigBox.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();
                ItemBean item = list.get(position);
                if (view == itemIcon || view == itemTitle) {
                    Common.showToast(item.name);
                } else if (view == itemDigBox) {
                    // 点击评论点赞
                    item.isDig = !item.isDig;
                    item.dig = item.isDig ? item.dig + 1 : item.dig - 1;
                    itemDigIcon.setImageResource(item.isDig ? R.mipmap.icon_dig_cur : R.mipmap.icon_dig_def);
                    itemDigNum.setVisibility(item.dig == 0 ? View.GONE : View.VISIBLE);
                    itemDigNum.setTextColor(Color.parseColor(item.isDig ? "#FC2E5D" : "#888888"));
                    itemDigNum.setText(item.getDigParse());
                }
            }

        }

    }

}