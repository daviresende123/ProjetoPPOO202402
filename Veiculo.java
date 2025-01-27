import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * Representa os veiculos da simulacao.
 * @author David J. Barnes and Michael Kolling and Luiz Merschmann
 */
public class Veiculo {
    private Localizacao localizacaoAtual;
    private Localizacao localizacaoDestino;
    private Image imagem;
    private boolean deveSerRemovido;

    public Veiculo(Localizacao localizacao) {
        this.localizacaoAtual = localizacao;
        this.localizacaoDestino = new Localizacao(localizacao.getX(), 0); // Destino fixo: y = 0
        this.imagem = new ImageIcon(getClass().getResource("Imagens/veiculo.png")).getImage();
        this.deveSerRemovido = false;
    }

    public Localizacao getLocalizacaoAtual() {
        return localizacaoAtual;
    }

    public void setLocalizacaoAtual(Localizacao localizacaoAtual) {
        this.localizacaoAtual = localizacaoAtual;
    }

    public Localizacao getLocalizacaoDestino() {
        return localizacaoDestino;
    }

    public Image getImagem() {
        return imagem;
    }

    public boolean deveSerRemovido() {
        return deveSerRemovido;
    }

    public void executarAcao() {
        if (localizacaoAtual.getY() == 0) {
            deveSerRemovido = true;
        } else {
            Localizacao proximaLocalizacao = localizacaoAtual.proximaLocalizacao(localizacaoDestino);
            setLocalizacaoAtual(proximaLocalizacao);
        }
    }
}