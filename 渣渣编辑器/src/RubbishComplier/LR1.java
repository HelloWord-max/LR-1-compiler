package RubbishComplier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;


public class LR1 {
String grammar_error="";
String Intermediate_code="";
String assemblylaguage_cade="";
public LR1(words_analyse as) {
		LR2_Analysis pa = new LR2_Analysis(as);
		pa.analysisProcessing(as);
		samplelaguage_to_assemblylaguage sta= new samplelaguage_to_assemblylaguage(pa.getSa().Guadruple_Form_list);
		grammar_error=pa.grammar_error;
		Intermediate_code=pa.Intermediate_code;
		assemblylaguage_cade=sta.assemblylaguage;
	}
}

class LR2_Analysis{
	private semantic_analyse sa=new semantic_analyse();
	String grammar_error="";
	String Intermediate_code="";
	private List<String> InputStream = new ArrayList<String>(); //输入流
	private int indexP = 0; //扫描指针，初始为0
	private int index_state = 0; //扫描指针，初始为0
	private int step = 0; //扫描指针，初始为0
 	BulidLRForm2 fal = new BulidLRForm2();
 	private Stack<String> analysisStack = new Stack<String>(); //分析栈
 	private Stack<Integer> stateStack = new Stack<Integer>(); //分析栈
	public semantic_analyse getSa() {
		return sa;
	}

	public void setSa(semantic_analyse sa) {
		this.sa = sa;
	}

	public LR2_Analysis(words_analyse as) {
		for(int words_index=0;words_index<as.TKS.size();words_index++)
		{
			if(as.TKS.get(words_index).TokenCode.equals("400")||as.TKS.get(words_index).TokenCode.equals("500")||as.TKS.get(words_index).TokenCode.equals("600")||as.TKS.get(words_index).TokenCode.equals("700")||as.TKS.get(words_index).TokenCode.equals("800"))
 			{
 				InputStream.add(as.TKS.get(words_index).getTokenCode());
 			}
 			else
 			{
				InputStream.add(as.TKS.get(words_index).getTokenName());
 			}
			
		}
		InputStream.add("#");  //末尾添加#号
//		System.out.println(InputStream);
		//分析栈中移入终结符号和文法的开始符号
 		analysisStack.push(fal.end);
 		analysisStack.push(fal.start);
 		stateStack.push(0);
	}
	
