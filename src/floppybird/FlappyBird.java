package floppybird;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class FlappyBird  extends JPanel implements ActionListener, KeyListener {

    int boardWidth = 360;
    int boardHight = 640;

    //assets
    Image background;
    Image pipe;
    Image bird;
    
    //bird position
    int birdX = boardWidth/10;
    int birdY = boardHight/2;
    
    boolean climbingUp = false;;

    Timer gameLoop;

    public FlappyBird(int width ,int height){
        setPreferredSize(new Dimension(width,height));
        background = new ImageIcon(getClass().getResource("./assets/background-day.png")).getImage();
        bird = new ImageIcon(getClass().getResource("./assets/bluebird-midflap.png")).getImage();
        pipe = new ImageIcon(getClass().getResource("./assets/pipe-red.png")).getImage();
        
        addKeyListener(this);
        setFocusable(true);
        
        gameLoop = new Timer(100/6, (ActionListener) this);
        gameLoop.start();
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0,boardWidth,boardHight,null);
        g.drawImage(bird, birdX, birdY,null);
    }

//by default the bird moves down
    public void move(boolean up){
        if(up){
            birdY--;
        }else{
            birdY++;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }


    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            climbingUp = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            climbingUp = false;
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        move(climbingUp);
        repaint();
    }


}