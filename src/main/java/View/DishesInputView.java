
package View;

import Controller.MainController;
import Model.Client;
import Model.Dishes;
import Model.OrderDao;
import Model.OrderStatus;
import Model.Threads.ProgressThread;
import Model.WorkOrder;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class DishesInputView extends javax.swing.JDialog {
    
    private MainController mainController;
    private Client currentClient;
    private ProgressThread progressThread;
    
    

    public DishesInputView(java.awt.Frame parent, boolean modal,MainController mainController,Client client) {
        super(parent,modal);
        this.mainController = mainController;
        this.currentClient = client;
        initComponents();
        setupProgressBars();
        configureTable();
        loadDishesComboBox();
        startProgressThread();
    }
    
     private void startProgressThread() {
        ProgressThread.ProgressUpdateListener listener = new ProgressThread.ProgressUpdateListener() {
            @Override
            public void onProgressUpdated(ArrayList<WorkOrder> orders) {
                System.out.println("[Listener] Actualizando tabla con " + orders.size() + " órdenes");
                SwingUtilities.invokeLater(() -> {
                    updateOrdersTable(orders);
                    updateBarsFromOrders(orders);
                    repaint(); // Forzar repintado
                });
            }

            @Override
            public void onPhaseProgressUpdate(int orderNumber, OrderStatus phase, int progress) {
                System.out.println("[Listener] Progreso individual: Orden#" + orderNumber + 
                                 " Fase: " + phase + " Progreso: " + progress + "%");
                SwingUtilities.invokeLater(() -> {
                    updateIndividualProgressBar(orderNumber, phase, progress);
                    repaint(); // Forzar repintado
                });
            }

            @Override
            public void onInvoicePending(WorkOrder order) {
                System.out.println("[Listener] Orden lista: #" + order.getOrderNumber());
                SwingUtilities.invokeLater(() -> showReadyNotification(order));
            }
        };

        // CORREGIDO: Agregar el cliente al ThreadManager ANTES de crear el ProgressThread
        mainController.getOrderController().getThreadManager().addClientProgress(currentClient, listener);
        
        System.out.println("[DishesInputView] ProgressThread y listener configurados para cliente: " + currentClient.getUser());
    }
    
    private void showReadyNotification(WorkOrder order) {
        JOptionPane.showMessageDialog(this, 
            "¡Tu orden #" + order.getOrderNumber() + " está lista para pagar!",
            "Pedido listo",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void updateBarsFromOrders(ArrayList<WorkOrder> orders) {
     int waitingCount = 0;
     int kitchenCount = 0;
     int readyCount = 0;
    
    if (orders != null) {
        for (WorkOrder order : orders) {
            switch (order.getOrderStatus()) {
                case QUEUE_WAIT: 
                    waitingCount++; 
                    break;
                case IN_KITCHEN: 
                    kitchenCount++; 
                    break;
                case READY: 
                    readyCount++; 
                    break;
            }
        }
    }
    
    final int finalWaitingCount = waitingCount;
    final int finalKitchenCount = kitchenCount;
    final int finalReadyCount = readyCount;
    
    
    // Solo actualizar texto cuando no hay progreso individual activo
    SwingUtilities.invokeLater(() -> {
        // Si no hay órdenes en espera, resetear barra
        if (finalWaitingCount == 0) {
            waitingQueueBar.setValue(0);
            waitingQueueBar.setString("Cola: Sin órdenes");
        } else if (waitingQueueBar.getString().contains("Sin órdenes")) {
            // Solo actualizar si no hay progreso individual activo
            waitingQueueBar.setString("Cola: " + finalWaitingCount + " órdenes");
        }
        
        // Si no hay órdenes en cocina, resetear barra
        if (finalKitchenCount == 0) {
            kitchenBar.setValue(0);
            kitchenBar.setString("Cocina: Sin órdenes");
        } else if (kitchenBar.getString().contains("Sin órdenes")) {
            // Solo actualizar si no hay progreso individual activo
            kitchenBar.setString("Cocina: " + finalKitchenCount + " órdenes");
        }
        
        // Para las órdenes listas
        if (finalReadyCount > 0) {
            if (!readyBar.getString().contains("Orden #")) {
                readyBar.setValue(100);
                readyBar.setString("Listo: " + finalReadyCount + " órdenes");
            }
        } else {
            readyBar.setValue(0);
            readyBar.setString("Listo: Sin órdenes");
        }
        
        // CORREGIDO: Forzar actualización visual
        waitingQueueBar.revalidate();
        waitingQueueBar.repaint();
        kitchenBar.revalidate();
        kitchenBar.repaint();
        readyBar.revalidate();
        readyBar.repaint();
    });
    
    System.out.println("[UI] Contadores actualizados - Cola: " + waitingCount + 
                     ", Cocina: " + kitchenCount + ", Listo: " + readyCount);
}
    
    
    private void updateOrdersTable(ArrayList<WorkOrder> orders) {
        DefaultTableModel model = (DefaultTableModel) ordersClientTable.getModel();
        model.setRowCount(0);
        
        if (orders != null) {
            for (WorkOrder order : orders) {
                model.addRow(new Object[]{
                    order.getOrderNumber(),
                    order.getDishes().getName(),
                    order.getOrderStatus().toString(),
                    order.getEmployee() != null ? order.getEmployee().getName() : "Sin asignar"
                });
            }
        }
    }
    
    private void updateIndividualProgressBar(int orderNumber, OrderStatus phase, int progress) {
    SwingUtilities.invokeLater(() -> {
        System.out.println("[UI] Actualizando barra - Orden: " + orderNumber + 
                         " | Fase: " + phase + " | Progreso: " + progress + "%");
        
        switch (phase) {
            case QUEUE_WAIT:
                waitingQueueBar.setValue(progress);
                waitingQueueBar.setString("Cola: " + progress + "% (Orden #" + orderNumber + ")");
                break;
            case IN_KITCHEN:
                kitchenBar.setValue(progress);
                kitchenBar.setString("Cocina: " + progress + "% (Orden #" + orderNumber + ")");
                break;
            case READY:
                readyBar.setValue(progress);
                readyBar.setString("Listo: " + progress + "% (Orden #" + orderNumber + ")");
                break;
        }
        
        // Forzar actualización visual
        waitingQueueBar.repaint();
        kitchenBar.repaint();
        readyBar.repaint();
    });
}
    private void setupProgressBars() {
    // Configuración de la barra de cola de espera
    waitingQueueBar.setMinimum(0);
    waitingQueueBar.setMaximum(100);
    waitingQueueBar.setStringPainted(true);
    waitingQueueBar.setValue(0);
    waitingQueueBar.setString("Cola: Sin órdenes");
    waitingQueueBar.setForeground(new java.awt.Color(255, 165, 0)); // Naranja

    // Configuración de la barra de cocina
    kitchenBar.setMinimum(0);
    kitchenBar.setMaximum(100);
    kitchenBar.setStringPainted(true);
    kitchenBar.setValue(0);
    kitchenBar.setString("Cocina: Sin órdenes");
    kitchenBar.setForeground(new java.awt.Color(255, 0, 0)); // Rojo

    // Configuración de la barra de listo
    readyBar.setMinimum(0);
    readyBar.setMaximum(100);
    readyBar.setStringPainted(true);
    readyBar.setValue(0);
    readyBar.setString("Listo: Sin órdenes");
    readyBar.setForeground(new java.awt.Color(0, 255, 0)); // Verde
    
    System.out.println("[UI] Barras de progreso inicializadas correctamente");
}
    
    @Override
    public void dispose() {
        System.out.println("[UI] Cerrando DishesInputView");
        
        // CORREGIDO: Remover el cliente del ThreadManager para limpiar el listener
        if (mainController != null && currentClient != null) {
            mainController.getOrderController().getThreadManager().removeClientProgress(currentClient.getDpi());
        }
        
        super.dispose();
    }
    
    private void configureTable() {
        // Configurar modelo de tabla
        ordersClientTable.setModel(new DefaultTableModel(
            new Object[][]{},
            new String[]{"N° Orden", "Platillo", "Estado", "Cocinero"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }

    private void loadDishesComboBox() {
        dishCmb.removeAllItems();
        ArrayList<Dishes> dishes = mainController.getDishController().getAllDishes();
        for (Dishes dish : dishes) {
            dishCmb.addItem(dish.getName());
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        returnButton = new javax.swing.JButton();
        dishCmb = new javax.swing.JComboBox<>();
        amountText = new javax.swing.JTextField();
        addOrderButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        ordersClientTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        waitingQueueBar = new javax.swing.JProgressBar();
        jLabel2 = new javax.swing.JLabel();
        kitchenBar = new javax.swing.JProgressBar();
        jLabel3 = new javax.swing.JLabel();
        readyBar = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        returnButton.setText("Regresar");
        returnButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                returnButtonActionPerformed(evt);
            }
        });
        jPanel1.add(returnButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 80, -1, -1));

        jPanel1.add(dishCmb, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 50, 210, -1));

        amountText.setText("Cantidad");
        jPanel1.add(amountText, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 50, 130, -1));

        addOrderButton.setText("Añadir Orden");
        addOrderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addOrderButtonActionPerformed(evt);
            }
        });
        jPanel1.add(addOrderButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 100, 130, 60));

        ordersClientTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Orden", "Platillo", "Estado", "Cocinero"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(ordersClientTable);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 200, 670, 310));

        jLabel1.setText("Cola de espera");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 520, -1, -1));
        jPanel1.add(waitingQueueBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 550, 740, 40));

        jLabel2.setText("Cocina");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 600, -1, -1));
        jPanel1.add(kitchenBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 630, 740, 40));

        jLabel3.setText("Listo");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 680, -1, -1));
        jPanel1.add(readyBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 710, 740, 50));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 896, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 827, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addOrderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addOrderButtonActionPerformed
        // TODO add your handling code here:
        try {
            String dishName = (String) dishCmb.getSelectedItem();
            int amount = Integer.parseInt(amountText.getText());
            
            Dishes selectedDish = mainController.getDishController().getDishesByName(dishName).get(0);
            
            for (int i = 0; i < amount; i++) {
                WorkOrder order = new WorkOrder(selectedDish, currentClient);
                mainController.getOrderController().createOrder(order);
            }
            
            JOptionPane.showMessageDialog(this, 
                amount + " órdenes de " + dishName + " creadas correctamente",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
            
            amountText.setText("1");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al crear la orden: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_addOrderButtonActionPerformed

    private void returnButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_returnButtonActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_returnButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addOrderButton;
    private javax.swing.JTextField amountText;
    private javax.swing.JComboBox<String> dishCmb;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JProgressBar kitchenBar;
    private javax.swing.JTable ordersClientTable;
    private javax.swing.JProgressBar readyBar;
    private javax.swing.JButton returnButton;
    private javax.swing.JProgressBar waitingQueueBar;
    // End of variables declaration//GEN-END:variables
}
