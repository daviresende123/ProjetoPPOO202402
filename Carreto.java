/**
 * Representa um carreto específico.
 */
public class Carreto extends Veiculo {

    public Carreto(Localizacao localizacao) {
        super(localizacao, "/Imagens/carreto.png"); // Caminho para a imagem
    }

    @Override
    public void setLocalizacaoAtual(Localizacao destino) {
        // Lógica para mover o carreto
        super.setLocalizacaoAtual(getLocalizacaoAtual().proximaLocalizacao(destino));
    }

    

}