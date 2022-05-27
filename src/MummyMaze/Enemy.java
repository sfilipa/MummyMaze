package MummyMaze;

import static MummyMaze.EnemyType.REDMUMMY;
import static MummyMaze.EnemyType.SCORPION;

public class Enemy {
    private EnemyType WHITEMUMMY = EnemyType.WHITEMUMMY;
    //private EnemyType mumiaBranca = EnemyType.WHITEMUMMY;
    private EnemyType mumiaVermelha = REDMUMMY;

    private Cell enemy;
    private Cell hero;

    private EnemyType tipoInimigo;
    private char[][] matrix;

    int mumiaparada=0;
    int mumiaparada2=0;
    int scorpionparado=0;
    int redMummyparado=0;
    int check=0;

    public Enemy(EnemyType tipoInimigo, Cell enemy) {
        this.enemy = enemy;
        this.tipoInimigo = tipoInimigo;
    }

    public void move(MummyMazeState mummyMazeState) {
        hero = mummyMazeState.getCellHero();
        matrix = mummyMazeState.getMatrix();
        check = mummyMazeState.checkMummy(enemy);

        if(tipoInimigo == WHITEMUMMY || tipoInimigo == REDMUMMY){ //2 movimentos
            if(mummyMazeState.getNaoMexeu() >= 2){//PERGUNTAR AO STOR
                //mummyMazeState.firePuzzleChanged(null);
                movimentosInimigos();
                mummyMazeState.firePuzzleChanged(null);
                //mummyMazeState.setValueAt(enemy, lineEnemy, columnEnemy);
                mummyMazeState.setNaoMexeu(0);
            }else {
                //mummyMazeState.firePuzzleChanged(null);
                movimentosInimigos();
                //mummyMazeState.setValueAt(enemy, lineEnemy, columnEnemy);
                mummyMazeState.firePuzzleChanged(null);
                movimentosInimigos();
            }
        }
        if(tipoInimigo == SCORPION){ //apenas 1 movimento
            //mummyMazeState.firePuzzleChanged(null);
            movimentosInimigos();
        }
    }

