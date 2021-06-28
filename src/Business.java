/**
 * Classe que define o objeto negócio.
 *
 * @author (grupo27)
 * @version (1)
 */


import java.io.Serializable;
import java.lang.String;

public class Business implements Serializable{
    private String business_id;
    private String name;
    private String city;
    private String state;
    private String categories;
    private int nr_reviews;

    /**
     * Construtor vazio de um business
     */
    public Business() {
        this.business_id = "";
        this.name = "";
        this.city = "";
        this.state = "";
        this.categories = "";
    }

    /**
     * Construtor parametrizado do business
     * @param business_id
     * @param name
     * @param city
     * @param state
     * @param categories
     */
    public Business(String business_id,String name,String city, String state,String categories, int nr_reviews) {
        this.business_id = business_id;
        this.name = name;
        this.city = city;
        this.state = state;
        this.categories = categories;
        this.nr_reviews = 0;
    }
    /**
     * Construtor parametrizado do business por um negócio
     * @param b negocio a copiar
     */
    public Business(Business b) {
        this.business_id = b.getBusiness_id();
        this.name        = b.getName();
        this.city        = b.getCity();
        this.state       = b.getState();
        this.categories  = b.getCategories();
        this.nr_reviews  = b.getNrReviews();
    }

    /**
     * Retorno do nome de um negocio
     * @return    nome do negocio
     */
    public String getName() {
        return this.name;
    }

    /**
     * Retorno do id de um negocio
     * @return    id do negocio
     */
    public String getBusiness_id() {
        return this.business_id;
    }

    /**
     * Retorno da cidade de um negocio
     * @return   cidade do negocio
     */
    public String getCity() {
        return this.city;
    }

    /**
     * Retorno do estado de um negocio
     * @return    estado do negocio
     */
    public String getState() {
        return this.state;
    }

    /**
     * Retorno do categoria de um negocio
     * @return    categoria do negocio
     */
    public String getCategories() {
        return this.categories;
    }

    /**
     * Retorno do numero de reviews de um negocio
     * @return    numero de reviews do negocio
     */
    public int getNrReviews(){
        return this.nr_reviews;
    }

    //setters

    /**
     * Coloca o nome num negocio
     *
     * @param  nome do negocio
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Coloca o id num negocio
     *
     * @param  id do negocio
     */
    public void setBusiness_id(String business_id) {
        this.business_id = business_id;
    }

    /**
     * Coloca o nome num negocio
     *
     * @param  nome do negocio
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * coloca categorias num negocio
     * @param categories
     */
    public void setCategories(String categories) {
        this.categories = categories;
    }

    /**
     *
     * @param state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     *
     * @param nr_reviews
     */
    public void setNrReviews(int nr_reviews){
        this.nr_reviews = nr_reviews;
    }

    /**
     * Devolve a informação de negocio num objeto String
     * @return Business in string format
     */
    public String toString() {
        return "business{" +
                "business_id='" + this.getBusiness_id() + '\'' +
                ", name='" + this.getName() + '\'' +
                ", city='" + this.getCity() + '\'' +
                ", state='" + this.getState() + '\'' +
                ", categories='" + this.getCategories() + '\'' +
                '}';
    }

    /**
     *
     * @return Business
     */
    public Business clone() {
        return new Business(this);
    }

    /**
     *
     * @param string com informação para ser parametrizada
     * @return negocio criado apartir de uma string
     * @throws BusinessInvalidoException
     */
    public Business fromLineToBusiness(String s) throws BusinessInvalidoException{
        String[] splited = s.split(";",6);
        if (splited.length == 5 || (splited.length == 6 && splited[5].isEmpty()))
            if (validString(splited[0]) && validID(splited[0]) && validString(splited[1]) && validString(splited[2]) && validString(splited[3]))
                    return new Business(splited[0],splited[1],splited[2],splited[3],splited[4], 0);
            else throw new BusinessInvalidoException();
        else throw new BusinessInvalidoException();
    }

    /**
     *
     * @param s
     * @return validação se não é string vazia
     */
    private boolean validString (String s) {
        if(s != null)
            return (s.length() > 0);
        else
            return false;
    }

    /**
     * Verifica se um id de negocio é válido
     * @param s
     * @return boolean
     */
    private boolean validID (String s) {
        return (s.length() == 22);
    }

}
