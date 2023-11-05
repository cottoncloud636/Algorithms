/* Input: two string X and Y, X has length n, Y has length m
   Output: Find the longest common sequence of X and Y
   
   Requirement: First genterate two random numbers n and m between 15 and 20.
                Then generate two strings with length n and m, where each character is randomly selected from A, B, C, D, and E.
                You will use the two randomly generated strings as input.
   Pseudo: Initialize a matrix OPT, with n+1 rows and m+1 columns
           for i from 0 to n
            for j from 0 to m
                if i==0 or j==0, OPT(i,j)=0
                else if X[i]==Y[j], OPT(i,j)=1+OPT(i-1, j-1)
                else OPT(i,j)=max(OPT(i-1, j), OPT(i, j-1))
            end for
           end for
           return OPT(n,m)
 */

import java.util.Random;

public class Longest_Common_Seq {
    public static void main(String[] args) {
        Random random = new Random(); //change random to static, so that the random in generateString method for loop can reach this Random class
        int n = random.nextInt(6)+15; //genterate a random number between 15 and 20
        int m = random.nextInt(6)+15;

        String X = generateString(n);
        String Y = generateString(m);
        System.out.println("String X: "+X);
        System.out.println("String Y: "+Y);

        /*Pseudo: Initialize a matrix OPT, with n+1 rows and m+1 columns */
        int[][] OPT = new int[n+1][m+1];

        /*Pseudo: for i from 0 to n
                    for j from 0 to m
                        if i==0 or j==0, OPT(i,j)=0
                        else if X[i]==Y[j], OPT(i,j)=1+OPT(i-1, j-1)
                        else OPT(i,j)=max(OPT(i-1, j), OPT(i, j-1))
                    end for
                  end for
                  return OPT(n,m) */
        for (int i=0; i<=n; i++){
            for (int j=0; j<=m; j++){
                //if no strings are presented, then OPT is 0
                if (i==0 || j==0) OPT[i][j]=0;

                //if a char in X is the same as a char in Y, then increase the length of LCS by 1, the length of this LCS is the final answer we are trying to find
                else if (X.charAt(i-1)==Y.charAt(j-1)){//clarification: i in this 2D arary is different than i in X and Y.
                                                    //ie: after we check if i or j equals to 0, we goto i==1, but in X, i==1 refers to the 
                                                    //second character, but we want to start from the first character, hence we need to 
                                                    //i-1=0 to retrieve first character in X
                    OPT[i][j] = 1+OPT[i-1][j-1];
                }

                //if a char in X is not the same as the char in Y, then get the bigger length of either the comparison 
                //from (i-1)th char in X and jth char in Y or ith char in X and (j-1)th char in Y
                else {
                    OPT[i][j] = Math.max(OPT[i-1][j], OPT[i][j-1]);
                }
            }
        }
        System.out.println("The length of longest common sequence between string X and string Y is: "+OPT[n][m]);

        /***************** display this longest common sequence *****************/
        //use backtrace the 2D OPT array, if start from beginning to compare each char by char from two string, could take exponential time
        StringBuilder LCSstr = new StringBuilder();
        int i=n;
        int j=m;
        while (i>0 && j>0){
            //start from the last char from both string, if they are the same, add this char to LCSstr string
            if (X.charAt(i-1)==Y.charAt(j-1)){
                LCSstr.append(X.charAt(i-1));//X's length is n, so last index is n-1, same as to Y
                i--;
                j--;
            }
            //if these two char are not the same, go toward the direction from which has a bigger value: OPT[i-1][j] vs OPT[j][i-1]
            //because based on the logic of finding the length of LCS when X[i] != Y[j], we choose the bigger value of these two OPT
            //so the char must reside toward that direction. Then we keep checking if X[i] == Y[j]
            else if (OPT[i-1][j]>OPT[i][j-1]) i--;
            else j--;
        }
        LCSstr.reverse();
        System.out.println("The longest common sequence between string X and string Y is: "+LCSstr.toString());//convert the StringBuilder object to a String object
        /***************** end of: display this longest common sequence *****************/
    }

    /***************** method to generate two strings X and Y *****************/
    public static String generateString(int length){//syntax comment: cannot define a method directly inside the main method, error occurred
        Random random = new Random();
        String characters = "ABCDE";
        StringBuilder str = new StringBuilder(length);
        int strIndex;
        for (int i=0; i<length; i++){
            strIndex=random.nextInt(characters.length());//ie: strIndex=random.nextInt(5), strIndex can be an int from 0 to 4
            str.append(characters.charAt(strIndex));//assume i is now 0,strIndex=3,str.append(characters.charAt(3))-->str.append('D')
                                                    //assume i is now 1,strIndex=1,str.append(characters.charAt(1))-->str.append('B')
        }
        return str.toString();//convert the StringBuilder object to a String object
    }
    /***************** End of: method to generate two strings X and Y *****************/

}