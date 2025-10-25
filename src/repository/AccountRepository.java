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

    public List<Account> findAccountByCustomerId(String id) {
        List<Account> result = new ArrayList<>();
        for (Account a : accountsByNumber.values()){
//            System.out.println(id + "-------" + a.getCustomerId());
            if (a.getCustomerId().equals(id)){
                System.out.println("yes i matched with customer id " + id);
                result.add(a);
            }
        }
        return result;
    }
}
