 

 

 



import java.util.function.Predicate;
import java.util.*;
import java.time.format.TextStyle;
import java.util.AbstractMap.SimpleEntry;
import java.util.stream.Collectors;


import java.io.NotActiveException;
import java.text.DateFormatSymbols;
import java.time.Month;

/**
 * Escreva a descri��o da classe Querys aqui.
 * 
 * @author (seu nome) 
 * @version (n�mero de vers�o ou data)
 */
public class Querys
{
    private Catalogo_users CUsers;
    private Catalogo_businesses CBusiness;
    private Catalogo_reviews CReviews;

    /**
     * COnstrutor para objetos da classe Querys
     */
    public Querys(Catalogo_users cUsers,Catalogo_businesses cBusinesses, Catalogo_reviews cReviews) {
        this.CUsers = cUsers;
        this.CBusiness = cBusinesses;
        this.CReviews = cReviews;
    }
    
    public Table query1(){
        Predicate<Business> p = b -> b.getNrReviews() == 0;
        Comparator<String> comp = (s1,s2) -> s1.compareToIgnoreCase(s2);
        Map<String,Business> arvoreBusiness = new TreeMap<>(comp);
        for (Business b: this.CBusiness.filterCat(p).values()) {
            arvoreBusiness.putIfAbsent(b.getBusiness_id(),b);
        }
        Table t = new Table(1);
        t.addHeader(Arrays.asList("Negocios"));
        int n = 0;
        for(Map.Entry<String, Business> entry: arvoreBusiness.entrySet()){
            List<String> lb = new ArrayList<>();
            lb.add(entry.getKey());
            t.addLinha(lb);
            n++;
        }
        t.setInformacaoExtra("Total de negocios nao avaliados :"+n);
        return t;
    }

    public Table query2(int mes, int ano) throws DataNaoValidaException{
        if(mes < 1 || mes > 12 || ano < 1992 || ano > 2021)
            throw new DataNaoValidaException("Mes ou ano inseridos nao sao validos!");
        
        Predicate<Review> p = r -> r.getDate().getMonthValue() == mes && r.getDate().getYear() == ano;
        Map<String, Review> filtered = this.CReviews.filterCat(p);
        Set<String> users = new TreeSet<>();

        for(Map.Entry<String, Review> entry: filtered.entrySet())
            users.add(entry.getValue().getUser_id());
        Table t = new Table (0);
        StringBuilder sb = new StringBuilder();
        sb.append("Numero total global reviews: " + filtered.size()).append("\n");
        sb.append("Numero total global users: " + users.size()).append("\n");
        t.setInformacaoExtra(sb.toString());
        return t;
    }
    
    
    public Table query3(String User_id) {
        Map<Integer,Map<String,RV>> result = new TreeMap<>();
        Map<Integer,Integer> nrMes = new HashMap<>();
        for (int i= 1;i <=12;i++) {
            result.put(i,new HashMap<>());
            nrMes.put(i,0);
        }
        Users u;
        StringBuilder output = new StringBuilder();
        try {
            u = (Users) this.CUsers.get_by_key(User_id);
            Predicate<Review> p= r-> r.getUser_id().equals(u.getUser_id());
            for(Review r : this.CReviews.filterCat(p).values()) {
                int month = r.getDate().getMonthValue();
                if (nrMes.containsKey(month)){
                    int n = nrMes.get(month).intValue();
                    n++;
                    nrMes.replace(month,n);
                }
                else nrMes.put(month,1);
                if(result.containsKey(month)) {
                    if (result.get(month).containsKey(r.getBusiness_id()))
                        result.get(month).get(r.getBusiness_id()).addBusiness(r);
                    else result.get(month).put(r.getBusiness_id(),new RV(r.getBusiness_id(), r));
                } 
                else {
                    result.put(month, new HashMap<>());
                    result.get(month).put(r.getBusiness_id(),new RV(r.getBusiness_id(), r));
                }
            }
        } catch (NotCorrectObject e) {
            output.append(e.getMessage());
        }
        Table t = new Table(4);
        t.addHeader(Arrays.asList("Mes","Total de reviews","Reviews distintas","Nota media"));        
            for (int i = 1;i <= 12;i++) {
                List<String> ls = new ArrayList<>();
                int negociosMesDistintos = result.get(i).size();
                int negociosMesTotal = nrMes.get(i);
                float stars = 0;
                for (RV r : result.get(i).values())
                    stars += r.getStars();
                float media = stars / negociosMesTotal;
                Month mes = Month.of(i);
                ls.add(mes.getDisplayName(TextStyle.FULL, Locale.forLanguageTag("pt-PT")));
                ls.add(""+negociosMesTotal);
                ls.add(""+negociosMesDistintos);
                ls.add(""+media);
                t.addLinha(ls);
            }
        return t;
    }

