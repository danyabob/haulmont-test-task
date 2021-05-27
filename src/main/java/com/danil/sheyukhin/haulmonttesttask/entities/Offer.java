package com.danil.sheyukhin.haulmonttesttask.entities;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Offer implements Entity {
    private Integer id;
    private Integer clientId;
    private Integer creditId;
    private int summa;
    private int duration;
    private String clientName;
    private String bankName;
    private String creditName;
    private double percentage;


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

    public List<Payment> getPayments(double percentage) {
        return Payment.getPayments(summa, duration, percentage);
    }

    public String getClientName() {
        return clientName;
    }
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getBankName() {
        return bankName;
    }
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCreditName() {
        return creditName;
    }
    public void setCreditName(String creditName) {
        this.creditName = creditName;
    }

    public double getPercentage() {
        return percentage;
    }
    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    @Override
    public String toString() {
        return "" + getSumma();
    }

    public static class Payment {
        private LocalDate paymentDate;
        private String paymentSumString;
        private String bodySumString;
        private String percentageSumString;
        private double paymentSum;
        private double bodySum;
        private double percentageSum;
        private DecimalFormat decimalFormat = new DecimalFormat("#.##");

        public Payment(LocalDate paymentDate, double paymentSum, double bodySum, double percentageSum) {
            this.paymentDate = paymentDate;
            this.paymentSum = paymentSum;
            this.bodySum = bodySum;
            this.percentageSum = percentageSum;
        }

        public static List<Payment> getPayments(int summa, int duration, double percentage) {
            List<Payment> payments = new ArrayList<Payment>();
            int nextMonth = 1;
            double scale = Math.pow(10, 2);
            double bodySum = summa / duration;
            for (int i=0; i < duration; i++) {
                LocalDate paymentDate = LocalDate.now().plusMonths(nextMonth++);
                int daysOfTheMonth = paymentDate.lengthOfMonth();
                int daysOfTheYear = paymentDate.lengthOfYear();
                double percentageSum = summa * percentage / 100 * daysOfTheMonth / daysOfTheYear;
                double paymentSum = bodySum + percentageSum;
                summa -= bodySum;
                Payment payment = new Payment(
                        paymentDate,
                        paymentSum,
                        bodySum,
                        percentageSum);
                payments.add(payment);
            }
            return payments;
        }

        public String getPaymentSumString() {
            return decimalFormat.format(paymentSum);
        }

        public void setPaymentSumString(String paymentSumString) {
            this.paymentSumString = paymentSumString;
        }

        public String getBodySumString() {
            return decimalFormat.format(bodySum);
        }

        public void setBodySumString(String bodySumString) {
            this.bodySumString = bodySumString;
        }

        public String getPercentageSumString() {
            return decimalFormat.format(percentageSum);
        }

        public void setPercentageSumString(String percentageSumString) {
            this.percentageSumString = percentageSumString;
        }

        public LocalDate getPaymentDate() {
            return paymentDate;
        }

        public double getPaymentSum() {
            return paymentSum;
        }

        public double getBodySum() {
            return bodySum;
        }

        public double getPercentageSum() {
            return percentageSum;
        }

        @Override
        public String toString() {
            return "Payment{" +
                    "paymentDate=" + paymentDate +
                    ", paymentSum=" + paymentSum +
                    ", bodySum=" + bodySum +
                    ", percentageSum=" + percentageSum +
                    '}';
        }
    }
}
