import java.io.IOException;
import java.util.Scanner;

class Player {
    private final char marker;

    Player(char marker) {
        this.marker = marker;
    }

    public char getMarker() {
        return marker;
    }
}

public class GameBoard {
    private final char[][] board = new char[3][3];
    private Player playerA;
    private Player playerB;
    private char currentPlayerMarker;

    public GameBoard() {
        createBoard();
    }

    public void createBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '-';
            }
        }
    }

    public void printBoard() {
        System.out.println("-------------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println();
            System.out.println("-------------");
        }
    }

    public boolean checkWinLogic() {
        boolean winner = false;
        if (checkRowCol(board[0][0], board[1][1], board[2][2]) || (checkRowCol(board[0][2], board[1][1], board[2][0]))) {
            System.out.println("We have a winner");
            winner = true;
        } else {
            for (int i = 0; i < 3; i++) {
                if (checkRowCol(board[i][0], board[i][1], board[i][2]) || checkRowCol(board[0][i], board[1][i], board[2][i])) {
                    System.out.println("We have a winner");
                    winner = true;
                }
            }
        }
        return winner;
    }

    private boolean checkRowCol(char c1, char c2, char c3) {
        return ((c1 != '-') && (c1 == c2) && (c2 == c3));
    }

    public boolean placeMarker(int row, int col) {
        if ((row >= 0) && (row < 3)) {
            if ((col >= 0) && (col < 3)) {
                if (board[row][col] == '-') {
                    board[row][col] = currentPlayerMarker;
                    return true;
                }
            }
        }
        return false;
    }

    public void changePlayer() {
        if (currentPlayerMarker == 'x') {
            currentPlayerMarker = playerB.getMarker();
        } else {
            currentPlayerMarker = playerA.getMarker();
        }
        System.out.println("\ncurrent player is " + currentPlayerMarker);
    }

    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ignored) {}
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GameBoard gameBoard = new GameBoard();

        System.out.print("Enter the marker for player 1: ");
        char marker1 = scanner.next().charAt(0);
        gameBoard.playerA = new Player(marker1);
        System.out.print("Enter the marker for player 2: ");
        char marker2 = scanner.next().charAt(0);
        gameBoard.playerB = new Player(marker2);
        while (!gameBoard.checkWinLogic()) {
            gameBoard.changePlayer();
            int row = scanner.nextInt();
            int col = scanner.nextInt();
            gameBoard.placeMarker(row, col);
            clearScreen();
            gameBoard.printBoard();
        }
        scanner.close();
    }
}