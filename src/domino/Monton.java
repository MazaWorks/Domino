package domino;

public class Monton {
    private PiezaDomino[] piezasTotal;
    private int numFichas;
    
    public Monton(){
        piezasTotal=new PiezaDomino[28];
        numFichas=0;
        for(int j=0; j<7; j++){
            for(int k=j; k<7; k++){
        piezasTotal[numFichas]=new PiezaDomino(j,k);
            numFichas++;
            }
        }
    }

    public void eliminarFicha(int pos){
                for (int j = pos; j < piezasTotal.length - 1; j++) {
                    piezasTotal[j] = piezasTotal[j+1];
                }
                numFichas--;
            }
    
    public int getNumFichas() {
        return numFichas;
    }

    public PiezaDomino getPiezasTotal(int pos) {
        return piezasTotal[pos];
    }
    
}
