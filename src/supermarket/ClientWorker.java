package supermarket;

/**
 * Created by 23878410v on 25/10/16.
 */
public class ClientWorker extends Thread {

    SuperMarket SuperMarket;
    Client client;
    private int TempsSimulat;

    public ClientWorker(supermarket.SuperMarket superMarket, Client client) {
        SuperMarket = superMarket;
        this.client = client;
        System.out.println("Entra Client al Supermercat, "+client.dataEntrada.toString());
    }

    @Override
    public void run() {
        // des de 0.3 segon fins a 5 segons
        // simula el temps que el client ha entrat al local i ha anat a la cua
        TempsSimulat = (int)(Math.random() * 5000) + 300;
        try {
            Thread.sleep(Long.valueOf(TempsSimulat));
            int decisio = (int)(Math.random() * 10) + 1;
            // simula que el client s'ha deixat algun producte
            // i torna a buscar-nel...
            while(decisio >= 5){
                Thread.sleep(5);
                decisio = (int)(Math.random() * 10) + 1;
            }
            SuperMarket.getQueue().add(client);
            while(true){
                if(!client.getStatus().equals("Ates")) {
                    System.out.println("Client on queue list: " + client.toString());
                }else{
                    System.out.println("Client go home:" + client.toString());
                    break;
                }
            }
        }catch(InterruptedException e){
            System.out.printf("ClientWorker::run() -> Err: "+e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "ClientWorker{" +
                ", client=" + client.toString() +
                ", TempsSimulat=" + TempsSimulat +
                '}';
    }
}
