/**
 * Classe que vai representar a exception quando um business for invalido
 *
 * @author (grupo27)
 * @version (1)
 */

public class ReviewInvalidaException extends Exception
{

    /**
     * COnstrutor para objetos da classe ReviewInvalidaException
     */
    public ReviewInvalidaException()
    {
        super();
    }

    /**
     * @param msg
     * COnstrutor para objetos da classe ReviewInvalidaException
     */
    public ReviewInvalidaException(String msg) {
        super(msg);
    }
}
