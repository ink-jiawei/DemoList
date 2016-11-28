package com.inkhjw.personalcommunity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.inkhjw.personalcommunity.R;
import com.inkhjw.personalcommunity.bean.AnnouncedShopBean;
import com.inkhjw.personalcommunity.framework.imageloader.GlideImageLoader;
import com.inkhjw.personalcommunity.widget.TimeCountDownView;

import java.util.List;

public class AnnouncedListAdapter extends RecyclerView.Adapter<AnnouncedListAdapter.BindViewHolder> {
    private Context context;
    private List<AnnouncedShopBean> data;
    private GlideImageLoader imageLoader;

    public AnnouncedListAdapter(Context context, List<AnnouncedShopBean> data) {
        this.context = context;
        this.data = data;
        this.imageLoader = new GlideImageLoader();
    }

    @Override
    public BindViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).
                inflate(R.layout.fragment_announced_list_item, parent, false);

        BindViewHolder holder = new BindViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(BindViewHolder holder, int position) {
        AnnouncedShopBean shopBean = data.get(position);
        imageLoader.displayImage(context, shopBean.getShopImage(), holder.announced_item_shop_image);
        holder.announced_item_shop_describe.setText(shopBean.getShopDescribe());
        holder.announced_item_shop_qihao.setText(shopBean.getQihao());
        holder.announced_item_shop_time.setTimeCoundDown(shopBean.getAnnouncedTime());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class BindViewHolder extends RecyclerView.ViewHolder {
        ImageView announced_item_shop_image;
        TextView announced_item_shop_describe;
        TextView announced_item_shop_qihao;
        TimeCountDownView announced_item_shop_time;


        public BindViewHolder(View view) {
            super(view);
            announced_item_shop_image = (ImageView) view.findViewById(R.id.announced_item_shop_image);
            announced_item_shop_describe = (TextView) view.findViewById(R.id.announced_item_shop_describe);
            announced_item_shop_qihao = (TextView) view.findViewById(R.id.announced_item_shop_qihao);
            announced_item_shop_time = (TimeCountDownView) view.findViewById(R.id.announced_item_shop_time);
        }
    }
}
