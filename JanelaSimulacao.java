import java.awt.*;
import javax.swing.*;

/**
 * Fornece a visualização da simulação.
 * Esta classe representa a janela gráfica onde o mapa e os veículos são exibidos.
 * 
 * @author Davi Gomides, João Ramalho, Eduardo Gomes
 * @version 1.0
 */

public class JanelaSimulacao extends JFrame {
    private Mapa mapa;
    private VisaoMapa visaoMapa;

    /**
     * Construtor da classe JanelaSimulacao.
     * Inicializa a janela e configura a exibição do mapa.
     * 
     * @param mapa O mapa que será exibido na simulação.
     */
    public JanelaSimulacao(Mapa mapa) {
        this.mapa = mapa;
        visaoMapa = new VisaoMapa(mapa.getLargura(), mapa.getAltura());
        getContentPane().add(visaoMapa);
        setTitle("Simulator");
        setSize(1000, 1000);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * Atualiza a exibição do mapa na simulação.
     * Renderiza os pontos de abastecimento e os veículos presentes no mapa.
     */
    public void executarAcao() {
        visaoMapa.preparePaint();

        // Desenha os pontos de abastecimento
        for (PontoAbastecimento ponto : mapa.getPontosAbastecimento()) {
            Localizacao loc = ponto.getLocalizacao();
            visaoMapa.desenharImagem(loc.getX(), loc.getY(), ponto.getImagem());
        }

        // Desenha os veículos
        for (int i = 0; i < mapa.getAltura(); i++) {
            for (int j = 0; j < mapa.getLargura(); j++) {
                if (mapa.getItem(i, j) != null) { // Se existir algum objeto na posição (i,j)
                    Veiculo veiculo = mapa.getItem(i, j);
                    Localizacao localizacao = veiculo.getLocalizacaoAtual();
                    visaoMapa.desenharImagem(localizacao.getX(), localizacao.getY(), veiculo.getImagem());
                }
            }
        }

        visaoMapa.repaint();
    }

    /**
     * Fornece uma visualização gráfica do mapa. Esta é uma classe interna 
     * que define os componentes da interface gráfica. 
     * 
     * Ela contém detalhes avançados sobre GUI, mas não é necessário entendê-los
     * para o funcionamento do programa.
     */
    private class VisaoMapa extends JPanel {

        private final int VIEW_SCALING_FACTOR = 6;

        private int larguraMapa, alturaMapa;
        private int xScale, yScale;
        private Dimension tamanho;
        private Graphics g;
        private Image imagemMapa;

        /**
         * Cria um novo componente VisaoMapa.
         * 
         * @param largura A largura do mapa.
         * @param altura A altura do mapa.
         */
        public VisaoMapa(int largura, int altura) {
            larguraMapa = largura;
            alturaMapa = altura;
            setBackground(Color.white);
            tamanho = new Dimension(0, 0);
        }

        /**
         * Retorna o tamanho preferido da visualização do mapa.
         * 
         * @return As dimensões preferidas para a exibição.
         */
        public Dimension getPreferredSize() {
            return new Dimension(larguraMapa * VIEW_SCALING_FACTOR,
                    alturaMapa * VIEW_SCALING_FACTOR);
        }

        /**
         * Prepara a área de exibição para um novo ciclo de renderização.
         * Recalcula a escala caso a janela seja redimensionada.
         */
        public void preparePaint() {
            if (!tamanho.equals(getSize())) { // se o tamanho mudou...
                tamanho = getSize();
                imagemMapa = visaoMapa.createImage(tamanho.width, tamanho.height);
                g = imagemMapa.getGraphics();

                xScale = tamanho.width / larguraMapa;
                if (xScale < 1) {
                    xScale = VIEW_SCALING_FACTOR;
                }
                yScale = tamanho.height / alturaMapa;
                if (yScale < 1) {
                    yScale = VIEW_SCALING_FACTOR;
                }
            }
            g.setColor(Color.white);
            g.fillRect(0, 0, tamanho.width, tamanho.height);
            g.setColor(Color.gray);
            for (int i = 0, x = 0; x < tamanho.width; i++, x = i * xScale) {
                g.drawLine(x, 0, x, tamanho.height - 1);
            }
            for (int i = 0, y = 0; y < tamanho.height; i++, y = i * yScale) {
                g.drawLine(0, y, tamanho.width - 1, y);
            }
        }

         /**
         * Desenha uma imagem em uma determinada posição do mapa.
         * 
         * @param x A posição X onde a imagem será desenhada.
         * @param y A posição Y onde a imagem será desenhada.
         * @param image A imagem a ser exibida.
         */
        public void desenharImagem(int x, int y, Image image) {
            g.drawImage(image, x * xScale + 1, y * yScale + 1,
                    xScale - 1, yScale - 1, this);
        }

         /**
         * Atualiza a exibição do componente VisaoMapa.
         * Copia a imagem interna para a tela.
         * 
         * @param g O contexto gráfico usado para desenhar.
         */
        public void paintComponent(Graphics g) {
            if (imagemMapa != null) {
                g.drawImage(imagemMapa, 0, 0, null);
            }
        }
    }

}
