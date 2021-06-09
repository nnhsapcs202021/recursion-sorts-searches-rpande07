/**
A class for executing binary searches in an array.
 */
public class BinarySearcher
{  
    /**
    Finds a value in a range of a sorted array, using the binary
    search algorithm.
    @param a the array in which to search
    @param low the low index of the range
    @param high the high index of the range
    @param value the value to find
    @return the index at which the value occurs, or -1
    if it does not occur in the array
     */
    public static int search(int[] a, int low, int high, int value)
    {  
        for (int i = low; i < high; i++)
        {
            if (a[i] == value)
                return i;
        }
        for (int x = low; x < high; x++)
        {
            if (a[x] > value)
                return x-1;
        }
        return a.length;

    }
}

