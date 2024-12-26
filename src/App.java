import javax.swing.*;  

public class App {
    public static void main(String[] args) throws Exception {

        int boardHight = 600;
        int boardwidth = 600;
        JFrame frame = new JFrame("snak");
        frame.setVisible(true);
        frame.setSize(boardwidth,boardHight);
        frame.setLocationRelativeTo(null);  
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SnakeGame snakGame = new SnakeGame(boardwidth, boardHight);
        frame.add(snakGame);
        frame.pack();
        
        snakGame.requestFocus();
    }
}
