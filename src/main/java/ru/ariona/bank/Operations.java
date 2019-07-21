package ru.ariona.bank;

import javax.naming.InsufficientResourcesException;
import java.util.logging.Logger;

public class Operations {

    private static Logger log = Logger.getLogger(Operations.class.getName());

    public static void main(String[] args) throws InsufficientResourcesException, InterruptedException {
        final Account a = new Account(1000);
        final Account b = new Account(2000);

        new Thread(new Runnable() {
            public void run() {
                try {
                    transfer(a,b,500);
                } catch (InsufficientResourcesException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        transfer(b,a,300);
    }

    static void transfer(Account acc1, Account acc2, int amount) throws InsufficientResourcesException, InterruptedException {
        if (acc1.getBalance() < amount) {
            throw new InsufficientResourcesException();
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
