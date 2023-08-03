package com.smallworld;

import com.smallworld.data.Transaction;
import com.smallworld.datastore.TransactionDataStore;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class TransactionDataFetcher {

    /**
     * Returns the sum of the amounts of all transactions
     */
    public double getTotalTransactionAmount() throws Exception {
        List<Transaction> transactions = TransactionDataStore.getTransactions();

        return transactions.stream()
            .distinct()
            .mapToDouble(Transaction::getAmount)
            .sum();
    }

    /**
     * Returns the sum of the amounts of all transactions sent by the specified client
     */
    public double getTotalTransactionAmountSentBy(String senderFullName) throws Exception {
        List<Transaction> transactions = TransactionDataStore.getTransactions();
        return transactions.stream()
            .distinct()
            .filter(t -> t.getSenderFullName().equals(senderFullName))
            .mapToDouble(Transaction::getAmount)
            .sum();
    }

    /**
     * Returns the highest transaction amount
     * @return
     */
    public OptionalDouble getMaxTransactionAmount() throws Exception {
        List<Transaction> transactions = TransactionDataStore.getTransactions();
        return transactions.stream()
            .mapToDouble(Transaction::getAmount)
            .max();
    }

    /**
     * Counts the number of unique clients that sent or received a transaction
     */
    public long countUniqueClients() throws Exception {
        List<Transaction> transactions = TransactionDataStore.getTransactions();
        return transactions.stream()
            .distinct()
            .flatMap(t -> Stream.of(t.getSenderFullName(), t.getBeneficiaryFullName()))
            .collect(Collectors.toSet()).size();
    }

    /**
     * Returns whether a client (sender or beneficiary) has at least one transaction with a compliance
     * issue that has not been solved
     */
    public boolean hasOpenComplianceIssues(String clientFullName) throws Exception {
        List<Transaction> transactions = TransactionDataStore.getTransactions();
        return transactions.stream()
            .anyMatch(t -> (t.getSenderFullName().equals(clientFullName) || t.getBeneficiaryFullName().equals(clientFullName)) && !t.getIssueSolved() && t.getIssueId() != null);
    }

    /**
     * Returns all transactions indexed by beneficiary name
     */
    public Map<String, Transaction> getTransactionsByBeneficiaryName() throws Exception {
        List<Transaction> transactions = TransactionDataStore.getTransactions();
        return transactions.stream()
            .distinct()
            .collect(Collectors.toMap(Transaction::getBeneficiaryFullName, transaction -> transaction));
    }

    /**
     * Returns the identifiers of all open compliance issues
     */
    public Set<Integer> getUnsolvedIssueIds() throws Exception {
        List<Transaction> transactions = TransactionDataStore.getTransactions();
        return transactions.stream()
            .filter(t -> !t.getIssueSolved() && t.getIssueId() != null)
            .map(Transaction::getIssueId)
            .collect(Collectors.toSet());
    }

    /**
     * Returns a list of all solved issue messages
     */
    public List<String> getAllSolvedIssueMessages() throws Exception {
        List<Transaction> transactions = TransactionDataStore.getTransactions();
        return transactions.stream()
            .filter(t -> t.getIssueSolved() && t.getIssueId() != null)
            .map(Transaction::getIssueMessage)
            .collect(Collectors.toList());
    }

    /**
     * Returns the 3 transactions with the highest amount sorted by amount descending
     */
    public List<Transaction> getTop3TransactionsByAmount() throws Exception {
        List<Transaction> transactions = TransactionDataStore.getTransactions();
        return transactions.stream()
            .distinct()
            .sorted(Comparator.comparing(Transaction::getAmount).reversed())
            .limit(3)
            .collect(Collectors.toList());
    }

    /**
     * Returns the senderFullName of the sender with the most total sent amount
     */
    public String getTopSender() throws Exception {
        List<Transaction> transactions = TransactionDataStore.getTransactions();
        Map<String, Double> groupedByTransactions = transactions.stream()
            .distinct()
            .collect(Collectors.groupingBy(Transaction::getSenderFullName, summingDouble(Transaction::getAmount)));

        return Collections.max(groupedByTransactions.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

}
