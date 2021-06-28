import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Classe que vai representar o resultado das queries.
 * 
 * @author (grupo27)
 * @version (1)
 */
public class Table
{
    private List<List<String>> data;
    private List<String> header;
    private int[] tamanhos;
    private int nCol;
    private int paginasParaMostrar;
    private String informacaoExtra;
    
    /**
     * COnstrutor para objetos da classe Table
     */
    public Table(){
        this.data = new ArrayList<>();
        this.header = new ArrayList<>();
        this.nCol = 3;
        this.tamanhos = new int[3];
        for (int i = 0; i < nCol;i++) tamanhos[i]= 0;
        this.informacaoExtra ="";
        this.paginasParaMostrar = 12;
    }

    /**
     * Construtor parametrizado da table
     * @param nCol
     */
    public Table (int nCol) {
        this.data = new ArrayList<>();
        this.header = new ArrayList<>();
        this.nCol = nCol;
        this.tamanhos = new int[nCol];
        for (int i = 0; i < nCol;i++) tamanhos[i]= 0;
        this.informacaoExtra ="";
        this.paginasParaMostrar = 12;
    }

    /**
     * Construtor parametrizado da table
     * @param data
     * @param header
     * @param nCol
     */
    public Table(List<List<String>> data,List<String> header,int nCol) {
        this.nCol = nCol;
        this.tamanhos = new int[this.nCol];
        this.data = new ArrayList<>();
        for (List<String> l: data)
                this.addLinha(l);
        this.header = new ArrayList<>(header);
        this.informacaoExtra ="";
        this.paginasParaMostrar = 12;
    }

    /**
     * Adiciona informação extra
     * @param s
     */
    public void setInformacaoExtra (String s) {
        this.informacaoExtra =s;
    }

    /**
     * Coloca o numero de paginas
     * @param num
     */
    public void setPaginas (int num) {
        this.paginasParaMostrar = num;
    }

    /**
     * Adiciona linha a table
     * @param linha
     */
    public void addLinha (List<String> linha) {
        if (this.nCol != linha.size()) ;
        else {
            this.data.add(linha);
            for (int i = 0;i < linha.size();i++)
                if (this.tamanhos[i] < linha.get(i).length() )
                    this.tamanhos[i] = linha.get(i).length() ;   
        }
    }

    /**
     * Adiciona um header a table com os parametros metidos na query
     * @param head
     */
    public void addHeader (List<String> head){
        if (this.nCol != head.size()) ;
        else {
            this.header = new ArrayList<>(head);
            for (int i = 0;i < head.size();i++)
                if (this.tamanhos[i] < head.get(i).length() )
                    this.tamanhos[i] = head.get(i).length() ; 
        }
    }

    /**
     * Linha me que a paginação para
     * @return
     */
    private String linhaTransicao() {
        StringBuilder sb = new StringBuilder();
        sb.append("+");
        for (int i = 0; i < this.nCol;i++) {
            for (int j = 0; j < this.tamanhos[i]+1;j++)
                sb.append("-");
            sb.append("+");
        }
        sb.append("\n");
        return sb.toString();
    }

    /**
     * Transform table numa string
     * @return Table un string format
     */
    public String toString () {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        if (this.nCol > 0 ) {
        sb.append(this.linhaTransicao());
        for (String s: this.header) {
            sb.append(String.format("|%-"+tamanhos[i]+"s ",s));
            i++;
        }
        sb.append("|");
        sb.append("\n");
        sb.append(this.linhaTransicao());
        for (List<String> l : this.data){
            i = 0;
            for (String s : l) {
                sb.append(String.format("|%-"+tamanhos[i]+"s ",s));
                i++;
            }
            sb.append("|");
            sb.append("\n");
            sb.append(this.linhaTransicao());
        }
        sb.append(this.informacaoExtra).append("\n");
        return sb.toString();
        }
        else return this.informacaoExtra;
    }

    /**
     * Transformação paginação de uma string
     * @param page
     * @return String
     */
    public String pageToString(int page) {
        StringBuilder sb = new StringBuilder();
        sb.append("");
        int paginasDisponiveis = this.data.size()/this.paginasParaMostrar;
        if (page > paginasDisponiveis) {
            return pageToString(paginasDisponiveis);
        }
        if (this.nCol > 0 ) {
            sb.append(this.linhaTransicao());
            int i = 0;
            for (String s: this.header) {
                sb.append(String.format("|%-"+tamanhos[i]+"s ",s));
                i++;
            }
            sb.append("|");
            sb.append("\n");
            sb.append(this.linhaTransicao());
            int pagina = (page - 1) * this.paginasParaMostrar;
            for (int ind = 0; ind < this.paginasParaMostrar;ind++) {
                i = 0;
                for (String s : this.data.get(pagina + ind)){
                    sb.append(String.format("|%-"+tamanhos[i]+"s ",s));
                    i++;
                }
                sb.append("|");
                sb.append("\n");
                sb.append(this.linhaTransicao());
            }
            sb.append(this.informacaoExtra).append("\n");
            sb.append("Pagina ").append(page).append(" de ").append(paginasDisponiveis);
        }
        return sb.toString();
    }

}
