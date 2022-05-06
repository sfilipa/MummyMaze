package MummyMaze;

import static MummyMaze.EnemyType.REDMUMMY;
import static MummyMaze.EnemyType.SCORPION;

public class Enemy {
    private static final EnemyType WHITEMUMMY = EnemyType.WHITEMUMMY;
    private EnemyType mumiaBranca = EnemyType.WHITEMUMMY;
    private EnemyType mumiaVermelha = REDMUMMY;


    private int lineHeroi;
    private int columnHeroi;

    private int lineEnemy;
    private int columnEnemy;
    private EnemyType tipoInimigo;
    private char[][] matrix;

    public Enemy(EnemyType tipoInimigo, int lineEnemy, int columnEnemy) {
        this.lineEnemy = lineEnemy;
        this.columnEnemy = columnEnemy;
        this.tipoInimigo = tipoInimigo;
    }

    public void move(MummyMazeState mummyMazeState) {
        this.lineHeroi = mummyMazeState.getLineHeroi();
        this.columnHeroi = mummyMazeState.getColumHeroi();
        this.matrix = mummyMazeState.getMatrix();

        if(tipoInimigo == WHITEMUMMY || tipoInimigo == REDMUMMY){ //2 movimentos
            if(mummyMazeState.getNaoMexeu() == 2){//PERGUNTAR AO STOR
                movimentosInimigos();
                mummyMazeState.setNaoMexeu(0);
            }else {
                movimentosInimigos();
                mummyMazeState.firePuzzleChanged(null);
                movimentosInimigos();
            }
        }
        if(tipoInimigo == SCORPION){ //apenas 1 movimento
            movimentosInimigos();
        }
    }

    public void movimentosInimigos(){
        switch (canMove()){//apenas faz os movimentos
            case "morreu":
                //matrix[lineHeroi][columnHeroi] = matrix[lineEnemy][columnEnemy];
                if(tipoInimigo == WHITEMUMMY){
                    matrix[lineEnemy][columnEnemy] = 'M';
                }
                if(tipoInimigo == REDMUMMY){
                    matrix[lineEnemy][columnEnemy] = 'V';
                }
                if(tipoInimigo == SCORPION){
                    matrix[lineEnemy][columnEnemy] = 'E';
                }
                matrix[lineEnemy][columnEnemy] = '.';

                break;
            case "cima":
                matrix[lineEnemy - 2][columnEnemy] = matrix[lineEnemy][columnEnemy];
                matrix[lineEnemy][columnEnemy] = '.';
                lineEnemy = lineEnemy -2;
                break;
            case "baixo":
                matrix[lineEnemy + 2][columnEnemy] = matrix[lineEnemy][columnEnemy];
                matrix[lineEnemy][columnEnemy] = '.';
                lineEnemy = lineEnemy + 2;
                break;
            case "esq":
                matrix[lineEnemy][columnEnemy -2] = matrix[lineEnemy][columnEnemy];
                matrix[lineEnemy][columnEnemy] = '.';
                columnEnemy = columnEnemy - 2;
                break;
            case "dir":
                matrix[lineEnemy][columnEnemy + 2] = matrix[lineEnemy][columnEnemy];
                matrix[lineEnemy][columnEnemy] = '.';
                columnEnemy = columnEnemy + 2;
                break;
            case "nao":
                matrix[lineEnemy][columnEnemy] = matrix[lineEnemy][columnEnemy];
                break;

        }
    }

