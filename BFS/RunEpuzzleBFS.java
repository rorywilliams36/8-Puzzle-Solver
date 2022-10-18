
import java.util.*;

public class RunEpuzzleBFS {
    public static void main(String[] args) {
        
        int seed = 123450;
        EpuzzGen gen = new EpuzzGen(seed);

        int d = 12;
        int[][] p = gen.puzzGen(d);
        
        int[][] p1 = {{1,0,3}, {4,2,6},{7,5,8}};
        int[][] p2 = {{4,1,3},{7,2,5},{0,8,6}};
        int[][] p3 = {{2,3,6},{1,5,8},{4,7,0}};
        int[][] target = {{1,2,3},{4,5,6},{7,8,0}};

        EpuzzleSearch searcher = new EpuzzleSearch(p1, target);
        SearchState initState = (SearchState) new EpuzzleState(p1);

        String status = searcher.runSearch(initState, "BFS");
        System.out.println(status);
    }
}