package com.example.ecommerce.models;

import java.io.Serializable;

public class PaymentData implements Serializable {
    private int totalBill;

    public PaymentData(int totalBill) {
        this.totalBill = totalBill;
    }

    public int getTotalBill() {
        return totalBill;
    }

    public void setTotalBill(int totalBill) {
        this.totalBill = totalBill;
    }
}
