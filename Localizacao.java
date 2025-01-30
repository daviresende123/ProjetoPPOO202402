/**
 * Representa uma localização no mapa
 * @author David J.
 * Barnes and Michael Kolling and Luiz Merschmann
 */
public class Localizacao {
    private int x;
    private int y;
    
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
     * Gera a próxima localização movendo apenas no eixo Y
     * @param localizacaoDestino Localização destino
     * @return Nova localização ajustada no eixo Y
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
     * Verifica a igualdade de duas localizações.
     * @return true se as coordenadas forem iguais, false caso contrário.
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
     * Representação textual da localização
     * @return String formatada com as coordenadas
     */
    @Override
    public String toString()
    {
        return "(" + x + ", " + y + ")";
    }
    
    
}
