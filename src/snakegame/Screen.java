/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package snakegame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author artemlive
 */
public class Screen extends JPanel implements Runnable, KeyListener{

    private ArrayList <SnakeBodyPart> snake;
    private static int WIDTH = 800, HEIGHT = 800;
    private int snakeSize = 8, TILESIZE = 10;
    boolean isRunning = false;
    private Thread thread;
    private int direction = -1;
    private boolean fruitIsEatten = true;
    private ArrayList <Fruit> fruit;
    
    public Screen(){
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        addKeyListener(this);
        setFocusable(true);
        initSnake();
        initFruit();
        start();
    }
    
    
    @Override
    public void run() {

        //I think, that 60 ticks in sec, will be enough
        double amountOfTicks = 10.0;
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
    //    System.out.println("Pressed key: " + e.getKeyChar());
        switch(e.getKeyCode())
        {
            case KeyEvent.VK_UP:
                if(direction != -1){
                    direction = 1;
                }
                break;
                
            case KeyEvent.VK_DOWN:
                if(direction != 1){
                    direction = -1;
                }
                break;
            case KeyEvent.VK_RIGHT:
                if(direction != -2){
                    direction = 2;
                }
                break;
            case KeyEvent.VK_LEFT:
                if(direction != 2){
                    direction = -2;
                }
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

    private void initFruit() {
        fruit = new ArrayList<Fruit>();
        Random rn = new Random();
        fruit.add(new Fruit(rn.nextInt((WIDTH / TILESIZE)) , rn.nextInt(HEIGHT / TILESIZE), TILESIZE));
        System.out.println("Fruit x: " + fruit.get(0).getxCoor() + ", fruit y: " + fruit.get(0).getyCoor());
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
        for(int i = 0; i < fruit.size(); ++i)
        {
            fruit.get(0).draw(g);
        }
    }

    private void start() {
        isRunning = true;
        thread = new Thread(this, "SnakeThread");
        thread.start();
    }

    private void tick() {

        switch(direction)
        {
            case 1:
                snake.remove(0);
                snake.add(new SnakeBodyPart(snake.get(snake.size() - 1).getxCoor(), snake.get(snake.size() - 1).getyCoor() -1, TILESIZE));
                break;
                
            case -1:
                snake.remove(0);
                snake.add(new SnakeBodyPart(snake.get(snake.size() - 1).getxCoor(), snake.get(snake.size() - 1).getyCoor() + 1, TILESIZE));
                break;
            case 2:
                snake.remove(0);
                snake.add(new SnakeBodyPart(snake.get(snake.size() - 1).getxCoor() + 1, snake.get(snake.size() - 1).getyCoor(), TILESIZE));
                break;
                
            case -2:
                snake.remove(0);
                snake.add(new SnakeBodyPart(snake.get(snake.size() - 1).getxCoor() - 1, snake.get(snake.size() - 1).getyCoor() , TILESIZE));
                break;                
        }
        System.out.println("snake head position: x = " + snake.get(snake.size() - 1).getxCoor() + ", y = " + snake.get(snake.size() - 1).getyCoor());
        if(snake.get(snake.size() - 1).getxCoor() == fruit.get(0).getxCoor() && snake.get(snake.size() - 1).getyCoor() == fruit.get(0).getyCoor()){
            switch(direction)
            {
                case 1:
                    snake.add(new SnakeBodyPart(snake.get(snake.size() - 1).getxCoor(), snake.get(snake.size() - 1).getyCoor() -1, TILESIZE));
                break;
                case -1:
                    snake.add(new SnakeBodyPart(snake.get(snake.size() - 1).getxCoor(), snake.get(snake.size() - 1).getyCoor() + 1, TILESIZE));
                break;
                case 2:
                    snake.add(new SnakeBodyPart(snake.get(snake.size() - 1).getxCoor() + 1, snake.get(snake.size() - 1).getyCoor(), TILESIZE));
                break;
                case -2:
                    snake.add(new SnakeBodyPart(snake.get(snake.size() - 1).getxCoor() - 1, snake.get(snake.size() - 1).getyCoor() , TILESIZE));
                break;                
            }
            //System.out.println("Collision detected");
            snakeSize++;
            fruit.clear();
        }
        for(int i = 0; i < snake.size() - 1; ++i)
        {
            if(snake.get(i).getxCoor() == snake.get(snake.size() - 1).getxCoor() && snake.get(i).getyCoor() == snake.get(snake.size() - 1).getyCoor())
            {
                  isRunning = false;
                  JOptionPane.showMessageDialog(null, "Скушали сами себя!:(", "Проигрыш:(", JOptionPane.INFORMATION_MESSAGE);
            }
        }
         if(snake.get(snake.size() - 1).getxCoor() >= WIDTH / TILESIZE ||
                 snake.get(snake.size() - 1).getxCoor() < 0 || 
                 snake.get(snake.size() - 1).getyCoor() >= HEIGHT / TILESIZE ||
                 snake.get(snake.size() - 1).getyCoor() < 0){
             
             isRunning = false;
             JOptionPane.showMessageDialog(null, "Вы проиграли! Нам оооочень жаль!", "Проигрыш:(", JOptionPane.INFORMATION_MESSAGE);
             //TODO: System menu, again exit etc
             System.exit(0);
         }
        if(fruit.isEmpty()) {
            Random rn = new Random();
            fruit.add(new Fruit(rn.nextInt(WIDTH / TILESIZE), rn.nextInt(HEIGHT / TILESIZE), TILESIZE));
            System.out.println("Fruit x: " + fruit.get(0).getxCoor() + ", fruit y: " + fruit.get(0).getyCoor());
        }
    }
   

    
}
