//Author:sumorin

package quickSort;

import cs1c.TimeConverter;
import cs1c.CSVFileWriter;

/**
 * Class benchmarks quick sort algorithm on large arrays and recursion limits and generates data
 * that is used to make a graph and analyze optimal range of performance
 */
public class TestQSwRecursionLimit {
    static String fileName;

    /**
     * method that times QS 3 times for each array size and gives AVG elapsed time
     * @param arraySize takes in array size to benchmark
     * @return returns concatenated string to send to CSVFileWriter
     */
    public static String benchmarkQS(int arraySize)

    {
        int limit = 2;
        int highBound = 300;
        int numberOfTests = 3;
        int k, randomInt;
        long startTime1, estimatedTime1;
        long startTime2, estimatedTime2;
        long startTime3, estimatedTime3;


        Integer[] originalArray = new Integer[arraySize];
        Integer[] toSortArray1 = new Integer[arraySize];
        Integer[] toSortArray2 = new Integer[arraySize];
        Integer[] toSortArray3 = new Integer[arraySize];
        FHsort fHsort = new FHsort();

        // build array with randomly generated data
        for (k = 0; k < arraySize; k++)
        {
            randomInt = (int) (Math.random() * arraySize);
            originalArray[k] = randomInt;
        }

        CSVFileWriter fileWriter = null;
        String str = "";
        setFileName("resources/" + arraySize + ".CSV");

        while (limit <= highBound) {

            //copy original array with random values generated before sorting
            System.arraycopy(originalArray, 0, toSortArray1, 0, originalArray.length);
            System.arraycopy(originalArray, 0, toSortArray2, 0, originalArray.length);
            System.arraycopy(originalArray, 0, toSortArray3, 0, originalArray.length);

            //test1
            startTime1 = System.nanoTime(); // ------------------ start
            fHsort.setRecursionLimit(limit);
            fHsort.quickSort(toSortArray1); //now we can sort the array because we saved the original copy to be used iteratively
            estimatedTime1 = System.nanoTime() - startTime1; // ---------------------- stop

            //test2
            startTime2 = System.nanoTime(); // ------------------ start
            fHsort.setRecursionLimit(limit);
            fHsort.quickSort(toSortArray2); //now we can sort the array because we saved the original copy to be used iteratively
            estimatedTime2 = System.nanoTime() - startTime2; // ---------------------- stop

            //test3
            startTime3 = System.nanoTime(); // ------------------ start
            fHsort.setRecursionLimit(limit);
            fHsort.quickSort(toSortArray3); //now we can sort the array because we saved the original copy to be used iteratively
            estimatedTime3 = System.nanoTime() - startTime3; // ---------------------- stop

            //take average of the 3 tests
            long averageEstimate = (estimatedTime1 + estimatedTime2 + estimatedTime3)/numberOfTests;

            System.out.println("Quick sort Elapsed Time: "
                    + " size: " + arraySize + ", " + " recursion limit: " + limit + ", "
                    + TimeConverter.convertTimeToString(averageEstimate)
                    + " = " + averageEstimate + "ns");

            str += limit + "," + averageEstimate + "\n";
            limit +=2;

        }
        return str;
    }

    //mutator and accessor methods for global variable filename
    public static void setFileName(String str){
        fileName = str;
    }

    public static String getFileName(){return fileName;}

    // -------  main --------------

    /**
     * test file that creates arrays of variable sizes (and random generated data) with recursion limit ranging 2-300 then
     *  calls on benchmarking feature to time performance
     * @param args
     * @throws Exception if there's an error within CSFFileWriter
     */
    public static void main(String[] args) throws Exception
    {
        final int [] ARRAY_SIZES = new int [20];
            //sets the 20 intervals with 500K width of interval (rounded up from 499K for better readability)
            //array of sizes
            for(int j = 20000, i = 0; j < 10000000; i++, j+=500000){
                ARRAY_SIZES[i] = j;
            }

        //goes through the list of sizes
        for (int test = 0; test < ARRAY_SIZES.length; test++)
        {
            int currentSize = ARRAY_SIZES[test];
            String str = null;

            str = benchmarkQS(currentSize);
            CSVFileWriter.writeCsvFile(str,getFileName());
        }
    }
}
