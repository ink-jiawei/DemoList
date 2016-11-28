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

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.BindViewHolder> {
    private Context context;
    private List<ShopListBean> data;
    private GlideImageLoader imageLoader;

    public SearchListAdapter(Context context, List<ShopListBean> data) {
        this.context = context;
        this.data = data;
        this.imageLoader = new GlideImageLoader();
    }

    @Override
    public BindViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).
                inflate(R.layout.activity_search_list_item, parent, false);

        BindViewHolder holder = new BindViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(BindViewHolder holder, int position) {
        ShopListBean listBean = data.get(position);

        holder.indiana_search_shop_image_type.setImageResource(listBean.getType() ? R.drawable.search_tenyuan : R.drawable.search_baiyuan);
        imageLoader.displayImage(context, listBean.getShopImage(), holder.indiana_search_shop_image);
        holder.indiana_search_shop_describe.setText(listBean.getShopDescribe());
        holder.indiana_search_shop_progress.setProgress(100 * (listBean.getAllNeed() - listBean.getNextNeed()) / listBean.getAllNeed());
        holder.indiana_search_shop_all_need.setText("总需" + listBean.getAllNeed());
        holder.indiana_search_shop_next_need.setText(listBean.getNextNeed() + "");
        holder.indiana_search_addlist.setOnClickListener(new View.OnClickListener() {
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
        private ImageView indiana_search_shop_image_type;
        public ImageView indiana_search_shop_image;
        public TextView indiana_search_shop_describe;
        public ProgressBar indiana_search_shop_progress;
        public TextView indiana_search_shop_all_need;
        public TextView indiana_search_shop_next_need;
        public Button indiana_search_addlist;


        public BindViewHolder(View view) {
            super(view);
            indiana_search_shop_image_type = (ImageView) view.findViewById(R.id.indiana_search_shop_image_type);
            indiana_search_shop_image = (ImageView) view.findViewById(R.id.indiana_search_shop_image);
            indiana_search_shop_describe = (TextView) view.findViewById(R.id.indiana_search_shop_describe);
            indiana_search_shop_progress = (ProgressBar) view.findViewById(R.id.indiana_search_shop_progress);
            indiana_search_shop_all_need = (TextView) view.findViewById(R.id.indiana_search_shop_all_need);
            indiana_search_shop_next_need = (TextView) view.findViewById(R.id.indiana_search_shop_next_need);
            indiana_search_addlist = (Button) view.findViewById(R.id.indiana_search_addlist);
        }
    }
}