    /*
    Dado o código de um negócio, determinar, mês a mês, quantas vezes foi avaliado,
por quantos users diferentes e a média de classificação;
     */

    /*
    Algoritmo:
        Percorrer reviews
        Criar um HashMap em que cada chave corresponde ao mês ao qual foi avaliado
        cada mes tem um hash map(chave:user_id) em que tem um struct rv
        Ao percorrer as reviews criar esta hash com os dados todos organizados por mês e depois por userid
     */
    public Table query4(String business_id) {
        Map<Integer,Map<String,RV>> b_by_month = new HashMap<>();
        Predicate<Review> p = r-> r.getBusiness_id().equals(business_id);
        for(Review r: this.CReviews.filterCat(p).values()) {
                int month = r.getDate().getMonthValue();
                String u_id = r.getUser_id();
                //adicionar à hash no respetivo mês
                if(b_by_month.containsKey(month)) {
                    if(b_by_month.get(month).containsKey(u_id))  b_by_month.get(month).get(u_id).addBusiness(r);
                    else b_by_month.get(month).putIfAbsent(u_id,new RV(u_id,r));
                }
                else {
                    Map<String,RV> aux = new HashMap<>();
                    aux.putIfAbsent(u_id,new RV(u_id,r));
                    b_by_month.putIfAbsent(month,aux);
                }
        }
        //passar para string
        Table t = new Table(4);
        t.addHeader(Arrays.asList("Mes","Total de avaliacoes","Users distintos","Media de estrelas"));
        for(Map.Entry<Integer,Map<String, RV>> m: b_by_month.entrySet()) {
            float stars = 0;
            int num_reviews = 0;
           List<String> ls = new ArrayList<>();
           for(RV r: b_by_month.get(m.getKey()).values()) {
               stars += r.getStars();
               num_reviews += r.getNum_businesses();
            }
            ls.add(Month.of(m.getKey()).getDisplayName(TextStyle.FULL, Locale.forLanguageTag("pt-PT")));
            ls.add(""+num_reviews);
            ls.add(""+m.getValue().size());
            ls.add(""+ stars/num_reviews);
            t.addLinha(ls);
        }
        return t;
    }
    
    /*
        Algoritmo
            -percorrer reviews e comparar com user_id
            -Criar uma lista com todos os negocios
            -ordenar a lista

     */

    
    public Table query5(String user_id) {
        Comparator<RV> c = (r1,r2)-> (r2.getNum_businesses() - r1.getNum_businesses() == 0) ? r1.getID().compareToIgnoreCase(r2.getID()) : (r2.getNum_businesses() - r1.getNum_businesses());
        Map<String,RV> list_business = new HashMap<>();
        Predicate<Review> p= r-> r.getUser_id().equals(user_id);
        for(Review r: this.CReviews.filterCat(p).values()) {
                Business b;
                try {
                    b = (Business) this.CBusiness.get_by_key(r.getBusiness_id());
                    String b_name = b.getName();
                    if(list_business.containsKey(b_name)) {
                        list_business.get(b_name).addBusiness(r);
                    } else {
                        RV acc = new RV(b_name,r);
                        list_business.putIfAbsent(b_name,acc);
                    }
                } catch (NotCorrectObject e) {
                }
        }
        List<RV> final_list = new ArrayList<>(list_business.values());
        final_list.sort(c);
        Table t = new Table(2);
        t.addHeader(Arrays.asList("Nome dos negocios","Numero de avaliacoes"));
        for (RV r : final_list){
            List<String> ls = new ArrayList<>();
            ls.add(r.getID());
            ls.add(""+r.getNum_businesses());
            t.addLinha(ls);
        }
        return t;
    }
     
