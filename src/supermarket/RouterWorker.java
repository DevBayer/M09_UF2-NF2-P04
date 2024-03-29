package supermarket;

import java.util.Date;
import java.util.Random;

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
        while(!SuperMarket.modern){
            while(SuperMarket.queue.size() > 0){
                // Determinem, de manera Random la caixa
                Random randomGenerator = new Random();
                Caixa tmp = SuperMarket.getCaixes().get(randomGenerator.nextInt(SuperMarket.getCaixes().size()));

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
