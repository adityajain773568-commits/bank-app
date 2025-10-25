package service.impl;

import domain.Account;
import domain.Customer;
import domain.Transaction;
import domain.Type;
import repository.AccountRepository;
import repository.CustomerRepository;
import repository.TransactionRepository;
import service.BankService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class BankServiceImpl implements BankService {
    private final AccountRepository accountRepository = new AccountRepository();
    private final TransactionRepository transactionRepository = new TransactionRepository();
    private final CustomerRepository customerRepository = new CustomerRepository();
    @Override
    public String openAccount(String name, String email, String accountType) {
        String customerId = UUID.randomUUID().toString();
//        String accountNumber = UUID.randomUUID().toString();
        String accountNumber = getAccountNumber();
        Account account = new Account(accountNumber,customerId, (double) 0, accountType);
        Customer customer  = new Customer(customerId,name,email);
        //save
        accountRepository.save(account);
        customerRepository.save(customer);
        return accountNumber;
    }

    @Override
    public List<Account> listAccount() {
        return accountRepository.findAll().stream().sorted(Comparator.comparing(Account::getAccountNumber)).toList();
    }

    @Override
    public void deposit(String accountNumber, Double amount, String depositNote) {
        Account account = accountRepository.findByNumber(accountNumber).orElseThrow(()->new RuntimeException("Account not found : "+ accountNumber));

        account.setBalance(account.getBalance() + amount);
        Transaction transaction = new Transaction(UUID.randomUUID().toString(), Type.DEPOSIT,accountNumber,amount, LocalDateTime.now(),depositNote);
        transactionRepository.add(transaction);

    }

    @Override
    public void withdraw(String accountNumber, Double amount, String withdrawalNote) {
        Account account = accountRepository.findByNumber(accountNumber).orElseThrow(()->new RuntimeException("Account not found : "+ accountNumber));
        if (amount<=account.getBalance()){
            account.setBalance(account.getBalance() - amount);
        }else {
            throw new RuntimeException("Insufficient Balance");
        }

        Transaction transaction = new Transaction(UUID.randomUUID().toString(), Type.WITHDRAW,accountNumber,amount, LocalDateTime.now(),withdrawalNote);
        transactionRepository.add(transaction);
    }

    @Override
    public void transfer(String from, String to, Double amount,String transferNote) {
        if (from.equals(to)) throw new RuntimeException("Both accounts are same!");
        Account senderAccount = accountRepository.findByNumber(from).orElseThrow(()->new RuntimeException("Sender's account not found"+from));
        Account recieverAccount = accountRepository.findByNumber(to).orElseThrow(()->new RuntimeException("receiver's account not found"+to));
        if (amount<=senderAccount.getBalance()){
            senderAccount.setBalance(senderAccount.getBalance()-amount);
            recieverAccount.setBalance(recieverAccount.getBalance()+amount);
            Transaction transaction = new Transaction(UUID.randomUUID().toString(),Type.TRANSFER_OUT,from,amount,LocalDateTime.now(),"transfer out");
            transactionRepository.add(transaction);
            Transaction transaction1 = new Transaction(UUID.randomUUID().toString(),Type.TRANSFER_IN,to,amount,LocalDateTime.now(),"transfer in");
            transactionRepository.add(transaction1);
        }else {
            throw new RuntimeException("Sender's didn't have this much amount ! ");
        }

    }

    @Override
    public List<Transaction> getStatement(String accNumber) {
        Account account = accountRepository.findByNumber(accNumber).orElseThrow(() -> new RuntimeException("Account Not found" + accNumber));
        return transactionRepository.getAll(account);
    }

    @Override
    public List<Account> searchAccountsByCustomerName(String customerName) {
        String query = ((customerName==null) ? " " : customerName.toLowerCase());
        List<Account> result = new ArrayList<>();
        for (Customer customer : customerRepository.findAll()){
            if (customer.getName().toLowerCase().equals(query)){
//                System.out.println("yess i m here" + query);
                List<Account> accountList = accountRepository.findAccountByCustomerId(customer.getId());
                result.addAll(accountList);
            }
        }
        result.sort(Comparator.comparing(Account::getAccountNumber));
        return result;
    }

    private String getAccountNumber() {
        int size = accountRepository.findAll().size() +1;
        return String.format("AC%06d",size);
    }

}
