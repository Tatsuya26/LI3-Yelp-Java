/**
 * Classe que define o objeto user.
 *
 * @author (grupo27)
 * @version (1)
 */



import java.io.Serializable;


public class Users implements Serializable{
    private String user_id;
    private String name;
    private String friends;
    private int nr_reviews;

    /**
     * Construtor vazio de um business
     */
    public Users() {
        this.user_id = "";
        this.name = "";
        this.friends = "";
        this.nr_reviews = 0;
    }

    /**
     * construtor parametrizado do user
     * @param user_id
     * @param name
     */
    public Users(String user_id,String name) {
        this.name = name;
        this.user_id = user_id;
        this.nr_reviews = 0;
    }

    /**
     * construtor parametrizado do user com friends
     * @param user_id
     * @param name
     * @param friends
     */
    public Users(String user_id,String name, String friends) {
        this.name = name;
        this.user_id = user_id;
        this.friends = friends;
        this.nr_reviews = 0;
    }

    /**
     * onstrutor parametrizado de user por outro user
     * @param u
     */
    public Users(Users u) {
        this.name = u.getName();
        this.friends = u.getFriends();
        this.user_id = u.getUser_id();
        this.nr_reviews = u.getNr_Reviews();
    };

    // getters

    /**
     * get nome do user
     * @return nome do user
     */
    public String getName() {
        return this.name;
    }

    /**
     * get id do user
     * @return user id
     */
    public String getUser_id() {
        return this.user_id;
    }

    /**
     * devolve friends
     * @return devolve friends
     */
    public String getFriends() {
        return this.friends;
    }

    /**
     * Devolve numero de reviews
     * @return
     */
    public int getNr_Reviews() {
        return this.nr_reviews;
    }

    //setters

    /**
     * Coloca nome do user
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Coloca friends no user
     * @param friends
     */
    public void setFriends(String friends) {
        this.friends = friends;
    }

    /**
     * Coloca User_id
     * @param user_id
     */
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    /**
     * coloca numero de reviews
     * @param n
     */
    public void setNr_reviews(int n) {
        this.nr_reviews = n;
    }

    /**
     * Devolve a informação de user num objeto String
     * @return User in string format
     */
    public String toString() {
        return "Users{" +
                "user_id='" + this.getUser_id() + '\'' +
                ", name='" + this.getName() + '\'' +
                ", friends='" + this.getFriends() + '\'' +
                '}';
    }

    /**
     *
     * @return user
     */
    public Users clone() {
        return new Users(this);
    }

    /**
     * Apartir de uma string com info do user ler para um objeto user
     * @param s
     * @param lerFriends
     * @return
     * @throws UserInvalidoException
     */
    public Users fromLineToUser(String s, boolean lerFriends) throws UserInvalidoException{
        String[] splited = s.split(";",4);
        if (splited.length == 3 || (splited.length == 4 && splited[3].isEmpty()))
            if (validString(splited[0]) && validID(splited[0]) && validString(splited[1]) && validString(splited[2]))
                if(lerFriends)
                    return new Users(splited[0],splited[1], splited[2]);
                else
                    return new Users(splited[0],splited[1]);
            else throw new UserInvalidoException();
        else throw new UserInvalidoException();
        
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
