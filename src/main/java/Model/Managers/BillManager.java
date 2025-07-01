
package Model.Managers;

import Model.Bill;
import Model.Client;
import Model.WorkOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class BillManager {
    private Map<Integer, Bill> bills;
    private int nextBillNumber;

    public BillManager() {
        this.bills = new HashMap<>();
        this.nextBillNumber = 1;
    }

    public synchronized Bill createBill(Client client) {
        Bill bill = new Bill(nextBillNumber++, client);
        bills.put(bill.getBillNumber(), bill);
        return bill;
    }

    public synchronized void payBill(int billNumber) {
        Bill bill = bills.get(billNumber);
        if (bill != null && !bill.isPaid()) {
            bill.payBill();
        }
    }

    public synchronized Bill getBill(int billNumber) {
        return bills.get(billNumber);
    }

    public synchronized ArrayList<Bill> getClientBills(Client client) {
        ArrayList<Bill> clientBills = new ArrayList<>();
        for (Bill bill : bills.values()) {
            if (bill.getClient().getDpi().equals(client.getDpi())) {
                clientBills.add(bill);
            }
        }
        return clientBills;
    }

    public synchronized ArrayList<Bill> getPendingBills() {
        ArrayList<Bill> pendingBills = new ArrayList<>();
        for (Bill bill : bills.values()) {
            if (!bill.isPaid()) {
                pendingBills.add(bill);
            }
        }
        return pendingBills;
    }

    public synchronized ArrayList<Bill> getPaidBills() {
        ArrayList<Bill> paidBills = new ArrayList<>();
        for (Bill bill : bills.values()) {
            if (bill.isPaid()) {
                paidBills.add(bill);
            }
        }
        return paidBills;
    }
    public synchronized void addOrderToBill(int billNumber, WorkOrder order) {
    Bill bill = bills.get(billNumber);
    if (bill != null && order.isReady() && !bill.isPaid()) { 
        bill.addOrder(order);
        bill.calculateOverall();
    }
}

    public synchronized int getNextBillNumber() {
        return nextBillNumber;
    }

    public synchronized void setNextBillNumber(int nextBillNumber) {
        this.nextBillNumber = nextBillNumber;
    }

    public synchronized Map<Integer, Bill> getBillsMap() {
        return new HashMap<>(bills);
    }

    public synchronized void setBillsMap(Map<Integer, Bill> bills) {
        this.bills = bills != null ? new HashMap<>(bills) : new HashMap<>();
    }
}
