/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bricksandpaddle;

import java.io.*;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.*;
import javax.swing.*;
import sun.audio.*;
/**
 *
 * @author Rene
 */

//new FileInputStream("C:/Users/Rene/Documents/NetBeansProjects/Game/src/game/music/3.wav")
public class MusicBox extends Thread{
    
    public File file;
    public double time;
    
    public AudioStream as;
    MusicBox(){

        
        start();
        
    }
    
    public void run(){
        
        while(true){
            
            replay();
            try {
                Thread.sleep((long) (time*1000));
            } catch (InterruptedException ex) {
                Logger.getLogger(MusicBox.class.getName()).log(Level.SEVERE, null, ex);
            }
        
            AudioPlayer.player.stop(as);
        }
    }
    
    public void replay(){
        File soundFile =  new File(System.getProperty("user.dir")  + "/src/music/3.wav");
        try {
            AudioInputStream audioFile = AudioSystem.getAudioInputStream(soundFile);
            AudioFormat format = audioFile.getFormat();
            long frames = audioFile.getFrameLength();
            time = (frames+0.0) / format.getFrameRate(); 
            //System.out.println(time);
            as = new AudioStream(new FileInputStream(soundFile));
            
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(MusicBox.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MusicBox.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        AudioPlayer.player.start(as);
    }
    
}
