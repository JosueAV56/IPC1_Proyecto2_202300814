
package View;

import Controller.MainController;
import Model.Dishes;
import Model.Ingredients;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class DishesView extends javax.swing.JDialog {
    private final MainController mainController;
    private DefaultTableModel tableModel;
    public DishesView(java.awt.Frame parent, boolean modal,MainController mainController) {
        super(parent, modal);
        this.mainController = mainController;
        initComponents();
        initTable();
        loadDishes();
    }
    private void initTable() {
        tableModel = (DefaultTableModel) dishesTbl.getModel();
        dishesTbl.getTableHeader().setReorderingAllowed(false);
    }
    
    private void loadDishes() {
    tableModel.setRowCount(0); 
    mainController.getDishController().calculateDishesPrices();
    
    ArrayList<Dishes> dishes = mainController.getDishController().getAllDishes();
    
    for (Dishes dish : dishes) {
        String ingredientsList = String.join(";", dish.getIngredientsId()
            .stream()
            .map(String::valueOf)
            .toArray(String[]::new));
        
        Object[] row = {
            dish.getIdentifier(),
            dish.getName(),
            ingredientsList,
            dish.getLaborPrice(),
            dish.getTotalPrice() 
        };
        tableModel.addRow(row);
    }
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        dishesTbl = new javax.swing.JTable();
        createBtn = new javax.swing.JButton();
        updateBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 153));

        jLabel1.setText("Platillos en Stock");

        dishesTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Id", "Platillo", "Ingredientes", "Mano de Obra", "Precio"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(dishesTbl);

        createBtn.setText("Agregar");
        createBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createBtnActionPerformed(evt);
            }
        });

        updateBtn.setText("Modificar");
        updateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateBtnActionPerformed(evt);
            }
        });

        deleteBtn.setText("Eliminar");
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(109, 109, 109)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(createBtn)
                                .addGap(135, 135, 135)
                                .addComponent(updateBtn))
                            .addComponent(deleteBtn))))
                .addContainerGap(147, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(createBtn)
                            .addComponent(updateBtn))
                        .addGap(63, 63, 63)
                        .addComponent(deleteBtn)))
                .addContainerGap(95, Short.MAX_VALUE))
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

    private void createBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createBtnActionPerformed
        // TODO add your handling code here:
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Seleccionar archivo de platillos");
    fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos de platillos (*.pltll)", "pltll"));
    
    if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
        String filePath = fileChooser.getSelectedFile().getAbsolutePath();
        ArrayList<Ingredients> allIngredients = mainController.getIngredientController().getAllIngredients();
        ArrayList<Dishes> validDishes = new ArrayList<>();
        StringBuilder errorLog = new StringBuilder();

        try {
            ArrayList<Dishes> dishes = mainController.getFileController().loadDishesFromFile(filePath, allIngredients);
            
            for (Dishes dish : dishes) {
                boolean allIngredientsExist = true;
                for (Integer ingId : dish.getIngredientsId()) {
                    if (mainController.getIngredientController().getIngredientById(ingId) == null) {
                        errorLog.append("Platillo '").append(dish.getName())
                               .append("' - Ingrediente ID ").append(ingId)
                               .append(" no existe\n");
                        allIngredientsExist = false;
                        break;
                    }
                }
                if (allIngredientsExist) {
                    validDishes.add(dish);
                }
            }

            if (!validDishes.isEmpty()) {
                int createdCount = 0;
                for (Dishes dish : validDishes) {
                    if (mainController.getDishController().addDish(dish)) {
                        createdCount++;
                    }
                }
                JOptionPane.showMessageDialog(this, 
                    createdCount + " platillos creados exitosamente\n" +
                    (dishes.size() - validDishes.size()) + " platillos omitidos\n\n" +
                    "Errores:\n" + errorLog.toString(),
                    "Resultado", JOptionPane.INFORMATION_MESSAGE);
                loadDishes();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "No se pudo crear ningún platillo. Verifique los ingredientes.\n\n" +
                    "Errores:\n" + errorLog.toString(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al leer el archivo: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    }                                         
//GEN-LAST:event_createBtnActionPerformed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        // TODO add your handling code here:
        int selectedRow = dishesTbl.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un platillo", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int id = (int) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, 
                "¿Está seguro de eliminar este platillo?", 
                "Confirmar eliminación", 
                JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (mainController.getDishController().deleteDish(id)) {
                JOptionPane.showMessageDialog(this, "Platillo eliminado correctamente");
                loadDishes();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar platillo", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }    
    }//GEN-LAST:event_deleteBtnActionPerformed

    private void updateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBtnActionPerformed
        // TODO add your handling code here:
        int selectedRow = dishesTbl.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un platillo", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int id = (int) tableModel.getValueAt(selectedRow, 0);
        UpdateDishView updateView = new UpdateDishView(null, true, mainController, id);
        updateView.setVisible(true);
        loadDishes(); 
        mainController.savePersistentData();
    }//GEN-LAST:event_updateBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton createBtn;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JTable dishesTbl;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton updateBtn;
    // End of variables declaration//GEN-END:variables
}
