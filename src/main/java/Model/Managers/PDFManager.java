package Model.Managers;

import Model.Bill;
import Model.Client;
import Model.Dishes;
import Model.Ingredients;
import Model.WorkOrder;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import org.jfree.chart.JFreeChart;

public class PDFManager {
    
    private static PdfFont getTitleFont() {
        try {
            return PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private static PdfFont getSubtitleFont() {
        try {
            return PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private static PdfFont getNormalFont() {
        try {
            return PdfFontFactory.createFont(StandardFonts.HELVETICA);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void generateClientReport(ArrayList<Client> clients, String filePath) {
        try {
            PdfWriter writer = new PdfWriter(filePath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Título del reporte
            document.add(new Paragraph("Reporte de Clientes")
                    .setFont(getTitleFont())
                    .setFontSize(18)
                    .setTextAlignment(TextAlignment.CENTER));
            
            document.add(new Paragraph("\n"));

            // Tabla de clientes
            Table table = new Table(UnitValue.createPercentArray(new float[]{2, 4, 2, 2}));
            table.setWidth(UnitValue.createPercentValue(100));

            // Encabezados
            addTableHeader(table, "DPI", "Nombre", "Tipo", "Platillos Pagados");

            // Datos
            for (Client client : clients) {
                table.addCell(new Cell().add(new Paragraph(client.getDpi()).setFont(getNormalFont()).setFontSize(10)));
                table.addCell(new Cell().add(new Paragraph(client.getCompleteName()).setFont(getNormalFont()).setFontSize(10)));
                table.addCell(new Cell().add(new Paragraph(client.getTypeClient().toString()).setFont(getNormalFont()).setFontSize(10)));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(client.getPaidDishes())).setFont(getNormalFont()).setFontSize(10)));
            }

            document.add(table);
            document.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void generateIngredientsReport(ArrayList<Ingredients> ingredients, String filePath, boolean top10) {
        try {
            PdfWriter writer = new PdfWriter(filePath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            String title = top10 ? "Top 10 Ingredientes Más Caros" : "Reporte de Ingredientes";
            document.add(new Paragraph(title)
                    .setFont(getTitleFont())
                    .setFontSize(18)
                    .setTextAlignment(TextAlignment.CENTER));
            
            document.add(new Paragraph("\n"));

            Table table = new Table(UnitValue.createPercentArray(new float[]{1, 3, 2, 2, 2, 2}));
            table.setWidth(UnitValue.createPercentValue(100));

            addTableHeader(table, "ID", "Nombre", "Marca", "Existencias", "Unidades", "Precio");

            for (Ingredients ingredient : ingredients) {
                table.addCell(new Cell().add(new Paragraph(String.valueOf(ingredient.getIdentifier())).setFont(getNormalFont()).setFontSize(10)));
                table.addCell(new Cell().add(new Paragraph(ingredient.getName()).setFont(getNormalFont()).setFontSize(10)));
                table.addCell(new Cell().add(new Paragraph(ingredient.getBrand()).setFont(getNormalFont()).setFontSize(10)));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(ingredient.getStock())).setFont(getNormalFont()).setFontSize(10)));
                table.addCell(new Cell().add(new Paragraph(ingredient.getUnits()).setFont(getNormalFont()).setFontSize(10)));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(ingredient.getPrice())).setFont(getNormalFont()).setFontSize(10)));
            }

            document.add(table);
            document.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void generateDishesReport(ArrayList<Dishes> dishes, String filePath) {
        try {
            PdfWriter writer = new PdfWriter(filePath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Top 10 Platillos Más Pedidos")
                    .setFont(getTitleFont())
                    .setFontSize(18)
                    .setTextAlignment(TextAlignment.CENTER));
            
            document.add(new Paragraph("\n"));

            Table table = new Table(UnitValue.createPercentArray(new float[]{1, 4, 2, 2}));
            table.setWidth(UnitValue.createPercentValue(100));

            addTableHeader(table, "ID", "Nombre", "Precio Total", "Veces Ordenado");

            for (Dishes dish : dishes) {
                table.addCell(new Cell().add(new Paragraph(String.valueOf(dish.getIdentifier())).setFont(getNormalFont()).setFontSize(10)));
                table.addCell(new Cell().add(new Paragraph(dish.getName()).setFont(getNormalFont()).setFontSize(10)));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(dish.getTotalPrice())).setFont(getNormalFont()).setFontSize(10)));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(dish.getOrderCounter())).setFont(getNormalFont()).setFontSize(10)));
            }

            document.add(table);
            document.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void generateBill(Bill bill, String filePath) {
        try {
            PdfWriter writer = new PdfWriter(filePath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Encabezado
            document.add(new Paragraph("Restaurante USAC")
                    .setFont(getTitleFont())
                    .setFontSize(18)
                    .setTextAlignment(TextAlignment.CENTER));
            
            document.add(new Paragraph("Factura No: " + bill.getBillNumber())
                    .setFont(getSubtitleFont())
                    .setFontSize(14));
            
            document.add(new Paragraph("Fecha: " + bill.getFormattedDate())
                    .setFont(getNormalFont())
                    .setFontSize(12));
            
            document.add(new Paragraph("Cliente: " + bill.getClient().getCompleteName())
                    .setFont(getNormalFont())
                    .setFontSize(12));
            
            document.add(new Paragraph("\n"));

            // Detalle de órdenes
            Table table = new Table(UnitValue.createPercentArray(new float[]{4, 2, 2}));
            table.setWidth(UnitValue.createPercentValue(100));
            addTableHeader(table, "Platillo", "Cantidad", "Precio");

            for (WorkOrder order : bill.getOrders()) {
                table.addCell(new Cell().add(new Paragraph(order.getDishes().getName()).setFont(getNormalFont()).setFontSize(10)));
                table.addCell(new Cell().add(new Paragraph("1").setFont(getNormalFont()).setFontSize(10)));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(order.getDishes().getTotalPrice())).setFont(getNormalFont()).setFontSize(10)));
            }

            document.add(table);
            document.add(new Paragraph("\n"));

            // Totales
            document.add(new Paragraph("Subtotal: Q" + String.format("%.2f", bill.getSubtotal()))
                    .setFont(getNormalFont())
                    .setFontSize(12));
            
            document.add(new Paragraph("Impuestos (12%): Q" + String.format("%.2f", bill.getTaxes()))
                    .setFont(getNormalFont())
                    .setFontSize(12));
            
            document.add(new Paragraph("Total: Q" + String.format("%.2f", bill.getOverall()))
                    .setFont(getSubtitleFont())
                    .setFontSize(14));
            
            document.add(new Paragraph("\n"));
            
            document.add(new Paragraph("Estado: " + (bill.isPaid() ? "PAGADA" : "PENDIENTE"))
                    .setFont(getSubtitleFont())
                    .setFontSize(14));

            document.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addTableHeader(Table table, String... headers) {
        for (String header : headers) {
            Cell cell = new Cell().add(new Paragraph(header)
                    .setFont(getSubtitleFont())
                    .setFontSize(12))
                    .setTextAlignment(TextAlignment.CENTER);
            table.addHeaderCell(cell);
        }
    }
    public static void addChartToPDF(Document document, JFreeChart chart) {
    try {
        // Convertir la gráfica JFreeChart a BufferedImage
        BufferedImage chartImage = chart.createBufferedImage(600, 400);
        
        // Convertir BufferedImage a bytes PNG
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(chartImage, "PNG", baos);
        byte[] imageBytes = baos.toByteArray();
        
        // Crear imagen para iText usando ImageDataFactory
        ImageData imageData = ImageDataFactory.create(imageBytes);
        com.itextpdf.layout.element.Image pdfImage = new com.itextpdf.layout.element.Image(imageData);
        
        // Ajustar tamaño y agregar al documento
        pdfImage.setAutoScale(true);
        pdfImage.setMarginTop(10);
        pdfImage.setMarginBottom(10);
        
        document.add(new Paragraph("Gráfica del Reporte")
            .setTextAlignment(TextAlignment.CENTER)
            .setFontSize(12));
        
        document.add(pdfImage);
        
    } catch (Exception e) {
        System.err.println("Error al agregar gráfica al PDF: " + e.getMessage());
        e.printStackTrace();
    }
}
    public static void generateClientReportWithChart(ArrayList<Client> clients, String filePath, 
                                               JFreeChart chart, long sortingTime) {
    try {
        PdfWriter writer = new PdfWriter(filePath);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // Título del reporte
        document.add(new Paragraph("Reporte de Clientes")
                .setFont(getTitleFont())
                .setFontSize(18)
                .setTextAlignment(TextAlignment.CENTER));
        
        document.add(new Paragraph("\n"));
        
        // Información del algoritmo usado
        document.add(new Paragraph("Tiempo de ordenamiento: " + sortingTime + " ms")
                .setFont(getNormalFont())
                .setFontSize(12)
                .setTextAlignment(TextAlignment.CENTER));
        
        document.add(new Paragraph("\n"));

        // Agregar gráfica si existe
        if (chart != null) {
            addChartToPDF(document, chart);
            document.add(new Paragraph("\n"));
        }

        // Tabla de clientes
        Table table = new Table(UnitValue.createPercentArray(new float[]{2, 4, 2, 2}));
        table.setWidth(UnitValue.createPercentValue(100));

        // Encabezados
        addTableHeader(table, "DPI", "Nombre", "Tipo", "Platillos Pagados");

        // Datos
        for (Client client : clients) {
            table.addCell(new Cell().add(new Paragraph(client.getDpi()).setFont(getNormalFont()).setFontSize(10)));
            table.addCell(new Cell().add(new Paragraph(client.getCompleteName()).setFont(getNormalFont()).setFontSize(10)));
            table.addCell(new Cell().add(new Paragraph(client.getTypeClient().toString()).setFont(getNormalFont()).setFontSize(10)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(client.getPaidDishes())).setFont(getNormalFont()).setFontSize(10)));
        }

        document.add(table);
        document.close();
        
    } catch (Exception e) {
        e.printStackTrace();
    }
}

