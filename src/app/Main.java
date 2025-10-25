package app;

import domain.Account;
import service.BankService;
import service.impl.BankServiceImpl;

import java.util.List;
import java.util.Scanner;

public class Main {
    static void main() {
        boolean running = true;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Console Bank !! ");
        BankService bankService = new BankServiceImpl();
        while (running) {
            System.out.print("""
                    1) Open Account
                    2) Deposit
                    3) Withdraw
                    4) Transfer
                    5) Account Statement
                    6) List Accounts
                    7) Search Accounts by Customer Name
                    0) Exit
                    """);

            System.out.print("CHOOSE: ");
            String choice = scanner.nextLine().trim();
            System.out.println("CHOICE : " + choice);

            switch (choice) {
                case "1":
                    openAccount(scanner,bankService);
                    break;

                case "2":
                    deposit(scanner,bankService);
                    break;

                case "3":
                    withdraw(scanner);
                    break;


                case "4":
                    transfer(scanner);
                    break;

                case "5":
                    statement(scanner);
                    break;

                case "6":
                    listAccounts(scanner,bankService);
                    break;

                case "7":
                    searchAccounts(scanner);
                    break;

                default:
                    break;
            }
        }


    }

    private static void openAccount(Scanner scanner ,BankService bankService) {
        System.out.println("Customer name : ");
        String name = scanner.nextLine().trim();
        System.out.println("Customer email : ");
        String email = scanner.nextLine().trim();
        System.out.println("Account Type (SAVINGS/CURRENT) : ");
        String type = scanner.nextLine().trim();
        System.out.println("Initial Deposit(optional, blank for 0): ");
        String amountStr = scanner.nextLine().trim();
        double initial = Double.parseDouble(amountStr);
        String accountNumber = bankService.openAccount(name, email, type);
        if (initial>0){
            bankService.deposit(accountNumber,initial,"Account opened and this is your first transaction! ");
        }
        System.out.println("Account opened Successfully!! with account number " + accountNumber);
    }

    private static void deposit(Scanner scanner,BankService bankService) {
        System.out.println("Enter Account number : ");
        String accountNumber = scanner.nextLine().trim();
        System.out.println("Enter Amount : ");
        Double amount = Double.valueOf(scanner.nextLine().trim());
        bankService.deposit(accountNumber,amount , "Deposit");

    }

    private static void withdraw(Scanner scanner) {
    }

    private static void transfer(Scanner scanner) {
    }

    private static void statement(Scanner scanner) {
    }

    private static void listAccounts(Scanner scanner , BankService bankService) {

        bankService.listAccount().forEach(a->{
            System.out.println(a.getAccountNumber() + " | " + a.getAccountType() + " | " + a.getBalance());
        });

    }

    private static void searchAccounts(Scanner scanner) {
    }


}
