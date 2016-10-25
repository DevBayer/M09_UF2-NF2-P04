package supermarket;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by 23878410v on 24/10/16.
 */
public class Caixa extends Thread {
    private int TotalClients;
    private int TotalCaixa;
    private int num_caixa;
    private ArrayList<Client> queue = new ArrayList<>();

    public Caixa(int num_caixa) {
        this.num_caixa = num_caixa;
        this.TotalClients = 0;
        this.TotalCaixa = 0;
    }

    public int getClientsOnQueue(){
        return queue.size();
    }

    public void addClientOnQueue(Client client){
        this.queue.add(client);
    }

    @Override
    public void run() {
        while(true){
            for (int i = 0; i < queue.size(); i++) {
                Client client = queue.get(i);
                client.setStatus("Ates");
                client.setDataSortida(new Date());
                client.setCaixaAtes(this.num_caixa);
                long diff = ( client.dataSortida.getTime() - client.dataEntrada.getTime() ) / 1000;
                System.out.println("Caixa " + this.num_caixa + ", client a pagar: " + diff);
                this.queue.remove(i);
                this.TotalClients++;
                this.TotalCaixa += diff;
                try{
                    // La caixera, per transacció, espera 1 segon...
                    Thread.sleep(1000);
                }catch(InterruptedException e){
                    System.out.println("RouterWorker::run() -> Err: "+e.getMessage());
                }
            }
            try {
                synchronized (this){
                    if(queue.size() == 0) this.wait();
                }
            }catch(InterruptedException e){
                System.out.println("RouterWorker::run() -> Err: "+e.getMessage());
            }
        }
    }

    public String getNameCaixa(){
        return "Caixa"+num_caixa;
    }


    @Override
    public String toString() {
        return "NumCaixa: "+this.num_caixa+" queueSize: " +queue.size() + " TotalClients: " + TotalClients + ", TotalCaixa: "+TotalCaixa+"$";
    }
}
