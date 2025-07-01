
package View;
import Controller.MainController;
import Model.Dishes;
import Model.Ingredients;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class UpdateDishView extends javax.swing.JDialog {
    
    private MainController mainController;
    private int id;
    private Dishes currentDish;
    public UpdateDishView(java.awt.Frame parent, boolean modal,MainController mainController,int id) {
        super(parent, modal);
        this.mainController = mainController;
        this.id = id;
        initComponents();
        loadDishData();
    }
    private void loadDishData() {
        currentDish = mainController.getDishController().getDishById(id);
        if (currentDish != null) {
            idTxt.setText(String.valueOf(currentDish.getIdentifier()));
            dishNameTxt.setText(currentDish.getName());
            
            // Convertir lista de IDs a string separado por ;
            String ingredientsList = String.join(";", 
                currentDish.getIngredientsId()
                    .stream()
                    .map(String::valueOf)
                    .toArray(String[]::new));
            ingredientsTxt.setText(ingredientsList);
            
            labourPriceTxt.setText(String.valueOf(currentDish.getLaborPrice()));
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
        updateBtn = new javax.swing.JButton();
        idTxt = new javax.swing.JTextField();
        dishNameTxt = new javax.swing.JTextField();
        ingredientsTxt = new javax.swing.JTextField();
        labourPriceTxt = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Actualizar Platillo");

        jLabel2.setText("Identificador");

        jLabel3.setText("Nombre Platillo");

        jLabel4.setText("Lista Ingredientes");

        jLabel5.setText("Precio Mano de Obra");

        updateBtn.setText("Modificar");
        updateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(labourPriceTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(30, 30, 30)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ingredientsTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 740, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(dishNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(idTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 515, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(391, 391, 391)
                        .addComponent(updateBtn)))
                .addContainerGap(73, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(idTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(62, 62, 62)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(dishNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(55, 55, 55)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(ingredientsTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labourPriceTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                .addComponent(updateBtn)
                .addGap(100, 100, 100))
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

    private void updateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBtnActionPerformed
        // TODO add your handling code here:
        try {
        String name = dishNameTxt.getText();
        String[] ingredientIds = ingredientsTxt.getText().split(";");
        double laborPrice = Double.parseDouble(labourPriceTxt.getText());
        
        // Validar ingredientes
        ArrayList<Integer> validIngredientIds = new ArrayList<>();
        StringBuilder invalidIngredients = new StringBuilder();
        
        for (String idStr : ingredientIds) {
            int id = Integer.parseInt(idStr.trim());
            Ingredients ing = mainController.getIngredientController().getIngredientById(id);
            if (ing == null) {
                invalidIngredients.append("ID ").append(id).append(", ");
            } else {
                validIngredientIds.add(id);
            }
        }
        
        if (!invalidIngredients.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Ingredientes no encontrados: " + invalidIngredients.substring(0, invalidIngredients.length()-2),
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Actualizar platillo
        currentDish.setName(name);
        currentDish.setIngredientsId(validIngredientIds);
        currentDish.setLaborPrice(laborPrice);
        
        // Calcular precio con ingredientes actuales
        ArrayList<Ingredients> ingredients = mainController.getIngredientController()
            .getIngredientsByIds(validIngredientIds);
        currentDish.calculateTotalPrice(ingredients);
        
        if (mainController.getDishController().updateDish(currentDish)) {
            JOptionPane.showMessageDialog(this, "Platillo actualizado correctamente");
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar platillo", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, 
            "Formato incorrecto en precio o IDs de ingredientes", 
            "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_updateBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField dishNameTxt;
    private javax.swing.JTextField idTxt;
    private javax.swing.JTextField ingredientsTxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField labourPriceTxt;
    private javax.swing.JButton updateBtn;
    // End of variables declaration//GEN-END:variables
}
