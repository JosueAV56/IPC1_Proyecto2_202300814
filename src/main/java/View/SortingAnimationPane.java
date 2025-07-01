package View;

import Model.Client;
import Model.Dishes;
import Model.Ingredients;
import Model.Managers.PDFManager;
import Model.OrderSpeed;
import Model.Utils.SortingAlgorithm;
import Model.Utils.SortingResults;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

public class SortingAnimationPane extends JPanel {
    private double[] values;
    private String[] labels;
    private Color[] colors;
    private int highlightIndex1 = -1;
    private int highlightIndex2 = -1;
    private String title;
    private SortingAlgorithm algorithm;
    private OrderSpeed speed;
    private boolean isAnimating = false;
    private Timer animationTimer;
    private JLabel statusLabel;
    private JLabel timeLabel;
    private long startTime;
    
    public SortingAnimationPane(String title) {
        this.title = title;
        setPreferredSize(new Dimension(800, 400));
        setBackground(Color.WHITE);
        
        // Panel para labels de estado
        JPanel statusPanel = new JPanel(new FlowLayout());
        statusLabel = new JLabel("Listo para ordenar");
        timeLabel = new JLabel("Tiempo: 0 ms");
        statusPanel.add(statusLabel);
        statusPanel.add(Box.createHorizontalStrut(20));
        statusPanel.add(timeLabel);
        
        setLayout(new BorderLayout());
        add(statusPanel, BorderLayout.SOUTH);
    }
    
    public void setData(double[] values, String[] labels, SortingAlgorithm algorithm, OrderSpeed speed) {
        this.values = values.clone();
        this.labels = labels.clone();
        this.algorithm = algorithm;
        this.speed = speed;
        
        // Generar colores aleatorios para cada barra
        this.colors = new Color[values.length];
        for (int i = 0; i < colors.length; i++) {
            colors[i] = new Color(
                (int)(Math.random() * 155) + 100,
                (int)(Math.random() * 155) + 100,
                (int)(Math.random() * 155) + 100
            );
        }
        
        highlightIndex1 = -1;
        highlightIndex2 = -1;
        repaint();
    }
    
    public void startSorting() {
        if (isAnimating || values == null) return;
        
        isAnimating = true;
        startTime = System.currentTimeMillis();
        statusLabel.setText("Ordenando con " + algorithm.getName() + "...");
        
        if (algorithm == SortingAlgorithm.BUBBLE_SORT) {
            startBubbleSort();
        } else if (algorithm == SortingAlgorithm.QUICK_SORT) {
            startQuickSort();
        }
    }
    
