import java.util.Scanner;

import javax.swing.*;

import floppybird.FlappyBird;
import snake.SnakeGame;  

public class App {
    public static void main(String[] args) throws Exception {

        //TODO :implement an interface to give user a way to select a game
        
        // System.out.println("""
        //         select wich game you'd like to Play :
        //             1 : snak game
        //             2 : floopy bird
        //             3 : neither
        //         """);
        // Scanner sc = new Scanner(System.in);
        // int userChoice = sc.nextInt();
        // sc.close();
        int userChoice = 2;


        if(userChoice == 1){
            int boardHight = 600;
            int boardwidth = 600;
            JFrame frame = new JFrame("snak");
            frame.setSize(boardwidth,boardHight);
            frame.setLocationRelativeTo(null);  
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            SnakeGame snakGame = new SnakeGame(boardwidth, boardHight);
            frame.add(snakGame);
            frame.pack();        
            snakGame.requestFocus();
            frame.setVisible(true);
        }
        
       if(userChoice == 2){
        int boardHight = 640;
        int boardwidth = 360;
        JFrame frame = new JFrame("flappy Bird");
        frame.setSize(boardwidth,boardHight);
        frame.setLocationRelativeTo(null);  
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        FlappyBird flappyBird = new FlappyBird(boardwidth,boardHight);
        frame.add(flappyBird);
        frame.pack();        
        flappyBird.requestFocus();        
        frame.setVisible(true);
        return;
        }
        if(userChoice == 3){
            System.out.println("more game will be added later stay tune :-) _");
            return;
        }
    }
}
