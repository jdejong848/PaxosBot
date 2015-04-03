import org.jibble.pircbot.PircBot;

/**
 * Created by Mark on 3/30/2015.
 */
public class EBGameThread extends Thread{
    private boolean bRunning = false;

    public void run(PircBot bot){
        bRunning = true;
        while(bRunning){

        }

    }

    public void halt(){
        bRunning = false;
    }

}
