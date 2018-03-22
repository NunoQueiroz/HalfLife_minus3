package org.academiadecodigo.haltistas.halflifeminus3.Client;

import org.academiadecodigo.haltistas.halflifeminus3.BackGround.Camera;
import org.academiadecodigo.haltistas.halflifeminus3.BackGround.Grid;
import org.academiadecodigo.haltistas.halflifeminus3.Controls;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Client {

    private Socket socket;
    private String hostName;
    private int portNumber;
    private PrintWriter printWriter;
    private Player player;
    private Controls controls;
    private Map<Integer, Player> playerList;
    private List<Bullet> bulletList;
    private Camera camera;

    public Client(String hostName, int portNumber, Player player, Controls controls, Camera camera) {

        this.hostName = hostName;
        this.portNumber = portNumber;
        this.player = player;
        this.controls = controls;
        this.playerList = new HashMap<>();
        this.bulletList = new LinkedList<>();
        this.camera = camera;

    }

    public void start() {

        try {

            socket = new Socket(hostName, portNumber);

            new Thread(new DataReceiver()).start();

            printWriter = new PrintWriter(socket.getOutputStream(), true);
            while (true) {

                if (controls.isPressedKey()) {

                    int col = player.getLogicalCol();
                    int row = player.getLogicalRow();
                    String message = PlayerCommandList.player(0, col, row);
                    System.out.println(message);
                    printWriter.println(message);
                    controls.resetPressedKey();
                }

                if (controls.shooted()) {
                    //  System.out.println("mandou mensagem");
                    double initialX = player.getLogicalCol() * Grid.CELLSIZE + Grid.PADDING;
                    double initialY = player.getLogicalRow() * Grid.CELLSIZE + Grid.PADDING;
                    double finalX = controls.getFinalX();
                    double finalY = controls.getFinalY();
                    String bulletMessage = PlayerCommandList.bullet(initialX, initialY, finalX, finalY);
                    printWriter.println(bulletMessage);

                    controls.resetShooted();
                }


                player.bulletsMove();
                drawBullet();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public class DataReceiver implements Runnable {

        private BufferedReader bufferedReader;

        @Override
        public void run() {

            try {

                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));


                while (true) {

                    String message = bufferedReader.readLine();
                    moveEnemy(message);
                    addBullet(message);

                }


            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                close(socket);
            }

        }
    }

    private void close(Socket socket) {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawBullet() {

        synchronized (bulletList) {

            for (int i = 0; i < bulletList.size(); i++) {

                if (camera.bulletIsInView(bulletList.get(i))) {
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    bulletList.get(i).draw();
                    bulletList.get(i).move();

                    if (collisionDetector(player, bulletList.get(i))) {

                        System.out.println("hit");
                    }

                } else {
                    bulletList.get(i).delete();
                    bulletList.remove(i);
                }

            }
        }
    }

    public boolean collisionDetector(Player player, Bullet bullet) {

        return (player.colToX() <= bullet.getX() && player.playerWidth() >= bullet.getX())
                && (player.rowToY() <= bullet.getY() && player.playerHeight() >= bullet.getY());


    }

    private void addBullet(String bulletMessage) {

        synchronized (bulletList) {

            String[] bulletData = bulletMessage.split(" ");

            if (!bulletData[0].equals("B")) {
                return;
            }


            double initialX = Double.parseDouble(bulletData[1]);
            double initialY = Double.parseDouble(bulletData[2]);
            double finalX = Double.parseDouble(bulletData[3]);
            double finalY = Double.parseDouble(bulletData[4]);

            Bullet bullet = new Bullet(initialX, initialY, finalX, finalY);
            bulletList.add(bullet);
            bullet.bulletInit();
        }
    }

    private void moveEnemy(String message) {

        String[] enemyPlayerData = message.split(" ");

        if (!enemyPlayerData[0].equals("M")) {
            return;
        }

        synchronized (playerList) {

            int playerNum = Integer.parseInt(enemyPlayerData[1]);
            int newCol = Integer.parseInt(enemyPlayerData[2]);
            int newRow = Integer.parseInt(enemyPlayerData[3]);


            Player enemy = playerList.get(playerNum);

            if (enemy == null) {
                playerList.put(playerNum, new Player());
                enemy = playerList.get(playerNum);
                enemy.initEnemyPlayer(newCol, newRow);

            }

            int lastCol = enemy.getLogicalCol();
            int lastRow = enemy.getLogicalRow();

            int translateX = newCol - lastCol;
            int translateY = newRow - lastRow;

            enemy.move(translateX, translateY);

            enemy.setPlayerCol(newCol);
            enemy.setPlayerRow(newRow);


            if (!camera.isInView(enemy)) {
                enemy.delete();
            } else {
                enemy.draw();
            }

        }
    }

}