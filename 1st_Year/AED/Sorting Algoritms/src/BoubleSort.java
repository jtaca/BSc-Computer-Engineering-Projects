

public class BoubleSort {

	public static void bubblesort(Integer [] v, int n){
	    Integer temp;
	    Integer lim1 = n-1;
	    
	    for(int limite = n-1; limite!=0;  limite = lim1){
	       lim1 = 0;
	       //print(v); 
	       for(int k = 0; k!=limite ; k++){
	           if( v[k].compareTo(v[k+1])>0 ){
	               temp = v[k]; v[k] = v[k+1]; v[k+1] = temp; //Troca
	               lim1 = k; 
	           } 
	       }
	      
	   }
	       
	}
	
	public static void main(String[] args) {
		Integer[] a = new Integer[500000];
		
		for(int i = 0; i<a.length;i++){
			a[i] = (int) ( 50 * Math.random());	
		}
		long startTime = System.nanoTime();
		print(a);
		bubblesort(a,a.length);
		print(a);
		long endTime = System.nanoTime();
		long duration = (endTime - startTime); 
		System.out.println(duration);
	}
	
	private static void print(Integer[] a){
		for(int i = 0; i<a.length;i++){
			System.out.print("-"+a[i]+"-");	
		}
		System.out.println();
	}

}
