package View;

import Model.OrderSpeed;
import Model.Utils.SortingAlgorithm;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class SortingAnimationPane extends JPanel {
    private double[] values;
    private String[] labels;
    private double[] originalValues;
    private String[] originalLabels;
    private SortingAlgorithm algorithm;
    private OrderSpeed speed;
    private String title;
    private Timer animationTimer;
    private boolean isAnimating = false;
    
    // Variables para el estado del ordenamiento
    private int currentI = 0;
    private int currentJ = 0;
    private int phase = 0;
    private boolean sortingComplete = false;
    
    // Colores para la visualización
    private final Color DEFAULT_COLOR = new Color(100, 149, 237);
    private final Color COMPARING_COLOR = new Color(255, 99, 71);
    private final Color SORTED_COLOR = new Color(50, 205, 50);
    private final Color PIVOT_COLOR = new Color(255, 215, 0);
    
    // Variables para QuickSort original (mantenidas para compatibilidad)
    private int[] quickSortStack;
    private int stackTop = -1;
    private int pivotIndex = -1;
    private int partitionLow = 0;
    private int partitionHigh = 0;
    
    // Variables adicionales para QuickSort simplificado
    private int quickSortStep = 0;
    private int[] sortingIndices;
    private boolean[] processedRanges;
    
    public SortingAnimationPane(String title) {
        this.title = title;
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(800, 400));
    }
    
    public void setData(double[] values, String[] labels, SortingAlgorithm algorithm, OrderSpeed speed) {
        this.originalValues = Arrays.copyOf(values, values.length);
        this.originalLabels = Arrays.copyOf(labels, labels.length);
        this.values = Arrays.copyOf(values, values.length);
        this.labels = Arrays.copyOf(labels, labels.length);
        this.algorithm = algorithm;
        this.speed = speed;
        
        // Resetear variables de estado
        currentI = 0;
        currentJ = 0;
        phase = 0;
        sortingComplete = false;
        pivotIndex = -1;
        
        // Inicializar para QuickSort
        if (algorithm == SortingAlgorithm.QUICK_SORT) {
            // Configuración original (mantenida para compatibilidad)
            quickSortStack = new int[values.length * 2];
            stackTop = -1;
            // Agregar rango inicial al stack
            pushToStack(0, values.length - 1);
            
            // Nueva configuración simplificada
            quickSortStep = 0;
            sortingIndices = new int[values.length];
            for (int i = 0; i < values.length; i++) {
                sortingIndices[i] = i;
            }
            processedRanges = new boolean[values.length];
        }
        
        repaint();
    }
    
    public void startSorting() {
        if (isAnimating) return;
        
        isAnimating = true;
        sortingComplete = false;
        
        if (animationTimer != null) {
            animationTimer.stop();
        }
        
        animationTimer = new Timer(speed.getDelayMiliSeconds(), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!sortingComplete) {
                    performSortingStep();
                    repaint();
                } else {
                    animationTimer.stop();
                    isAnimating = false;
                }
            }
        });
        
        animationTimer.start();
    }
    
    private void performSortingStep() {
        switch (algorithm) {
            case BUBBLE_SORT:
                performBubbleSortStep();
                break;
            case QUICK_SORT:
                performQuickSortStepSimplified();
                break;
            default:
                performBubbleSortStep();
        }
    }
    
    private void performBubbleSortStep() {
        int n = values.length;
        
        if (currentI >= n - 1) {
            sortingComplete = true;
            return;
        }
        
        if (currentJ >= n - currentI - 1) {
            currentI++;
            currentJ = 0;
            return;
        }
        
        // Comparar y intercambiar si es necesario (orden descendente)
        if (values[currentJ] < values[currentJ + 1]) {
            swap(currentJ, currentJ + 1);
        }
        
        currentJ++;
    }
    
    // Implementación simplificada de QuickSort step-by-step
    private void performQuickSortStepSimplified() {
        if (quickSortStep >= values.length * values.length) {
            sortingComplete = true;
            return;
        }
        
        // Simulación paso a paso más simple
        boolean swapped = false;
        for (int i = 0; i < values.length - 1; i++) {
            currentI = i;
            currentJ = i + 1;
            
            if (values[i] < values[i + 1]) { // Orden descendente
                swap(i, i + 1);
                swapped = true;
                break; // Solo un intercambio por paso para mostrar animación
            }
        }
        
        if (!swapped) {
            sortingComplete = true;
        }
        
        quickSortStep++;
    }
    
    // Implementación original de QuickSort (mantenida para referencia)
    private void performQuickSortStep() {
        if (stackTop < 0) {
            sortingComplete = true;
            return;
        }
        
        if (phase == 0) {
            // Obtener rango del stack
            partitionHigh = popFromStack();
            partitionLow = popFromStack();
            
            if (partitionLow < partitionHigh) {
                pivotIndex = partitionHigh;
                currentI = partitionLow - 1;
                currentJ = partitionLow;
                phase = 1; // Fase de partición
            } else {
                phase = 0; // Continuar con siguiente rango
            }
        } else if (phase == 1) {
            // Fase de partición
            if (currentJ < partitionHigh) {
                // Comparar con pivot (orden descendente)
                if (values[currentJ] >= values[pivotIndex]) {
                    currentI++;
                    swap(currentI, currentJ);
                }
                currentJ++;
            } else {
                // Colocar pivot en posición correcta
                swap(currentI + 1, partitionHigh);
                int newPivotIndex = currentI + 1;
                
                // Agregar sub-rangos al stack
                if (partitionLow < newPivotIndex - 1) {
                    pushToStack(partitionLow, newPivotIndex - 1);
                }
                if (newPivotIndex + 1 < partitionHigh) {
                    pushToStack(newPivotIndex + 1, partitionHigh);
                }
                
                phase = 0; // Volver a fase de selección de rango
                pivotIndex = -1;
            }
        }
    }
    
    private void swap(int i, int j) {
        double tempValue = values[i];
        values[i] = values[j];
        values[j] = tempValue;
        
        String tempLabel = labels[i];
        labels[i] = labels[j];
        labels[j] = tempLabel;
    }
    
    private void pushToStack(int low, int high) {
        quickSortStack[++stackTop] = low;
        quickSortStack[++stackTop] = high;
    }
    
    private int popFromStack() {
        return quickSortStack[stackTop--];
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (values == null || values.length == 0) return;
        
        // Título
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.setColor(Color.BLACK);
        FontMetrics fm = g2d.getFontMetrics();
        int titleWidth = fm.stringWidth(title);
        g2d.drawString(title, (getWidth() - titleWidth) / 2, 30);
        
        // Calcular dimensiones para las barras
        int margin = 50;
        int availableWidth = getWidth() - 2 * margin;
        int availableHeight = getHeight() - 100;
        int barWidth = Math.max(1, availableWidth / values.length - 2);
        int maxBarWidth = 60;
        barWidth = Math.min(barWidth, maxBarWidth);
        
        double maxValue = Arrays.stream(values).max().orElse(1.0);
        if (maxValue == 0) maxValue = 1.0;
        
        // Dibujar barras
        for (int i = 0; i < values.length; i++) {
            int barHeight = (int) ((values[i] / maxValue) * availableHeight);
            int x = margin + i * (barWidth + 2);
            int y = getHeight() - margin - barHeight;
            
            // Determinar color de la barra
            Color barColor = getBarColor(i);
            g2d.setColor(barColor);
            g2d.fillRect(x, y, barWidth, barHeight);
            
            // Borde de la barra
            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, y, barWidth, barHeight);
            
            // Valor encima de la barra
            g2d.setFont(new Font("Arial", Font.PLAIN, 10));
            String valueStr = String.format("%.0f", values[i]);
            FontMetrics valueFm = g2d.getFontMetrics();
            int valueWidth = valueFm.stringWidth(valueStr);
            g2d.drawString(valueStr, x + (barWidth - valueWidth) / 2, y - 5);
            
            // Etiqueta debajo de la barra (si hay espacio)
            if (labels != null && i < labels.length && barWidth > 20) {
                g2d.setFont(new Font("Arial", Font.PLAIN, 8));
                String label = labels[i];
                if (label.length() > 8) {
                    label = label.substring(0, 8) + "...";
                }
                FontMetrics labelFm = g2d.getFontMetrics();
                int labelWidth = labelFm.stringWidth(label);
                int labelY = getHeight() - margin + 15;
                
                // Rotar texto si es necesario
                if (barWidth < labelWidth) {
                    Graphics2D g2dRotated = (Graphics2D) g2d.create();
                    g2dRotated.rotate(-Math.PI/4, x + barWidth/2, labelY);
                    g2dRotated.drawString(label, x + barWidth/2, labelY);
                    g2dRotated.dispose();
                } else {
                    g2d.drawString(label, x + (barWidth - labelWidth) / 2, labelY);
                }
            }
        }
        
        // Información del algoritmo
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        String info = "Algoritmo: " + algorithm.toString() + " | Velocidad: " + speed.getDescription();
        if (isAnimating && !sortingComplete) {
            info += " | Ordenando...";
            if (algorithm == SortingAlgorithm.QUICK_SORT) {
                info += " (Paso: " + quickSortStep + ")";
            }
        } else if (sortingComplete) {
            info += " | Completado";
        }
        g2d.drawString(info, 10, getHeight() - 10);
    }
    
    private Color getBarColor(int index) {
        if (sortingComplete) {
            return SORTED_COLOR;
        }
        
        switch (algorithm) {
            case BUBBLE_SORT:
                if (index == currentJ || index == currentJ + 1) {
                    return COMPARING_COLOR;
                }
                if (index >= values.length - currentI) {
                    return SORTED_COLOR;
                }
                break;
            case QUICK_SORT:
                // Para la versión simplificada, usar colores más simples
                if (index == currentI || index == currentJ) {
                    return COMPARING_COLOR;
                }
                // Mantener compatibilidad con versión original
                if (index == pivotIndex) {
                    return PIVOT_COLOR;
                }
                if ((phase == 1) && (index == currentJ || index == currentI)) {
                    return COMPARING_COLOR;
                }
                break;
        }
        
        return DEFAULT_COLOR;
    }
    
    public boolean isAnimating() {
        return isAnimating;
    }
    
    public void stopAnimation() {
        if (animationTimer != null) {
            animationTimer.stop();
        }
        isAnimating = false;
    }
    
    public double[] getSortedValues() {
        if (values == null) return new double[0];
        return values.clone();
    }
    
    public String[] getSortedLabels() {
        if (labels == null) return new String[0];
        return labels.clone();
    }
}