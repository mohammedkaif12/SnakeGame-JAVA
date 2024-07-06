import javax.swing.*;
public class App {
    public static void main(String[] args) throws Exception {
        int boardwidth=700;
        int boardheight=boardwidth;

        JFrame frame = new JFrame("Snake");
        frame.setVisible(true);
        frame.setSize(boardheight, boardwidth);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Snakegame snakeGame = new Snakegame(boardwidth, boardheight);
        frame.add(snakeGame);
        frame.pack();
        snakeGame.requestFocus();

    }
}
