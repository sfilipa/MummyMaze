package MummyMaze;

import agent.Action;
import agent.State;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MummyMazeState extends State implements Cloneable {
    //cleneable é copiar o objeto

    //sitio onde as peças devem estar na matriz final
    //                              1  2  3  4  5  6  7  8  9 Peças de 1 a 9
    //final char[] linesfinalMatrix = {0, 0, 0, 1, 1, 1, 2, 2, 2};
    //final char[] colsfinalMatrix = {0, 1, 2, 0, 1, 2, 0, 1, 2};
    public static final int SIZE = 13; //tamanho da matriz, 13*13
    private char[][] matrix;
    private int lineBlank; //variável auxiliar
    private int columnBlank; //variável auxiliar
    private int lineHeroi;
    private int columHeroi;
    private List<Enemy> enemies;


    public MummyMazeState(char[][] matrix) {
        this.matrix = new char[matrix.length][matrix.length];
        enemies = new ArrayList<>();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                this.matrix[i][j] = matrix[i][j];
                if (this.matrix[i][j] == 0) {
                    lineBlank = i;
                    columnBlank = j;
                }
                if(matrix[i][j] == 'H'){
                    lineHeroi = i;
                    columHeroi =j;
                }
                if(matrix[i][j] == 'M'){
                    Enemy enemy = new Enemy(EnemyType.WHITEMUMMY, i, j);
                    enemies.add(enemy);
                }
            }
        }

    }

    @Override
    public void executeAction(Action action) {
        action.execute(this);//método polimórfico - pode executar métodos diferentes consoante a action
        for (Enemy e : enemies) {
            e.move(this);
        }

        firePuzzleChanged(null); //atualizar a interface gráfica
    }

    public boolean canMoveUp() {//pode mover-se se não tiver parede nem mumia nem nd do genero
        if(lineHeroi > 1){
            if(matrix[lineHeroi-2][columHeroi] == '.' && matrix[lineHeroi-1][columHeroi] != '-' && matrix[lineHeroi-2][columHeroi] != 'M') {
                return true;
            }
        }
        return false;
    }

    public boolean canMoveRight() {
        if(columHeroi != matrix.length - 2 ) {
            if (matrix[lineHeroi][columHeroi + 2] == '.' && matrix[lineHeroi][columHeroi+1] != '|' && matrix[lineHeroi][columHeroi + 2] != 'M') {
                return true;
            }
        }
        return false;
    }

    public boolean canMoveDown() {
        if(lineHeroi!= matrix.length - 2 ) {
            if (matrix[lineHeroi + 2][columHeroi] == '.' && matrix[lineHeroi+1][columHeroi] != '-' && matrix[lineHeroi + 2][columHeroi] != 'M') {
                return true;
            }
        }
        return false;

    }

    public boolean canMoveLeft() {
        if(columHeroi > 1) {
            if (matrix[lineHeroi][columHeroi - 2] == '.' && matrix[lineHeroi][columHeroi-1] != '|' && matrix[lineHeroi][columHeroi - 2] != 'M') {
                return true;
            }
        }
        return false;
    }

    public boolean cannotMove(){  //acrescentado, quando o heroi não se mexe - mudar
        return false;
    }

    public void moveUp() { //para cima
        matrix[lineHeroi - 2][columHeroi] = matrix[lineHeroi][columHeroi];
        matrix[lineHeroi][columHeroi] = '.';
        lineHeroi = lineHeroi -2;
    }


    public void moveRight() {
        matrix[lineHeroi][columHeroi+2] = matrix[lineHeroi][columHeroi];
        matrix[lineHeroi][columHeroi] = '.';
        columHeroi = columHeroi+2;
    }


    public void moveDown() {
        matrix[lineHeroi+2][columHeroi] = matrix[lineHeroi][columHeroi];
        matrix[lineHeroi][columHeroi] = '.';
        lineHeroi=lineHeroi+2;
    }

    public void moveLeft() {
        matrix[lineHeroi][columHeroi-2] = matrix[lineHeroi][columHeroi];
        matrix[lineHeroi][columHeroi] = '.';
        columHeroi=columHeroi-2;
    }

    public void dontMove(){
        matrix[lineHeroi][columHeroi] = matrix[lineHeroi][columHeroi];
        matrix[lineHeroi][columHeroi] = 'H';
    }


    public boolean chegouASaida() {
        if(matrix[lineHeroi][columHeroi-1] == 'S' || matrix[lineHeroi][columHeroi+1] == 'S' || matrix[lineHeroi-1][columHeroi] == 'S' || matrix[lineHeroi+1][columHeroi] == 'S') {
            return true;
        }
        return false;
    }

    //HEURISTICAS
    //numero de quadriculas

    public double computeNumberOfSquares(MummyMazeState finalState) {


        //devolve o numero de peças fora do sitio
        double numberOfSquares = 0;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if(matrix[i][j] != 0) {
                    if (matrix[i][j] != finalState.matrix[i][j]) {
                        numberOfSquares += 1;
                    }
                }
            }
        }

        return numberOfSquares;
    }
    /*
    public double computeTileDistances(MummyMazeState finalState) {


        //sum of manhattan distances between where the tiles are and where they should

        double tilesDistances = 0;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                //diferença absoluta entre a linha onde está e a linha;
                //onde devia estar + a diferença absoluta entre a coluna onde está e a coluna onde devia estar

                //para ir buscar a linha onde está, temos de ir buscar a matriz -> matrix[i][j]
                if(matrix[i][j] != 0) {
                    tilesDistances += Math.abs(i - linesfinalMatrix[matrix[i][j]])
                            + Math.abs(j - colsfinalMatrix[matrix[i][j]]);
                }
            }
        }

        return tilesDistances;
    }*/

    /*public int getNumLines() {
        return matrix.length;
    }

    public int getNumColumns() {
        return matrix[0].length;
    }*/

    public void setMatrix(char[][] matrix){
        this.matrix = matrix;
    }

    public char[][] getMatrix() {
        return matrix;
    }

    public int getLineHeroi() {
        return lineHeroi;
    }

    public int getColumHeroi() {
        return columHeroi;
    }

    public int getTileValue(int line, int column) {
        if (!isValidPosition(line, column)) {
            throw new IndexOutOfBoundsException("Invalid position!");
        }
        return matrix[line][column];
    }

    public boolean isValidPosition(int line, int column) {
        return line >= 0 && line < matrix.length && column >= 0 && column < matrix[0].length;
    }

    @Override
    public boolean equals(Object other) {//devolve verdadeiro se houver igualidade entre 2 objetos(se as suas qualidades são iguais)
        if (!(other instanceof MummyMazeState)) {
            return false;
        }

        MummyMazeState o = (MummyMazeState) other;
        if (matrix.length != o.matrix.length) {
            return false;
        }

        return Arrays.deepEquals(matrix, o.matrix);
    }

    @Override
    public int hashCode() {
        return 97 * 7 + Arrays.deepHashCode(this.matrix);
    } //corresponde ao hash da matriz

    @Override
    public String toString() {
        String turno = "";
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                turno += matrix[i][j];
            }
            turno += "\n";
        }
        return turno;
    }

    @Override
    public Object clone() {
        return new MummyMazeState(matrix);
    }

    //Listeners
    private transient ArrayList<MummyMazeListener> listeners = new ArrayList<MummyMazeListener>(3);

    public synchronized void removeListener(MummyMazeListener l) {
        if (listeners != null && listeners.contains(l)) {
            listeners.remove(l);
        }
    }

    public synchronized void addListener(MummyMazeListener l) {
        if (!listeners.contains(l)) {
            listeners.add(l);
        }
    }

    public void firePuzzleChanged(MummyMazeEvent pe) {
        for (MummyMazeListener listener : listeners) {
            listener.mummyMazeChanged(null);
        }
    }
}
