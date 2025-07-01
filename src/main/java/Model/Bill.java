
package Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class Bill implements Serializable {
    private static final long serialVersionUID = 1L;
    private int billNumber;
    private Client client;
    private ArrayList<WorkOrder> orders;
    private LocalDateTime issueDate;
    private double subtotal;
    private double taxes;
    private double overall;
    private boolean paid;

    public Bill() {
        this.orders = new ArrayList<>();
        this.issueDate = LocalDateTime.now();
        this.subtotal = 0.0;
        this.taxes = 0.0;
        this.overall = 0.0;
        this.paid = false;
    }

    public Bill(int billNumber, Client client) {
        this.billNumber = billNumber;
        this.client = client;
        this.orders = new ArrayList<>();
        this.issueDate = LocalDateTime.now();
        this.subtotal = 0.0;
        this.taxes = 0.0;
        this.overall = 0.0;
        this.paid = false;
        
    }
    
    public synchronized void addOrder(WorkOrder order){
        if(order != null && order.isReady()){
            orders.add(order);
        }
    }
    public synchronized void removeOrder(WorkOrder order){
        orders.remove(order);
    }
    public void calculateOverall(){
        subtotal = 0.0;
        for(WorkOrder wo : orders){
            if(wo.getDishes() != null){
                subtotal += wo.getDishes().getTotalPrice();
            }
        }
        taxes = subtotal * 0.12;
        overall = subtotal + taxes; 
    }
    public boolean isEmpty(){
        return orders.isEmpty();
    }
    public int getDishesCount(){
        return orders.size();
    }
    public synchronized void payBill() {
    if (!this.paid) {
        this.paid = true;
        
        if (this.client != null) {
            client.setPaidDishes(client.getPaidDishes() + orders.size());
            if (client.getPaidDishes() >= 10) {
                client.setTypeClient(TypeClient.GOLD);
            }
        }
        
        for (WorkOrder order : orders) {
            order.setOrderStatus(OrderStatus.PAID);
        }
    }
}
    public String getFormattedDate (){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return issueDate.format(formatter);
    }
    
     @Override
    public String toString() {
        return "Factura{" +
                "numeroFactura=" + billNumber +
                ", cliente=" + (client != null ? client.getCompleteName() : "null") +
                ", cantidadOrdenes=" + orders.size() +
                ", fechaEmision=" + getFormattedDate() +
                ", total=" + overall +
                ", pagada=" + paid +
                '}';
    }
    
    

    public int getBillNumber() {return billNumber;}
    public void setBillNumber(int billNumber) {this.billNumber = billNumber;}
    public Client getClient() {return client;}
    public void setClient(Client client) {this.client = client;}
    public ArrayList<WorkOrder> getOrders() {return orders;}
    public void setOrders(ArrayList<WorkOrder> orders) {this.orders = orders;}
    public LocalDateTime getIssueDate() {return issueDate;}
    public void setIssueDate(LocalDateTime issueDate) {this.issueDate = issueDate;}
    public double getSubtotal() {return subtotal;}
    public void setSubtotal(double subtotal) {this.subtotal = subtotal;}
    public double getTaxes() {return taxes;}
    public void setTaxes(double taxes) {this.taxes = taxes;}
    public double getOverall() {return overall;}
    public void setOverall(double overall) {this.overall = overall;}
    public boolean isPaid() {return paid;}
    public void setPaid(boolean paid) {this.paid = paid;}

}
