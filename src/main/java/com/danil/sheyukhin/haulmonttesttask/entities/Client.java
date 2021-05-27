/*
 * Copyright (c) 2021.
 * Danil Sheyukhin
 * danya.bob@gmail.com
 */

package com.danil.sheyukhin.haulmonttesttask.entities;

public class Client {

    private Integer id;
    private Integer bankId;

    private String name;
    private String phone;
    private String email;
    private String passport;

    public Client(Integer id, String name, String phone, String email, String passport, Integer bankId) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.passport = passport;
        this.bankId = bankId;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassport() {
        return passport;
    }
    public void setPassport(String passport) {
        this.passport = passport;
    }

    public Integer getBankId() {
        return bankId;
    }
    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    @Override
    public String toString() {
        return name + ", " + phone + ", " + email + ", " + passport;
    }
}