    public void movimentosInimigos(){
        switch (canMove()){//apenas faz os movimentos
            case "morreu":
                if(!hero.equals(enemy)) {

                    matrix[hero.getLine()][hero.getColumn()] = matrix[enemy.getLine()][enemy.getColumn()];

                    matrix[enemy.getLine()][enemy.getColumn()] = '.';
                    enemy.setLine(hero.getLine());
                    enemy.setColumn(hero.getColumn());
                    if(tipoInimigo == WHITEMUMMY && check == 1) {
                        mumiaparada = 0;
                    }else if(tipoInimigo == WHITEMUMMY && check == 2){
                        mumiaparada2 =0;
                    }else if(tipoInimigo == SCORPION){
                        scorpionparado =0;
                    }else if(tipoInimigo == REDMUMMY){
                        redMummyparado =0;
                    }
                }
                break;
            case "cima":
                matrix[enemy.getLine() - 2][enemy.getColumn()] = matrix[enemy.getLine()][enemy.getColumn()];
                matrix[enemy.getLine()][enemy.getColumn()] = '.';
                enemy.setLine(enemy.getLine()-2);
                if(tipoInimigo == WHITEMUMMY && check == 1) {
                    mumiaparada = 0;
                }else if(tipoInimigo == WHITEMUMMY && check == 2){
                        mumiaparada2 =0;
                }else if(tipoInimigo == SCORPION){
                    scorpionparado =0;
                }else if(tipoInimigo == REDMUMMY){
                    redMummyparado =0;
                }
                break;
            case "baixo":
                matrix[enemy.getLine() + 2][enemy.getColumn()] = matrix[enemy.getLine()][enemy.getColumn()];
                matrix[enemy.getLine()][enemy.getColumn()] = '.';
                enemy.setLine(enemy.getLine()+2);
                if(tipoInimigo == WHITEMUMMY && check == 1) {
                    mumiaparada = 0;
                }else if(tipoInimigo == WHITEMUMMY && check == 2){
                    mumiaparada2 =0;
                }else if(tipoInimigo == SCORPION){
                    scorpionparado =0;
                }else if(tipoInimigo == REDMUMMY){
                    redMummyparado =0;
                }
                break;
            case "esq":
                matrix[enemy.getLine()][enemy.getColumn() -2] = matrix[enemy.getLine()][enemy.getColumn()];
                matrix[enemy.getLine()][enemy.getColumn()] = '.';
                enemy.setColumn(enemy.getColumn()-2);
                if(tipoInimigo == WHITEMUMMY && check == 1) {
                    mumiaparada = 0;
                }else if(tipoInimigo == WHITEMUMMY && check == 2){
                    mumiaparada2 =0;
                }else if(tipoInimigo == SCORPION){
                    scorpionparado =0;
                }else if(tipoInimigo == REDMUMMY){
                    redMummyparado =0;
                }
                break;
            case "dir":
                matrix[enemy.getLine()][enemy.getColumn() + 2] = matrix[enemy.getLine()][enemy.getColumn()];
                matrix[enemy.getLine()][enemy.getColumn()] = '.';
                enemy.setColumn(enemy.getColumn()+2);
                if(tipoInimigo == WHITEMUMMY && check == 1) {
                    mumiaparada = 0;
                }else if(tipoInimigo == WHITEMUMMY && check == 2){
                    mumiaparada2 =0;
                }else if(tipoInimigo == SCORPION){
                    scorpionparado =0;
                }else if(tipoInimigo == REDMUMMY){
                    redMummyparado =0;
                }
                break;
            case "nao":
                matrix[enemy.getLine()][enemy.getColumn()] = matrix[enemy.getLine()][enemy.getColumn()];
                //matrix[lineEnemy][columnEnemy] = matrix[lineEnemy][columnEnemy];
                if(tipoInimigo == WHITEMUMMY && check == 1) {
                    mumiaparada = 1;
                }else if(tipoInimigo == WHITEMUMMY && check == 2){
                    mumiaparada2 =1;
                }else if(tipoInimigo == SCORPION){
                    scorpionparado =1;
                }else if(tipoInimigo == REDMUMMY){
                    redMummyparado =1;
                }
                break;

        }
    }

