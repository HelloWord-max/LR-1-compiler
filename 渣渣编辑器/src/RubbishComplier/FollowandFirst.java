package RubbishComplier;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FollowandFirst {
//First集
private Map<String,Set<String>> First = new HashMap<String,Set<String>>();
//Follow集
private Map<String,Set<String>> Follow = new HashMap<String,Set<String>>();
//产生式的First集
private Map<String,Set<String>> productionFirst = new HashMap<String,Set<String>>();  	
private Map<String, List<List<List<String>>>> grammar;
private List<String> Vn;
private List<String> Vt;
public Map<String, Set<String>> getFirst() {
	return First;
}

public void setFirst(Map<String, Set<String>> first) {
	First = first;
}

public Map<String, Set<String>> getFollow() {
	return Follow;
}

public void setFollow(Map<String, Set<String>> follow) {
	Follow = follow;
}

public Map<String, Set<String>> getProductionFirst() {
	return productionFirst;
}

public void setProductionFirst(Map<String, Set<String>> productionFirst) {
	this.productionFirst = productionFirst;
}

public FollowandFirst(Map<String, List<List<List<String>>>> grammar,List<String> Vn,List<String> Vt) {
	this.grammar=grammar;
	this.Vn=Vn;
	this.Vt=Vt;
	// TODO Auto-generated constructor stub
	getFOLLOWANDFIRST();
}

private void getFOLLOWANDFIRST() {
	//非终结符号的First集和Follow集初始化
		for(int i = 0;i<Vn.size();++i) {
			First.put(Vn.get(i), new HashSet<String>());
			Follow.put(Vn.get(i), new HashSet<String>());
		}
		
		//根据文法初始化productionFirst集
		for(int i = 0;i<Vn.size();++i) {
			List<List<List<String>>> str = grammar.get(Vn.get(i));
//			System.out.println(str);
			for(int j = 0;j<str.size();++j) {
				productionFirst.put(Vn.get(i)+"->"+str.get(j), new HashSet<String>());
			}
		}
		
		
	//构造first集
		this.buildFirst();
		//显示first集
		//this.displayFirst();
		displayFirst();
		
	//构造Follow集
		this.buildFollow();
		//显示Follow集
		this.displayFollow(); 
}

//构建First集合
private void buildFirst() {
		boolean bigger = true;
		while(bigger) {
			bigger = false;
			int setSize = 0;
			for(int i = 0;i<Vn.size();++i) {
				String left = Vn.get(i); //产生式左部符号
				List<List<List<String>>> right = grammar.get(left); //产生式右部
				setSize = First.get(left).size();
				for(int k = 0;k<right.size();++k) {   //对右部的产生式一个一个处理
					int rightIndex = 0;
					String cha =right.get(k).get(rightIndex).get(0);
					if (Vt.indexOf(cha) != -1) { // 是终结符号
						// 加入left的FIRST集
						First.get(left).add(cha);
						productionFirst.get(left+"->"+right.get(k)).add(cha);
						if(First.get(left).size()>setSize)
							bigger = true;
					} else if (Vn.indexOf(cha) != -1) { // 是非终结符号
						Set<String> Y;
						do {
							if(rightIndex>=right.get(k).size()) {
								//说明到最后Y的first集中都有$,此时应该将$加入first集
								productionFirst.get(left+"->"+right.get(k)).add("$");
								First.get(left).add("$");
								break;
							}
							cha = right.get(k).get(rightIndex).get(0);
							Y = First.get(cha);
							//把Y的First集（除$）放入X的First集
							Iterator<String> it = Y.iterator();
							while (it.hasNext()) {
								String tempc = (String) it.next();
								if (!tempc.equals("$")) {
									productionFirst.get(left+"->"+right.get(k)).add(tempc);
									First.get(left).add(tempc);
								}
							}
							++rightIndex;
						} while (Y.contains("$"));
						
						if(First.get(left).size()>setSize)
							bigger = true;
					}
				}
			}
			
		}
	}

	//显示FIRST集
	public void displayFirst() {

		System.out.println("文法的FIRST集如下：");
		for(int i = 0;i < Vn.size();++i) {
			System.out.print(Vn.get(i)+":");
			Iterator<String> it = First.get(Vn.get(i)).iterator();
			while(it.hasNext()) {
				System.out.print(it.next()+" ");
			}
			System.out.println();
		}
	}
	
//构建Follow集合
private void buildFollow() {
		//#属于FOLLOW(E)
//		Follow.get(start).add(end);
		boolean bigger = true;
		while(bigger) {
			bigger=false;
			int setSize = 0;
			for(int i = 0;i < Vn.size();++i) {
				String left = Vn.get(i); //产生式左部符号
				for(int right_index=0;right_index<grammar.get(left).size();right_index++)
				{
					List<List<String>> right = grammar.get(left).get(right_index); //产生式右部
	  				int rightIndex = 0;
	  				//对产生式的右部进行操作
	  					rightIndex=0;
	  					while(rightIndex<right.get(0).size())
	  					{
	  						String firstChar = right.get(0).get(rightIndex);
	  						if(Vt.indexOf(firstChar)!=-1) { //终结符号
	  	  						++rightIndex;
	  	  						continue;
	  	  					}
	  						if(right.get(0).size()==rightIndex+1)//达到产生的尾部了
	  						{
		  							setSize=Follow.get(firstChar).size();
		  							Follow.get(firstChar).addAll(Follow.get(left));
		  							if(Follow.get(firstChar).size()>setSize)
		  								bigger = true;
		  							rightIndex++;
		  							continue;
	  						} 
	  						if(right.get(0).size()>rightIndex+1) { //还可以继续识别符号
	  							String secondChar = right.get(0).get(rightIndex+1);
	  	  						if(Vt.indexOf(secondChar)!=-1) { //终结符号，移入firstChar的Follow集
	  	  							//System.out.println(firstChar);
	  	  							setSize=Follow.get(firstChar).size();
	  	  							Follow.get(firstChar).add(secondChar);
	  	  							if(Follow.get(firstChar).size()>setSize)
	  	  								bigger = true;
	  	  						}else if(Vn.indexOf(secondChar)!=-1) { //非终结符号
	  	  							//将second的FIRST集元素除$移入firstChar的Follow集
	  	  							setSize=Follow.get(firstChar).size();
	  	  							Iterator<String> it = First.get(secondChar).iterator();
	  	  							while (it.hasNext()) {
	  	  								String tempc = (String) it.next();
	  	  								if (!tempc.equals("$")) {
	  	  									Follow.get(firstChar).add(tempc);
	  	  								}
	  	  							}
	  	  							if(Follow.get(firstChar).size()>setSize)
	  	  								bigger = true;
	  	  							
	  	  							//如果$属于secondChar的First集，将left的Follow集全部加入firstChar的Follow集
	  	  							if(First.get(secondChar).contains("#")) {
	  	  								setSize=Follow.get(firstChar).size();
	  	  								Follow.get(firstChar).addAll(Follow.get(left));
	  	  								if(Follow.get(firstChar).size()>setSize)
	  	  									bigger = true;
	  	  							}
	  	  						}
	  	  					}
  	  						++rightIndex;
	  					}
				}
			}
		}
	}
	
	// 显示Follow集
	public void displayFollow() {
		System.out.println("文法的FOLLOW集如下：");
		for (int i = 0; i < Vn.size(); ++i) {
			System.out.print(Vn.get(i) + ":");
			Iterator<String> it = Follow.get(Vn.get(i)).iterator();
			while (it.hasNext()) {
				System.out.print(it.next() + " ");
			}
			System.out.println();
		}
	}
}
