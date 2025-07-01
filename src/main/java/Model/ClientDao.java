
package Model;
import java.util.ArrayList;

public class ClientDao {
    private ArrayList<Client> clients;

    public ClientDao(ArrayList<Client> clients) {
        this.clients = clients;
    }
    
    public ClientDao (){
        this.clients = new ArrayList<>();
    }
    
    public  Client getBydpi(String dpi){
        for(Client client: clients){
            if(client.getDpi().equals(dpi)){
                return client;
            }
        }
        return null;
    }     
    public synchronized boolean create(Client client){
        if(client!=null && !clientExists(client.getDpi())){
            return clients.add(client);
        }
        return false;
    }
    
    public Client getByUser(String user){
        for(Client client : clients){
            if(client.getUser().equals(user)){
                return client;
            }
        }
        return null;
    }
    public ArrayList<Client> getAll(){
        return new ArrayList<>(clients);
    }
    public ArrayList<Client> getByType(TypeClient type){
        ArrayList<Client> clientsType = new ArrayList<>();
        for(Client client: clients){
            if(client.getTypeClient() == type){
                clientsType.add(client);
            }    
        }
        return clientsType;
    }
    public synchronized boolean uptade(Client updateClient){
        for(int i=0; i<clients.size(); i++){
            if(clients.get(i).getDpi().equals(updateClient.getDpi())){
                clients.set(i, updateClient);
                return true;
            }
        }
        return false;
    }
    public synchronized boolean remove(String dpi){
        for(int i =0; i<clients.size(); i++){
            if(clients.get(i).getDpi().equals(dpi)){
                clients.remove(i);
                return true;
            }
        }
        return false;
    }
    public Client authenticate(String user, String pass){
        Client client = getByUser(user);
        if(client != null && client.validateCredentials(user, pass)){
            return client;
        }
        return null;
    }
    public synchronized int countByType(TypeClient type){
        int count=0;
        for(Client client : clients){
            if(client.getTypeClient() == type){
                count++;                
            }
        }
        return count;
    }
    public int size(){
        return clients.size();
    }
    public boolean isEmpty(){
        return clients.isEmpty();
    }
    public void clear(){
        clients.clear();
    }
    public synchronized void loadClients(ArrayList<Client> newClients){
        if(newClients != null){
            for(Client client : newClients){
                create(client);
            }
        }
    } 
    public synchronized boolean clientExists(String dpi){
        return getBydpi(dpi) != null;
    }
    public synchronized ArrayList<Client> getInterList(){
        return clients;
    }
    public synchronized void setListaInterna(ArrayList<Client> client) {
        this.clients = client != null ? client : new ArrayList<>();
    } 
    public synchronized ArrayList<Client> obtenerTop5ConMasPedidos() {
        ArrayList<Client> clientesOrdenados = new ArrayList<>(clients);
        // Ordenamiento burbuja por platillos pagados (descendente)
        for (int i = 0; i < clientesOrdenados.size() - 1; i++) {
            for (int j = 0; j < clientesOrdenados.size() - i - 1; j++) {
                if (clientesOrdenados.get(j).getPaidDishes() < 
                    clientesOrdenados.get(j + 1).getPaidDishes()) {
                    Client temp = clientesOrdenados.get(j);
                    clientesOrdenados.set(j, clientesOrdenados.get(j + 1));
                    clientesOrdenados.set(j + 1, temp);
                }
            }
        }
        
        // Retornar solo los primeros 5
        ArrayList<Client> top5 = new ArrayList<>();
        int limite = Math.min(5, clientesOrdenados.size());
        for (int i = 0; i < limite; i++) {
            top5.add(clientesOrdenados.get(i));
        }
        return top5;
    }
} 

