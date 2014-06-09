package corejava.voumeII.chapter11.warehouse1;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
   The remote interface for a simple warehouse.
   @version 1.0 2007-10-09
   @author Cay Horstmann
*/
public interface Warehouse extends Remote {

    double getPrice(String description) throws RemoteException;
}
