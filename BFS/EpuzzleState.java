
import java.util.*;

public class EpuzzleState extends SearchState {
    private int[][] current; //Current puzzle/state


    public EpuzzleState(int[][] c) {
        current = c;
    }

    public int[][] getCurrent() {
        return current;
    }

    // deepEquals return true if arrays are identical
    // Checks state if current is the same as the goal
    boolean goalPredicate(Search searcher) {
        EpuzzleSearch EpuzzSearcher = (EpuzzleSearch) searcher;
        int[][] target = EpuzzSearcher.getTarget();
        return (Arrays.deepEquals(target, current));
    }

    // Gets list of successors (every move for the current state)
    ArrayList<SearchState> getSuccessors(Search searcher) {
        EpuzzleSearch EpuzzSearcher = (EpuzzleSearch) searcher;
        int[][] puzz = EpuzzSearcher.getPuzzle();

       // System.out.println("Getting successors for: " + puzz.toString());
        ArrayList<EpuzzleState> puzzStateList = new ArrayList<EpuzzleState>();
        ArrayList<SearchState> searchStateList = new ArrayList<SearchState>();

        // Loops through current state then performs moves based on position of 0
        // temporay value storing item being swapped with the space
        int temp = 0;
         for (int r=0; r<current.length; r++) {
            for (int c=0; c<current[r].length; c++){
                if (current[r][c] == 0) {
                    // If there are tiles to right swaps space with right tile
                    if (c < 2) {
                        int[][] copy = copyArray(current);
                        temp = copy[r][c+1];
                        copy[r][c+1] = 0;
                        copy[r][c] = temp;
                        puzzStateList.add(new EpuzzleState(copy));
                    }

                    // Swaps space with left tile
                    if (c > 0) {
                        int[][] copy = copyArray(current);
                        temp = copy[r][c-1];
                        copy[r][c-1] = 0;
                        copy[r][c] = temp;
                        puzzStateList.add(new EpuzzleState(copy));
                    }

                    // Swaps space with tile above
                    if (r < 2) {
                        int[][] copy = copyArray(current);
                        temp = copy[r+1][c];
                        copy[r+1][c] = 0;
                        copy[r][c] = temp;
                        puzzStateList.add(new EpuzzleState(copy));
                    }

                    // Swaps space with tile below
                    if (r > 0) {
                        int[][] copy = copyArray(current);
                        temp = copy[r-1][c];
                        copy[r-1][c] = 0;
                        copy[r][c] = temp;
                        puzzStateList.add(new EpuzzleState(copy));
                    }
                }
            }
        }

        // Converts states in EpuzzleState list to searchStates
        for (EpuzzleState s : puzzStateList) {
            searchStateList.add((SearchState) s);
        }

        return searchStateList;
    
    }

    // Checks closed if current is the same as another
    boolean sameState(SearchState n2) {
        EpuzzleState pn2 = (EpuzzleState) n2;
        return Arrays.deepEquals(pn2.getCurrent(), current);
    }

    // Converts current state to string
    public String toString() {
        String s = "\n";
        for (int r=0; r<current.length; r++) {
            for (int c=0; c<current[r].length; c++) {
                s += current[r][c] + " ";
            }
            s += " \n";
        }
        return s;
    }

    // Creates a copy of the current state
    public int[][] copyArray(int[][] p) {
        int[][] copy = new int[3][3];
        for (int i=0; i<p.length; i++) {
            for (int j=0; j<p[i].length; j++) {
                copy[i][j] = p[i][j];
            }
        }

        return copy;
    }
}