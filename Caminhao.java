/**
 * Representa um caminhão específico no sistema.
 * Esta classe herda da classe Veículo e define um caminhão com uma imagem específica.
 * 
 * @author Davi Gomides, João Ramalho, Eduardo Gomes
 * @version 1.0
 */
public class Caminhao extends Veiculo {

    /**
     * Construtor da classe Caminhao.
     * 
     * @param localizacao A localização inicial do caminhão.
     */
    public Caminhao(Localizacao localizacao) {
        super(localizacao, "/Imagens/caminhao.png"); 
    }
}