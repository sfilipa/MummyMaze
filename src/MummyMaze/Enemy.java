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

    public Enemy(EnemyType tipoInimigo, Cell enemy) {
        this.enemy = enemy;
        this.tipoInimigo = tipoInimigo;
    }

    public void move(MummyMazeState mummyMazeState) {
        this.hero = mummyMazeState.getCellHero();
        this.matrix = mummyMazeState.getMatrix();

        if(tipoInimigo == WHITEMUMMY || tipoInimigo == REDMUMMY){ //2 movimentos
            if(mummyMazeState.getNaoMexeu() == 2){//PERGUNTAR AO STOR
                mummyMazeState.firePuzzleChanged(null);
                movimentosInimigos();
                //mummyMazeState.setValueAt(enemy, lineEnemy, columnEnemy);
                mummyMazeState.setNaoMexeu(0);
            }else {
                mummyMazeState.firePuzzleChanged(null);
                movimentosInimigos();
                //mummyMazeState.setValueAt(enemy, lineEnemy, columnEnemy);
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
                    matrix[enemy.getLine()][enemy.getColumn()] = 'M';
                }
                if(tipoInimigo == REDMUMMY){
                    matrix[enemy.getLine()][enemy.getColumn()] = 'V';
                }
                if(tipoInimigo == SCORPION){
                    matrix[enemy.getLine()][enemy.getColumn()] = 'E';
                }
                matrix[enemy.getLine()][enemy.getColumn()] = '.';

                break;
            case "cima":
                matrix[enemy.getLine() - 2][enemy.getColumn()] = matrix[enemy.getLine()][enemy.getColumn()];
                matrix[enemy.getLine()][enemy.getColumn()] = '.';
                enemy.setLine(enemy.getLine()-2);
                break;
            case "baixo":
                matrix[enemy.getLine() + 2][enemy.getColumn()] = matrix[enemy.getLine()][enemy.getColumn()];
                matrix[enemy.getLine()][enemy.getColumn()] = '.';
                enemy.setLine(enemy.getLine()+2);
                break;
            case "esq":
                matrix[enemy.getLine()][enemy.getColumn() -2] = matrix[enemy.getLine()][enemy.getColumn()];
                matrix[enemy.getLine()][enemy.getColumn()] = '.';
                enemy.setColumn(enemy.getColumn()-2);
                break;
            case "dir":
                matrix[enemy.getLine()][enemy.getColumn() + 2] = matrix[enemy.getLine()][enemy.getColumn()];
                matrix[enemy.getLine()][enemy.getColumn()] = '.';
                enemy.setColumn(enemy.getColumn()+2);
                break;
            case "nao":
                matrix[enemy.getLine()][enemy.getColumn()] = matrix[enemy.getLine()][enemy.getColumn()];
                //matrix[lineEnemy][columnEnemy] = matrix[lineEnemy][columnEnemy];
                break;

        }
    }



    private String canMove() { //mas este não recebe o state
        switch (tipoInimigo) {
            case WHITEMUMMY:
                    if (enemy.isInSameColumn(hero)) {//se a coluna do heroi for a mesma que a da mumia
                        if (enemy.getLine() > hero.getLine()) {//se a linha da mumia tiver a baixo da do heroi, move-se para cima
                            if (enemy.getLine() > 1) {
                                if (matrix[enemy.getLine() - 1][enemy.getColumn()] != '-' && matrix[enemy.getLine() - 1][enemy.getColumn()] != '=') {
                                    return "cima";
                                }
                            }
                        } else {//se a linha da mumia tiver a cima da do heroi, move-se para baixo
                            if (enemy.getLine() != matrix.length - 2) {
                                if (matrix[enemy.getLine() + 1][enemy.getColumn()] != '-' && matrix[enemy.getLine() + 1][enemy.getColumn()] != '=') {
                                    return "baixo";
                                }
                            }
                        }
                        if (enemy.isInSameLine(hero) && enemy.isInSameColumn(hero)) {//Ao chegar aqui, ja apanhou o heroi, pq esta na mesma linha e coluna dele
                            return "morreu";
                        }
                    } else {//se a coluna do heroi não for a mesma que a da mumia
                        if (enemy.getColumn() > hero.getColumn()) {//Se a mumia tiver á direita do heroi, tem de se mover para a esquerda
                            if (enemy.getColumn() > 1) {
                                if (matrix[enemy.getLine()][enemy.getColumn() - 1] != '|' && matrix[enemy.getLine()][enemy.getColumn() - 1] != ')') {
                                    return "esq";
                                }
                            }
                        } else if (enemy.getColumn() < hero.getColumn()) {
                            if (enemy.getColumn() != matrix.length - 2) {//Se a mumia tiver á esquerda do heroi, tem de se mover para a direita
                                if (matrix[enemy.getLine()][enemy.getColumn() + 1] != '|' && matrix[enemy.getLine()][enemy.getColumn() + 1] != ')') {
                                    return "dir";
                                }
                            }
                        }
                    }

                if (enemy.isInSameLine(hero)) {//se a linha do heroi for a mesma que a da mumia
                    if (enemy.getColumn() > hero.getColumn()) {//se a linha da mumia tiver a baixo da do heroi, move-se para cima
                        if (enemy.getColumn() > 1) {
                            if (matrix[enemy.getLine()][enemy.getColumn() - 1] != '|' && matrix[enemy.getLine()][enemy.getColumn() - 1] != ')') {
                                return "esq";
                            }
                        }
                    } else {//se a linha da mumia tiver a cima da do heroi, move-se para baixo
                        if (enemy.getColumn() != matrix.length - 2) {
                            if (matrix[enemy.getLine()][enemy.getColumn() + 1] != '|' && matrix[enemy.getLine()][enemy.getColumn() + 1] != ')') {
                                return "dir";
                            }
                        }
                    }

                } else {//se a linha do heroi não for a mesma que a da mumia
                    if (enemy.getLine() > hero.getLine()) {//Se a mumia tiver á direita do heroi, tem de se mover para a esquerda
                        if (enemy.getLine() > 1) {
                            if (matrix[enemy.getLine() - 1][enemy.getColumn()] != '-' && matrix[enemy.getLine() - 1][enemy.getColumn()] != '=') {
                                return "cima";
                            }
                        }
                    } else {
                        if (enemy.getLine() != matrix.length - 2) {//Se a mumia tiver á esquerda do heroi, tem de se mover para a direita
                            if (matrix[enemy.getLine() + 1][enemy.getColumn()] != '-' && matrix[enemy.getLine() + 1][enemy.getColumn()] != '=') {
                                return "baixo";
                            }
                        }
                    }
                }
                break;
            case REDMUMMY:
                if (enemy.isInSameLine(hero)) {//se a coluna do heroi for a mesma que a da mumia
                    if (enemy.getColumn() > hero.getColumn()) {//se a linha da mumia tiver a baixo da do heroi, move-se para cima
                        if (enemy.getColumn() > 1) {
                            if (matrix[enemy.getLine()][enemy.getColumn() - 1] != '|' && matrix[enemy.getLine()][enemy.getColumn() - 1] != ')') {
                                return "esq";
                            }
                        }
                    } else {//se a linha da mumia tiver a cima da do heroi, move-se para baixo
                        if (enemy.getColumn() != matrix.length - 2) {
                            if (matrix[enemy.getLine()][enemy.getColumn() + 1] != '|' && matrix[enemy.getLine()][enemy.getColumn() - 1] != ')') {
                                return "dir";
                            }
                        }
                    }
                    if (enemy.isInSameColumn(hero)) {//Ao chegar aqui, ja apanhou o heroi, pq esta na mesma linha e coluna dele
                        return "morreu";
                    }
                } else {//se a coluna do heroi não for a mesma que a da mumia
                    if (enemy.getLine() > hero.getLine()) {//Se a mumia tiver á direita do heroi, tem de se mover para a esquerda
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
                }
                if (enemy.isInSameColumn(hero)) {//se a coluna do heroi for a mesma que a da mumia
                    if (enemy.getLine() > hero.getLine()) {//se a linha da mumia tiver a baixo da do heroi, move-se para cima
                        if (enemy.getLine() > 1) {
                            if (matrix[enemy.getLine() - 1][enemy.getColumn()] != '-' && matrix[enemy.getLine() - 1][enemy.getColumn()] != '=') {
                                return "cima";
                            }
                        }
                    } else {//se a linha da mumia tiver a cima da do heroi, move-se para baixo
                        if (enemy.getLine() != matrix.length - 2) {
                            if (matrix[enemy.getLine() + 1][enemy.getColumn()] != '-' && matrix[enemy.getLine() + 1][enemy.getColumn()] != '=') {
                                return "baixo";
                            }
                        }
                    }
                    if (enemy.isInSameLine(hero) && enemy.isInSameColumn(hero)) {//Ao chegar aqui, ja apanhou o heroi, pq esta na mesma linha e coluna dele
                        return "morreu";
                    }
                } else {//se a coluna do heroi não for a mesma que a da mumia
                    if (enemy.getColumn() > hero.getColumn()) {//Se a mumia tiver á direita do heroi, tem de se mover para a esquerda
                        if (enemy.getColumn() > 1) {
                            if (matrix[enemy.getLine()][enemy.getColumn() - 1] != '|' && matrix[enemy.getLine()][enemy.getColumn() - 1] != ')') {
                                return "esq";
                            }
                        }
                    } else if (enemy.getColumn() < hero.getColumn()) {
                        if (enemy.getColumn() != matrix.length - 2) {//Se a mumia tiver á esquerda do heroi, tem de se mover para a direita
                            if (matrix[enemy.getLine()][enemy.getColumn() + 1] != '|' && matrix[enemy.getLine()][enemy.getColumn() + 1] != ')') {
                                return "dir";
                            }
                        }
                    }
                }

                break;
            case SCORPION:
                if (enemy.isInSameColumn(hero)) {//se a coluna do heroi for a mesma que a da mumia
                    if (enemy.getLine() > hero.getLine()) {//se a linha da mumia tiver a baixo da do heroi, move-se para cima
                        if (enemy.getLine() > 1) {
                            if (matrix[enemy.getLine() - 1][enemy.getColumn()] != '-' && matrix[enemy.getLine() - 1][enemy.getColumn()] != '=') {
                                return "cima";
                            }
                        }
                    } else {//se a linha da mumia tiver a cima da do heroi, move-se para baixo
                        if (enemy.getLine() != matrix.length - 2) {
                            if (matrix[enemy.getLine() + 1][enemy.getColumn()] != '-' && matrix[enemy.getLine() + 1][enemy.getColumn()] != '=') {
                                return "baixo";
                            }
                        }
                    }
                    if (enemy.isInSameLine(hero)) {//Ao chegar aqui, ja apanhou o heroi, pq esta na mesma linha e coluna dele

                        return "morreu";
                    }
                } else {//se a coluna do heroi não for a mesma que a da mumia
                    if (enemy.getColumn() > hero.getColumn()) {//Se a mumia tiver á direita do heroi, tem de se mover para a esquerda
                        if (enemy.getColumn() > 1) {
                            if (matrix[enemy.getLine()][enemy.getColumn() - 1] != '|' && matrix[enemy.getLine()][enemy.getColumn() - 1] != ')') {
                                return "esq";
                            }
                        }
                    } else {
                        if (enemy.getColumn() != matrix.length - 2) {//Se a mumia tiver á esquerda do heroi, tem de se mover para a direita
                            if (matrix[enemy.getLine()][enemy.getColumn() + 1] != '|' && matrix[enemy.getLine()][enemy.getColumn() + 1] != ')') {
                                return "dir";
                            }
                        }
                    }
                }
                if (enemy.isInSameLine(hero)) {//se a linha do heroi for a mesma que a da mumia
                    if (enemy.getColumn() > hero.getColumn()) {//se a linha da mumia tiver a baixo da do heroi, move-se para cima
                        if (enemy.getColumn() > 1) {
                            if (matrix[enemy.getLine()][enemy.getColumn() - 1] != '|' && matrix[enemy.getLine()][enemy.getColumn() - 1] != ')') {
                                return "esq";
                            }
                        }
                    } else {//se a linha da mumia tiver a cima da do heroi, move-se para baixo
                        if (enemy.getColumn() != matrix.length - 2) {
                            if (matrix[enemy.getLine()][enemy.getColumn() + 1] != '|' && matrix[enemy.getLine()][enemy.getColumn() + 1] != ')') {
                                return "dir";
                            }
                        }
                    }

                } else {//se a linha do heroi não for a mesma que a da mumia
                    if (enemy.getLine() > hero.getLine()) {//Se a mumia tiver á direita do heroi, tem de se mover para a esquerda
                        if (enemy.getLine() > 1) {
                            if (matrix[enemy.getLine() - 1][enemy.getColumn()] != '-' && matrix[enemy.getLine() - 1][enemy.getColumn()] != '=') {
                                return "cima";
                            }
                        }
                    } else {
                        if (enemy.getLine() != matrix.length - 2) {//Se a mumia tiver á esquerda do heroi, tem de se mover para a direita
                            if (matrix[enemy.getLine() + 1][enemy.getColumn()] != '-' && matrix[enemy.getLine() + 1][enemy.getColumn()] != '=') {
                                return "baixo";
                            }
                        }
                    }
                }
                break;
        }
        return "nao"; //caso ninguém se possa mover
    }



}
