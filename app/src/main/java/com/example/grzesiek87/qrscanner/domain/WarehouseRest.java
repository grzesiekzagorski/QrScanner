package com.example.grzesiek87.qrscanner.domain;

public class WarehouseRest {
    private int id;
    private int delivery_number;
    private String nameOfMedicine;
    private int amount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDelivery_number() {
        return delivery_number;
    }

    public void setDelivery_number(int delivery_number) {
        this.delivery_number = delivery_number;
    }

    public String getNameOfMedicine() {
        return nameOfMedicine;
    }

    public void setNameOfMedicine(String nameOfMedicine) {
        this.nameOfMedicine = nameOfMedicine;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "| Id:" + id +
                " | Lek:" + nameOfMedicine +
                " | Ilość:" + amount;
    }
}
