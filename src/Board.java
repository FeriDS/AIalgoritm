import java.util.ArrayList;
import java.util.List;

class Board implements Ilayout, Cloneable {
    private static final int dim = 3;
    private final int[][] board;

    public Board() {
        board = new int[dim][dim];
    }

    public Board(String str) throws IllegalStateException {
        if (str.length() != dim * dim) throw new
                IllegalStateException("Invalid arg in Board constructor");
        board = new int[dim][dim];
        int si = 0;
        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++)
                board[i][j] = Character.getNumericValue(str.charAt(si++));
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (board[i][j] == 0) result.append(" ");
                else result.append(board[i][j]);
            }
            result.append("\r\n");
        }
        return result.toString();
    }

    public boolean equals(Object o) {
        if (!(o instanceof Board)) return false;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (board[i][j] != ((Board) o).board[i][j]) return false;
            }
        }
       return true;
    }

    public int hashCode() {
// TO BE COMPLETED
        return board[0][0];
    }

    @Override
    protected Object clone(){
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                result.append(board[i][j]);
            }
        }
        return new Board(result.toString());
    }

    private Board moveUp(int row, int col) {
        Board result = (Board) this.clone();
        result.board[row][col] = board[row-1][col];
        result.board[row-1][col] = 0;
        return result;
    }
    private Board moveDown(int row, int col) {
        Board result = (Board) this.clone();
        result.board[row][col] = board[row+1][col];
        result.board[row+1][col] = 0;
        return result;
    }

    private Board moveLeft(int row, int col) {
        Board result = (Board) this.clone();
        result.board[row][col] = board[row][col-1];
        result.board[row][col-1] = 0;
        return result;
    }
    private Board moveRight(int row, int col) {
        Board result = (Board) this.clone();
        result.board[row][col] = board[row][col+1];
        result.board[row][col+1] = 0;
        return result;
    }

    @Override
    public List<Ilayout> children() {
        List<Ilayout> result = new ArrayList<>();
        //posições de onde se encontra o 0 na board
        int row = 0;
        int collum = 0;
        //buffer para troca do 0 com outros para criação de criançãs
        int buffert = 0;

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (board[i][j] == 0) {
                    collum = j;
                    break;
                }
            }
            if (board[i][collum] == 0) {
                row = i;
                break;
            }
        }
        /*
            Raciocinio na minha cabeça que queria escrever para lembrar.
            Mas codigo (not anymore)HORRIVEL (fixed melhor agora só falta as funcões de mover e clonar o novo)
            https://prnt.sc/vskaLRt0UW3j paint de eu a chegar ao raciocinio
         */
        if (row < dim - 1) { //move baixo
            result.add(this.moveDown(row, collum));

        }
        if (row > 0) { //move cima
            result.add(this.moveUp(row, collum));

        }
        if (collum < dim - 1) { //move right
            result.add(this.moveRight(row, collum));

        }
        if (collum > 0) { //move left
            result.add(this.moveLeft(row, collum));
        }
        return result;
    }

    @Override
    public boolean isGoal(Ilayout l) {
        return this.equals(l);
    }

    @Override
    public double getK() {
        return 0;
    }
}
