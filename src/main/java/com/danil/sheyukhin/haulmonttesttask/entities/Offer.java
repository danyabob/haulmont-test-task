package com.danil.sheyukhin.haulmonttesttask.entities;

import com.danil.sheyukhin.haulmonttesttask.creditCalc.Payment;

import java.util.List;

public class Offer implements Entity {
    private Integer id;
    private Integer clientId;
    private Integer creditId;
    private int summa;
    private int duration;

    public Offer(Integer id, Integer clientId, Integer creditId, int summa, int duration) {
        this.id = id;
        this.clientId = clientId;
        this.creditId = creditId;
        this.summa = summa;
        this.duration = duration;
    }

    @Override
    public Integer getId() {
        return id;
    }
    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClientId() {
        return clientId;
    }
    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getCreditId() {
        return creditId;
    }
    public void setCreditId(Integer creditId) {
        this.creditId = creditId;
    }

    public int getSumma() {
        return summa;
    }
    public void setSumma(int summa) {
        this.summa = summa;
    }

    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<Payment> getPayments() {
        return Payment.getPayments(summa, duration, 20);
    }

    @Override
    public String toString() {
        return "Offer{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", creditId=" + creditId +
                ", summa=" + summa +
                ", duration=" + duration +
                '}';
    }
}
