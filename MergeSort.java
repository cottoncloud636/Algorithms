import java.util.Scanner;
import java.util.Arrays;

/*Input: an unordered array of n value 
  Output: a sorted array
  pseudo code:
  Sort(Arr):
     if |Arr|==1; return Arr
     else L=Arr[0, n/2], R=Arr[n/2 + 1, n-1]
     LS=Sort(L)
     RS=Sort(R)
     return Merge (LS, RS)

   Merge (X, Y):
     M={}, i=0, j=0
     while i<=|X| and j<=|Y|: 
        if X[i]<Y[j], append X[i] to M and i++;
        else append Y[j] to M and j++;
     if i<|X|, append the rest of X to result
     else append the rest of Y to the result
*/

public class MergeSort {
  public static int[] Sort(int[] arr) {
    if (arr.length == 1) // pseudo: if |Arr|==1; return Arr
      return arr;

    int mid = arr.length / 2;
    int[] L = new int[mid];// pseudo: else L=Arr[0, n/2]
    int[] R = new int[arr.length - mid]; // pseudo: R=Arr[n/2 + 1, n-1]
    for (int i = 0; i < mid; i++) { // copy left half of the values from original array to this L array
      L[i] = arr[i];
    }
    for (int i = mid; i < arr.length; i++) {// copy right half of the values from original array to this R array
      R[i - mid] = arr[i]; // R is a new array, hence its index should start from 0
    }

    int[] LeftArray = Sort(L); // pseudo: LS=Sort(L)
    int[] RightArray = Sort(R); // pseudo: LR=Sort(R)

    return Merge(LeftArray, RightArray); // pseudo: return Merge (LS, RS)
  }

  public static int[] Merge(int[] X, int[] Y) {
    int i = 0, j = 0, k = 0;// pseudo: M={}, i=0, j=0
                            // pointer i initially points to X[0], y initially points to Y][0], k points to

    int[] M = new int[X.length + Y.length];
    while (i < X.length && j < Y.length) {// pseudo: while i<=|X| and j<=|Y|
                                          // while i and j still points w/i the range of array X and array Y
      if (X[i] < Y[j]) // pseudo: if X[i]<Y[j], append X[i] to M and i++;
        M[k++] = X[i++];// post increment, ie: assign X[i] to M[k] first, then shift k to next index
      else
        M[k++] = Y[j++]; // pseudo: else append Y[j] to M and j++;
    }

    for (; i < X.length; i++) // pseudo: if i<|X|, append the rest of X to result
                              // i value starts from whatever i results from previous while loop
      M[k++] = X[i];
    for (; j < Y.length; j++) // pseudo: else append the rest of Y to the result
                              // j value starts from whatever j results from previous while loop
      M[k++] = Y[j];

    return M;
  }

  public static void main(String[] args) {
    // int[] A = { 4, 2, 7, 1, 3, 6, 6, 5 };
    // int[] result = Sort(A);
    //
    System.out.print("Please enter the array length, please enter one reasonable number: ");

    Scanner scanner = new Scanner(System.in); // read in user input for array length
    int size = scanner.nextInt();
    if (size <= 0) {
      System.out.print("Invalid array, try again!");
      System.exit(0);
    }

    int[] A = new int[size];
    System.out.println("");
    System.out.print(
        "Please enter the array elements, please put a space after each number you typed in and make sure the amount of elements match with the length that you just entered: ");

    for (int i = 0; i < size; i++) { // read in user input for array elements
      A[i] = scanner.nextInt();
    }

    scanner.close();

    int[] sortedArray = Sort(A); // call method Sort and store the value into the sortedArray
    System.out.println(Arrays.toString(sortedArray)); // display result

  }

}
