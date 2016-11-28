package com.inkhjw.personalcommunity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inkhjw.personalcommunity.R;

import java.util.List;

public class HistroyListAdapter extends RecyclerView.Adapter<HistroyListAdapter.BindViewHolder> {
    private Context context;
    private List<String> data;

    private OnItemClickListener itemClickListener;

    public HistroyListAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public BindViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).
                inflate(R.layout.activity_search_histroy_item, parent, false);

        BindViewHolder holder = new BindViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(BindViewHolder holder, int position) {
        holder.activity_search_histroy_text.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class BindViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView activity_search_histroy_text;

        BindViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            activity_search_histroy_text = (TextView) view.findViewById(R.id.activity_search_histroy_text);
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(getLayoutPosition());
            }
        }
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
