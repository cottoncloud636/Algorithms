/* input: 1) set of items 1,2,3...n, each item has value Vi and weight Wi
          2) the max capacity W of knapsack
   output: subset S⊆{1,2,3...n} such that 1) total weight of the items in S is <= max capacity W
                                          2) maximize the total Vi
requirement: First genterate a random number n between 15 and 20.
            Then generate n items each with a value 5 to 10 and a weight 1 to 5, the numbers will be randomly selected.
            The capacity of the bag is a value randomy generated from 20 to 25.
pseudo code: 
            1. when j=1 (row#=1), for s=1 to W, if s<Wi, OPT(1,s)=0, else OPT(1,s)=V1
            2. for j=2 to n
                    for s=1 to W
                        OPT(j,s)=max(Vj+OPT(j-1, s-Wj), OPT(j-1, s))
                    end for
                end for
            return OPT(n,W)
  */

import java.util.Random;
import java.util.Arrays;

public class Knapsack {
    public static void main(String[] args) {
        Random random = new Random();
        int n = random.nextInt(6)+15; //nextInt(6) generate a number from 0 to 5, n is the total item counts
        int W = random.nextInt(6)+20; //max capacity
        int[] values=new int[n];//create an array to store each item's value
        int[] weights=new int[n];//create an array to store each item's weight
        for (int i=0; i<n; i++){
            values[i] = random.nextInt(6)+5; //a random number between 5 to 10
            weights[i] = random.nextInt(5)+1; //a random number between 1 to 5
        }

        System.out.println("In this 0/1 Knapsack problem:");
        System.out.println("There are "+n+" items available to choose.");
        System.out.println("Each item's value is: "+Arrays.toString(values));
        System.out.println("Each item's weight is: "+Arrays.toString(weights));
        System.out.println("The max weight of the knapsack can hold is: "+W);

        int[][] OPT=new int[n+1][W+1]; //create a 2D array representing optimal profit. If 4 items, max W 8, then OPT[][] size is 5by9
        for (int j=0; j<=n; j++){
            for (int s=0; s<=W; s++){
                if (j==0 || s==0) OPT[j][s]=0;

                /* pseudo: 1. when j=1 (row#=1), for s=1 to W, if s<Wi, OPT(1,s)=0, else OPT(1,s)=V1 */
                else if (j==1){
                    if (weights[0]>s) OPT[1][s]=0;
                        else OPT[1][s]=values[0];
                }

                /*pseudo 2. for j=2 to n
                                for s=1 to W
                                    OPT(j,s)=max(Vj+OPT(j-1, s-Wj), OPT(j-1, s))
                                end for
                            end for
                            return OPT(n,W) */
                else{
                    if (weights[j-1]<=s){
                    OPT[j][s] = Math.max(values[j-1]+OPT[j-1][s-weights[j-1]], OPT[j-1][s]);
                    }
                    else OPT[j][s] = OPT[j-1][s];
                }
            }
        }
        System.out.println("The optimal value we can have while not exceed max weight of the bag is: " + OPT[n][W]);//return the last field of array OPT

    
    }
}