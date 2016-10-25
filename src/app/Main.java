package app;

import supermarket.Caixa;
import supermarket.Client;
import supermarket.ClientWorker;
import supermarket.SuperMarket;

import java.util.Date;
import java.util.Scanner;

/**
 * Created by 23878410v on 24/10/16.
 */
public class Main {

    public static void main(String[] args) {
        boolean flag = false;
        SuperMarket sm = new SuperMarket();
        Scanner sc = new Scanner(System.in);
        while(true){
            if(flag){
                System.out.println("-----------------------------------------------------------");
            }else{
                flag = true;
            }
            System.out.println("Menú d'accions");
            System.out.println("1. Llistar Caixes");
            System.out.println("2. Llistar Clients en selecció de caixa");
            System.out.println("3. Llistar Historial clients");
            System.out.println("4. Simular client");
            String accio = sc.nextLine();
            switch( Integer.parseInt(accio) ){
                case 1:
                    System.out.println("Numero de caixes: "+sm.getCaixes().size());
                    for (Caixa c: sm.getCaixes()) {
                        System.out.println(c.toString());
                    }
                    break;


                case 2:
                    System.out.println("Numero de clients en cua de caixa: "+sm.getQueue().size());
                    for(Client c : sm.getQueue()){
                        System.out.println(c.toString());
                    }
                    break;
                case 3:
                    System.out.println("Numero de clients: "+sm.getPoolClientWorkers().size());
                    for(ClientWorker cw : sm.getPoolClientWorkers()){
                        System.out.println(cw.toString());
                    }
                    break;
                case 4:
                    for (int i = 0; i < 50; i++) {
                        sm.addClientNow(new Date());
                    }
                    break;
            }
        }


    }
}
