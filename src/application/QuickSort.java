package application;

public class QuickSort {
	
	static void qSort(int[] a, int start, int end , int pivotLocation){//2--> n/2th element, similarly 4 and 6
	   if ( end > start )//>=2 elements
	     {
	     int pivot = partition( a, start, end, pivotLocation);
	     qSort( a, start, pivot-1, pivotLocation);
	     qSort( a, pivot+1, end, pivotLocation);
	     }
	}
	
	static int partition(int[] a, int start, int end, int pivotLocation){
		SWAP(a,end,start+(end-start)/pivotLocation);
		int pivotItem = a[end];
		int partitionIndex = start;
		for(int i = start; i<end; i++){
			if(a[i]<pivotItem){
				SWAP(a, i, partitionIndex);
				partitionIndex++;
			}
		}
		SWAP(a, partitionIndex, end);
		return partitionIndex;
	}
	
	static void SWAP(int[] a, int l, int r){
		int temp = a[l];
		a[l]=a[r];
		a[r]=temp;
	}
}
