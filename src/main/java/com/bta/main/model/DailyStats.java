package com.bta.main.model;


import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class DailyStats {
    private String date;
    private int balance;
    private int numberOfTransactions;
    private int minimum;
    private int maximum;
    private int income;
    private int outgoing;

    public DailyStats() {
    }

    /*
     * Constructs statistics about one day of the MT940 file.
     * @param list A list of transactions from the same day of the same MT940 file to compress.
     */
    private DailyStats(List<Transaction> list) {
        date = list.get(0).getDate();
        balance = list.get(list.size() - 1).getAfterTransaction();
        numberOfTransactions = list.size();
        minimum = list.get(0).getAfterTransaction() - list.get(0).getAmount();
        maximum = list.get(0).getAfterTransaction() - list.get(0).getAmount();
        income = 0;
        outgoing = 0;
        for (Transaction tr : list) {
            if (tr.getAfterTransaction() < minimum) {
                minimum = tr.getAfterTransaction();
            }
            if (tr.getAfterTransaction() > maximum) {
                maximum = tr.getAfterTransaction();
            }
            if (tr.getAmount() > 0) {
                income = income + tr.getAmount();
            } else {
                outgoing = outgoing - tr.getAmount();
            }
        }
    }

    /*
     * Creates a list of daily statistics from a list of MT940 file transactions.
     * @param trans A list of transactions from a MT940 file to compress.
     * @return List of daily statistics of the MT940 file.
     */
    public static List<DailyStats> createStats(List<Transaction> trans) {
        List<DailyStats> list = new ArrayList<>();
        List<Transaction> add = new ArrayList<>();
        String date = trans.get(0).getDate();
        for (Transaction tr : trans) {
            if (!tr.getDate().equals(date)) {
                list.add(new DailyStats(add));
                add.clear();
                date = tr.getDate();
            }
            add.add(tr);
        }
        list.add(new DailyStats(add));
        return list;
    }


    // GETTERS AND SETTERS

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getNumberOfTransactions() {
        return numberOfTransactions;
    }

    public void setNumberOfTransactions(int numberOfTransactions) {
        this.numberOfTransactions = numberOfTransactions;
    }

    public int getMinimum() {
        return minimum;
    }

    public void setMinimum(int minimum) {
        this.minimum = minimum;
    }

    public int getMaximum() {
        return maximum;
    }

    public void setmaximum(int maximum) {
        this.maximum = maximum;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public int getOutgoing() {
        return outgoing;
    }

    public void setOutgoing(int outgoing) {
        this.outgoing = outgoing;
    }


}
