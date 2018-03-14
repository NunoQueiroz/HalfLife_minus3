package org.academiadecodigo.haltistas.halflifeminus3.BackGround;

import org.academiadecodigo.simplegraphics.pictures.Picture;

public class Grid {

    private int totalCol;
    private int totalRow;
    private int currentCol;
    private int currentRow;
    private Picture[][] grid;
    public static final int CELLSIZE = 30;
    public static final int PADDING = 10;
    public static final int CAMERA_HEIGHT = 15;
    public static final int CAMERA_WIDHT = 20;


    public Grid (int totalCol, int totalRow, int currentCol, int currentRow) {
        this.totalCol = totalCol;
        this.totalRow = totalRow;
        this.grid = new Picture[totalCol][totalRow];
        this.currentCol = currentCol;
        this.currentRow = currentRow;
    }

    public void init () {

        for (int col = 0; col < totalCol; col++) {
            for (int row = 0; row < totalRow; row++) {
                grid[col][row] = new Picture(col * CELLSIZE + PADDING, row * CELLSIZE + PADDING, "assets/grass_tile.jpg");
            }
        }
    }

    public void camera() {
        translate(-currentCol, -currentRow);

        for (int col = currentCol; col < CAMERA_WIDHT + currentCol; col++) {
            for (int row = currentRow; row < CAMERA_HEIGHT + currentRow; row++) {
                grid[col][row].draw();
            }
        }

    }

    public void translate(int colMove, int rowMove) {

        for (int col = 0; col < totalCol; col++) {
            for (int row = 0; row < totalRow; row++) {
                grid[col][row].translate(colMove * CELLSIZE, rowMove * CELLSIZE);
            }
        }
    }

    public void moveLeft() {

        if (currentCol == 0) {
            return;
        }

        translate(1, 0);
        for (int i = currentRow; i < currentRow + CAMERA_HEIGHT; i++) {
            grid[currentCol - 1][i].draw();
            grid[currentCol + CAMERA_WIDHT - 1][i].delete();
        }
        currentCol -= 1;
    }

    public void moveRight() {

        if (currentCol == totalCol - CAMERA_WIDHT) {
            return;
        }

        translate(-1, 0);
        for (int i = currentRow; i < currentRow + CAMERA_HEIGHT; i++) {
            grid[currentCol][i].delete();
            grid[currentCol + CAMERA_WIDHT][i].draw();
        }
        currentCol += 1;
    }

    public void moveUp() {
        if (currentRow == 0) {
            return;
        }

        translate(0, 1);
        for (int i = currentCol; i < currentCol + CAMERA_WIDHT; i++) {
            grid[i][currentRow - 1].draw();
            grid[i][currentRow + CAMERA_HEIGHT - 1].delete();

        }
        currentRow -= 1;

    }

    public void moveDown() {
        if (currentRow == totalRow - CAMERA_HEIGHT - 1) {
            return;
        }

        translate(0, -1);

        for (int i = currentCol; i < currentCol + CAMERA_WIDHT; i++) {
            grid[i][currentRow].delete();
            grid[i][currentRow + CAMERA_HEIGHT].draw();
        }
        currentRow += 1;

    }


}
