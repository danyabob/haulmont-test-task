package com.danil.sheyukhin.haulmonttesttask.entities;

import java.util.Date;

public class Offer implements Entity{

    private Long id = null;
    private Client client;
    private Credit credit;
    private int sumOfCredit;
    private Date dateOfNextPayment;
    private int sumOfNextPayment;
    private int sumOfBodyOfNextPayment;
    private int sumOfPercentOfNextPayment;

    public Offer(Client client, Credit credit, int sumOfCredit, Date dateOfNextPayment, int sumOfNextPayment, int sumOfBodyOfNextPayment, int sumOfPercentOfNextPayment) {
        this.client = client;
        this.credit = credit;
        this.sumOfCredit = sumOfCredit;
        this.dateOfNextPayment = dateOfNextPayment;
        this.sumOfNextPayment = sumOfNextPayment;
        this.sumOfBodyOfNextPayment = sumOfBodyOfNextPayment;
        this.sumOfPercentOfNextPayment = sumOfPercentOfNextPayment;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Credit getCredit() {
        return credit;
    }

    public void setCredit(Credit credit) {
        this.credit = credit;
    }

    public int getSumOfCredit() {
        return sumOfCredit;
    }

    public void setSumOfCredit(int sumOfCredit) {
        this.sumOfCredit = sumOfCredit;
    }

    public Date getDateOfNextPayment() {
        return dateOfNextPayment;
    }

    public void setDateOfNextPayment(Date dateOfNextPayment) {
        this.dateOfNextPayment = dateOfNextPayment;
    }

    public int getSumOfNextPayment() {
        return sumOfNextPayment;
    }

    public void setSumOfNextPayment(int sumOfNextPayment) {
        this.sumOfNextPayment = sumOfNextPayment;
    }

    public int getSumOfBodyOfNextPayment() {
        return sumOfBodyOfNextPayment;
    }

    public void setSumOfBodyOfNextPayment(int sumOfBodyOfNextPayment) {
        this.sumOfBodyOfNextPayment = sumOfBodyOfNextPayment;
    }

    public int getSumOfPercentOfNextPayment() {
        return sumOfPercentOfNextPayment;
    }

    public void setSumOfPercentOfNextPayment(int sumOfPercentOfNextPayment) {
        this.sumOfPercentOfNextPayment = sumOfPercentOfNextPayment;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "client=" + client +
                ", credit=" + credit +
                ", sumOfCredit=" + sumOfCredit +
                ", dateOfNextPayment=" + dateOfNextPayment +
                ", sumOfNextPayment=" + sumOfNextPayment +
                ", sumOfBodyOfNextPayment=" + sumOfBodyOfNextPayment +
                ", sumOfPercentOfNextPayment=" + sumOfPercentOfNextPayment +
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
