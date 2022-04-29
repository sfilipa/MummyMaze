package MummyMaze;

public class Enemy {
    private EnemyType tipoInimigo = EnemyType.WHITEMUMMY;

    private int lineHeroi;
    private int columHeroi;

    private int lineMumiaBranca;
    private int columMumiaBranca;
    private char[][] matrix;

    public Enemy(EnemyType tipoInimigo,  int lineMumia, int columMumia) {
        this.tipoInimigo = tipoInimigo;
        this.lineMumiaBranca = lineMumia;
        this.columMumiaBranca = columMumia;
    }

    public void move(MummyMazeState mummyMazeState) {
        this.lineHeroi = mummyMazeState.getLineHeroi();
        this.columHeroi = mummyMazeState.getColumHeroi();
        this.matrix = mummyMazeState.getMatrix();

        if(mummyMazeState.getNaoMexeu() == 2){//PERGUNTAR AO STOR
            movimentosMumia();
            mummyMazeState.setNaoMexeu(0);
        }else {
            movimentosMumia();
            mummyMazeState.firePuzzleChanged(null);
            movimentosMumia();
        }
        //mummyMazeState.setMatrix(matrix);//para atualizar a matrix do outro lado
    }

    public void movimentosMumia(){
        switch (canMove()){//apenas faz os movimentos
            case "nao":
                matrix[lineMumiaBranca][columMumiaBranca] = matrix[lineMumiaBranca][columMumiaBranca];
                matrix[lineMumiaBranca][columMumiaBranca] = 'M';
                break;
            case "cima":
                matrix[lineMumiaBranca - 2][columMumiaBranca] = matrix[lineMumiaBranca][columMumiaBranca];
                matrix[lineMumiaBranca][columMumiaBranca] = '.';
                lineMumiaBranca = lineMumiaBranca -2;
                break;
            case "baixo":
                matrix[lineMumiaBranca + 2][columMumiaBranca] = matrix[lineMumiaBranca][columMumiaBranca];
                matrix[lineMumiaBranca][columMumiaBranca] = '.';
                lineMumiaBranca = lineMumiaBranca + 2;
                break;
            case "esq":
                matrix[lineMumiaBranca][columMumiaBranca -2] = matrix[lineMumiaBranca][columMumiaBranca];
                matrix[lineMumiaBranca][columMumiaBranca] = '.';
                columMumiaBranca = columMumiaBranca - 2;
                break;
            case "dir":
                matrix[lineMumiaBranca][columMumiaBranca + 2] = matrix[lineMumiaBranca][columMumiaBranca];
                matrix[lineMumiaBranca][columMumiaBranca] = '.';
                columMumiaBranca = columMumiaBranca + 2;
                break;
        }
    }

    private String canMove() { //mas este não recebe o state
        switch (tipoInimigo) {
            case WHITEMUMMY:
                    if (columHeroi == columMumiaBranca) {//se a coluna do heroi for a mesma que a da mumia
                        if (lineMumiaBranca > lineHeroi) {//se a linha da mumia tiver a baixo da do heroi, move-se para cima
                            if (lineMumiaBranca > 1) {
                                if (matrix[lineMumiaBranca - 2][columMumiaBranca] == '.' && matrix[lineMumiaBranca - 1][columMumiaBranca] != '-') {
                                    return "cima";
                                }
                            }
                        } else {//se a linha da mumia tiver a cima da do heroi, move-se para baixo
                            if (lineMumiaBranca != matrix.length - 2) {
                                if (matrix[lineMumiaBranca + 2][columMumiaBranca] == '.' && matrix[lineMumiaBranca + 1][columMumiaBranca] != '-') {
                                    return "baixo";
                                }
                            }
                        }
                        if (lineHeroi == lineMumiaBranca) {//Ao chegar aqui, ja apanhou o heroi, pq esta na mesma linha e coluna dele
                            return "nao";
                        }
                    } else {//se a coluna do heroi não for a mesma que a da mumia
                        if (columMumiaBranca > columHeroi) {//Se a mumia tiver á direita do heroi, tem de se mover para a esquerda
                            if (columMumiaBranca > 1) {
                                if (matrix[lineMumiaBranca][columMumiaBranca - 2] == '.' && matrix[lineMumiaBranca][columMumiaBranca - 1] != '|') {
                                    return "esq";
                                }
                            }
                        } else {
                            if (columMumiaBranca != matrix.length - 2) {//Se a mumia tiver á esquerda do heroi, tem de se mover para a direita
                                if (matrix[lineMumiaBranca][columMumiaBranca + 2] == '.' && matrix[lineMumiaBranca][columMumiaBranca + 1] != '|') {
                                    return "dir";
                                }
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


    public int getLineMumiaBranca() {
        return lineMumiaBranca;
    }

    public int getColumMumiaBranca() {
        return columMumiaBranca;
    }

}
