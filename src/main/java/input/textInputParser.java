package input;

public class textInputParser {
    //helper function to parse standard chess notation like for the ascii board input
    public static int[] parseInput(String input) {


        //find collumn from standard chess notation like 'e2'
        char fileChar = input.charAt(0);
        int file = fileChar - 'a';       // in ascii: 'e'-'a'= (101)-(97) = 4
        
        // get the rank
        char rankChar = input.charAt(1);
        int rank = Character.getNumericValue(rankChar) - 1; // 2 - 1 = 1
        
        return new int[]{rank, file};

        
    }

}
