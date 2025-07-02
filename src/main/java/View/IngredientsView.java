
package View;
import Controller.MainController;
import Model.Ingredients;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class IngredientsView extends javax.swing.JDialog {

    private MainController mainController;
    private DefaultTableModel tableModel;
    public IngredientsView(java.awt.Frame parent, boolean modal,MainController mainController) {
        super(parent, modal);
        this.mainController = mainController;
        initComponents();
        setupTable();
        loadIngredients();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ingredientsTbl = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        createBtn = new javax.swing.JButton();
        updateBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 102, 0));

        ingredientsTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Ingrediente", "Marca", "Existencias", "Unidades", "Precio"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(ingredientsTbl);

        jLabel1.setText("Ingredients en stock");

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
                .addGap(58, 58, 58)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(116, 116, 116)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(createBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 138, Short.MAX_VALUE)
                        .addComponent(updateBtn)
                        .addGap(129, 129, 129))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(deleteBtn)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(createBtn)
                            .addComponent(updateBtn))
                        .addGap(71, 71, 71)
                        .addComponent(deleteBtn)))
                .addContainerGap(122, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void createBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createBtnActionPerformed
        // TODO add your handling code here:
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar archivo de ingredientes (.igd)");
        
        // Mostrar solo archivos .igd
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(java.io.File f) {
                return f.getName().toLowerCase().endsWith(".igd") || f.isDirectory();
            }

            @Override
            public String getDescription() {
                return "Archivos de ingredientes (*.igd)";
            }
        });

        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            
            // Usar FileController para cargar los ingredientes
            ArrayList<Ingredients> loadedIngredients = mainController.getFileController().loadIngredientsFromFile(filePath);
            
            if (loadedIngredients != null && !loadedIngredients.isEmpty()) {
                int addedCount = 0;
                for (Ingredients ingredient : loadedIngredients) {
                    if (mainController.getIngredientController().addIngredient(ingredient)) {
                        addedCount++;
                    }
                }
                
                JOptionPane.showMessageDialog(this, 
                    "Se agregaron " + addedCount + " ingredientes correctamente", 
                    "Resultado", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "No se pudieron cargar ingredientes del archivo", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_createBtnActionPerformed

    private void updateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBtnActionPerformed
        // TODO add your handling code here:
        int selectedRow = ingredientsTbl.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, 
            "Por favor seleccione un ingrediente", 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    int id = (int) ingredientsTbl.getValueAt(selectedRow, 0);
    UpdateIngredientView updateView = new UpdateIngredientView(
        null, 
        true, 
        mainController, 
        id
    );
    updateView.setVisible(true);
    refreshTable();
    }//GEN-LAST:event_updateBtnActionPerformed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        // TODO add your handling code here:
        int selectedRow = ingredientsTbl.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Por favor seleccione un ingrediente", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int id = (int) ingredientsTbl.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de eliminar este ingrediente?", 
            "Confirmar eliminación", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = mainController.getIngredientController().deleteIngredient(id);
            if (success) {
                JOptionPane.showMessageDialog(this, "Ingrediente eliminado correctamente");
                loadIngredients();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar el ingrediente", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_deleteBtnActionPerformed
    private void loadIngredients() {
        tableModel.setRowCount(0); // Limpiar tabla
        ArrayList<Ingredients> ingredients = mainController.getIngredientController().getAllIngredients();
        
        for (Ingredients ingredient : ingredients) {
            Object[] row = {
                ingredient.getIdentifier(),
                ingredient.getName(),
                ingredient.getBrand(),
                ingredient.getStock(),
                ingredient.getUnits(),
                ingredient.getPrice()
            };
            tableModel.addRow(row);
        }
    }
    
    private void setupTable() {
        tableModel = (DefaultTableModel) ingredientsTbl.getModel();
        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0); // Limpiar tabla
        ArrayList<Ingredients> ingredients = mainController.getIngredientController().getAllIngredients();
        
        for (Ingredients ingredient : ingredients) {
            tableModel.addRow(new Object[]{
                ingredient.getIdentifier(),
                ingredient.getName(),
                ingredient.getBrand(),
                ingredient.getStock(),
                ingredient.getUnits(),
                ingredient.getPrice()
            });
        }
    }

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton createBtn;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JTable ingredientsTbl;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton updateBtn;
    // End of variables declaration//GEN-END:variables
}
