
package View;
import Controller.MainController;
import Model.OrderStatus;
import Model.WorkOrder;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class DishesProgressView extends javax.swing.JFrame {

    private MainController mainController;
    private javax.swing.Timer refreshTimer;
    public DishesProgressView(MainController mainController ) {
        this.mainController = mainController;
        initComponents();
        setupTables();
        startAutoRefresh();
    }
    private void setupTables() {
        // Configurar modelos de tabla
        queueWaitTable.setModel(createTableModel(new String[]{"Orden", "Platillo", "Cliente", "Tipo Cliente"}));
        queueKitchenTable.setModel(createTableModel(new String[]{"Orden", "Platillo", "Cliente", "Chef Asignado"}));
        readyOrdersTable.setModel(createTableModel(new String[]{"Orden", "Platillo", "Cliente", "Total", "Atendió", "Fecha y hora"}));
        
        // Actualizar datos iniciales
        refreshTables();
    }
    
    private DefaultTableModel createTableModel(String[] columns) {
        return new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Todas las celdas no editables
            }
        };
    }
    
    private void startAutoRefresh() {
        // Actualizar cada 5 segundos (5000 ms)
        refreshTimer = new javax.swing.Timer(5000, e -> refreshTables());
        refreshTimer.start();
    }
    
    private void refreshTables() {
        refreshWaitingOrders();
        refreshKitchenOrders();
        refreshReadyOrders();
    }
    
    private void refreshWaitingOrders() {
        DefaultTableModel model = (DefaultTableModel) queueWaitTable.getModel();
        model.setRowCount(0); // Limpiar tabla
        
        ArrayList<WorkOrder> waitingOrders = mainController.getOrderController().getOrdersByStatus(OrderStatus.QUEUE_WAIT);
        
        for (WorkOrder order : waitingOrders) {
            model.addRow(new Object[]{
                order.getOrderNumber(),
                order.getDishes() != null ? order.getDishes().getName() : "N/A",
                order.getClient() != null ? order.getClient().getCompleteName() : "N/A",
                order.hasGoldClient() ? "Oro" : "Normal"
            });
        }
    }
    
    private void refreshKitchenOrders() {
        DefaultTableModel model = (DefaultTableModel) queueKitchenTable.getModel();
        model.setRowCount(0); // Limpiar tabla
        
        ArrayList<WorkOrder> kitchenOrders = mainController.getOrderController().getOrdersByStatus(OrderStatus.IN_KITCHEN);
        
        for (WorkOrder order : kitchenOrders) {
            model.addRow(new Object[]{
                order.getOrderNumber(),
                order.getDishes() != null ? order.getDishes().getName() : "N/A",
                order.getClient() != null ? order.getClient().getCompleteName() : "N/A",
                order.getEmployee() != null ? order.getEmployee().getName() : "N/A"
            });
        }
    }
    
    private void refreshReadyOrders() {
        DefaultTableModel model = (DefaultTableModel) readyOrdersTable.getModel();
        model.setRowCount(0); // Limpiar tabla
        
        ArrayList<WorkOrder> readyOrders = mainController.getOrderController().getOrdersByStatus(OrderStatus.READY);
        
        for (WorkOrder order : readyOrders) {
            model.addRow(new Object[]{
                order.getOrderNumber(),
                order.getDishes() != null ? order.getDishes().getName() : "N/A",
                order.getClient() != null ? order.getClient().getCompleteName() : "N/A",
                order.getDishes() != null ? String.format("Q%.2f", order.getDishes().getTotalPrice()) : "N/A",
                order.getEmployee() != null ? order.getEmployee().getName() : "N/A",
                order.getFormattedDate()
            });
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        backButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        queueWaitTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        queueKitchenTable = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        readyOrdersTable = new javax.swing.JTable();
        refreshBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        backButton.setText("Regresar");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Cola de Espera");

        jLabel2.setText("Cola de Cocina");

        queueWaitTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Orden", "Platillo", "Cliente"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(queueWaitTable);

        queueKitchenTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Orden", "Platillo", "Cliente"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(queueKitchenTable);

        jLabel3.setText("Ordenes Listas");

        readyOrdersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Orden", "Platillo", "Cliente", "Total", "Atendio", "Fecha y hora"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(readyOrdersTable);

        refreshBtn.setText("refresh");
        refreshBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(132, 132, 132))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(84, 84, 84)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(415, 415, 415)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(301, 301, 301)
                                .addComponent(refreshBtn))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 937, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(88, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(refreshBtn, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        // TODO add your handling code here:
        if (refreshTimer != null) {
            refreshTimer.stop();
        }
        this.dispose();
    }//GEN-LAST:event_backButtonActionPerformed

    private void refreshBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshBtnActionPerformed
        // TODO add your handling code here:
        refreshTables();
    }//GEN-LAST:event_refreshBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable queueKitchenTable;
    private javax.swing.JTable queueWaitTable;
    private javax.swing.JTable readyOrdersTable;
    private javax.swing.JButton refreshBtn;
    // End of variables declaration//GEN-END:variables
}
