package audio;

import java.io.FileInputStream;

import javazoom.jl.player.Player;

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
