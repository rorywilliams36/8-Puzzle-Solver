
import java.util.*;

public class EpuzzleSearch extends Search {
    
    private int[][] target;
    private int[][] puzzle;
    private String strat;

    // Puzzle = current state
    // Target = goal 
    // strat = method to calculate costs
    public EpuzzleSearch(int[][] p, int[][] tar, String s) {
        puzzle = p;
        target = tar;
        strat = s;
    }

    public int[][] getPuzzle() {
        return puzzle;
    }

    public int[][] getTarget() {
        return target;
    }

    public String getStrategy() {
        return strat;
    }
}