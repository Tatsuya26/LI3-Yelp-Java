/**
 *
 *  Utilização de uma estrutura do tipo de dados map que dada uma chave(id de negócio) e um valor retorna
 *  o negócio associado a esse id.
 * @author (grupo27)
 * @version (1)
 */

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.function.Predicate;

/*
    Utilização de uma estrutura do tipo de dados map que dada uma chave(id de negócio) e um valor retorna
o negócio associado a esse id.
 */

public class Catalogo_businesses implements Catalogo ,Serializable{
    private Map<String, Business> catalogo;
    private String nomeF;

    /**
     * Construtor vazio de uma catalogo de business
     */
    public Catalogo_businesses() {
        this.catalogo = new HashMap<>();
        this.nomeF = "";
    }

    /**
     * Copia de um catalogo de business
     * @param c
     */
    public Catalogo_businesses(Catalogo_businesses c) {
        this.catalogo = new HashMap<>();
        for (Business b : c.getCatalogo().values()) this.catalogo.putIfAbsent(b.getBusiness_id(), b.clone());
        this.nomeF = "";
    }

    /**
     * Copia por um map de chaves e negocios
     * @param l
     */
    public Catalogo_businesses(Map<String, Business> l) {
        for (Business b : l.values())
            if (!(this.catalogo.containsKey(b.getBusiness_id())))
                this.catalogo.putIfAbsent(b.getBusiness_id(), b.clone());
        this.nomeF = "";
    }

    /**
     * Retorna o nome do ficheiro
     * @return nome do ficheiro
     */
    public String getNomeFicheiro() {
        return this.nomeF;
    }

    /**
     *
     * @return copia do catalgo de negocios
     */
    public Map<String, Business> getCatalogo() {
        return this.catalogo.values().stream().map(Business::clone).collect(Collectors.toMap(b->b.getBusiness_id(),b->b));
    }

    /**
     *
     * @return Lista de todos os negócios
     */
    public List<Business> getListBusiness() {
        return this.catalogo.values().stream().map(Business::clone).collect(Collectors.toList());
    }

    /**
     * Dado um objeto verificar se é da classe business e adiconar a estrutura de dados
     * @param o
     * @throws NotCorrectObject
     * @throws StructureAlreadyExists
     */
    public void add(Object o) throws NotCorrectObject, StructureAlreadyExists {
        if (!(o instanceof Business)) throw new NotCorrectObject();
        Business b = (Business) o;
        if (this.catalogo.containsKey(b.getBusiness_id()))
            throw new StructureAlreadyExists("Business already exists!");
        else this.catalogo.putIfAbsent(b.getBusiness_id(), b.clone());
    }

    /**
     * Verifica se a estrutura de dados tem um business identificado por uma dada string
     * @param s
     * @return boolean
     */
    public boolean contains(String s) {
        return this.catalogo.containsKey(s);
    }

    /**
     * Remove um negocio identificado por uma dada String da estrutura de dados
     * @param s
     */
    public void remove(String s) {
        this.catalogo.remove(s);
    }


    /**
     * Devolve uma cópia de um neogocio numa estrutura de dados
     * @param key
     * @return Object
     * @throws NotCorrectObject
     */
    public Object get_by_key(String key) throws NotCorrectObject{
        if (this.catalogo.containsKey(key))
            return (Object) this.catalogo.get(key).clone();
        else throw new NotCorrectObject("Negocio nao existe!");
    }

    /**
     * Devolve o tamanho do catalogo de negocios
     * @return tamanho da estrutura
     */
    public int sizeCatalogo () {
        return this.catalogo.size();
    }


    /**
     * Devolve o numero de negocios avaliados
     * @return numero de negocios avaliados
     */
    public long nrBusinessAvaliados () {
        long nr = this.catalogo.values().stream().filter(b->b.getNrReviews() > 0).count();
        return nr;
    }

    /**
     * Devolve o numero de negocios não avaliados
     * @return numero de negocios não avaliados
     */
    public long nrBusinessNaoAvaliados() {
        long nr = this.catalogo.values().stream().filter(b->b.getNrReviews() == 0).count();
        return nr;
    }

    /**
     * Incrementa o número de reviews de um negócio
     * @return numero de negocios avaliados
     */
    public void incNrReview (String id) {
        Business b = this.catalogo.get(id);
        b.setNrReviews(b.getNrReviews() + 1);
    }

    /*public void loadStructFromFile(String filename) throws FileNotFoundException, IOException, NotCorrectObject, StructureAlreadyExists {
        List<String> lines = new ArrayList<>();
        lines = Files.readAllLines(Paths.get(filename), StandardCharsets.UTF_8);

        Iterator<String> iter = lines.iterator();
        while(iter.hasNext()){
            String s = iter.next();
            Business novo = new Business();
            try {
                novo = novo.fromLineToBusiness(s);
                this.add(novo);
            } 
            catch (BusinessInvalidoException e) {
            }
        }

        lines = null;
    }*/

    /**
     * Lê para memoria principal de um ficheiro um conjunto de negócios
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
        while((line = buffer.readLine()) != null) {
            Business novo = new Business();
            try {
                novo = novo.fromLineToBusiness(line);
                this.add(novo);
            } 
            catch (BusinessInvalidoException e) {
                //
            }
        }
        buffer.close();
        file.close();
    }

    /**
     * Retorna todos os negócios num map que respeiram o predicado dado
     * @param p
     * @return Map<id de negocio, negocio>
     */
    public Map<String, Business> filterCat(Predicate<Business> p){
        return this.catalogo.values().stream().filter(p).collect(Collectors.toMap(Business::getBusiness_id, Business::clone));
    }
}
