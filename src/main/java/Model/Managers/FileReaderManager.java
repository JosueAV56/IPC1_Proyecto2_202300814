
package Model.Managers;

import Model.Client;
import Model.Dishes;
import Model.Ingredients;
import Model.TypeClient;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileReaderManager {
    public static ArrayList<Ingredients> readIngredientsFile(String filePath) {
        ArrayList<Ingredients> ingredients = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    try {
                        String[] parts = line.split("-");
                        if (parts.length == 6) {
                            int identifier = Integer.parseInt(parts[0].trim());
                            String name = parts[1].trim();
                            String brand = parts[2].trim();
                            int stock = Integer.parseInt(parts[3].trim());
                            String units = parts[4].trim();
                            double price = Double.parseDouble(parts[5].trim());
                            
                            Ingredients ingredient = new Ingredients(identifier, name, brand, stock, units, price);
                            ingredients.add(ingredient);
                        } else {
                            System.err.println("Formato incorrecto en línea: " + line);
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Error de formato numérico en línea: " + line + " - " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer archivo de ingredientes: " + e.getMessage());
        }
        
        return ingredients;
    }
    
    /**
     * Lee archivo de platillos con extensión .pltll
     * Formato: nombrePlatillo-lista ingredientes-precioManoDeObra
     * Lista de ingredientes separados por ";"
     * Ejemplo: Pizza Margherita-1;2;3-15.50
     */
    public static ArrayList<Dishes> readDishesFile(String filePath, ArrayList<Ingredients> availableIngredients) {
        ArrayList<Dishes> dishes = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int dishId = 1; // ID correlativo asignado por el sistema
            
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    try {
                        String[] parts = line.split("-");
                        if (parts.length == 3) {
                            String dishName = parts[0].trim();
                            String[] ingredientIds = parts[1].trim().split(";");
                            double laborPrice = Double.parseDouble(parts[2].trim());
                            
                            // Buscar ingredientes por ID
                            ArrayList<Ingredients> dishIngredients = new ArrayList<>();
                            for (String idStr : ingredientIds) {
                                int ingredientId = Integer.parseInt(idStr.trim());
                                Ingredients ingredient = findIngredientById(availableIngredients, ingredientId);
                                if (ingredient != null) {
                                    dishIngredients.add(ingredient);
                                } else {
                                    System.err.println("Ingrediente con ID " + ingredientId + " no encontrado para platillo: " + dishName);
                                }
                            }
                            
                            if (!dishIngredients.isEmpty()) {
                                Dishes dish = new Dishes(dishId++, dishName, dishIngredients, laborPrice);
                                dishes.add(dish);
                            }
                        } else {
                            System.err.println("Formato incorrecto en línea: " + line);
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Error de formato numérico en línea: " + line + " - " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer archivo de platillos: " + e.getMessage());
        }
        
        return dishes;
    }
    
    /**
     * Lee archivo de clientes con extensión .clnt
     * Formato: DPI-NombreCompleto-Usuario-Contraseña-TipoCliente-Foto
     * Ejemplo: 2345679890301-Tedy Lopez-ted-123-oro-C:\Users\Tedy\Desktop\foto.jpg
     */
    public static ArrayList<Client> readClientsFile(String filePath) {
        ArrayList<Client> clients = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    try {
                        String[] parts = line.split("-");
                        if (parts.length == 6) {
                            String dpi = parts[0].trim();
                            String completeName = parts[1].trim();
                            String username = parts[2].trim();
                            String password = parts[3].trim();
                            String typeStr = parts[4].trim().toLowerCase();
                            String photoPath = parts[5].trim();
                            
                            // Validar DPI (debe ser numérico)
                            if (!isValidDPI(dpi)) {
                                System.err.println("DPI inválido en línea: " + line);
                                continue;
                            }
                            
                            // Convertir tipo de cliente
                            TypeClient typeClient;
                            if (typeStr.equals("oro")) {
                                typeClient = TypeClient.GOLD;
                            } else {
                                typeClient = TypeClient.COMMUN;
                            }
                            
                            Client client = new Client(dpi, completeName, username, password, photoPath, typeClient);
                            clients.add(client);
                        } else {
                            System.err.println("Formato incorrecto en línea: " + line);
                        }
                    } catch (Exception e) {
                        System.err.println("Error al procesar línea: " + line + " - " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer archivo de clientes: " + e.getMessage());
        }
        
        return clients;
    }
    
    /**
     * Método auxiliar para encontrar ingrediente por ID
     */
    private static Ingredients findIngredientById(ArrayList<Ingredients> ingredients, int id) {
        for (Ingredients ingredient : ingredients) {
            if (ingredient.getIdentifier() == id) {
                return ingredient;
            }
        }
        return null;
    }
    
    /**
     * Valida que el DPI sea numérico y tenga longitud correcta
     */
    private static boolean isValidDPI(String dpi) {
        if (dpi == null || dpi.length() != 13) {
            return false;
        }
        try {
            Long.parseLong(dpi);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Método para validar extensión de archivo
     */
    public static boolean validateFileExtension(String filePath, String expectedExtension) {
        if (filePath == null || expectedExtension == null) {
            return false;
        }
        return filePath.toLowerCase().endsWith("." + expectedExtension.toLowerCase());
    }
    
    /**
     * Método para validar que el archivo existe y es legible
     */
    public static boolean validateFileExists(String filePath) {
        try {
            java.io.File file = new java.io.File(filePath);
            return file.exists() && file.canRead();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Método para obtener estadísticas de carga
     */
    public static void printLoadStatistics(String fileType, int totalLoaded, int totalErrors) {
        System.out.println("=== Estadísticas de carga de " + fileType + " ===");
        System.out.println("Registros cargados exitosamente: " + totalLoaded);
        System.out.println("Errores encontrados: " + totalErrors);
        System.out.println("========================================");
    }
}
