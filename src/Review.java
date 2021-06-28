/**
 * Classe que define o objeto Review.
 *
 * @author (grupo27)
 * @version (1)
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.Serializable;

public class Review implements Serializable{
    private String review_id;
    private String user_id;
    private String business_id;
    private float stars;
    private int useful;
    private int funny;
    private int cool ;
    private LocalDateTime date;
    private String text;

    /**
     * Construtor vazio de um review
     */
    public Review() {
        this.review_id = "";
        this.user_id = "";
        this.business_id = "";
        this.stars = 0;
        this.useful = 0;
        this.funny = 0;
        this.cool = 0;
        this.date = LocalDateTime.now();
        this.text = "";
    }

    /**
     * Construtor parametrizado de uma review
     * @param review_id
     * @param user_id
     * @param business_id
     * @param stars
     * @param useful
     * @param funny
     * @param cool
     * @param date
     * @param text
     */
    public Review(String review_id, String user_id, String business_id,float stars,int useful,int funny,int cool, LocalDateTime date, String text) {
        this.review_id = review_id;
        this.user_id = user_id;
        this.business_id = business_id;
        this.stars = stars;
        this.useful = useful;
        this.funny = funny;
        this.cool = cool;
        this.date = date;
        this.text = text;
    }

    /**
     * Construtor parametrizado de uma review através de outra review
     * @param r
     */
    public Review(Review r) {
        this.review_id = r.getReview_id();
        this.user_id = r.getUser_id();
        this.business_id = r.getBusiness_id();
        this.stars = r.getStars();
        this.useful = r.getUseful();
        this.funny = r.getFunny();
        this.cool = r.getCool();
        this.date = r.getDate();
        this.text = r.getText();
    }

    /**
     * Devolve id do user
     * @return id do user
     */
    public String getUser_id() {
        return this.user_id;
    }

    /**
     * Retorno do id de um negocio reviewed
     * @return    id do negocio reviewed
     */
    public String getBusiness_id() {
        return this.business_id;
    }

    /**
     * Devolve numero de estrelas da review
     * @return numero de estrelas da review
     */
    public float getStars() {
        return this.stars;
    }

    /**
     *  Devolde estado da instância cool
     * @return cool
     */
    public int getCool() {
        return this.cool;
    }

    /**
     *  Devolde estado da instância funny
     * @return funny
     */
    public int getFunny() {
        return this.funny;
    }

    /**
     * Devolve id da review
     * @return id do user
     */
    public String getReview_id() {
        return this.review_id;
    }

    /**
     * Devolve texto da review
     * @return texto da review
     */
    public String getText() {
        return this.text;
    }

    /**
     *  Devolde estado da instância usefull
     * @return usefull
     */
    public int getUseful() {
        return this.useful;
    }

    /**
     * Devolve Data da review
     * @return Data da review
     */
    public LocalDateTime getDate() {
        return date;
    }


    /**
     * Coloca user_id
     * @param user_id
     */
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    /**
     * Coloca id do negocio
     * @param id do negocio
     */
    public void setBusiness_id(String business_id) {
        this.business_id = business_id;
    }

    /**
     * Coloca id da review
     * @param id da review
     */
    public void setReview_id(String review_id) {
        this.review_id = review_id;
    }

    /**
     * Coloca numero de estrelas
     * @param stars
     */
    public void setStars(float stars) {
        this.stars = stars;
    }

    /**
     * Coloca no estado usefull um estado
     * @param useful
     */
    public void setUseful(int useful) {
        this.useful = useful;
    }

    /**
     * Coloca no campo data a dara da review
     * @param useful
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    /**
     * Coloca no estado cool um estado
     * @param useful
     */
    public void setCool(int cool) {
        this.cool = cool;
    }

    /**
     * Coloca no estado  um estado
     * @param useful
     */
    public void setFunny(int funny) {
        this.funny = funny;
    }

    /**
     * Coloca no campo text o texto da review
     * @param text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Devolve a informação de review num objeto String
     * @return Review in string format
     */
    public String toString() {
        return "Review{" +
                "review_id='" + this.review_id + '\'' +
                ", user_id='" + this.user_id + '\'' +
                ", business_id='" + this.business_id + '\'' +
                ", stars=" + this.stars +
                ", useful=" + this.useful +
                ", funny=" + this.funny +
                ", cool=" + this.cool +
                ", date=" + this.date +
                ", text='" + this.text + '\'' +
                '}';
    }

    /**
     * Le de uma string com informação construindo uma review
     * @param s
     * @param cu
     * @param cb
     * @return
     * @throws ReviewInvalidaException
     */
    public Review fromLineToReview(String s, Catalogo_users cu , Catalogo_businesses cb) throws ReviewInvalidaException {
        String[] splited = s.split(";",9);
        int funny, useful, cool;
        
        float stars;
        try {
            stars = Float.parseFloat(splited[3]);
            useful = Integer.parseInt(splited[4]);
            funny = Integer.parseInt(splited[5]);
            cool  = Integer.parseInt(splited[6]);
        }catch (NumberFormatException e) {
            throw new ReviewInvalidaException();
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(splited[7], formatter);
       if (splited.length == 9)
            if (validString(splited[0]) && validStars (stars) 
                                && validNum (useful) && validNum (funny) && validNum (cool) && validData (dateTime)
                                && cu.contains(splited[1]) && cb.contains(splited[2])) {
                                                cb.incNrReview(splited[2]);
                                                cu.incNrReview(splited[1]);
                                                return new Review(splited[0],splited[1],splited[2],stars,useful,funny,cool,dateTime,splited[8]);
                                }
            else throw new ReviewInvalidaException();
        else throw new ReviewInvalidaException();
    }

    /**
     *
     * @return copia review
     */
    public Review clone() {
        return new Review(this);
    }

    /**
     *
     * @param s
     * @return validação se não é string vazia
     */
    private boolean validString (String s) {
        return (s.length() > 0);
    }

    /**
     * Valida se o numero de estrelas e valido
     * @param stars
     * @return
     */
    private boolean validStars (float stars) {
        if (stars >= 0 && stars <= 5) return true;
        else return false;
    }

    /**
     * Valudade se um num e maior que 0
     * @param num
     * @return
     */
    private boolean validNum (int num) {
        if (num >= 0) return true;
        else return false;
    }

    /**
     * Valida uma data
     * @param data
     * @return boolean
     */
    private boolean validData (LocalDateTime data) {
        if (data.isAfter(LocalDateTime.of(1992,1,1,0,0)) && data.isBefore(LocalDateTime.now()) ) return true;
        else return false;
    }
    
}

