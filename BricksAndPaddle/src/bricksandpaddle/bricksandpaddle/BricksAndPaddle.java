
package bricksandpaddle;

import java.awt.Dimension;
import javax.swing.JFrame;

public class BricksAndPaddle {

   
    public static void main(String[] args) {
        int w = 1280;
        int h = 768;
        JFrame frame = new JFrame();
        Panel panel;
        panel = new Panel(frame,w, h);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        panel.setPreferredSize(new Dimension(w,h));
        
        frame.getContentPane().add(panel);
        frame.pack();
        
        frame.setVisible(true);

      
        
        
       // System.out.println("Frame size = " + panel.getSize());
    }
    
}
