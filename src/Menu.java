


import java.util.List;
import java.util.Scanner;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Escreva a descri��o da classe Menu aqui.
 * 
 * @author (seu nome) 
 * @version (n�mero de vers�o ou data)
 */
public class Menu
{
    String header;
    List<String> opcoes;
    int operacao;
    public Menu(String header, String[] opcoes)
    {
        this.header = header;
        this.opcoes = Arrays.asList(opcoes);
        this.operacao = 0;
    }

    public int getOpcao () {
        return this.operacao;
    }

    public void mostraMenu () {
        System.out.println(this.header);
        for (int i = 0;i < this.opcoes.size();i++) {
                System.out.println((i+1) + " - " + this.opcoes.get(i));
        }
        System.out.println("0 - Sair!");
    }

    public void executaMenu () {
        do {
            this.mostraMenu();
            this.operacao = this.lerOpcaoMenu();
        }
        while (this.operacao == -1);
    }

    public int lerOpcaoMenu() throws InputMismatchException{
        Scanner sc = new Scanner(System.in);
        int op;
        try {
            op = sc.nextInt();
        }
        catch (InputMismatchException e) {
            System.out.println("Nao escreveu um inteiro. Tente novamente.");
            op = -1;
        }
        if (op < 0 ||  op > this.opcoes.size()) {
            System.out.println("Opcao invalida. Tente de novo.");
           op = -1;
        }
        sc.close();
        return op;
    }
}
