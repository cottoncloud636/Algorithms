/*
* Input: A set of n invervals, each interval i with Si, Fi and Wi, where Si < Fi and Wi>0
  Output: A subset of non-overlapping intervals that maximizes the total weight
* Requirement:  
  Generate 20 intervals the start time of each is a random integer from 1 to 20,
  the finish time is its start time plus a random value from 5 to 15, and the weight is a random integer from 1 to 9.
* Algorithm:
  1. Order intervals by finish time (whichever finish earliest to latest) and relabel them, so that F1<=F2<=F3...Fn
  2. For each interval #j, we say P(j)=max{i<j; i and j don't overlap}
  3. Initialize: OPT(0)=0 and OPT(1)=W1
     For j=2 to n, OPT(j)=max of either  1) Wj+OPT(P(j)), if interval j is included in optimal solution
                                   or    2) OPT(j-1), if interval j is not included in optimal solution
  4. selectedT=[]
     j=n
     while (j!=0)
        if (OPT(j)=OPT(j-1)) j=j-1
        else add j to selectedT, j=P(j)
     End while
     output OPT(j), selectedT
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Weighted_Interval_Scheduling {
  private static class intervalJob implements Comparable<intervalJob> { // create a class for the interval job
    int intervalStart, intervalFinish, weight;// attributes of each intervalJob

    public intervalJob(int intervalStart, int intervalFinish, int weight) {// constructor
      this.intervalStart = intervalStart;// this intervalStart declared in class intervalJob
      this.intervalFinish = intervalFinish;
      this.weight = weight;
    }

    /* 
     * Algorithm: 1. Order intervals by finish time (whichever finish earliest to latest) and relabel them, so that F1<=F2<=F3...Fn 
     */
    @Override
    public int compareTo(intervalJob that) {
      return this.intervalFinish - that.intervalFinish; // if return negative, means this interval finishes earlier than that interval
    }
  }

  public static void main(String[] args) {
    List<intervalJob> intervals = new ArrayList<>();// create an array list to hold 20 intervals
    Random random = new Random();
    int intervalStart, intervalFinish, weight;
    intervals.add(0, null);
    intervals.set(0, new intervalJob(0,0,0)); //set the first elment of arrayList from null value to 0,0,0 to avoid nullponter exception
    for (int i = 1; i <= 20; i++) {
      intervalStart = random.nextInt(20) + 1; // nextInt(20) returns 0 to 19
      intervalFinish = intervalStart + random.nextInt(11) + 5;// nextInt(11) returns 0 to 10
      weight = random.nextInt(9) + 1;// nextInt(8) returns 0 to 8
      intervals.add(i, new intervalJob(intervalStart, intervalFinish, weight));// create intervalJob object, pass in the
                                                                             // 3 values then add this object to arraylist                                                               
    }
 
    Comparator<intervalJob> compare = new Comparator<intervalJob>() {
      public int compare(intervalJob No1, intervalJob No2) {
          if (No1 == null) {
              return 1;
          } else if (No2 == null) {
              return -1;
          } else {
              return No1.compareTo(No2);
          }
      }
    };
    
    Collections.sort(intervals, compare); // sort the intervals by finish time, based on the previous compareTo testing condition

    /*
     * Algorithm: 2. For each interval #j, we say P(j)=max{i<j; i and j don't overlap}
     */
    int jobCounts = intervals.size();
    int[] P = new int[jobCounts];

    for (int j = 1; j < jobCounts; j++) { // loop through each interval
      int i = j - 1; // i is the closest job# than before j which don't overlap with j
      while (i > 0 && intervals.get(i).intervalFinish > intervals.get(j).intervalStart) {
          i--; // under the condition of i>0, when Fi>Sj, means i and j overlaps,then go to
           // the previous previous job i, we compare again to see this previous previous i
             // overlaps with j, until find an i doesn't overlap with j, we get out of while, assign this i to P(j)
        } 
      P[j] = i;
    }
      

    /*
     * Algorithm: 3. Initialize: OPT(0)=0 and OPT(1)=W1
     * For j=2 to n, OPT(j)=max of either 1) Wj+OPT(P(j)), if interval j is included in optimal solution
     * or 2) OPT(j-1), if interval j is not included in optimal solution
     */
    int[] OPT = new int[jobCounts]; 
    OPT[0] = 0;
    OPT[1] = intervals.get(1).weight;
    for (int j = 2; j < jobCounts; j++) {
      int includeJ = intervals.get(j).weight + OPT[P[j]];
      int excludeJ = OPT[j-1];
      OPT[j] = Math.max(includeJ, excludeJ);
    }

    /*
     * Algorithm: 4. selectedT=[]
     * j=n
     * while (j!=0)
     * if (OPT(j)=OPT(j-1)) j=j-1
     * else add j to selectedT, j=P(j)
     * End while
     * output OPT(j), selectedT
     */
    List<intervalJob> selectedT = new ArrayList<>();
    int j = jobCounts-1; 
    while (j>0){
      if (OPT[j]==OPT[j-1]) 
        j--; 
        else {
          selectedT.add(intervals.get(j));
          j=P[j]; 
      }
    }
    Collections.reverse(selectedT);
    
    System.out.println("Entire Intervals are as follows: ");
    for(intervalJob tasks : intervals){ //loop each element in selectedT, assign each element to "tasks" variable
                                       //The loop body use "tasks" variable to access attributes or methods in the list declared earlier
    System.out.println("Interval starts at time: "+tasks.intervalStart + ", Interval finishes at time: " + tasks.intervalFinish + ", Interval's weight is: " + tasks.weight);
  }
    System.out.println("------------------------------------------------------");

    System.out.println("Selected intervals are: ");
    for (intervalJob selected : selectedT) {
        System.out.println("SelectedT starts at time: "+selected.intervalStart + ", SelectedT finishes at time: " + selected.intervalFinish + ", SelectedT's weight is: " + selected.weight);
      }
    System.out.println("------------------------------------------------------");

    System.out.println("Optimal weight is: " + OPT[jobCounts-1]);

  }

}