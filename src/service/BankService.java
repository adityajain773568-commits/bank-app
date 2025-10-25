package service;

import domain.Account;
import domain.Transaction;

import java.util.List;

public interface BankService {
    String openAccount(String name, String email, String accountType);

    List<Account> listAccount();

    void deposit(String accountNumber , Double amount , String depositNote);

    void withdraw(String accountNumber, Double amount, String withdrawal);

    void transfer(String from, String to, Double amount,String transferNote);

    List<Transaction> getStatement(String accNumber);

    List<Account> searchAccountsByCustomerName(String customerName);
}
