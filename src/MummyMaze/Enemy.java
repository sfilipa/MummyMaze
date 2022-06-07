package MummyMaze;

import java.util.List;
import java.util.Objects;

import static MummyMaze.EnemyType.*;

public class Enemy {
    private Cell enemyCell;
    private Cell heroCell;
    private Cell keyCell;

    private EnemyType tipoInimigo;
    private char[][] matrix;

    public EnemyType getTipoInimigo() {
        return tipoInimigo;
    }

    public Cell getCellEnemy() {
        return enemyCell;
    }

    public char getSymbol() {
        switch (tipoInimigo) {
            case WHITEMUMMY:
                return 'M';
            case REDMUMMY:
                return 'V';
            case SCORPION:
                return 'E';
            default:
                return '0';
        }
    }

    public Enemy(EnemyType tipoInimigo, Cell enemy) {
        this.enemyCell = enemy;
        this.tipoInimigo = tipoInimigo;
    }

    public Enemy(Enemy enemy) {
        this(enemy.tipoInimigo, enemy.enemyCell.clone());
        if (enemy.heroCell != null) {
            this.heroCell = enemy.heroCell.clone();
        }
        if (enemy.keyCell != null) {
            this.keyCell = enemy.keyCell.clone();
        }
    }

    public void move(MummyMazeState mummyMazeState, Enemy enemy) {
        heroCell = mummyMazeState.getCellHero();
        matrix = mummyMazeState.getMatrix();
        keyCell = mummyMazeState.getCellKey();

        if (tipoInimigo == WHITEMUMMY || tipoInimigo == REDMUMMY) { //2 movimentos
            movimentosInimigos(mummyMazeState);
            mummyMazeState.VerifyThings(enemy);

            mummyMazeState.firePuzzleChanged(null);
            movimentosInimigos(mummyMazeState);
        }
        if (tipoInimigo == SCORPION) { //apenas 1 movimento
            movimentosInimigos(mummyMazeState);
        }
    }

