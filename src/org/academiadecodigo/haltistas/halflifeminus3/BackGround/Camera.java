package org.academiadecodigo.haltistas.halflifeminus3.BackGround;

import org.academiadecodigo.haltistas.halflifeminus3.Client.Player;

public class Camera {

    public static final int CAMERA_WIDTH = 24;
    public static final int CAMERA_HEIGHT = 14;

    private int backCol;
    private int topRow;
    private Grid grid;
    private Player player;


    public Camera(int backCol, int topRow, Grid grid) {
        this.backCol = backCol;
        this.topRow = topRow;
        this.grid = grid;

    }

    public void init() {
        int colP = (backCol + Camera.CAMERA_WIDTH) / 2 - 1;
        int rowP = (topRow + Camera.CAMERA_HEIGHT) / 2 - 1;

        grid.translateGrid(-backCol, -topRow);

        for (int col = backCol; col < backCol + CAMERA_WIDTH; col++) {
            for (int row = topRow; row < topRow + CAMERA_HEIGHT; row++) {
                grid.getGrid()[col][row].draw();

            }

        }

        this.player = new Player(colP, rowP);
        player.init();
    }

    public void moveRight() {

        System.out.println(backCol + "C");

        if (backCol + CAMERA_WIDTH >= Grid.MAX_COL) {
            return;

        }


        for (int row = topRow; row < topRow + CAMERA_HEIGHT; row++) {
            grid.getGrid()[backCol][row].delete();
            grid.getGrid()[backCol + CAMERA_WIDTH][row].draw();

        }

        grid.translateGrid(-1, 0);
        player.debug();
        backCol++;

    }

    public void moveLeft() {

        System.out.println(backCol + "C");

        if (backCol <= 0) {
            return;

        }

        for (int row = topRow; row < topRow + CAMERA_HEIGHT; row++) {
            grid.getGrid()[backCol + CAMERA_WIDTH - 1][row].delete();
            grid.getGrid()[backCol - 1][row].draw();

        }

        grid.translateGrid(1, 0);
        player.debug();
        backCol--;

    }

    public void moveUp() {
        System.out.println(topRow + "R");
        if (topRow <= 0) {
            return;

        }


        for (int col = backCol; col < backCol + CAMERA_WIDTH; col++) {
            grid.getGrid()[col][topRow + CAMERA_HEIGHT - 1].delete();
            grid.getGrid()[col][topRow - 1].draw();

        }

        grid.translateGrid(0, 1);
        player.debug();
        topRow--;
    }


    public void moveDown() {
        System.out.println(topRow + "R");
        if (topRow + CAMERA_HEIGHT >= Grid.MAX_ROW) {
            return;

        }

        for (int col = backCol; col < backCol + CAMERA_WIDTH; col++) {
            grid.getGrid()[col][topRow].delete();
            grid.getGrid()[col][topRow + CAMERA_HEIGHT].draw();

        }

        grid.translateGrid(0, -1);
        player.debug();
        topRow++;
    }
}
