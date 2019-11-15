package Ex2;

public class Rational implements Comparable<Rational> {
	private int denominator=0;
	private int numerator=0;
	
	public Rational(int numerator1, int denominator1) {
		//super();
		this.numerator = numerator1;
		this.denominator = denominator1;
		}
		public int getNumerator() {
		return numerator;
		}
		public int getDenominator() {
		return denominator;
		}
		@Override
		public String toString() {
		return numerator + "/" + denominator;
		}

	@Override
	/*public int compareTo(Rational r) {
		int i;
		if(this.denominator==r.denominator){
			if(this.numerator>r.numerator){
			i=1;
			}else{
				i=0;
			}
		}else{
			int m=this.denominator;
			int j=r.denominator;
			int m1=this.numerator;
			int j1=r.numerator;
			j1=j1*m;
			
			m1=m1*j;
			m=m*j;
			j=m;
			
			if(m1>j1){
				i=1;
			}else{
				i=0;
			}
			
		}
		return i;
		
	}*/
	public int compareTo(final Rational another) { int leftNumerator =
		    getNumerator() * another.getDenominator();
		
	int rightNumerator =
		    another.getNumerator() * getDenominator();
		
		if (leftNumerator > rightNumerator) 
			return 1;
		
		if (leftNumerator < rightNumerator)
		    return -1;
		return 0;
	}

	

}
