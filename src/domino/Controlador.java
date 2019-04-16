package domino;

import java.util.Random;
public class Controlador {
    public void controlador() {
        JuegoEnLaMesa juego=new JuegoEnLaMesa();
        Monton nuevo=new Monton();
        int numJug=numJug();  //Introducimos el número de jugadores
        Jugador[] jugadores=new Jugador[numJug];
        Jugador ganador=null;
        String ganar1 = null;
        int j=-1;
        
        for(int i=0; i<numJug; i++){  //Introducimos los nombres de los jugadores
            System.out.println("\nJugador Nº" + (i+1) + " : ");
            String nombre=new MetodosLectura().leerString("\nEscriba su nombre: ");
            jugadores[i]=new Jugador(nombre);
        }
        jugadores[0].setMano(true);
        
        repartirFichas(nuevo,jugadores); //Repartimos las fichas entre los jugadores
        
        /* Juegan hasta que un jugador se quede sin fichas o se cierre el juego*/
        do{
            j++;
            if(j==jugadores.length)
                j=0;
            System.out.println("\n\nJugador de este turno: " + jugadores[j].getNombre());
            System.out.println(jugadores[j].toString());
            jugarTurno(jugadores[j],juego,nuevo,numJug);
            System.out.println("\nTablero: \n" + juego.toString());
        }while(jugadores[j].getNumPiezas()!=0 && !juego.haCerrado());//Juega la partida hasta que se cierre el juego o gane alguien
        
        if(jugadores[j].getNumPiezas()==0){  //Si un jugador se queda sin fichas
            ganador=jugadores[j];
            ganar1=ganador.getNombre();
        }
        else if(juego.haCerrado()){     //Si se cierra el juego
            ganar1=cerró(jugadores,numJug).getNombre();
        }
        
        System.out.println("El juego ha finalizado, a continuación veremos las manos de los jugadores y el resultado");
        for (int i = 0; i < jugadores.length; i++) {
            System.out.println("Jugador " + jugadores[i].getNombre() );
            System.out.println(jugadores[i].toString());
        }
        System.out.println("Jugador que ha ganado: " + ganar1 + "\nGracias por jugar con MAZABRAM Games");
        System.exit(0);
    }
    
    private int numJug(){           //Te introduce el numero de jugadores
        int numJug=0;
        do{
            numJug = new MetodosLectura().leerInt("Introduzca el número de jugadores [2-4]: ", 4);
        }while(numJug<2 || numJug>4);
        return numJug;
    }
    
    private void repartirFichas(Monton nuevo, Jugador[] jug){   //Reparte las fichas al array de jugadores que se les pase por parametro desde el monton
        Random rnd = new Random();
        for(int i=0;i<jug.length;i++)
            for(int j=0;j<7;j++){
                int aleat=rnd.nextInt(nuevo.getNumFichas());
                jug[i].cogerFicha(nuevo.getPiezasTotal(aleat));
                nuevo.eliminarFicha(aleat);
            }
    }
    
    private void jugarTurno(Jugador jug, JuegoEnLaMesa nuevo, Monton nuevo2, int numJug){  // Juega el turno de los jugadores
        boolean sePuede=false;
        int elec;
        if(nuevo.getNumPiezas()==0){
            elec=new MetodosLectura().elegirFicha(jug);
            insertarFicha(elec,false,jug,nuevo);
        }
        else{
        for(int i=0;i<jug.getNumPiezas();i++){
                sePuede|=sePuedeJugar(nuevo,jug.getPiezas(i));
        }
        if(!sePuede && numJug==4){
            System.out.println("No tienes fichas");
        }
        else if(!sePuede && numJug!=4){           
            System.out.println("Cogio al azar");
            cogerAlAzar(nuevo2,jug,nuevo);
        }
        else{
            do{
                elec=new MetodosLectura().elegirFicha(jug);
            }
            while(!sePuedeJugar(nuevo,jug.getPiezas(elec-1)));
            insertarFicha(elec,ladoFichas(nuevo,jug.getPiezas(elec-1)),jug,nuevo);
        }
        }
    }
    
    private void insertarFicha(int pos, boolean lado, Jugador jug, JuegoEnLaMesa aux){ //quita la ficha del jugador y la inserta en la mesa
        aux.insertarFicha(jug.getPiezas(pos-1), lado);
        jug.eliminarFicha(pos-1);
    }
    
