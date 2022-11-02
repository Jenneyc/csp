package csp;
import java.util.*;

class Role{
	int life=30;
	int attack=0;
	Role(){
		life=30;
		attack=0;
	}
	Role(int attack,int life){
		this.attack=attack;
		this.life=life;
	}
}
class Player{
	Vector<Role> field;
	Player(){
		field=new Vector<Role>();
		Role hero=new Role();
		field.add(hero);
	}
	void summon(int pos,int attack,int life) {
		Role servant=new Role(attack,life);
		field.add(pos, servant);
	}
	void offend(int of,Player b,int de) {
		field.get(of).life-=b.field.get(de).attack;
		b.field.get(de).life-=field.get(of).attack;
		if(of!=0&&field.get(of).life<=0) {
			field.remove(of);
		}
		if(de!=0&&b.field.get(de).life<=0) {
			b.field.remove(de);
		}
	}
	boolean lose() {
		return field.get(0).life<=0; 
	}
	void display() {
		System.out.println(field.get(0).life);
		String str=String.valueOf(field.size()-1);
		for(int i=1;i<field.size();i++) {
			str+=" "+field.get(i).life;
		}
		System.out.println(str);
	}
}
/** @author chen linfang*/
public class Csp20160903 {
public static void main(String args[]) {
	final int num=2;
	Player[] players=new Player[2];
	for(int i=0;i<num;i++) {
		players[i]=new Player();
	}
	Scanner s=new Scanner(System.in);
	int t=s.nextInt();
	s.nextLine();
	String operation;
	int player=0,op=1;
	
	while(t--!=0) {
		operation=s.nextLine();
		String[] values=operation.split(" ");
		switch(values[0]) {
		case"summon":
			int pos=Integer.valueOf(values[1]);
			int attack=Integer.valueOf(values[2]);
			int life=Integer.valueOf(values[3]);
			players[player].summon(pos,attack,life);
			
			break;
			
		case "attack":
			int of=Integer.valueOf(values[1]);
			int de=Integer.valueOf(values[2]);
			players[player].offend(of,players[op],de);
			break;
			
		case"end":
			int p=op;
			op=player;
			player=p;
			break;
			
		default:
			break;
		}
		
		
	}
	int winner=0;
	if(players[1].lose()) {
		winner=1;
	}
	else if(players[0].lose()) {
		winner=-1;
	}
	System.out.println(winner);
	for(int i=0;i<=1;i++) {
		players[i].display();
	}
}
}
