package com.inkhjw.personalcommunity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.inkhjw.personalcommunity.R;
import com.inkhjw.personalcommunity.bean.ShopListBean;
import com.inkhjw.personalcommunity.framework.imageloader.GlideImageLoader;

import java.util.List;

public class IndianaListAdapter extends RecyclerView.Adapter<IndianaListAdapter.BindViewHolder> {
    private Context context;
    private List<ShopListBean> data;
    private GlideImageLoader imageLoader;

    public IndianaListAdapter(Context context, List<ShopListBean> data) {
        this.context = context;
        this.data = data;
        this.imageLoader = new GlideImageLoader();
    }

    @Override
    public BindViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).
                inflate(R.layout.fragment_indiana_list_item, parent, false);

        BindViewHolder holder = new BindViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(BindViewHolder holder, int position) {
        ShopListBean listBean = data.get(position);
        imageLoader.displayImage(context, listBean.getShopImage(), holder.indiana_item_shop_image);
        holder.indiana_item_shop_describe.setText(listBean.getShopDescribe());
        holder.indiana_item_announced_progress.setText(100 * (listBean.getAllNeed() - listBean.getNextNeed()) / listBean.getAllNeed() + "%");
        holder.indiana_item_shop_progress.setProgress(100 * (listBean.getAllNeed() - listBean.getNextNeed()) / listBean.getAllNeed());
        holder.indiana_item_shop_addlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class BindViewHolder extends RecyclerView.ViewHolder {
        ImageView indiana_item_shop_image;
        TextView indiana_item_shop_describe;
        TextView indiana_item_announced_progress;
        ProgressBar indiana_item_shop_progress;
        Button indiana_item_shop_addlist;


        public BindViewHolder(View view) {
            super(view);
            indiana_item_shop_image = (ImageView) view.findViewById(R.id.indiana_item_shop_image);
            indiana_item_shop_describe = (TextView) view.findViewById(R.id.indiana_item_shop_describe);
            indiana_item_announced_progress = (TextView) view.findViewById(R.id.indiana_item_announced_progress);
            indiana_item_shop_progress = (ProgressBar) view.findViewById(R.id.indiana_item_shop_progress);
            indiana_item_shop_addlist = (Button) view.findViewById(R.id.indiana_item_shop_addlist);
        }
    }
}
