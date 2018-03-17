package org.academiadecodigo.haltistas.halflifeminus3.BackGround;

import org.academiadecodigo.simplegraphics.pictures.Picture;

public class GameBackground {
    private Picture backgroundTile;
    private int col;
    private int row;

    public GameBackground(int col, int row,String asset){
        this.col=col;
        this.row=row;
        backgroundTile = new Picture(col*Grid.CELLSIZE+Grid.PADDING, row*Grid.CELLSIZE+Grid.PADDING,asset);

    }

    public void draw(){
        backgroundTile.draw();
    }

    public void translateTile(double col, double row){
        backgroundTile.translate(col, row);
    }

    public void delete() {
        backgroundTile.delete();
    }
}