package com.pochih.mokapos.entity;

/**
 * Created by A-Po on 2018/01/03.
 */

public class CartItem extends Item {
    public int quantity;
    public double discount;

    public CartItem(Item item) {
        setId(item.getId());
        setAlbumId(item.getAlbumId());
        setTitle(item.getTitle());
        setUrl(item.getUrl());
        setThumbnailUrl(item.getThumbnailUrl());
        setPrice(item.getPrice());
        setQuantity(1);
        setDiscount(0);
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
