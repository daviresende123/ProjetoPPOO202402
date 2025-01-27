import java.util.Random;
/**
 * Responsavel pela simulacao.
 * @author David J. Barnes and Michael Kolling and Luiz Merschmann
 */
public class Simulacao {
    private Veiculo veiculo;
    private JanelaSimulacao janelaSimulacao;
    private Mapa mapa;
    
    /*@DaviGomides Alterei o construtor para iniciar sempre da "posicao inicial" no eixo Y, variando apenas o eixo X, onde
     os caminhoes e carretos SEMPRE ocuparão um X ímpar
    */
    public Simulacao() {
        Random rand = new Random();
        mapa = new Mapa();
        int largura = mapa.getLargura(); // Igual a 34
        int altura = mapa.getAltura();  // Igual a 34
    
        // Gerar posição inicial no eixo X (apenas números ímpares entre 1 e 33)
        int xInicial = 1 + 2 * rand.nextInt(largura / 2); // (1, 3, 5, ..., 33)
    
        // Definir o eixo Y na posição inicial mais baixa
        int yInicial = altura - 1;
    
        // Criar o veículo na posição inicial (X ímpar, Y mais baixo)
        veiculo = new Veiculo(new Localizacao(xInicial, yInicial));
    
        // Definir uma posição destino aleatória no mapa
        veiculo.setLocalizacaoDestino(new Localizacao(rand.nextInt(largura), rand.nextInt(altura)));
    
        // Adicionar o veículo ao mapa
        mapa.adicionarItem(veiculo);
    
        // Inicializar a janela da simulação
        janelaSimulacao = new JanelaSimulacao(mapa);
    }
    
    public void executarSimulacao(int numPassos){
        janelaSimulacao.executarAcao();
        for (int i = 0; i < numPassos; i++) {
            executarUmPasso();
            esperar(100);
        }        
    }

    private void executarUmPasso() {
        mapa.removerItem(veiculo);
        veiculo.executarAcao();
        mapa.adicionarItem(veiculo);
        janelaSimulacao.executarAcao();
    }
    
    private void esperar(int milisegundos){
        try{
            Thread.sleep(milisegundos);
        }catch(InterruptedException e){
            System.out.println(e.getMessage());
        }
    }
    
}
