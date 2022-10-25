package text;
 
import java.util.Scanner;
import java.util.Vector;
/** @author Jenney*/

public class Csp20160903 {
	static Vector<Role> player1 = new Vector<>();
	static Vector<Role> player2 = new Vector<>();
	static Vector<Role> player;
	static Vector<Role> oppo;
 

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		String operation;
		Role hero1 = new Role(0, 30);
		Role hero2 = new Role(0, 30);
		player1.add(hero1);
		player2.add(hero2);
		sc.nextLine();
		boolean flag = true;
		
		
		for (int i = 0; i < n; i++) {
			player = round(flag);
			
			
			operation = sc.nextLine();
			String[] values = operation.split(" ");
			switch (values[0]) {
			case "summon":
				int pos = Integer.valueOf(values[1]);
				int atk = Integer.valueOf(values[2]);
				int health = Integer.valueOf(values[3]);
				
				Role servant = new Role(atk, health);
				player.add(pos, servant);
				break;
				
				
			case "attack":
				int attacker = Integer.valueOf(values[1]);
				int defender = Integer.valueOf(values[2]);
				oppo = round(!flag);
				
				oppo.get(defender).setHealth(
						oppo.get(defender).health - player.get(attacker).atk);
				player.get(attacker).setHealth(
						player.get(attacker).health - oppo.get(defender).atk);
				
				if (player.get(attacker).health <= 0)
					{player.remove(attacker);}
				if (defender != 0 && oppo.get(defender).health <= 0)
					{oppo.remove(defender);}
				break;
				
				
			case "end":
				flag = !flag;
				break;
			default:
				break;
			}
		}
		int winner = 0;
		String str;
		if (player1.get(0).health <= 0)
			{winner = -1;}
		if (player2.get(0).health <= 0)
			{winner = 1;}
		
		System.out.println(winner);
		
		System.out.println(player1.get(0).health);
		str = String.valueOf(player1.size() - 1);
		for (int i = 1; i < player1.size(); i++) {
			str += " " + player1.get(i).health;
		}
		System.out.println(str);
		
		System.out.println(player2.get(0).health);
		
		str = String.valueOf(player2.size() - 1);
		for (int i = 1; i < player2.size(); i++) {
			str += " " + player2.get(i).health;
		}
		System.out.println(str);
 
	}
 
	public static Vector<Role> round(boolean flag) {
		if (flag)
			{return player1;}
		else
			{return player2;}
	}
}
 
class Role// 角色
{
	int atk;
	int health;
 
	public Role(int atk, int health) {
		this.atk = atk;
		this.health = health;
	}
 
	public int getAtk() {
		return atk;
	}
 
	public void setAtk(int atk) {
		this.atk = atk;
	}
 
	public int getHealth() {
		return health;
	}
 
	public void setHealth(int health) {
		this.health = health;
	}
}