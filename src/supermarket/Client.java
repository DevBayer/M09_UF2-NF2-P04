package supermarket;

import java.util.*;

/**
 * Created by 23878410v on 24/10/16.
 */
public class Client {
    Date dataEntrada;
    Date dataSortida;
    String status;
    int CaixaAtes;
    Date dataRouter;

    public Client(Date dataEntrada) {
        this.dataEntrada = dataEntrada;
        this.status = "Buscan";
    }

    synchronized void setStatus(String s){
        this.status = s;
    }

    synchronized String getStatus(){
        return this.status;
    }

    synchronized void setDataSortida(Date d){ this.dataSortida = d; }

    synchronized void setDataRouter(Date d){
        this.dataRouter = d;
    }

    synchronized void setCaixaAtes(int i) { this.CaixaAtes = i; }



    @Override
    public String toString() {
        if(CaixaAtes > 0){
            return "Client "+dataEntrada.toString()+" status: "+this.getStatus() + " dataRouterToCaixa:" + dataRouter.toString() +" CaixaAtes: "+CaixaAtes;
        }else{
            return "Client "+dataEntrada.toString()+" status: "+this.getStatus();
        }
    }
}