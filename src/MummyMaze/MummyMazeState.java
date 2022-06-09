package MummyMaze;

import agent.Action;
import agent.State;

import java.util.*;

public class MummyMazeState extends State implements Cloneable {
    //definição dos objetos
    public static final int SIZE = 13;
    private char[][] matrix;
    private List<Enemy> enemies;
    private List<Cell> doors;
    private List<Cell> traps;
    private Cell hero;
    private Cell exit;
    private Cell key;

    public MummyMazeState(char[][] matrix) {
        //inicialização dos objetos
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
        //segundo construtor para copiar o estado
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
        //executa as ações
        action.execute(this);

        firePuzzleChanged(null);
        if (!arrivedToExit()) {
            //para cada enemy
            for (int i = 0; i < enemies.size(); i++) {
                Enemy enemy = enemies.get(i);
                //executa o movimento do enemy
                enemy.move(this, enemy);

                //verificar se o enemy está na mesma posição de outro enemy e mata o outro
                verifyOverlapEnemies(enemy);

                //se existirem armadilhas, desenha-as na matriz
                //se o enemy estiver em cima da armadilha, desenha o enemy na matriz
                if (traps.size() != 0) {
                    for (Cell trap : traps) {
                        if (enemy.getCellEnemy().equals(trap)) {
                            matrix[trap.getLine()][trap.getColumn()] = enemy.getSymbol();
                            return;
                        } else {
                            matrix[trap.getLine()][trap.getColumn()] = 'A';
                        }
                    }

                }
            }

            firePuzzleChanged(null);
        }
    }

        //verificar enemies sobrepostos
    public void verifyOverlapEnemies(Enemy enemy) {
        for (int j = 0; j < enemies.size(); j++) {
            Enemy e = enemies.get(j);
            //se o "enemy" não tiver a mesma referência do "e"
            if (enemy != e) {
                //se o "enemy" estiver na mesma posição do "e", remove o "e" (o enemy que foi sobreposto por último)
                if(enemy.getCellEnemy().equals(e.getCellEnemy())) {
                    enemies.remove(e);
                }
            }
        }
    }
    //verifica se pode mover para cima(se não tem paredes e portas verticais/horizontais)
    public boolean canMoveUp() {
        if (hero.getLine() > 1) {
            if (matrix[hero.getLine() - 1][hero.getColumn()] != '-' && matrix[hero.getLine() - 1][hero.getColumn()] != '=') {
                return true;
            }
        }
        return matrix[hero.getLine() - 1][hero.getColumn()] == 'S';
    }
    //verifica se pode mover para a direita(se não tem paredes e portas verticais/horizontais)
    public boolean canMoveRight() {
        if (hero.getColumn() != matrix.length - 2) {
            if (matrix[hero.getLine()][hero.getColumn() + 1] != '|' && matrix[hero.getLine()][hero.getColumn() + 1] != '"') {
                return true;
            }
        }
        return matrix[hero.getLine()][hero.getColumn() + 1] == 'S';
    }
    //verifica se pode mover para baixo(se não tem paredes e portas verticais/horizontais)
    public boolean canMoveDown() {
        if (hero.getLine() != matrix.length - 2) {
            if (matrix[hero.getLine() + 1][hero.getColumn()] != '-' && matrix[hero.getLine() + 1][hero.getColumn()] != '=') {
                return true;
            }
        }
        return matrix[hero.getLine() + 1][hero.getColumn()] == 'S';
    }
    //verifica se pode mover para a esquerda(se não tem paredes e portas verticais/horizontais)
    public boolean canMoveLeft() {
        if (hero.getColumn() > 1) {
            if (matrix[hero.getLine()][hero.getColumn() - 1] != '|' && matrix[hero.getLine()][hero.getColumn() - 1] != '"') {
                return true;
            }
        }
        return matrix[hero.getLine()][hero.getColumn() - 1] == 'S';
    }
    //verifica se não pode mover
    public boolean cannotMove() {
        if(matrix[hero.getLine()-1][hero.getColumn()] != '-' && matrix[hero.getLine()-1][hero.getColumn()] != '=' && matrix[hero.getLine()+1][hero.getColumn()] != '-' && matrix[hero.getLine()+1][hero.getColumn()] != '=' && matrix[hero.getLine()][hero.getColumn()-1] != '|' && matrix[hero.getLine()][hero.getColumn()-1] != '"' && matrix[hero.getLine()][hero.getColumn()+1] != '|' && matrix[hero.getLine()][hero.getColumn()+1] != '"') {
            return false;
        }
        return true;
    }
    //verifica se o heroi está na mesma posição dos enemies e das armadilhas e mata o heroi
    public boolean isHeroDead() {
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
    //altera o estado das portas conforme estejam abertas ou fechadas
    public void Key() {
        if (doors.size() > 0) {
            for (Cell door : doors) {
                if (matrix[door.getLine()][door.getColumn()] == '_') { //se a porta estiver aberta
                    matrix[door.getLine()][door.getColumn()] = '='; //fecha
                } else if (matrix[door.getLine()][door.getColumn()] == '=') { //se a porta estiver fechada
                    matrix[door.getLine()][door.getColumn()] = '_'; //aberta
                } else if (matrix[door.getLine()][door.getColumn()] == '"') { //se a porta estiver fechada
                    matrix[door.getLine()][door.getColumn()] = ')'; //aberta
                } else if (matrix[door.getLine()][door.getColumn()] == ')') { //se a porta estiver aberta
                    matrix[door.getLine()][door.getColumn()] = '"'; //fecha
                }
            }
        }
    }
    //heroi move para cima
    public void moveUp() {
        //se o heroi na ronda anterior estiver na mesma posição da chave, desenha a chave
        //senão desenha uma quadricula vazia
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
        //se o heroi no final do movimento estiver na mesma posição da chave, aciona o método Key() para alterar as portas
        if (key != null && hero.equals(key)) {
            Key();
        }
    }
    //heroi move para direita
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
        if (key != null && hero.equals(key)) {
            Key();
        }
    }