public static void generateIngredientsReportWithChart(ArrayList<Ingredients> ingredients, String filePath, 
                                                    boolean isByPrice, JFreeChart chart, long sortingTime) {
    try {
        PdfWriter writer = new PdfWriter(filePath);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        String title = isByPrice ? "Top 10 Ingredientes Más Caros" : "Top 10 Ingredientes Más Usados";
        document.add(new Paragraph(title)
                .setFont(getTitleFont())
                .setFontSize(18)
                .setTextAlignment(TextAlignment.CENTER));
        
        document.add(new Paragraph("\n"));
        
        // Información del algoritmo usado
        document.add(new Paragraph("Tiempo de ordenamiento: " + sortingTime + " ms")
                .setFont(getNormalFont())
                .setFontSize(12)
                .setTextAlignment(TextAlignment.CENTER));
        
        document.add(new Paragraph("\n"));

        // Agregar gráfica si existe
        if (chart != null) {
            addChartToPDF(document, chart);
            document.add(new Paragraph("\n"));
        }

        Table table = new Table(UnitValue.createPercentArray(new float[]{1, 3, 2, 2, 2, 2}));
        table.setWidth(UnitValue.createPercentValue(100));

        if (isByPrice) {
            addTableHeader(table, "ID", "Nombre", "Marca", "Existencias", "Unidades", "Precio");
        } else {
            addTableHeader(table, "ID", "Nombre", "Marca", "Existencias", "Unidades", "Veces Usado");
        }

        for (Ingredients ingredient : ingredients) {
            table.addCell(new Cell().add(new Paragraph(String.valueOf(ingredient.getIdentifier())).setFont(getNormalFont()).setFontSize(10)));
            table.addCell(new Cell().add(new Paragraph(ingredient.getName()).setFont(getNormalFont()).setFontSize(10)));
            table.addCell(new Cell().add(new Paragraph(ingredient.getBrand()).setFont(getNormalFont()).setFontSize(10)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(ingredient.getStock())).setFont(getNormalFont()).setFontSize(10)));
            table.addCell(new Cell().add(new Paragraph(ingredient.getUnits()).setFont(getNormalFont()).setFontSize(10)));
            
            if (isByPrice) {
                table.addCell(new Cell().add(new Paragraph(String.valueOf(ingredient.getPrice())).setFont(getNormalFont()).setFontSize(10)));
            } else {
                // Para ingredientes más usados, necesitarías un método para obtener el conteo de uso
                // Por ahora usamos un placeholder
                table.addCell(new Cell().add(new Paragraph("N/A").setFont(getNormalFont()).setFontSize(10)));
            }
        }

        document.add(table);
        document.close();
        
    } catch (Exception e) {
        e.printStackTrace();
    }
}

