package quickSort;

/**
 * A divide and conquer algorithm that uses a pivot to group the elements into two groups (higher/lower than pivot) to sort
 */
public class FHsort
{
    // insertion sort -----------------------------------------------------
    public static < E extends Comparable< ? super E > >
    void insertionSort(E[] a)
    {
        int k, pos, arraySize;
        E tmp;

        arraySize = a.length;
        for(pos = 1; pos < arraySize; pos++ )
        {
            tmp = a[pos];
            for(k = pos; k > 0 && tmp.compareTo(a[k-1]) < 0; k-- )
                a[k] = a[k-1];
            a[k] = tmp;
        }
    }

    /**
     * public version of the quickSort method that calls on workhorse version
     * @param a takes in an array of generic type
     */
    public static < E extends Comparable< ? super E > >
    void quickSort( E[] a )
    {
        quickSort(a, 0, a.length - 1);
    }


    // quicksort and helpers -------------------------------------------
    // median3 sorts a[left], a[center] and a[right].
    // it leaves the smallest in a[left], the largest in a[right]
    // and median (the pivot) is moved "out-of-the-way" in a[right-1].
    // (a[center] has what used to be in a[right-1])
    protected static < E extends Comparable< ? super E > >
    E median3(E[] a, int left, int right)
    {
        int center;
        E tmp;

        // swaps are done in-line for speed;  each compound line is a swap
        center = (left + right) / 2;
        if(a[center].compareTo(a[left]) < 0)
        { tmp = a[center]; a[center] = a[left]; a[left] = tmp; }
        if(a[right].compareTo(a[left]) < 0)
        { tmp = a[right]; a[right] = a[left]; a[left] = tmp; }
        if(a[right].compareTo(a[center]) < 0)
        { tmp = a[right]; a[right] = a[center]; a[center] = tmp; }

        tmp = a[center]; a[center] = a[right-1]; a[right-1] = tmp;

        return a[right - 1];
    }

    protected static int QS_RECURSION_LIMIT = 15;

    /**
     * sets the recursion limit
     * @param newLim takes in a limit and sets global variable
     * @return returns true if limit was successfully set
     */
    public static boolean setRecursionLimit(int newLim)
    {
        if (newLim < 2 || newLim > 1000)
            return false;
        QS_RECURSION_LIMIT = newLim;
        return true;
    }

    /**
     * recursive method that sorts a given array using a pivot
     * @param a array to sort
     * @param left smallest value
     * @param right largest value
     * @param <E> generic type array
     */
    protected static < E extends Comparable< ? super E > >
    void quickSort(E[] a, int left, int right)
    {
        E pivot, tmp;
        int i, j;

        if( left + QS_RECURSION_LIMIT <= right )
        {
            pivot = median3(a, left, right);

            for(i = left, j = right - 1; ; )
            {
                while( a[++i].compareTo(pivot) < 0 )
                    ;
                while( pivot.compareTo(a[--j]) < 0)
                    ;
                if(i < j)
                { tmp = a[i];
                    a[i] = a[j];
                    a[j] = tmp; }

                else
                    break;
            }

            // restore pivot
            tmp = a[i]; a[i] = a[right - 1]; a[right - 1] = tmp;

            // recursive calls on smaller sub-groups
            quickSort(a, left, i - 1);
            quickSort(a, i + 1, right);
        }
        else
            // non-recursive escape valve - insertion sort
            insertionSort(a, left, right);
    }

    /**
     * for efficient performance time we will do InsertSort for smaller arrays because QS does not perform well in this case
     * @param a array to sort
     * @param start value we are starting at (smallest)
     * @param stop last value (greatest)
     * @param <E> we are taking generic array
     */
    // private insertion sort that works on sub-arrays --------------
    protected static < E extends Comparable< ? super E > >
    void insertionSort(E[] a, int start, int stop)
    {
        int k, pos;
        E tmp;

        // we are not testing for ranges to keep times down - private so ok
        for(pos = start + 1; pos <= stop; pos++ )
        {
            tmp = a[pos];
            for(k = pos; k > 0 && tmp.compareTo(a[k-1]) < 0; k-- )
                a[k] = a[k-1];
            a[k] = tmp;
        }
    }


}