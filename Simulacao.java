import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulacao {
    private List<Veiculo> veiculos; // Lista de veículos ativos
    private JanelaSimulacao janelaSimulacao;
    private Mapa mapa;
    private Random rand;
    private int tempoParaNovoVeiculo; // Contador para gerar novos veículos

    public Simulacao() {
        rand = new Random();
        mapa = new Mapa();
        veiculos = new ArrayList<>();
        janelaSimulacao = new JanelaSimulacao(mapa);
        tempoParaNovoVeiculo = 0; // Inicializa o contador

        adicionarPontosAbastecimento();
    }

    private void adicionarPontosAbastecimento() {
        // Exemplo: Adiciona pontos de abastecimento em posições fixas
        for (int i = 1; i < 35; i = i + 2)
            mapa.adicionarPontoAbastecimento(new PontoAbastecimento(new Localizacao(i, 0)));
    }

    public void executarSimulacao(int numPassos) {
        for (int i = 0; i < numPassos; i++) {
            executarUmPasso();
            esperar(100); // Espera 100 ms entre cada passo
        }
    }

    private void executarUmPasso() {
        // Gera um novo veículo a cada segundo (10 passos, considerando 100 ms por
        // passo)
        if (tempoParaNovoVeiculo == 0) {
            gerarNovoVeiculo();
            tempoParaNovoVeiculo = 10; // Reinicia o contador (1 segundo)
        } else {
            tempoParaNovoVeiculo--;
        }

        // Atualiza a posição de todos os veículos
        for (int i = veiculos.size() - 1; i >= 0; i--) {
            Veiculo veiculo = veiculos.get(i);
            mapa.removerItem(veiculo); // Remove o veículo do mapa temporariamente

            if (veiculo.deveSerRemovido()) {
                // Remove o veículo da lista e do mapa
                veiculos.remove(i);
            } else {
                // Atualiza a posição do veículo
                veiculo.executarAcao(mapa); // Passa o mapa para verificar colisões
                mapa.adicionarItem(veiculo); // Adiciona o veículo de volta ao mapa
            }
        }

        // Atualiza a visualização
        janelaSimulacao.executarAcao();
    }

    private void gerarNovoVeiculo() {
        int largura = mapa.getLargura();
        int altura = mapa.getAltura();

        // Gera uma posição inicial no eixo X (apenas números ímpares entre 1 e 33)
        int xInicial = 1 + 2 * rand.nextInt(largura / 2); // (1, 3, 5, ..., 33)

        // Define o eixo Y na posição inicial mais baixa
        int yInicial = altura - 1;

        // Cria o veículo na posição inicial (X ímpar, Y mais baixo)
        Veiculo novoVeiculo;

        // Se o número aleatório for menor que 0.5, cria um Carreto, senão cria um
        // Caminhao
        if (rand.nextDouble() < 0.5) {
            novoVeiculo = new Carreto(new Localizacao(xInicial, yInicial)); // Instanciando Carreto
        } else {
            novoVeiculo = new Caminhao(new Localizacao(xInicial, yInicial)); // Instanciando Caminhao
        }

        // Adiciona o veículo à lista e ao mapa
        veiculos.add(novoVeiculo);
        mapa.adicionarItem(novoVeiculo);
    }

    private void esperar(int milisegundos) {
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}