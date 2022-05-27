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
    private int naoMexeu = 0;
    private List<Enemy> enemies;
    private Cell hero;
    private Cell trap;
    private Cell horizontalDoorClosed;
    private Cell horizontalDoorOpen;
    private Cell verticalDoorClosed;
    private Cell verticalDoorOpen;
    private Cell exit;
    private Cell whiteMummy;
    private Cell whiteMummy2;
    private Cell redMummy;
    private Cell scorpion;
    private Cell key;
    private int conta = 0;


    public MummyMazeState(char[][] matrix) {
        this.matrix = new char[matrix.length][matrix.length];
        enemies = new ArrayList<>();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                this.matrix[i][j] = matrix[i][j];
                if(matrix[i][j] == 'H'){
                    this.hero = new Cell(i,j);
                }
                if(matrix[i][j] == 'S'){
                    this.exit = new Cell(i,j);
                }
                if(matrix[i][j] == 'M'){
                    if(conta == 0) {
                        this.whiteMummy = new Cell(i, j);
                        Enemy enemy = new Enemy(EnemyType.WHITEMUMMY, whiteMummy);
                        enemies.add(enemy);
                        conta = 1;
                    }else{
                        this.whiteMummy2 = new Cell(i, j);
                        Enemy enemy = new Enemy(EnemyType.WHITEMUMMY, whiteMummy2);
                        enemies.add(enemy);
                    }
                }
                if(matrix[i][j] == 'V'){
                    this.redMummy = new Cell(i,j);
                    Enemy enemyMummyRed = new Enemy(EnemyType.REDMUMMY, redMummy);
                    enemies.add(enemyMummyRed);
                }

                if(matrix[i][j] == 'E'){
                    this.scorpion = new Cell(i,j);
                    Enemy enemyScorpion = new Enemy(EnemyType.SCORPION, scorpion);
                    enemies.add(enemyScorpion);
                }

                if(matrix[i][j] == 'A'){
                    this.trap = new Cell(i,j);;
                }

                if(matrix[i][j] == '='){
                    this.horizontalDoorClosed = new Cell(i,j);
                }

                if(matrix[i][j] == '_'){
                    this.horizontalDoorOpen = new Cell(i,j);
                }

                if(matrix[i][j] == '"'){
                    this.verticalDoorOpen = new Cell(i,j);
                }

                if(matrix[i][j] == ')'){
                    this.verticalDoorClosed = new Cell(i,j);
                }
                if(matrix[i][j] == 'C'){
                    this.key = new Cell(i,j);
                }
            }
        }
    }

    @Override
    public void executeAction(Action action) {
        action.execute(this);//método polimórfico - pode executar métodos diferentes consoante a action

        firePuzzleChanged(null);
        if(!chegouASaida()) {//no nivel 5 a mumia comia o heroi depois de ele chegar a saida ent pus isto aqui
            for (Enemy e : enemies) {
                e.move(this);
                firePuzzleChanged(null);
            }
        }

        if(hero.equals(key)){
            Key();
        }
           if (enemies.contains(whiteMummy) && enemies.contains(redMummy) && whiteMummy != null && redMummy != null) {
               if (whiteMummy.equals(redMummy)) {
                   enemies.remove(whiteMummy);
                   whiteMummy = null;//??
               }
           }
        if(enemies.contains(whiteMummy) && enemies.contains(redMummy) && enemies.contains(scorpion) && whiteMummy != null && redMummy != null && scorpion != null) {
            if (redMummy.equals(scorpion)) {
                enemies.remove(redMummy);
                redMummy = null;//??
            }
            if (redMummy.equals(whiteMummy) || scorpion.equals(whiteMummy)) {
                enemies.remove(whiteMummy);
                whiteMummy = null;//??
            }
        }

           if (enemies.contains(whiteMummy) && enemies.contains(scorpion) && whiteMummy != null && scorpion != null) {
               if (whiteMummy.equals(scorpion)) {
                   enemies.remove(whiteMummy);
                   whiteMummy = null;//??
               }
           }
           if(enemies.contains(redMummy) && enemies.contains(scorpion) && redMummy != null && scorpion != null) {
               if (redMummy.equals(scorpion)) {
                   enemies.remove(redMummy);
                   redMummy = null;//??
               }
           }
        if(enemies.contains(whiteMummy) && enemies.contains(whiteMummy2) && whiteMummy != null && whiteMummy2 != null) {
            if (whiteMummy.equals(whiteMummy2)) {
                enemies.remove(whiteMummy2);
                whiteMummy2 = null;//??
            }
        }

            if(enemies.contains(whiteMummy2) && enemies.contains(scorpion) && whiteMummy2 != null && scorpion != null) {
         /*       if(!whiteMummy2.equals(trap) && !scorpion.equals(trap) && trap != null){
                    matrix[trap.getLine()][trap.getColumn()] = 'A';
                }*/
                if (scorpion.equals(whiteMummy2)) {
                    enemies.remove(whiteMummy2);
                    whiteMummy2 = null;//??
                }
            }
            if(key != null) {
                MostrarKey();
            }
        if(trap != null) {
            MostrarTrap();
        }

    }

    public boolean canMoveUp() {//pode mover-se se não tiver parede nem mumia nem nd do genero
        if(!isDead()) {
            if (hero.getLine() > 1) {
                if (matrix[hero.getLine() - 1][hero.getColumn()] != '-' && matrix[hero.getLine()-1][hero.getColumn()] != '=') {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean canMoveRight() {
        if(!isDead()) {
            if (hero.getColumn() != matrix.length - 2) {
                if (matrix[hero.getLine()][hero.getColumn() + 1] != '|' && matrix[hero.getLine()][hero.getColumn()+1] != '"') {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean canMoveDown() {
        if(!isDead() ) {
            if (hero.getLine() != matrix.length - 2) {
                if (matrix[hero.getLine() + 1][hero.getColumn()] != '-' && matrix[hero.getLine()+1][hero.getColumn()] != '=') {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean canMoveLeft() {
        if(!isDead()) {
            if (hero.getColumn() > 1) {
                if (matrix[hero.getLine()][hero.getColumn() - 1] != '|' && matrix[hero.getLine()][hero.getColumn()-1] != '"') {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isDead(){
        if(hero.equals(trap) || hero.equals(whiteMummy) || hero.equals(whiteMummy2)|| hero.equals(redMummy) || hero.equals(scorpion)){
            return true;
        }
        return false;
    }

    public void Key(){
        if(horizontalDoorOpen != null|| horizontalDoorClosed != null)
            if(horizontalDoorClosed.equals("=")){ //se a porta estiver aberta
                horizontalDoorClosed = horizontalDoorOpen; //fecha
            }else{
                horizontalDoorClosed = horizontalDoorOpen; //se estiver fechada, vai abrir
            }
            if(verticalDoorOpen != null|| verticalDoorClosed != null){
                if(verticalDoorClosed.equals(")")){ //porta fechada
                    verticalDoorClosed = verticalDoorOpen;
                }else{
                    verticalDoorOpen = verticalDoorClosed;
                }
            }
    }

    public boolean cannotMove(){  //acrescentado, quando o heroi não se mexe - mudar
        return false;
    }

    public void moveUp() {
        matrix[hero.getLine()][hero.getColumn()] = '.';
        hero.setLine(hero.getLine()-2);
        matrix[hero.getLine()][hero.getColumn()] = 'H';
        naoMexeu = 0;
    }


    public void moveRight() {
        matrix[hero.getLine()][hero.getColumn()] = '.';
        hero.setColumn(hero.getColumn()+2);
        matrix[hero.getLine()][hero.getColumn()] = 'H';
        naoMexeu = 0;
    }


    public void moveDown() {
        matrix[hero.getLine()][hero.getColumn()] = '.';
        hero.setLine(hero.getLine()+2);
        matrix[hero.getLine()][hero.getColumn()] = 'H';
        naoMexeu = 0;
    }

    public void moveLeft() {
        matrix[hero.getLine()][hero.getColumn()] = '.';
        hero.setColumn(hero.getColumn()-2);
        matrix[hero.getLine()][hero.getColumn()] = 'H';
        naoMexeu = 0;
    }

    public void dontMove(){
        matrix[hero.getLine()][hero.getColumn()] = 'H';
        naoMexeu = naoMexeu+1;
    }


    public boolean chegouASaida() {
        if(matrix[hero.getLine()][hero.getColumn()-1] == 'S' || matrix[hero.getLine()][hero.getColumn()+1] == 'S' || matrix[hero.getLine()-1][hero.getColumn()] == 'S' || matrix[hero.getLine()+1][hero.getColumn()] == 'S') {
        //if(hero.equals(exit)){
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

    public void MostrarKey(){
         if(hero != null){
            if(hero.equals(key)) {
                matrix[hero.getLine()][hero.getColumn()] = 'H';
            }
        }
        else if(whiteMummy != null){
            if(whiteMummy.equals(key)) {
                matrix[whiteMummy.getLine()][whiteMummy.getColumn()] = 'M';
                firePuzzleChanged(null);
            }
        }
        else if(whiteMummy2 != null){
            if(whiteMummy2.equals(key)) {
                matrix[whiteMummy2.getLine()][whiteMummy2.getColumn()] = 'M';
                firePuzzleChanged(null);
            }
        }
        else if(redMummy != null){
            if(redMummy.equals(key)) {
                matrix[redMummy.getLine()][redMummy.getColumn()] = 'V';
                firePuzzleChanged(null);
            }
        }
        else if(scorpion != null){
            if(scorpion.equals(key)) {
                matrix[scorpion.getLine()][scorpion.getColumn()] = 'E';
                firePuzzleChanged(null);
            }
        }else{
            matrix[key.getLine()][key.getColumn()] = 'C';
        }
        firePuzzleChanged(null);
    }

    public void MostrarTrap(){
        if(hero != null){
            if(hero.equals(trap)) {
                matrix[hero.getLine()][hero.getColumn()] = 'T';
                firePuzzleChanged(null);
            }
        }
        else if(whiteMummy != null){
            if(whiteMummy.equals(trap)) {
                matrix[whiteMummy.getLine()][whiteMummy.getColumn()] = 'M';
                firePuzzleChanged(null);
            }
        }
        else if(whiteMummy2 != null){
            if(whiteMummy2.equals(trap)) {
                matrix[whiteMummy2.getLine()][whiteMummy2.getColumn()] = 'M';
                firePuzzleChanged(null);
            }
        }
        else if(redMummy != null){
            if(redMummy.equals(trap)) {
                matrix[redMummy.getLine()][redMummy.getColumn()] = 'V';
                firePuzzleChanged(null);
            }
        }
        else if(scorpion != null){
            if(scorpion.equals(trap)) {
                matrix[scorpion.getLine()][scorpion.getColumn()] = 'E';
                firePuzzleChanged(null);
            }
        }else{
            matrix[trap.getLine()][trap.getColumn()] = 'T';
        }
        firePuzzleChanged(null);
    }

    public char[][] getMatrix() {
        return matrix;
    }

    public Cell getCellHero() {
        return hero;
    }

    public int checkMummy(Cell cell){
        if(cell.equals(whiteMummy)){
            return 1;
        }
        if(cell.equals(whiteMummy2)){
            return 2;
        }
        return 0;
    }

    public int getNaoMexeu() {
        return naoMexeu;
    }
    public void setNaoMexeu(int reset){
        this.naoMexeu = reset;
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
