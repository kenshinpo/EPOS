package com.pochih.mokapos.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pochih.mokapos.R;
import com.pochih.mokapos.entity.Discount;

import java.util.List;

import timber.log.Timber;

/**
 * Created by A-Po on 2018/01/04.
 */

public class DiscountAdapter extends RecyclerView.Adapter<DiscountAdapter.ViewHolder> {
    private Context context;
    private List<Discount> discounts;

    public DiscountAdapter(Context context, List<Discount> discounts) {
        this.context = context;
        this.discounts = discounts;
    }

    @Override
    public DiscountAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_discount_adapter, parent, false);
        DiscountAdapter.ViewHolder vh = new DiscountAdapter.ViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(DiscountAdapter.ViewHolder holder, final int position) {
        try {
            final Discount discount = discounts.get(position);
            holder.itemView.setTag(position);
            //region Data binding
            holder.tvName.setText(discount.getTitle());
            holder.tvPercentage.setText(discount.getPercentage() + " %");
            //endregion
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    @Override
    public int getItemCount() {
        return discounts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivThumb;
        public TextView tvName, tvPercentage;

        public ViewHolder(View itemView) {
            super(itemView);
            ivThumb = itemView.findViewById(R.id.ivThumb);
            tvName = itemView.findViewById(R.id.tvName);
            tvPercentage = itemView.findViewById(R.id.tvPercentage);
        }
    }
}
