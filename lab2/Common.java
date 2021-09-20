package lab2;

public class Common {
	
	private static void swap(int []arr, int a, int b) {
		int temp = arr[a];
		arr[a] = arr[b];
		arr[b] = temp;
	}

	public static int partition(int []arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for(int j = low; j <= high - 1; j++) 
            if (arr[j] < pivot) 
                swap(arr, ++i, j);
        swap(arr, i + 1, high);
        return i + 1;
    }
}
