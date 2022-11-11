package sheep;
import java.util.*;

class file{
	boolean isfile;
	long limitall;
	long limitdir;
	long size;
	long nextsize;
	long allsize;
	boolean allchange=false;
	boolean nextchange=false;
	Map<String,file> child;
	public file(boolean isfile,long size) {
		this.isfile=isfile;
		if(!isfile) {
			child=new HashMap<>();
			limitall=0;
			limitdir=0;
		}
		else {
			this.size=size;
		}
	}
	public file findfile(String[] split,int cur) {
		if(cur==split.length) {
			return this;
		}
		if(!isfile&&child.containsKey(split[cur])) {
			return child.get(split[cur]).findfile(split, cur+1);
		}
		else {
			return null;
		}
	}
	public long getnextsize() {
		if(!nextchange) {
			return nextsize;
		}
		long ans=0;
		for(file f:child.values()) {
			ans+=f.size;
		}
		nextsize=ans;
		nextchange=false;
		return ans;
	}
	public long getallsize() {
		if(!allchange) {
			return allsize;
		}
		long ans=0;
		for(file f:child.values()) {
			if(f.isfile) {
				ans+=f.size;
			}
			else {
				ans+=f.getallsize();
			}
		}
		allsize=ans;
		allchange=false;
		return ans;
	}
	public boolean createfile(String[] path,long size,int cur) {
		if(cur+1==path.length) {
			if((limitall!=0&&getallsize()+size>limitall)||
					(limitdir!=0&&getnextsize()+size>limitdir)) {
				return false;
			}
			file next=child.get(path[cur]);
			if(next==null) {
				next=new file(true,size);
				child.put(path[cur], next);
			}
			else {
				next.size+=size;
			}
			allchange=true;
			nextchange=true;
			return true;
		}
		else {
			if(limitall!=0&&getallsize()+size>limitall) {
				return false;
			}
			file next=child.get(path[cur]);
			if(next==null) {
				next=new file(false,0);
				child.put(path[cur], next);
			}
			if(next.isfile) {
				return false;
			}
			else {
				if(next.createfile(path, size, cur+1)) {
					allchange=true;
					return true;
				}
				return false;
			}
		}
	}
	public boolean removefile(String[] path,int cur) {
		if(cur+1==path.length) {
			nextchange=true;
			allchange=true;
			child.remove(path[cur]);
			return true;
		}
		else {
			file next=child.get(path[cur]);
			if(next!=null&&!next.isfile&&next.removefile(path, cur+1)) {
				allchange=true;
				return true;
			}
			return false;
		}
	}
}

public class Csp20201203 {
  public static void main(String[] args) {
	  Scanner s=new Scanner(System.in);
	  int t=s.nextInt();
	  s.nextLine();
	  String op;
	  file root=new file(false,0);
	  while(t--!=0) {
		  op=s.nextLine();
		  String[] ops=op.split(" ");
		  boolean result=false;
		  String[] path;
		  switch(ops[0]) {
		  case"C":
			  path=ops[1].substring(1).split("/");
			  long size=Long.parseLong(ops[2]);
			  file f=root.findfile(path,0);
			  if(f!=null) {
				  if(f.isfile) {
					  long changesize=size-f.size;
					  if(root.createfile(path,changesize,0)) {
						  result=true;
					  }
				  }
			  }
			  else {
				  if(root.createfile(path,size,0)) {
					  result=true;
				  }
			  }
			  break;
		  case"R":
			  path=ops[1].substring(1).split("/");
			  root.removefile(path,0);
			  result=true;
			  break;
		  case"Q":
			  long ld=Long.parseLong(ops[2]);
			  long lr=Long.parseLong(ops[3]);
			  path=ops[1].substring(1).split("/");
			  file target=root.findfile(path, 0);
			  if(path[0].equals("")){
				  target=root;
			  }
			  if(target!=null&&!target.isfile) {
				  if((lr==0||target.getallsize()<=lr)
						  &&(ld==0||target.getnextsize()<=ld)) {
					  target.limitall=lr;
					  target.limitdir=ld;
					  result=true;
				  }
			  }
			  break;
		  }
		  if(result) {
			  System.out.println("Y");
		  }
		  else {
			  System.out.println("N");
		  }
	  }
  }
}
