/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bricksandpaddle;
import java.awt.Color;
import java.awt.event.*;
import java.awt.Graphics;
import javax.swing.*;
import java.util.*;
import java.io.*;
/**
 *
 * @author Rene
 */
public class Controller implements KeyListener{
    Panel panel;
    
    public boolean sPressed;
    public boolean fPressed;
    public boolean dPressed;
    Controller(Panel p){
        panel = p;
        p.addKeyListener(this);
        p.setFocusable(true);
        
        dPressed = fPressed = sPressed = false;
    }
    
    public void update(){
        if(fPressed){
            panel.paddle.dx = 1;
        }
        
        else if(sPressed){
            panel.paddle.dx = -1;
        }
        
        else if(sPressed == false && fPressed  == false)
            panel.paddle.dx = 0;
        
        if(dPressed){
            if(panel.paddle.gun && panel.ball.released)
                panel.paddle.shoot = true;
                
            dPressed = false;    
        }
       
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
           update();
    }

 
    @Override
    public void keyPressed(KeyEvent e) {
        if(!panel.ball.released){
            if(e.getKeyChar()=='e'){
                 panel.ball.released = true;
                 
                 if(Math.random()*100 < 50.0)
                     panel.ball.setDirections(1, -1);                
                 else
                    panel.ball.setDirections(-1, -1);
            }
        }
        
        if(e.getKeyChar()=='d'){
            
            dPressed = true;
        }
        
        if(e.getKeyChar()=='f'){
           fPressed = true;
        }
        else if(e.getKeyChar()=='s'){
           sPressed = true;
        }
        
        update();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyChar()=='f'){
           fPressed = false;
           //panel.paddle.dx = 0;
           
        }
        else if(e.getKeyChar()=='s'){
           sPressed = false;
          // panel.paddle.dx = 0;
        }    
        update();
    }
}
