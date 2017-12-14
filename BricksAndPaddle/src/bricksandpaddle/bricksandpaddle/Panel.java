
package bricksandpaddle;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
 
public class Panel extends JPanel implements Runnable {
    JFrame frame;
    Thread starter;
   
    public LinkedList<Brick> bricks;
    public int brick_counter;
    public Paddle paddle;
    public Ball ball;
    
    public Controller controller;
    public MusicBox mb;
    
    public int width;
    public int height;
    
    public LinkedList<Rectangle> perk_box;
    public LinkedList<Perk> falling_perks;
    public int continues;
    public int stage;
    public Image background;
    
    Panel(JFrame f, int w, int h) {
       Controller controller = new Controller(this);
       mb = new MusicBox();
       
       default_setBricks();
       default_setPaddle();
       default_setBall();
       falling_perks = new LinkedList<>();
       continues = 3;
       background  = new ImageIcon(System.getProperty("user.dir")  + "/src/bricksandpaddle/images/space.jpg").getImage();
       stage = 1;
       
       width = w;
       height = h;
       
       starter = new Thread(this, "Game_Thread");
       starter.start();
    }
   
   
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        g.drawImage(background, 0, 0,null);
        
        for(Brick b :bricks){
            b.draw(g);
        }
        
        
        
        paddle.draw(g);
        ball.draw(g);
            
        for(Perk p: falling_perks)
            p.draw(g);
        
        g.setColor(Color.white);
        g.setFont(g.getFont().deriveFont(0, 20.0f));
        String str = "x"+continues;
        g.drawString(str, 60, 30);
        
        str = "Stage: "+stage;
        g.drawString(str, 120, 30);
        
        str = "Ball_Speed: "+ ball.speed;
        g.drawString(str, 260, 30);
        
        str = "Speed: "+paddle.speed;
        g.drawString(str, 500, 30);
        str = "Bullet_Capacity: "+paddle.bullet_capacity;
        g.drawString(str, 500, 70);
    }

    public void update(){
        if(brick_counter == 0){
            nextStage();
        }
        if(continues < 0){
            resetGame();
        }
        
        paddle.update();
        
        checkBallRelease();
        ball.update();
        brickUpdate();
        perkUpdate();
        
        
            
    }
    public void nextStage(){
       default_setBricks();
       default_setBall();
       
       while(!paddle.shots.isEmpty())
            paddle.shots.removeFirst();
       
       
       falling_perks = new LinkedList<>();
       stage++;
       ball.speed += (stage -1);
    }
    
    public void resetGame(){
       default_setBricks();
       default_setPaddle();
       default_setBall();
       falling_perks = new LinkedList<>();
       continues = 3;
       stage = 1;
    }
    public void checkBallRelease(){
        if(!ball.released)
            ball.setLocation(paddle.x + (int)(paddle.width/2) - (int)(ball.width/2), 
                paddle.y-ball.height);  
    }
    
    public void brickUpdate(){
        for(Brick b:bricks){
            if(ball.intersects(b) && (b.destroyed == false)){
                ball.setDirections(ball.dx, -ball.dy);
                b.destroyed = true;
                brick_counter--;
                if(Math.random()<0.35){
                    double r = Math.random();
                    if(r>0.9)
                        falling_perks.add(new Perk(b.x, b.y, type.EXTRA_CONT));       
                    else if(r>0.5)
                        falling_perks.add(new Perk(b.x, b.y, type.GUN));
                    else if(r>0.4)
                        falling_perks.add(new Perk(b.x, b.y, type.FASTER));
                    else{}
                    
                }
            }
                
            int target_3 = -1;
            for(Bullet bu: paddle.shots){
                if(bu.intersects(b) && b.destroyed == false){
                    b.destroyed = true;
                    brick_counter--;
                    target_3 = paddle.shots.indexOf(bu);
                }
            } 
            if(target_3 != -1)
                paddle.shots.remove(target_3);
        }
    }
    
    public void perkUpdate(){
        int target_1 = -1;
        int target_2 = -1;
        LinkedList<Perk> temp = new LinkedList<>();
        for(Perk p: falling_perks){
            p.update();
            
            if(p.y > height)
                 target_1 = falling_perks.indexOf(p);
            
            
            
            if(p.intersects(paddle)){
                if(p.t == type.GUN){        
                    if(paddle.gun == false)
                        paddle.gun = true;
                    paddle.bullet_capacity++;
                }
                if(p.t == type.FASTER)
                    paddle.speed++;
                if(p.t == type.EXTRA_CONT)
                    continues++;
                temp.add(p);
            }
        }
        
        if(target_1 != -1)
            falling_perks.remove(target_1);
       for(Perk p: temp)
           falling_perks.remove(p);
       
    }
    
    void default_setPaddle(){
        paddle = new Paddle(this);
        paddle.setLocation( 500, 700);
    }
    
    void default_setPerkBox(){
        for(int i=0; i<5; i++)
            perk_box.add(new Rectangle());
    }
    
    void default_setBall(){  
        int n = 3;
        ball = new Ball(n, this);
        ball.setLocation(paddle.x + (int)(paddle.width/2) - (int)(ball.width/2), 
                paddle.y-ball.height);     
    }
    
    void default_setBricks(){
        Brick temp;
        int rows = 7;
        int columns = 5;
        bricks = new LinkedList<>();
        for(int i = 0; i<rows; i++){
            for(int j = 0; j<columns; j++){
                temp = new Brick();
                temp.setLocation(100+(i*150), 100+(j * 100));
                bricks.add(temp);
            }  
        }       
        brick_counter = bricks.size();
    }
    
    @Override
    public void run() {
     
        while(true){
            
            try {
                Thread.sleep(15);
            } catch (InterruptedException ex) {
                Logger.getLogger(Panel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            update();
            this.repaint();
        }
        
    }     
}
