package repository;

import domain.Account;
import domain.Transaction;

import java.util.*;
import java.util.stream.Collectors;

public class TransactionRepository {
    private final Map<String , List<Transaction>> txByAccount = new HashMap<>();

    public void add(Transaction transaction) {
        List<Transaction> list = txByAccount.computeIfAbsent(transaction.getAccountNumber(), k -> new ArrayList<>());
        list.add(transaction);
    }

    public List<Transaction> getAll(Account account){
        return txByAccount.computeIfAbsent(account.getAccountNumber(),k->new ArrayList<>()).stream().sorted(Comparator.comparing(Transaction::getTimestamp)).collect(Collectors.toList());
    }
}
