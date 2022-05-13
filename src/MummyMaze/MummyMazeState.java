package MummyMaze;

import agent.Action;
import agent.State;

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
    private int lineHeroi;
    private int columHeroi;
    private int lineArmadilha;
    private int columnArmadilha;
    private int linePortaHorizontal;
    private int columnPortaHorizontal;
    private int linePortaVertical;
    private int columnPortaVertical;
    private int naoMexeu = 0;
    private List<Enemy> enemies;
    //private List<Elements> elements;
    private List<Cell> elementsSobrepostos;
    private Cell hero;
    private Cell trap;
    private Cell horizontalDoor;
    private Cell verticalDoor;
    private Cell exit;
    private Cell whiteMummy;
    private Cell redMummy;
    private Cell scorpion;
    private Cell key;

    //criar classe cell, agente reativo, metodo equals
    //ter lista de elementos moveis, mumias, escorpioes, chaves, portas, armadilha, hierarquia de classes elementos?, subclasse de elementos moveis

    public MummyMazeState(char[][] matrix) {
        this.matrix = new char[matrix.length][matrix.length];
        enemies = new ArrayList<>();
        elementsSobrepostos = new ArrayList<>();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                this.matrix[i][j] = matrix[i][j];
                if(matrix[i][j] == 'H'){
                    this.hero = new Cell(i,j);
                    lineHeroi = i;
                    columHeroi =j;

                    elementsSobrepostos.add(hero);
                }
                if(matrix[i][j] == 'S'){
                    this.exit = new Cell(i,j);
                }
                if(matrix[i][j] == 'M'){
                    this.whiteMummy = new Cell(i, j);
                    Enemy enemy = new Enemy(EnemyType.WHITEMUMMY, whiteMummy);
                    enemies.add(enemy);

                    /*Elements element = new Elements(whiteMummy);
                    elements.add(element);*/
                }
                if(matrix[i][j] == 'V'){
                    this.redMummy = new Cell(i,j);
                    Enemy enemyMummyRed = new Enemy(EnemyType.REDMUMMY, redMummy);
                    enemies.add(enemyMummyRed);

                    /*Elements element = new Elements(ElementType.REDMUMMY);
                    elements.add(element);*/
                }

                if(matrix[i][j] == 'E'){
                    this.scorpion = new Cell(i,j);
                    Enemy enemyScorpion = new Enemy(EnemyType.SCORPION, scorpion);
                    enemies.add(enemyScorpion);

                    /*Elements element = new Elements(ElementType.SCORPION);
                    elements.add(element);*/
                }

                if(matrix[i][j] == 'A'){
                    this.trap = new Cell(i,j);;
                    lineArmadilha = i;
                    columnArmadilha =j;
                    /*Elements element = new Elements(ElementType.TRAP);
                    elements.add(element);*/
                }

                if(matrix[i][j] == '=' || matrix[i][j] == '_'){
                    this.horizontalDoor = new Cell(i,j);
                    linePortaHorizontal = i;
                    columnPortaHorizontal =j;

                    /*Elements element = new Elements(ElementType.HORIZONTALDOOR);
                    elements.add(element);*/
                }

                if(matrix[i][j] == '"' || matrix[i][j] == ')'){
                    this.verticalDoor = new Cell(i,j);
                    linePortaVertical = i;
                    columnPortaVertical =j;

                    /*Elements element = new Elements(ElementType.VERTICALDOOR);
                    elements.add(element);*/
                }
                if(matrix[i][j] == 'C'){
                    this.key = new Cell(i,j);

                    /*Elements element = new Elements(ElementType.KEY);
                    elements.add(element);*/
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
            }
            firePuzzleChanged(null);//atualizar a interface gráfica
        }
    }

    public boolean canMoveUp() {//pode mover-se se não tiver parede nem mumia nem nd do genero
        if(!isDead()) {
            HasKey();
            if (lineHeroi > 1) {
                if (matrix[lineHeroi - 1][columHeroi] != '-' && matrix[lineHeroi-1][columHeroi] != '=') {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean canMoveRight() {
        if(!isDead()) {
            HasKey();
            if (columHeroi != matrix.length - 2) {
                if (matrix[lineHeroi][columHeroi + 1] != '|' && matrix[lineHeroi][columHeroi+1] != '"') {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean canMoveDown() {
        if(!isDead() ) {
            HasKey();
            if (lineHeroi != matrix.length - 2) {
                if (matrix[lineHeroi + 1][columHeroi] != '-' && matrix[lineHeroi+1][columHeroi] != '=') {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean canMoveLeft() {
        if(!isDead()) {
            HasKey();
            if (columHeroi > 1) {
                if (matrix[lineHeroi][columHeroi - 1] != '|' && matrix[lineHeroi][columHeroi-1] != '"') {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isDead(){

        if(hero.equals(trap) || hero.equals(whiteMummy) || hero.equals(redMummy) || hero.equals(scorpion)){
            return true;
        }
        return false;
    }

    public void HasKey(){
        if(hero.equals(key)){
            elementsSobrepostos.add(key);
            if(matrix[linePortaHorizontal][columnPortaHorizontal] == '_'){ //se a porta estiver aberta
                matrix[linePortaHorizontal][columnPortaHorizontal] = '='; //fecha
            }else{
                matrix[linePortaHorizontal][columnPortaHorizontal] = '_'; //se estiver fechada, vai abrir
            }
            if(matrix[linePortaVertical][columnPortaVertical] == ')'){
                matrix[linePortaVertical][columnPortaVertical] = '"';
            }else{
                matrix[linePortaVertical][columnPortaVertical] = ')';
            }
        }
    }

    public boolean cannotMove(){  //acrescentado, quando o heroi não se mexe - mudar
        return false;
    }

    public void moveUp() { //para cima
        matrix[lineHeroi - 2][columHeroi] = matrix[lineHeroi][columHeroi];
        matrix[lineHeroi][columHeroi] = '.';
        lineHeroi = lineHeroi -2;

        this.hero = setValueAt(hero, lineHeroi, columHeroi);
    }


    public void moveRight() {
        matrix[lineHeroi][columHeroi + 2] = matrix[lineHeroi][columHeroi];
        matrix[lineHeroi][columHeroi] = '.';
        columHeroi = columHeroi+2;

        this.hero = setValueAt(hero, lineHeroi, columHeroi);
    }


    public void moveDown() {
        matrix[lineHeroi + 2][columHeroi] = matrix[lineHeroi][columHeroi];
        matrix[lineHeroi][columHeroi] = '.';
        lineHeroi=lineHeroi+2;

        this.hero = setValueAt(hero, lineHeroi, columHeroi);
    }

    public void moveLeft() {
        matrix[lineHeroi][columHeroi - 2] = matrix[lineHeroi][columHeroi];
        matrix[lineHeroi][columHeroi] = '.';
        columHeroi=columHeroi-2;

        this.hero = setValueAt(hero, lineHeroi, columHeroi);
    }

    public void dontMove(){
        matrix[lineHeroi][columHeroi] = matrix[lineHeroi][columHeroi];
        matrix[lineHeroi][columHeroi] = 'H';
        naoMexeu = naoMexeu+1;

        this.hero = setValueAt(hero, lineHeroi, columHeroi);
    }


    public boolean chegouASaida() {
        if(matrix[lineHeroi][columHeroi-1] == 'S' || matrix[lineHeroi][columHeroi+1] == 'S' || matrix[lineHeroi-1][columHeroi] == 'S' || matrix[lineHeroi+1][columHeroi] == 'S') {
        //if(hero.equals(exit)){
            elementsSobrepostos.add(exit);
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

    public char[][] getMatrix() {
        return matrix;
    }

    public int getLineHeroi() {
        return lineHeroi;
    }

    public Cell getCellHeroi() {
        return hero;
    }

    public int getColumHeroi() {
        return columHeroi;
    }
    public int getNaoMexeu() {
        return naoMexeu;
    }
    public void setNaoMexeu(int reset){
        this.naoMexeu = reset;
    }

    public Cell setValueAt(Cell cell, int line, int column){
        cell = new Cell(line, column);
        return cell;
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
