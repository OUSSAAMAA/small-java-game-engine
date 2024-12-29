package snake;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.Timer;
import javax.swing.JPanel;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {
    int boardWidth;
    int boardHeight;
    int tileSize = 25;

    Tile snakeHead;
    Tile target;

    // use a timer to keep redrowing panel each 100ms or so

    Timer gameLoop;

    int velocityX;
    int velocityY;

    ArrayList<Tile> snakeBody;
    boolean  gameOver = false; 
    public SnakeGame(int width, int height) {
        this.boardWidth = width;
        this.boardHeight = height;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);
        snakeHead = new Tile(0, 0);

        placeFood();

        snakeBody = new ArrayList<>();

        velocityX = 0;
        velocityY = 0;
        gameLoop = new Timer(150, (ActionListener) this);
        gameLoop.start();
    }

    // paintComponent is automatically add when panel is added to a frame
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        for (int i = 0; i < boardWidth / tileSize; i++) {
            // line starting point x,y Ending point x,y
            g.drawLine(0, tileSize * i, boardWidth, tileSize * i);
            g.drawLine(tileSize * i, 0, tileSize * i, boardHeight);
        }

        // the snak head
        g.setColor(Color.GREEN);
        g.fillRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize);

        // snake body
        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakPart = snakeBody.get(i);
            g.fillRect(snakPart.x * tileSize, snakPart.y * tileSize, tileSize, tileSize);

        }

        // the snak food
        g.setColor(Color.RED);
        g.fillOval(target.x * tileSize, target.y * tileSize, tileSize, tileSize);

        //score
        g.setFont(new Font(Font.MONOSPACED,Font.PLAIN,14));
        g.setColor(Color.yellow);
        g.drawString("Score : "+String.valueOf(snakeBody.size()) , 15,15 );
        if(gameOver == true){
            g.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,27));
            g.setColor(Color.RED);
            g.drawString("Game over" , 200,200 );
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        this.move();
        if (collision(snakeHead, target)) {
            snakeBody.add(new Tile(target.x, target.y));
            placeFood();
        }
        repaint();

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            snakeBody.clear();
            gameOver = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            velocityX = 0;
            velocityY = -1;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            velocityX = 0;
            velocityY = 1;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            velocityX = 1;
            velocityY = 0;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
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

    public void move() {

         if(velocityX == 0 && velocityY ==0)return;

        // snak's body follow's the head
        for (int i = snakeBody.size() - 1; i >= 0; i--) {
            Tile part = snakeBody.get(i);
            if (i == 0) {
                part.x = snakeHead.x;
                part.y = snakeHead.y;
            } else {
                part.x = snakeBody.get(i - 1).x;
                part.y = snakeBody.get(i - 1).y;

            }
        }
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;


        for (int i = 0; i < snakeBody.size(); i++) {
            if (collision(snakeHead, snakeBody.get(i))){
                stopTile();
                break;
            }
        }

        if (snakeHead.x * tileSize == boardWidth) {
            snakeHead.x = 0;
        }
        if (snakeHead.x == -1) {
            snakeHead.x = (boardWidth / tileSize);
        }
        if (snakeHead.y * tileSize == boardHeight) {
            snakeHead.y = 0;
        }
        if (snakeHead.y == -1) {
            snakeHead.y = (boardHeight / tileSize);

        }

    }

    public void stopTile() {
        velocityX = 0;
        velocityY = 0;
    }

    public boolean collision(Tile a, Tile b) {
        return (a.x == b.x && a.y == b.y);
    }

    public void placeFood() {
        int randomX = (int) (Math.random() * ((boardWidth / tileSize) - 1));
        int randomY = (int) (Math.random() * ((boardHeight / tileSize) - 4));
        target = new Tile(randomX, randomY);
    }

    private class Tile {
        int x;
        int y;

        Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }

    }
}
