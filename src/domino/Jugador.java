package domino;

public class Jugador {
    private final String nombre;
    private PiezaDomino[] piezas;
    private int numPiezas;
    private boolean mano;

    public Jugador(String nombre) {
        this.nombre = nombre;
        piezas=new PiezaDomino[7];
        numPiezas=0;
        mano=false;
    }
    
    public int getNumPiezas() {
        return numPiezas;
    }
    
    public boolean getMano(){
        return mano;
    }
    
    public void setMano(boolean mano){
        this.mano=mano;
    }
    
    public PiezaDomino getPiezas(int posicion) {
        return piezas[posicion];
    }

    public String getNombre() {
        return nombre;
    }

    public void cogerFicha(PiezaDomino nueva){
        if(numPiezas==piezas.length){
            PiezaDomino[] aux=new PiezaDomino[piezas.length*2];
            for(int i=0;i<numPiezas;i++)
                aux[i]=piezas[i];
            piezas=aux;
        }
        piezas[numPiezas]=nueva;
        numPiezas++;
    }
    
    public void eliminarFicha(int pos){
        for (int j = (pos); j < piezas.length - 1; j++) {
            piezas[j] = piezas[j+1];
        }
        numPiezas--;
    }
    
    public int contarPuntuacion(){      //Cuenta la puntuacion del jugador en caso de cierre
        int toret=0;
        for(int i=0;i<getNumPiezas();i++){
            toret+=getPiezas(i).getNumero1();
            toret+=getPiezas(i).getNumero2();
        }
        return toret;
    }
    
    public String toString(){
        StringBuilder toret=new StringBuilder();
        for(int i=0;i<numPiezas;i++){
            toret.append(i+1).append(":").append(piezas[i].toString()).append("\t");
        }
        return toret.toString();
    }
}
