package MummyMaze;

import agent.Action;
import agent.State;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MummyMazeState extends State implements Cloneable {
    //cleneable é copiar o objeto
    public static final int SIZE = 13; //tamanho da matriz, 13*13
    private char[][] matrix;
    private List<Enemy> enemies;
    private List<Cell> doors;
    private List<Cell> traps;
    private Cell hero;
    private Cell exit;
    private Cell key;

    public MummyMazeState(char[][] matrix) {
        this.matrix = new char[matrix.length][matrix.length];
        enemies = new ArrayList<>();
        doors = new ArrayList<>();
        traps = new ArrayList<>();

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
                    traps.add(new Cell(i, j));
                }
                if (matrix[i][j] == '=' || matrix[i][j] == '_' || matrix[i][j] == ')' || matrix[i][j] == '"') {
                    doors.add(new Cell(i, j));
                }

                if (matrix[i][j] == 'C') {
                    this.key = new Cell(i, j);
                }
            }
        }
    }

    public MummyMazeState(MummyMazeState state) {
        this(state.matrix);

        if (state.hero != null) {
            this.hero = state.hero.clone();
        }
        if (state.exit != null) {
            this.exit = state.exit.clone();
        }
        if (state.key != null) {
            this.key = state.key.clone();
        }

        this.enemies = new ArrayList<>();
        this.doors = new ArrayList<>();
        this.traps = new ArrayList<>();

        for (Cell trap : state.traps) {
            this.traps.add(trap.clone());
        }
        for (Cell door : state.doors) {
            this.doors.add(door.clone());
        }
        for (Enemy enemy : state.enemies) {
            this.enemies.add(enemy.clone());
        }
    }

    @Override
    public void executeAction(Action action) {
        action.execute(this);//método polimórfico - pode executar métodos diferentes consoante a action

        if(key!=null) {
            if (hero.equals(key)) {
                Key();
            }
        }

        firePuzzleChanged(null);
        if (!chegouASaida()) {//no nivel 5 a mumia comia o heroi depois de ele chegar a saida ent pus isto aqui
            for (int i = 0; i < enemies.size(); i++) {
                Enemy enemy = enemies.get(i);
                enemy.move(this, enemy);

                VerifyThings(enemy);

                firePuzzleChanged(null);
            }
        }

        if (traps.size() != 0) {
            for (int i = 0; i < enemies.size(); i++) {
                Enemy enemy = enemies.get(i);

                for (Cell trap : traps) {
                    if (enemy.getCellEnemy().equals(trap)) {
                        matrix[trap.getLine()][trap.getColumn()] = enemy.getSymbol();
                        firePuzzleChanged(null);
                        return;
                    } else {
                        matrix[trap.getLine()][trap.getColumn()] = 'A';
                    }
                }

            }
        }
    }

    public void VerifyThings(Enemy enemy){
        if (enemy.getTipoInimigo() == EnemyType.REDMUMMY) {
            for (int j = 0; j < enemies.size(); j++) {
                Enemy e = enemies.get(j);
                if (enemy != e) {

                    if (e.getTipoInimigo() == EnemyType.WHITEMUMMY) {
                        if (e.getCellEnemy().equals(enemy.getCellEnemy())) {
                            enemies.remove(e);
                            matrix[enemy.getCellEnemy().getLine()][enemy.getCellEnemy().getColumn()] = 'V';
                        }
                    }
                    if (e.getTipoInimigo() == EnemyType.REDMUMMY) {
                        if (e.getCellEnemy().equals(enemy.getCellEnemy())) {
                            enemies.remove(e);
                            matrix[enemy.getCellEnemy().getLine()][enemy.getCellEnemy().getColumn()] = 'V';
                        }
                    }
                    if (e.getTipoInimigo() == EnemyType.SCORPION) {
                        if (e.getCellEnemy().equals(enemy.getCellEnemy())) {
                            enemies.remove(enemy);
                            matrix[e.getCellEnemy().getLine()][e.getCellEnemy().getColumn()] = 'E';
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
                            matrix[enemy.getCellEnemy().getLine()][enemy.getCellEnemy().getColumn()] = 'M';
                        }
                    }
                    if (e.getTipoInimigo() == EnemyType.REDMUMMY) {
                        if (e.getCellEnemy().equals(enemy.getCellEnemy())) {
                            enemies.remove(enemy);
                            matrix[e.getCellEnemy().getLine()][e.getCellEnemy().getColumn()] = 'V';
                        }
                    }
                    if (e.getTipoInimigo() == EnemyType.SCORPION) {
                        if (e.getCellEnemy().equals(enemy.getCellEnemy())) {
                            enemies.remove(enemy);
                            matrix[e.getCellEnemy().getLine()][e.getCellEnemy().getColumn()] = 'E';
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
                            matrix[enemy.getCellEnemy().getLine()][enemy.getCellEnemy().getColumn()] = 'E';
                        }
                    }
                    if (e.getTipoInimigo() == EnemyType.REDMUMMY) {
                        if (e.getCellEnemy().equals(enemy.getCellEnemy())) {
                            enemies.remove(e);
                            matrix[enemy.getCellEnemy().getLine()][enemy.getCellEnemy().getColumn()] = 'E';
                        }
                    }
                    if (e.getTipoInimigo() == EnemyType.SCORPION) {
                        if (e.getCellEnemy().equals(enemy.getCellEnemy())) {
                            enemies.remove(e);
                            matrix[enemy.getCellEnemy().getLine()][enemy.getCellEnemy().getColumn()] = 'E';
                        }
                    }
                }
            }
        }
    }

    public boolean canMoveUp() {//pode mover-se se não tiver parede nem mumia nem nd do genero
        if (hero.getLine() > 1) {
            if (matrix[hero.getLine() - 1][hero.getColumn()] != '-' && matrix[hero.getLine() - 1][hero.getColumn()] != '=') {
                return true;
            }
        }
        return matrix[hero.getLine() - 1][hero.getColumn()] == 'S';
    }

    public boolean canMoveRight() {
        if (hero.getColumn() != matrix.length - 2) {
            if (matrix[hero.getLine()][hero.getColumn() + 1] != '|' && matrix[hero.getLine()][hero.getColumn() + 1] != '"') {
                return true;
            }
        }
        return matrix[hero.getLine()][hero.getColumn() + 1] == 'S';
    }

    public boolean canMoveDown() {
        if (hero.getLine() != matrix.length - 2) {
            if (matrix[hero.getLine() + 1][hero.getColumn()] != '-' && matrix[hero.getLine() + 1][hero.getColumn()] != '=') {
                return true;
            }
        }
        return matrix[hero.getLine() + 1][hero.getColumn()] == 'S';
    }

    public boolean canMoveLeft() {
        if (hero.getColumn() > 1) {
            if (matrix[hero.getLine()][hero.getColumn() - 1] != '|' && matrix[hero.getLine()][hero.getColumn() - 1] != '"') {
                return true;
            }
        }
        return matrix[hero.getLine()][hero.getColumn() - 1] == 'S';
    }

    public boolean cannotMove() {  //acrescentado, quando o heroi não se mexe - mudar
        if (matrix[hero.getLine()][hero.getColumn() - 1] == '|' || matrix[hero.getLine()][hero.getColumn() - 1] == '"' || matrix[hero.getLine()][hero.getColumn() + 1] == '|' || matrix[hero.getLine()][hero.getColumn() + 1] == '"') {
            return true;
        }
        if (matrix[hero.getLine() - 1][hero.getColumn()] == '-' || matrix[hero.getLine() - 1][hero.getColumn()] == '=' || matrix[hero.getLine() + 1][hero.getColumn()] == '-' || matrix[hero.getLine() + 1][hero.getColumn()] == '=') {
            return true;
        }
        return false;
    }

    public boolean isDead() {
        for (Enemy enemy : enemies) {
            if (enemy.getCellEnemy().equals(hero)) {
                return true;
            }
        }
        for (Cell trap : traps) {
            if (trap.equals(hero)) {
                return true;
            }
        }
        return false;
    }

    public void Key() {
        if (doors.size() != 0) {
            for (Cell door : doors) {
                if (matrix[door.getLine()][door.getColumn()] == '_') {
                    matrix[door.getLine()][door.getColumn()] = '=';
                } else if (matrix[door.getLine()][door.getColumn()] == '=') {
                    matrix[door.getLine()][door.getColumn()] = '_';
                } else if (matrix[door.getLine()][door.getColumn()] == '"') {
                    matrix[door.getLine()][door.getColumn()] = ')';
                } else if (matrix[door.getLine()][door.getColumn()] == ')') {
                    matrix[door.getLine()][door.getColumn()] = '"';
                }
            }
        }

    }

    public void moveUp() {
        if (hero.equals(key) && key != null) {
            matrix[hero.getLine()][hero.getColumn()] = 'C';
        } else {
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
        if (hero.equals(key) && key != null) {
            matrix[hero.getLine()][hero.getColumn()] = 'C';
        } else {
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
        if (hero.equals(key) && key != null) {
            matrix[hero.getLine()][hero.getColumn()] = 'C';
        } else {
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
        if (hero.equals(key) && key != null) {
            matrix[hero.getLine()][hero.getColumn()] = 'C';
        } else {
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

    public double computeHeroToExitDistance() {

        double HeroToExitDistance = 0;

        return HeroToExitDistance = Math.abs(hero.getLine() - exit.getLine())
                + Math.abs(hero.getColumn() - exit.getColumn());
    }

    public double computeEnemyDistances() {

        double enemyDistance = 0;

        List<Double> listEnemyDistances = new ArrayList<>();

        double x = -1;

        for (Enemy enemy : enemies) {
            enemyDistance = Math.abs(hero.getLine() - enemy.getCellEnemy().getLine())
                    + Math.abs(hero.getColumn() - enemy.getCellEnemy().getColumn());
            enemyDistance = enemyDistance * x;
            listEnemyDistances.add(enemyDistance);
        }

        double min = Collections.min(listEnemyDistances);

        for (int i = 1; i < listEnemyDistances.size(); i++) {
            if (listEnemyDistances.get(i) < min) {
                min = listEnemyDistances.get(i);
            }
        }

        return min;
    }


    public char[][] getMatrix() {
        return matrix;
    }

    public Cell getCellHero() {
        return hero;
    }

    public Cell getCellKey() {
        return key;
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
        return new MummyMazeState(this);
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
