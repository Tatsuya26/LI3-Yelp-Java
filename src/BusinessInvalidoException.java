
/**
 * Classe que vai representar a exception quando um business for invalido
 *
 * @author (grupo27)
 * @version (1)
 */

public class BusinessInvalidoException extends Exception
{

    /**
     * Construtor para objetos da classe BusinessInvalidoException
     */
    public BusinessInvalidoException()
    {
        super();
    }

    /**
     * Construtor para objetos da classe BusinessInvalidoException
     * @param msg
     */
    public BusinessInvalidoException(String msg) {
        super(msg);
    }
}
