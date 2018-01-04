package com.pochih.mokapos.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pochih.mokapos.R;
import com.pochih.mokapos.dialog.CartItemDialog;
import com.pochih.mokapos.entity.CartItem;
import com.pochih.mokapos.entity.Item;
import com.squareup.picasso.Picasso;

import java.util.List;

import timber.log.Timber;

/**
 * Created by A-Po on 2018/01/02.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private Context context;
    private List<Item> items;

    public ItemAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_item_adapter, parent, false);
        ViewHolder vh = new ViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        try {
            final Item item = items.get(position);
            holder.itemView.setTag(position);

            //region Data binding
            Picasso.with(context).load(item.getThumbnailUrl()).into(holder.ivThumb);
            holder.tvName.setText(item.getTitle());
            holder.tvPrice.setText("$" + item.getPrice());
            //endregion

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Item i = items.get(position);
                    // click event;
                    //CartItem ci = AppApplication.instance.getCartItem(i, 0);
                    CartItem ci = new CartItem(i);
                    new CartItemDialog(context, ci, false).show();

                }
            });
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivThumb;
        public TextView tvName, tvPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            ivThumb = itemView.findViewById(R.id.ivThumb);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }
    }
}
