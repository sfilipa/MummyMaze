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
        this.lineMumia = lineMumia;
        this.columMumia = columMumia;
    }

    public void move(MummyMazeState mummyMazeState) {
        this.lineHeroi = mummyMazeState.getLineHeroi();
        this.columHeroi = mummyMazeState.getColumHeroi();
        this.matrix = mummyMazeState.getMatrix();
        switch (canMove()){//apenas faz os movimentos
            case "nao":
                matrix[lineMumia][columMumia] = matrix[lineMumia][columMumia];
                matrix[lineMumia][columMumia] = 'M';
                break;
            case "cima":
                matrix[lineMumia - 2][columMumia] = matrix[lineMumia][columMumia];
                matrix[lineMumia][columMumia] = '.';
                lineMumia = lineMumia -2;
                break;
            case "baixo":
                matrix[lineMumia + 2][columMumia] = matrix[lineMumia][columMumia];
                matrix[lineMumia][columMumia] = '.';
                lineMumia = lineMumia + 2;
                break;
            case "esq":
                matrix[lineMumia][columMumia-2] = matrix[lineMumia][columMumia];
                matrix[lineMumia][columMumia] = '.';
                columMumia = columMumia - 2;
                break;
            case "dir":
                matrix[lineMumia][columMumia + 2] = matrix[lineMumia][columMumia];
                matrix[lineMumia][columMumia] = '.';
                columMumia = columMumia + 2;
                break;
        }
        mummyMazeState.setMatrix(matrix);//para atualizar a matrix do outro lado
    }

    private String canMove() { //mas este não recebe o state
        switch (tipoInimigo) {
            case WHITEMUMMY:
                    if (columHeroi == columMumia) {//se a coluna do heroi for a mesma que a da mumia
                        System.out.println("ESTÃO NA MESMA COLUNA");
                        if(lineMumia > lineHeroi) {
                            if(lineMumia > 1) {//se a linha da mumia tiver a baixo da do heroi, move-se para cima
                                if (matrix[lineMumia - 2][columMumia] == '.' && matrix[lineMumia - 1][columMumia] != '-') {
                                    return "cima";
                                }
                            }
                        }
                        if(lineMumia < lineHeroi) {//se a linha da mumia tiver a cima da do heroi, move-se para baixo
                            if(columMumia > 1) {
                                if (matrix[lineMumia + 2][columMumia] == '.' && matrix[lineMumia + 1][columMumia] != '-') {
                                    return "baixo";
                                }
                            }
                        }
                        if (lineHeroi == lineMumia) {//Ao chegar aqui, ja apanhou o heroi, pq esta na mesma linha e coluna dele
                            return "nao";
                        }
                    } else {//se a coluna do heroi não for a mesma que a da mumia
                        if(columMumia > columHeroi){//Se a mumia tiver á direita do heroi, tem de se mover para a esquerda
                            if(lineMumia != matrix.length - 2) {
                                if (matrix[lineMumia][columMumia - 2] == '.' && matrix[lineMumia][columMumia - 1] != '|') {
                                    return "esq";
                                }
                            }
                        }
                        if(columMumia != matrix.length - 2) {//Se a mumia tiver á esquerda do heroi, tem de se mover para a direita
                            if (matrix[lineMumia][columMumia + 2] == '.' && matrix[lineMumia][columMumia + 1] != '|') {
                                return "dir";
                            }
                        }
                    }
                break;
            case REDMUMMY:
                break;
            case SCORPION:
                break;
        }
        return "nao"; //caso ninguém se possa mover
    }


    public int getLineMumia() {
        return lineMumia;
    }

    public int getColumMumia() {
        return columMumia;
    }

}