    /*
     * Determinar o conjunto dos X negocios mais avaliados (com mais reviews) em cada
    ano, indicando o numero total de distintos utilizadores que o avaliaram (X e um
    inteiro dado pelo utilizador);
     */
    public Table query6 (int top) {
        Map <Integer,Map<String,RV>> negociosAno = new HashMap<>();
        Map <Integer,Map<String,Set<String>>> busAnoUsers = new HashMap<>();
        CloneIteratorReview it = this.CReviews.initIterator();
        while(it.hasNext()) {
            Review r = it.next();
            int ano = r.getDate().getYear(); String bus_id = r.getBusiness_id(); String user_id = r.getUser_id();
            if (negociosAno.containsKey(ano)) {
                if (negociosAno.get(ano).containsKey(bus_id)) {
                    negociosAno.get(ano).get(bus_id).addBusiness(r);
                }
                else {
                    RV business = new RV(bus_id, r);
                    negociosAno.get(ano).put(bus_id, business);
                }
            }
            else {
                negociosAno.put(ano, new HashMap<>());
                RV business = new RV(bus_id, r);
                negociosAno.get(ano).put(bus_id, business);
            }
            if (busAnoUsers.containsKey(ano)) {
                if (busAnoUsers.get(ano).containsKey(bus_id)) {
                }
                else {
                    busAnoUsers.get(ano).put(bus_id,new HashSet<>());
                }
            }
            else {
                busAnoUsers.put(ano, new HashMap<>());
                busAnoUsers.get(ano).put(bus_id,new HashSet<>());
            }
            busAnoUsers.get(ano).get(bus_id).add(user_id);
        }
        Comparator<RV> comp = (r1,r2)-> (r2.getNum_businesses() - r1.getNum_businesses() == 0) ? r1.getID().compareTo(r2.getID()) : r2.getNum_businesses() - r1.getNum_businesses();
        Map <Integer,List<String>> topNegociosAno = new TreeMap<>();
        Map <Integer,Map<String,Integer>> busAnoUserDistintos = new HashMap<>();
        for (Map.Entry<Integer,Map<String,Set<String>>> p : busAnoUsers.entrySet()) {
            if (!busAnoUserDistintos.containsKey(p.getKey())) busAnoUserDistintos.put(p.getKey(), new HashMap<>());
            for (Map.Entry<String,Set<String>> par : p.getValue().entrySet()) {
                busAnoUserDistintos.get(p.getKey()).put(par.getKey(), par.getValue().size());
            }
        }
        for (Map.Entry<Integer,Map<String,RV>> p : negociosAno.entrySet()) {
            List<RV> rvs = new ArrayList<>();
            for (RV r : p.getValue().values()) 
                rvs.add(r);
            rvs.sort(comp);
            if (rvs.size() >= top) rvs = rvs.subList(0, top);
            List<String> negocios = rvs.stream().map(r->r.getID()).collect(Collectors.toList());
            topNegociosAno.put(p.getKey(),negocios);
        }
        Table t = new Table(top+1);
        List<String> header = new ArrayList<>();
        header.add("Ano");
        for (int i = 1;i < top + 1;i++)
            header.add("Negocio "+i);
        t.addHeader(header);
        for (Map.Entry<Integer,List<String>> p : topNegociosAno.entrySet()) {
            List<String> ls = new ArrayList<>();
            ls.add(""+p.getKey());
            for (String b : p.getValue())
                ls.add(b + " | " + busAnoUserDistintos.get(p.getKey()).get(b));
            t.addLinha(ls);
        }
        return t;
    }

    /*
     * Determinar, para cada cidade, a lista dos três mais famosos negócios em termos de
        número de reviews;
     */

     public Table query7 () {
         Map<String,List<Business>> negociosCidade = new TreeMap<>();
         Predicate<Business> p = b-> b.getNrReviews() > 0;
         for (Business b : this.CBusiness.filterCat(p).values()) {
            String cidade = b.getCity();
            if (!negociosCidade.containsKey(cidade)) {
                 negociosCidade.put(cidade, new ArrayList<>());
                }
            negociosCidade.get(cidade).add(b);
         }

         Comparator<Business> comp = (b1,b2) -> (b2.getNrReviews() - b1.getNrReviews() == 0) ? b1.getBusiness_id().compareToIgnoreCase(b2.getBusiness_id()) : b2.getNrReviews() - b1.getNrReviews();
         for (Map.Entry<String,List<Business>> par : negociosCidade.entrySet()) {
            par.getValue().sort(comp);
            List<Business> top = par.getValue().stream().limit(3).collect(Collectors.toList());
            negociosCidade.replace(par.getKey(), top);
           }
        Table t = new Table(4);
            t.addHeader(Arrays.asList("Cidade","Negocio1","Negocio2","Negocio3"));
        for (Map.Entry<String,List<Business>> par : negociosCidade.entrySet()) {
            List<String> ls = new ArrayList<>();
            ls.add(par.getKey());
            for (Business b: par.getValue())
                ls.add(b.getBusiness_id() + " " + b.getNrReviews());
            while (ls.size() < 4) {
                ls.add("");
            }
                t.addLinha(ls);
        }
        return t;
    }
    
