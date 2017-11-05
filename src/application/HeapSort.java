package application;

public class HeapSort {
	int[] a;
	int size;
	public HeapSort(int[] a){
		size=a.length;
		this.a = new int[size];
		for(int i = 0; i<size; i++)
			this.a[i] = a[i];
		for(int i=size/2; i>=0; i--)
			heapifyDown(i);	
	}
	
	int getLeftChildIndex(int parentIndex){return 2*parentIndex + 1;}
	int getRightChildIndex(int parentIndex){return 2*parentIndex + 2;}
	int getParentIndex(int childIndex){return (childIndex-1)/2;}
	
	boolean hasLeftChild(int index){return getLeftChildIndex(index)<size;}
	boolean hasRightChild(int index){return getRightChildIndex(index)<size;}
	boolean hasParent(int index){return getParentIndex(index)>=0;}
	
	int leftChildValue(int index){return a[getLeftChildIndex(index)];}
	int rightChildValue(int index){return a[getRightChildIndex(index)];}
	int parentValue(int index){return a[getParentIndex(index)];}
	
	int removeTop(){//remove top
		if(size==0)	throw new IllegalStateException();
		int item = a[0];
		a[0] = a[size-1];
		size--;
		heapifyDown(0);
		return item;
	}
	
	void heapifyDown(int index){//recursive
		if(!hasLeftChild(index)) 	return;
		int largerChildIndex = getLeftChildIndex(index);
		if(hasRightChild(index) && rightChildValue(index)>leftChildValue(index))
			largerChildIndex = getRightChildIndex(index);
		
		if(a[index]<a[largerChildIndex]){
			SWAP(index, largerChildIndex);
			heapifyDown(largerChildIndex);
		}
	}
	
	static int[] heapSort(int[] a){
		HeapSort h = new HeapSort(a);
		int[] b = new int[a.length];
		int bIndex = a.length-1;
		while(h.size>0)
			b[bIndex--] = h.removeTop();
		return b;
	}
	
	void SWAP(int l, int r){
		int temp = a[l];
		a[l]=a[r];
		a[r]=temp;
	}
}

