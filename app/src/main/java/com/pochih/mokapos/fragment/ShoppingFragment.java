package com.pochih.mokapos.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pochih.mokapos.AppApplication;
import com.pochih.mokapos.R;
import com.pochih.mokapos.adapter.CartItemAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class ShoppingFragment extends Fragment {

    @BindView(R.id.rvCart)
    RecyclerView rvCart;

    @BindView(R.id.llSubtotal)
    LinearLayout llSubtotal;

    @BindView(R.id.llDiscount)
    LinearLayout llDiscount;

    @BindView(R.id.tvSubtotal)
    TextView tvSubtotal;

    @BindView(R.id.tvDiscount)
    TextView tvDiscount;

    @BindView(R.id.tvNoItems)
    TextView tvNoItems;

    @BindView(R.id.btnCharge)
    Button btnCharge;

    private CartItemAdapter adapter;
    private ReceiveBroadCast receiveBroadCast;

    public ShoppingFragment() {
    }


    public static ShoppingFragment newInstance() {
        ShoppingFragment fragment = new ShoppingFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        receiveBroadCast = new ReceiveBroadCast();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ShoppingFragment.class.getPackage().getName());
        context.registerReceiver(receiveBroadCast, filter);
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_shopping, container, false);
        try {
            ButterKnife.bind(this, view);
            //region Setup RecyclerView UI
            rvCart.setLayoutManager(new LinearLayoutManager(getContext()));
            //endregion
        } catch (Exception e) {
            Timber.e(e);
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter = new CartItemAdapter(getContext(), AppApplication.getCartItems());
        rvCart.setAdapter(adapter);
        updateCart();
    }

    @Override
    public void onDestroyView() {
        getActivity().unregisterReceiver(receiveBroadCast);
        super.onDestroyView();
    }

    class ReceiveBroadCast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateCart();
        }
    }


    @OnClick(R.id.btnClearSale)
    void clearAllCartItem() {
        try {
            AppApplication.clearAllCartItem();
            updateCart();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateCart() {
        if (AppApplication.getCartItems() == null || AppApplication.getCartItems().size() == 0) {
            rvCart.setVisibility(View.GONE);
            llSubtotal.setVisibility(View.GONE);
            llDiscount.setVisibility(View.GONE);
            tvNoItems.setVisibility(View.VISIBLE);
            btnCharge.setText("Charge $ 0");
        } else {
            rvCart.setVisibility(View.VISIBLE);
            llSubtotal.setVisibility(View.VISIBLE);
            llDiscount.setVisibility(View.VISIBLE);
            tvNoItems.setVisibility(View.GONE);

            //region Calc
            int subtotal = 0;
            int dicount = 0;
            for (int i = 0; i < AppApplication.getCartItems().size(); i++) {
                int itemsPrice = AppApplication.getCartItems().get(i).quantity * AppApplication.getCartItems().get(i).getPrice();
                subtotal += itemsPrice;
                dicount += itemsPrice * AppApplication.getCartItems().get(i).getDiscount() / 100;
            }

            tvSubtotal.setText("$ " + subtotal);
            tvDiscount.setText("($ " + dicount + ")");
            btnCharge.setText("Charge $ " + (subtotal - dicount));
            //endregion
            adapter.notifyDataSetChanged();
        }
    }
}
