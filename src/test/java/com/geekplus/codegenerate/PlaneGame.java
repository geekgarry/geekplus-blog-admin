
package com.geekplus.codegenerate;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.*;

/**
 * author     : geekplus
 * date       : 6/1/24 9:43 PM
 * description:
 */
public class PlaneGame extends JFrame implements KeyListener {

    private int frameWidth = 800;  // 窗口宽度
    private int frameHeight = 600; // 窗口高度
    private int enemyCount = 6;    // 敌机数量
    private int bulletSpeed = 4;   // 子弹速度
    private int enemySpeed = 2;    // 敌机速度

    private List<EnemyPlane> enemies;  // 敌机集合
    private Plane player;             // 玩家飞机

    // 初始化游戏
    public void init() {
        // 创建敌机集合
        enemies = new ArrayList<>();
        for (int i = 0; i < enemyCount; i++) {
            enemies.add(new EnemyPlane());
        }

        // 创建玩家飞机
        player = new Plane();

        // 设置窗口大小和标题
        setSize(frameWidth, frameHeight);
        setTitle("打飞机游戏");

        // 添加键盘监听器
        addKeyListener(this);

        // 设置窗口可见性
        setVisible(true);
    }

    // 绘制游戏界面
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // 绘制背景
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, frameWidth, frameHeight);

        // 绘制敌机
        for (EnemyPlane enemy : enemies) {
            enemy.draw(g);
        }

        // 绘制玩家飞机
        player.draw(g);
    }

    // 检测敌机与玩家飞机的碰撞
    public boolean checkCollision() {
        for (EnemyPlane enemy : enemies) {
            if (enemy.getRectangle().intersects(player.getRectangle())) {
                return true;
            }
        }
        return false;
    }

    // 更新游戏状态
    public void update() {
        // 更新敌机位置
        for (EnemyPlane enemy : enemies) {
            enemy.move();
        }

        // 检查敌机与玩家飞机的碰撞
        if (checkCollision()) {
            // 游戏结束
            JOptionPane.showMessageDialog(this, "游戏结束！");
            System.exit(0);
        }
    }

    // 处理键盘事件
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            player.moveUp();
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            player.moveDown();
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            player.moveLeft();
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            player.moveRight();
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            // 发射子弹
            player.shoot();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // 按键抬起时停止移动
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
            player.stopYMove();
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            player.stopXMove();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    // 主函数
    public static void main(String[] args) {
        PlaneGame game = new PlaneGame();
        game.init();

        // 游戏主循环
        while (true) {
            game.update();
            game.repaint();

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // 敌机类
    private class EnemyPlane {

        private int x;    // 横坐标
        private int y;    // 纵坐标
        private int speed;    // 速度
        private Image image;  // 图片

        public EnemyPlane() {
            speed = enemySpeed;
            image = new ImageIcon("enemy.png").getImage();
            randomPosition();  // 随机生成位置
        }

        // 随机生成位置
        public void randomPosition() {
            Random random = new Random();
            x = random.nextInt(frameWidth - image.getWidth(null));
            y = -image.getHeight(null);
        }

        // 移动
        public void move() {
            y += speed;
            if (y > frameHeight) {
                randomPosition();
            }
        }

        // 绘制
        public void draw(Graphics g) {
            g.drawImage(image, x, y, null);
        }

        // 获取矩形区域
        public Rectangle getRectangle() {
            return new Rectangle(x, y, image.getWidth(null), image.getHeight(null));
        }
    }

    // 玩家飞机类
    private class Plane {

        private int x;    // 横坐标
        private int y;    // 纵坐标
        private int speed;    // 速度
        private Image image;  // 图片
        private List<Bullet> bullets;    // 子弹集合

        public Plane() {
            x = frameWidth / 2 - 50;
            y = frameHeight / 2 - 50;
            speed = 5;
            image = new ImageIcon("plane.png").getImage();
            bullets = new ArrayList<>();
        }

        // 向上移动
        public void moveUp() {
            y -= speed;
        }

        // 向下移动
        public void moveDown() {
            y += speed;
        }

        // 向左移动
        public void moveLeft() {
            x -= speed;
        }

        // 向右移动
        public void moveRight() {
            x += speed;
        }

        // 停止Y轴移动
        public void stopYMove() {
            speed = 0;
        }

        // 停止X轴移动
        public void stopXMove() {
            speed = 0;
        }

        // 发射子弹
        public void shoot() {
            bullets.add(new Bullet());
        }

        // 绘制
        public void draw(Graphics g) {
            g.drawImage(image, x, y, null);

            // 绘制子弹
            for (Bullet bullet : bullets) {
                bullet.draw(g);
            }
        }

        // 获取矩形区域
        public Rectangle getRectangle() {
            return new Rectangle(x, y, image.getWidth(null), image.getHeight(null));
        }
    }

    // 子弹类
    private class Bullet {

        private int x;    // 横坐标
        private int y;    // 纵坐标
        private int speed;    // 速度

        public Bullet() {
            x = player.x + player.image.getWidth(null) / 2 - 2;
            y = player.y;
            speed = bulletSpeed;
        }

        // 移动
        public void move() {
            y -= speed;
            if (y < 0) {
                // 子弹超出边界，从集合中移除
                player.bullets.remove(this);
            }
        }

        // 绘制
        public void draw(Graphics g) {
            g.setColor(Color.RED);
            g.fillOval(x, y, 4, 4);
        }
    }
}
