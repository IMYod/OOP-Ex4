package audio;

import javazoom.jl.player.*;
import java.io.FileInputStream;
/**
 * This class is responsible to make a eating sound music for the player for each fruit he will eat.
 * NOTE: This class use the jl1.0.1.jar 
 *
 */
public class SimplePlayer implements Runnable{

    private final String path = "audio\\marioSong.mpeg";
    
    public void run()
    {
        try{

             FileInputStream fis = new FileInputStream(path);
             Player playMP3 = new Player(fis);
             playMP3.play();

        }  catch(Exception e){
             System.out.println(e);
        }
    }
}