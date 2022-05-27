package MummyMaze;

import static MummyMaze.EnemyType.*;

public class Enemy {
    private Cell enemyCell;
    private Cell heroCell;

    private EnemyType tipoInimigo;
    private char[][] matrix;

    public EnemyType getTipoInimigo() {
        return tipoInimigo;
    }

    public Enemy(EnemyType tipoInimigo, Cell enemy) {
        this.enemyCell = enemy;
        this.tipoInimigo = tipoInimigo;
    }

    public void move(MummyMazeState mummyMazeState) {
        heroCell = mummyMazeState.getCellHero();
        matrix = mummyMazeState.getMatrix();

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
                if(!heroCell.equals(enemyCell)) {

                    matrix[heroCell.getLine()][heroCell.getColumn()] = matrix[enemyCell.getLine()][enemyCell.getColumn()];

                    matrix[enemyCell.getLine()][enemyCell.getColumn()] = '.';
                    enemyCell.setLine(heroCell.getLine());
                    enemyCell.setColumn(heroCell.getColumn());
                }
                break;
            case "cima":
                matrix[enemyCell.getLine() - 2][enemyCell.getColumn()] = matrix[enemyCell.getLine()][enemyCell.getColumn()];
                matrix[enemyCell.getLine()][enemyCell.getColumn()] = '.';
                enemyCell.setLine(enemyCell.getLine()-2);
                break;
            case "baixo":
                matrix[enemyCell.getLine() + 2][enemyCell.getColumn()] = matrix[enemyCell.getLine()][enemyCell.getColumn()];
                matrix[enemyCell.getLine()][enemyCell.getColumn()] = '.';
                enemyCell.setLine(enemyCell.getLine()+2);
                break;
            case "esq":
                matrix[enemyCell.getLine()][enemyCell.getColumn() -2] = matrix[enemyCell.getLine()][enemyCell.getColumn()];
                matrix[enemyCell.getLine()][enemyCell.getColumn()] = '.';
                enemyCell.setColumn(enemyCell.getColumn()-2);
                break;
            case "dir":
                matrix[enemyCell.getLine()][enemyCell.getColumn() + 2] = matrix[enemyCell.getLine()][enemyCell.getColumn()];
                matrix[enemyCell.getLine()][enemyCell.getColumn()] = '.';
                enemyCell.setColumn(enemyCell.getColumn()+2);
                break;
            case "nao":
                matrix[enemyCell.getLine()][enemyCell.getColumn()] = matrix[enemyCell.getLine()][enemyCell.getColumn()];
                //matrix[lineEnemy][columnEnemy] = matrix[lineEnemy][columnEnemy];
                break;

        }
    }

    private String canMove() { //mas este não recebe o state
        switch (tipoInimigo) {
            case WHITEMUMMY:
                if (enemyCell.isInSameColumn(heroCell)) {//se a coluna do heroi for a mesma que a da mumia
                    if (enemyCell.getLine() > heroCell.getLine()) {//se a linha da mumia tiver a baixo da do heroi, move-se para cima
                        if (enemyCell.getLine() > 2) {
                            if (matrix[enemyCell.getLine() - 1][enemyCell.getColumn()] != '-' && matrix[enemyCell.getLine() - 1][enemyCell.getColumn()] != '=') {
                                return "cima";
                            }
                        }
                    } else if(enemyCell.getLine() < heroCell.getLine()){//se a linha da mumia tiver a cima da do heroi, move-se para baixo
                        if (enemyCell.getLine() < 10) {
                            if (matrix[enemyCell.getLine() + 1][enemyCell.getColumn()] != '-' && matrix[enemyCell.getLine() + 1][enemyCell.getColumn()] != '=') {
                                return "baixo";
                            }
                        }
                    }else{
                        if (enemyCell.isInSameLine(heroCell) && enemyCell.isInSameColumn(heroCell)) {//Ao chegar aqui, ja apanhou o heroi, pq esta na mesma linha e coluna dele
                            return "morreu";
                        }
                    }
                } else if(enemyCell.getColumn() > heroCell.getColumn()){//se a coluna do heroi não for a mesma que a da mumia
                    //Se a mumia tiver á direita do heroi, tem de se mover para a esquerda
                    if (enemyCell.getColumn() > 2) {
                        if (matrix[enemyCell.getLine()][enemyCell.getColumn() - 1] != '|' && matrix[enemyCell.getLine()][enemyCell.getColumn() - 1] != ')') {
                            return "esq";
                        }
                    }
                } else {
                    if (enemyCell.getColumn() < 10) {//Se a mumia tiver á esquerda do heroi, tem de se mover para a direita
                        if (matrix[enemyCell.getLine()][enemyCell.getColumn() + 1] != '|' && matrix[enemyCell.getLine()][enemyCell.getColumn() + 1] != ')') {
                            return "dir";
                        }
                    }
                }
                if (enemyCell.isInSameLine(heroCell)) {//se a coluna do heroi for a mesma que a da mumia
                    if (enemyCell.getColumn() > heroCell.getColumn()) {//se a linha da mumia tiver a baixo da do heroi, move-se para cima
                        if (enemyCell.getColumn() > 1) {
                            if (matrix[enemyCell.getLine()][enemyCell.getColumn() - 1] != '|' && matrix[enemyCell.getLine()][enemyCell.getColumn() - 1] != ')') {
                                return "esq";
                            }
                        }
                    } else if(enemyCell.getColumn() < heroCell.getColumn()){//se a mumia tiver
                        if (enemyCell.getColumn() != matrix.length - 2) {
                            if (matrix[enemyCell.getLine()][enemyCell.getColumn() + 1] != '|' && matrix[enemyCell.getLine()][enemyCell.getColumn() - 1] != ')') {
                                return "dir";
                            }
                        }
                    }else {
                        if (enemyCell.isInSameColumn(heroCell)) {//Ao chegar aqui, ja apanhou o heroi, pq esta na mesma linha e coluna dele
                            return "morreu";
                        }
                    }
                } else if (enemyCell.getLine() > heroCell.getLine()) {//Se a mumia tiver á direita do heroi, tem de se mover para a esquerda
                    if (enemyCell.getLine() > 1) {
                        if (matrix[enemyCell.getLine() - 1][enemyCell.getColumn()] != '-' && matrix[enemyCell.getLine() - 1][enemyCell.getColumn()] != '=') {
                            return "cima";
                        }
                    }
                } else {
                    if (enemyCell.getColumn() != matrix.length - 2) {//Se a mumia tiver á esquerda do heroi, tem de se mover para a direita
                        if (matrix[enemyCell.getLine() +1][enemyCell.getColumn()] != '-' && matrix[enemyCell.getLine() +1][enemyCell.getColumn()] != '=') {
                            return "baixo";
                        }
                    }
                }
                break;
            case REDMUMMY:
                if (enemyCell.isInSameLine(heroCell)) {//se a coluna do heroi for a mesma que a da mumia
                    if (enemyCell.getColumn() > heroCell.getColumn()) {//se a linha da mumia tiver a baixo da do heroi, move-se para cima
                        if (enemyCell.getColumn() > 1) {
                            if (matrix[enemyCell.getLine()][enemyCell.getColumn() - 1] != '|' && matrix[enemyCell.getLine()][enemyCell.getColumn() - 1] != ')') {
                                return "esq";
                            }
                        }
                    } else if(enemyCell.getColumn() < heroCell.getColumn()){//se a mumia tiver
                        if (enemyCell.getColumn() != matrix.length - 2) {
                            if (matrix[enemyCell.getLine()][enemyCell.getColumn() + 1] != '|' && matrix[enemyCell.getLine()][enemyCell.getColumn() - 1] != ')') {
                                return "dir";
                            }
                        }
                    }else {
                        if (enemyCell.isInSameColumn(heroCell)) {//Ao chegar aqui, ja apanhou o heroi, pq esta na mesma linha e coluna dele
                            return "morreu";
                        }
                    }
                } else if (enemyCell.getLine() > heroCell.getLine()) {//Se a mumia tiver á direita do heroi, tem de se mover para a esquerda
                        if (enemyCell.getLine() > 1) {
                            if (matrix[enemyCell.getLine() - 1][enemyCell.getColumn()] != '-' && matrix[enemyCell.getLine() - 1][enemyCell.getColumn()] != '=') {
                                return "cima";
                            }
                        }
                    } else {
                        if (enemyCell.getColumn() != matrix.length - 2) {//Se a mumia tiver á esquerda do heroi, tem de se mover para a direita
                            if (matrix[enemyCell.getLine() +1][enemyCell.getColumn()] != '-' && matrix[enemyCell.getLine() +1][enemyCell.getColumn()] != '=') {
                                return "baixo";
                            }
                        }
                    }
                if (enemyCell.isInSameColumn(heroCell)) {//se a coluna do heroi for a mesma que a da mumia
                    if (enemyCell.getLine() > heroCell.getLine()) {//se a linha da mumia tiver a baixo da do heroi, move-se para cima
                        if (enemyCell.getLine() > 2) {
                            if (matrix[enemyCell.getLine() - 1][enemyCell.getColumn()] != '-' && matrix[enemyCell.getLine() - 1][enemyCell.getColumn()] != '=') {
                                return "cima";
                            }
                        }
                    } else if(enemyCell.getLine() < heroCell.getLine()){//se a linha da mumia tiver a cima da do heroi, move-se para baixo
                        if (enemyCell.getLine() < 10) {
                            if (matrix[enemyCell.getLine() + 1][enemyCell.getColumn()] != '-' && matrix[enemyCell.getLine() + 1][enemyCell.getColumn()] != '=') {
                                return "baixo";
                            }
                        }
                    }else{
                        if (enemyCell.isInSameLine(heroCell) && enemyCell.isInSameColumn(heroCell)) {//Ao chegar aqui, ja apanhou o heroi, pq esta na mesma linha e coluna dele
                            return "morreu";
                        }
                    }
                } else if(enemyCell.getColumn() > heroCell.getColumn()){//se a coluna do heroi não for a mesma que a da mumia
                    //Se a mumia tiver á direita do heroi, tem de se mover para a esquerda
                    if (enemyCell.getColumn() > 2) {
                        if (matrix[enemyCell.getLine()][enemyCell.getColumn() - 1] != '|' && matrix[enemyCell.getLine()][enemyCell.getColumn() - 1] != ')') {
                            return "esq";
                        }
                    }
                } else {
                    if (enemyCell.getColumn() < 10) {//Se a mumia tiver á esquerda do heroi, tem de se mover para a direita
                        if (matrix[enemyCell.getLine()][enemyCell.getColumn() + 1] != '|' && matrix[enemyCell.getLine()][enemyCell.getColumn() + 1] != ')') {
                            return "dir";
                        }
                    }
                }
                break;
            case SCORPION:
                if (enemyCell.getLine() == 7 && enemyCell.getColumn() == 9) {
                    System.out.println("debug");
                }
                if (enemyCell.isInSameColumn(heroCell)) {//se a coluna do heroi for a mesma que a da mumia
                    if (enemyCell.getLine() > heroCell.getLine()) {//se a linha da mumia tiver a baixo da do heroi, move-se para cima
                        if (enemyCell.getLine() > 2) {
                            if (matrix[enemyCell.getLine() - 1][enemyCell.getColumn()] != '-' && matrix[enemyCell.getLine() - 1][enemyCell.getColumn()] != '=') {
                                return "cima";
                            }
                        }
                    } else if(enemyCell.getLine() < heroCell.getLine()){//se a linha da mumia tiver a cima da do heroi, move-se para baixo
                        if (enemyCell.getLine() < 10) {
                            if (matrix[enemyCell.getLine() + 1][enemyCell.getColumn()] != '-' && matrix[enemyCell.getLine() + 1][enemyCell.getColumn()] != '=') {
                                return "baixo";
                            }
                        }
                    }else{
                        if (enemyCell.isInSameLine(heroCell) && enemyCell.isInSameColumn(heroCell)) {//Ao chegar aqui, ja apanhou o heroi, pq esta na mesma linha e coluna dele
                            return "morreu";
                        }
                    }
                } else if(enemyCell.getColumn() > heroCell.getColumn()){//se a coluna do heroi não for a mesma que a da mumia
                    //Se a mumia tiver á direita do heroi, tem de se mover para a esquerda
                    if (enemyCell.getColumn() > 2) {
                        if (matrix[enemyCell.getLine()][enemyCell.getColumn() - 1] != '|' && matrix[enemyCell.getLine()][enemyCell.getColumn() - 1] != ')') {
                            return "esq";
                        }
                    }
                } else {
                    if (enemyCell.getColumn() < 10) {//Se a mumia tiver á esquerda do heroi, tem de se mover para a direita
                        if (matrix[enemyCell.getLine()][enemyCell.getColumn() + 1] != '|' && matrix[enemyCell.getLine()][enemyCell.getColumn() + 1] != ')') {
                            return "dir";
                        }
                    }
                }
                if (enemyCell.isInSameLine(heroCell)) {//se a coluna do heroi for a mesma que a da mumia
                    if (enemyCell.getColumn() > heroCell.getColumn()) {//se a linha da mumia tiver a baixo da do heroi, move-se para cima
                        if (enemyCell.getColumn() > 1) {
                            if (matrix[enemyCell.getLine()][enemyCell.getColumn() - 1] != '|' && matrix[enemyCell.getLine()][enemyCell.getColumn() - 1] != ')') {
                                return "esq";
                            }
                        }
                    } else if(enemyCell.getColumn() < heroCell.getColumn()){//se a mumia tiver
                        if (enemyCell.getColumn() != matrix.length - 2) {
                            if (matrix[enemyCell.getLine()][enemyCell.getColumn() + 1] != '|' && matrix[enemyCell.getLine()][enemyCell.getColumn() - 1] != ')') {
                                return "dir";
                            }
                        }
                    }else {
                        if (enemyCell.isInSameColumn(heroCell)) {//Ao chegar aqui, ja apanhou o heroi, pq esta na mesma linha e coluna dele
                            return "morreu";
                        }
                    }
                } else if (enemyCell.getLine() > heroCell.getLine()) {//Se a mumia tiver á direita do heroi, tem de se mover para a esquerda
                    if (enemyCell.getLine() > 1) {
                        if (matrix[enemyCell.getLine() - 1][enemyCell.getColumn()] != '-' && matrix[enemyCell.getLine() - 1][enemyCell.getColumn()] != '=') {
                            return "cima";
                        }
                    }
                } else {
                    if (enemyCell.getColumn() != matrix.length - 2) {//Se a mumia tiver á esquerda do heroi, tem de se mover para a direita
                        if (matrix[enemyCell.getLine() +1][enemyCell.getColumn()] != '-' && matrix[enemyCell.getLine() +1][enemyCell.getColumn()] != '=') {
                            return "baixo";
                        }
                    }
                }
                break;
        }
        return "nao"; //caso ninguém se possa mover
    }
}