	//预测分析执行
	 	public void analysisProcessing(words_analyse as) {
	 		System.out.println("\n\n预测分析过程");
	 		System.out.println("步骤\t\t\t"+"状态栈\t\t\t"+"符号栈\t\t\t"+"输入串\t\t\t"+"action\t\t\t"+"goto\t\t\t");
 			analysisStack.pop();
// 			analysisStack.isEmpty() && indexP<InputStream.size()
 			int judge=0;
	 		while(!analysisStack.lastElement().equals("Program")) {
	 			String Input=InputStream.get(indexP);
	 			
	 			//分析栈栈栈顶元素弹栈
	 			if(fal.getDFA_SS().get(stateStack.get(stateStack.size()-1)).getNEXT_DFA().get(Input)!=null)//如果可以移进，则移进
	 			{
	 				if(fal.getDFA_SS().get(stateStack.get(stateStack.size()-1)).getNEXT_DFA().get(Input).getState()!=-1)
	 				{
	 					if(Input.equals("while"))
	 					{
	 		 				CHAIN CH=new CHAIN();
	 		 				CH.judge_type="while";
	 		 				sa.chain_stack.add(CH);
	 		 				
	 		 				RETURN rt=new RETURN();
	 						rt.return_index=sa.Guadruple_Form_index;
	 						rt.return_type="while";
	 						sa.return_stack.add(rt);
	 					}
	 					if(Input.equals("for"))
	 					{
	 		 				CHAIN CH=new CHAIN();
	 		 				CH.judge_type="for";
	 		 				sa.chain_stack.add(CH);
	 					}
	 					if(Input.equals("if"))
	 		 			{
	 		 				CHAIN CH=new CHAIN();
	 		 				CH.judge_type="if";
	 		 				sa.chain_stack.add(CH);
	 		 			}
	 					if(Input.equals("else"))
	 		 			{
	 						
	 						EXIT EI=new EXIT();
		 					sa.exit_stack.add(EI);
	 		 				Guadruple_Form GF=new Guadruple_Form();
	 		 				GF.agr1="_";
	 		 				GF.OP="j";
	 		 				GF.agr2="_";
	 		 				GF.result="-1";
	 		 				GF.exit=sa.exit_stack.lastElement();
	 		 				GF.num=sa.Guadruple_Form_index++;
	 		 				sa.Guadruple_Form_list.add(GF);
	 						sa.chain_stack.lastElement().chain=sa.Guadruple_Form_index;
	 						sa.chain_stack.pop();
	 		 				
	 		 			}
	 					
		 				index_state=fal.getDFA_SS().get(stateStack.get(stateStack.size()-1)).getNEXT_DFA().get(Input).getState();
		 				displayProcessing("S"+String.valueOf(index_state));
		 				stateStack.push(fal.getDFA_SS().get(stateStack.get(stateStack.size()-1)).getNEXT_DFA().get(Input).getState());
		 				analysisStack.push(Input);
			 			++indexP;
	 				}
	 			}
	 			else
	 			{
	 				int charge1=0;
	 				for(int Vn_index=0;Vn_index<fal.getVn().size();Vn_index++)//循环遍历每一个非终结符
	 				{
 						int charge=0;
	 					if(fal.getDFA_SS().get(stateStack.get(stateStack.size()-1)).getDFA().get(fal.getVn().get(Vn_index))!=null)//如果当前非终结符存在产生式，则才进入下一步
	 					{
		 					for(int DFA_value_index=0;DFA_value_index<fal.getDFA_SS().get(stateStack.get(stateStack.size()-1)).getDFA().get(fal.getVn().get(Vn_index)).size();DFA_value_index++)
		 					{
		 						int point_index=fal.getDFA_SS().get(stateStack.get(stateStack.size()-1)).getDFA().get(fal.getVn().get(Vn_index)).get(DFA_value_index).get(0).indexOf(".");//取出当前产生式.的位置
		 						int length1=fal.getDFA_SS().get(stateStack.get(stateStack.size()-1)).getDFA().get(fal.getVn().get(Vn_index)).get(DFA_value_index).get(0).size();//取出当前产生式的长度
		 						if(point_index==length1-1)//如果.在产生式最末尾，则考虑规约
		 						{
		 							if(fal.getDFA_SS().get(stateStack.get(stateStack.size()-1)).getDFA().get(fal.getVn().get(Vn_index)).get(DFA_value_index).get(1).contains(Input))//当前产生式的可归约项
			 						{
		 								displayProcessing("r");
			 							int length=fal.getDFA_SS().get(stateStack.get(stateStack.size()-1)).getDFA().get(fal.getVn().get(Vn_index)).get(DFA_value_index).get(0).size()-1;
			 							List<String> pop_str=new ArrayList<String>();
			 							for(int i=0;i<length;i++)
			 							{
			 								String pop_str_singal=analysisStack.pop();
			 								if(pop_str_singal.equals("700"))
			 								{
			 									pop_str.add(0,as.TKS.get(indexP-1).getTokenName());
			 								}
			 								else if(pop_str_singal.equals("400"))
			 								{
			 									pop_str.add(0,as.TKS.get(indexP-1).getTokenName());
			 								}
			 								else
			 								{
			 									pop_str.add(0,pop_str_singal);
			 								}
			 								stateStack.pop();
			 							}
			 							sa.reduction_dispose(as,pop_str,fal.getVn().get(Vn_index));//规约处理
			 							analysisStack.push(fal.getVn().get(Vn_index));
			 							stateStack.push(fal.getDFA_SS().get(stateStack.get(stateStack.size()-1)).getNEXT_DFA().get(fal.getVn().get(Vn_index)).getState());
			 							charge=1;
			 							break;
			 						}
			 						else if(fal.getFollowSet().get(fal.getVn().get(Vn_index)).contains(Input))//当前产生式的非终结符的follow集合
			 						{
			 							displayProcessing("r");
			 							int length=fal.getDFA_SS().get(stateStack.get(stateStack.size()-1)).getDFA().get(fal.getVn().get(Vn_index)).get(DFA_value_index).get(0).size()-1;
//			 							System.out.println(fal.getDFA_SS().get(stateStack.get(stateStack.size()-1)).getDFA().get(fal.getVn().get(Vn_index)));
			 							List<String> pop_str=new ArrayList<String>();
			 							for(int i=0;i<length;i++)
			 							{
			 								String pop_str_singal=analysisStack.pop();
			 								if(pop_str_singal.equals("700"))
			 								{
			 									pop_str.add(0,as.TKS.get(indexP-1).getTokenName());
			 								}
			 								else if(pop_str_singal.equals("400"))
			 								{
			 									pop_str.add(0,as.TKS.get(indexP-1).getTokenName());
			 								}
			 								else if(pop_str_singal.equals("500"))
			 								{
			 									pop_str.add(0,as.TKS.get(indexP-1).getTokenName());
			 								}
			 								else
			 								{
			 									pop_str.add(0,pop_str_singal);
			 								}
			 								stateStack.pop();
			 							}
			 							sa.reduction_dispose(as,pop_str,fal.getVn().get(Vn_index));//规约处理
			 							analysisStack.push(fal.getVn().get(Vn_index));
			 							stateStack.push(fal.getDFA_SS().get(stateStack.get(stateStack.size()-1)).getNEXT_DFA().get(fal.getVn().get(Vn_index)).getState());
			 							charge=1;
			 							break;
			 						}
			 						else
			 						{
			 							continue;
			 						}
		 						}
		 					}
		 					if(charge==1)
		 					{
		 						
		 						charge1=1;
		 						break;
		 					}
	 					}
	 				}
	 				if(charge1!=1)
	 				{
	 					judge=1;
	 					grammar_error="分析失败:"+"第"+as.TKS.get(indexP).getTokenRow()+"行："+"后面不应该为"+Input;
	 					System.out.println("分析失败:"+"第"+as.TKS.get(indexP).getTokenRow()+"行："+"后面不应该为"+Input);
	 					break;
	 				}
	 			}

	 		}
	 		displayProcessing("");
	 		if(judge==0)
	 		{
 				grammar_error="分析成功";
	 			System.out.println("分析成功");
	 			Guadruple_Form GF=new Guadruple_Form();
	 			GF.agr1="_";
 				GF.OP="_";
 				GF.agr2="_";
 				GF.result="_";
 				GF.num=sa.Guadruple_Form_index++;
 				sa.Guadruple_Form_list.add(GF);
	 		}
	 		System.out.println("符号表：");
	 		display_symple_table();//展示符号表
	 		display_quadruple_form_table();//展示四元式
	 	}
	 	

