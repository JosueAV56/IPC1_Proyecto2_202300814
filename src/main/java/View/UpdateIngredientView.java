
package View;
import Controller.MainController;
import Model.Ingredients;
import javax.swing.JOptionPane;


public class UpdateIngredientView extends javax.swing.JDialog {
    private MainController mainController;
    private int ingredientId;
    public UpdateIngredientView(java.awt.Frame parent, boolean modal,MainController mainController, int ingredientId) {
        super(parent, modal);
        this.mainController = mainController;
        this.ingredientId = ingredientId;
        initComponents();
        loadIngredientData();
        setLocationRelativeTo(parent);
    }
    private void loadIngredientData() {
        Ingredients ingredient = mainController.getIngredientController().getIngredientById(ingredientId);
        
        if (ingredient != null) {
            idTxt.setText(String.valueOf(ingredient.getIdentifier()));
            idTxt.setEditable(false); // El ID no se puede modificar
            IngredientTxt.setText(ingredient.getName());
            brandTxt.setText(ingredient.getBrand());
            stockTxt.setText(String.valueOf(ingredient.getStock()));
            unitsTxt.setText(ingredient.getUnits());
            priceTxt.setText(String.valueOf(ingredient.getPrice()));
        } else {
            JOptionPane.showMessageDialog(this, 
                "No se encontró el ingrediente con ID: " + ingredientId, 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            this.dispose();
        }
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        updateBtn = new javax.swing.JButton();
        idTxt = new javax.swing.JTextField();
        IngredientTxt = new javax.swing.JTextField();
        brandTxt = new javax.swing.JTextField();
        stockTxt = new javax.swing.JTextField();
        unitsTxt = new javax.swing.JTextField();
        priceTxt = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Id");

        jLabel2.setText("Ingrediente");

        jLabel3.setText("Marca");

        jLabel4.setText("Existencias");

        jLabel5.setText("Unidades");

        jLabel6.setText("Precio");

        updateBtn.setText("Modificar");
        updateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateBtnActionPerformed(evt);
            }
        });

        unitsTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unitsTxtActionPerformed(evt);
            }
        });

        priceTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                priceTxtActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(idTxt)
                            .addComponent(IngredientTxt)
                            .addComponent(brandTxt)
                            .addComponent(stockTxt)
                            .addComponent(unitsTxt)
                            .addComponent(priceTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(229, 229, 229)
                        .addComponent(updateBtn)))
                .addContainerGap(398, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(idTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(IngredientTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLabel3))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(brandTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(stockTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(unitsTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(priceTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(54, 54, 54)
                .addComponent(updateBtn)
                .addContainerGap(23, Short.MAX_VALUE))
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

    private void unitsTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unitsTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_unitsTxtActionPerformed

    private void priceTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_priceTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_priceTxtActionPerformed

    private void updateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBtnActionPerformed
        // TODO add your handling code here:
        try {
            // Validar campos obligatorios
            if (IngredientTxt.getText().isEmpty() || brandTxt.getText().isEmpty() || 
                stockTxt.getText().isEmpty() || unitsTxt.getText().isEmpty() || 
                priceTxt.getText().isEmpty()) {
                throw new IllegalArgumentException("Todos los campos son obligatorios");
            }
            
            // Crear objeto con los datos modificados
            Ingredients updatedIngredient = new Ingredients(
                ingredientId,
                IngredientTxt.getText(),
                brandTxt.getText(),
                Integer.parseInt(stockTxt.getText()),
                unitsTxt.getText(),
                Double.parseDouble(priceTxt.getText())
            );
            
            // Actualizar en el sistema
            boolean success = mainController.getIngredientController().updateIngredient(updatedIngredient);
            
            if (success) {
                JOptionPane.showMessageDialog(this, 
                    "Ingrediente actualizado correctamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Error al actualizar el ingrediente", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Existencias y Precio deben ser valores numéricos válidos", 
                "Error de formato", 
                JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, 
                e.getMessage(), 
                "Error de validación", 
                JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_updateBtnActionPerformed
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField IngredientTxt;
    private javax.swing.JTextField brandTxt;
    private javax.swing.JTextField idTxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField priceTxt;
    private javax.swing.JTextField stockTxt;
    private javax.swing.JTextField unitsTxt;
    private javax.swing.JButton updateBtn;
    // End of variables declaration//GEN-END:variables
}