    public void movimentosInimigos(MummyMazeState mummyMazeState){
        switch (canMove()) {//apenas faz os movimentos
            case "morreu":
                if (!heroCell.equals(enemyCell)) {
                    matrix[heroCell.getLine()][heroCell.getColumn()] = matrix[enemyCell.getLine()][enemyCell.getColumn()];
                    matrix[enemyCell.getLine()][enemyCell.getColumn()] = '.';

                    enemyCell.setLine(heroCell.getLine());
                    enemyCell.setColumn(heroCell.getColumn());
                }
                break;
            case "cima":

                matrix[enemyCell.getLine() - 2][enemyCell.getColumn()] = matrix[enemyCell.getLine()][enemyCell.getColumn()];
                if (enemyCell.equals(keyCell) && keyCell != null) {
                    matrix[enemyCell.getLine()][enemyCell.getColumn()] = 'C';
                } else {
                    matrix[enemyCell.getLine()][enemyCell.getColumn()] = '.';
                }
                enemyCell.setLine(enemyCell.getLine() - 2);
                if(keyCell!=null) {
                    if (enemyCell.equals(keyCell)) {
                        mummyMazeState.Key();
                    }
                }
                break;
            case "baixo":
                matrix[enemyCell.getLine() + 2][enemyCell.getColumn()] = matrix[enemyCell.getLine()][enemyCell.getColumn()];
                if (enemyCell.equals(keyCell) && keyCell != null) {
                    matrix[enemyCell.getLine()][enemyCell.getColumn()] = 'C';
                } else {
                    matrix[enemyCell.getLine()][enemyCell.getColumn()] = '.';
                }
                enemyCell.setLine(enemyCell.getLine() + 2);
                if(keyCell!=null) {
                    if (enemyCell.equals(keyCell)) {
                        mummyMazeState.Key();
                    }
                }
                break;
            case "esq":
                matrix[enemyCell.getLine()][enemyCell.getColumn() - 2] = matrix[enemyCell.getLine()][enemyCell.getColumn()];
                if (enemyCell.equals(keyCell) && keyCell != null) {
                    matrix[enemyCell.getLine()][enemyCell.getColumn()] = 'C';
                } else {
                    matrix[enemyCell.getLine()][enemyCell.getColumn()] = '.';
                }
                enemyCell.setColumn(enemyCell.getColumn() - 2);
                if(keyCell!=null) {
                    if (enemyCell.equals(keyCell)) {
                        mummyMazeState.Key();
                    }
                }
                break;
            case "dir":
                matrix[enemyCell.getLine()][enemyCell.getColumn() + 2] = matrix[enemyCell.getLine()][enemyCell.getColumn()];
                if (enemyCell.equals(keyCell) && keyCell != null) {
                    matrix[enemyCell.getLine()][enemyCell.getColumn()] = 'C';
                } else {
                    matrix[enemyCell.getLine()][enemyCell.getColumn()] = '.';
                }
                enemyCell.setColumn(enemyCell.getColumn() + 2);
                if(keyCell!=null) {
                    if (enemyCell.equals(keyCell)) {
                        mummyMazeState.Key();
                    }
                }
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
                        if (enemyCell.getLine() > 1) {
                            if (matrix[enemyCell.getLine() - 1][enemyCell.getColumn()] != '-' && matrix[enemyCell.getLine() - 1][enemyCell.getColumn()] != '=') {
                                return "cima";
                            }
                        }
                    } else if (enemyCell.getLine() < heroCell.getLine()) {//se a linha da mumia tiver a cima da do heroi, move-se para baixo
                        if (enemyCell.getLine() < 10) {
                            if (matrix[enemyCell.getLine() + 1][enemyCell.getColumn()] != '-' && matrix[enemyCell.getLine() + 1][enemyCell.getColumn()] != '=') {
                                return "baixo";
                            }
                        }
                    } else {
                        if (enemyCell.isInSameLine(heroCell) && enemyCell.isInSameColumn(heroCell)) {//Ao chegar aqui, ja apanhou o heroi, pq esta na mesma linha e coluna dele
                            return "morreu";
                        }
                    }
                } else if (enemyCell.getColumn() > heroCell.getColumn()) {//se a coluna do heroi não for a mesma que a da mumia
                    //Se a mumia tiver á direita do heroi, tem de se mover para a esquerda
                    if (enemyCell.getColumn() > 1) {
                        if (matrix[enemyCell.getLine()][enemyCell.getColumn() - 1] != '|' && matrix[enemyCell.getLine()][enemyCell.getColumn() - 1] != '"') {
                            return "esq";
                        }
                    }
                } else {
                    if (enemyCell.getColumn() < 10) {//Se a mumia tiver á esquerda do heroi, tem de se mover para a direita
                        if (matrix[enemyCell.getLine()][enemyCell.getColumn() + 1] != '|' && matrix[enemyCell.getLine()][enemyCell.getColumn() + 1] != '"') {
                            return "dir";
                        }
                    }
                }
                if (enemyCell.isInSameLine(heroCell)) {//se a coluna do heroi for a mesma que a da mumia
                    if (enemyCell.getColumn() > heroCell.getColumn()) {//se a linha da mumia tiver a baixo da do heroi, move-se para cima
                        if (enemyCell.getColumn() > 1) {
                            if (matrix[enemyCell.getLine()][enemyCell.getColumn() - 1] != '|' && matrix[enemyCell.getLine()][enemyCell.getColumn() - 1] != '"') {
                                return "esq";
                            }
                        }
                    } else if (enemyCell.getColumn() < heroCell.getColumn()) {//se a mumia tiver
                        if (enemyCell.getColumn() != matrix.length - 2) {
                            if (matrix[enemyCell.getLine()][enemyCell.getColumn() + 1] != '|' && matrix[enemyCell.getLine()][enemyCell.getColumn() + 1] != '"') {
                                return "dir";
                            }
                        }
                    } else {
                        if (enemyCell.isInSameColumn(heroCell) && enemyCell.isInSameLine(heroCell)) {//Ao chegar aqui, ja apanhou o heroi, pq esta na mesma linha e coluna dele
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
                    if (enemyCell.getColumn() != matrix.length - 1) {//Se a mumia tiver á esquerda do heroi, tem de se mover para a direita
                        if (matrix[enemyCell.getLine() + 1][enemyCell.getColumn()] != '-' && matrix[enemyCell.getLine() + 1][enemyCell.getColumn()] != '=') {
                            return "baixo";
                        }
                    }
                }
                break;
            case REDMUMMY:
                if (enemyCell.isInSameLine(heroCell)) {//se a coluna do heroi for a mesma que a da mumia
                    if (enemyCell.getColumn() > heroCell.getColumn()) {//se a linha da mumia tiver a baixo da do heroi, move-se para cima
                        if (enemyCell.getColumn() > 1) {
                            if (matrix[enemyCell.getLine()][enemyCell.getColumn() - 1] != '|' && matrix[enemyCell.getLine()][enemyCell.getColumn() - 1] != '"') {
                                return "esq";
                            }
                        }
                    } else if (enemyCell.getColumn() < heroCell.getColumn()) {//se a mumia tiver
                        if (enemyCell.getColumn() != matrix.length - 2) {
                            if (matrix[enemyCell.getLine()][enemyCell.getColumn() + 1] != '|' && matrix[enemyCell.getLine()][enemyCell.getColumn() + 1] != '"') {
                                return "dir";
                            }
                        }
                    } else {
                        if (enemyCell.isInSameColumn(heroCell) && enemyCell.isInSameLine(heroCell)) {//Ao chegar aqui, ja apanhou o heroi, pq esta na mesma linha e coluna dele
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
                    if (enemyCell.getColumn() != matrix.length - 1) {//Se a mumia tiver á esquerda do heroi, tem de se mover para a direita
                        if (matrix[enemyCell.getLine() + 1][enemyCell.getColumn()] != '-' && matrix[enemyCell.getLine() + 1][enemyCell.getColumn()] != '=') {
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
                    } else if (enemyCell.getLine() < heroCell.getLine()) {//se a linha da mumia tiver a cima da do heroi, move-se para baixo
                        if (enemyCell.getLine() < 10) {
                            if (matrix[enemyCell.getLine() + 1][enemyCell.getColumn()] != '-' && matrix[enemyCell.getLine() + 1][enemyCell.getColumn()] != '=') {
                                return "baixo";
                            }
                        }
                    } else {
                        if (enemyCell.isInSameLine(heroCell) && enemyCell.isInSameColumn(heroCell)) {//Ao chegar aqui, ja apanhou o heroi, pq esta na mesma linha e coluna dele
                            return "morreu";
                        }
                    }
                } else if (enemyCell.getColumn() > heroCell.getColumn()) {//se a coluna do heroi não for a mesma que a da mumia
                    //Se a mumia tiver á direita do heroi, tem de se mover para a esquerda
                    if (enemyCell.getColumn() > 2) {
                        if (matrix[enemyCell.getLine()][enemyCell.getColumn() - 1] != '|' && matrix[enemyCell.getLine()][enemyCell.getColumn() - 1] != '"') {
                            return "esq";
                        }
                    }
                } else {
                    if (enemyCell.getColumn() < 10) {//Se a mumia tiver á esquerda do heroi, tem de se mover para a direita
                        if (matrix[enemyCell.getLine()][enemyCell.getColumn() + 1] != '|' && matrix[enemyCell.getLine()][enemyCell.getColumn() + 1] != '"') {
                            return "dir";
                        }
                    }
                }
                break;
            case SCORPION:
                if (enemyCell.isInSameColumn(heroCell)) {//se a coluna do heroi for a mesma que a da mumia
                    if (enemyCell.getLine() > heroCell.getLine()) {//se a linha da mumia tiver a baixo da do heroi, move-se para cima
                        if (enemyCell.getLine() > 1) {
                            if (matrix[enemyCell.getLine() - 1][enemyCell.getColumn()] != '-' && matrix[enemyCell.getLine() - 1][enemyCell.getColumn()] != '=') {
                                return "cima";
                            }
                        }
                    } else if (enemyCell.getLine() < heroCell.getLine()) {//se a linha da mumia tiver a cima da do heroi, move-se para baixo
                        if (enemyCell.getLine() < 10) {
                            if (matrix[enemyCell.getLine() + 1][enemyCell.getColumn()] != '-' && matrix[enemyCell.getLine() + 1][enemyCell.getColumn()] != '=') {
                                return "baixo";
                            }
                        }
                    } else {
                        if (enemyCell.isInSameLine(heroCell) && enemyCell.isInSameColumn(heroCell)) {//Ao chegar aqui, ja apanhou o heroi, pq esta na mesma linha e coluna dele
                            return "morreu";
                        }
                    }
                } else if (enemyCell.getColumn() > heroCell.getColumn()) {//se a coluna do heroi não for a mesma que a da mumia
                    //Se a mumia tiver á direita do heroi, tem de se mover para a esquerda
                    if (enemyCell.getColumn() > 1) {
                        if (matrix[enemyCell.getLine()][enemyCell.getColumn() - 1] != '|' && matrix[enemyCell.getLine()][enemyCell.getColumn() - 1] != '"') {
                            return "esq";
                        }
                    }
                } else {
                    if (enemyCell.getColumn() < 10) {//Se a mumia tiver á esquerda do heroi, tem de se mover para a direita
                        if (matrix[enemyCell.getLine()][enemyCell.getColumn() + 1] != '|' && matrix[enemyCell.getLine()][enemyCell.getColumn() + 1] != '"') {
                            return "dir";
                        }
                    }
                }
                if (enemyCell.isInSameLine(heroCell)) {//se a coluna do heroi for a mesma que a da mumia
                    if (enemyCell.getColumn() > heroCell.getColumn()) {//se a linha da mumia tiver a baixo da do heroi, move-se para cima
                        if (enemyCell.getColumn() > 1) {
                            if (matrix[enemyCell.getLine()][enemyCell.getColumn() - 1] != '|' && matrix[enemyCell.getLine()][enemyCell.getColumn() - 1] != '"') {
                                return "esq";
                            }
                        }
                    } else if (enemyCell.getColumn() < heroCell.getColumn()) {//se a mumia tiver
                        if (enemyCell.getColumn() != matrix.length - 2) {
                            if (matrix[enemyCell.getLine()][enemyCell.getColumn() + 1] != '|' && matrix[enemyCell.getLine()][enemyCell.getColumn() + 1] != '"') {
                                return "dir";
                            }
                        }
                    } else {
                        if (enemyCell.isInSameColumn(heroCell) && enemyCell.isInSameLine(heroCell)) {//Ao chegar aqui, ja apanhou o heroi, pq esta na mesma linha e coluna dele
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
                    if (enemyCell.getColumn() != matrix.length - 1) {//Se a mumia tiver á esquerda do heroi, tem de se mover para a direita
                        if (matrix[enemyCell.getLine() + 1][enemyCell.getColumn()] != '-' && matrix[enemyCell.getLine() + 1][enemyCell.getColumn()] != '=') {
                            return "baixo";
                        }
                    }
                }
                break;
        }
        return "nao"; //caso ninguém se possa mover
    }

    @Override
    protected Enemy clone() {
        return new Enemy(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Enemy)) return false;
        Enemy enemy = (Enemy) o;
        return Objects.equals(enemyCell, enemy.enemyCell) && tipoInimigo == enemy.tipoInimigo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(enemyCell, tipoInimigo);
    }
}