    public Table query8(int n){
        if(n > 0){
            Collection<Review> cat = this.CReviews.getValues();
            Map<String, List<String>> numPorUser = new HashMap<>();

            CloneIteratorReview iter1 = this.CReviews.initIterator();
            while(iter1.hasNext()){
                Review r = iter1.next();
                if(numPorUser.containsKey(r.getUser_id()) == true){
                    if(numPorUser.get(r.getUser_id()).contains(r.getBusiness_id()) == false){
                        List<String> l = numPorUser.get(r.getUser_id());
                        l.add(r.getBusiness_id());
                        numPorUser.replace(r.getUser_id(), l);
                    }
                }
                else{
                    List<String> l = new ArrayList<>();
                    l.add(r.getBusiness_id());
                    numPorUser.put(r.getUser_id(), l);
                }
                r = null;
            } 

            Comparator<SimpleEntry<String, Integer>> c = (e1, e2) -> (!e1.getValue().equals(e2.getValue())? 
                                                                        e2.getValue() - e1.getValue(): e1.getKey().compareTo(e2.getKey()));


            Set<SimpleEntry<String, Integer>> npuSorted = new TreeSet<>(c);
            for(Map.Entry<String, List<String>> entry: numPorUser.entrySet()){
                SimpleEntry<String, Integer> new_entry = new AbstractMap.SimpleEntry<String, Integer>(entry.getKey(), entry.getValue().size());
                npuSorted.add(new_entry);
            }


            List<SimpleEntry<String, Integer>> r = new ArrayList<>();
            Iterator<SimpleEntry<String, Integer>> iter2 = npuSorted.iterator();
            int i = 0;
            while(i < n && iter2.hasNext()){
                SimpleEntry<String, Integer> entry = iter2.next();
                r.add(entry);
                i++;
            }
            Table t = new Table(2);
            t.addHeader(Arrays.asList("ID User", "Numero de negocios"));
            for(SimpleEntry<String, Integer> entry: r){
                List<String> ls = new ArrayList<>();
                ls.add(entry.getKey());
                ls.add(""+entry.getValue());
                t.addLinha(ls);
            }

            return t;
        }
        return new Table(0);
    }

    public Table query9(int n, String id){
        if(n > 0){
            Predicate<Review> p = b -> b.getBusiness_id().equals(id);
            Map<String, Review> filtered = this.CReviews.filterCat(p);
            Map<String, List<Float>> infoPorUser = new HashMap<>();

            int j = 0;

            for(Review r: filtered.values()){
                if(infoPorUser.containsKey(r.getUser_id()) == true){
                    List<Float> l = infoPorUser.get(r.getUser_id());
                    l.set(0, l.get(0) + 1);
                    l.set(1, l.get(1) + r.getStars());
                    infoPorUser.replace(r.getUser_id(), l);
                }
                else{
                    List<Float> l = new ArrayList<>();
                    float one = (float) 1.0;
                    l.add(one);
                    l.add(r.getStars());
                    infoPorUser.put(r.getUser_id(), l);
                }
                j++;
          }

          Comparator<SimpleEntry<String, List<Float>>> c = (se1, se2) -> {
                                                                    if (!se1.getValue().get(0).equals(se2.getValue().get(0))){
                                                                        return  Math.round(se2.getValue().get(0) - se1.getValue().get(0));
                                                                    }
                                                                    else{
                                                                        return se1.getKey().compareTo(se2.getKey());
                                                                    }
                                                                };
          Set<SimpleEntry<String, List<Float>>> mediaPorUser = new TreeSet<>(c);
          
          j = 0;
          for(Map.Entry<String, List<Float>> entry: infoPorUser.entrySet()){
              List<Float> l = entry.getValue();
              l.set(1, l.get(1) / l.get(0));
              mediaPorUser.add(new AbstractMap.SimpleEntry<String, List<Float>>(entry.getKey(), l));
              j++;
            }

            Iterator<SimpleEntry<String, List<Float>>> iter = mediaPorUser.iterator();
            List<SimpleEntry<String, Float>> r = new ArrayList<>();
            int i = 0;
            while(i < n && iter.hasNext()){
                SimpleEntry<String, List<Float>> entry = iter.next();
                SimpleEntry<String, Float> addValue = new AbstractMap.SimpleEntry<String, Float>(entry.getKey(), entry.getValue().get(1));
                r.add(addValue);
                i++;
            }

            Table t = new Table(2);
            t.addHeader(Arrays.asList("ID User", "Media de classificacao"));
            for(SimpleEntry<String, Float> entry: r){
                List<String> ls = new ArrayList<>();
                ls.add(entry.getKey());
                ls.add(""+entry.getValue());
                t.addLinha(ls);
            }
            return t;
        }
        return new Table(0);
    }

