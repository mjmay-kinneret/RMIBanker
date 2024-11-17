package il.ac.kinneret.mjmay.banker.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * An object with the list of the accounts in the RMI Bank example.
 *
 * @author Michael J. May
 */
public interface AccountList extends Remote {
    /**
     * Gets the list of accounts in the bank
     *
     * @return The list of account numbers
     * @throws RemoteException
     */
    int[] getAccounts() throws RemoteException;
}
