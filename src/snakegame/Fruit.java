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
public class Fruit {
    private int xCoor = 0, yCoor = 0, width = 0, height = 0;
    
    public Fruit(int x, int y, int tileSize) {
        xCoor = x;
        yCoor = y;
        width = height = tileSize;
    }

    public int getxCoor() {
        return xCoor;
    }

    public int getyCoor() {
        return yCoor;
    }
    
    public void draw(Graphics g){
        g.setColor(Color.blue);
        g.fillRect(xCoor * width, yCoor * height, width, height);
    }
}
