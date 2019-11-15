package map;

public class Main {
	public static void main(String[] args){
		Casa c=new Casa();
		Eletrodomestico e1 =new Eletrodomestico("Bosh","forno",100); 
		Eletrodomestico e2 =new Eletrodomestico("Bosa","forno1",100); 
		Eletrodomestico e3 =new Eletrodomestico("Bosy","forno2",100); 
		Eletrodomestico e4 =new Eletrodomestico("Bosh","forno3",100); 
		Eletrodomestico e5 =new Eletrodomestico("Boh","forno4",100); 
		
		c.addEletrodomestico("casita", e1);
		c.addEletrodomestico("casita", e2);
		c.addEletrodomestico("casita", e3);
		c.addEletrodomestico("casita", e4);
		c.addEletrodomestico("casita", e5);
		System.out.println(c);
		
		c.removeAll("Bosh", "casita");
		
		System.out.println(c);
		
		
	}
}