    private boolean sePuedeJugar(JuegoEnLaMesa nuevo, PiezaDomino elegida){     //Devuelve true si se puede jugar la ficha elegida en la mesa
        boolean toret=true;
        if(elegida.getNumero1()!=nuevo.getFirst().getNumero1() && elegida.getNumero1()!=nuevo.getLast().getNumero2()
                && elegida.getNumero2()!=nuevo.getFirst().getNumero1() && elegida.getNumero2()!=nuevo.getLast().getNumero2())//No se puede jugar la ficha
            toret=false;
        return toret;
    }
    
    private boolean cogerAlAzar(Monton nuevo, Jugador jug, JuegoEnLaMesa nuevo2){   //Coge al azar una ficha en caso de que no se pueda jugar
        boolean sePuede2=true;
        Random rnd = new Random();
        if(nuevo.getNumFichas()==0)
            sePuede2=false;
        if(sePuede2){
        int aleat=rnd.nextInt(nuevo.getNumFichas());
        PiezaDomino auxiliar=nuevo.getPiezasTotal(aleat);
        jug.cogerFicha(auxiliar);
        nuevo.eliminarFicha(aleat);
            System.out.println(jug.toString());
        if(sePuedeJugar(nuevo2,auxiliar)){
            nuevo2.insertarFicha(auxiliar, ladoFichas(nuevo2,auxiliar));
            jug.eliminarFicha(jug.getNumPiezas()-1);
            System.out.println("La ficha cogida al azar se ha colocado");
        }
            }
        return sePuede2;
    }
    
    private boolean ladoFichas(JuegoEnLaMesa nuevo, PiezaDomino elegida){       //Devuelve true si se juega por la izquierda y false si se juega por la derecha
        boolean lado=false;//Por la derecha
        if ((elegida.getNumero1()==nuevo.getFirst().getNumero1() || elegida.getNumero1()==nuevo.getLast().getNumero2())
                && (elegida.getNumero2()!=nuevo.getFirst().getNumero1() && elegida.getNumero2()!=nuevo.getLast().getNumero2())){//Solo se puede jugar el primer numero de la ficha
            if(elegida.getNumero1()==nuevo.getFirst().getNumero1() && elegida.getNumero1()==nuevo.getLast().getNumero2())//Se puede jugar a los dos lados
                lado=false;
            else if(elegida.getNumero1()==nuevo.getFirst().getNumero1() && elegida.getNumero1()!=nuevo.getLast().getNumero2()){//Se juega a la izquierda
                lado=true;
                elegida.cambiarNumeros();
            }
            else if(elegida.getNumero1()!=nuevo.getFirst().getNumero1() && elegida.getNumero1()==nuevo.getLast().getNumero2())//Se juega a la derecha
                lado=false;
        }
        else if((elegida.getNumero1()!=nuevo.getFirst().getNumero1() && elegida.getNumero1()!=nuevo.getLast().getNumero2())
                && (elegida.getNumero2()==nuevo.getFirst().getNumero1() || elegida.getNumero2()==nuevo.getLast().getNumero2())){//Solo se puede jugar el segundo numero de la ficha
            if(elegida.getNumero2()==nuevo.getFirst().getNumero1() && elegida.getNumero2()==nuevo.getLast().getNumero2())//Se puede jugar a los dos lados
                lado=true;
            else if(elegida.getNumero2()==nuevo.getFirst().getNumero1() && elegida.getNumero2()!=nuevo.getLast().getNumero2())//Se juega a la izquierda
                lado=true;
            else if(elegida.getNumero2()!=nuevo.getFirst().getNumero1() && elegida.getNumero2()==nuevo.getLast().getNumero2()){//Se juega a la derecha
                lado=false;
                elegida.cambiarNumeros();
            }}
        else if((elegida.getNumero1()==nuevo.getFirst().getNumero1() || elegida.getNumero1()==nuevo.getLast().getNumero2())
                && (elegida.getNumero2()==nuevo.getFirst().getNumero1() || elegida.getNumero2()==nuevo.getLast().getNumero2()))//Se pueden jugar las dos
            if(elegida.getNumero1()==elegida.getNumero2()){//Tenemos una ficha doble
                if(elegida.getNumero1()==nuevo.getFirst().getNumero1()){
                    lado=true;
                }
                else if(elegida.getNumero1()==nuevo.getLast().getNumero1())
                    lado=false;}
            else{//No es una ficha doble
                lado=new MetodosLectura().elegirLado();//Elije el lado por el que se va a jugar
                if(lado){
                    if(elegida.getNumero1()==nuevo.getFirst().getNumero1())
                        elegida.cambiarNumeros();}
                else
                    if(elegida.getNumero2()==nuevo.getLast().getNumero2())
                        elegida.cambiarNumeros();
            }
        return lado;
    }
    
