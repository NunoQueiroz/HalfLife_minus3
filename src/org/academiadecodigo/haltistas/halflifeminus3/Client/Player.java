package org.academiadecodigo.haltistas.halflifeminus3.Client;

import org.academiadecodigo.haltistas.halflifeminus3.BackGround.Grid;
import org.academiadecodigo.simplegraphics.pictures.Picture;

import java.util.LinkedList;

public class Player {

    private int col;
    private int row;

    private int logicalCol;
    private int logicalRow;

    private Picture picture;
    private LinkedList<Bullet> bullets;
    private static final int MAX_BULLETS = 15;

    public Player (int col, int row) {
        this.col = col;
        this.row = row;
        this.picture = new Picture(col * Grid.CELLSIZE + Grid.PADDING,
                row * Grid.CELLSIZE + Grid.PADDING, "assets/player_sprite3.png");
        this.bullets = new LinkedList<>();

    }

    public void init () {
        picture.draw();
        this.logicalCol = col;
        this.logicalRow = row;
    }

    public void setLogicalCol(int logicalCol) {
        this.logicalCol += logicalCol;
    }

    public void setLogicalRow(int logicalRow) {
        this.logicalRow += logicalRow;
    }

    public void move (int colMove, int rowMove) {

        picture.translate(colMove * Grid.CELLSIZE, rowMove * Grid.CELLSIZE);
    }

    public void shoot (int finalX, int finalY) {

        if (bullets.size() >= MAX_BULLETS) {
            return;
        }

        double inicialX = Grid.PADDING + col * Grid.CELLSIZE;
        double inicialY = Grid.PADDING + row * Grid.CELLSIZE;

        bullets.add(new Bullet(inicialX, inicialY, finalX, finalY));
    }

    public void bulletsMove () {

        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).move();
        }
    }
}
