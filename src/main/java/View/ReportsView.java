
package View;

import Controller.MainController;
import Model.Client;
import Model.Dishes;
import Model.IngredientDao;
import Model.Ingredients;
import Model.OrderSpeed;
import Model.TypeClient;
import Model.Utils.SortingAlgorithm;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class ReportsView extends javax.swing.JDialog {
    
    private final MainController mainController;
    private ChartPanel currentChartPanel;
    private SortingAnimationPane animationPane;
    private JFreeChart currentChart; 
    private long sortingTime = 0;
    public ReportsView(java.awt.Frame parent, boolean modal,MainController mainController) {
        super(parent, modal);
        this.mainController = mainController;
        initComponents();
        setupComponents();
    }
    private void setupComponents() {
        typeCmb.removeAllItems();
        typeCmb.addItem("Clientes por tipo");
        typeCmb.addItem("Top 10 ingredientes más usados");
        typeCmb.addItem("Top 10 ingredientes más caros");
        typeCmb.addItem("Top 10 platillos más pedidos");
        typeCmb.addItem("Top 5 clientes con más pedidos");
        
        sortingAlgorithmCmb.removeAllItems();
        for (SortingAlgorithm algo : SortingAlgorithm.values()) {
            sortingAlgorithmCmb.addItem(algo);
        }
        
        speedCmb.removeAllItems();
        for (OrderSpeed speed : OrderSpeed.values()) {
            speedCmb.addItem(speed);
        }
        
        graficPane.setLayout(new BorderLayout());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        graficPane = new javax.swing.JPanel();
        pdfButton = new javax.swing.JButton();
        speedCmb = new javax.swing.JComboBox<>();
        typeCmb = new javax.swing.JComboBox<>();
        returnButton = new javax.swing.JButton();
        reportButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        sortingAlgorithmCmb = new javax.swing.JComboBox<>();
        timeLbl = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        pdfButton.setText("Exportar PDF");
        pdfButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pdfButtonActionPerformed(evt);
            }
        });

        typeCmb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typeCmbActionPerformed(evt);
            }
        });

        returnButton.setText("Regresar");
        returnButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                returnButtonActionPerformed(evt);
            }
        });

        reportButton.setText("Generar Reporte");
        reportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reportButtonActionPerformed(evt);
            }
        });

        jLabel2.setText("Velocidad");

        jLabel1.setText("Tipo");

        jLabel3.setText("Ordenamiento");

        timeLbl.setText("Tiempo:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(returnButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(212, 212, 212))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(75, 75, 75)
                        .addComponent(graficPane, javax.swing.GroupLayout.PREFERRED_SIZE, 825, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(pdfButton))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(103, 103, 103)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(typeCmb, 0, 275, Short.MAX_VALUE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sortingAlgorithmCmb, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(219, 219, 219)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(speedCmb, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(reportButton)
                            .addComponent(timeLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(293, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(returnButton, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(speedCmb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(typeCmb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(timeLbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(reportButton)
                    .addComponent(sortingAlgorithmCmb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(51, 51, 51)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(graficPane, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(pdfButton)
                        .addGap(140, 140, 140))))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pdfButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pdfButtonActionPerformed
        // TODO add your handling code here:
        try {
            if (currentChart == null) {
                JOptionPane.showMessageDialog(this, 
                    "Primero debe generar un reporte para poder exportarlo", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar reporte como PDF");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos PDF (*.pdf)", "pdf"));
            fileChooser.setSelectedFile(new File("reporte.pdf"));
            
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                String filePath = file.getAbsolutePath();
                
                if (!filePath.toLowerCase().endsWith(".pdf")) {
                    filePath += ".pdf";
                }
                
                generateReportPDF(filePath);
                JOptionPane.showMessageDialog(this, "Reporte generado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al generar el PDF: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_pdfButtonActionPerformed
    
    private void typeCmbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_typeCmbActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_typeCmbActionPerformed

    private void reportButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reportButtonActionPerformed
        // TODO add your handling code here:
        generateReport();
    }//GEN-LAST:event_reportButtonActionPerformed

    private void returnButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_returnButtonActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_returnButtonActionPerformed
    private void generateReportPDF(String filePath) {
        try {
            String reportType = (String) typeCmb.getSelectedItem();
            SortingAlgorithm algorithm = (SortingAlgorithm) sortingAlgorithmCmb.getSelectedItem();
            OrderSpeed speed = (OrderSpeed) speedCmb.getSelectedItem();
            
            switch (reportType) {
                case "Clientes por tipo":
                    mainController.getReportController().generateClientReportWithChart(
                        new ArrayList<>(mainController.getClientController().getAllClients()),
                        filePath, algorithm, speed, currentChart, sortingTime);
                    break;
                case "Top 10 ingredientes más usados":
                    mainController.getReportController().generateMostUsedIngredientsReportWithChart(
                        new ArrayList<>(mainController.getIngredientController().getAllIngredients()),
                        filePath, algorithm, speed, currentChart, sortingTime);
                    break;
                case "Top 10 ingredientes más caros":
                    mainController.getReportController().generateMostExpensiveIngredientsReportWithChart(
                        new ArrayList<>(mainController.getIngredientController().getAllIngredients()),
                        filePath, algorithm, speed, currentChart, sortingTime);
                    break;
                case "Top 10 platillos más pedidos":
                    mainController.getReportController().generateMostOrderedDishesReportWithChart(
                        new ArrayList<>(mainController.getDishController().getAllDishes()),
                        filePath, algorithm, speed, currentChart, sortingTime);
                    break;
                case "Top 5 clientes con más pedidos":
                    mainController.getReportController().generateTop5ClientsReportWithChart(
                        new ArrayList<>(mainController.getClientController().getAllClients()),
                        filePath, algorithm, speed, currentChart, sortingTime);
                    break;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error generando PDF: " + e.getMessage(), e);
        }
    }
    private void generateReport() {
        try {
            String reportType = (String) typeCmb.getSelectedItem();
            SortingAlgorithm algorithm = (SortingAlgorithm) sortingAlgorithmCmb.getSelectedItem();
            OrderSpeed speed = (OrderSpeed) speedCmb.getSelectedItem();
            
            if (reportType == null || algorithm == null || speed == null) {
                JOptionPane.showMessageDialog(this, 
                    "Por favor seleccione todos los parámetros necesarios", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            switch (reportType) {
                case "Top 10 ingredientes más usados":
                    showMostUsedIngredientsChart(algorithm, speed);
                    break;
                case "Top 10 ingredientes más caros":
                    showMostExpensiveIngredientsChart(algorithm, speed);
                    break;
                case "Top 10 platillos más pedidos":
                    showMostOrderedDishesChart(algorithm, speed);
                    break;
                case "Top 5 clientes con más pedidos":
                    showTop5ClientsChart(algorithm, speed);
                    break;
                case "Clientes por tipo":
                    showClientsByTypeChart(algorithm, speed);
                    break;
                default:
                    JOptionPane.showMessageDialog(this, 
                        "Tipo de reporte no reconocido", 
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al generar el reporte: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void showMostUsedIngredientsChart(SortingAlgorithm algorithm, OrderSpeed speed) {
        try {
            IngredientDao dao = mainController.getIngredientController().getIngredientDao();
            ArrayList<Ingredients> ingredients = dao.getAll();
            
            if (ingredients.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay ingredientes para mostrar", 
                                            "Sin datos", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            double[] values = new double[ingredients.size()];
            String[] labels = new String[ingredients.size()];
            
            for (int i = 0; i < ingredients.size(); i++) {
                values[i] = dao.getUsageCount(ingredients.get(i).getIdentifier());
                labels[i] = ingredients.get(i).getName();
            }
            
            showSortingAnimation(values, labels, algorithm, speed, "Top 10 Ingredientes Más Usados");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void showMostExpensiveIngredientsChart(SortingAlgorithm algorithm, OrderSpeed speed) {
        try {
            ArrayList<Ingredients> ingredients = new ArrayList<>(mainController.getIngredientController().getAllIngredients());
            
            if (ingredients.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay ingredientes para mostrar", 
                                            "Sin datos", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            double[] values = new double[ingredients.size()];
            String[] labels = new String[ingredients.size()];
            
            for (int i = 0; i < ingredients.size(); i++) {
                values[i] = ingredients.get(i).getPrice();
                labels[i] = ingredients.get(i).getName();
            }
            
            showSortingAnimation(values, labels, algorithm, speed, "Top 10 Ingredientes Más Caros");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showMostOrderedDishesChart(SortingAlgorithm algorithm, OrderSpeed speed) {
        try {
            ArrayList<Dishes> dishes = new ArrayList<>(mainController.getDishController().getAllDishes());
            
            if (dishes.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay platillos para mostrar", 
                                            "Sin datos", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            double[] values = new double[dishes.size()];
            String[] labels = new String[dishes.size()];
            
            for (int i = 0; i < dishes.size(); i++) {
                values[i] = dishes.get(i).getOrderCounter();
                labels[i] = dishes.get(i).getName();
            }
            
            showSortingAnimation(values, labels, algorithm, speed, "Top 10 Platillos Más Pedidos");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showTop5ClientsChart(SortingAlgorithm algorithm, OrderSpeed speed) {
        try {
            ArrayList<Client> clients = new ArrayList<>(mainController.getClientController().getAllClients());
            
            if (clients.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay clientes para mostrar", 
                                            "Sin datos", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            double[] values = new double[clients.size()];
            String[] labels = new String[clients.size()];
            
            for (int i = 0; i < clients.size(); i++) {
                values[i] = clients.get(i).getPaidDishes();
                labels[i] = clients.get(i).getCompleteName();
            }
            
            showSortingAnimation(values, labels, algorithm, speed, "Top 5 Clientes con Más Pedidos");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showClientsByTypeChart(SortingAlgorithm algorithm, OrderSpeed speed) {
        try {
            int goldClients = mainController.getClientController().countClientsByType(TypeClient.GOLD);
            int normalClients = mainController.getClientController().countClientsByType(TypeClient.COMMUN);
            
            if (goldClients == 0 && normalClients == 0) {
                JOptionPane.showMessageDialog(this, 
                    "No hay clientes registrados para mostrar", 
                    "Sin datos", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            DefaultPieDataset dataset = new DefaultPieDataset();
            dataset.setValue("Clientes Oro", goldClients);
            dataset.setValue("Clientes Normales", normalClients);
            
            JFreeChart chart = ChartFactory.createPieChart(
                "Distribución de Clientes por Tipo", 
                dataset, 
                true, true, false);
            
            PiePlot plot = (PiePlot) chart.getPlot();
            plot.setSectionPaint("Clientes Oro", new Color(255, 215, 0));
            plot.setSectionPaint("Clientes Normales", new Color(0, 100, 200));
            plot.setBackgroundPaint(Color.WHITE);
            
            currentChart = chart;
            displayChart(chart);
            
            sortingTime = speed.getDelayMiliSeconds() * 2;
            timeLbl.setText("Tiempo de procesamiento: " + sortingTime + " ms");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al generar gráfica de clientes: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void showSortingAnimation(double[] values, String[] labels, SortingAlgorithm algorithm, 
                                    OrderSpeed speed, String title) {
        graficPane.removeAll();
        
        animationPane = new SortingAnimationPane(title);
        animationPane.setData(values, labels, algorithm, speed);
        
        graficPane.add(animationPane, BorderLayout.CENTER);
        graficPane.revalidate();
        graficPane.repaint();
        
        long startTime = System.currentTimeMillis();
        animationPane.startSorting();
        
        Timer timer = new Timer(100, null);
        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!animationPane.isAnimating()) {
                    timer.stop();
                    sortingTime = System.currentTimeMillis() - startTime;
                    timeLbl.setText("Tiempo de procesamiento: " + sortingTime + " ms");
                    
                    Timer delayTimer = new Timer(1000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            ((Timer)e.getSource()).stop();
                            createFinalChart(values, labels, title);
                        }
                    });
                    delayTimer.setRepeats(false);
                    delayTimer.start();
                }
            }
        });
        timer.start();
    }
    
    private void createFinalChart(double[] values, String[] labels, String title) {
    double[] sortedValues = animationPane.getSortedValues();
    String[] sortedLabels = animationPane.getSortedLabels();
    
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    
    int limit = title.contains("Top 5") ? Math.min(5, sortedValues.length) : Math.min(10, sortedValues.length);
    
    for (int i = 0; i < limit; i++) {
        dataset.addValue(sortedValues[i], "Valor", sortedLabels[i]);
    }
    
    JFreeChart chart = ChartFactory.createBarChart(
        title,
        "Elemento", "Valor",
        dataset, PlotOrientation.VERTICAL,
        true, true, false
    );
    
    CategoryPlot plot = chart.getCategoryPlot();
    plot.setBackgroundPaint(Color.WHITE);
    BarRenderer renderer = (BarRenderer) plot.getRenderer();
    
    if (title.contains("Ingredientes Más Usados")) {
        renderer.setSeriesPaint(0, new Color(75, 192, 192));
    } else if (title.contains("Ingredientes Más Caros")) {
        renderer.setSeriesPaint(0, new Color(192, 80, 77));
    } else if (title.contains("Platillos")) {
        renderer.setSeriesPaint(0, new Color(155, 187, 89));
    } else {
        renderer.setSeriesPaint(0, new Color(128, 100, 162));
    }
    
    currentChart = chart;
    displayChart(chart);
    }

    private void displayChart(JFreeChart chart) {
        try {
            if (currentChartPanel != null) {
                graficPane.remove(currentChartPanel);
            }
            
            currentChartPanel = new ChartPanel(chart);
            currentChartPanel.setPreferredSize(new Dimension(800, 400));
            currentChartPanel.setMouseWheelEnabled(true);
            currentChartPanel.setMouseZoomable(true);
            
            graficPane.removeAll();
            graficPane.add(currentChartPanel, BorderLayout.CENTER);
            
            graficPane.revalidate();
            graficPane.repaint();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al mostrar la gráfica: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel graficPane;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton pdfButton;
    private javax.swing.JButton reportButton;
    private javax.swing.JButton returnButton;
    private javax.swing.JComboBox<Object> sortingAlgorithmCmb;
    private javax.swing.JComboBox<Object> speedCmb;
    private javax.swing.JLabel timeLbl;
    private javax.swing.JComboBox<String> typeCmb;
    // End of variables declaration//GEN-END:variables
}
