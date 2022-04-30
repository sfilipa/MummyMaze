package MummyMaze;

public class Enemy {
    private EnemyType mumiaBranca = EnemyType.WHITEMUMMY;
    private EnemyType mumiaVermelha = EnemyType.REDMUMMY;

    private int lineHeroi;
    private int columHeroi;

    private int lineMumiaBranca;
    private int columnMumiaBranca;
    private int lineMumiaVermelha;
    private int columnMumiaVermelha;
    private EnemyType tipoInimigo;
    private char[][] matrix;

    public Enemy(EnemyType tipoInimigo, int lineEnemy, int columnEnemy) {
        //this.mumiaBranca = tipoInimigo;
        this.tipoInimigo = tipoInimigo;
        this.lineMumiaBranca = lineEnemy;
        this.columnMumiaBranca = columnEnemy;
        this.lineMumiaVermelha = lineEnemy;
        this.columnMumiaVermelha = columnEnemy;
        this.tipoInimigo = tipoInimigo;
    }

    public void move(MummyMazeState mummyMazeState) {
        this.lineHeroi = mummyMazeState.getLineHeroi();
        this.columHeroi = mummyMazeState.getColumHeroi();
        this.matrix = mummyMazeState.getMatrix();

        if(mummyMazeState.getNaoMexeu() == 2){//PERGUNTAR AO STOR
            movimentosMumiaBranca();
            mummyMazeState.setNaoMexeu(0);
        }else {
            movimentosMumiaBranca();
            mummyMazeState.firePuzzleChanged(null);
            movimentosMumiaBranca();
        }

       /* mummyMazeState.firePuzzleChanged(null);
        movimentosMumiaVermelha();
        mummyMazeState.firePuzzleChanged(null);*/
        //mummyMazeState.setMatrix(matrix);//para atualizar a matrix do outro lado
    }

    public void movimentosMumiaBranca(){
        switch (canMove()){//apenas faz os movimentos
            case "nao":
                matrix[lineMumiaBranca][columnMumiaBranca] = matrix[lineMumiaBranca][columnMumiaBranca];
                matrix[lineMumiaBranca][columnMumiaBranca] = 'M';
                break;
            case "cima":
                matrix[lineMumiaBranca - 2][columnMumiaBranca] = matrix[lineMumiaBranca][columnMumiaBranca];
                matrix[lineMumiaBranca][columnMumiaBranca] = '.';
                lineMumiaBranca = lineMumiaBranca -2;
                break;
            case "baixo":
                matrix[lineMumiaBranca + 2][columnMumiaBranca] = matrix[lineMumiaBranca][columnMumiaBranca];
                matrix[lineMumiaBranca][columnMumiaBranca] = '.';
                lineMumiaBranca = lineMumiaBranca + 2;
                break;
            case "esq":
                matrix[lineMumiaBranca][columnMumiaBranca -2] = matrix[lineMumiaBranca][columnMumiaBranca];
                matrix[lineMumiaBranca][columnMumiaBranca] = '.';
                columnMumiaBranca = columnMumiaBranca - 2;
                break;
            case "dir":
                matrix[lineMumiaBranca][columnMumiaBranca + 2] = matrix[lineMumiaBranca][columnMumiaBranca];
                matrix[lineMumiaBranca][columnMumiaBranca] = '.';
                columnMumiaBranca = columnMumiaBranca + 2;
                break;
        }
    }

    public void movimentosMumiaVermelha(){
        switch (canMove()){//apenas faz os movimentos
            case "nao":
                matrix[lineMumiaVermelha][columnMumiaVermelha] = matrix[lineMumiaVermelha][columnMumiaVermelha];
                matrix[lineMumiaVermelha][columnMumiaVermelha] = 'V';
                break;
            case "cima":
                matrix[lineMumiaVermelha][columnMumiaVermelha - 2] = matrix[lineMumiaVermelha][columnMumiaVermelha];
                matrix[lineMumiaVermelha][columnMumiaVermelha] = '.';
                columnMumiaVermelha = columnMumiaVermelha - 2;
                break;
            case "baixo":
                matrix[lineMumiaVermelha][columnMumiaVermelha + 2] = matrix[lineMumiaVermelha][columnMumiaVermelha];
                matrix[lineMumiaVermelha][columnMumiaVermelha] = '.';
                columnMumiaVermelha = columnMumiaVermelha + 2;
                break;
            case "esq":
                matrix[lineMumiaVermelha - 2][columnMumiaVermelha] = matrix[lineMumiaVermelha][columnMumiaVermelha];
                matrix[lineMumiaVermelha][columnMumiaVermelha] = '.';
                lineMumiaVermelha = lineMumiaVermelha - 2;
                break;
            case "dir":
                matrix[lineMumiaVermelha + 2][columnMumiaVermelha] = matrix[lineMumiaVermelha][columnMumiaVermelha];
                matrix[lineMumiaVermelha][columnMumiaVermelha] = '.';
                lineMumiaVermelha = lineMumiaVermelha + 2;
                break;
            /*case "morreu":
                matrix[lineMumiaVermelha][columnMumiaVermelha] = 'M';
                break;*/
        }
    }

