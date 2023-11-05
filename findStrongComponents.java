import java.util.*;

public class findStrongComponents {

    public void BFS(int BFS_startV, int[][] graph, boolean[] visitedV, Deque<Integer> stack) {
        visitedV[BFS_startV] = true; // after pick a V, put it in visited array
        Queue<Integer> queue = new Linkedlist<>();// then put this V in the queue, so that it will be used for exploring
                                                  // its adjacent Vs
        // use linked list to implement Queue interface. Queue is also used to display
        // all visited Vs
        queue.add(BFS_startV);
        while (!queue.isEmpty()) {
            int removedV = queue.remove(BFS_startV); // pop out a V from queue and start exloring its adjacent Vs in
                                                     // matrix
            for (int i = 0; i < graph[removedV].length; i++) {// i is the column#, now i point to the row that the
                                                              // removedV is located,
                                                              // loop the entire row
                if (graph[removedV][i] == 1 && visitedV[i] == false) { // if this removedV from queue is adjacent with
                                                                       // vertex i,
                    // AND vertex i not been visited
                    visitedV[i] = true; // then add this V to visited array
                    queue.add(i);
                }
            }
            stack.push(removedV);// store the removedV (also row# in matrix) in a stack, last V in will be first
                                 // V to pop
        }
    }

    // While going through DFS Vs, use a List to store the Vs that went through.
    // Because in DFS, as we go though Vs, they can form a path
    public void DFS(int DFS_startV, int[][] graph, boolean[] visitedV, List<Integer> strongComponents) {
        visitedV[DFS_startV] = true;
        // strong components code go in
        // here??????????????????????????????????????????????????????????????
        for (int i = 0; i < graph[DFS_startV].length; i++) {// i is the column#, also is the adjacent V of DFS_startV
            if (graph[DFS_startV][i] == 1 && visitedV[i] == false) {
                DFS(i, graph, visitedV, strongComponents); // pass in V that adjacent to DFS_startV as a parameter to
                                                           // replace DFS_startV,
                                                           // recursively call DFS so that in matrix, it goes to next
                                                           // row, as in graph,it goes
                                                           // to the next level of V, rather than go to DFS_startV's
                                                           // another adjacent V if any

            }
        }
    }

    public int[][] reversedGraph(int[][] graph) {// to reverse a graph means to transpose, that is switch the row and
                                                 // column in matrix
        int[][] revGraph = new int[graph[0].length][graph.length];// need a new graph object to store the reversed
                                                                  // graph, its row is original graph's col, col is
                                                                  // original graph's row
        for (int row = 0; row < graph.length; row++) {
            for (int col = 0; col < graph[0].lenght; col++) {
                revGraph[col][row] = graph[row][col];// this 2D for loop is based on orginal graph, so it starts with
                                                     // original graph's row first, make sense to let
                                                     // revGraph[col][row]=graph[row][col]
            }
        }
        return revGraph;
    }

    public void traversing() {

    }

    public static void main(String[] args) {
        int[][] digraph = { { 0, 1, 0, 0, 0, 1 },
                { 1, 0, 1, 0, 1, 0 },
                { 0, 0, 0, 1, 1, 0 },
                { 0, 0, 0, 0, 1, 0 },
                { 0, 0, 1, 0, 0, 0 },
                { 1, 0, 0, 0, 1, 0 }
        };

        System.out.println("Hello World");
    }
}
