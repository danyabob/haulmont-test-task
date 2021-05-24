package com.danil.sheyukhin.haulmonttesttask.entities;

import java.time.LocalDate;

public class Offer implements Entity {
    private Integer id;
    private Integer clientId;
    private Integer creditId;
    private int creditSum;
    private PaymentGraphic paymentGraphic;

    public Offer(Integer id, Integer clientId, Integer creditId, int creditSum, LocalDate paymentDate, int payment, int bodySum, int percentageSum) {
        this.id = id;
        this.clientId = clientId;
        this.creditId = creditId;
        this.creditSum = creditSum;
        this.paymentGraphic = new PaymentGraphic(paymentDate, payment, bodySum, percentageSum);
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

    public int getCreditSum() {
        return creditSum;
    }
    public void setCreditSum(int creditSum) {
        this.creditSum = creditSum;
    }

    public PaymentGraphic getPaymentGraphic() {
        return paymentGraphic;
    }

    public void setPaymentGraphic(PaymentGraphic paymentGraphic) {
        this.paymentGraphic = paymentGraphic;
    }

    public static class PaymentGraphic {
        private LocalDate paymentDate;
        private int payment;
        private int bodySum;
        private int percentageSum;

        public PaymentGraphic(LocalDate paymentDate, int payment, int bodySum, int percentageSum) {
            this.paymentDate = paymentDate;
            this.payment = payment;
            this.bodySum = bodySum;
            this.percentageSum = percentageSum;
        }

        public LocalDate getPaymentDate() {
            return paymentDate;
        }

        public void setPaymentDate(LocalDate paymentDate) {
            this.paymentDate = paymentDate;
        }

        public int getPayment() {
            return payment;
        }

        public void setPayment(int payment) {
            this.payment = payment;
        }

        public int getBodySum() {
            return bodySum;
        }

        public void setBodySum(int bodySum) {
            this.bodySum = bodySum;
        }

        public int getPercentageSum() {
            return percentageSum;
        }

        public void setPercentageSum(int percentageSum) {
            this.percentageSum = percentageSum;
        }
    }
}
