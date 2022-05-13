package MummyMaze;

public class Cell {
    private final int line, column;

    public Cell(int line, int column) {
        this.line = line;
        this.column = column;
    }

    //equals, is in mesma linha
    //em vez das linhas para heroi e mumia por as celulas
    //celula so para guardar pos




    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }
}