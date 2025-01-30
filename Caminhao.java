/**
 * Representa um caminhao específico.
 */
public class Caminhao extends Veiculo {

    public Caminhao(Localizacao localizacao) {
        super(localizacao, "/Imagens/caminhao.png"); // Caminho para a imagem
    }

    @Override
    public void setLocalizacaoAtual(Localizacao destino) {
        // Lógica para mover o carreto
        super.setLocalizacaoAtual(getLocalizacaoAtual().proximaLocalizacao(destino));
    }

    @Override
    public boolean deveSerRemovido() {
        // O caminhão nunca é removido enquanto estiver abastecendo
        if (isAbastecendo()) {
            return false; // Nunca remove o caminhão
        }
        return super.deveSerRemovido(); // Comportamento padrão para outros casos
    }
}