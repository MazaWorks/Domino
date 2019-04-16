package domino;

import java.util.Scanner;
public class MetodosLectura {
    
    public int leerInt(String msg, int limite){        //Lee un integer del teclado y lo verifica
        Scanner num=new Scanner(System.in);
        System.out.println(msg);
        int toret;
        do{
            try{
                //toret = num.nextInt();
                toret = Integer.parseInt(num.nextLine());
            }catch(NumberFormatException e){
                System.err.print("Inserte un número, casi cuela pero NO. Introduzcalo de nuevo: ");
                toret = 0;
            }
        }while(toret == 0 || toret > limite);
        return toret;
    }
    
    public String leerString(String msg){  //Lee un String del teclado
        Scanner entrada=new Scanner(System.in);
        System.out.println(msg);
        String toret = entrada.nextLine();
        return toret;
    }
    
    public boolean elegirLado(){           //En caso de que se pueda jugar por los dos lados, pìde al usuario si jugar por la izquierda o por la derecha
        boolean lado=false;
        int op = 0;
        do{
        System.out.println("Elija el lado en el que quiera poner la ficha"
                + "\n1: Izquierda"
                + "\n2: Derecha");
        op=leerInt("\nOpcion:", 3);}
        while(op!=1 && op!=2);
        
        switch(op){
            case 1: lado=true;
            break;
            case 2: lado=false;
            break;}
        
        return lado;
        }
    
    public int elegirFicha(Jugador jug){       //Elije la posicion de la ficha que se quiere jugar
        int elec = 0;
        do{
            jug.toString();
            elec = leerInt("Elija el numero de la ficha: ", jug.getNumPiezas());
        }while(elec<1 && elec>jug.getNumPiezas());
        
        return elec;
    }
}
