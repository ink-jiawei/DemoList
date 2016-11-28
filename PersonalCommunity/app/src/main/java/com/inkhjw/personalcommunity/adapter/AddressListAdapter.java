package com.inkhjw.personalcommunity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inkhjw.personalcommunity.R;
import com.inkhjw.personalcommunity.bean.AddressListBean;
import com.inkhjw.personalcommunity.event.AddressEdtEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.BindViewHolder> {
    private Context context;
    private List<AddressListBean> data;

    private OnItemClickListener itemClickListener;

    public AddressListAdapter(Context context, List<AddressListBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public BindViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).
                inflate(R.layout.activity_personal_address_list_item, parent, false);

        BindViewHolder holder = new BindViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final BindViewHolder holder, final int position) {
        AddressListBean listBean = data.get(position);

        holder.address_list_item_name.setText(listBean.getName());
        holder.address_list_item_phone.setText(listBean.getPhone());
        holder.address_list_item_address.setText(listBean.getProvince() + listBean.getCity() + listBean.getQu() + listBean.getDetailAddress());
        holder.address_list_item_set_default.setSelected(listBean.isDefault());
        holder.address_list_item_is_set_default.setText(listBean.isDefault() ? R.string.user_presonal_address_edt_default : R.string.user_presonal_address_edt_set_default);

//        holder.address_list_item_set_default.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                holder.address_list_item_set_default.setSelected(!holder.address_list_item_set_default.isSelected());
//            }
//        });
        holder.address_list_item_edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.address_list_item_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new AddressEdtEvent(3, position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class BindViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView address_list_item_name;
        TextView address_list_item_phone;
        TextView address_list_item_address;
        ImageView address_list_item_set_default;
        TextView address_list_item_is_set_default;
        LinearLayout address_list_item_edt;
        LinearLayout address_list_item_del;

        BindViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            address_list_item_name = (TextView) view.findViewById(R.id.address_list_item_name);
            address_list_item_phone = (TextView) view.findViewById(R.id.address_list_item_phone);
            address_list_item_address = (TextView) view.findViewById(R.id.address_list_item_address);
            address_list_item_set_default = (ImageView) view.findViewById(R.id.address_list_item_set_default);
            address_list_item_is_set_default = (TextView) view.findViewById(R.id.address_list_item_is_set_default);
            address_list_item_edt = (LinearLayout) view.findViewById(R.id.address_list_item_edt);
            address_list_item_del = (LinearLayout) view.findViewById(R.id.address_list_item_del);
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
