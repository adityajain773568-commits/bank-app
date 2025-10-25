package repository;

import domain.Account;

import java.util.*;

public class AccountRepository {
    private final Map<String, Account> accountsByNumber = new HashMap<>();

    public void save(Account account){
        accountsByNumber.put(account.getAccountNumber(),account);
    }

    public Map<String, Account> getAccountsByNumber() {
        return accountsByNumber;
    }


    public List<Account> findAll() {
        return new ArrayList<>(accountsByNumber.values());
    }

    public Optional<Account> findByNumber(String accountNumber) {

        return Optional.ofNullable(accountsByNumber.get(accountNumber));


    }
}
