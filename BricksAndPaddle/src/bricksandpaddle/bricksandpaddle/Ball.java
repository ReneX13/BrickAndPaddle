
package bricksandpaddle;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.math.*;
import javax.swing.ImageIcon;

public class Ball extends Rectangle{
    public Color color;
    public int radius;
    public boolean released;
    public int dx;
    public int dy;
    public int speed;
    public Panel panel;
    public Image image;

    
    Ball(int N, Panel P){
        radius = 25;
        width = height = 2*radius;
        color = Color.RED;
        released = false;
        
        dx=dy=0;
        speed = 2;
        panel = P;

        image = new ImageIcon(System.getProperty("user.dir")  + "/src/bricksandpaddle/images/pokeball.png").getImage();
    }
 
    public void setDirections(int DX, int DY){
        dx = DX;
        dy = DY;
    }
    
    public void update(){
        x += (int)(dx*speed);
        y += (int)(dy*speed);
        
        checkPaddleCollision();
        checkWallCollision();
    }
    
    public void checkPaddleCollision(){
        if(this.intersects(panel.paddle)){
                if(panel.paddle.dx != 0)
                    setDirections(panel.paddle.dx, -dy);
                else
                    setDirections(dx, -dy);      
                
                while(intersects(panel.paddle)){
                    x += dx;
                    y += dy;
                }
                    
                if(y + radius < panel.paddle.y )
                    y = panel.paddle.y - height;
                else
                    y = panel.paddle.y + panel.paddle.height;
        }
    }
    
    public void checkWallCollision(){
        if(x < 0){
            x = 0;
            dx = -dx;
        }
        
        if(x + width > panel.width){
            x = panel.width - width;
            dx = -dx;
        }
        if(y < 0){
            y =0;
            dy = -dy;
        }
        
        if(y + height > panel.height){
            panel.paddle.setLocation( 500, 700);
            setLocation(panel.paddle.x + (int)(panel.paddle.width/2) 
                - (int)(width/2), 
                panel.paddle.y-height); 
            
            panel.continues--;
        }
    }
    
    public void setLocation(int X, int Y){
        x = X;
        y = Y;
    }
    
    public void draw(Graphics g){
        g.drawImage(image, x, y, null);    
    }
}

