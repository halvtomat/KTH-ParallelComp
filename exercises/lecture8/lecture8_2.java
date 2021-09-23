package lecture8;

public class lecture8_2 extends Thread {
	int val;
	boolean condition = true;
	
	public void run() {
		if (condition)
		while (condition) {
			val=val+1;
		}
	}
	
	public static void main(String[] args) throws Exception {
		lecture8_2 s=new lecture8_2();
		s.start();
		Thread.sleep(1000);
		s.condition=false;
		System.out.println(s.val);
		System.out.println(s.val);
		s.join();
	}
}
