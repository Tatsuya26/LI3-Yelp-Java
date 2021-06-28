import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

/**
 * Escreva a descri��o da classe GestReviewsView aqui.
 * 
 * @author (seu nome) 
 * @version (n�mero de vers�o ou data)
 */
public class PerformanceProgramView implements Observer
{
    private Table valueToPrint;
    private PerformanceProgramController controlador;
    private Menu menuInicial,menuFicheiros,menuEstatistica,menuQuerys;

    /**
     * COnstrutor para objetos da classe GestReviewsView
     */

    public PerformanceProgramView (PerformanceProgramController controlador) {
        String[] opcoes1 = {"Ler ou guardar ficheiros","Querys"};
        String[] opcoes2 = {"Carregar estado a partir de ficheiro objeto","Guardar estado num ficheiro objeto","Carregar estado a partir de um ficheiro texto","Ativar carregamento dos friends"};
        String[] opcoes3 = {"Query 1","Query 2","Query 3","Query 4","Query 5","Query 6","Query 7","Query 8","Query 9","Query 10"};
        this.valueToPrint = new Table(0);
        this.controlador = controlador;
        this.menuInicial = new Menu("--------------PerformanceProgram------------", opcoes1);
        this.menuFicheiros = new Menu("--------------PerformanceProgram------------", opcoes2);
        this.menuQuerys = new Menu("--------------PerformanceProgram------------", opcoes3);
    }

    public void update (Observable ob,Object table) {
        this.valueToPrint = (Table) table;
    }

    public void run () {
        do {
            this.menuInicial.executaMenu();
                switch (this.menuInicial.getOpcao()){
                    case 1: this.runGestaoFicheiros();
                                break;
                    case 2: this.runGestaoQuerys();
                            break;
                }
            System.out.println();
        }
        while (this.menuInicial.getOpcao() != 0);
    }

    private void runGestaoFicheiros () {
        do {
            this.menuFicheiros.executaMenu();
                switch(this.menuFicheiros.getOpcao()){
                    case 1: this.carregarEstado();
                            break;
                    case 2: this.guardarEstado();
                            break;
                    case 3: this.carregarEstadoTexto();
                            break;
                    case 4: this.setFriends();
                            break;
                }
                System.out.println();
        }
        while (this.menuFicheiros.getOpcao() != 0);
    }

    private void runGestaoQuerys() {
        do {
            this.menuQuerys.executaMenu();
                switch(this.menuQuerys.getOpcao()){
                    case 1: this.query1();
                            break;
                    case 2: this.query2();
                            break;
                    case 3: this.query3();
                            break;
                    case 4: this.query4();
                            break;
                    case 5: this.query5();
                            break;
                    case 6: this.query6();
                            break;
                    case 7: this.query7();
                            break;
                    case 8: this.query8();
                            break;
                    case 9: this.query9();
                            break;
                    case 10: this.query10();
                            break;
                }
                System.out.println();
        }
        while (this.menuQuerys.getOpcao() != 0);
    }

    private void carregarEstado() {
        List<String> args = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        System.out.print("Indique o nome do ficheiro onde se encontra o estado a carregar:");
        String nomeF = sc.nextLine();
        args.add(nomeF);
        Memory.start();
        Crono.start();
        this.controlador.setComando(1);
        this.controlador.processaComando(args);
        Crono.printElapsedTime();
        Memory.printMemory();
        System.out.println(valueToPrint.toString());
        sc.close();
    }

    private void guardarEstado() {
        List<String> args = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        System.out.print("Indique o nome do ficheiro onde pretende guardar o estado:");
        String nomeF = sc.nextLine();
        args.add(nomeF);
        Memory.start();
        Crono.start();
        this.controlador.setComando(2);
        this.controlador.processaComando(args);
        Crono.printElapsedTime();
        Memory.printMemory();
        System.out.println(valueToPrint.toString());
        sc.close();
    }

    private void carregarEstadoTexto() {
        List<String> args = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        System.out.print("Indique o nome do ficheiro onde se encontra o texto para os negocios:");
        String nomeF = sc.nextLine();
        args.add(nomeF);
        System.out.print("Indique o nome do ficheiro onde se encontra o texto para as reviews:");
        nomeF = sc.nextLine();
        args.add(nomeF);
        System.out.print("Indique o nome do ficheiro onde se encontra o texto para os utilizadores:");
        nomeF = sc.nextLine();
        args.add(nomeF);
        Memory.start();
        Crono.start();
        this.controlador.setComando(3);
        this.controlador.processaComando(args);
        Crono.printElapsedTime();
        Memory.printMemory();
        System.out.println(valueToPrint.toString());
        sc.close();
    }

