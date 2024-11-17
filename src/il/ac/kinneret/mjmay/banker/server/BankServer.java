package il.ac.kinneret.mjmay.banker.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Vector;

/**
 * The bank server for the RMI Bank example.
 *
 * @author Michael J. May
 */
public class BankServer {

    /**
     * Starts the server for RMI bank example
     *
     * @param args The name of the file with the account balance information
     */
    public static void main(String[] args) {

        if (args.length < 1) {
            showUsage();
            return;
        }
        // generate some accounts based on the file given
        File f = new File(args[0]);
        Vector<String> v = new Vector<>();

        try (BufferedReader input = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = input.readLine()) != null) {
                v.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Couldn't open or read file.");
            return;
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }

        AccountImp[] accounts = new AccountImp[v.size()];
        AccountListImp list;
        try {
            list = new AccountListImp(v.size());
        } catch (RemoteException re) {
            System.out.println("Error creating account list object");
            return;
        }

        // trying to open the registry
        Registry registry;
        try {
            registry = LocateRegistry.createRegistry(1099);
        } catch (RemoteException e1) {
            // can't open the registry, die
            System.out.println("Can't create local registry: " + e1.getMessage());
            return;
        }

        for (int i = 0; i < v.size(); i++) {
            String[] parts = v.elementAt(i).split(";");
            Customer c = new Customer(parts[0], parts[1], parts[2], parts[3]);
            try {
                accounts[i] = new AccountImp(c, Integer.parseInt(parts[4]), Double.parseDouble(parts[5]));
                System.out.println("Added account: " + accounts[i].toString());
                registry.rebind("act" + accounts[i].getNumber(), accounts[i]);
                System.out.println("Bound account: " + accounts[i].toString());
                list.addAccount(accounts[i].getNumber());
            } catch (Exception e) {
                System.out.println("Error creating customer from line: " + v.elementAt(i));
            }
        }

        // shows the accounts list object
        try {
            registry.rebind("accounts", list);
            System.out.println("Bound account list object with " + accounts.length + " objects");
        } catch (Exception ex) {
            System.out.println("Error binding list object.");
        }
    }

    /**
     * Shows a usage message for the tool
     */
    private static void showUsage() {
        System.out.println("BankServer configurationFile");

    }
}
