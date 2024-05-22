package com.bta.main.database;

import com.bta.main.model.File;
import com.bta.main.model.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DatabaseConn {

    public static Connection c;
    static Scanner sc = new Scanner(System.in);
    static private String dbuser;
    static private String passwd;
    static private String schema;

    /*
     * Setup a connection to the UT Bronto database server.
     */
    public DatabaseConn() throws SQLException {
        dbuser = "dab_di20212b_34";
        passwd = "wXmEGKbZbkGQY0Lc";

        schema = "?currentSchema=bta_data";
        CreateConnection();
    }

    /*
     * clears all tables and creates them again, also creating a dummy user.
     */
    public void reset() throws SQLException {
        dropTransactionFilesTable();
        dropTransactionsTable();
        dropUserCredidentialsTable();
        createFileTable();
        createTransactionsTable();
        createUserCredidentialsTable();
        writeDummyUser();
    }

    /*
     * Creates a connection to the SQL server or returns an error if it is not able to.
     * @throws SQLException if the connection could not be made.
     */
    public void CreateConnection() throws SQLException {
        c = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(
                    "jdbc:postgresql://bronto.ewi.utwente.nl:5432/" + dbuser + schema,
                    dbuser, passwd);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Something went wrong while creating a connection");
            throw e;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Something went wrong while creating a connection");
            throw new SQLException();
        }
        System.out.println("\nOpened database successfully");
    }

    /*
     * Drops the general table with the data about the files.
     * @throws SQLException if it is not able to.
     */
    public void dropTransactionFilesTable() throws SQLException {

        String query = "DROP TABLE IF EXISTS transaction_files CASCADE";
        try {
            Statement st = c.createStatement();
            System.out.println("Executing dropTransactionFilesTable query ...");
            st.executeUpdate(query);
            st.close();
        } catch (SQLException e) {
            System.err.println("Oops: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            throw e;
        }
        System.out.println("Query: dropTransactionFilesTable executed successfully!");
    }


    /*
     * Drops the table with the separate transactions from all files.
     * @throws SQLException if it is not able to.
     */
    public void dropTransactionsTable() throws SQLException {

        String query = "DROP TABLE IF EXISTS transactions CASCADE";
        try {
            Statement st = c.createStatement();
            System.out.println("Executing dropTransactionsTable query ...");
            st.executeUpdate(query);
            st.close();
        } catch (SQLException e) {
            System.err.println("Oops: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            throw e;
        }
        System.out.println("Query: dropTransactionsTable executed successfully!");
    }


    /*
     * Creates a SQL schema "bta_data" if it does not already exist.
     * @Throws SQLEException if something went wrong with the execution.
     */
    public void createSchema() throws SQLException {

        String query = "CREATE SCHEMA IF NOT EXISTS bta_data";
        try {
            Statement st = c.createStatement();
            System.out.println("Executing createSchema query ...");
            st.executeUpdate(query);
            st.close();
        } catch (SQLException e) {
            System.err.println("Oops: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            throw e;
        }
        System.out.println("Query: createSchema executed successfully!");

    }


    /*
     * Creates the general table with the data about the mt940 files if it does not already exist.
     * The table includes: file_name PR, transactionReferenceNumber PR, AccountIdentification, StatementNumber, openingBalance, openingDate, currencyCode, numberOfTransactions, closingBalance, closingDate
     * @throws SQLException if something went wrong with the execution.
     */
    public void createFileTable() throws SQLException {

        String query = "CREATE TABLE IF NOT EXISTS transaction_files (\n" +
                "\tfile_name varchar(255) NOT NULL,\n" +
                "\ttransactionReferenceNumber varchar(16) NOT NULL CHECK ( transactionReferenceNumber ~* '^[A-Z0-9]+$'),\n" +
                "\tAccountIdentification varchar(24) NOT NULL,\n" +
                "\tStatementNumber varchar(255) NOT NULL CHECK ( StatementNumber ~* '^[A-Z0-9]+$'),\n" +
                "\topeningBalance varchar(29) NOT NULL,\n" +
                "\topeningDate VARCHAR (255),\n" +
                "\tcurrencyCode varchar(3),\n" +
                "\tnumberOfTransactions varchar(3),\n" +
                "\tclosingBalance varchar(29) NOT NULL CHECK(closingBalance ~ '^[0-9]*$'),\n" +
                "\tclosingDate VARCHAR (255) NOT NULL CHECK(closingDate ~ '^[0-9]*$'), \n" +
                "\tPRIMARY KEY(file_name,transactionReferenceNumber));";
        try {
            Statement st = c.createStatement();
            System.out.println("Executing createFileTable query ...");
            st.executeUpdate(query);
            st.close();
        } catch (SQLException e) {
            System.err.println("Oops: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            throw e;
        }
        System.out.println("Query: createFileTable executed successfully!");


    }


    /*
     * Creates the table with the data from all transactions from the mt940 files if it does not already exist.
     * The table includes: id PK, file_name, date, entryDate, fundsCode, amount, afterTransaction, typeId, customerRef, bankRef, suppDetails, description, fraudRisk
     * @throws SQLException if something went wrong with the execution.
     */
    public void createTransactionsTable() throws SQLException {

        String query = "CREATE TABLE IF NOT EXISTS transactions (\n" +
                "\tid SERIAL,\n" +
                "\tfile_name varchar(255) NOT NULL,\n" +
                "\tdate varchar(6) NOT NULL ,\n" +
                "\tentryDate varchar(4) NOT NULL CHECK(entryDate ~ '^[0-9]*$') ,\n" +
                "\tfundsCode varchar(3),\n" +
                "\tamount varchar(19) NOT NULL CHECK(amount ~ '^[+-]*\\d+?$')  ,\n" +
                "\tafterTransaction varchar(255) NOT NULL CHECK(afterTransaction ~ '^[+-]*\\d+?$') ,\n" +
                "\ttypeId varchar(1),\n" +
                "\tcustomerRef varchar(6) NOT NULL,\n" +
                "\tbankRef varchar(34),\n" +
                "\tsuppDetails varchar(65),\n" +
                "\tdescription varchar(390),\n" +
                "\tfraudRisk varchar(10),\n" +
                "\tPRIMARY KEY(id));\n";
        try {
            Statement st = c.createStatement();
            System.out.println("Executing createTransactionsTable query ...");
            st.executeUpdate(query);
            st.close();
        } catch (SQLException e) {
            System.err.println("Oops: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            throw e;
        }
        System.out.println("Query: createTransactionsTable executed successfully!");


    }


    /*
     * Creates the table with the user credentials to login with if it does not already exist.
     * The Table includes: user_id PK, username PK, password_salt, hashed_password.
     * @throws SQLException if it is not able to.
     */
    public void createUserCredidentialsTable() throws SQLException {

        String query = "CREATE TABLE IF NOT EXISTS user_credidentials (\n" +
                "\tuser_id SERIAL,\n" +
                "\tusername varchar(255),\n" +
                "\tpassword_salt   varchar(255),\n" +
                "\thashed_password   varchar(64),\n" +
                "\tPRIMARY KEY(user_id,username));\n";
        try {
            Statement st = c.createStatement();
            System.out.println("Executing createUserCredidentialsTable query ...");
            st.executeUpdate(query);
            st.close();
        } catch (SQLException e) {
            System.err.println("Oops: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            throw e;
        }
        System.out.println("Query: createUserCredidentialsTable executed successfully!");

    }

    /*
     * Adds a dummy user to the User Credentials table with login: (usr: admin, psw: admin).
     * @throws SQLException if it is not able to.
     */
    public void writeDummyUser() throws SQLException {
        PreparedStatement stmt = null;

        try {
            stmt = c.prepareStatement("INSERT INTO user_credidentials (username,password_salt,hashed_password) VALUES (?,?,?)");
            stmt.setString(1, "admin");
            stmt.setString(2, "dontpwnme9");
            stmt.setString(3, "2e64d014244418e7fc0eef4dcad52b1ed594d427b22e3bfe3a8d7744c1d65fbc");

            System.out.println("Executing writeDummyUser query ...");

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Oops: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            throw e;
        }
        System.out.println("Query: writeDummyUser executed successfully!");
    }


    /*
     * TODO
     */
    public List<String> getUserData(String username) throws SQLException {

        List<String> userData = new ArrayList<String>();
        PreparedStatement stmt = null;

        try {
            stmt = c.prepareStatement("SELECT password_salt,hashed_password FROM user_credidentials WHERE username = ?");
            stmt.setString(1, username);
            System.out.println("Executing getUserData query ...");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                userData.add(rs.getString("password_salt"));
                userData.add(rs.getString("hashed_password"));
            }

        } catch (SQLException e) {

            System.err.println("Oops: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            throw e;
        }
        System.out.println("Query: getUserData executed successfully!");
        return userData;
    }


    /*
     * Drops the user Credentials table on the SQL server.
     * @throws SQLException if it is not able to.
     */
    public void dropUserCredidentialsTable() throws SQLException {

        String query = "DROP TABLE IF EXISTS user_credidentials CASCADE";
        try {
            Statement st = c.createStatement();
            System.out.println("Executing dropUserCredidentialsTable query ...");
            st.executeUpdate(query);
            st.close();
        } catch (SQLException e) {
            System.err.println("Oops: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            throw e;
        }
        System.out.println("Query: dropUserCredidentialsTable executed successfully!");
    }


    /*
     * Closes the SQL connection wit the SQL server.
     */
    public void closeConnection() {

        if (c != null) {
            try {
                c.close();
            } catch (SQLException e) {
                System.out.println("Connection already closed!");
            }
        }

    }


    /*
     * Adds an entry for a transaction file to the general file table.
     * @param file The mt940 file to write into the database.
     * @throws SQLException if it is not able to.
     */
    public void writeTransactionFile(File file) throws SQLException {
        PreparedStatement stmt = null;
        String fileName = file.getName();
        try {
            fileName.replace(" ", "_");
            stmt = c.prepareStatement("INSERT INTO transaction_files VALUES (?,?,?,?,?,?,?,?,?,?)");
            stmt.setString(1, String.valueOf(fileName));
            stmt.setString(2, String.valueOf(file.getTransactionReferenceNumber()));
            stmt.setString(3, String.valueOf(file.getAccountID()));
            stmt.setString(4, String.valueOf(file.getStatementNumber()));
            stmt.setString(5, String.valueOf(file.getOpeningBalance()));
            stmt.setString(6, String.valueOf(file.getOpeningDate()));
            stmt.setString(7, String.valueOf(file.getCurrencyCode()));
            stmt.setString(8, String.valueOf(file.getTransactions().size()));
            stmt.setString(9, String.valueOf(file.getClosingBalance()));
            stmt.setString(10, String.valueOf(file.getClosingDate()));
            System.out.println("Executing writeTransactionFile query ...");
            stmt.executeUpdate();
            stmt.close();
            for (int i = 0; i < file.getTransactions().size(); i++) {
                System.out.println("Inserting transaction: " + (i + 1) + "/" + file.getTransactions().size());
                writeTransactions(file.getName(), file.getTransactions().get(i));
            }
            System.out.println("Query: writeTransactionFile executed successfully!");
        } catch (SQLException e) {
            System.err.println("Oops: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            throw e;
        }

    }


    /*
     * Removes a transaction file from the general file table with the given filename.
     * @param filename The filename to search for in the table to delete.
     * @throws SQLException if it is not able to.
     */
    public void deleteTransactionFile(String filename) throws SQLException {

        PreparedStatement stmt = null;
        PreparedStatement stmt2 = null;
        try {
            stmt = c.prepareStatement("DELETE FROM transaction_files WHERE file_name = ?");
            stmt.setString(1, String.valueOf(filename));
            System.out.println("Executing deleteTransactionFile 1 query ...");
            stmt.executeUpdate();
            stmt.close();
            System.out.println("Query: deleteTransactionFile 1 executed successfully!");
            stmt2 = c.prepareStatement("DELETE FROM transactions WHERE file_name = ?");
            stmt2.setString(1, String.valueOf(filename));
            System.out.println("Executing deleteTransactionFile 2 query ...");
            stmt2.executeUpdate();
            stmt2.close();
        } catch (SQLException e) {
            System.err.println("Oops: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            throw e;
        }
        System.out.println("Query: deleteTransactionFile 2 executed successfully!");
    }


    /*
     * Gets all the filenames that are stored in the general file table as a list.
     * @returns List of strings as the filenames.
     * @throws SQLException if it is not able to.
     */
    public List<String> getFileNames() throws SQLException {

        List<String> fileNames = new ArrayList<>();
        PreparedStatement stmt = null;

        try {
            stmt = c.prepareStatement("SELECT DISTINCT file_name FROM transaction_files");
            System.out.println("Executing getFileNames query ...");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                fileNames.add(rs.getString("file_name"));
            }
            stmt.close();
        } catch (SQLException e) {

            System.err.println("Oops: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            throw e;
        }
        System.out.println("Query: getFileNames executed successfully!");
        return fileNames;
    }


    /*
     * Adds an entry in the transactions table for a transaction from a mt940 file.
     * @param filename The filename of the mt940 file the transaction is connected to.
     * @param trans The transaction to write in the table.
     * @throws SQLException if it is not able to.
     */
    public void writeTransactions(String filename, Transaction trans) throws SQLException {

        PreparedStatement stmt = null;

        try {
            stmt = c.prepareStatement("INSERT INTO transactions(file_name,date,entryDate,fundsCode,amount,afterTransaction,typeId,customerRef,bankRef,suppDetails,description,fraudRisk) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
            stmt.setString(1, String.valueOf(filename));
            stmt.setString(2, String.valueOf(trans.getDate()));
            if (trans.getEntryDate() != null) {
                stmt.setString(3, String.valueOf(trans.getEntryDate()));
            } else {
                stmt.setNull(3, Types.NULL);
            }
            if (trans.getFundsCode() != null) {
                stmt.setString(4, String.valueOf(trans.getFundsCode()));
            } else {
                stmt.setNull(4, Types.NULL);
            }
            stmt.setString(5, String.valueOf(trans.getAmount()));
            stmt.setString(6, String.valueOf(trans.getAfterTransaction()));
            stmt.setString(7, String.valueOf(trans.getTypeId()));
            stmt.setString(8, String.valueOf(trans.getCustomerRef()));
            if (trans.getBankRef() != null) {
                stmt.setString(9, String.valueOf(trans.getBankRef()));
            } else {
                stmt.setNull(9, Types.NULL);
            }
            if (trans.getSuppDetails() != null) {
                stmt.setString(10, String.valueOf(trans.getSuppDetails()));
            } else {
                stmt.setNull(10, Types.NULL);
            }
            if (trans.getDescription() != null) {
                stmt.setString(11, String.valueOf(trans.getDescription()));
            } else {
                stmt.setNull(11, Types.NULL);
            }
            if (trans.getFraud() != null) {
                stmt.setString(12, String.valueOf(trans.getFraud()));
            } else {
                stmt.setNull(12, Types.NULL);
            }
            System.out.println("Executing writeTransactions query ...");
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            System.err.println("Oops: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            throw e;
        }
        System.out.println("Query INSERT  " + filename + " executed successfully!");

    }


    /*
     * Gets the data from a specific file.
     * @param filename The filename to get the data for.
     * @throws SQLException if it is not able to.
     */
    public File getFile(String filename) throws SQLException {

        PreparedStatement stmt = null;
        File file = new File();
        ResultSet rs = null;
        try {
            stmt = c.prepareStatement("SELECT * FROM transaction_files WHERE file_name = ?");
            stmt.setString(1, String.valueOf(filename));
            System.out.println("Executing getFile query ...");
            rs = stmt.executeQuery();


            while (rs.next()) {
                file.setName(rs.getString("file_name"));
                file.setTransactionReferenceNumber(rs.getString("transactionreferencenumber"));
                file.setAccountID(rs.getString("accountidentification"));
                file.setStatementNumber(rs.getString("statementnumber"));
                file.setOpeningBalance(rs.getInt("openingbalance"));
                file.setOpeningDate(rs.getString("openingdate"));
                file.setCurrencyCode(rs.getString("currencycode"));
                file.setNumberOfTransactions(rs.getInt("numberoftransactions"));
                file.setClosingBalance(rs.getInt("closingbalance"));
                file.setClosingDate(rs.getString("closingdate"));
            }
            stmt.close();
        } catch (SQLException e) {
            System.err.println("Oops: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            e.printStackTrace();
            throw e;
        }
        System.out.println("Query: getFile executed successfully!");
        return file;

    }


    /*
     * Gets a list of all transactions from a mt940 file.
     * @param filename The filename of the mt940 file to get the transactions from.
     * @throws SQLException if something went wrong with the execution.
     */
    public List<Transaction> getTransactions(String file_name) throws SQLException {

        String query = "SELECT * FROM transactions WHERE file_name = ? ORDER BY date";
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        List<Transaction> transactions = new ArrayList<>();

        try {
            preparedStatement = c.prepareStatement(query);
            preparedStatement.setString(1, file_name);
            System.out.println("Executing getTransactions query ...");

            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setDate(rs.getString("date"));
                transaction.setEntryDate(rs.getString("entrydate"));
                transaction.setFundsCode(rs.getString("fundscode"));
                transaction.setAmount(rs.getInt("amount"));
                transaction.setAfterTransaction(rs.getInt("aftertransaction"));
                transaction.setTypeId(rs.getString("typeid"));
                transaction.setCustomerRef(rs.getString("customerref"));
                transaction.setBankRef(rs.getString("bankref"));
                transaction.setSuppDetails(rs.getString("suppdetails"));
                transaction.setDescription(rs.getString("description"));
                transaction.setFraud(rs.getString("fraudRisk"));
                transactions.add(transaction);
            }
            preparedStatement.close();
        } catch (SQLException e) {
            System.err.println("Oops: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            throw e;
        }
        System.out.println("Query: getTransactions executed successfully!");
        return transactions;
    }


}








