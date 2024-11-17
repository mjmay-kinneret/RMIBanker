package il.ac.kinneret.mjmay.banker.server;

import java.io.Serial;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Implementation of Account interface for RMI banking example.
 *
 * @author Michael J. May
 */
public class AccountImp extends UnicastRemoteObject implements Account {

    /**
     * Serial UID for RMI identification
     */
    @Serial
    private static final long serialVersionUID = -5677093365471646101L;
    /**
     * Customer object with customer info
     */
    private final Customer customer;
    /**
     * Account number for the customer
     */
    private final int accountNumber;
    /**
     * Balance for the account
     */
    private double balance;

    /**
     * Creates a new AccountImp object with full parameter information.
     *
     * @param c      The Customer who owns the account.  The values are copied so there is no problem of references.
     * @param num    The account number.
     * @param amount The initial balance of the account.
     * @throws RemoteException
     */
    public AccountImp(Customer c, int num, double amount) throws RemoteException {
        this.customer = new Customer(c.getTZ(), c.getName(), c.getAddress(), c.getCity());
        this.accountNumber = num;
        this.balance = amount;
    }

    /**
     * Deposits money in the account
     *
     * @param amount The amount of money to deposit.  Must be greater or less than 0.
     * @return True if the deposit worked.  False otherwise.
     * @throws RemoteException
     */
    @Override
    public Boolean deposit(double amount) throws RemoteException {
        if (amount <= 0) {
            return false;
        } else {
            this.balance += amount;
            return true;
        }
    }

    /**
     * Withdraws money from the account
     *
     * @param amount The amount of money to withdraw.  Must be greater or less than 0 and can't be greater than the
     *               account balance.
     * @return True if the withdraw worked.  False otherwise.
     * @throws RemoteException
     */
    @Override
    public Boolean withdraw(double amount) throws RemoteException {
        if (balance - amount < 0 || amount < 0) {
            return false;
        } else {
            this.balance -= amount;
            return true;
        }
    }

    /**
     * Gets the account number
     *
     * @return The account number
     * @throws RemoteException
     */
    @Override
    public int getNumber() throws RemoteException {
        return this.accountNumber;
    }

    /**
     * Gets the account's Customer details object
     *
     * @return The Customer object
     * @throws RemoteException
     */
    @Override
    public Customer getCustomer() throws RemoteException {
        return this.customer;
    }

    /**
     * Gets the balance for the account
     *
     * @return The balance for the account
     * @throws RemoteException
     */
    @Override
    public double getBalance() throws RemoteException {
        return this.balance;
    }

    /**
     * Converts the Account into a string
     *
     * @return The account as a string
     */
    @Override
    public String toString() {
        return customer.getTZ() + ";" + customer.getName() + ";" + customer.getAddress() + ";" + customer.getCity() + ";" + this.accountNumber + ";" + this.balance;
    }

}
