package il.ac.kinneret.mjmay.banker.client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import il.ac.kinneret.mjmay.banker.server.Account;
import il.ac.kinneret.mjmay.banker.server.AccountList;

/**
 * The client for the RMI banking system.
 * @author Michael J. May
 */
public class BankClient {
    /**
     * Reads in from the keyboard
     */
    private static BufferedReader brKeyboard;

    public static void main(String[] args) throws IOException
    {
        if (args.length < 1)
        {
            System.out.println("Usage: ServerIP AccountListName");
            return;
        }

        brKeyboard = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Welcome to the Bank Client.");

        int[] accounts;
        Registry registry;
        try
        {
            // open the registry
            registry = LocateRegistry.getRegistry(args[0]);
            AccountList list = (AccountList)registry.lookup(args[1]);

            accounts = list.getAccounts();
        }
        catch (Exception ex)
        {
            System.out.println("Error reading accounts list object: " + ex.getMessage());
            return;
        }

        while (true)
        {
            int accountNum = GetAccountNumber(accounts);
            if (accountNum == -1)
            {
                System.out.println("Bye!");
                break;
            }

            // get the relevant account
            Account account;
            try {
                account = (Account)registry.lookup("act" + accountNum);
            } catch (NotBoundException e) {
                // error with this account, just try again
                System.out.println("Can't open the account you asked for: " + e.getMessage());
                System.out.println("Try again.");
                continue;
            }
            // show the menu for the account
            while (true)
            {
                System.out.println("Choose an operation to perform:");
                System.out.println("1. Deposit");
                System.out.println("2. Withdraw");
                System.out.println("3. Show Customer details and account number");
                System.out.println("4. Show balance.");
                System.out.println("5. Log into another account");

                String choiceLine = brKeyboard.readLine();

                int choice;
                try
                {
                    choice = Integer.parseInt(choiceLine);
                }
                catch (NumberFormatException nfe)
                {
                    System.out.println("Error reading input.  Try again.");
                    continue;
                }
                boolean leaveaccount = false;
                switch (choice)
                {
                    case 1: performDeposit(account); break;
                    case 2: performWithdrawal(account); break;
                    case 3: performShowDetails(account); break;
                    case 4: performShowBalance(account); break;
                    case 5: leaveaccount = true; break;
                    default: break;
                }
                // if the user choose to move to another account, leave this loop
                if ( leaveaccount)
                {
                    break;
                }

            }

        }

    }

    /**
     * Performs the show balance operation on the server account object
     * @param account The account object to operate on
     * @throws RemoteException
     */
    private static void performShowBalance(Account account) throws RemoteException {
        System.out.println("Current balance: " + account.getBalance());
    }

    /**
     * Performs the show account details operation on the server account object
     * @param account The account object to operate on
     * @throws RemoteException
     */
    private static void performShowDetails(Account account) throws RemoteException {
        System.out.println("Account details: " + account.getCustomer().toString());
    }

    /**
     * Performs the withdraw money operation on the server account object
     * @param account The account object to operate on
     * @throws RemoteException
     */
    private static void performWithdrawal(Account account) throws RemoteException {
        performShowBalance(account);
        System.out.print("How much to withdraw? ");
        double amount;
        do {
            // if we can't read from the keyboard, something is awful
            String line;
            try { line = brKeyboard.readLine(); } catch (Exception ex) { return; }
            try {
                amount = Double.parseDouble(line);
            }
            catch (Exception ex)
            {
                amount = -1;
            }
            if (amount > account.getBalance() || amount < 0)
            {
                System.out.println("Illegal amount.  Try again.");
            }
        } while (amount < 0 || amount > account.getBalance());

        if ( account.withdraw(amount) )
        {
            System.out.println("Withdrawal successful");
            performShowBalance(account);
        }
        else
        {
            System.out.println("Error withdrawing");
        }
    }

    /**
     * Performs the deposit money operation on the server account object
     * @param account The account object to operate on
     * @throws RemoteException
     */
    private static void performDeposit(Account account) throws RemoteException {
        performShowBalance(account);
        System.out.print("How much to deposit? ");
        double amount;
        do {
            // if we can't read from the keyboard, something is awful
            String line;
            try { line = brKeyboard.readLine(); } catch (Exception ex) { return; }
            try {
                amount = Double.parseDouble(line);
            }
            catch (Exception ex)
            {
                amount = -1;
            }
            if (amount < 0)
            {
                System.out.println("Illegal amount.  Try again.");
            }
        } while (amount < 0);

        if ( account.deposit(amount) )
        {
            System.out.println("Deposit successful");
            performShowBalance(account);
        }
        else
        {
            System.out.println("Error depositing");
        }

    }

    /**
     * Returns the account number selected or -1 if the user asked to quit
     * @param accounts The accounts list object
     * @return The selected account number or -1 if the user chose to quit
     * @throws IOException
     */
    private static int GetAccountNumber(int[] accounts) throws IOException {
        int accountNum = 0;
        System.out.println(accounts.length + "accounts recognized:");
        for (int account : accounts) {
            System.out.println("Account number: " + account);
        }
        boolean success = false;
        do {
            System.out.println("Enter an account number or a blank line to quit:");
            String lineIn = brKeyboard.readLine();
            if (lineIn.trim().isEmpty()) { return -1;}
            try {
                accountNum = Integer.parseInt(lineIn);
                // see if it's in the list
                for ( int a : accounts)
                {
                    if (a == accountNum)
                    {
                        success = true;
                        break;
                    }
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Error reading account number. Try again.");
                success = false;
            }
        } while (!success);

        return accountNum;
    }

}
