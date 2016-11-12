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
    private int TotalTempsCompra;

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
                // Mirem la cua de la nostra Caixa, si tenim clients i
                // aquests estan esperant a ser atesos, els atenem
                if(client.worker.getState().equals(State.WAITING)) {
                    client.setStatus("Ates");
                    client.setDataSortida(new Date());
                    client.setCaixaAtes(this.num_caixa);
                    // Calculem el import a pagar mitjançant el temps entre que ha sortit i entra (en segons)
                    long diff = client.getTempsActivitat() / 1000;
                    // El treiem de la cua de caixa, el client ja ha sigut atés,
                    // incrementem el enter de totalClients i sumem l'import que ha pagat a la caixa registradora
                    this.queue.remove(i);
                    this.TotalClients++;
                    this.TotalCaixa += diff;
                    this.TotalTempsCompra += client.getTempsActivitat();
                    try {
                        // La caixera, per transacció, espera 1 segon...
                        Thread.sleep(1000);
                        synchronized (client) {
                            // Notifiquem al client que ja pot continuar amb la seva vida..
                            client.notify();
                        }
                    } catch (InterruptedException e) {
                        System.out.println("RouterModernWorker::run() -> Err: " + e.getMessage());
                    }
                }
            }
            try {
                synchronized (this){
                    // Comprovem, que en cas que la Caixa no tingui a ningú en la Cua, pugui anar a descansar...
                    if(queue.size() == 0) this.wait();
                }
            }catch(InterruptedException e){
                System.out.println("RouterModernWorker::run() -> Err: "+e.getMessage());
            }
        }
    }

    public String getNameCaixa(){
        return "Caixa"+num_caixa;
    }


    @Override
    public String toString() {
        return "NumCaixa: "+this.num_caixa
                +" queueSize: " +queue.size()
                + " MitjanaTempsCompra (segons): " + (this.TotalTempsCompra / this.TotalClients ) / 1000
                + " TotalClients: "
                + TotalClients
                + ", TotalCaixa: "+TotalCaixa+"$";
    }
}
