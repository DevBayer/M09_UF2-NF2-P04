package supermarket;

import java.util.Date;

/**
 * Created by 23878410v on 25/10/16.
 */
public class RouterModernWorker extends Thread {
    SuperMarket SuperMarket;

    public RouterModernWorker(supermarket.SuperMarket superMarket) {
        SuperMarket = superMarket;
    }


    @Override
    public void run() {
        while(SuperMarket.modern){
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
                        if(SuperMarket.debug) {
                            System.out.println("RouterModernWorker::QueueSize(" + SuperMarket.queue.size() + ") -> OnQueue(" + caixa.getNameCaixa() + ") size: " + caixa.getClientsOnQueue() + " vs size: " + min + " (" + tmp.getNameCaixa() + ")");
                        }
                }
                // Li assignem a la Caixa, el Client 0
                Client c = SuperMarket.queue.get(0);
                c.setStatus(tmp.getNameCaixa());
                c.setDataRouter(new Date());
                if(SuperMarket.debug) {
                    System.out.println("S'assigna el client: " + c.toString());
                }
                synchronized (tmp){
                    tmp.addClientOnQueue(c);
                    tmp.notify();
                }
                SuperMarket.queue.remove(0);
            }
            try {
                Thread.sleep(500);
            }catch(InterruptedException e){
                System.out.println("RouterModernWorker::run() -> Err: "+e.getMessage());
            }
        }
    }
}
