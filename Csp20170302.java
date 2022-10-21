package text;
import java.util.*;
/** @author Jenney*/

public class Csp20170302 {
	static final int DIRF=-1;
	static final int DIRA=1;

	public static void main(String args[]) {
		Scanner s=new Scanner(System.in);
		int n=s.nextInt();
		int m=s.nextInt();
		int[]dui=new int[n+1];
		
		int i;
		for(i=1;i<=n;i++) {
			dui[i]=i;
		}
		
		while(m--!=0) {
			int id=s.nextInt();
			int num=s.nextInt();
			int pos;
			
			for(pos=1;pos<=n;pos++) {
				if(dui[pos]==id) {
					break;
				}
			}
			
			int des=pos+num;
			int dir=num>0?DIRA:DIRF;
			i=pos+dir;
			for(;;i=i+dir) {
				dui[i-dir]=dui[i];
				if(i==des) {
					break;
				}
			}
			dui[des]=id;
			
		}
		
		for(i=1;i<=n;i++) {
			System.out.print(dui[i]+" ");
		}
		
		s.close();
	}
}
