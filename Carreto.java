/**
 * Representa um carreto específico no sistema.
 * Esta classe herda de Veiculo e define um carreto com uma imagem específica.
 * 
 * @author Davi Gomides, João Ramalho, Eduardo Gomes
 * @version 1.0
 */
public class Carreto extends Veiculo {

     /**
     * Construtor da classe Carreto.
     * 
     * @param localizacao A localização inicial do carreto.
     */
    public Carreto(Localizacao localizacao) {
        super(localizacao, "/Imagens/carreto.png"); 
    }
}