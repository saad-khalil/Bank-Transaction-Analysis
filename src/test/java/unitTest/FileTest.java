package unitTest;

import com.bta.main.model.File;
import com.prowidesoftware.swift.model.mt.mt9xx.MT940;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This file contains all the test for the File class (the bean).
 */
public class FileTest {

    /*
     * Simple Strings containing the messages contents to parse.
     */
    String validFile = "{1:F01AAAABB99BSMK3513951576}" +
            "{2:O9400934081223BBBBAA33XXXX03592332770812230834N}" +
            "{4:\n" +
            ":20:0112230000000890\n" +
            ":25:SAKG800030155USD\n" +
            ":28C:255/1\n" +
            ":60F:C011223USD175768,92\n" +
            ":61:0112201223CD110,92NDIVNONREF//08 IL053309\n" +
            "/GB/2542049/SHS/312,\n" +
            ":62F:C011021USD175879,84\n" +
            ":20:NONREF\n" +
            ":25:4001400010\n" +
            ":28C:58/1\n" +
            ":60F:C140327EUR6308,75\n" +
            ":61:1403270327C3519,76NTRF50RS201403240008//2014032100037666\n" +
            "ABC DO BRASIL LTDA\n" +
            ":86:INVOICE NR. 6000012801 \n" +
            "ORDPRTY : ABC DO BRASIL LTDA RUA LIBERO BADARO,293-SAO \n" +
            "PAULO BRAZIL }";

    String invalidFile = "{1:F01AAAABB99BSMK3513951576}" +
            "{2:O9400934081223BBBBAA33XXXX03592332770812230834N}" +
            "{4:\n" +
            ":21:0112230000000890\n" +
            ":25:SAKG800030155USD\n" +
            ":28C:255/1\n" +
            ":60F:C011223USD175768,92\n" +
            ":61:0112201223CD110,92NDIVNONREF//08 IL053309\n" +
            "/GB/2542049/SHS/312,\n" +
            ":62F:C0110219856175879,84\n" +
            ":20:NONREF\n" +
            ":25:4001400010\n" +
            ":28C:58/1\n" +
            ":60F:C140327EUR6308,75\n" +
            ":61:1403270327C3519,76NTRF50RS201403240008//2014032100037666\n" +
            "ABC DO BRASIL LTDA\n" +
            ":86:INVOICE NR. 6000012801 \n" +
            "ORDPRTY : ABC DO BRASIL LTDA RUA LIBERO BADARO,293-SAO \n" +
            "PAULO BRAZIL }";


    /*
     * Parse the String content into a SWIFT message object.
     */
    MT940 mt = MT940.parse(validFile);
    File file = new File(mt, "test");


    /**
     * Tests if the valid file is valid.
     */
    @Test
    public void validFileTest() {
        MT940 mt = MT940.parse(validFile);
        File file = new File(mt, "test");
        assertTrue(file.isCorrect(), "Valid file deemed incorrect!");
    }


    /**
     * Tests if the invalid file is invalid.
     */
    @Test
    public void invalidFileTest() {
        MT940 mt = MT940.parse(invalidFile);
        File file = new File(mt, "test");
        assertFalse(file.isCorrect(), "Invalid file deemed correct!");
    }


    /**
     * Tests if the file name is correct.
     */
    @Test
    public void fileNameTest() {
        MT940 mt = MT940.parse(validFile);
        File file = new File(mt, "test");
        assertEquals("test", file.getName(), "Error while getting file name!");
    }


    /**
     * Tests if the setter of the file name works.
     */
    @Test
    public void fileNameTest2() {
        MT940 mt = MT940.parse(validFile);
        File file = new File(mt, "test");
        file.setName("notTest");
        assertNotEquals("test", file.getName(), "Error while setting file name!");
    }


    /**
     * Tests the opening balance.
     */
    @Test
    public void openingBalanceTest() {
        MT940 mt = MT940.parse(validFile);
        File file = new File(mt, "test");
        assertEquals(17576892, file.getOpeningBalance(), "Error while getting file opening balance!");
    }


    /**
     * Tests the opening date.
     */
    @Test
    public void openingDateTest() {
        MT940 mt = MT940.parse(validFile);
        File file = new File(mt, "test");
        assertEquals("011223", file.getOpeningDate(), "Error while getting file opening date!");
    }