		private void display_quadruple_form_table() {
			System.out.println("\n\n四元式：");
			for(int i=0;i<sa.Guadruple_Form_list.size();i++)
			{
				System.out.print("("+sa.Guadruple_Form_list.get(i).num+")\t"+"("+sa.Guadruple_Form_list.get(i).OP+","+sa.Guadruple_Form_list.get(i).agr1+","+sa.Guadruple_Form_list.get(i).agr2+",");
				Intermediate_code+="("+sa.Guadruple_Form_list.get(i).num+")\t"+"("+sa.Guadruple_Form_list.get(i).OP+","+sa.Guadruple_Form_list.get(i).agr1+","+sa.Guadruple_Form_list.get(i).agr2+",";
				if(sa.Guadruple_Form_list.get(i).result!=null)
				{
					if(sa.Guadruple_Form_list.get(i).result.equals("-1"))
					{
						if(sa.Guadruple_Form_list.get(i).chain!=null)
						{
							System.out.println(sa.Guadruple_Form_list.get(i).chain.chain+")");
							Intermediate_code+=sa.Guadruple_Form_list.get(i).chain.chain+")"+"\n";
						}
						else
						{
							System.out.println(sa.Guadruple_Form_list.get(i).exit.exit+")");
							Intermediate_code+=sa.Guadruple_Form_list.get(i).exit.exit+")"+"\n";
						}
					}
					else
					{
						System.out.println(sa.Guadruple_Form_list.get(i).result+")");
						Intermediate_code+=sa.Guadruple_Form_list.get(i).result+")"+"\n";
					}
				}
				else
				{
					if(sa.Guadruple_Form_list.get(i).chain!=null)
					{
						System.out.println(sa.Guadruple_Form_list.get(i).chain.chain+")");
						Intermediate_code+=sa.Guadruple_Form_list.get(i).chain.chain+")"+"\n";
					}
					else
					{
						System.out.println(sa.Guadruple_Form_list.get(i).exit.exit+")");
						Intermediate_code+=sa.Guadruple_Form_list.get(i).exit.exit+")"+"\n";
					}
				}
			}
		
	}

		private void display_symple_table() {
			System.out.println("入口\t常量名\t常量类型\t值\t");
			for(int i=0;i<sa.symbols_table.size();i++)
			{
				System.out.println(sa.symbols_table.get(i).Entry+"\t"+sa.symbols_table.get(i).Name+"\t"+sa.symbols_table.get(i).Type+"\t"+sa.symbols_table.get(i).Val);
			}
		
	}

		private void displayProcessing(String string) {
			System.out.print(step+++"\t\t\t");
			System.out.print(stateStack+"\t\t\t");
			System.out.print(analysisStack+"\t\t\t");
	 		System.out.print(InputStream.subList(+indexP, InputStream.size())+"\t\t\t");
	 		System.out.println(string);
			
		}

	
	
}

