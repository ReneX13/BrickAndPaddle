/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bricksandpaddle;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Rectangle;
import java.util.LinkedList;

/**
 *
 * @author Rene
 */
public class Paddle extends Rectangle{
    public Color color;
    public int dx;
    public int speed;
    public Panel panel;
    
    public boolean gun;
    public boolean shoot;
    
    public boolean faster;
   
    public LinkedList<Bullet> shots;
    public int bullet_capacity;
    Paddle(Panel p){
       width = 100;
       height = 10;
       
       color = Color.BLUE;
       dx = 0;
       speed = 3;
     
       panel = p;      
       gun = false;
       shoot = false;
       faster = false;
       shots = new LinkedList<>();
       bullet_capacity = 0;
    }
    public void update(){
        x += dx*speed;
        
        if(x < 0)
            x = 0;
        if(x + width > panel.width)
            x = panel.width - width;
        
        
        int target = -1;
        for(Bullet b: shots){
            b.update();
            
            if(b.y<0)
                target = shots.indexOf(b);
        }
        if(target != -1)
            shots.remove(target);
        if(shots.isEmpty()){}
            //System.out.println("Frame size = " );

        if(shoot){
            if(shots.size() < bullet_capacity )
                shots.add(new Bullet(x + (width/2), y));
            shoot = false;
        }
    };
    
    public void setLocation(int X, int Y){
        x = X;
        y = Y;
    }
    
    public void draw(Graphics g){
        g.setColor(color);
        g.fillRect(x, y, width, height);
        
        for(Bullet b: shots)
            b.draw(g);
    }
    
}

class Bullet extends Rectangle{
    public int dy;
    public int speed;
    
    Bullet(){}
    Bullet(int X, int Y){
        x = X;
        y= Y;
       
        speed = 10;
       width = 3;
       height = 10;
       dy = 1;
    }
    public void update(){
        y -= dy*speed;
    }
    
    public void draw(Graphics g){
        
        g.setColor(Color.red);
        g.fillRect(x, y, width, height);
    }
}
