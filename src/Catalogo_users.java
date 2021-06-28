/**
 *
 *  Utilização de uma estrutura do tipo de dados map que dada uma chave(id de negócio) e um valor retorna
 *  o user associado a esse id.
 * @author (grupo27)
 * @version (1)
 */

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class Catalogo_users implements Catalogo,Serializable{
    private Map<String, Users> catalogo;
    private String nomeF;
    private static boolean lerFriends = false;

    /**
     * Construtor vazio do catalogo de users
     */
    public Catalogo_users() {
        this.catalogo = new HashMap<>();
        this.nomeF = "";
    }

    /**
     * Copia de um catalogo de busin
     * @param c_users
     */
    public Catalogo_users(Map <String,Users> c_users) {
            for (Users u : c_users.values())
                if (!(this.catalogo.containsKey(u.getUser_id())))
                    this.catalogo.putIfAbsent(u.getUser_id(), u.clone());
            this.nomeF = "";
    }

    /**
     * Devolve se o parametro de friends deve ser lido ou nao
     * @return boolean
     */
    public static boolean getLerFriends() {
        return Catalogo_users.lerFriends;
    }

    /**
     * Coloca se o parametro de friends deve ser lido ou nao
     */
    public static void setLerFriends(boolean lerFriends){
        Catalogo_users.lerFriends = lerFriends;
    }

    /**
     * Retorna o nome do ficheiro
     * @return nome do ficheiro
     */
    public String getNomeFicheiro() {
        return this.nomeF;
    }
    /**
     * Dado um objeto verificar se é da classe business e adiconar a estrutura de dados
     * @param o
     * @throws NotCorrectObject
     * @throws StructureAlreadyExists
     */
    public void add(Object o) throws NotCorrectObject, StructureAlreadyExists {
        if (!(o instanceof Users)) throw new NotCorrectObject();
        Users u = (Users) o;
        if (this.catalogo.containsKey(u.getUser_id()))
            throw new StructureAlreadyExists("User already exists!");
        else this.catalogo.putIfAbsent(u.getUser_id(), u.clone());
    }
    /**
     * Verifica se a estrutura de dados tem um  user identificado por uma dada string
     * @param s
     * @return boolean
     */
    public boolean contains(String s) {
        return this.catalogo.containsKey(s);
    }

    /**
     * Remove um user identificado por uma dada String da estrutura de dados
     * @param s
     */
    public void remove(String s) {
        this.catalogo.remove(s);
    }

    /**
     * Devolve uma cópia de um user numa estrutura de dados
     * @param key
     * @return Object
     * @throws NotCorrectObject
     */
    public Object get_by_key(String key) throws NotCorrectObject{
        if (this.catalogo.containsKey(key))
            return (Object) this.catalogo.get(key).clone();
        else throw new NotCorrectObject("User nao existe!");
    }
    /**
     * Devolve o tamanho do catalogo de users
     * @return tamanho da estrutura
     */
    public int sizeCatalogo () {
        return this.catalogo.size();
    }

    /**
     * Devolve o numero de users avaliados
     * @return numero de users avaliados
     */
    public long nrUsersAvaliadores () {
        long nr = this.catalogo.values().stream().filter(u->u.getNr_Reviews() > 0).count();
        return nr;
    }

    /**
     * Devolve o numero de users inativos
     * @return numero de users inativos
     */
    public long nrUsersInactivos () {
        long nr = this.catalogo.values().stream().filter(u->u.getNr_Reviews() == 0).count();
        return nr;
    }
    /**
     * Incrementa o número de reviews de um negócio
     * @return numero de negocios avaliados
     */
    public void incNrReview (String id) {
        Users u = this.catalogo.get(id);
        u.setNr_reviews(u.getNr_Reviews() + 1);
    }

    /**
     * Lê para memoria principal de um ficheiro um conjunto de users
     * @param filename
     * @throws FileNotFoundException
     * @throws IOException
     * @throws NotCorrectObject
     * @throws StructureAlreadyExists
     */
    public void loadStructFromFile(String filename) throws FileNotFoundException, IOException, NotCorrectObject, StructureAlreadyExists {
        String line = "";
        this.nomeF = filename;
        FileReader file = new FileReader(filename);
        BufferedReader buffer = new BufferedReader(file);
        while( (line = buffer.readLine()) != null) {
            Users novo = new Users();
            try {
                novo = novo.fromLineToUser(line, lerFriends);
                this.add(novo);
            } 
            catch (UserInvalidoException e) {
            }
        }
        buffer.close();
        file.close();
    }
    

}