class BulidLRForm2{
	public static String epsn = "#";//设置空
  	public static String start = "S"; //开始符号
  	public static String end = "#";  //终止符号
	
  	FollowandFirst FF;
  	
  	private List<String> Vt = new ArrayList<String>();
  	//非终结符号集
  	private List<String> Vn = new ArrayList<String>();
  	//用map来代替文法
  	private Map<String, List<List<List<String>>>> grammar = new HashMap<String, List<List<List<String>>>>();
  //用map来代替文法
  	private List<DFA> DFA_SS = new ArrayList<DFA>();
  	private Map<String, List<List<List<String>>>> PFP = new HashMap<String, List<List<List<String>>>>();//所有产生式项目
  	
//已经存在的DFA
  	private List<Map<String, DFA>> exist_DFA = new ArrayList<Map<String, DFA>>();
  	
  	private Stack<DFA> DFAStack = new Stack<DFA>(); //分析栈
  	//状态表示
  	private int states = 0;
  	public List<DFA> getDFA_SS(){
  		return DFA_SS;
  	}
  	public Map<String, Set<String>> getFollowSet(){
  		return FF.getFollow();
  	}
  	public Map<String, Set<String>> getProductionFirstSet(){
  		return FF.getProductionFirst();
  	}
  	public List<String> getVt(){
  		return this.Vt;
  	}
  	public List<String> getVn(){
  		return this.Vn;
  	}
	public BulidLRForm2() {
		Grammar gm=new Grammar(grammar,Vn,Vt);
		FF=new FollowandFirst(grammar,Vn,Vt);
  		buildLR();
  		show_table();
	}  	
	
	
	private void show_table() {
		Map<String, List<List<List<String>>>> acc=new HashMap<String, List<List<List<String>>>>();
		List<List<List<String>>> acc0=new ArrayList<List<List<String>>>();
		List<List<String>> acc1=new ArrayList<List<String>>();
		List<String> acc2=new ArrayList<String>();
		acc2.add("E");
		acc2.add(".");
		List<String> acc3=new ArrayList<String>();
		acc3.add("#");
		acc1.add(acc2);
		acc1.add(acc3);
		acc0.add(acc1);
		acc.put("S", acc0);
		System.out.println("\t\t\t"+"attion"+"\t\t\t"+"goto");
		System.out.print("状态"+"\t");
		for(int Vt_index=0;Vt_index<Vt.size();Vt_index++)
		{
			System.out.print(Vt.get(Vt_index)+"\t");
		}
		for(int Vn_index=1;Vn_index<Vn.size();Vn_index++)
		{
			System.out.print(Vn.get(Vn_index)+"\t");
		}
		System.out.println();
		for(int state=0;state<DFA_SS.size();state++)
		{
			if(DFA_SS.get(state).getDFA().containsKey("S"))
			{
				if(DFA_SS.get(state).getDFA().get("S").equals(acc.get("S")))
				{
					DFA_SS.get(state).getNext_r().put("#", 0);
					continue;
				}
			}
			System.out.print(DFA_SS.get(state).getState()+"\t");
			for(int Vt_index=0;Vt_index<Vt.size();Vt_index++)
			{
				if(DFA_SS.get(state).getNEXT_DFA().get(Vt.get(Vt_index))!=null)
				{
					System.out.print("S"+DFA_SS.get(state).getNEXT_DFA().get(Vt.get(Vt_index)).getState()+"\t");
				}
				else
				{
					for(int Vn_index=0;Vn_index<Vn.size();Vn_index++)
					{
						if(DFA_SS.get(state).getDFA().get(Vn.get(Vn_index))!=null)
						{
							for(int DFA_SS_value=0;DFA_SS_value<DFA_SS.get(state).getDFA().get(Vn.get(Vn_index)).size();DFA_SS_value++)
							{
								int point_index=DFA_SS.get(state).getDFA().get(Vn.get(Vn_index)).get(DFA_SS_value).get(0).indexOf(".");
								if(point_index<DFA_SS.get(state).getDFA().get(Vn.get(Vn_index)).get(DFA_SS_value).get(0).size()-1)
								{
									continue;
								}
								else
								{
									if(DFA_SS.get(state).getDFA().get(Vn.get(Vn_index)).get(DFA_SS_value).get(1).contains(Vt.get(Vt_index)))
									{
										List<List<String>> str1=new ArrayList<List<String>>();
										List<String> str=new ArrayList<String>();
										for(int i=0;i<DFA_SS.get(state).getDFA().get(Vn.get(Vn_index)).get(0).get(0).size();i++)
								  		{
								  			if(!DFA_SS.get(state).getDFA().get(Vn.get(Vn_index)).get(0).get(0).get(i).equals("."))
								  			{
								  				str.add(DFA_SS.get(state).getDFA().get(Vn.get(Vn_index)).get(0).get(0).get(i));
								  			}
								  		}
										str1.add(str);
										int index=0;
										for(int i=0;i<Vn_index;i++)
										{
											index+=grammar.get(Vn.get(i)).size();
										}
										index+=grammar.get(Vn.get(Vn_index)).indexOf(str1);
										
										DFA_SS.get(state).getNext_r().put(Vt.get(Vt_index), index);
										System.out.print("r"+index+"\t");
										break;
									}
									else if(FF.getFollow().get(Vn.get(Vn_index))!=null)
									{
										if(FF.getFollow().get(Vn.get(Vn_index)).contains(Vt.get(Vt_index)))
										{
											List<List<String>> str1=new ArrayList<List<String>>();
											List<String> str=new ArrayList<String>();
											for(int i=0;i<DFA_SS.get(state).getDFA().get(Vn.get(Vn_index)).get(0).get(0).size();i++)
									  		{
									  			if(!DFA_SS.get(state).getDFA().get(Vn.get(Vn_index)).get(0).get(0).get(i).equals("."))
									  			{
									  				str.add(DFA_SS.get(state).getDFA().get(Vn.get(Vn_index)).get(0).get(0).get(i));
									  			}
									  		}
											str1.add(str);
											int index=0;
											for(int i=0;i<Vn_index;i++)
											{
												index+=grammar.get(Vn.get(i)).size();
											}
											index+=grammar.get(Vn.get(Vn_index)).indexOf(str1);
											
											DFA_SS.get(state).getNext_r().put(Vt.get(Vt_index), index);
											System.out.print("r"+index+"\t");
											break;
										}
									}
								}
							}
						}
					}

					System.out.print("\t");
				}
			}
			for(int Vn_index=1;Vn_index<Vn.size();Vn_index++)
			{
				if(DFA_SS.get(state).getNEXT_DFA().get(Vn.get(Vn_index))!=null)
				{
					System.out.print(DFA_SS.get(state).getNEXT_DFA().get(Vn.get(Vn_index)).getState()+"\t");
				}
				else
				{
					System.out.print("\t");
				}
			}
			System.out.println();
		}
	}

	
  	private void GET_DFA_COLONE1(DFA DFA_COLONE,DFA DFA) {//DFA完全复制
  		for(int Vn_index=0;Vn_index<Vn.size();Vn_index++)
  		{
  			String current_Vn=Vn.get(Vn_index);
  			if(DFA.getDFA().get(current_Vn)!=null)
  			{
  				List<List<List<String>>> DFA_COLONE_VALUE = new ArrayList<List<List<String>>>();
  				List<List<List<String>>> DFA_VALUE = DFA.getDFA().get(current_Vn);
  				for(int i=0;i<DFA_VALUE.size();i++)
  	  			{
  	  				List<List<String>> list1 = new ArrayList<List<String>>();
  	  				for(int j=0;j<DFA_VALUE.get(i).size();j++)
  	  				{
  	  					List<String> list2=new ArrayList<String>();
  	  					for(int k=0;k<DFA_VALUE.get(i).get(j).size();k++)
  	  					{
  	  						list2.add(DFA_VALUE.get(i).get(j).get(k));
  	  					}
  	  					list1.add(list2);
  	  				}
  	  				DFA_COLONE_VALUE.add(list1);
  	  			}
  				DFA_COLONE.getDFA().put(current_Vn, DFA_COLONE_VALUE);
  				
  			}
  		}
	}
  	