    private void startBubbleSort() {
        final int n = values.length;
        final boolean[] swapped = {true};
        final int[] currentI = {0};
        final int[] currentN = {n};
        
        animationTimer = new Timer(speed.getDelayMiliSeconds(), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!swapped[0]) {
                    // Terminó el ordenamiento
                    finishSorting();
                    return;
                }
                
                if (currentI[0] == 0) {
                    swapped[0] = false;
                }
                
                if (currentI[0] < currentN[0] - 1) {
                    highlightIndex1 = currentI[0];
                    highlightIndex2 = currentI[0] + 1;
                    
                    // Comparar y posiblemente intercambiar
                    if (values[currentI[0]] < values[currentI[0] + 1]) { // Orden descendente
                        swap(currentI[0], currentI[0] + 1);
                        swapped[0] = true;
                    }
                    
                    currentI[0]++;
                    repaint();
                } else {
                    // Termina una pasada
                    currentI[0] = 0;
                    currentN[0]--;
                }
            }
        });
        
        animationTimer.start();
    }
    
    private void startQuickSort() {
        // Para simplicidad, usamos una implementación iterativa simple
        final int[] stack = new int[values.length * 2];
        final int[] top = {-1};
        
        // Inicializar stack
        stack[++top[0]] = 0;
        stack[++top[0]] = values.length - 1;
        
        animationTimer = new Timer(speed.getDelayMiliSeconds(), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (top[0] < 0) {
                    finishSorting();
                    return;
                }
                
                // Pop del stack
                int high = stack[top[0]--];
                int low = stack[top[0]--];
                
                if (low < high) {
                    int pi = partition(low, high);
                    
                    // Push left part
                    if (pi - 1 > low) {
                        stack[++top[0]] = low;
                        stack[++top[0]] = pi - 1;
                    }
                    
                    // Push right part
                    if (pi + 1 < high) {
                        stack[++top[0]] = pi + 1;
                        stack[++top[0]] = high;
                    }
                }
                
                repaint();
            }
        });
        
        animationTimer.start();
    }
    
    private int partition(int low, int high) {
        double pivot = values[high];
        int i = low - 1;
        
        for (int j = low; j < high; j++) {
            highlightIndex1 = j;
            highlightIndex2 = high;
            
            if (values[j] >= pivot) { // Orden descendente
                i++;
                swap(i, j);
            }
        }
        
        swap(i + 1, high);
        return i + 1;
    }
    
    private void swap(int i, int j) {
        if (i == j) return;
        
        // Intercambiar valores
        double tempVal = values[i];
        values[i] = values[j];
        values[j] = tempVal;
        
        // Intercambiar labels
        String tempLabel = labels[i];
        labels[i] = labels[j];
        labels[j] = tempLabel;
        
        // Intercambiar colores
        Color tempColor = colors[i];
        colors[i] = colors[j];
        colors[j] = tempColor;
    }
    
    private void finishSorting() {
        isAnimating = false;
        highlightIndex1 = -1;
        highlightIndex2 = -1;
        
        if (animationTimer != null) {
            animationTimer.stop();
        }
        
        long endTime = System.currentTimeMillis();
        long elapsed = endTime - startTime;
        
        statusLabel.setText("Ordenamiento completado");
        timeLabel.setText("Tiempo: " + elapsed + " ms");
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (values == null || values.length == 0) {
            g.setColor(Color.BLACK);
            g.drawString("No hay datos para mostrar", 50, 50);
            return;
        }
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Dimensiones del área de dibujo
        int width = getWidth();
        int height = getHeight() - 50; // Dejar espacio para labels
        int margin = 50;
        int drawWidth = width - 2 * margin;
        int drawHeight = height - 2 * margin;
        
        // Encontrar valor máximo para escalar
        double maxValue = 0;
        for (double value : values) {
            if (value > maxValue) maxValue = value;
        }
        
        if (maxValue == 0) maxValue = 1; // Evitar división por cero
        
        // Dibujar título
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        FontMetrics fm = g2d.getFontMetrics();
        int titleWidth = fm.stringWidth(title);
        g2d.drawString(title, (width - titleWidth) / 2, 25);
        
        // Calcular dimensiones de las barras
        int barWidth = Math.max(10, drawWidth / values.length - 5);
        int barSpacing = 5;
        int totalBarWidth = values.length * barWidth + (values.length - 1) * barSpacing;
        int startX = margin + (drawWidth - totalBarWidth) / 2;
        
        // Dibujar barras
        for (int i = 0; i < values.length; i++) {
            int x = startX + i * (barWidth + barSpacing);
            int barHeight = (int) ((values[i] / maxValue) * drawHeight);
            int y = margin + drawHeight - barHeight;
            
            // Determinar color de la barra
            Color barColor = colors[i];
            if (i == highlightIndex1 || i == highlightIndex2) {
                barColor = Color.RED; // Destacar barras siendo comparadas
            }
            
            // Dibujar barra
            g2d.setColor(barColor);
            g2d.fillRect(x, y, barWidth, barHeight);
            
            // Dibujar borde
            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, y, barWidth, barHeight);
            
            // Dibujar valor encima de la barra
            g2d.setFont(new Font("Arial", Font.PLAIN, 10));
            String valueStr = String.format("%.1f", values[i]);
            int valueWidth = g2d.getFontMetrics().stringWidth(valueStr);
            g2d.drawString(valueStr, x + (barWidth - valueWidth) / 2, y - 5);
            
            // Dibujar label debajo de la barra
            String label = labels[i];
            if (label.length() > 8) {
                label = label.substring(0, 8) + "...";
            }
            int labelWidth = g2d.getFontMetrics().stringWidth(label);
            g2d.drawString(label, x + (barWidth - labelWidth) / 2, 
                          margin + drawHeight + 15);
        }
        
        // Dibujar ejes
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        
        // Eje Y
        g2d.drawLine(margin, margin, margin, margin + drawHeight);
        
        // Eje X
        g2d.drawLine(margin, margin + drawHeight, margin + drawWidth, margin + drawHeight);
    }
    
    public boolean isAnimating() {
        return isAnimating;
    }
    
    public void stopAnimation() {
        if (animationTimer != null) {
            animationTimer.stop();
        }
        isAnimating = false;
        highlightIndex1 = -1;
        highlightIndex2 = -1;
        repaint();
    }
    
}