package domino;

import java.util.Deque;
import java.util.LinkedList;

public class JuegoEnLaMesa {
    private Deque<PiezaDomino> piezas;
    private int[] cont=new int[7];
    
    public JuegoEnLaMesa(){
        piezas = new LinkedList<>();
    }

    public int getNumPiezas() {
        return piezas.size();
    }
    
    public int getCont(int i){
        int toret=0;
        switch(i){
            case 0:
                toret=cont[0];
                break;
            case 1:
                toret=cont[1];
                break;
            case 2:
                toret=cont[2];                
                break;
            case 3:
                toret=cont[3];
                break;
            case 4:
                toret=cont[4];    
                break;
            case 5:
                toret=cont[5];
                break;
            case 6:
                toret=cont[6];
                break;
        }
        return toret;
    }
    
    public PiezaDomino getFirst(){
        return piezas.getFirst();
    }
    
    public PiezaDomino getLast(){
        return piezas.getLast();
    }
    
    public void insertarFicha(PiezaDomino nueva, boolean lado){
        if(lado){
            piezas.addFirst(nueva);
        }
        else
            piezas.addLast(nueva);
        switch(nueva.getNumero1()){
            case 0: cont[0]++;
            break;
            case 1: cont[1]++;
            break;
            case 2: cont[2]++;
            break;
            case 3: cont[3]++;
            break;
            case 4: cont[4]++;
            break;
            case 5: cont[5]++;
            break;
            case 6: cont[6]++;
        }
        switch(nueva.getNumero2()){
            case 0: cont[0]++;
            break;
            case 1: cont[1]++;
            break;
            case 2: cont[2]++;
            break;
            case 3: cont[3]++;
            break;
            case 4: cont[4]++;
            break;
            case 5: cont[5]++;
            break;
            case 6: cont[6]++;
        }
    }
    
    public boolean haCerrado(){//Verifica si se ha cerrado el juego y devuelve true o false
        boolean toret=false;
        if(getFirst().getNumero1()==getLast().getNumero2())
            if(getCont(getFirst().getNumero1())==8){
                toret=true;
            }
        return toret;
    }
    
    public String toString(){
        int cont=0;
        StringBuilder toret = new StringBuilder();
        for(PiezaDomino i : piezas){
            cont++;
            if(cont==12)
                toret.append("-").append(i).append("-").append("\n");
            else
                toret.append("-").append(i).append("-");
        }
        return toret.toString();
    }
}