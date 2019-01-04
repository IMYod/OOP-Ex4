package audio;
import java.io.FileInputStream;

import javazoom.jl.player.Player;
/**
 * This class is responsible to make a background music for this game.
 * NOTE: This class use the jl1.0.1.jar 
 *
 */
public class EatingSound implements Runnable {
	
	 private final String path = "audio\\eating.mpeg";

	@Override
	public void run() {
		try{

            FileInputStream fis = new FileInputStream(path);
            Player playMP3 = new Player(fis);
            playMP3.play();
            Thread.sleep(1000);
            playMP3.close();

       }  catch(Exception e){
            System.out.println(e);
       }
	}

}
