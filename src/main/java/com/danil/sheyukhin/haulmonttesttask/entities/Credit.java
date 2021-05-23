package com.danil.sheyukhin.haulmonttesttask.entities;

public class Credit implements Entity{

    private Long id = null;
    private String name;
    private int limit;
    private int percent;

    public Credit(String name, int limit, int percent) {
        this.name = name;
        this.limit = limit;
        this.percent = percent;
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

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    @Override
    public String toString() {
        return "Credit{" +
                "name='" + name + '\'' +
                ", limit=" + limit +
                ", percent=" + percent +
                '}';
    }

    @Override
    public Integer getId() {
        return null;
    }

    @Override
    public void setId(Integer id) {

    }
}
