/*Input:A list of n unsorted values
  Output: Median of the list (with time complexity of O(n))
  Algorithm: 
  1. partition input into group of 5, n/5
  2. Sort values in each group, collect medians of each group
  3. Find median of medians
  4. Partition input set to a group of values less than median of medians, call small. 
     And another group of values greater than median of medians, call large
  5. k is the target rank, r is the rank of median of medians
     if k<r, recursively find rank kth element in small
     if k=r, return Mm
     if k>r, recursively find rank (k-r)th element in large
 */

import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class MedianFind_LinearTime {
    public static int MedianFind_Linear(int[] arr) {
        return medianFind(arr, 0, arr.length - 1, arr.length / 2);
    }

    public static int medianFind(int[] A, int firstIndex, int lastIndex, int k) {
        if (firstIndex == lastIndex) // if there is only one element in A, return this element directly
            return A[firstIndex];

        /*
         * Algorithm: 1. partition input into group of 5, n/5
         * Algorithm: 2. Sort values in each group, collect medians of each group
         */
        int[] medians = partition(A, firstIndex, lastIndex);

        /*
         * Algorithm: 3. Find median of medians
         */
        int mediansArrayEnd = medians.length - 1;
        int Mm = medianOfMedians(medians, 0, mediansArrayEnd); // (array, medianArray start index, medianArray end index

        /*
         * Algorithm: 4. Partition input set to a group of values less than median of medians, call small. And another group of values
         * greater than median of medians, call large.
         */
        List<Integer> small = new ArrayList<>();
        List<Integer> large = new ArrayList<>();
        for (int i = firstIndex; i <= lastIndex; i++) {
            if (A[i] < Mm)
                small.add(A[i]);
            else if (A[i] > Mm)
                large.add(A[i]);
        }

        /*
         * Algorithm: 5. k is the target rank, r is the rank of median of medians
         * if k<r, recursively find rank kth element in small
         * if k=r, return Mm
         * if k>r, recursively find rank (k-r)th element in large
         */
        int r = small.size();

        if (k < r) {
            return medianFind(listToArray(small), 0, r - 1, k); // parameters: (small array, start index, end index, target k)
        } else if (k > r) {
            return medianFind(listToArray(large), 0, large.size() - 1, k - r);
        } else if (k == r) {
            return Mm;
        }
        return -1;
    }

    public static int[] listToArray(List<Integer> array_list) { // convert list to array
        int[] arr = new int[array_list.size()];
        for (int i = 0; i < array_list.size(); i++) {
            arr[i] = array_list.get(i);
        }
        return arr;
    }

    public static int[] partition(int[] A, int firstIndex, int lastIndex) {
        /*
         * Algorithm: 1. partition input into group of 5, n/5
         */
        int arraySize = A.length;
        int groupNum = arraySize / 5; // partition original array into subgroups each with size 5

        // if the original array size is not divisible by 5, either original input with
        // less than 5 elements or last group has less than 5 elements hence we manually increase the groupNum by 1
        if (arraySize % 5 != 0)
            groupNum++;

        int[] medians = new int[groupNum];

        /*
         * Algorithm: 2. Sort values in each group, collect medians of each group
         */
        for (int i = 0; i < groupNum; i++) {
            int groupStartIndex = firstIndex + i * 5; // loop through each sub groups to identify each group's index range
            int groupEndIndex = Math.min(groupStartIndex + 4, lastIndex); // if hit the last group with less than 5
                                                                          // elements, take the lastIndex from original array directly

            // prepare to sort each group
            int groupSize = groupEndIndex - groupStartIndex + 1;
            int[] group = new int[groupSize];
            // copy elements to each subgroup array --> System.arraycopy(source_array,
            // source_position, dest_array, dest_startPosition, length_subgroup);
            System.arraycopy(A, groupStartIndex, group, 0, groupSize);

            group = mergeSort(group);// call merger sort
            medians[i] = group[(groupStartIndex + groupEndIndex) / 2];// use the sorted sub group and calculate median
        }
        return medians;
    }

    public static int[] mergeSort(int[] arr) {
        if (arr.length == 1) // pseudo: if |Arr|==1; return Arr
            return arr;

        int mid = arr.length / 2;
        int[] L = new int[mid];
        int[] R = new int[arr.length - mid]; 
        for (int i = 0; i < mid; i++) { // copy left half of the values from original array to this L array
            L[i] = arr[i];
        }
        for (int i = mid; i < arr.length; i++) {// copy right half of the values from original array to this R array
            R[i - mid] = arr[i]; // R is a new array, hence its index should start from 0
        }

        int[] LeftArray = mergeSort(L);
        int[] RightArray = mergeSort(R);

        return Merge(LeftArray, RightArray); 
    }

    public static int[] Merge(int[] X, int[] Y) {
        int i = 0, j = 0, k = 0;// pointer i initially points to X[0], y initially points to Y][0]

        int[] M = new int[X.length + Y.length];
        while (i < X.length && j < Y.length) {//while i and j still points w/i the range of array X and array Y
            if (X[i] < Y[j])
                M[k++] = X[i++];// post increment, ie: assign X[i] to M[k] first, then shift k to next index
            else
                M[k++] = Y[j++];
        }

        for (; i < X.length; i++) // i value starts from whatever i results from previous while loop
            M[k++] = X[i];
        for (; j < Y.length; j++)// j value starts from whatever j results from previous while loop
            M[k++] = Y[j];
        return M;
    }

    /*
    Algorithm 3. Find median of medians
    */
    public static int medianOfMedians(int[] mediansArr, int mediansArrayStart, int mediansArrayEnd) {
        if (mediansArrayStart == mediansArrayEnd)// if there are only 1 element in the medians array (which means
                                                 // originally the array only has 5 elements), return this element directly
            return mediansArr[mediansArrayStart];

        // if the number of inputs over 25, medians will have more than 5 elements
        // In this case, use the partition method again
        int arraySize = mediansArr.length;
        int groupNum = arraySize / 5;
        if (arraySize % 5 != 0)
            groupNum++;

        int[] medians = new int[groupNum];
        /*
         * Algorithm: 2. Sort values in each group, collect medians of each group
         */
        for (int i = 0; i < groupNum; i++) {
            int groupStartIndex = mediansArrayStart + i * 5; // loop through each sub groups to identify each group's index range
            int groupEndIndex = Math.min(groupStartIndex + 4, mediansArrayEnd); // if hit the last group with less than 5 elements, 
                                                                                // take the lastIndex from original array directly

            // prepare to sort each group
            int groupSize = groupEndIndex - groupStartIndex + 1;
            int[] group = new int[groupSize];
            // copy elements to each subgroup array --> System.arraycopy(source_array,
            // source_position, dest_array, dest_startPosition, length_subgroup)
            System.arraycopy(mediansArr, groupStartIndex, group, 0, groupSize);
            group = mergeSort(group);// call merger sort
            medians[i] = group[(groupStartIndex + groupEndIndex) / 2];// use the sorted sub group and calculate median
        }
        return medianFind(medians, 0, medians.length - 1, medians.length / 2); // (array, medianArray start index, medianArray end index, k)
    }

    public static void main(String[] args) {
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
        int result = MedianFind_Linear(A);
        System.out.println("Median is: " + result); // display result
        
    }
}