/**
 * Estrutura utilizada como um tuplo para otimizar as contagens de estrelas e num de busineess ou reviews feitas
 * @author (grupo27)
 * @version (1)
 */

 

 



public class RV {
    private String id; // any id, depends on the query that is using the struct
    private float stars;
    private int num_businesses;

    /**
     * Estrutura vazia de RV
     */
    public RV() {
        this.id = "";
        this.stars = 0;
        this.num_businesses = 0;
    }

    /**
     * Construtor parametrizado
     * @param id
     * @param r
     */
    public RV(String id, Review r) {
        this.id = id;
        this.stars = r.getStars();
        this.num_businesses = 1;
    }

    /**
     * Construtor RV através de outro RV
     * @param r
     */
    public RV(RV r) {
        this.id = r.getID();
        this.num_businesses = r.getNum_businesses();
        this.stars = r.getStars();
    }

    /**
     * Adiciona id a review
     * @param id
     */
    public void add_id(String id) {
        this.id = id;
    }

    /**
     * Adiciona uma as estrelas e incremnenta 1 através de uma review
     * @param r
     */
    public void addBusiness(Review r) {
        this.stars += r.getStars();
        this.num_businesses += 1;
    }

    /**
     * Devolve id da RV
     * @return id
     */
    public String getID() {
        return this.id;
    }

    /**
     * Devolve numero de estrelas
     * @return
     */
    public float getStars() {
        return this.stars;
    }

    /**
     * Devolve numero de business
     * @return numero de business
     */
    public int getNum_businesses() {
        return this.num_businesses;
    }

    /**
     * Calcula media da RV
     * @return
     */
    public float calcula_media() {
        return this.getStars() / this.getNum_businesses();
    }

    /**
     * Devolve a informação de RV num objeto String
     * @return RV in string format
     */return boolean
     */
    public String toString() {
        return  this.id + " : " + "\n" + "Media de estrelas: " + (this.getStars()/this.getNum_businesses()) + "\n" + "Numero de negocios avaliados " + this.getNum_businesses() + "\n";
    }

    /**
     * Copia de uma rv
     * @return rv
     */
    public RV clone() {
        return new RV(this);
    }

}
