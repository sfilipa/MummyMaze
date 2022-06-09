package MummyMaze;

import java.util.Arrays;
import java.util.Objects;

public class Cell {
    //atributos
    private int line, column;

    //construtores
    public Cell(int line, int column) {
        this.line = line;
        this.column = column;
    }
    public Cell(Cell cell) {
        this(cell.line, cell.column);
    }

    //setters
    public void setLine(int line) {
        this.line = line;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    //getters
    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    //se estão na mesma linha
    public boolean isInSameLine(Cell c){
        if(line == c.line){
            return true;
        }
        return false;
    }
    //se estão na mesma coluna
    public boolean isInSameColumn(Cell c){
        if(column == c.column){
            return true;
        }
        return false;
    }

    @Override
    protected Cell clone(){
        return new Cell(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cell)) return false;
        Cell cell = (Cell) o;
        return line == cell.line && column == cell.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(line, column);
    }


}