package sheep;
import java.util.*;

class node{
	boolean isfile;
	long size;
	long ld;
	long lr;
	long dd;
	long rr;
	HashMap<String,node> child;
	boolean ddchange;
	boolean rrchange;
	public node(boolean isfile,long size) {
		if(!isfile) {
			this.isfile=isfile;
			this.size=size;
			ld=0;
			lr=0;
			child=new HashMap<>();
			ddchange=false;
			rrchange=false;
		}
		else {
			this.isfile=isfile;
			this.size=size;
		}
	}
}

class tree{
	node root;
	public tree() {
		root=new node(false,0);
	}
	public node findnode(node p,String[] path,int cur) {
		if(cur==path.length) {
			return p;
		}
		else {
			if(!p.isfile&&p.child.containsKey(path[cur])) {
				return findnode(p.child.get(path[cur]),path,cur+1);
			}
			else {
				return null;
			}
		}
	}
	public long getrr(node p) {
		if(!p.rrchange) {
			return p.rr;
		}
		else {
			long ans=0;
			for(node a:p.child.values()) {
				if(a.isfile) {
					ans+=a.size;
				}
				else {
					ans+=getrr(a);
				}
			}
			p.rr=ans;
			p.rrchange=false;
			return ans;
		}
	}
	public long getdd(node p) {
		if(!p.ddchange) {
			return p.dd;
		}
		else {
			long ans=0;
			for(node a:p.child.values()) {
					ans+=a.size;
			}
			p.dd=ans;
			p.ddchange=false;
			return p.dd;
		}
	}
	public boolean createnode(node p,String[] path,long size,int cur) {
		if(cur+1==path.length) {
			if(
					(p.lr!=0&&getrr(p)+size>p.lr)||
					(p.ld!=0&&getdd(p)+size>p.ld)) {
				return false;
			}
			else {
				node t=p.child.get(path[cur]);
				if(t==null) {
					t=new node(true,size);
					p.child.put(path[cur], t);
				}
				else if(t.isfile) {
					t.size+=size;
				}
				p.ddchange=true;
				p.rrchange=true;
				return true;
			}
		}
		else {
			if(p.isfile||
					(p.lr!=0&&getrr(p)+size>p.lr)) {
				return false;
			}
			else {
				node t=p.child.get(path[cur]);
				if(t==null) {
					t=new node(false,0);
					p.child.put(path[cur], t);
				}
				if(t.isfile) {
					return false;
				}
				else {
					if(createnode(t,path,size,cur+1)) {
						p.rrchange=true;
						return true;
					}
					return false;
				}
			}
		}
	}
	public boolean remove(node p,String[] path,int cur) {
		if(cur+1==path.length) {
			if(p.isfile) {
				return false;
			}
			node t=p.child.get(path[cur]);
			if(t!=null) {
				p.child.remove(path[cur]);
				p.ddchange=true;
				p.rrchange=true;
				return true;
			}
			else {
				return false;
			}
		}
		else {
			if(p.isfile) {
				return false;
			}
			else {
				node t=p.child.get(path[cur]);
				if(t==null) {
					return false;
				}
				else {
					if(remove(t,path,cur+1)) {
						p.rrchange=true;
						return true;
					}
					else {
						return false;
					}
				}
			}
		}
	}
}

public class Csp20201203 {
  public static void main(String[] args) {
	  Scanner s=new Scanner(System.in);
	  int t=s.nextInt();
	  tree mytree=new tree();
	  while(t--!=0) {
		  char op=s.next().charAt(0);
		  String a=s.next();
		  boolean result=false;
		  node p;
		  String[] path=a.substring(1).split("/");
		  switch(op) {
		  case'C':
			  long size=s.nextLong();
			  p=mytree.findnode(mytree.root, path, 0);
			  if(p!=null) {
				  if(p.isfile) {
					  size=size-p.size;
					  result=mytree.createnode(mytree.root,path,size,0);
				  }
				  else {
					  result=false;
				  }
			  }
			  else {
				  result=mytree.createnode(mytree.root,path,size,0);
			  }
			  break;
		  case'R':
			  mytree.remove(mytree.root,path,0);
			  result=true;
			  break;
		  case'Q':
			  long ld=s.nextLong();
			  long lr=s.nextLong();
			  node f=mytree.findnode(mytree.root, path, 0);
			  if(path[0].equals("")) {
				  f=mytree.root;
			  }
			  if(f==null) {
				  result=false;
			  }
			  else if(f.isfile) {
				  result=false;
			  }
			  else {
				  if(ld!=0&&ld<mytree.getdd(f)||
						  lr!=0&&mytree.getrr(f)>lr) {
					  result=false;
				  }
				  else {
					  f.ld=ld;
					  f.lr=lr;
					  result=true;
				  }
			  }
			  break;
		  }
		  char r='N';
		  if(result) {
			  r='Y';
		  }
		  System.out.println(r);
	  }
  }
}
