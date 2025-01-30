import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * Representa os pontos de abastecimento na simulação.
 * 
 * @author Luiz Merschmann
 */
public class PontoAbastecimento {
    private Localizacao localizacao;
    private Image imagem;

    /**
     * Constrói um ponto de abastecimento em uma localização específica.
     * 
     * @param localizacao Localização do ponto de abastecimento (não pode ser null).
     * @throws IllegalArgumentException se a localização for null.
     */
    public PontoAbastecimento(Localizacao localizacao) {
        this.localizacao = localizacao;
        this.imagem = new ImageIcon(getClass().getResource("Imagens/ponto.png")).getImage();
    }

    public Localizacao getLocalizacao() {
        return localizacao;
    }

    public Image getImagem() {
        return imagem;
    }
}
