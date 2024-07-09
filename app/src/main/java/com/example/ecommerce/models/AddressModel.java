package com.example.ecommerce.models;

public class AddressModel {

    String userAddress;

    boolean isSelected;
    public String getUserAddress() {
        return userAddress;
    }

    public AddressModel(String userAddress, boolean isSelected) {
        this.userAddress = userAddress;
        this.isSelected = isSelected;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public AddressModel() {
    }
}
