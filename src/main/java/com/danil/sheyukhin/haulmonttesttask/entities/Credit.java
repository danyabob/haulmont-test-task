package com.danil.sheyukhin.haulmonttesttask.entities;

public class Credit implements Entity {

    private Integer id = null;
    private Integer bankId;
    private int limit;
    private int percentage;

    public Credit(Integer id, Integer bankId, int limit, int percentage) {
        this.id = id;
        this.bankId = bankId;
        this.limit = limit;
        this.percentage = percentage;
    }

    @Override
    public Integer getId() {
        return id;
    }
    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBankId() {
        return bankId;
    }
    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    @Override
    public String toString() {
        return "Credit{" +
                "id=" + id +
                ", bankId=" + bankId +
                ", limit=" + limit +
                ", percentage=" + percentage +
                '}';
    }
}
