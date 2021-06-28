import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DateFormatSymbols;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class GestReviews extends Observable implements Serializable{
    private Catalogo_users CUsers;
    private Catalogo_reviews CReviews;
    private Catalogo_businesses CBusiness;
    private Table resultado;

    public GestReviews() {
        this.CBusiness = new Catalogo_businesses();
        this.CReviews  = new Catalogo_reviews();
        this.CUsers    = new Catalogo_users();
    }

    public void setLerFriends(boolean lerFriends){
        Catalogo_users.setLerFriends(lerFriends);
    }

    public void loadGestReviewsFromFiles(String business, String reviews, String users ) throws StructureAlreadyExists, IOException, NotCorrectObject {
        this.CBusiness.loadStructFromFile(business);
        this.CUsers.loadStructFromFile(users);
        this.CReviews.loadStructFromFile(reviews,this.CUsers,this.CBusiness);
    }

    public void reloadGestReviewsFromFiles(String business, String reviews, String users ) throws StructureAlreadyExists, IOException, NotCorrectObject {
        this.CBusiness = null;
        this.CUsers = null;
        this.CReviews = null;
        System.gc();
        this.CBusiness = new Catalogo_businesses();
        this.CReviews  = new Catalogo_reviews();
        this.CUsers    = new Catalogo_users();
        this.CBusiness.loadStructFromFile(business);
        this.CUsers.loadStructFromFile(users);
        this.CReviews.loadStructFromFile(reviews,this.CUsers,this.CBusiness);
    }
    
    public void guardaEstadoGestReviews (String nomeF) throws IOException{
        FileOutputStream fos = new FileOutputStream(nomeF);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);

        oos.flush();
        oos.close();
    }

    public static GestReviews carregaEstadoGestReviews (String nomeF) throws IOException, FileNotFoundException,ClassNotFoundException{
        FileInputStream fis = new FileInputStream(nomeF);
        ObjectInputStream ois = new ObjectInputStream(fis);

        GestReviews novo = (GestReviews) ois.readObject();

        ois.close();
        return novo;
    }
    
    public void updateControlador () {
        this.setChanged();
        this.notifyObservers(this.resultado);
    }
    
    public void estatistica1 () {
        this.resultado = new Estatistica(CUsers, CBusiness, CReviews).estatistica1();
        this.updateControlador();
    }
    
    public void estatistica2 () {
        this.resultado = new Estatistica(CUsers, CBusiness, CReviews).estatistica2();
        this.updateControlador();
    }

    public void query1(){
        this.resultado = new Querys(CUsers, CBusiness, CReviews).query1();
        this.updateControlador();
    }

    public void query2(int mes, int ano) {
        try {
            this.resultado = new Querys(CUsers, CBusiness, CReviews).query2(mes,ano);
        } catch (DataNaoValidaException e) {
            resultado = new Table(0);
        }
        this.updateControlador();
    }


    public void query3(String User_id) {
        this.resultado = new Querys(CUsers, CBusiness, CReviews).query3(User_id);
        this.updateControlador();
    }

    public void query4(String business_id) {
        this.resultado = new Querys(CUsers, CBusiness, CReviews).query4(business_id);
        this.updateControlador();
    }

    public void query5(String user_id) {
        this.resultado = new Querys(CUsers, CBusiness, CReviews).query5(user_id);
        this.updateControlador();
    }
     
    public void query6 (int top) {
        this.resultado = new Querys(CUsers, CBusiness, CReviews).query6(top);
        this.updateControlador();
    }

    public void query7 () {
        this.resultado = new Querys(CUsers, CBusiness, CReviews).query7();
        this.updateControlador();
    }

    public void query8(int n){
        this.resultado = new Querys(CUsers, CBusiness, CReviews).query8(n);
        this.updateControlador();
    }

    public void query9(int n, String id){
        this.resultado = new Querys(CUsers, CBusiness, CReviews).query9(n,id);
        this.updateControlador();
    }

    public void query10(){
        this.resultado = new Querys(CUsers, CBusiness, CReviews).query10();
        this.updateControlador();
    }

}
