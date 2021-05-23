package com.danil.sheyukhin.haulmonttesttask.entities;

public class Bank implements Entity{

    private Integer id;
    private String name;

    public Bank(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public Integer getId() {
        return id;
    }
    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Bank{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
