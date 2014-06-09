package corejava.voumeII.chapter11.warehouse1;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is the implementation for the remote Warehouse interface.
 * @version 1.01 2012-01-26
 * @author Cay Horstmann
 */
public class WarehouseImpl extends UnicastRemoteObject implements Warehouse {

    private static final long serialVersionUID = -3022926466727322303L;

    private Map<String, Double> prices;

    public WarehouseImpl() throws RemoteException {

        prices = new HashMap<>();
        prices.put("Blackwell Toaster", 24.95);
        prices.put("ZapXpress Microwave Oven", 49.95);
    }

    @Override
    public double getPrice(String description) throws RemoteException {

        Double price = prices.get(description);
        return price == null ? 0 : price;
    }
}
