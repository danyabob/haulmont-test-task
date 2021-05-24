package com.danil.sheyukhin.haulmonttesttask.entities;

public class Client implements Entity {

    private Integer id;
    private String name;
    private String phone;
    private String email;
    private String passport;
    private Integer bankId;

    public Client(String name, String phone, String email, String passport) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.passport = passport;
        this.bankId = bankId;
    }

    @Override
    public Integer getId() {
        return id;
    }
    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() { return name; }
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
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", passport='" + passport + '\'' +
                ", bankId=" + bankId +
                '}';
    }
}