    /**
     * Tests the currency of the file.
     */
    @Test
    public void fileCurrencyTest() {
        MT940 mt = MT940.parse(validFile);
        File file = new File(mt, "test");
        assertEquals("USD", file.getCurrencyCode(), "Error in getting the transaction currency code!");
    }


    /**
     * Tests the closing balance.
     */
    @Test
    public void fileClosingBalanceTest() {
        MT940 mt = MT940.parse(validFile);
        File file = new File(mt, "test");
        assertEquals(17587900, file.getClosingBalance(), "Error in getting the file closing balance!");
    }


    /**
     * Tests the transaction reference number.
     */
    @Test
    public void fileTransactionReferenceNumberTest() {
        MT940 mt = MT940.parse(validFile);
        File file = new File(mt, "test");
        assertEquals("0112230000000890", file.getTransactionReferenceNumber(), "Error in getting the file transaction reference number!");
    }


    /**
     * Tests the sender.
     */
    @Test
    public void senderTest() {
        MT940 mt = MT940.parse(validFile);
        File file = new File(mt, "test");
        assertEquals("BBBBAA33XXXX", mt.getSender(), "Error while getting sender!");
    }


    /**
     * Tests the receiver.
     */
    @Test
    public void receiverTest() {
        MT940 mt = MT940.parse(validFile);
        File file = new File(mt, "test");
        assertEquals("AAAABB99BSMK", mt.getReceiver(), "Error while getting receiver!");
    }


    /**
     * Tests the transaction reference.
     */
    @Test
    public void transactionReferenceTest() {
        MT940 mt = MT940.parse(validFile);
        File file = new File(mt, "test");
        assertEquals("0112230000000890", file.getTransactionReferenceNumber(), "Error while getting receiver!");
    }


    /**
     * Tests the transaction number.
     */
    @Test
    public void transactionNumberTest() {
        MT940 mt = MT940.parse(validFile);
        File file = new File(mt, "test");
        assertEquals(1, file.getNumberOfTransactions(), "Error in number of transactions!");
    }


    /**
     * Tests the transaction amount.
     */
    @Test
    public void transactionAmountTest() {
        MT940 mt = MT940.parse(validFile);
        File file = new File(mt, "test");
        assertEquals(11092, file.getTransactions().get(0).getAmount(), "Error while getting the amount!");
    }


    /**
     * Tests the transaction after.
     */
    @Test
    public void transactionAfterTest() {
        MT940 mt = MT940.parse(validFile);
        File file = new File(mt, "test");
        assertEquals(17587984, file.getTransactions().get(0).getAfterTransaction(), "Error in getting the after transaction!");
    }


    /**
     * Tests the transaction bank reference.
     */
    @Test
    public void transactionBankRefTest() {
        MT940 mt = MT940.parse(validFile);
        File file = new File(mt, "test");
        assertEquals("08 IL053309", file.getTransactions().get(0).getBankRef(), "Error in getting the bank ref!");
    }


    /**
     * Tests the transaction date.
     */
    @Test
    public void transactionDateTest() {
        MT940 mt = MT940.parse(validFile);
        File file = new File(mt, "test");
        assertEquals("011220", file.getTransactions().get(0).getDate(), "Error in getting the transaction date!");
    }


    /**
     * Tests teh transaction type id.
     */
    @Test
    public void transactionTypeIDTest() {
        MT940 mt = MT940.parse(validFile);
        File file = new File(mt, "test");
        assertEquals("N", file.getTransactions().get(0).getTypeId(), "Error in getting the transaction type id!");
    }


    /**
     * Tests the transaction reference of the account owner.
     */
    @Test
    public void transactionReferenceAccOwnerTest() {
        MT940 mt = MT940.parse(validFile);
        File file = new File(mt, "test");
        assertEquals("NONREF", file.getTransactions().get(0).getCustomerRef(), "Error in getting the transaction reference of the account owner!");
    }


    /**
     * Tests the fraud level of a transaction.
     */
    @Test
    public void transactionFraudTest() {
        MT940 mt = MT940.parse(validFile);
        File file = new File(mt, "test");
        assertEquals("LOW", file.getTransactions().get(0).getFraud(), "Error in getting the transaction fraud level!");
    }


}
