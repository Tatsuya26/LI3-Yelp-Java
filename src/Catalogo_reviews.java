/**
 * Classe que vai catalogar todas as reviews
 *  Utilização de uma estrutura do tipo de dados map que dada uma chave(id de negócio) e um valor retorna
 *  o negócio associado a esse id.
 * @author (grupo27)
 * @version (1)
 */








import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.Serializable;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Catalogo_reviews implements Catalogo ,Serializable {
    private Map<String, Review> catalogo;
    private int nr_reviewsErradas;
    private String nomeF;

    /**
     * Construtor vazio do catalogo de reviews
     */
    public Catalogo_reviews() {
        this.catalogo = new HashMap<>();
        this.nr_reviewsErradas = 0;
        this.nomeF = "";
    }

    /**
     * Copia para o catalogo tudo que se encontra no map
     * @param cr
     */
    public Catalogo_reviews(Map <String,Review> cr) {
        for (Review r : cr.values())
            if (!(this.catalogo.containsKey(r.getReview_id())))
                this.catalogo.putIfAbsent(r.getReview_id(), r.clone());
        this.nr_reviewsErradas = 0;
        this.nomeF = "";
    }

    /**
     * Devolve lista de reviews feitas
     * @return Lista de reciews
     */
    public List<Review> getListReviews () {
        return this.catalogo.values().stream().map(Review::clone).collect(Collectors.toList());
    }

    /**
     * Retorna o nome do ficheiro
     * @return nome do ficheiro
     */
    public String getNomeFicheiro() {
        return this.nomeF;
    }

    public List<Review> getListReviewsPredicate (Predicate<Review> p) {
        return this.catalogo.values().stream().filter(p).map(Review::clone).collect(Collectors.toList());
    }

    /**
     * Dado um objeto verificar se é da classe Reviews e adiconar a estrutura de dados
     * @param o
     * @throws NotCorrectObject
     * @throws StructureAlreadyExists
     */
    public void add(Object o) throws NotCorrectObject, StructureAlreadyExists {
        if (!(o instanceof Review)) throw new NotCorrectObject();
        Review r = (Review) o;
        if (this.catalogo.containsKey(r.getReview_id()))
            throw new StructureAlreadyExists("Review already exists!");
        else this.catalogo.putIfAbsent(r.getReview_id(), r.clone());
    }


    /**
     * Verifica se a estrutura de dados tem uma review identificado por uma dada string
     * @param s
     * @return boolean
     */
    public boolean contains(String s) {
        return this.catalogo.containsKey(s);
    }

    /**
     * Remove um review identificado por uma dada String da estrutura de dados
     * @param s
     */
    public void remove(String s) {
        this.catalogo.remove(s);
    }

    /**
     * Devolve uma cópia de uma reviews numa estrutura de dados
     * @param key
     * @return Object
     * @throws NotCorrectObject
     */
    public Object get_by_key(String key) throws NotCorrectObject {
        if (this.catalogo.containsKey(key))
            return (Object) this.catalogo.get(key).clone();
        else throw new NotCorrectObject("Review nao existe!");
    }

    /**
     * Devolve o tamanho do catalogo de reviews
     * @return tamanho da estrutura
     */
    public int sizeCatalogo () {
        return this.catalogo.size();
    }


    /**
     * Devolve o numero de reviews erradas
     * @return numero reviews erradas
     */
    public int getNumeroReviewsErradas () {
        return this.nr_reviewsErradas;
    }

    /**
     * Incrementa o número de reviews de um negócio
     * @return numero de negocios avaliados
     */
    public void incNrReview () {
        this.nr_reviewsErradas++;
    }

    /**
     * Numero de reviews sem impacto
     * @return
     */
    public long nrReviewsSemImpacto () {
        long nr = this.catalogo.values().stream().filter(r-> r.getCool() + r.getFunny() + r.getUseful() == 0).count();
        return nr;
    }

    /**
     * Calcula a media de reviews
     * @return media de reviews
     */
    public float classificacaoMediaReviews () {
        return (this.catalogo.values().stream().map(Review::getStars).reduce(0f, (result,element)-> result + element) / this.sizeCatalogo());
    }

    /*public void loadStructFromFile(String filename,Catalogo_users cu, Catalogo_businesses cb) throws FileNotFoundException, IOException, NotCorrectObject, StructureAlreadyExists {
        List<String> lines = new ArrayList<>();
        lines = Files.readAllLines(Paths.get(filename), StandardCharsets.UTF_8);

        Iterator<String> iter = lines.iterator();
        while(iter.hasNext()){
            String s = iter.next();
            Review novo = new Review();
            try {
                novo = novo.fromLineToReview(s,cu,cb);
                this.add(novo);
            } 
            catch (ReviewInvalidaException e) {
            }
        }

        lines = null;
    }*/

    /**
     * Lê para memoria principal de um ficheiro um conjunto de reviews
     * @param filename
     * @throws FileNotFoundException
     * @throws IOException
     * @throws NotCorrectObject
     * @throws StructureAlreadyExists
     */
    public void loadStructFromFile(String filename,Catalogo_users cu, Catalogo_businesses cb) throws FileNotFoundException, IOException, NotCorrectObject, StructureAlreadyExists {
        String line = "";
        this.nomeF = filename;
        FileReader file = new FileReader(filename);
        BufferedReader buffer = new BufferedReader(file);
        line = buffer.readLine();
        while((line = buffer.readLine()) != null) {
            Review novo = new Review();
            try {
                novo = novo.fromLineToReview(line,cu,cb);
                this.add(novo);
            } catch (ReviewInvalidaException e) {
                this.incNrReview();
            }
        }

        buffer.close();
        file.close();
    }

    /**
     *
     * @return
     */
    public Collection<Review> getValues() {
        return this.catalogo.values();
    }

    /**
     * Retorna todos os reviews num map que respeiram o predicado dado
     * @param p
     * @return Map<id de negocio, negocio>
     */
    public Map<String, Review> filterCat(Predicate<Review> p){
        return this.catalogo.values().stream().filter(p).collect(Collectors.toMap(Review::getReview_id, Review::clone));
    }

    /**
     * Iterador externo para percorrer
     * @return iterator
     */
    public CloneIteratorReview  initIterator () {
        return new CloneIteratorReview(this.catalogo.values());
    }
}
