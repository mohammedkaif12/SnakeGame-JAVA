import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.*;
import java.util.random.*;
import javax.swing.*;

public class Snakegame extends JPanel implements ActionListener, KeyListener {
    private class Tile{
        int x;
        int y;
        Tile(int x, int y){
            this.x=x;
            this.y=y;
        }

    }
    int boardwidth;
    int boardheight;
    int tileSize = 25;
    //snake
    Tile SnakeHead;
    ArrayList<Tile> snakebody;
    Tile food;
    Random random;

    //game logic
    Timer gameLoop;
    int velocityX;
    int velocityY;
    boolean gameOver = false;

    Snakegame(int boardwidth, int boardheight){
        this.boardwidth = boardwidth;
        this.boardheight = boardheight;
        setPreferredSize(new Dimension(this.boardwidth, this.boardheight));
        setBackground(Color.WHITE);
        addKeyListener(this);
        setFocusCycleRoot(true);

        SnakeHead = new Tile(5, 5);
        snakebody = new ArrayList<Tile>();

        food = new Tile(10, 10);
        random = new Random();
        placefood();

        velocityX=0;
        velocityY=1;

        gameLoop= new Timer(100,this);
        gameLoop.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        //grid
        // for(int i=0;i<boardwidth/tileSize;i++){
        //     g.drawLine(i*tileSize, 0, i*tileSize, boardheight);
        //     g.drawLine(0, i*tileSize, boardwidth, i*tileSize);
        // }
        //food
        g.setColor(Color.red);
        //g.fillRect(food.x*tileSize,food.y*tileSize, tileSize, tileSize);
        g.fill3DRect(food.x*tileSize,food.y*tileSize, tileSize, tileSize,true);

        //Snakehead
        g.setColor(Color.GREEN);
        // g.fillRect(SnakeHead.x*tileSize, SnakeHead.y*tileSize,tileSize,tileSize);
        g.fill3DRect(SnakeHead.x*tileSize, SnakeHead.y*tileSize,tileSize,tileSize,true);


        //snakebody
        for(int i=0;i<snakebody.size();i++){
            Tile snakepart =snakebody.get(i);
            //g.fillRect(snakepart.x*tileSize, snakepart.y*tileSize, tileSize, tileSize);
            g.fill3DRect(snakepart.x*tileSize, snakepart.y*tileSize, tileSize, tileSize,true);
        }
        //scor
        g.setFont(new Font("Arial",Font.PLAIN, 16));
        if(gameOver){
            g.setColor(Color.red);
            g.drawString("Game Over: "+String.valueOf(snakebody.size()), tileSize-16, tileSize);
        }
        else{
            g.drawString("Score: "+String.valueOf(snakebody.size()),tileSize-16,tileSize);
        }

    }
    public void placefood(){
        food.x=random.nextInt(boardwidth/tileSize);
        food.y= random.nextInt(boardheight/tileSize);
    }
    public boolean collision(Tile tile1, Tile tile2){
        return tile1.x == tile2.x && tile1.y==tile2.y;
    }
    public void move(){
        //snakehead
        if(collision(SnakeHead, food)){
            snakebody.add(new Tile(food.x, food.y));
            placefood();
        }

        for(int i=snakebody.size()-1;i>=0;i--){
            Tile snakepart = snakebody.get(i);
            if(i==0){
                snakepart.x=SnakeHead.x;
                snakepart.y=SnakeHead.y;
            }
            else{
                Tile prevsnakepart= snakebody.get(i-1);
                snakepart.x=prevsnakepart.x;
                snakepart.y=prevsnakepart.y;
            }
        }
        SnakeHead.x+=velocityX;
        SnakeHead.y+=velocityY;
        //gameover condition
        for(int i=0;i<snakebody.size();i++){
            Tile snakepart = snakebody.get(i);
            if(collision(SnakeHead, snakepart)){
                gameOver=true;
            }
        }
        if(SnakeHead.x*tileSize<0 || SnakeHead.x*tileSize>boardwidth || SnakeHead.y*tileSize<0 || SnakeHead.y*tileSize>boardheight){
            gameOver=true;
        }
    }
    @Override
    public void actionPerformed(ActionEvent e){
        move();
        repaint();
        if(gameOver){
            gameLoop.stop();
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_UP && velocityY!=1){
            velocityX=0;
            velocityY=-1;
        }
        else if(e.getKeyCode()==KeyEvent.VK_DOWN && velocityY!=-1){
            velocityX=0;
            velocityY=1;
        }
        else if(e.getKeyCode()==KeyEvent.VK_LEFT && velocityX!=1){
            velocityX=-1;
            velocityY=0;
        }
        else if(e.getKeyCode()==KeyEvent.VK_RIGHT && velocityX!=-1){
            velocityX=1;
            velocityY=0;
        }
    }
    //do not need these 
    //   ||
    //   ||
    //  \  /
    //   \/ 
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
    
}
