package com.pochih.mokapos.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pochih.mokapos.R;
import com.pochih.mokapos.dialog.CartItemDialog;
import com.pochih.mokapos.entity.CartItem;

import java.util.List;

import timber.log.Timber;

/**
 * Created by A-Po on 2018/01/03.
 */

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.ViewHolder> {

    private Context context;
    private List<CartItem> cartItems;


    public CartItemAdapter(Context context, List<CartItem> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
    }

    @Override
    public CartItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cartitem_adapter, parent, false);
        CartItemAdapter.ViewHolder vh = new CartItemAdapter.ViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(CartItemAdapter.ViewHolder holder, final int position) {
        try {
            final CartItem cartItem = cartItems.get(position);
            holder.itemView.setTag(position);

            //region Data binding
            holder.tvName.setText(cartItem.getTitle());
            holder.tvDiscount.setText("- " + String.valueOf(cartItem.getDiscount()) + "%");
            holder.tvQuantity.setText("x" + String.valueOf(cartItem.getQuantity()));
            holder.tvPrice.setText("$" + cartItem.getPrice() * cartItem.getQuantity());
            //endregion

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CartItem i = cartItems.get(position);
                    new CartItemDialog(context, i, true).show();

                }
            });
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName, tvDiscount, tvQuantity, tvPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvDiscount = itemView.findViewById(R.id.tvDiscount);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }
    }
}
