package audio;

import javazoom.jl.player.*;
import java.io.FileInputStream;

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