    private String canMove() { //mas este não recebe o state
        switch (tipoInimigo) {
            case WHITEMUMMY:
                    if (columnHeroi == columnEnemy) {//se a coluna do heroi for a mesma que a da mumia
                        if (lineEnemy > lineHeroi) {//se a linha da mumia tiver a baixo da do heroi, move-se para cima
                            if (lineEnemy > 1) {
                                if (matrix[lineEnemy - 1][columnEnemy] != '-' && matrix[lineEnemy-2][columnEnemy] != 'M' && matrix[lineEnemy-2][columnEnemy] != 'V' && matrix[lineEnemy-2][columnEnemy] != 'E') {
                                    return "cima";
                                }
                            }
                        } else {//se a linha da mumia tiver a cima da do heroi, move-se para baixo
                            if (lineEnemy != matrix.length - 2) {
                                if (matrix[lineEnemy + 1][columnEnemy] != '-' && matrix[lineEnemy+2][columnEnemy] != 'M' && matrix[lineEnemy+2][columnEnemy] != 'E' && matrix[lineEnemy+2][columnEnemy] != 'V') {
                                    return "baixo";
                                }
                            }
                        }
                        if (lineHeroi == lineEnemy) {//Ao chegar aqui, ja apanhou o heroi, pq esta na mesma linha e coluna dele
                            return "morreu";
                        }
                    } else {//se a coluna do heroi não for a mesma que a da mumia
                        if (columnEnemy > columnHeroi) {//Se a mumia tiver á direita do heroi, tem de se mover para a esquerda
                            if (columnEnemy > 1) {
                                if ( matrix[lineEnemy][columnEnemy - 1] != '|' && matrix[lineEnemy][columnEnemy - 2] != 'M' && matrix[lineEnemy][columnEnemy - 2] != 'E' && matrix[lineEnemy][columnEnemy - 2] != 'V') {
                                    return "esq";
                                }
                            }
                        } else {
                            if (columnEnemy != matrix.length - 2) {//Se a mumia tiver á esquerda do heroi, tem de se mover para a direita
                                if (matrix[lineEnemy][columnEnemy + 1] != '|' && matrix[lineEnemy][columnEnemy + 2] != 'M' && matrix[lineEnemy][columnEnemy + 2] != 'E' && matrix[lineEnemy][columnEnemy + 2] != 'V') {
                                    return "dir";
                                }
                            }
                        }
                    }
                break;
            case REDMUMMY:
                /*if(columnMumiaVermelha == columnMumiaBranca && lineMumiaVermelha == lineMumiaBranca){
                    return "morreu";
                }*/
                if (lineHeroi == lineEnemy) {//se a coluna do heroi for a mesma que a da mumia
                    if (columnEnemy > columnHeroi) {//se a linha da mumia tiver a baixo da do heroi, move-se para cima
                        if (columnEnemy > 1) {
                            if (matrix[lineEnemy][columnEnemy - 1] != '|' && matrix[lineEnemy][columnEnemy-2] != 'M' && matrix[lineEnemy][columnEnemy-2] != 'E' && matrix[lineEnemy][columnEnemy-2] != 'V') {
                                return "esq";
                            }
                        }
                    } else {//se a linha da mumia tiver a cima da do heroi, move-se para baixo
                        if (columnEnemy != matrix.length - 2) {
                            if (matrix[lineEnemy][columnEnemy + 2] != '|' && matrix[lineEnemy][columnEnemy+2] != 'M' && matrix[lineEnemy][columnEnemy+2] != 'E' && matrix[lineEnemy][columnEnemy+2] != 'V') {
                                return "dir";
                            }
                        }
                    }
                    if (columnHeroi == columnEnemy) {//Ao chegar aqui, ja apanhou o heroi, pq esta na mesma linha e coluna dele
                        return "morreu";
                    }
                } else {//se a coluna do heroi não for a mesma que a da mumia
                    if (lineEnemy > lineHeroi) {//Se a mumia tiver á direita do heroi, tem de se mover para a esquerda
                        if (lineEnemy > 1) {
                            if (matrix[lineEnemy - 1][columnEnemy] != '-' && matrix[lineEnemy-2][columnEnemy] != 'M' && matrix[lineEnemy-2][columnEnemy] != 'V' && matrix[lineEnemy-2][columnEnemy] != 'E') {
                                return "cima";
                            }
                        }
                    } else {
                        if (columnEnemy != matrix.length - 2) {//Se a mumia tiver á esquerda do heroi, tem de se mover para a direita
                            if (matrix[lineEnemy +1][columnEnemy] != '-' && matrix[lineEnemy+2][columnEnemy] != 'M' && matrix[lineEnemy+2][columnEnemy] != 'V' && matrix[lineEnemy+2][columnEnemy] != 'E') {
                                return "baixo";
                            }
                        }
                    }
                }
                break;
            case SCORPION:
                if (columnHeroi == columnEnemy) {//se a coluna do heroi for a mesma que a da mumia
                    if (lineEnemy > lineHeroi) {//se a linha da mumia tiver a baixo da do heroi, move-se para cima
                        if (lineEnemy > 1) {
                            if (matrix[lineEnemy - 1][columnEnemy] != '-' && matrix[lineEnemy-2][columnEnemy] != 'M' && matrix[lineEnemy-2][columnEnemy] != 'V' && matrix[lineEnemy-2][columnEnemy] != 'E') {
                                return "cima";
                            }
                        }
                    } else {//se a linha da mumia tiver a cima da do heroi, move-se para baixo
                        if (lineEnemy != matrix.length - 2) {
                            if (matrix[lineEnemy + 1][columnEnemy] != '-' && matrix[lineEnemy+2][columnEnemy] != 'M' && matrix[lineEnemy+2][columnEnemy] != 'V' && matrix[lineEnemy+2][columnEnemy] != 'E') {
                                return "baixo";
                            }
                        }
                    }
                    if (lineHeroi == lineEnemy) {//Ao chegar aqui, ja apanhou o heroi, pq esta na mesma linha e coluna dele
                        return "morreu";
                    }
                } else {//se a coluna do heroi não for a mesma que a da mumia
                    if (columnEnemy > columnHeroi) {//Se a mumia tiver á direita do heroi, tem de se mover para a esquerda
                        if (columnEnemy > 1) {
                            if (matrix[lineEnemy][columnEnemy - 1] != '|' && matrix[lineEnemy][columnEnemy-2] != 'M' && matrix[lineEnemy][columnEnemy-2] != 'V' && matrix[lineEnemy][columnEnemy-2] != 'E') {
                                return "esq";
                            }
                        }
                    } else {
                        if (columnEnemy != matrix.length - 2) {//Se a mumia tiver á esquerda do heroi, tem de se mover para a direita
                            if (matrix[lineEnemy][columnEnemy + 1] != '|'&& matrix[lineEnemy][columnEnemy+2] != 'M' && matrix[lineEnemy][columnEnemy+2] != 'V' && matrix[lineEnemy][columnEnemy+2] != 'E') {
                                return "dir";
                            }
                        }
                    }
                }
                break;
        }
        return "nao"; //caso ninguém se possa mover
    }


