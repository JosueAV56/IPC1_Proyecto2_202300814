
package View;
import Controller.MainController;
import Model.Bill;
import Model.Client;
import Model.Managers.PDFManager;
import Model.OrderDao;
import Model.OrderStatus;
import Model.WorkOrder;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class ClientView extends javax.swing.JFrame {
    
    private MainController mainController;
    private Client client;
    private OrderDao orderDao;
    public ClientView(MainController mainController,Client client) {
        this.mainController = mainController;
        this.orderDao = new OrderDao();
        this.client = client;
        initComponents();
        setupClientData();

    }
 
    private void setupClientData() {
        welcomeLabel.setText("Bienvenido a Restaurante USAC " + client.getCompleteName());
        nameUserLabel.setText(client.getUser());
        
        try {
            if (client.getPhotoPath() != null && !client.getPhotoPath().isEmpty()) {
                ImageIcon icon = new ImageIcon(client.getPhotoPath());
                Image img = icon.getImage().getScaledInstance(
                    photoUserPanel.getWidth(), 
                    photoUserPanel.getHeight(), 
                    Image.SCALE_SMOOTH);
                photoUserPanel.setLayout(new java.awt.BorderLayout());
                photoUserPanel.add(new javax.swing.JLabel(new ImageIcon(img)));
            } else {
                photoUserPanel.setLayout(new java.awt.BorderLayout());
                photoUserPanel.add(new javax.swing.JLabel("Sin foto"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al cargar la foto: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            photoUserPanel.setLayout(new java.awt.BorderLayout());
            photoUserPanel.add(new javax.swing.JLabel("Foto no disponible"));
        }
    }    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        welcomeLabel = new javax.swing.JLabel();
        photoUserPanel = new javax.swing.JPanel();
        nameUserLabel = new javax.swing.JLabel();
        enterDishesButton = new javax.swing.JButton();
        billButton = new javax.swing.JButton();
        logoutBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 153, 153));

        welcomeLabel.setText("jLabel1");

        javax.swing.GroupLayout photoUserPanelLayout = new javax.swing.GroupLayout(photoUserPanel);
        photoUserPanel.setLayout(photoUserPanelLayout);
        photoUserPanelLayout.setHorizontalGroup(
            photoUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 211, Short.MAX_VALUE)
        );
        photoUserPanelLayout.setVerticalGroup(
            photoUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 144, Short.MAX_VALUE)
        );

        nameUserLabel.setText("jLabel2");

        enterDishesButton.setText("Ingresar Platillos");
        enterDishesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enterDishesButtonActionPerformed(evt);
            }
        });

        billButton.setText("Facturas");
        billButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                billButtonActionPerformed(evt);
            }
        });

        logoutBtn.setText("Cerrar Sesion");
        logoutBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(118, 118, 118)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(billButton, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(enterDishesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nameUserLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(logoutBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(41, 41, 41)))
                        .addGap(24, 24, 24))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(welcomeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(66, 66, 66)
                        .addComponent(photoUserPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(69, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(photoUserPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)
                        .addComponent(nameUserLabel)
                        .addGap(18, 18, 18)
                        .addComponent(logoutBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 104, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(welcomeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(enterDishesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(58, 58, 58)))
                .addComponent(billButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(98, 98, 98))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void enterDishesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enterDishesButtonActionPerformed
        // TODO add your handling code here:
        DishesInputView dishesView = new DishesInputView(this,true,mainController,client);
        dishesView.setVisible(true);
        mainController.savePersistentData();
    }//GEN-LAST:event_enterDishesButtonActionPerformed

    private void logoutBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutBtnActionPerformed
        // TODO add your handling code here:
        LoginView login = new LoginView(mainController);
        login.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_logoutBtnActionPerformed

    private void billButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_billButtonActionPerformed
        // TODO add your handling code here:
     ArrayList<WorkOrder> clientOrders = mainController.getOrderController().getOrderDao().getOrdersByClient(client);
    
    ArrayList<WorkOrder> readyOrders = new ArrayList<>();
    for (WorkOrder order : clientOrders) {
        if (order.getOrderStatus() == OrderStatus.READY) {
            readyOrders.add(order);
        }
    }
    
    if (readyOrders.isEmpty()) {
        StringBuilder message = new StringBuilder();
        message.append("No hay órdenes listas para facturar.\n");
        message.append("Órdenes del cliente: ").append(clientOrders.size()).append("\n");
        message.append("Estados de las órdenes:\n");
        
        for (WorkOrder order : clientOrders) {
            message.append("- Orden #").append(order.getOrderNumber())
                  .append(": ").append(order.getOrderStatus().getDescription()).append("\n");
        }
        
        JOptionPane.showMessageDialog(this, 
            message.toString(),
            "Información detallada", 
            JOptionPane.INFORMATION_MESSAGE);
        return;
    }
    
    int billNumber = (int) System.currentTimeMillis() % 1000000;
    
    Bill newBill = new Bill(billNumber, client);
    for (WorkOrder order : readyOrders) {
        newBill.addOrder(order);
        order.setOrderStatus(OrderStatus.PAID);
    }
    newBill.calculateOverall();
    newBill.payBill(); 
    
    try {
        new File("facturas").mkdirs();
        
        String pdfPath = "facturas/Factura_" + billNumber + "_" + client.getDpi() + ".pdf";
        PDFManager.generateBill(newBill, pdfPath);
        
        mainController.savePersistentData();
        
        JOptionPane.showMessageDialog(this, 
            "Factura generada correctamente:\n" + pdfPath, 
            "Facturación exitosa", 
            JOptionPane.INFORMATION_MESSAGE);
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, 
            "Error al generar factura:\n" + e.getMessage(), 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
    }//GEN-LAST:event_billButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton billButton;
    private javax.swing.JButton enterDishesButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton logoutBtn;
    private javax.swing.JLabel nameUserLabel;
    private javax.swing.JPanel photoUserPanel;
    private javax.swing.JLabel welcomeLabel;
    // End of variables declaration//GEN-END:variables
}
