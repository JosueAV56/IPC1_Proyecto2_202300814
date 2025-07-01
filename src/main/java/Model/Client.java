
package Model;

import java.io.Serializable;

public class Client implements Serializable,Comparable<Client>{
    private static final long serialVersionUID = 1L; 
    private String dpi;
    private String completeName;
    private String user;
    private String password;
    private String photoPath;
    private TypeClient typeClient;
    private int paidDishes;

    public Client() {
        this.typeClient = TypeClient.COMMUN;
        this.paidDishes = 0;
    }

    public Client(String dpi, String completeName, String user, String password, 
            String photoPath, TypeClient typeClient) {
        this.dpi = dpi;
        this.completeName = completeName;
        this.user = user;
        this.password = password;
        this.photoPath = photoPath;
        this.typeClient = typeClient;
        this.paidDishes = 0;
    }

    public Client(String dpi, String completeName, String user, String password, String photoPath) {
        this.dpi = dpi;
        this.completeName = completeName;
        this.user = user;
        this.password = password;
        this.photoPath = photoPath;
        this.typeClient = TypeClient.COMMUN;
        this.paidDishes = 0;
    }
    
    public boolean validateCredentials (String user, String password){
        return this.user.equals(user) && this.password.equals(password);
    }
    
    public synchronized void increasePaidDishes (){
        this.paidDishes ++;
        verifyUpgradeClient();
    } 
    
    private synchronized void verifyUpgradeClient(){
        if(this.paidDishes >= 10 && this.typeClient == TypeClient.COMMUN){
            this.typeClient = TypeClient.GOLD;
        }
    }
    public synchronized boolean isGoldClient (){
        return this.typeClient == TypeClient.GOLD;
    }
    
    public String toString(){
        return "Cliente {" +
                "dpi='" + dpi + '\'' +
                ", nombreCompleto='" + completeName + '\'' +
                ", usuario= '" + user + '\'' +
                ", TipoCliente='" + typeClient + 
                ", platillosPagados='" + paidDishes + 
                '}';
    }
    
    public int compareTo(Client other) {
        // Ordenar por cantidad de platillos pagados (descendente)
        return Integer.compare(other.getPaidDishes(), this.getPaidDishes());
    }
    

    public String getDpi() {return dpi;}
    public void setDpi(String dpi) {this.dpi = dpi;}
    public String getCompleteName() {return completeName;}
    public void setCompleteName(String completeName) {this.completeName = completeName;}
    public String getUser() {return user;}
    public void setUser(String user) {this.user = user;}
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
    public String getPhotoPath() {return photoPath;}
    public void setPhotoPath(String photoPath) {this.photoPath = photoPath;}
    public TypeClient getTypeClient() {return typeClient;}
    public void setTypeClient(TypeClient typeClient) {this.typeClient = typeClient;}
    public int getPaidDishes() {return paidDishes;}
    public void setPaidDishes(int paidDishes) {this.paidDishes = paidDishes;}
    
    
    
    
    
    
}
