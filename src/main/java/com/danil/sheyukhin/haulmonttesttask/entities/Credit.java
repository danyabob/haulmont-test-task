/*
 * Copyright (c) 2021.
 * Danil Sheyukhin
 * danya.bob@gmail.com
 */

package com.danil.sheyukhin.haulmonttesttask.entities;

public class Credit {

    private Integer id;
    private Integer bankId;

    private String name;

    private int limit;
    private int percentage;

    public Credit(Integer id, Integer bankId, String name, int limit, int percentage) {
        this.id = id;
        this.bankId = bankId;
        this.name = name;
        this.limit = limit;
        this.percentage = percentage;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBankId() {
        return bankId;
    }
    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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
        return name + ", " + limit + " руб, " + percentage + " %";
    }
}
