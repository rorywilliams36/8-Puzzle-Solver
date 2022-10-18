
import java.util.*;

public class EpuzzleState extends SearchState {
    private int[][] current; //Current puzzle/state

    public EpuzzleState(int[][] c, int g, int est) {
        current = c;
        localCost = g;
        estRemCost = est;
    }

    public int[][] getCurrent() {
        return current;
    }

    public int getLocalCost() {
        return localCost;
    }

    public int getEstRemCost() {
        return estRemCost;
    }

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
        String strat = EpuzzSearcher.getStrategy();

        EpuzzGen gen = new EpuzzGen();
        int[][] target = gen.tar;

       // System.out.println("Getting successors for: " + puzz.toString());

        ArrayList<EpuzzleState> puzzStateList = new ArrayList<EpuzzleState>();
        ArrayList<SearchState> searchStateList = new ArrayList<SearchState>();

        // Stores value of item being swapped with the space
        int temp = 0;

        // Swaps tiles depending on position of 0
        for (int r=0; r<current.length; r++) {
            for (int c=0; c<current[r].length; c++){
                // checks if current tile is empty
                if (current[r][c] == 0) {
                    // If there are items to right swaps space with right tile
                    if (c < 2) {
                        int[][] copy = copyArray(current);
                        temp = copy[r][c+1];
                        copy[r][c+1] = 0;
                        copy[r][c] = temp;
                        puzzStateList.add(new EpuzzleState(copy, localCost, calculateEstRemCost(strat, copy, target)));
                    }

                    // Swaps space with left tile
                    if (c > 0) {
                        int[][] copy = copyArray(current);
                        temp = copy[r][c-1];
                        copy[r][c-1] = 0;
                        copy[r][c] = temp;
                        puzzStateList.add(new EpuzzleState(copy, localCost, calculateEstRemCost(strat, copy, target)));
                    }

                    // Swaps space with tile above
                    if (r < 2) {
                        int[][] copy = copyArray(current);
                        temp = copy[r+1][c];
                        copy[r+1][c] = 0;
                        copy[r][c] = temp;
                        puzzStateList.add(new EpuzzleState(copy, localCost, calculateEstRemCost(strat, copy, target)));
                    }

                    // Swaps space with tile below
                    if (r > 0) {
                        int[][] copy = copyArray(current);
                        temp = copy[r-1][c];
                        copy[r-1][c] = 0;
                        copy[r][c] = temp;
                        puzzStateList.add(new EpuzzleState(copy, localCost, calculateEstRemCost(strat, copy, target)));
                    }
                }
            }
        }

        // Converts EpuzzleState objects to SearchState
        for (EpuzzleState s : puzzStateList) {
            searchStateList.add((SearchState) s);
        }

        return searchStateList;
        
    }

    // Checks closed and removes current node if its same as a closed node
    boolean sameState(SearchState n2) {
        EpuzzleState pn2 = (EpuzzleState) n2;
        return (Arrays.deepEquals(pn2.getCurrent(), current));
    }

    public String toString() {
        String s = "\n";
        for (int r=0; r<current.length; r++) {
            for (int c=0; c<current[r].length; c++) {
                s += current[r][c] + " ";
            }
            s += "\n";
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

    // Calls function depending on the string strat
    public int calculateEstRemCost(String strat, int[][] state, int[][] target) {
        if (strat == "Hamming") {
            return hamming(state, target);
        }
        else {
            return manhattan(state, target);
        }
    }

    // Caluclates Hamming Distance
    // Displays how many numbers are in the wrong positions compared to target state
    private int hamming(int[][] state, int[][] target) {
        int hamDist = 0;
        for (int row=0; row<state.length; row++) {
            for (int col=0; col<state[row].length; col++) {
                if (state[row][col] != target[row][col]) {
                    hamDist +=1;
                }
            }
        }
        int inPlace = 9-hamDist;
        // System.out.println("Hamming Distance: " + hamDist + " (all out of place except " + inPlace + ")");

        return (hamDist);
    }

    // Works out manhattan distance (absolute difference between tile and target tile)
    // dist = dist + |x1 - x2| + |y1 - y2|
    private int manhattan(int[][] s, int[][] t) {
        int d = 0;
        int si = 0; // current state x (row)
        int sj = 0; // current state y (col)

        // n = correct value for each position
        for(int n = 0; n <= 8; ++n) {
            int i; // row 
            int j; // col
           
            // Loops through matrix ixj
            for(i = 0; i <= 2; ++i) {
                for(j = 0; j <= 2; ++j) {
                    // if value at ixj is same as the correct value (target)
                    // state ixj is changed to equal 0 below
                    if (s[i][j] == n) {
                        si = i; 
                        sj = j;
                    }
                }
            }


            for(i = 0; i <= 2; ++i) {
                for(j = 0; j <= 2; ++j) {
                    // When target element is matched correctly with n
                    if (t[i][j] == n) {
                        d = d + Math.abs(i - si) + Math.abs(j - sj); 
                    }
                }
            }
        }
  
        return d;
     }
}