    private String canMove() { //mas este não recebe o state
        switch (tipoInimigo) {
            case WHITEMUMMY:
                if (enemy.isInSameColumn(hero) || (mumiaparada == 1 && check ==1) || (mumiaparada2 == 1 && check ==2)) {//se a coluna do heroi for a mesma que a da mumia
                    if (enemy.getLine() > hero.getLine()) {//se a linha da mumia tiver a baixo da do heroi, move-se para cima
                        if (enemy.getLine() > 2) {
                            if (matrix[enemy.getLine() - 1][enemy.getColumn()] != '-' && matrix[enemy.getLine() - 1][enemy.getColumn()] != '=') {
                                return "cima";
                            }
                        }
                    } else if(enemy.getLine() < hero.getLine()){//se a linha da mumia tiver a cima da do heroi, move-se para baixo
                        if (enemy.getLine() < 10) {
                            if (matrix[enemy.getLine() + 1][enemy.getColumn()] != '-' && matrix[enemy.getLine() + 1][enemy.getColumn()] != '=') {
                                return "baixo";
                            }
                        }
                    }else{
                        if (enemy.isInSameLine(hero) && enemy.isInSameColumn(hero)) {//Ao chegar aqui, ja apanhou o heroi, pq esta na mesma linha e coluna dele
                            return "morreu";
                        }
                    }
                } else if(enemy.getColumn() > hero.getColumn()){//se a coluna do heroi não for a mesma que a da mumia
                    //Se a mumia tiver á direita do heroi, tem de se mover para a esquerda
                    if (enemy.getColumn() > 2) {
                        if (matrix[enemy.getLine()][enemy.getColumn() - 1] != '|' && matrix[enemy.getLine()][enemy.getColumn() - 1] != ')') {
                            return "esq";
                        }
                    }
                } else {
                    if (enemy.getColumn() < 10) {//Se a mumia tiver á esquerda do heroi, tem de se mover para a direita
                        if (matrix[enemy.getLine()][enemy.getColumn() + 1] != '|' && matrix[enemy.getLine()][enemy.getColumn() + 1] != ')') {
                            return "dir";
                        }
                    }
                }
                break;
            case REDMUMMY:
                if (enemy.isInSameLine(hero)|| redMummyparado==1) {//se a coluna do heroi for a mesma que a da mumia
                    if (enemy.getColumn() > hero.getColumn()) {//se a linha da mumia tiver a baixo da do heroi, move-se para cima
                        if (enemy.getColumn() > 1) {
                            if (matrix[enemy.getLine()][enemy.getColumn() - 1] != '|' && matrix[enemy.getLine()][enemy.getColumn() - 1] != ')') {
                                return "esq";
                            }
                        }
                    } else if(enemy.getColumn() < hero.getColumn()){//se a mumia tiver
                        if (enemy.getColumn() != matrix.length - 2) {
                            if (matrix[enemy.getLine()][enemy.getColumn() + 1] != '|' && matrix[enemy.getLine()][enemy.getColumn() - 1] != ')') {
                                return "dir";
                            }
                        }
                    }else {
                        if (enemy.isInSameColumn(hero)) {//Ao chegar aqui, ja apanhou o heroi, pq esta na mesma linha e coluna dele
                            return "morreu";
                        }
                    }
                } else if (enemy.getLine() > hero.getLine()) {//Se a mumia tiver á direita do heroi, tem de se mover para a esquerda
                        if (enemy.getLine() > 1) {
                            if (matrix[enemy.getLine() - 1][enemy.getColumn()] != '-' && matrix[enemy.getLine() - 1][enemy.getColumn()] != '=') {
                                return "cima";
                            }
                        }
                    } else {
                        if (enemy.getColumn() != matrix.length - 2) {//Se a mumia tiver á esquerda do heroi, tem de se mover para a direita
                            if (matrix[enemy.getLine() +1][enemy.getColumn()] != '-' && matrix[enemy.getLine() +1][enemy.getColumn()] != '=') {
                                return "baixo";
                            }
                        }
                    }
                break;
            case SCORPION:
                if (enemy.isInSameColumn(hero) || scorpionparado==1) {//se a coluna do heroi for a mesma que a da mumia
                    if (enemy.getLine() > hero.getLine()) {//se a linha da mumia tiver a baixo da do heroi, move-se para cima
                        if (enemy.getLine() > 2) {
                            if (matrix[enemy.getLine() - 1][enemy.getColumn()] != '-' && matrix[enemy.getLine() - 1][enemy.getColumn()] != '=') {
                                return "cima";
                            }
                        }
                    } else if(enemy.getLine() < hero.getLine()){//se a linha da mumia tiver a cima da do heroi, move-se para baixo
                        if (enemy.getLine() < 10) {
                            if (matrix[enemy.getLine() + 1][enemy.getColumn()] != '-' && matrix[enemy.getLine() + 1][enemy.getColumn()] != '=') {
                                return "baixo";
                            }
                        }
                    }else{
                        if (enemy.isInSameLine(hero) && enemy.isInSameColumn(hero)) {//Ao chegar aqui, ja apanhou o heroi, pq esta na mesma linha e coluna dele
                            return "morreu";
                        }
                    }
                } else if(enemy.getColumn() > hero.getColumn()){//se a coluna do heroi não for a mesma que a da mumia
                    //Se a mumia tiver á direita do heroi, tem de se mover para a esquerda
                    if (enemy.getColumn() > 2) {
                        if (matrix[enemy.getLine()][enemy.getColumn() - 1] != '|' && matrix[enemy.getLine()][enemy.getColumn() - 1] != ')') {
                            return "esq";
                        }
                    }
                } else {
                    if (enemy.getColumn() < 10) {//Se a mumia tiver á esquerda do heroi, tem de se mover para a direita
                        if (matrix[enemy.getLine()][enemy.getColumn() + 1] != '|' && matrix[enemy.getLine()][enemy.getColumn() + 1] != ')') {
                            return "dir";
                        }
                    }
                }
                break;
        }
        return "nao"; //caso ninguém se possa mover
    }



}
