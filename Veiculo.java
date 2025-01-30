import java.awt.Image;
import java.time.Duration;
import java.time.Instant;
import javax.swing.ImageIcon;

/**
 * Classe que representa um veículo no sistema.
 * Um veículo pode se mover, abastecer e colidir com outros objetos no mapa.
 * 
 * @author Davi Gomides, João Ramalho, Eduardo Gomes
 */
public class Veiculo {
    private Localizacao localizacaoAtual;
    private Localizacao localizacaoDestino;
    private Image imagem;
    private boolean deveSerRemovido;
    private Instant tempoInicioAbastecimento; // Timestamp do início do abastecimento
    private Duration tempoParado; // Tempo de parada como Duration
    private boolean abastecendo; // Indica se o veículo está abastecendo

    /**
     * Construtor da classe Veiculo.
     * Inicializa as propriedades do veículo com a localização inicial e a imagem.
     * 
     * @param localizacao Localização inicial do veículo.
     * @param imagem Caminho da imagem a ser usada para representar o veículo.
     */
    public Veiculo(Localizacao localizacao, String imagem) {
        this.localizacaoAtual = localizacao;
        this.localizacaoDestino = new Localizacao(localizacao.getX(), 0); // Destino fixo: y = 0
        this.imagem = new ImageIcon(getClass().getResource(imagem)).getImage();
        this.deveSerRemovido = false;
        this.tempoInicioAbastecimento = null;
        this.tempoParado = Duration.ZERO;
        this.abastecendo = false;
    }

    /**
     * Define o tempo de parada do veículo.
     * 
     * @param tempoParado Tempo de parada do veículo.
     */
    public void setTempoParado(Duration tempoParado) {
        this.tempoParado = tempoParado;
    }

    /**
     * Retorna a localização atual do veículo.
     * 
     * @return Localização atual do veículo.
     */
    public Localizacao getLocalizacaoAtual() {
        return localizacaoAtual;
    }

    /**
     * Define a localização atual do veículo.
     * 
     * @param localizacaoAtual Nova localização do veículo.
     */
    public void setLocalizacaoAtual(Localizacao localizacaoAtual) {
        this.localizacaoAtual = localizacaoAtual;
    }


    /**
     * Retorna a localização de destino do veículo.
     * 
     * @return Localização de destino do veículo.
     */
    public Localizacao getLocalizacaoDestino() {
        return localizacaoDestino;
    }

    /**
     * Retorna a imagem do veículo.
     * 
     * @return Imagem do veículo.
     */
    public Image getImagem() {
        return imagem;
    }

     /**
     * Verifica se o veículo deve ser removido do mapa.
     * 
     * @return true se o veículo deve ser removido, false caso contrário.
     */
    public boolean deveSerRemovido() {
        return deveSerRemovido;
    }

     /**
     * Verifica se o veículo está em processo de abastecimento.
     * 
     * @return true se o veículo está abastecendo, false caso contrário.
     */
    public boolean isAbastecendo() {
        return abastecendo;
    }

    /**
     * Define se o veículo está abastecendo.
     * 
     * @param abastecendo true para indicar que o veículo está abastecendo, false caso contrário.
     */
    public void setAbastecendo(boolean abastecendo) {
        this.abastecendo = abastecendo;
    }

    /**
     * Executa a ação do veículo no mapa, movendo-o ou realizando outras ações como abastecimento.
     * 
     * @param mapa Mapa onde o veículo está localizado.
     */
    public void executarAcao(Mapa mapa) {
        if (abastecendo) {
            // Se o veículo está abastecendo, verifica se o tempo de parada já passou
            if (tempoInicioAbastecimento != null) {
                Duration tempoDecorrido = Duration.between(tempoInicioAbastecimento, Instant.now());
                if (tempoDecorrido.compareTo(tempoParado) >= 0) {
                    deveSerRemovido = true; // Marca o veículo para remoção
                }
            }
        } else {
            Localizacao proximaLocalizacao = localizacaoAtual.proximaLocalizacao(localizacaoDestino);

            if (colidiuComPosto(mapa, proximaLocalizacao)) {
                // Para o veículo uma casa abaixo do posto
                Localizacao posicaoParada = new Localizacao(proximaLocalizacao.getX(), proximaLocalizacao.getY() + 1);
                setLocalizacaoAtual(posicaoParada);

                // Define o tempo de parada (maior para caminhões, menor para carretos)
                if (this instanceof Caminhao) {
                    setTempoParado(Duration.ofSeconds(15)); // 15 segundos para caminhão
                } else {
                    setTempoParado(Duration.ofSeconds(5)); // 5 segundos para carretos
                }

                tempoInicioAbastecimento = Instant.now();
                abastecendo = true; // Marca o veículo como abastecendo
            } else if (colidiuComVeiculo(mapa, proximaLocalizacao)) {
                // Encontra a última posição da fila
                Localizacao ultimaPosicaoFila = encontrarUltimoVeiculoNaFila(mapa, proximaLocalizacao);

                // Para o veículo uma casa abaixo da última posição da fila
                Localizacao posicaoParada = new Localizacao(ultimaPosicaoFila.getX(), ultimaPosicaoFila.getY() + 1);
                setLocalizacaoAtual(posicaoParada);
            } else {
                // Move o veículo normalmente
                setLocalizacaoAtual(proximaLocalizacao);
            }
        }
    }

    /**
     * Verifica se o veículo colidiu com um ponto de abastecimento.
     * 
     * @param mapa Mapa onde os postos de abastecimento estão localizados.
     * @param localizacao Localização para verificar a colisão.
     * @return true se houver colisão com um posto, false caso contrário.
     */
    private boolean colidiuComPosto(Mapa mapa, Localizacao localizacao) {
        for (PontoAbastecimento posto : mapa.getPontosAbastecimento()) {
            if (posto.getLocalizacao().equals(localizacao)) {
                return true; // Colisão detectada
            }
        }
        return false; // Nenhuma colisão
    }

    /**
     * Verifica se o veículo colidiu com outro veículo.
     * 
     * @param mapa Mapa onde os veículos estão localizados.
     * @param proximaLocalizacao Localização do veículo a ser verificada.
     * @return true se houver colisão com outro veículo, false caso contrário.
     */
    private boolean colidiuComVeiculo(Mapa mapa, Localizacao proximaLocalizacao) {
        for (Veiculo veiculo : mapa.getVeiculos()) {
            if (veiculo.getLocalizacaoAtual().equals(proximaLocalizacao)) {
                return true; // Colisão com qualquer veículo
            }
        }
        return false; // Nenhuma colisão
    }

      /**
     * Encontra a última posição disponível na fila de veículos.
     * 
     * @param mapa Mapa onde os veículos estão localizados.
     * @param posicaoInicial Posição inicial para verificar a fila.
     * @return Última posição da fila disponível.
     */
    private Localizacao encontrarUltimoVeiculoNaFila(Mapa mapa, Localizacao posicaoInicial) {
        Localizacao posicaoAtual = posicaoInicial;
        boolean encontrouVeiculo = true;
    
        while (encontrouVeiculo) {
            Localizacao proximaPosicao = new Localizacao(posicaoAtual.getX(), posicaoAtual.getY() + 1);
            encontrouVeiculo = false;
    
            for (Veiculo veiculo : mapa.getVeiculos()) {
                if (veiculo.getLocalizacaoAtual().equals(proximaPosicao)) {
                    posicaoAtual = proximaPosicao;
                    encontrouVeiculo = true;
                }
            }
        }
    
        return posicaoAtual;
    }
    
}