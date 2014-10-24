/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package snakegame;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author artemlive
 */
public class SnakeBodyPart {
    private int xCoor, yCoor, width, height;
    
    public SnakeBodyPart(int x, int y, int tileSize){
        xCoor = x;
        yCoor = y;
        width = tileSize;
        height = tileSize;
    }

    public int getxCoor() {
        return xCoor;
    }

    public int getyCoor() {
        return yCoor;
    }
    
    public void draw(Graphics g){
        g.setColor(Color.black);
        g.drawRect(xCoor * width, yCoor * height, width, height);
        g.setColor(Color.green);
        g.fillRect(xCoor * width + 2 , yCoor*height + 2, width - 2, height - 2);
    }
}