    private void setFriends () {
        this.controlador.setComando(14);
        this.controlador.processaComando(new ArrayList<>());
        if (Catalogo_users.getLerFriends())
            System.out.println("Friends serão agora carregados na leitura!");
        else  System.out.println("Friends deixaram de ser carregados na leitura!");
    }

    private void query1() {
        Scanner sc = new Scanner(System.in);
        Memory.start();
        Crono.start();
        this.controlador.setComando(4);
        this.controlador.processaComando(new ArrayList<>());
        Crono.printElapsedTime();
        Memory.printMemory();
        sc.close();
    }

    private void query2() {
        List<String> args = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        System.out.print("Insira o mes:");
        try{
            args.add(Integer.toString(sc.nextInt()));
            System.out.print("Insira o ano:");
            args.add(Integer.toString(sc.nextInt()));
            Memory.start();
            Crono.start();
            this.controlador.setComando(5);
            this.controlador.processaComando(args);
            Crono.printElapsedTime();
            Memory.printMemory();
        }
        catch (InputMismatchException e) {
            System.out.println("Nao inseriu um inteiro para o mes ou para o ano!");
        }
        sc.close();
    }

    private void query3() {
        List<String> args = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        System.out.print("Insira o identificador do utilizador:");
        args.add(sc.nextLine());
        Memory.start();
        Crono.start();
        this.controlador.setComando(6);
        this.controlador.processaComando(args);
        Crono.printElapsedTime();
        Memory.printMemory();
        sc.close();
    }

    private void query4() {
        List<String> args = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        System.out.print("Insira o identificador do negocio:");
        args.add(sc.nextLine());
        Memory.start();
        Crono.start();
        this.controlador.setComando(7);
        this.controlador.processaComando(args);
        Crono.printElapsedTime();
        Memory.printMemory();
        sc.close();
    }

    private void query5() {
        List<String> args = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        System.out.print("Insira o identificador do utilizador:");
        args.add(sc.nextLine());
        Memory.start();
        Crono.start();
        this.controlador.setComando(8);
        this.controlador.processaComando(args);
        Crono.printElapsedTime();
        Memory.printMemory();
        sc.close();
    }

    private void query6() {
        List<String> args = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        System.out.print("Insira um inteiro :");
        try{
            args.add(Integer.toString(sc.nextInt()));
            Memory.start();
            Crono.start();
            this.controlador.setComando(9);
            this.controlador.processaComando(args);
            Crono.printElapsedTime();
            Memory.printMemory();
        }
        catch (InputMismatchException e) {
            System.out.println("Nao inseriu um inteiro!");
        }
        sc.close();
    }

    private void query7() {
        Scanner sc = new Scanner(System.in);
        Memory.start();
        Crono.start();
        this.controlador.setComando(10);
        this.controlador.processaComando(new ArrayList<>());
        Crono.printElapsedTime();
        Memory.printMemory();
        sc.close();
    }

    private void query8() {
        List<String> args = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        System.out.print("Insira um inteiro :");
        try{
            args.add(Integer.toString(sc.nextInt()));
            Memory.start();
            Crono.start();
            this.controlador.setComando(11);
            this.controlador.processaComando(args);
            Crono.printElapsedTime();
            Memory.printMemory();
        }
        catch (InputMismatchException e) {
            System.out.println("Nao inseriu um inteiro!");
        }
        sc.close();
    }

    private void query9() {
        List<String> args = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        System.out.print("Insira um inteiro:");
        try{
            args.add(Integer.toString(sc.nextInt()));
            System.out.print("Insira o identificador do negocio: ");
            String s = sc.next();
            args.add(s);
            Memory.start();
            Crono.start();
            this.controlador.setComando(12);
            this.controlador.processaComando(args);
            Crono.printElapsedTime();
            Memory.printMemory();
        }
        catch (InputMismatchException e) {
            System.out.println("Nao inseriu um inteiro!");
        }
        sc.close();
    }

    private void query10() {
        Scanner sc = new Scanner(System.in);
        Memory.start();
        Crono.start();
        this.controlador.setComando(13);
        this.controlador.processaComando(new ArrayList<>());
        Crono.printElapsedTime();
        Memory.printMemory();
        sc.close();
    }

}
