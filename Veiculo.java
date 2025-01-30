import java.awt.Image;
import java.time.Duration;
import java.time.Instant;
import javax.swing.ImageIcon;

public class Veiculo {
    private Localizacao localizacaoAtual;
    private Localizacao localizacaoDestino;
    private Image imagem;
    private boolean deveSerRemovido;
    private Instant tempoInicioAbastecimento; // Timestamp do início do abastecimento
    private Duration tempoParado; // Tempo de parada como Duration
    private boolean abastecendo; // Indica se o veículo está abastecendo

    public Veiculo(Localizacao localizacao, String imagem) {
        this.localizacaoAtual = localizacao;
        this.localizacaoDestino = new Localizacao(localizacao.getX(), 0); // Destino fixo: y = 0
        this.imagem = new ImageIcon(getClass().getResource(imagem)).getImage();
        this.deveSerRemovido = false;
        this.tempoInicioAbastecimento = null;
        this.tempoParado = Duration.ZERO;
        this.abastecendo = false;
    }

    public void setTempoParado(Duration tempoParado) {
        this.tempoParado = tempoParado;
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

    public boolean isAbastecendo() {
        return abastecendo;
    }

    public void setAbastecendo(boolean abastecendo) {
        this.abastecendo = abastecendo;
    }

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

    private boolean colidiuComPosto(Mapa mapa, Localizacao localizacao) {
        for (PontoAbastecimento posto : mapa.getPontosAbastecimento()) {
            if (posto.getLocalizacao().equals(localizacao)) {
                return true; // Colisão detectada
            }
        }
        return false; // Nenhuma colisão
    }

    private boolean colidiuComVeiculo(Mapa mapa, Localizacao proximaLocalizacao) {
        for (Veiculo veiculo : mapa.getVeiculos()) {
            if (veiculo.getLocalizacaoAtual().equals(proximaLocalizacao)) {
                return true; // Colisão com qualquer veículo
            }
        }
        return false; // Nenhuma colisão
    }
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