    //heroi move para baixo
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
        if (key != null && hero.equals(key)) {
            Key();
        }
    }
    //heroi move para a esquerda
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
        if (key != null && hero.equals(key)) {
            Key();
        }
    }
    //o heroi permanece na mesma posição
    public void dontMove() {
        matrix[hero.getLine()][hero.getColumn()] = 'H';
    }
    //verifica se a posição do heroi coincide com a saida
    public boolean arrivedToExit() {
        return hero.equals(exit);
    }

    //HEURISTICAS

    public double computeHeroToExitDistance() {

        double HeroToExitDistance = 0;

        return HeroToExitDistance = Math.abs(hero.getLine() - exit.getLine())
                + Math.abs(hero.getColumn() - exit.getColumn());
    }


    public double computeHeroToTrapDistance() {
        if (traps.size() > 0) {
            double trapDistance = 0;

            List<Double> listTrapDistances = new ArrayList<>();

            double x = -1;

            for (Cell trap : traps) {
                trapDistance = Math.abs(hero.getLine() - trap.getLine())
                        + Math.abs(hero.getColumn() - trap.getColumn());
                trapDistance = trapDistance * x;
                listTrapDistances.add(trapDistance);
            }
            double min = Collections.min(listTrapDistances);

            for (int i = 1; i < listTrapDistances.size(); i++) {
                if (listTrapDistances.get(i) < min) {
                    min = listTrapDistances.get(i);
                }
            }

            return min;
        }
        return 0;

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
   //getters - obtém a matriz, o heroi e a chave, usado na classe enemy
    public char[][] getMatrix() {
        return matrix;
    }

    public Cell getCellHero() {
        return hero;
    }

    public Cell getCellKey() {
        return key;
    }


    /*public boolean isValidPosition(int line, int column) {
        return line >= 0 && line < matrix.length && column >= 0 && column < matrix[0].length;
    }*/

    @Override
    //devolve verdadeiro se houver igualidade entre 2 objetos(se as suas qualidades são iguais)
    public boolean equals(Object other) {
        if (!(other instanceof MummyMazeState)) {
            return false;
        }

        MummyMazeState o = (MummyMazeState) other;
        if (matrix.length != o.matrix.length) {
            return false;
        }

        return Arrays.deepEquals(matrix, o.matrix) && hero.equals(o.hero) && enemies.equals(o.enemies);
    }

    @Override
    public int hashCode() {
        int result = Arrays.deepHashCode(matrix) + hero.hashCode() + enemies.hashCode();
        return result;
    }

    @Override
    //transforma o estado numa string
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
