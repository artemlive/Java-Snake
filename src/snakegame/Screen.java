/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package snakegame;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.Color;
import javax.swing.JPanel;

/**
 *
 * @author artemlive
 */
public class Screen extends JPanel implements Runnable, KeyListener{

    private ArrayList <SnakeBodyPart> snake;
    private static int WIDTH = 800, HEIGHT = 800;
    private int snakeSize = 10, TILESIZE = 10;
    boolean isRunning = false;
    private Thread thread;
    private int direction = 1;
    
    public Screen(){
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        addKeyListener(this);
        setFocusable(true);
        initSnake();
        start();
    }
    
    
    @Override
    public void run() {

        //I think, that 60 ticks in sec, will be enough
        final double amountOfTicks = 15.0;
        long lastTime = System.nanoTime(); //get current time
        double ns = 1000000000 / amountOfTicks; //nanoseconds to seconds devider
        double delta = 0;
        int updates = 0, fps = 0;
        System.out.println("Start");
        long timer = System.currentTimeMillis();
        while(isRunning){
              // work out how long its been since the last update, this
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            //System.out.println("Delta: " + delta);
            if(delta >= 1)
            {
                tick();
                updates++;
                fps++;
                repaint();
                delta = 0;
            }
            if(System.currentTimeMillis() - timer > 1000)
            {
                timer += 1000;
                System.out.println("UPDATES: " + updates + " FPS:" + fps);
                updates = 0;
                fps = 0;
            }

        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("pressed key");
        switch(e.getKeyCode())
        {
            case KeyEvent.VK_UP:
                direction = 1;
                System.out.println("direction = 1");
                break;
                
            case KeyEvent.VK_DOWN:
                direction = -1;
                break;
            case KeyEvent.VK_RIGHT:
                direction = 2;
                break;
            case KeyEvent.VK_LEFT:
                direction = -2;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

    private void initSnake() {
        snake = new ArrayList<SnakeBodyPart>();
        for(int i = 0; i < snakeSize; i++)
        {
            snake.add(new SnakeBodyPart(0, i, TILESIZE));
        }
    }


    @Override
    public void paint(Graphics g){
        //System.out.println("Repainted");
        g.clearRect(0, 0, WIDTH, HEIGHT);
        g.setColor(new Color(50, 0, 0));
        g.fillRect(0, 0, WIDTH, HEIGHT);
        for(int i = 0; i < snakeSize; ++i)
        {
            snake.get(i).draw(g);
        }

    }

    private void start() {
        isRunning = true;
        thread = new Thread(this, "SnakeThread");
        thread.start();
    }

    private void tick() {
        for(int i = 0; i < snakeSize; ++i)
        {
            int x = snake.get(i).getxCoor();
            int y = snake.get(i).getyCoor();
            System.out.println("X-part-" + i + "-coor:" + x + ", Y-part-" + i +"-coor:" + y);
        }
        switch(direction)
        {
            case 1:
                snake.add(new SnakeBodyPart(snake.get(snake.size() - 1).getxCoor(), snake.get(snake.size() - 1).getyCoor() -1, TILESIZE));
                snake.remove(0);
                break;
                
            case -1:
                snake.add(new SnakeBodyPart(snake.get(snake.size() - 1).getxCoor(), snake.get(snake.size() - 1).getyCoor() + 1, TILESIZE));
                snake.remove(0);
                break;
            case 2:
                snake.add(new SnakeBodyPart(snake.get(snake.size() - 1).getxCoor() + 1, snake.get(snake.size() - 1).getyCoor(), TILESIZE));
                snake.remove(0);
                break;
                
            case -2:
                snake.add(new SnakeBodyPart(snake.get(snake.size() - 1).getxCoor() - 1, snake.get(snake.size() - 1).getyCoor() , TILESIZE));
                snake.remove(0);
                break;                
        }
    }
   

    
}
