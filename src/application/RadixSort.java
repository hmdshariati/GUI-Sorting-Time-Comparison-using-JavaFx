package application;

class RadixSort {
    static void rSort(int[] a, int base){
        int i, max = a[0], n = a.length, exp = 1;
        int[] b = new int[n];
        for (i = 1; i < n; i++)
            if (a[i] > max)
                max = a[i];//number of digits in max number for radixSort
        while (max / exp > 0){
            int[] bucket = new int[base];
 
            for (i = 0; i < n; i++)
                bucket[ (a[i]/exp)%base ]++;
            for (i = 1; i < base; i++)
                bucket[i] += bucket[i - 1];
            for (i = n - 1; i >= 0; i--){//stable counting sort
                b[ bucket[(a[i]/exp)%base]-1 ] = a[i];
                bucket[ (a[i]/exp)%base ]--;   
            }
            for (i = 0; i < n; i++)
                a[i] = b[i];
            exp *= base;        
        }
    }     
}
