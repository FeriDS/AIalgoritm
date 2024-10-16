import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@SuppressWarnings("all")
class Board implements Ilayout, Cloneable {
    private static final int dim = 3;
    private final int[][] board;

    public Board() {
        board = new int[dim][dim];
    }

    /**
     * Construtor da classe Board
     * @param str string com os parametros necessarios para construir uma Board
     * @throws IllegalStateException
     */
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

    @Override
    public Stack<Pair<Character, Integer>>[] getLayout() {
        return null;
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
        int result = 3;
        int prime = 7;

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                result = prime * result + this.board[i][j];
            }
        }
        return result;
    }

    @Override
    protected Object clone() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                result.append(board[i][j]);
            }
        }
        return new Board(result.toString());
    }

    /**
     * Função que move o 0 para cima
     * @param row inteiro que representa a linha onde ésta o 0
     * @param col inteiro que representa a coluna onde ésta o 0
     * @return umm deep clone da Board onde o 0 foi movimentado
     */
    private Board moveUp(int row, int col) {
        Board result = (Board) this.clone();
        result.board[row][col] = board[row-1][col];
        result.board[row-1][col] = 0;
        return result;
    }

    /**
     * Função que move o 0 para baixo
     * @param row inteiro que representa a linha onde ésta o 0
     * @param col inteiro que representa a coluna onde ésta o 0
     * @return umm deep clone da Board onde o 0 foi movimentado
     */
    private Board moveDown(int row, int col) {
        Board result = (Board) this.clone();
        result.board[row][col] = board[row+1][col];
        result.board[row+1][col] = 0;
        return result;
    }

    /**
     * Função que move o 0 para esquerda
     * @param row inteiro que representa a linha onde ésta o 0
     * @param col inteiro que representa a coluna onde ésta o 0
     * @return umm deep clone da Board onde o 0 foi movimentado
     */
    private Board moveLeft(int row, int col) {
        Board result = (Board) this.clone();
        result.board[row][col] = board[row][col-1];
        result.board[row][col-1] = 0;
        return result;
    }

    /**
     * Função que move o 0 para direita
     * @param row inteiro que representa a linha onde ésta o 0
     * @param col inteiro que representa a coluna onde ésta o 0
     * @return umm deep clone da Board onde o 0 foi movimentado
     */
    private Board moveRight(int row, int col) {
        Board result = (Board) this.clone();
        result.board[row][col] = board[row][col+1];
        result.board[row][col+1] = 0;
        return result;
    }

    /**
     * Função que faz todos os movimentos possiveis
     * @return uma lista de Ilayout(Board) com todos os movimentos
     */
    @Override
    public List<Ilayout> children() {
        List<Ilayout> result = new ArrayList<>();
        //posições de onde se encontra o 0 na board
        int row = 0;
        int collum = 0;

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
        return 1;
    }
}
