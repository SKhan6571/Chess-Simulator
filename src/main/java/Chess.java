import board.Board;
import renderer.BoardPrinter;
import java.util.Scanner;
import input.textInputParser;

public class Chess {
    public static void main(String[] args) {
        ///simple ascii board print test
        ////////USES DEPENDENCY INJECTION!!!!!!!!!!!!!!!
        Board board = new Board();
        BoardPrinter.printBoard(board);


        //GEt move input from user
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the piece to move (e.g., e2): ");
        String rawInput = scanner.nextLine();
        int[] pieceLocation = textInputParser.parseInput(rawInput);





        //close scanner to prevent errors
        scanner.close();
    }




    
}