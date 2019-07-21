package ru.ariona.bank;

import java.util.logging.Logger;

public class Operations {

    private static Logger log = Logger.getLogger(Operations.class.getName());

    public static void main(String[] args){
        final Account a = new Account(1000);
        final Account b = new Account(2000);

        new Thread(() -> {
                    transfer(a,b,500);

            }
        ).start();
        transfer(b,a,300);
    }

    private static void transfer(Account acc1, Account acc2, int amount) {
        if (acc1.getBalance() < amount) {
            throw new RuntimeException("Недостаточно средств!");
        }

        if (acc1.getBalance() > acc2.getBalance()) {
            tr(acc1,acc2,amount);
        } else {
            tr(acc2,acc1,amount);
        }


        log.info("done");
    }

    private static void tr(Account acc1, Account acc2, int amount) {
        synchronized (acc1){
            synchronized (acc2) {
                acc1.withdraw(amount);
                acc2.deposit(amount);
            }
        }
    }
}
