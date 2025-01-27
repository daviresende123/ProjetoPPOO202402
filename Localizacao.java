import java.util.Random;

/**
 * Representa uma localização no mapa
 * @author David J. Barnes and Michael Kolling and Luiz Merschmann
 */
public class Localizacao {
    private int x;
    private int y;
    private static Random rand = new Random();
    
    /**
     * Representa uma localização na cidade
     * @param x Coordenada x: deve ser maior ou igual a 0.
     * @param y Coordenada y: deve ser maior ou igual a 0.
     */
    public Localizacao(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    /**
     * Gera a localizacao para se mover visando alcançar o destino
     * @param localizacaoDestino: localizacao que se deseja alcancar.
     * @return Localizacao para onde se deve ir
     * @DaviGomides Alterei para que movesse apenas no eixo Y
     */
    public Localizacao proximaLocalizacao(Localizacao localizacaoDestino) {
        if (localizacaoDestino.equals(this)) { // Verifica se já alcançou o destino
            return localizacaoDestino;
        } else {
            int destY = localizacaoDestino.getY();
            int novoY = y < destY ? y + 1 : y > destY ? y - 1 : y; // Movimenta no eixo Y, sem ultrapassar o destino
            return new Localizacao(x, novoY); // Retorna a nova localização apenas com alteração no Y
        }
    }
    
    /**
     * Verificacao de igualdade de conteudo de objetos do tipo Localizacao.
     * @return true: se a localizacao é igual.
     *         false: caso contrario.
     */
    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }else if(!(obj instanceof Localizacao)){
            return false;
        }else{
            Localizacao outro = (Localizacao) obj;
            return x == outro.x && y == outro.y;
        }
    }
    
    /**
     * @return A representacao da localizacao.
     */
    @Override
    public String toString()
    {
        return "(" + x + ", " + y + ")";
    }
    
    
}
