package com.bta.main.model;


import javax.xml.bind.annotation.XmlRootElement;

import com.prowidesoftware.swift.model.field.Field61;
import com.prowidesoftware.swift.model.field.Field86;

@XmlRootElement
public class Transaction {

    //Risk of fraud
    private String fraudRisk;

    // 61 tag:
    private String date;
    private String entryDate;
    private String fundsCode;
    private int amount; // inc. d/c mark
    private int afterTransaction;
    private String typeId;
    private String customerRef;
    private String bankRef;
    private String suppDetails;

    // 86 tag:
    private String description;


    public Transaction() {

    }

    /*
     * Constructs a transaction (bean) from the two fields in a MT940 file that represent a transaction.
     * @param field61 The elements in the :61: tag in the MT940 file.
     * @param field86 The elements in the :86: tag in the MT940 file.
     * @param previous The previous transaction in the file (to calculate the balance after this transaction).
     */
    public Transaction(Field61 field61, Field86 field86, Transaction previous) {
        this.setDate(field61.getValueDate());
        entryDate = field61.getEntryDate();
        fundsCode = field61.getFundsCode();
        this.setTypeId(field61.getTransactionType());
        this.setCustomerRef(field61.getReferenceForTheAccountOwner());
        bankRef = field61.getReferenceOfTheAccountServicingInstitution();
        suppDetails = field61.getSupplementaryDetails();
        if (field86 != null) {
            description = field86.getNarrative();
        }
        this.amount = (int) (field61.getAmountAsNumber().doubleValue() * 100); //TODO

        if (field61.getDCMark().equals("D")) {
            this.amount *= -1;
        }
        //System.out.println("Amount: " + this.amount);
        this.setAfterTransaction(previous.getAfterTransaction() + this.amount);

        //System.out.println("After: " + this.afterTransaction);
    }


    // GETTERS AND SETTERS

    public String getFraud() {
        return fraudRisk;
    }

    public void setFraud(Fraud.Risk risk) {
        this.fraudRisk = risk.toString();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getFundsCode() {
        return fundsCode;
    }

    public void setFundsCode(String fundsCode) {
        this.fundsCode = fundsCode;
    }

    public int getAfterTransaction() {
        return afterTransaction;
    }

    public void setAfterTransaction(int afterTransaction) {
        this.afterTransaction = afterTransaction;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCustomerRef() {
        return customerRef;
    }

    public void setCustomerRef(String customerRef) {
        this.customerRef = customerRef;
    }

    public String getBankRef() {
        return bankRef;
    }

    public void setBankRef(String bankRef) {
        this.bankRef = bankRef;
    }

    public String getSuppDetails() {
        return suppDetails;
    }

    public void setSuppDetails(String suppDetails) {
        this.suppDetails = suppDetails;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFraud(String string) {
        this.fraudRisk = string;
    }


}