  	//闭包扩展
  	public void Closure(DFA DFA,List<String> EXIST_Vn) {
  		DFA.setNext_r("#");
  		int charge=0;
  		for(int Vn_index=0;Vn_index<Vn.size();Vn_index++)//遍历每一条产生式
  		{
  			String current_Vn=Vn.get(Vn_index);
  			if(DFA.getDFA().get(current_Vn)!=null)
  			{
  				List<List<List<String>>> DFA_COLONE =new ArrayList<List<List<String>>>();
  				GET_DFA_COLONE(DFA_COLONE,DFA.getDFA().get(current_Vn));
  				List<List<List<String>>>nArryStr = DFA_COLONE;//将文法裁开，逐个分析
  				int point_index=0;

  				int nArryStr_size=nArryStr.size();
  				for(int nArryStr_index=0;nArryStr_index<nArryStr_size;nArryStr_index++)
  				{
  					int length=DFA.getDFA().get(current_Vn).get(nArryStr_index).get(0).size();//获取当前产生式内部长度
  					point_index=DFA.getDFA().get(current_Vn).get(nArryStr_index).get(0).indexOf(".");//获取.的位置
  					if(point_index<length-1)//如果.后面还有终结符或则非终结符
  					{
  						String next_String=DFA.getDFA().get(current_Vn).get(nArryStr_index).get(0).get(point_index+1);
  						
  						if(Vn.indexOf(next_String)!=-1&&!EXIST_Vn.contains(next_String))//如果.后面一位为非终结符,并且未被扩展过，则进行扩展
  						{
  							List<String> first=new ArrayList<String>();
  							if(point_index<length-2)//如果非终结符后面还有字符。则求first集合
  							{
  								String second_String=DFA.getDFA().get(current_Vn).get(nArryStr_index).get(0).get(point_index+2);
  								if(Vt.indexOf(second_String)!=-1)
  								{
  									first.add(second_String);
  								}
  								else
  								{
  									first.addAll(FF.getFirst().get(second_String));
  								}
  							}
  							else
  							{
  								first.addAll(DFA.getDFA().get(Vn.get(Vn_index)).get(nArryStr_index).get(1));
  							}
							List<List<List<String>>> DFA_COLONE1 =new ArrayList<List<List<String>>>();
							GET_DFA_COLONE(DFA_COLONE1,PFP.get(next_String));
							if(DFA.getDFA().get(next_String)==null)
							{
								for(int DFA_COLONE1_index=0;DFA_COLONE1_index<DFA_COLONE1.size();DFA_COLONE1_index++)
								{
									DFA_COLONE1.get(DFA_COLONE1_index).get(1).addAll(first);
								}
								DFA.getDFA().put(next_String, DFA_COLONE1);
							}
							else
							{
								for(int DFA_COLONE1_index=0;DFA_COLONE1_index<DFA_COLONE1.size();DFA_COLONE1_index++)
								{
									DFA_COLONE1.get(DFA_COLONE1_index).get(1).addAll(first);
									DFA.getDFA().get(next_String).add(DFA_COLONE1.get(DFA_COLONE1_index));
										
									
								}
							}
  	  						EXIST_Vn.add(next_String);
  	  						charge=1;
  						}
  					}
  				}
  			}
  		}
  		if(charge==1)
  		{
  			Closure(DFA, EXIST_Vn);
  		}
	}
  	

