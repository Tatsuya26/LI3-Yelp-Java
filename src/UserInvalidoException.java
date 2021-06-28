
/**
 * Classe que vai representar a exception quando um user for invalido
 *
 * @author (grupo27)
 * @version (1)
 */

public class UserInvalidoException extends Exception
{

    /**
     * Construtor para objetos da classe UserInvalidoException
     */
    public UserInvalidoException()
    {
        super();
    }

    /**
     *  Construtor para objetos da classe UserInvalidoException
     * @param msg
     */
    public UserInvalidoException(String msg) {
        super(msg);
    }
}
