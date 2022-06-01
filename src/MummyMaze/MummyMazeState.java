package MummyMaze;

import agent.Action;
import agent.State;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MummyMazeState extends State implements Cloneable {
    //cleneable é copiar o objeto
    public static final int SIZE = 13; //tamanho da matriz, 13*13
    private char[][] matrix;
    private List<Enemy> enemies;
    private Cell hero;
    private Cell trap;
    private Cell exit;
    private Cell verticalDoor;
    private Cell horizontalDoor;
    private Cell key;
    private int conta = 0;


    public MummyMazeState(char[][] matrix) {
        this.matrix = new char[matrix.length][matrix.length];
        enemies = new ArrayList<>();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                this.matrix[i][j] = matrix[i][j];
                if (matrix[i][j] == 'H') {
                    this.hero = new Cell(i, j);
                }
                if (matrix[i][j] == 'S') {
                    this.exit = new Cell(i, j);
                }
                if (matrix[i][j] == 'M') {
                    Enemy enemy = new Enemy(EnemyType.WHITEMUMMY, new Cell(i, j));
                    enemies.add(enemy);
                }
                if (matrix[i][j] == 'V') {
                    Enemy enemyMummyRed = new Enemy(EnemyType.REDMUMMY, new Cell(i, j));
                    enemies.add(enemyMummyRed);
                }

                if (matrix[i][j] == 'E') {
                    Enemy enemyScorpion = new Enemy(EnemyType.SCORPION, new Cell(i, j));
                    enemies.add(enemyScorpion);
                }

                if (matrix[i][j] == 'A') {
                    this.trap = new Cell(i, j);
                }
                if (matrix[i][j] == '=' || matrix[i][j] == '_') {
                    horizontalDoor = new Cell(i, j);
                }
                if (matrix[i][j] == ')' || matrix[i][j] == '"') {
                    verticalDoor = new Cell(i, j);
                }
                if (matrix[i][j] == 'C') {
                    this.key = new Cell(i, j);
                }
            }
        }
    }

    @Override
    public void executeAction(Action action) {
        action.execute(this);//método polimórfico - pode executar métodos diferentes consoante a action

        if (hero.equals(key)) {
            Key();
        }

        firePuzzleChanged(null);
        if (!chegouASaida()) {//no nivel 5 a mumia comia o heroi depois de ele chegar a saida ent pus isto aqui
            for (Enemy e : enemies) {
                e.move(this);
                firePuzzleChanged(null);
            }
        }

        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            if (enemy.getTipoInimigo() == EnemyType.REDMUMMY) {
                for (int j = 0; j < enemies.size(); j++) {
                    Enemy e = enemies.get(j);
                    if (enemy != e) {
                        if (e.getTipoInimigo() == EnemyType.WHITEMUMMY) {
                            if (e.getCellEnemy().equals(enemy.getCellEnemy())) {
                                enemies.remove(e);
                            }
                        }
                        if (e.getTipoInimigo() == EnemyType.REDMUMMY) {
                            if (e.getCellEnemy().equals(enemy.getCellEnemy())) {
                                enemies.remove(e);
                            }
                        }
                        if (e.getTipoInimigo() == EnemyType.SCORPION) {
                            if (e.getCellEnemy().equals(enemy.getCellEnemy())) {
                                enemies.remove(e);
                            }
                        }
                        if (e.getCellEnemy() == getCellHero()) {
                            if (e.getCellEnemy().equals(enemy.getCellEnemy())) {
                                enemies.remove(e);
                            }
                        }
                    }
                }
            }
            if (enemy.getTipoInimigo() == EnemyType.WHITEMUMMY) {
                for (int j = 0; j < enemies.size(); j++) {
                    Enemy e = enemies.get(j);

                    if (enemy != e) {
                        if (e.getTipoInimigo() == EnemyType.WHITEMUMMY) {
                            if (e.getCellEnemy().equals(enemy.getCellEnemy())) {
                                enemies.remove(e);
                            }
                        }
                        if (e.getTipoInimigo() == EnemyType.REDMUMMY) {
                            if (e.getCellEnemy().equals(enemy.getCellEnemy())) {
                                enemies.remove(enemy);
                            }
                        }
                        if (e.getTipoInimigo() == EnemyType.SCORPION) {
                            if (e.getCellEnemy().equals(enemy.getCellEnemy())) {
                                enemies.remove(enemy);
                            }
                        }
                    }
                }

            }
            if (enemy.getTipoInimigo() == EnemyType.SCORPION) {
                for (int j = 0; j < enemies.size(); j++) {
                    Enemy e = enemies.get(j);
                    if (enemy != e) {
                        if (e.getTipoInimigo() == EnemyType.WHITEMUMMY) {
                            if (e.getCellEnemy().equals(enemy.getCellEnemy())) {
                                enemies.remove(e);
                            }
                        }
                        if (e.getTipoInimigo() == EnemyType.REDMUMMY) {
                            if (e.getCellEnemy().equals(enemy.getCellEnemy())) {
                                enemies.remove(e);
                            }
                        }
                        if (e.getTipoInimigo() == EnemyType.SCORPION) {
                            if (e.getCellEnemy().equals(enemy.getCellEnemy())) {
                                enemies.remove(enemy);
                            }
                        }
                    }
                }
            }
            if (trap != null) {
                if (!enemy.getCellEnemy().equals(trap)) {
                    matrix[trap.getLine()][trap.getColumn()] = 'A';
                    firePuzzleChanged(null);
                }
            }
        }

        /*if (key != null) {
            MostrarKey();
            firePuzzleChanged(null);
        }
        if (trap != null && !isDead()) {
            MostrarTrap();
            firePuzzleChanged(null);
        }*/

    }

    public boolean canMoveUp() {//pode mover-se se não tiver parede nem mumia nem nd do genero
        if (hero.getLine() > 1) {
            if (matrix[hero.getLine() - 1][hero.getColumn()] != '-' && matrix[hero.getLine() - 1][hero.getColumn()] != '=') {
                return true;
            }
            return false;
        }
        return matrix[hero.getLine() - 1][hero.getColumn()] == 'S';
    }

    public boolean canMoveRight() {
        if (hero.getColumn() != matrix.length - 2) {
            if (matrix[hero.getLine()][hero.getColumn() + 1] != '|' && matrix[hero.getLine()][hero.getColumn() + 1] != '"') {
                return true;
            }
            return false;
        }
        return matrix[hero.getLine()][hero.getColumn() + 1] == 'S';
    }

    public boolean canMoveDown() {
        if (hero.getLine() != matrix.length - 2) {
            if (matrix[hero.getLine() + 1][hero.getColumn()] != '-' && matrix[hero.getLine() + 1][hero.getColumn()] != '=') {
                return true;
            }
            return false;
        }
        return matrix[hero.getLine() + 1][hero.getColumn()] == 'S';
    }

    public boolean canMoveLeft() {
        if (hero.getColumn() > 1) {
            if (matrix[hero.getLine()][hero.getColumn() - 1] != '|' && matrix[hero.getLine()][hero.getColumn() - 1] != '"') {
                return true;
            }
            return false;
        }
        return matrix[hero.getLine()][hero.getColumn() - 1] == 'S';
    }

    public boolean isDead() {
        for (Enemy enemy : enemies) {
            if (enemy.getCellEnemy().equals(hero)) {
                return true;
            }

        }
        if(hero.equals(trap)){
            return true;
        }
        return false;
    }

    public void Key() {
        if (horizontalDoor != null) {
            if (matrix[horizontalDoor.getLine()][horizontalDoor.getColumn()] == '_') { //se a porta estiver aberta
                matrix[horizontalDoor.getLine()][horizontalDoor.getColumn()] = '=';
            } else {
                matrix[horizontalDoor.getLine()][horizontalDoor.getColumn()] = '_';//se estiver fechada, vai abrir
            }
        }
        if (verticalDoor != null) {
            if (matrix[verticalDoor.getLine()][verticalDoor.getColumn()] == '"') { //porta fechada
                matrix[verticalDoor.getLine()][verticalDoor.getColumn()] = ')';
            } else {
                matrix[verticalDoor.getLine()][verticalDoor.getColumn()] = '"';
            }
        }
    }

    public boolean cannotMove() {  //acrescentado, quando o heroi não se mexe - mudar
        return false;
    }

    public void moveUp() {
        if(hero.equals(key) && key != null){
            matrix[hero.getLine()][hero.getColumn()] = 'C';
        }else {
            matrix[hero.getLine()][hero.getColumn()] = '.';
        }
        if (hero.getLine() == 1) {
            hero.setLine(hero.getLine() - 1);
        } else {
            hero.setLine(hero.getLine() - 2);
        }
        matrix[hero.getLine()][hero.getColumn()] = 'H';
    }


    public void moveRight() {
        if(hero.equals(key) && key != null){
            matrix[hero.getLine()][hero.getColumn()] = 'C';
        }else {
            matrix[hero.getLine()][hero.getColumn()] = '.';
        }

        if (hero.getColumn() == matrix.length - 2) {
            hero.setColumn(hero.getColumn() + 1);
        } else {
            hero.setColumn(hero.getColumn() + 2);
        }
        matrix[hero.getLine()][hero.getColumn()] = 'H';
    }


    public void moveDown() {
        if(hero.equals(key) && key != null){
            matrix[hero.getLine()][hero.getColumn()] = 'C';
        }else {
            matrix[hero.getLine()][hero.getColumn()] = '.';
        }
        if (hero.getLine() == matrix.length - 2) {
            hero.setLine(hero.getLine() + 1);
        } else {
            hero.setLine(hero.getLine() + 2);
        }
        matrix[hero.getLine()][hero.getColumn()] = 'H';
    }

    public void moveLeft() {
        if(hero.equals(key) && key != null){
            matrix[hero.getLine()][hero.getColumn()] = 'C';
        }else {
            matrix[hero.getLine()][hero.getColumn()] = '.';
        }
        if (hero.getColumn() == 1) {
            hero.setColumn(hero.getColumn() - 1);
        } else {
            hero.setColumn(hero.getColumn() - 2);
        }
        matrix[hero.getLine()][hero.getColumn()] = 'H';
    }

    public void dontMove() {
        matrix[hero.getLine()][hero.getColumn()] = 'H';
    }


    public boolean chegouASaida() {
        return hero.equals(exit);
    }


    //HEURISTICAS

    public double computeTileDistances() {

        double tilesDistances = 0;

        return tilesDistances = Math.abs(hero.getLine() - exit.getLine())
                + Math.abs(hero.getColumn() - exit.getColumn());
    }

    /*public void MostrarKey() {
        if (hero != null) {
            if (hero.equals(key)) {
                matrix[hero.getLine()][hero.getColumn()] = 'H';
                return;
            }
        }
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            if (enemy.equals(key)) {
                if (enemy.getTipoInimigo() == EnemyType.REDMUMMY) {
                    matrix[enemy.getCellEnemy().getLine()][enemy.getCellEnemy().getColumn()] = 'V';
                    return;
                }
                if (enemy.getTipoInimigo() == EnemyType.WHITEMUMMY) {
                    matrix[enemy.getCellEnemy().getLine()][enemy.getCellEnemy().getColumn()] = 'M';
                    return;
                }
                if (enemy.getTipoInimigo() == EnemyType.SCORPION) {
                    matrix[enemy.getCellEnemy().getLine()][enemy.getCellEnemy().getColumn()] = 'E';
                    return;
                }
            }
            matrix[key.getLine()][key.getColumn()] = 'C';
        }
    }*/

    public char[][] getMatrix() {
        return matrix;
    }

    public Cell getCellHero() {
        return hero;
    }

    /*public int getTileValue(int line, int column) {
        if (!isValidPosition(line, column)) {
            throw new IndexOutOfBoundsException("Invalid position!");
        }
        return matrix[line][column];
    }*/

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
