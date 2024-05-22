package com.bta.main.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.prowidesoftware.swift.model.SwiftTagListBlock;
import com.prowidesoftware.swift.model.Tag;
import com.prowidesoftware.swift.model.field.Field61;
import com.prowidesoftware.swift.model.field.Field86;
import com.prowidesoftware.swift.model.mt.mt9xx.MT940;

@XmlRootElement
public class File {
    private String name;
    private String transactionReferenceNumber;
    private String accountID;
    private String statementNumber;

    private int openingBalance; // inc. c/d mark
    private String openingDate;
    private String currencyCode;
    private List<Transaction> transactions;
    private int closingBalance;
    private String closingDate;
    private int numberOfTransactions;

    @XmlTransient
    private boolean correct;

    public File() {
    }


    /*
     * Constructs a MT940 (bean) file from a given MT940 library file and a given filename.
     * @param mt940 The MT940 file to parse to the file.
     * @param filename The filename to use for the file.
     */
    public File(MT940 mt940, String filename) {
        try {
            this.transactions = new ArrayList<>();
            this.name = filename;
            this.transactionReferenceNumber = mt940.getField20().getReference();
            this.accountID = mt940.getField25().getAccount();
            this.statementNumber = mt940.getField28C().getStatementNumber();
            if (mt940.getField60F() == null) {
                //System.out.println(mt940.getField60M().getAmountAsNumber());
                this.openingBalance = (int) (mt940.getField60M().getAmountAsNumber().doubleValue() * 100); //TODO
                if (mt940.getField60M().getDCMark().equals("D")) {
                    this.openingBalance *= -1;
                }
                this.currencyCode = mt940.getField60M().getCurrency();
                this.openingDate = mt940.getField60M().getDate();
            } else {
                this.openingBalance = (int) (mt940.getField60F().getAmountAsNumber().doubleValue() * 100); //TODO
                if (mt940.getField60F().getDCMark().equals("D")) {
                    this.openingBalance *= -1;
                }
                this.currencyCode = mt940.getField60F().getCurrency();
                this.openingDate = mt940.getField60F().getDate();
            }


            if (mt940.getField62F() == null) {
                this.closingBalance = (int) (mt940.getField62M().getAmountAsNumber().doubleValue() * 100); //TODO
                if (mt940.getField62M().getDCMark().equals("D")) {
                    this.closingBalance *= -1;
                }
                this.closingDate = mt940.getField62M().getDate();
            } else {
                this.closingBalance = (int) (mt940.getField62F().getAmountAsNumber().intValue() * 100); //TODO
                if (mt940.getField62F().getDCMark().equals("D")) {
                    this.closingBalance *= -1;
                }
                this.closingDate = mt940.getField62F().getDate();
            }
            Tag start = mt940.getSwiftMessage().getBlock4().getTagByNumber(60);
            Tag end = mt940.getSwiftMessage().getBlock4().getTagByNumber(62);
            SwiftTagListBlock loop = mt940.getSwiftMessage().getBlock4().getSubBlock(start, end);
            Transaction previousTrans = new Transaction();
            previousTrans.setAfterTransaction(this.openingBalance);
            for (int i = 0; i < loop.size(); i++) {
                Tag t = loop.getTag(i);
                if (t.getName().equals("61")) {
                    Field61 tx = (Field61) t.asField();
                    if (i + 1 < loop.size() && loop.getTag(i + 1).getName().equals("86")) {
                        Field86 t2 = (Field86) loop.getTag(i + 1).asField();
                        this.transactions.add(previousTrans = new Transaction(tx, t2, previousTrans));
                        i++;
                    } else {
                        this.transactions.add(previousTrans = new Transaction(tx, null, previousTrans));
                    }
                }

            }
            this.setNumberOfTransactions(transactions.size());
            Fraud.calculateRisk(transactions);
            this.correct = true;
        } catch (NullPointerException e) {
            System.err.println("Something went wrong while parsing the file\n" + e);
            this.correct = false;
        }
//		if (!(transactions.get(transactions.size()-1).getAfterTransaction() == this.closingBalance)) {
//			throw new IOException();
//		}
    }


    // GETTERS AND SETTERS

    public boolean isCorrect() {
        return this.correct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTransactionReferenceNumber() {
        return transactionReferenceNumber;
    }

    public void setTransactionReferenceNumber(String transactionReferenceNumber) {
        this.transactionReferenceNumber = transactionReferenceNumber;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public String getStatementNumber() {
        return statementNumber;
    }

    public void setStatementNumber(String statementNumber) {
        this.statementNumber = statementNumber;
    }

    public int getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(int openingBalance) {
        this.openingBalance = openingBalance;
    }

    public String getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(String openingDate) {
        this.openingDate = openingDate;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public int getClosingBalance() {
        return closingBalance;
    }

    public void setClosingBalance(int closingBalance) {
        this.closingBalance = closingBalance;
    }

    public String getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(String closingDate) {
        this.closingDate = closingDate;
    }

    public int getNumberOfTransactions() {
        return numberOfTransactions;
    }

    public void setNumberOfTransactions(int numberOfTransactions) {
        this.numberOfTransactions = numberOfTransactions;
    }

}