    public Table query10(){
        Map<String, Map<String, Map<String, List<Float>>>> bigMap = new HashMap<>();

        CloneIteratorReview iter1 = this.CReviews.initIterator();
        while(iter1.hasNext()){
            Review r = iter1.next();
            Business b;
            try {
                b = (Business) this.CBusiness.get_by_key(r.getBusiness_id());
                String state = b.getState();
                if(bigMap.containsKey(state) == true){
                    Map<String, Map<String, List<Float>>> city = bigMap.get(state);
                    if(city.containsKey(b.getCity()) == true){
                        Map<String, List<Float>> reviewedBUSINESS = city.get(b.getCity());
                        if(reviewedBUSINESS.containsKey(b.getBusiness_id()) == true){
                            List<Float> l = reviewedBUSINESS.get(b.getBusiness_id());
                            l.set(0, l.get(0) + 1);
                            l.set(1, l.get(1) + r.getStars());
                            float media = l.get(1) / l.get(0);
                            l.set(2, media);
                            reviewedBUSINESS.replace(b.getBusiness_id(), l);
                            
                        }
                        else{
                            List<Float> l = new ArrayList<>();
                            float one = (float) 1.0;
                            l.add(one);
                            l.add(r.getStars());
                            l.add(r.getStars());
                            reviewedBUSINESS.put(b.getBusiness_id(), l);
                        }
                    }
                    else{
                        List<Float> l = new ArrayList<>();
                        float one = (float) 1.0;
                        l.add(one);
                    l.add(r.getStars());
                    l.add(r.getStars());
                    Map<String, List<Float>> reviewedBUSINESS = new HashMap<>();
                    reviewedBUSINESS.put(b.getBusiness_id(), l);
                    city.put(b.getCity(), reviewedBUSINESS);
                }
            }
            else{
                List<Float> l = new ArrayList<>();
                float one = (float) 1.0;
                l.add(one);
                l.add(r.getStars());
                l.add(r.getStars());
                Map<String, List<Float>> reviewedBUSINESS = new HashMap<>();
                reviewedBUSINESS.put(b.getBusiness_id(), l);
                Map<String, Map<String, List<Float>>> cities = new HashMap<>();
                cities.put(b.getCity(), reviewedBUSINESS);
                bigMap.put(state, cities);
            }
            b = null;
            r = null;
        }
        catch(Exception e){
        }
    }
        Table t = new Table(4);
        t.addHeader(Arrays.asList("Estado","Cidade","Negocio","Media"));
        for(Map.Entry<String, Map<String, Map<String, List<Float>>>> entry1: bigMap.entrySet()){
            for(Map.Entry<String, Map<String, List<Float>>> entry2: entry1.getValue().entrySet()){
                for(Map.Entry<String, List<Float>> entry3: entry2.getValue().entrySet()){
                    List<String> ls = new ArrayList<>();
                    ls.add(entry1.getKey());
                    ls.add(entry2.getKey());
                    ls.add(entry3.getKey());
                    ls.add(""+entry3.getValue().get(2));
                    t.addLinha(ls);
                }
            }
        }
        return t;
    }
}
