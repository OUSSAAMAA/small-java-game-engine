package floppybird;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class FlappyBird  extends JPanel implements ActionListener, KeyListener {

    int boardWidth = 360;
    int boardHight = 640;

    //assets
    Image background;
    Image pipe_up_img;
    Image pipe_down_img;
    Image bird_midFlap;
    Image bird_upFlap;
    Image gameOver_img;
    Image bird_downFlap;
    
    int counter = 0;
    int pipeTouched = 0;
    

    //bird position
    int birdX = boardWidth/10;
    int birdY = boardHight/2;


    //pipes
    Pipe pipe_up;
    Pipe pipe_down;
    
    


    boolean climbingUp = false;
    int gravityEffect = 1;

    Timer gameLoop;

    boolean gameOver = false;

    public FlappyBird(int width ,int height){
        setPreferredSize(new Dimension(width,height));
        background = new ImageIcon(getClass().getResource("./assets/background-day.png")).getImage();
        bird_midFlap = new ImageIcon(getClass().getResource("./assets/bluebird-midflap.png")).getImage();
        bird_upFlap = new ImageIcon(getClass().getResource("./assets/bluebird-upflap.png")).getImage();
        bird_downFlap = new ImageIcon(getClass().getResource("./assets/bluebird-downflap.png")).getImage();
        pipe_up_img = new ImageIcon(getClass().getResource("./assets/pipe-green_up.png")).getImage();
        pipe_down_img = new ImageIcon(getClass().getResource("./assets/pipe-green.png")).getImage();
        gameOver_img = new ImageIcon(getClass().getResource("./assets/gameover.png")).getImage();
        
        addKeyListener(this);
        setFocusable(true);

        pipe_up = new Pipe(boardWidth-100, boardHight-240, 60, 240);
        pipe_down = new Pipe(boardWidth-100, 0, 60, 240);
        
        gameLoop = new Timer(100/6, (ActionListener) this);
        gameLoop.start();
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0,boardWidth,boardHight,null);
        if(counter%3 == 0 ){
            g.drawImage(bird_midFlap, birdX, birdY,null);
        }
        if(counter%3 == 1 ){
            g.drawImage(bird_upFlap, birdX, birdY,null);
        }
        if(counter%3 == 2 ){
            g.drawImage(bird_downFlap, birdX, birdY,null);
            
        }
        if(!gameOver)counter++;

        g.setFont(new Font(Font.MONOSPACED,Font.PLAIN,14));
        g.setColor(Color.yellow);
        g.drawString("Score : "+String.valueOf(counter-1) , 15,15 );
        if(gameOver == true){
            g.drawImage(gameOver_img, 80, boardHight/3,null);
        }

        this.drawPipeswithRandomness(g);
    }

//by default the bird moves down 
    public void move(boolean up){
        if(gameOver == false){
            birdY+=gravityEffect;
            if(up){
                birdY--;
            }else{
                birdY++;
            }
            birdY = Math.max(birdY, 0);
            //moving the pipes
            if(pipe_down != null && pipe_up != null){
                pipe_down.x -=1;
                pipe_up.x -=1;
            }
        }

        if(collision()){
            gameOver = true;
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }


    @Override
    public void keyPressed(KeyEvent e) {
        // if (e.getKeyCode() == KeyEvent.VK_UP) {
        //     climbingUp = false;
        // }
        // if (e.getKeyCode() == KeyEvent.VK_DOWN) {
        //     climbingUp = false;
        // }
        if (e.getKeyCode() == KeyEvent.VK_SPACE && gameOver == false) {
            birdY-=80;
            climbingUp = false;
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        // move(climbingUp);
        move(false);
        repaint();
    }



    //the trick is that will keep drawing pipes at the same coordinates but is it up/down/both and how much going to tighen stuffs
    public void drawPipeswithRandomness(Graphics g) {
        if(gameOver)return;
        pipe_down.x--;
        pipe_up.x--;

        g.drawImage(pipe_up_img, pipe_up.x, pipe_up.y,pipe_up.width , pipe_up.height ,null);
        g.drawImage(pipe_down_img, pipe_down.x, pipe_down.y,pipe_down.width , pipe_down.height ,null);
        

        if(pipe_down.x < -pipe_down.width && pipe_up.x < -pipe_down.width ){
            Random random = new Random();
            pipe_down.height =  random.nextInt((boardHight/2));
            pipe_up.height =  random.nextInt((boardHight/2));
            
            pipe_down.x = boardWidth;
            pipe_up.x = boardWidth;
        }
    }

    public boolean collision(){
        boolean touched = 
        (birdY < pipe_down.y+pipe_down.height && (birdX > pipe_down.x && birdX<pipe_down.x+pipe_down.width)) || (birdY > boardHight-pipe_up.height && (birdX > pipe_up.x && birdX<pipe_up.x+pipe_up.width)); 
        if(touched){
            pipeTouched++;
        }
        return touched;
    }
                                                                                                                                                                        
}