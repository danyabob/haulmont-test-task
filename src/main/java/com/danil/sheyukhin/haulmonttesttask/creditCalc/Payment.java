package com.danil.sheyukhin.haulmonttesttask.creditCalc;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Payment {
    private LocalDate paymentDate;
    private double paymentSum;
    private double bodySum;
    private double percentageSum;

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
            LocalDate date = LocalDate.now().plusMonths(nextMonth++);
            double percentageSum = summa * percentage / 100 * date.lengthOfMonth() / date.lengthOfYear();
            double paymentSum = bodySum + percentageSum;
            summa -= bodySum;
            Payment payment = new Payment(
                                           date,
                                Math.ceil(paymentSum * scale) / scale,
                                   Math.ceil(bodySum * scale) / scale,
                    Math.ceil(percentageSum * scale) / scale);
            payments.add(payment);
        }
        return payments;
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

    public static void main(String[] args) {
        System.out.println(getPayments(500000, 36, 20));
    }
}