package domino;

public class PiezaDomino {
    private int numero1;
    private int numero2;

    public PiezaDomino(int numeroIzquierda, int numeroDerecha) {
        this.numero1 = numeroIzquierda;
        this.numero2 = numeroDerecha;
    }

    public int getNumero1() {
        return numero1;
    }

    public int getNumero2() {
        return numero2;
    }

    public void setNumero1(int numero1) {
        this.numero1 = numero1;
    }

    public void setNumero2(int numero2) {
        this.numero2 = numero2;
    }

    public void cambiarNumeros(){
        int auxiliar=numero1;
        numero1=numero2;
        numero2=auxiliar;
    }
    
    public String toString(){
        StringBuilder toret = new StringBuilder();
        toret.append("[").append(getNumero1()).append("|")
                .append(getNumero2()).append("]");
        return toret.toString();
    }
}
