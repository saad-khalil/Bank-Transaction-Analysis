package com.bta.main.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * You mostly do not now upfront if a transaction is fraudulent.
 * So, ideally it would be great if you can score each transaction (or the whole set of transactions at once, that’s up to you)
 * with a change of being fraudulent.
 * There are some indications that a transaction has a higher change of being fraudulent.
 * Some examples:
 * •	When there is no description entered for a specific transaction
 * •	When you loop through all transactions, and you see multiple transactions having the same name (recipient of the money) but have different IBAN numbers.
 * •	When all bank transactions are going to Dutch IBAN numbers, but only one bank transaction is going to a foreign
 */
public class Fraud {
    enum Risk {
        NO,
        LOW,
        MEDIUM,
        HIGH
    }


    public Fraud() {
    }

    public static List<Transaction> calculateRisk(List<Transaction> list) {
        HashMap<String, String> nameIBANCheck = new HashMap<>();
        int dutchIBANCount = 0;
        int foreignIBANCount = 0;
        List<Transaction> foreignIBAN = new ArrayList<>();

        for (Transaction transaction : list) {
            //No description
            if (transaction.getDescription() == null) {
                transaction.setFraud(nextRisk(transaction.getFraud()));
            }

            //Same name, different IBAN
            if (!nameIBANCheck.containsKey(transaction.getCustomerRef())) {
                if (transaction.getBankRef() != null) {
                    nameIBANCheck.put(transaction.getCustomerRef(), transaction.getBankRef());
                }
            } else if (!nameIBANCheck.get(transaction.getCustomerRef()).equals(transaction.getBankRef())) {
                transaction.setFraud(nextRisk(transaction.getFraud()));
            }

            //All transactions to dutch IBAN, but one to foreign IBAN (continues after for loop)
            if (transaction.getBankRef() != null && transaction.getBankRef().startsWith("NL")) {
                dutchIBANCount++;
            } else if (transaction.getBankRef() != null) {
                foreignIBANCount++;
                foreignIBAN.add(transaction);
            }
        }

        if (dutchIBANCount > foreignIBANCount && foreignIBANCount == 1) {
            for (Transaction transaction : foreignIBAN) {
                transaction.setFraud(nextRisk(transaction.getFraud()));
            }
        }
        return list;
    }


    private static Risk nextRisk(String risk) {
        Risk nextRisk;
        if (risk == null) {
            nextRisk = Risk.LOW;
        } else {
            switch (risk) {
                case "LOW":
                    nextRisk = Risk.MEDIUM;
                    break;
                case "MEDIUM":
                    nextRisk = Risk.HIGH;
                    break;
                case "HIGH":
                    nextRisk = Risk.HIGH;
                    break;
                default:
                    nextRisk = Risk.NO;
                    System.err.println("No such risk");
            }
        }
        return nextRisk;
    }

}