	private void buildLR() {
		for(int g_n=0;g_n<grammar.size();g_n++)//g_n:grammar序号下标
		{
			List<List<List<String>>> nArryStr = grammar.get(Vn.get(g_n));//将文法按照“|”裁开，逐个分析
			if(Vn.get(g_n).equals("Program"))
			{
				System.out.println(grammar.get(Vn.get(g_n)));
			}
			for(int nArryStr_indedx=0;nArryStr_indedx<nArryStr.size();nArryStr_indedx++)
			{
					if(PFP.get(Vn.get(g_n))==null)
					{
						List<List<List<String>>> PFP_put=new ArrayList<List<List<String>>>();
						List<List<String>> newstr=new ArrayList<List<String>>();
						List<String> newstr1=new ArrayList<String>();
						newstr1.add(".");
						newstr1.addAll(nArryStr.get(nArryStr_indedx).get(0));
						newstr.add(newstr1);
						List<String> yc1= new ArrayList<String>();
						newstr.add(yc1);
						PFP_put.add(newstr);
						PFP.put(Vn.get(g_n),PFP_put);
					}
					else
					{
						List<List<List<String>>> PFP_put=new ArrayList<List<List<String>>>();
						List<List<String>> newstr=new ArrayList<List<String>>();
						List<String> newstr1=new ArrayList<String>();
						newstr1.add(".");
						newstr1.addAll(nArryStr.get(nArryStr_indedx).get(0));
						newstr.add(newstr1);
						PFP_put.add(newstr);
						List<String> yc1= new ArrayList<String>();
						newstr.add(yc1);
						for(int PFP_index=0;PFP_index<PFP.get(Vn.get(g_n)).size();PFP_index++)
						{
							if(!PFP_put.contains(PFP.get(Vn.get(g_n)).get(PFP_index)))
							{
								PFP_put.add(PFP.get(Vn.get(g_n)).get(PFP_index));
							}
						}
						PFP.put(Vn.get(g_n),PFP_put);
						
					}
			}
			
		}
		System.out.println("PFP:"+PFP);
		List<String> EXIST_Vn=new ArrayList<String>();
		DFA DFA=new DFA();
		List<List<List<String>>> DFA_COLONE =new ArrayList<List<List<String>>>();
		GET_DFA_COLONE(DFA_COLONE,PFP.get(Vn.get(0)));
		DFA.getDFA().put("S", DFA_COLONE);
		DFA.getDFA().get("S").get(0).get(1).add("#");
		Closure(DFA,EXIST_Vn);//获取第一个产生式S->E的Closure
		System.out.println("DFA:"+DFA.getDFA());
		DFAStack.push(DFA);
		while(DFAStack.size()!=0)
		{
			try {
				DFA_SS.add(GET_DFA());
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("状态长度为："+DFA_SS.size());
	}
	
	//克隆DFA，防止地址错误
	public void GET_DFA_COLONE(List<List<List<String>>> DFA_COLONE_VALUE,List<List<List<String>>> DFA_VALUE) {

		for(int i=0;i<DFA_VALUE.size();i++)
		{
			List<List<String>> list1 = new ArrayList<List<String>>();
			for(int j=0;j<DFA_VALUE.get(i).size();j++)
			{
				List<String> list2=new ArrayList<String>();
				for(int k=0;k<DFA_VALUE.get(i).get(j).size();k++)
				{
					list2.add(DFA_VALUE.get(i).get(j).get(k));
				}
				list1.add(list2);
			}
			DFA_COLONE_VALUE.add(list1);
		}
	}
	
	//获取DFA
	private DFA GET_DFA() throws CloneNotSupportedException {
		if(DFAStack.size()==0)
		{
			return null;
		}
		DFA DFA=DFAStack.pop();
		System.out.println(states+":"+DFA.getDFA());
		if(DFA.getState()!=-1)
		{
			return null;
		}
		DFA.setState(states++);

		for(int Vn_index=0;Vn_index<Vn.size();Vn_index++)//判断每一个终结符
		{
			DFA DFA1 = new DFA();

			List<String> EXIST_Vn=new ArrayList<String>();
			for(int Vn_index1=0;Vn_index1<Vn.size();Vn_index1++)//判断每一个终结符
			{
				List<List<List<String>>> exp = new ArrayList<List<List<String>>>();
				if(DFA.getDFA().get(Vn.get(Vn_index1))!=null)
				{
					List<List<List<String>>> DFA_COLONE =new ArrayList<List<List<String>>>();
					GET_DFA_COLONE(DFA_COLONE,DFA.getDFA().get(Vn.get(Vn_index1)));
					List<List<List<String>>> nArryStr = DFA_COLONE;//将文法按照“|”裁开，逐个分析
					for(int nArryStr_index=0;nArryStr_index<nArryStr.size();nArryStr_index++)
					{
						int point_index=nArryStr.get(nArryStr_index).get(0).indexOf(".");//.的位置
						if(point_index==nArryStr.get(nArryStr_index).get(0).size()-1)//如果.在最后一位
						{
							continue;
						}
						else
						{
							String NEXT_String=nArryStr.get(nArryStr_index).get(0).get(point_index+1);//取.后面一位字符
							if(NEXT_String.equals(Vn.get(Vn_index)))//如果.后面一个字符为当前可以移进字符，则移进
							{
								nArryStr.get(nArryStr_index).get(0).remove(point_index);
								nArryStr.get(nArryStr_index).get(0).add(point_index+1, ".");
								exp.add(nArryStr.get(nArryStr_index));
							}
						}
						
					}
					if(DFA1.getDFA().get(Vn.get(Vn_index1))==null)
					{
						if(exp.size()!=0)
						{
							DFA1.getDFA().put(Vn.get(Vn_index1), exp);
							Closure(DFA1, EXIST_Vn);
						}
					}
					else
					{
						exp.addAll(DFA1.getDFA().get(Vn.get(Vn_index1)));
						if(exp.size()!=0)
						{
							DFA1.getDFA().put(Vn.get(Vn_index1),exp);
							Closure(DFA1, EXIST_Vn);
						}
					}
				}
			}

			if(DFA1.getDFA().size()!=0)//判断是否有值，有值才考虑加入
			{
				int charge=0;
				int exit_dfa_index;
				for(exit_dfa_index=0;exit_dfa_index<exist_DFA.size();exit_dfa_index++)
				{
					if(exist_DFA.get(exit_dfa_index).get(Vn.get(Vn_index))!=null)
					{
						if(exist_DFA.get(exit_dfa_index).get(Vn.get(Vn_index)).getDFA().equals(DFA1.getDFA()))
						{
							charge=1;
							break;
						}
					}
				}
				if(charge==0)
				{
					Map<String, DFA> NEXT_DFA = new HashMap<String, DFA>();
					NEXT_DFA.put(Vn.get(Vn_index), DFA1);
					exist_DFA.add(NEXT_DFA);
					DFAStack.add(0,DFA1);
					DFA.setNEXT_DFA(Vn.get(Vn_index),DFA1);
					DFA.getNEXT_DFA().get(Vn.get(Vn_index)).setDFA(DFA1.getDFA());
	
				}
				else
				{
					DFA.setNEXT_DFA(Vn.get(Vn_index),exist_DFA.get(exit_dfa_index).get(Vn.get(Vn_index)));
				}
			}
		}
		
		
		for(int Vt_index=0;Vt_index<Vt.size();Vt_index++)//判断每一个终结符
		{
			DFA DFA1 = new DFA();
			List<String> EXIST_Vn=new ArrayList<String>();
			for(int Vn_index1=0;Vn_index1<Vn.size();Vn_index1++)//判断每一个终结符
			{
				List<List<List<String>>> exp = new ArrayList<List<List<String>>>();
				if(DFA.getDFA().get(Vn.get(Vn_index1))!=null)
				{
					List<List<List<String>>> DFA_COLONE =new ArrayList<List<List<String>>>();
					GET_DFA_COLONE(DFA_COLONE,DFA.getDFA().get(Vn.get(Vn_index1)));
					List<List<List<String>>> nArryStr = DFA_COLONE;//将文法按照“|”裁开，逐个分析
					for(int nArryStr_index=0;nArryStr_index<nArryStr.size();nArryStr_index++)
					{
						int point_index=nArryStr.get(nArryStr_index).get(0).indexOf(".");//.的位置
						
						if(point_index==nArryStr.get(nArryStr_index).get(0).size()-1)//如果.在最后一位
						{
							continue;
						}
						else
						{
							String NEXT_String=nArryStr.get(nArryStr_index).get(0).get(point_index+1);//取.后面一位字符
							if(NEXT_String.equals(Vt.get(Vt_index)))//如果.后面一个字符为当前可以移进字符，则移进
							{
//								System.out.println(nArryStr);
								nArryStr.get(nArryStr_index).get(0).remove(point_index);
								nArryStr.get(nArryStr_index).get(0).add(point_index+1, ".");
//								System.out.println(nArryStr);
								exp.add(nArryStr.get(nArryStr_index));
//								System.out.println("exp----"+exp);
							}
						}
						
					}
					if(DFA1.getDFA().get(Vn.get(Vn_index1))==null)
					{
						if(exp.size()!=0)
						{
							DFA1.getDFA().put(Vn.get(Vn_index1), exp);
							Closure(DFA1, EXIST_Vn);
						}
					}
					else
					{
						exp.addAll(DFA1.getDFA().get(Vn.get(Vn_index1)));
						if(exp.size()!=0)
						{
							DFA1.getDFA().put(Vn.get(Vn_index1), exp);
							Closure(DFA1, EXIST_Vn);
						}
					}
				}
			}

			if(DFA1.getDFA().size()!=0)//判断是否有值，有值才考虑加入
			{
				int charge=0;
				int exit_dfa_index;
				for(exit_dfa_index=0;exit_dfa_index<exist_DFA.size();exit_dfa_index++)
				{
					if(exist_DFA.get(exit_dfa_index).get(Vt.get(Vt_index))!=null)
					{
						if(exist_DFA.get(exit_dfa_index).get(Vt.get(Vt_index)).getDFA().equals(DFA1.getDFA()))
						{
							charge=1;
							break;
						}
					}
				}
				if(charge==0)//如果还未产生重复的DFA，则将这次的DAF加入
				{
					Map<String, DFA> NEXT_DFA = new HashMap<String, DFA>();
					NEXT_DFA.put(Vt.get(Vt_index), DFA1);
					exist_DFA.add(NEXT_DFA);
					DFAStack.add(0,DFA1);
					DFA.setNEXT_DFA(Vt.get(Vt_index),DFA1);
					DFA.getNEXT_DFA().get(Vt.get(Vt_index)).setDFA(DFA1.getDFA());
				}
				else
				{
					DFA.setNEXT_DFA(Vt.get(Vt_index),exist_DFA.get(exit_dfa_index).get(Vt.get(Vt_index)));
				}
			}
		}
		return DFA;
	}
}