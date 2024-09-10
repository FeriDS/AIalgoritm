import java.util.ArrayList;
import java.util.List;

class Board implements Ilayout, Cloneable {
    private static final int dim = 3;
    private int board[][];

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
    public List<Ilayout> children() {
        List<Ilayout> result = new ArrayList<>();
        //posições de onde se encontra o 0 na board
        int bufferi = 0;
        int bufferj = 0;
        //buffer para troca do 0 com outros para criação de criançãs
        int buffert = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (board[i][j] == 0) {
                    bufferj = j;
                    break;
                }
            }
            if (board[i][bufferj] == 0) {
                bufferi = i;
                break;
            }
        }
        /*
            Raciocinio na minha cabeça que queria escrever para lembrar
            Mas codigo HORRIVEL
            https://prnt.sc/vskaLRt0UW3j
         */
        if (bufferj == dim - 1) { //baixo
        }
        if (bufferj == 0) { //cima

        }
        if (bufferi == dim - 1) { //lado direito

        }
        if (bufferi == 0) { //lado esquerdo

        }
        if (bufferi > 0 && bufferi < dim -1) { //meio vertical

        }
        if (bufferj > 0 && bufferj < dim -1) { //meio horizontal

        }
        return List.of();
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
