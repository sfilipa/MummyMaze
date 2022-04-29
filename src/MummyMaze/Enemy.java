package MummyMaze;

import static MummyMaze.EnemyType.REDMUMMY;
import static MummyMaze.EnemyType.SCORPION;

public class Enemy {
    private EnemyType tipoInimigo = EnemyType.WHITEMUMMY;

    private int lineHeroi;
    private int columHeroi;


    private int lineMumia;
    private int columMumia;
    private char[][] matrix;

    public Enemy(EnemyType tipoInimigo,  int lineMumia, int columMumia) {
        this.tipoInimigo = tipoInimigo;
        this.lineHeroi = mummyMazeState.getLineHeroi(); //mudar
        this.columHeroi = mummyMazeState.getColumHeroi(); //mudar
        this.lineMumia = lineMumia;
        this.columMumia = columMumia;

    }

    public void move(MummyMazeState mummyMazeState) {

        if (canMove()) {
            if(matrix[columHeroi] == matrix[columMumia] && mummyMazeState.cannotMove()){ //se estiver na coluna, mexe na linha
                matrix[lineMumia - 2][columMumia] = matrix[lineMumia][columMumia];
                matrix[lineMumia][columMumia] = '.';
                lineMumia = lineMumia -2;
            }else {
                matrix[lineMumia][columMumia - 4] = matrix[lineMumia][columMumia];
                matrix[lineMumia][columMumia] = '.';
                columMumia = columMumia -4;
            }
        }
    }

    private boolean canMove() { //mas este não recebe o state
        switch (tipoInimigo) {
            case WHITEMUMMY:
                if(lineMumia > 1) {
                    if (matrix[columHeroi] == matrix[columMumia]) { //move para cima/baixo
                        if (matrix[lineMumia - 2][columMumia] == '.' && matrix[lineMumia - 1][columMumia] != '-'
                                && matrix[lineMumia - 1][columMumia] != '|' && matrix[lineMumia + 2][columMumia] == '.'
                                && matrix[lineMumia + 1][columMumia] != '-'
                                && matrix[lineMumia + 1][columMumia] != '|'
                                || (matrix[lineMumia + 2][columMumia] == 'H' //não sei se dará assim
                                || matrix[lineMumia - 2][columMumia] == 'H')) { //caso ele esteja perto, vai ter com o heroi
                            return true;
                        }
                    } else { //move para os lados
                        if (matrix[lineMumia][columMumia - 2] == '.' && matrix[lineMumia][columMumia - 1] != '-'
                                && matrix[lineMumia][columMumia - 1] != '|' && matrix[lineMumia][columMumia + 2] == '.'
                                && matrix[lineMumia][columMumia + 1] != '-'
                                && matrix[lineMumia][columMumia + 1] != '|') {
                            return true;
                        }
                    }
                }
                break;
            case REDMUMMY:
                break;
            case SCORPION:
                break;
        }
        return false; //caso ninguém se possa mover
    }


    public int getLineMumia() {
        return lineMumia;
    }

    public int getColumMumia() {
        return columMumia;
    }

}
