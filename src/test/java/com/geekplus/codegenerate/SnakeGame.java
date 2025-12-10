package com.geekplus.codegenerate;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * author     : geekplus
 * date       : 6/1/24 9:47 PM
 * description:
 */
public class SnakeGame extends JPanel implements ActionListener {

    private final int WIDTH = 600, HEIGHT = 400;
    private int x, y, size = 5;
    private int appleX, appleY;
    private boolean running = false;
    private Timer timer;
    private ArrayList<Point> snakeParts = new ArrayList<>();
    private Random random = new Random();

    public SnakeGame() {
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        addKeyListener(new MyKeyListener());
        setFocusable(true);
        start();
    }

    public void start() {
        x = WIDTH / 2;
        y = HEIGHT / 2;
        placeApple();
        running = true;
        timer = new Timer(100, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawSnake(g);
        drawApple(g);
    }

    public void drawSnake(Graphics g) {
        for (Point part : snakeParts) {
            g.setColor(Color.WHITE);
            g.fillRect(part.x, part.y, size, size);
        }
    }

    public void drawApple(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(appleX, appleY, size, size);
    }

    public void placeApple() {
        appleX = random.nextInt(WIDTH / size) * size;
        appleY = random.nextInt(HEIGHT / size) * size;
    }

    public void move() {
        Point newHead = new Point(x, y);
        snakeParts.add(0, newHead);

        if (x == appleX && y == appleY) {
            placeApple();
        } else {
            snakeParts.remove(snakeParts.size() - 1);
        }
    }

    public void checkCollisions() {
        for (int i = 1; i < snakeParts.size(); i++) {
            if (x == snakeParts.get(i).x && y == snakeParts.get(i).y) {
                running = false;
            }
        }

        if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) {
            running = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkCollisions();
            repaint();
        } else {
            timer.stop();
        }
    }

    private class MyKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_UP && y > 0) {
                y -= size;
            }

            if (key == KeyEvent.VK_DOWN && y < HEIGHT - size) {
                y += size;
            }

            if (key == KeyEvent.VK_LEFT && x > 0) {
                x -= size;
            }

            if (key == KeyEvent.VK_RIGHT && x < WIDTH - size) {
                x += size;
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(new SnakeGame(), BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
}
