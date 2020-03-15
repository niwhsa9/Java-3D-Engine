package engine;

import java.util.Arrays;

public class Sort {
	public static double[] merge(double[] l, double[] h) {
		double[] q = new double[l.length + h.length];
		int cInL = 0;
		int cInH = 0;
		for(int i = 0; i < q.length; i++) {
			if(cInL != l.length && (cInH == h.length || l[cInL] < h[cInH])) {
				q[i] = l[cInL];
				cInL++;
			} else {
				q[i] = h[cInH];
				cInH++;
			}	
		}
		return q;
	}
	
	public static double[] mergeSort(double[] list) {
		if(list.length == 1) return list; 
		
		int med = list.length/2;
		double[] l = mergeSort(Arrays.copyOfRange(list, 0, med));
		double[] h = mergeSort(Arrays.copyOfRange(list, med, list.length));
			
		return merge(l, h);
	}
	

}
