/**
 * Modulo de estatistica
 * 
 * @author (grupo27)
 * @version (1)
 */

 
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.*;

public class Estatistica
{
    private Catalogo_users CUsers;
    private Catalogo_businesses CBusiness;
    private Catalogo_reviews CReviews;

    /**
     * Construtor parametrizado
     * @param cUsers
     * @param cBusinesses
     * @param cReviews
     */
    public Estatistica(Catalogo_users cUsers,Catalogo_businesses cBusinesses, Catalogo_reviews cReviews) {
        this.CUsers = cUsers;
        this.CBusiness = cBusinesses;
        this.CReviews = cReviews;
    }

    /**
     * Calculo da primeira estatistica
     * @return Resultado da estatistica1
     */
    public Table estatistica1 () {
        StringBuilder sb = new StringBuilder();
        sb.append("Ficheiro com os negocios : ").append(this.CBusiness.getNomeFicheiro()).append("\n");
        sb.append("Ficheiro com as reviews : ").append(this.CReviews.getNomeFicheiro()).append("\n");
        sb.append("Ficheiro com os utilizadores : ").append(this.CUsers.getNomeFicheiro()).append("\n");
       sb.append("Reviews erradas: " + CReviews.getNumeroReviewsErradas()).append("\n");
       sb.append("Numero de negocios: " + CBusiness.sizeCatalogo()).append("\n");
       sb.append("Negocios avaliados : " + CBusiness.nrBusinessAvaliados()).append("\n");
       sb.append("Negocios nao avaliados : " + CBusiness.nrBusinessNaoAvaliados()).append("\n");
       sb.append ("Numero de users : " + CUsers.sizeCatalogo()).append("\n");
       sb.append("Numero de users que avaliaram: "+ CUsers.nrUsersAvaliadores()).append("\n");
       sb.append("Numero de users inactivos: " + CUsers.nrUsersInactivos()).append("\n");
       sb.append("Reviews sem impacto : " + CReviews.nrReviewsSemImpacto()).append("\n");
       Table t = new Table (0);
       t.setInformacaoExtra(sb.toString());
       return t;
    }

    /**
     * Calculo da segunda estatistica
     * @return Resultado da estatistica2
     */
    public Table estatistica2 () {
        int i = 0;
        long[] nr_reviewMes = new long [12];
        float[] classMediaMes = new float[12];
        int[] usersMes = new int[12];
        for (i = 0; i < 12; i++) {
            int mes = i+1;
            List<Review> l= CReviews.getListReviewsPredicate(r->r.getDate().getMonthValue() == mes);
            Set <String> users= new HashSet<>();
            nr_reviewMes[i] = l.size();
            classMediaMes[i] = (l.stream().map(Review::getStars).reduce(0f, (result,element)-> result + element) / l.size());
            for (Review r: l) {
                String user_id = r.getUser_id();
                if (users.contains(user_id));
                else {
                    usersMes[i]++;
                    users.add(user_id);
                }
            }
        }
        Table t = new Table (4);
        t.addHeader(Arrays.asList("Mes","Numero de reviews","Classificacao media","Users distintos"));
        for (i = 0; i < 12;i++) {
            Month mes = Month.of(i+1);
            List<String> ls = new ArrayList<>();
            ls.add(mes.getDisplayName(TextStyle.FULL, Locale.forLanguageTag("pt-PT")));
            ls.add(nr_reviewMes[i]+"");
            ls.add(classMediaMes[i]+"");
            ls.add(usersMes[i]+"");
            t.addLinha(ls);
        }
        t.setInformacaoExtra("Classificacao media total : "+ CReviews.classificacaoMediaReviews());
        return t;
    }
}
