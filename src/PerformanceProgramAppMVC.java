import java.util.concurrent.*;
import java.io.IOException;

public class PerformanceProgramAppMVC {
    private GestReviews model;
    private PerformanceProgramController controlador;
    private PerformanceProgramView view;

    public PerformanceProgramAppMVC () {
        this.model = new GestReviews();
        try {
            Memory.start();
            Crono.start();
            this.model.loadGestReviewsFromFiles( "../input_files/business_full.csv",
                                "../input_files/reviews_1M.csv",
                                "../input_files/users_full.csv");
            Crono.printElapsedTime();
            Memory.printMemory();
        } catch (StructureAlreadyExists | IOException | NotCorrectObject e) {
            System.out.println(e.getMessage());
        }
        this.controlador = new PerformanceProgramController(this.model);
        this.view = new PerformanceProgramView(this.controlador);
        model.addObserver(this.controlador);
        this.controlador.addObserver(this.view);
    }
    
    public static void main(String[] args) throws IOException, StructureAlreadyExists, NotCorrectObject {
        new PerformanceProgramAppMVC().run();
    }

    private void run () { 
        this.view.run();
    }

}
