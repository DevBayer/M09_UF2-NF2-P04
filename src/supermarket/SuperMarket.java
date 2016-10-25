package supermarket;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by 23878410v on 24/10/16.
 */
public class SuperMarket {

    ArrayList<Caixa> caixes = new ArrayList<>();
    ArrayList<Client> queue = new ArrayList<>();
    ArrayList<ClientWorker> PoolClientWorkers = new ArrayList<>();


    public SuperMarket() {
        this.caixes.add(new Caixa(1));
        this.caixes.add(new Caixa(2));
        this.caixes.add(new Caixa(3));
        this.caixes.add(new Caixa(4));

        for (Caixa c: caixes) {
            c.start();
        }

        RouterWorker rw = new RouterWorker(this);
        rw.start();
    }

    public ArrayList<Caixa> getCaixes() {
        return caixes;
    }

    public ArrayList<Client> getQueue() {
        return queue;
    }

    public ArrayList<ClientWorker> getPoolClientWorkers() {
        return PoolClientWorkers;
    }

    public void addClientNow(Date d){
        Client c = new Client(d);
        ClientWorker worker = new ClientWorker(this, c);
        worker.start();
        PoolClientWorkers.add(worker);
    }

}
