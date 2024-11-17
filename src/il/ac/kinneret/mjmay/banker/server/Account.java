package il.ac.kinneret.mjmay.banker.server;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Account interface for RMI banking example.
 *
 * @author Michael J. May
 */
public interface Account extends Remote {
    Boolean deposit(double amount) throws RemoteException;
    Boolean withdraw(double amount) throws RemoteException;
    int getNumber() throws RemoteException;
    Customer getCustomer() throws RemoteException;
    double getBalance() throws RemoteException;
}
