import java.io.IOException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Write a description of class PerformanceProgramController here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class PerformanceProgramController extends Observable implements Observer
{
    private int comando;
    private Table valueFromModel;
    private GestReviews model;
    
    
    public void update (Observable ob,Object valueModel) {
        this.valueFromModel = (Table) valueModel;
    }

    public PerformanceProgramController (GestReviews model) {
        this.comando = 0;
        this.valueFromModel = new Table(0);
        this.model = model;
    }

    private void updateView () {
        this.setChanged();
        this.notifyObservers(this.valueFromModel);
    }

    public int getComando () {
        return this.comando;
    }

    public Table getValueFromModel () {
        return this.valueFromModel;
    }

    public void setComando (int comando) {
        this.comando = comando;
    }

    public void processaComando (List<String> args) {
        switch (this.comando) {
            case 1 : this.carregaEstado(args.get(0));
                    break;
            case 2 : this.guardaEstado (args.get(0));
                    break;
            case 3: this.carregaEstadoTexto (args) ;
                    break;
            case 4: this.query1();
                    break;
            case 5: this.query2(args);
                    break;
            case 6: this.query3(args.get(0));
                    break;
            case 7: this.query4(args.get(0));
                    break;
            case 8: this.query5(args.get(0));
                    break;
            case 9: this.query6(args.get(0));
                    break;
            case 10: this.query7();
                    break;
            case 11: this.query8(args.get(0));
                    break;
            case 12: this.query9(args);
                    break;
            case 13: this.query10();
                    break;
            case 14: this.setFriends();
                    break;
        }
    }

    private void carregaEstado (String nomeF) {
        GestReviews novo = null;
        try {
            novo = GestReviews.carregaEstadoGestReviews(nomeF);
        } catch (ClassNotFoundException | IOException e) {
            valueFromModel = new Table(0);
            valueFromModel.setInformacaoExtra(e.getMessage());
        }
        if (novo != null) {
            this.model = novo;
            novo.addObserver(this);
            valueFromModel = new Table(0);
            valueFromModel.setInformacaoExtra("Estado carregado com sucesso");
        }
        this.updateView();
    }

    private void guardaEstado (String nomeF) {
        try {
            this.model.guardaEstadoGestReviews(nomeF);
            valueFromModel = new Table(0);
            valueFromModel.setInformacaoExtra("Estado guardado com sucesso");
        } catch (IOException e) {
             valueFromModel = new Table(0);
            valueFromModel.setInformacaoExtra(e.getMessage());
        }
        this.updateView();
    }

    private void carregaEstadoTexto(List<String> args) {
        try {
            this.model.reloadGestReviewsFromFiles(args.get(0),args.get(1),args.get(2));
            valueFromModel = new Table(0);
            valueFromModel.setInformacaoExtra("Estado carregado com sucesso a partir dos ficheiros texto");
        } catch (StructureAlreadyExists | IOException | NotCorrectObject e) {
            valueFromModel = new Table(0);
            valueFromModel.setInformacaoExtra(e.getMessage());
        }
        this.updateView();
    }

    private void query1() {
        this.model.query1();
        this.updateView();
    }

    private void query2(List<String> args) {
        int mes = Integer.parseInt(args.get(0)),ano =Integer.parseInt(args.get(1)); 
        this.model.query2(mes,ano);
        this.updateView();
    }

    private void query3(String id) {
        this.model.query3(id);
        this.updateView();
    }

    private void query4(String id) {
        this.model.query4(id);
        this.updateView();
    }

    private void query5(String id) {
        this.model.query5(id);
        this.updateView();
    }

    private void query6(String s) {
        int top = Integer.parseInt(s);
        this.model.query6(top);
        this.updateView();
    }

    private void query7() {
        this.model.query7();
        this.updateView();
    }

    private void query8(String s) {
        int top = Integer.parseInt(s);
        this.model.query8(top);
        this.updateView();
    }

    private void query9(List<String> args) {
        int n =  Integer.parseInt(args.get(0));
        this.model.query9(n,args.get(1));
        this.updateView();
    }

    private void query10() {
        this.model.query10();
        this.updateView();
    }

    private void setFriends () {
        this.model.setLerFriends(!Catalogo_users.getLerFriends());
        this.updateView();
    }

}