    private String canMove() { //mas este não recebe o state
        switch (tipoInimigo) {
            case WHITEMUMMY:
                    if (columHeroi == columnMumiaBranca) {//se a coluna do heroi for a mesma que a da mumia
                        if (lineMumiaBranca > lineHeroi) {//se a linha da mumia tiver a baixo da do heroi, move-se para cima
                            if (lineMumiaBranca > 1) {
                                if (matrix[lineMumiaBranca - 2][columnMumiaBranca] == '.' && matrix[lineMumiaBranca - 1][columnMumiaBranca] != '-') {
                                    return "cima";
                                }
                            }
                        } else {//se a linha da mumia tiver a cima da do heroi, move-se para baixo
                            if (lineMumiaBranca != matrix.length - 2) {
                                if (matrix[lineMumiaBranca + 2][columnMumiaBranca] == '.' && matrix[lineMumiaBranca + 1][columnMumiaBranca] != '-') {
                                    return "baixo";
                                }
                            }
                        }
                        if (lineHeroi == lineMumiaBranca) {//Ao chegar aqui, ja apanhou o heroi, pq esta na mesma linha e coluna dele
                            return "nao";
                        }
                    } else {//se a coluna do heroi não for a mesma que a da mumia
                        if (columnMumiaBranca > columHeroi) {//Se a mumia tiver á direita do heroi, tem de se mover para a esquerda
                            if (columnMumiaBranca > 1) {
                                if (matrix[lineMumiaBranca][columnMumiaBranca - 2] == '.' && matrix[lineMumiaBranca][columnMumiaBranca - 1] != '|') {
                                    return "esq";
                                }
                            }
                        } else {
                            if (columnMumiaBranca != matrix.length - 2) {//Se a mumia tiver á esquerda do heroi, tem de se mover para a direita
                                if (matrix[lineMumiaBranca][columnMumiaBranca + 2] == '.' && matrix[lineMumiaBranca][columnMumiaBranca + 1] != '|') {
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
                if (lineHeroi == lineMumiaVermelha) {//se a coluna do heroi for a mesma que a da mumia
                    if (columnMumiaVermelha > columHeroi) {//se a linha da mumia tiver a baixo da do heroi, move-se para cima
                        if (columnMumiaVermelha > 1) {
                            if (matrix[lineMumiaVermelha][columnMumiaVermelha - 2] == '.' && matrix[lineMumiaVermelha][columnMumiaVermelha - 1] != '-') {
                                return "cima";
                            }
                        }
                    } else {//se a linha da mumia tiver a cima da do heroi, move-se para baixo
                        if (columnMumiaVermelha != matrix.length - 2) {
                            if (matrix[lineMumiaVermelha][columnMumiaVermelha + 2] == '.' && matrix[lineMumiaVermelha][columnMumiaVermelha + 2] != '-') {
                                return "baixo";
                            }
                        }
                    }
                    if (lineHeroi == lineMumiaVermelha) {//Ao chegar aqui, ja apanhou o heroi, pq esta na mesma linha e coluna dele
                        return "nao";
                    }
                } else {//se a coluna do heroi não for a mesma que a da mumia
                    if (lineMumiaVermelha > lineHeroi) {//Se a mumia tiver á direita do heroi, tem de se mover para a esquerda
                        if (lineMumiaVermelha > 1) {
                            if (matrix[lineMumiaVermelha][columnMumiaVermelha - 2] == '.' && matrix[lineMumiaVermelha][columnMumiaVermelha - 1] != '|') {
                                return "esq";
                            }
                        }
                    } else {
                        if (columnMumiaVermelha != matrix.length - 2) {//Se a mumia tiver á esquerda do heroi, tem de se mover para a direita
                            if (matrix[lineMumiaVermelha][columnMumiaVermelha + 2] == '.' && matrix[lineMumiaVermelha][columnMumiaVermelha + 1] != '|') {
                                return "dir";
                            }
                        }
                    }
                }
                break;
            case SCORPION:
                break;
        }
        return "nao"; //caso ninguém se possa mover
    }


    public int getLineMumiaBranca() {
        return lineMumiaBranca;
    }

    public int getColumnMumiaBranca() {
        return columnMumiaBranca;
    }

    public int getLineMumiaVermelha() {
        return lineMumiaVermelha;
    }

    public int getColumnMumiaVermelha() {
        return columnMumiaVermelha;
    }
}
