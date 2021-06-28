/**
 * Classe que define a API dos metodos que todas as apis de catalogo vão utilizar
 *
 * @author (grupo27)
 * @version (1)
 */

 

public interface Catalogo {
    /**
     * Adicionar objeto à estrutura
     * @param o
     * @throws NotCorrectObject
     * @throws StructureAlreadyExists
     */
    public void add(Object o) throws NotCorrectObject, StructureAlreadyExists;

    /**
     * Verifica se a estrutura de dados tem um objeto identificado por uma dada string
     * @param s
     * @return boolean
     */
    public boolean contains(String s);

    /**
     * Remove um objeto identificado por uma dada String da estrutura de dados
     * @param s
     */
    public void remove(String s);

    /**
     * Devolve uma cópia de um objeto numa estrutura de dados
     * @param key
     * @return Object
     * @throws NotCorrectObject
     */
    public Object get_by_key(String key) throws NotCorrectObject;

    /**
     * Devolve o tamanho do catalogo
     * @return tamanho da estrutura
     */
    public int sizeCatalogo ();

}
