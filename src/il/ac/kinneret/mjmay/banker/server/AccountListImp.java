package il.ac.kinneret.mjmay.banker.server;

import java.io.Serial;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Implementation of the account list interface for the RMI bank example.
 *
 * @author Michael J. May
 */
public class AccountListImp extends UnicastRemoteObject implements AccountList {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * The list of account numbers
     */
    int[] accounts;
    /**
     * The last used account number
     */
    int top;

    /**
     * Builds a new list of the account numbers.
     *
     * @param s The initial number of accounts
     * @throws RemoteException
     */
    public AccountListImp(int s) throws RemoteException {
        accounts = new int[s];
        top = 0;
    }

    /**
     * Adds a new account to the list
     *
     * @param i The account number
     */
    public void addAccount(int i) {
        if (top == accounts.length) {
            int[] a = new int[accounts.length * 2];
            System.arraycopy(accounts, 0, a, 0, accounts.length);

            accounts = a;
        }

        accounts[top] = i;
        top++;
    }

    /**
     * Gets the list of the account numbers
     *
     * @return The list of account numbers
     * @throws RemoteException
     */
    public int[] getAccounts() throws RemoteException {
        int[] tightList = new int[top];
        System.arraycopy(accounts, 0, tightList, 0, top);
        return tightList;
    }
}
