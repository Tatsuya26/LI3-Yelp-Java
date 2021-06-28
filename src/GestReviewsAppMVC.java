

import java.util.concurrent.*;
import java.io.IOException;

public class GestReviewsAppMVC {
    private GestReviews model;
    private GestReviewsController controlador;
    private GestReviewsView view;

    public GestReviewsAppMVC () {
        this.model = new GestReviews();
        Crono.start();
        try {
            this.model.loadGestReviewsFromFiles( "../input_files/business_full.csv",
                                "../input_files/reviews_1M.csv",
                                "../input_files/users_full.csv");
        } catch (StructureAlreadyExists | IOException | NotCorrectObject e) {
            System.out.println(e.getMessage());
        }
        Crono.printElapsedTime();
        this.controlador = new GestReviewsController(this.model);
        this.view = new GestReviewsView(this.controlador);
        model.addObserver(this.controlador);
        this.controlador.addObserver(this.view);
    }
    
    public static void main(String[] args) throws IOException, StructureAlreadyExists, NotCorrectObject {
        new GestReviewsAppMVC().run();
    }

    private void run () { 
        this.view.run();
    }

}
