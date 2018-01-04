package com.pochih.mokapos;

import android.app.Application;
import android.util.Log;

import com.pochih.mokapos.entity.CartItem;
import com.pochih.mokapos.entity.Discount;
import com.pochih.mokapos.entity.Item;
import com.pochih.mokapos.http.service.IHttpService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * Created by A-Po on 2017/12/29.
 */

public class AppApplication extends Application {
    private static final String TAG = AppApplication.class.getSimpleName();

    public static AppApplication instance;
    public static IHttpService httpService;
    private static List<CartItem> cartItems;
    private List<Discount> discounts;
    private List<Item> items;

    private static final String DEFAULT_BASE_URL = "https://jsonplaceholder.typicode.com";
    private static final int CARTITEM_ID_SUBTOTAL = 181;

    @Override
    public void onCreate() {
        try {
            //region Step 1. Initial settings
            super.onCreate();
            Timber.plant(new Timber.DebugTree());
            instance = this;
            cartItems = new ArrayList<>();
            discounts = new ArrayList<>();
            discounts.add(new Discount("Discount A", 0.0));
            discounts.add(new Discount("Discount B", 10.0));
            discounts.add(new Discount("Discount C", 35.5));
            discounts.add(new Discount("Discount D", 50.0));
            discounts.add(new Discount("Discount E", 100.0));
            //endregion

            //region Step 2. Initial Http call setting
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(DEFAULT_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            httpService = retrofit.create(IHttpService.class);
            //endregion

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public List<Discount> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<Discount> discounts) {
        this.discounts = discounts;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public static List<CartItem> getCartItems() {
        return cartItems;
    }

    public CartItem getCartItem(Item item, int discount) {
        CartItem result = new CartItem(item);
        if (cartItems != null && cartItems.size() != 0) {
            for (int i = 0; i < cartItems.size(); i++) {
                if (cartItems.get(i).getId() == item.getId() && cartItems.get(i).getDiscount() == discount) {
                    result = cartItems.get(i);
                    break;
                }
            }
        }
        return result;
    }

    // For AllItemFragment: isUpdating = false;  For ShoppingFragment: isUpdating = false;
    public static void setCartItem(CartItem cartItem, boolean isUpdating) {
        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }

        int index = -1;
        for (int i = 0; i < cartItems.size(); i++) {
            if (cartItems.get(i).getId() == cartItem.getId() && cartItems.get(i).getDiscount() == cartItem.getDiscount()) {
                index = i;
                break;
            }
        }

        if (index > -1) {
            if (!isUpdating) {
                CartItem oriCartItem = cartItems.get(index);
                cartItem.setQuantity(cartItem.getQuantity() + oriCartItem.getQuantity());
            }
            cartItems.set(index, cartItem);
        } else {
            cartItems.add(cartItem);
        }
    }

    public static void clearAllCartItem() {
        cartItems.clear();
    }
}