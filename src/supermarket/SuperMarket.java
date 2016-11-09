package supermarket;

import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by 23878410v on 24/10/16.
 */
public class SuperMarket {

    ArrayList<Caixa> caixes = new ArrayList<>();
    ArrayList<Client> queue = new ArrayList<>();
    ArrayList<ClientWorker> PoolClientWorkers = new ArrayList<>();
    Boolean debug = false;
    public Boolean modern = true;
    RouterModernWorker rw_modern;
    RouterWorker rw_normal;


    public SuperMarket(boolean modern) {
        this.caixes.add(new Caixa(1));
        this.caixes.add(new Caixa(2));
        this.caixes.add(new Caixa(3));
        this.caixes.add(new Caixa(4));

        for (Caixa c: caixes) {
            c.start();
        }

        changeRouter(modern);
    }

    public void changeRouter(boolean modern){
        this.modern = modern;
        if(modern){
            if(rw_normal != null && rw_normal.isAlive()) {
                rw_normal.stop();
            }
            rw_modern = new RouterModernWorker(this);
            rw_modern.start();
        }else{
            if(rw_modern != null && rw_modern.isAlive()) {
                rw_modern.stop();
            }
            rw_normal = new RouterWorker(this);
            rw_normal.start();
        }
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
