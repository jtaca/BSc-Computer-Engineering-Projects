package f1_2016;

public class B implements A {

private String str;
public B(String str) {
this.str = str;

}

@Override

public String doubleStr() {
return str + str;

}
    public String getStr() {
        return str;
    }

    
    public static void main(String[] Args){
     	

    	B obj = new B("Olá");
    	System.out.println(obj.doubleStr());
    			
     	

    	A obj1 = new B("Olá");
    	System.out.println(obj1.doubleStr());
    }
    
    
}


