
import java.util.*;

public class EpuzzleSearch extends Search {
    
    private int[][] target;
    private int[][] puzzle;

    public EpuzzleSearch(int[][] p, int[][] tar) {
        puzzle = p;
        target = tar;
    }

    public int[][] getPuzzle() {
        return puzzle;
    }

    public int[][] getTarget() {
        return target;
    }
}