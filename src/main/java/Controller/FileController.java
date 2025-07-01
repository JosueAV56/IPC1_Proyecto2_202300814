
package Controller;

import Model.Client;
import Model.Dishes;
import Model.Ingredients;
import Model.Managers.FileReaderManager;
import java.util.ArrayList;

public class FileController {
    public ArrayList<Ingredients> loadIngredientsFromFile(String filePath){
        if(FileReaderManager.validateFileExtension(filePath, "igd") &&
           FileReaderManager.validateFileExists(filePath)){
           return FileReaderManager.readIngredientsFile(filePath);
        }
        return new ArrayList<>();
    }
    public ArrayList<Dishes> loadDishesFromFile(String filePath, ArrayList<Ingredients> ingredients){
        if(FileReaderManager.validateFileExtension(filePath, "pltll") &&
           FileReaderManager.validateFileExists(filePath)){
           return FileReaderManager.readDishesFile(filePath, ingredients);
        }
        return new ArrayList<>();
    }
    public ArrayList<Client> loadClientsFromFile(String filePath){
        if(FileReaderManager.validateFileExtension(filePath, "clnt") &&
           FileReaderManager.validateFileExists(filePath)){
        return FileReaderManager.readClientsFile(filePath);
        }
        return null;
    }
    
}
