import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Timer;
import javax.swing.JPanel;

public class SnakeGame extends JPanel implements ActionListener,KeyListener{
    int boardWidth;
    int boardHeight;
    int tileSize = 12;

    Tile snakeHead;
    Tile target;

    //use a timer to keep redrowing panel each 100ms or so

    Timer gameLoop;

    int velocityX;
    int velocityY;

    SnakeGame(int width,int height){
        this.boardWidth = width;
        this.boardHeight = height;
        setPreferredSize(new Dimension(this.boardWidth,this.boardHeight));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);
        snakeHead = new Tile(5, 5);

        int randomX = (int)(Math.random()*((width/tileSize)-1));
        int randomY = (int)(Math.random()*((height/tileSize)-4));
        if(randomX == 5)randomX+=7;//make sure they don't overlap
        target = new Tile(randomX,randomY);

        velocityX = 0;
        velocityY = 0;
        gameLoop = new Timer(105,(ActionListener) this);
        gameLoop.start();
    }
    

    //paintComponent is automatically add when panel is added to a frame 
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        for(int i=0;i<boardWidth/tileSize;i++){
            //line starting point x,y Ending point x,y
            g.drawLine(0,tileSize*i, boardWidth, tileSize*i);
            g.drawLine(tileSize*i,0, tileSize*i, boardHeight);
        }
        
        //the snak head
        g.setColor(Color.GREEN);
        g.fillRect(snakeHead.x*tileSize, snakeHead.y*tileSize, tileSize, tileSize);


        //the snak food
        g.setColor(Color.RED);
        g.fillOval(target.x*tileSize, target.y*tileSize, tileSize, tileSize);
    }

    private class Tile {
        int x;
        int y;
        
        Tile(int x,int y){
            this.x=x;
            this.y=y;
        }
    
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(snakeHead.x*tileSize == boardWidth-1 ) return;
        // snakeHead.x = 0;

        snakeHead.x+=velocityX;
        snakeHead.y+=velocityY;



        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            velocityX = 0;
            velocityY = 0;
        }
        if(e.getKeyCode() == KeyEvent.VK_UP){
            velocityX = 0;
            velocityY = -1;
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN){
            velocityX = 0;
            velocityY = 1;
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            velocityX = 1;
            velocityY = 0;
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            velocityX = -1;
            velocityY = 0;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }
} 
