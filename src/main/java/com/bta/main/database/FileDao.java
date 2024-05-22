package com.bta.main.database;

import java.sql.SQLException;
import java.util.List;

import com.bta.main.model.DailyStats;
import com.bta.main.model.File;
import com.bta.main.model.Transaction;
import com.prowidesoftware.swift.model.mt.mt9xx.MT940;

public enum FileDao {
    instance;


    /*
     * Saves a mt940 file in the database using the DatabaseConn class.
     * @param mt940 The MT940 file to store in the database.
     * @param filename The filename of the MT940 file.
     * @returns true if it was successful.
     */
    public synchronized boolean save(MT940 mt940, String filename) {
        File file;
        DatabaseConn c = null;
        try {
            c = new DatabaseConn();
        } catch (SQLException e1) {
        }
        if (c != null) {
            try {
                file = new File(mt940, filename);
                if (!file.isCorrect()) {
                    return false;
                }
                c.writeTransactionFile(file);

            } catch (SQLException e) {
                return false;
            } finally {
                c.closeConnection();
            }
            return true;
        }
        return false;

    }

    /*
     * Gets a list if transactions of a MT940 file from the database.
     * @param filename The filename of the MT940 file.
     * @returns list of Transactions from the file.
     */
    public synchronized List<Transaction> getTransactions(String filename) {
        List<Transaction> list = null;
        DatabaseConn c = null;
        try {
            c = new DatabaseConn();
        } catch (SQLException e1) {
        }
        if (c != null) {
            try {
                list = c.getTransactions(filename);
            } catch (SQLException e) {
            } finally {
                c.closeConnection();
            }
        }
        return list;
    }


    /*
     * Gets the MT940 file from the database without the transactions.
     * @param filename The filename of the MT940 file.
     * @returns File (MT940 file type).
     */
    public synchronized File getFile(String filename) {
        File file = null;
        DatabaseConn c = null;
        try {
            c = new DatabaseConn();
        } catch (SQLException e1) {
        }
        if (c != null) {
            try {
                file = c.getFile(filename);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                c.closeConnection();
            }
        }
        return file;
    }

    /*
     * Gets all the filenames stored in the database.
     * @returns a list of filenames as strings.
     */
    public synchronized List<String> getFileNames() {
        List<String> list = null;
        DatabaseConn c = null;
        try {
            c = new DatabaseConn();
        } catch (SQLException e1) {
        }
        if (c != null) {
            try {
                list = c.getFileNames();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                c.closeConnection();
            }
        }
        return list;
    }

    /*
     * Deletes all files and transactions from the database.
     * @returns true if it was successful.
     */
    public synchronized boolean deleteAll() {
        DatabaseConn c = null;
        try {
            c = new DatabaseConn();
        } catch (SQLException e1) {
        }
        if (c != null) {
            try {
                c.reset();
            } catch (SQLException throwables) {
                System.out.println("Unable to delete the data!");
                return false;
            } finally {
                c.closeConnection();
            }
            return true;
        }
        return false;
    }

    /*
     * Deletes the general data and all transactions from a MT940 file stored in the database.
     * @param filename The filename of the MT940 file to delete.
     * @returns true if it was successful.
     */
    public synchronized boolean delete(String filename) {
        DatabaseConn c = null;
        try {
            c = new DatabaseConn();
        } catch (SQLException e1) {
        }
        if (c != null) {
            try {
                c.deleteTransactionFile(filename);
            } catch (SQLException throwables) {
                System.out.println("Unable to delete the data!");
                return false;
            } finally {
                c.closeConnection();
            }
            return true;
        }
        return false;

    }

    /*
     * Gets the daily statistics from the MT940 file to use for the statistics page.
     * @param filename The filename of the MT940 file.
     * @returns list of daily statistics about the MT940 file.
     */
    public synchronized List<DailyStats> getDailyStats(String filename) {
        List<Transaction> list = null;
        DatabaseConn c = null;
        try {
            c = new DatabaseConn();
        } catch (SQLException e1) {
        }
        if (c != null) {
            try {
                list = c.getTransactions(filename);
            } catch (SQLException e) {
            } finally {
                c.closeConnection();
            }
        }
        return DailyStats.createStats(list);
    }
}
