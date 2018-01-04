package com.pochih.mokapos.entity;

/**
 * Created by A-Po on 2018/01/04.
 */

public class Discount {
    private String title;
    private double percentage;

    public Discount(String title, double percentage) {
        this.title = title;
        this.percentage = percentage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}
