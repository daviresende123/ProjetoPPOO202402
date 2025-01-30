import java.util.ArrayList;
import java.util.List;

/**
 * Representa um mapa com todos os itens que participam da simulacao
 * 
 * @author David J. Barnes and Michael Kolling and Luiz Merschmann
 */
public class Mapa {
    private Veiculo[][] itens;
    private List<PontoAbastecimento> pontosAbastecimento; // Lista de pontos de abastecimento
    private int largura;
    private int altura;

    private static final int LARGURA_PADRAO = 35;
    private static final int ALTURA_PADRAO = 35;

    /**
     * Cria mapa para alocar itens da simulacao.
     * 
     * @param largura: largura da área de simulacao.
     * @param altura:  altura da área de simulação.
     */
    public Mapa(int largura, int altura) {
        this.largura = largura;
        this.altura = altura;
        itens = new Veiculo[altura][largura];
        pontosAbastecimento = new ArrayList<>(); // Inicializa a lista
    }

    public void adicionarPontoAbastecimento(PontoAbastecimento ponto) {
        pontosAbastecimento.add(ponto); // Adiciona o ponto à lista
    }

    public List<PontoAbastecimento> getPontosAbastecimento() {
        return pontosAbastecimento; // Retorna a lista de pontos de abastecimento
    }

    /**
     * Cria mapa com tamanho padrao.
     */
    public Mapa() {
        this(LARGURA_PADRAO, ALTURA_PADRAO);
    }

    public void adicionarItem(Veiculo v) {
        itens[v.getLocalizacaoAtual().getX()][v.getLocalizacaoAtual().getY()] = v;
    }

    public void removerItem(Veiculo v) {
        itens[v.getLocalizacaoAtual().getX()][v.getLocalizacaoAtual().getY()] = null;
    }

    public void atualizarMapa(Veiculo v) {
        removerItem(v);
        adicionarItem(v);
    }

    public Veiculo getItem(int x, int y) {
        return itens[x][y];
    }

    public List<Veiculo> getVeiculos() {
        List<Veiculo> veiculos = new ArrayList<>();
        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < largura; j++) {
                if (itens[i][j] != null) {
                    veiculos.add(itens[i][j]);
                }
            }
        }
        return veiculos;
    }

    public int getLargura() {
        return largura;
    }

    public int getAltura() {
        return altura;
    }

}