public static void generateDishesReportWithChart(ArrayList<Dishes> dishes, String filePath, 
                                               JFreeChart chart, long sortingTime) {
    try {
        PdfWriter writer = new PdfWriter(filePath);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("Top 10 Platillos Más Pedidos")
                .setFont(getTitleFont())
                .setFontSize(18)
                .setTextAlignment(TextAlignment.CENTER));
        
        document.add(new Paragraph("\n"));
        
        // Información del algoritmo usado
        document.add(new Paragraph("Tiempo de ordenamiento: " + sortingTime + " ms")
                .setFont(getNormalFont())
                .setFontSize(12)
                .setTextAlignment(TextAlignment.CENTER));
        
        document.add(new Paragraph("\n"));

        // Agregar gráfica si existe
        if (chart != null) {
            addChartToPDF(document, chart);
            document.add(new Paragraph("\n"));
        }

        Table table = new Table(UnitValue.createPercentArray(new float[]{1, 4, 2, 2}));
        table.setWidth(UnitValue.createPercentValue(100));

        addTableHeader(table, "ID", "Nombre", "Precio Total", "Veces Ordenado");

        for (Dishes dish : dishes) {
            table.addCell(new Cell().add(new Paragraph(String.valueOf(dish.getIdentifier())).setFont(getNormalFont()).setFontSize(10)));
            table.addCell(new Cell().add(new Paragraph(dish.getName()).setFont(getNormalFont()).setFontSize(10)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(dish.getTotalPrice())).setFont(getNormalFont()).setFontSize(10)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(dish.getOrderCounter())).setFont(getNormalFont()).setFontSize(10)));
        }

        document.add(table);
        document.close();
        
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}