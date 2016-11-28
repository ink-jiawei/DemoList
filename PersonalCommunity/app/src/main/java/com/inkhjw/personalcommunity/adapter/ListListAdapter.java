package com.inkhjw.personalcommunity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.inkhjw.personalcommunity.R;
import com.inkhjw.personalcommunity.bean.ShopListBean;
import com.inkhjw.personalcommunity.event.ListSelectedEvent;
import com.inkhjw.personalcommunity.framework.imageloader.GlideImageLoader;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class ListListAdapter extends RecyclerView.Adapter<ListListAdapter.BindViewHolder> {
    private Context context;
    private List<ShopListBean> data;
    private GlideImageLoader imageLoader;

    public ListListAdapter(Context context, List<ShopListBean> data) {
        this.context = context;
        this.data = data;
        this.imageLoader = new GlideImageLoader();
    }

    @Override
    public BindViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).
                inflate(R.layout.fragment_list_item, parent, false);

        BindViewHolder holder = new BindViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(BindViewHolder holder, int position) {
        ShopListBean listBean = data.get(position);

        holder.list_item_checked.setSelected(false);
        imageLoader.displayImage(context, listBean.getShopImage(), holder.list_item_shop_image);
        holder.list_item_shop_describe.setText(listBean.getShopDescribe());
        holder.list_item_all_need.setText(listBean.getAllNeed() + "");
        holder.list_item_next_need.setText(listBean.getNextNeed() + "");
        holder.list_item_shop_num.setText("1");

        holder.list_item_checked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setSelected(!view.isSelected());
                EventBus.getDefault().post(new ListSelectedEvent());
            }
        });

        holder.list_item_shop_less.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.list_item_shop_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void showChecked() {

    }

    public void hideChecked() {

    }

    public class BindViewHolder extends RecyclerView.ViewHolder {

        public TextView list_item_checked;
        public ImageView list_item_shop_image;
        public TextView list_item_shop_describe;
        public TextView list_item_all_need;
        public TextView list_item_next_need;
        public ImageView list_item_shop_less;
        public TextView list_item_shop_num;
        public ImageView list_item_shop_add;


        public BindViewHolder(View view) {
            super(view);
            list_item_checked = (TextView) view.findViewById(R.id.list_item_checked);
            list_item_shop_image = (ImageView) view.findViewById(R.id.list_item_shop_image);
            list_item_shop_describe = (TextView) view.findViewById(R.id.list_item_shop_describe);
            list_item_all_need = (TextView) view.findViewById(R.id.list_item_all_need);
            list_item_next_need = (TextView) view.findViewById(R.id.list_item_next_need);
            list_item_shop_less = (ImageView) view.findViewById(R.id.list_item_shop_less);
            list_item_shop_num = (TextView) view.findViewById(R.id.list_item_shop_num);
            list_item_shop_add = (ImageView) view.findViewById(R.id.list_item_shop_add);
        }
    }
}