    public int getLineEnemy() {
        return lineEnemy;
    }

    public int getColumnEnemy() {
        return columnEnemy;
    }

    /*public String movimentoMumiaBrancaScorpion(){
        if (columnHeroi == columnEnemy) {//se a coluna do heroi for a mesma que a da mumia
            if (lineEnemy > lineHeroi) {//se a linha da mumia tiver a baixo da do heroi, move-se para cima
                if (lineEnemy > 1) {
                    if (matrix[lineEnemy - 2][columnEnemy] == '.' && matrix[lineEnemy - 1][columnEnemy] != '-') {
                        return "cima";
                    }
                }
            } else {//se a linha da mumia tiver a cima da do heroi, move-se para baixo
                if (lineEnemy != matrix.length - 2) {
                    if (matrix[lineEnemy + 2][columnEnemy] == '.' && matrix[lineEnemy + 1][columnEnemy] != '-') {
                        return "baixo";
                    }
                }
            }
            if (lineHeroi == lineEnemy) {//Ao chegar aqui, ja apanhou o heroi, pq esta na mesma linha e coluna dele
                return "nao";
            }
        } else {//se a coluna do heroi não for a mesma que a da mumia
            if (columnEnemy > columnHeroi) {//Se a mumia tiver á direita do heroi, tem de se mover para a esquerda
                if (columnEnemy > 1) {
                    if (matrix[lineEnemy][columnEnemy - 2] == '.' && matrix[lineEnemy][columnEnemy - 1] != '|') {
                        return "esq";
                    }
                }
            } else {
                if (columnEnemy != matrix.length - 2) {//Se a mumia tiver á esquerda do heroi, tem de se mover para a direita
                    if (matrix[lineEnemy][columnEnemy + 2] == '.' && matrix[lineEnemy][columnEnemy + 1] != '|') {
                        return "dir";
                    }
                }
            }
        }

        return "null";

    }*/



}
