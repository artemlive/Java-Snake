/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package snakegame;

import java.awt.GridLayout;
import javax.swing.JFrame;
/**
 *
 * @author artemlive
 */
public class SnakeGame extends JFrame{

    
    public SnakeGame(){
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Snake");
        setResizable(false);
        
        init();
    }
    
    public void init(){
        setLayout(new GridLayout());
        
        Screen s = new Screen();
        add(s);
        
        pack();
        
        setLocationRelativeTo(null);
        setVisible(true);
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new SnakeGame();
    }
    
}
