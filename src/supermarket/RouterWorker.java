package supermarket;

import java.util.Date;

/**
 * Created by 23878410v on 25/10/16.
 */
public class RouterWorker extends Thread {
    SuperMarket SuperMarket;

    public RouterWorker(supermarket.SuperMarket superMarket) {
        SuperMarket = superMarket;
    }


    @Override
    public void run() {
        while(true){
            while(SuperMarket.queue.size() > 0){
                // Determinem, quina es la caixa amb menys cua
                int min = 9999;
                Caixa tmp = null;
                for (Caixa caixa: SuperMarket.caixes) {
                        int OnQueue = caixa.getClientsOnQueue();
                        if(OnQueue <= min){
                            min = OnQueue;
                            tmp = caixa;
                        }
                        System.out.println("RouterWorker::QueueSize("+SuperMarket.queue.size()+") -> OnQueue("+caixa.getNameCaixa()+") size: "+caixa.getClientsOnQueue()+" vs size: "+min+" ("+tmp.getNameCaixa()+")");
                }
                // Li assignem a la Caixa, el Client 0
                Client c = SuperMarket.queue.get(0);
                c.setStatus(tmp.getNameCaixa());
                c.setDataRouter(new Date());
                System.out.println("S'assigna el client: "+c.toString());
                synchronized (tmp){
                    tmp.addClientOnQueue(c);
                    tmp.notify();
                }
                SuperMarket.queue.remove(0);
            }
            try {
                Thread.sleep(500);
            }catch(InterruptedException e){
                System.out.println("RouterWorker::run() -> Err: "+e.getMessage());
            }
        }
    }
}
