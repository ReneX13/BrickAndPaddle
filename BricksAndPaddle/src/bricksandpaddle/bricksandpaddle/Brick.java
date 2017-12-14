/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bricksandpaddle;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;


public class Brick extends Rectangle{
    
    public Color color;
    public boolean destroyed;
    public Image image;
    Brick(){
        width = 100;
        height = 100;
        color = Color.BLACK;
        destroyed = false;    
        image = new ImageIcon(System.getProperty("user.dir")  + "/src/bricksandpaddle/images/pokemon.png").getImage();
    }
    
    
    
    public void setLocation(int X, int Y){
        x = X;
        y = Y;
    }
    
    public void draw(Graphics g){
        if(destroyed)
            return;
        
        g.drawImage(image, x, y, null); 
    }
}

enum type {
        GUN, EXTRA_CONT, FASTER
}
class Perk extends Rectangle{
    public int dy;
    public type t;
    
    Perk(){}
    Perk(int X, int Y, type T){
        x = X;
        y= Y;
       
       width = 10;
       height = 10;
       dy = 1;
       
       t = T;
    }
    public void update(){
        y += dy;
    }
    
    public void draw(Graphics g){
        if(t == type.GUN)
            g.setColor(Color.RED);
        if(t == type.FASTER)
            g.setColor(Color.green);
        if(t == type.EXTRA_CONT)
            g.setColor(Color.BLUE);
        g.fillRect(x, y, width, height);
    }
}