    private Jugador cerró(Jugador[] jugadores, int numJug){
        Jugador ganador=null;
        int[] puntuacion;
        puntuacion=new int[numJug];
            for(int k=0;k<numJug;k++){
                puntuacion[k]=jugadores[k].contarPuntuacion();
            }
        switch (numJug) {
            case 2:
                if(puntuacion[0]==puntuacion[1]){
                    if(jugadores[0].getMano()==true)
                        ganador=jugadores[0];
                    else if(jugadores[0].getMano()==false && jugadores[2].getMano()==false)
                        ganador=new Jugador("Empate");
                    else
                        ganador=jugadores[1];
                }
                else if(puntuacion[0]>puntuacion[1])
                    ganador=jugadores[1];
                else
                    ganador=jugadores[0];
                break;
            case 3:
                if(puntuacion[0]<puntuacion[1]){
                    if(puntuacion[0]<puntuacion[2]){
                        ganador=jugadores[0];
                    }
                    else if(puntuacion[0]==puntuacion[2]){
                        if(jugadores[0].getMano()==false && jugadores[2].getMano()==false)
                            ganador=new Jugador("Empate");
                        else if(jugadores[0].getMano()==true)
                            ganador=jugadores[0];
                        else
                            ganador=jugadores[2];}
                    else
                        ganador=jugadores[2];
                }
                else{
                    if(puntuacion[1]<puntuacion[2]){
                        ganador=jugadores[1];}
                    else if(puntuacion[1]==puntuacion[2]){
                        if(jugadores[1].getMano()==false && jugadores[2].getMano()==false)
                            ganador=new Jugador("Empate");
                        else if(jugadores[1].getMano()==true)
                            ganador=jugadores[1];
                        else
                            ganador=jugadores[2];}
                    else
                        ganador=jugadores[2];
                }
                break;
            default:
                if(puntuacion[0]<puntuacion[1]){
                    if(puntuacion[0]<puntuacion[2]){
                        if(puntuacion[0]<puntuacion[3])
                            ganador=jugadores[0];
                        else if(puntuacion[0]==puntuacion[3]){
                            if(jugadores[0].getMano()==false && jugadores[3].getMano()==false)
                                ganador=new Jugador("Empate");
                            else if(jugadores[0].getMano()==true)
                                ganador=jugadores[0];
                            else
                                ganador=jugadores[3];}
                        else
                            ganador=jugadores[3];}
                    else{
                        if(puntuacion[2]<puntuacion[3])
                            ganador=jugadores[2];
                        else if(puntuacion[2]==puntuacion[3]){
                            if(jugadores[2].getMano()==false && jugadores[3].getMano()==false)
                                ganador=new Jugador("Empate");
                            else if(jugadores[2].getMano()==true)
                                ganador=jugadores[2];
                            else
                                ganador=jugadores[3];}
                        else
                            ganador=jugadores[3];}
                }
                else{
                    if(puntuacion[1]<puntuacion[2]){
                        if(puntuacion[1]<puntuacion[3])
                            ganador=jugadores[1];
                        else if(puntuacion[1]==puntuacion[3]){
                            if(jugadores[1].getMano()==false && jugadores[3].getMano()==false)
                                ganador=new Jugador("Empate");
                            else if(jugadores[1].getMano()==true)
                                ganador=jugadores[1];
                            else
                                ganador=jugadores[3];}
                        else
                            ganador=jugadores[3];}
                    else{
                        if(puntuacion[2]<puntuacion[3])
                            ganador=jugadores[2];
                        else if(puntuacion[2]==puntuacion[3]){
                            if(jugadores[2].getMano()==false && jugadores[3].getMano()==false)
                                ganador=new Jugador("Empate");
                            else if(jugadores[2].getMano()==true)
                                ganador=jugadores[2];
                            else
                                ganador=jugadores[3];}
                        else
                            ganador=jugadores[3];}
                }
                break;
        }
        return ganador;
    }
}
