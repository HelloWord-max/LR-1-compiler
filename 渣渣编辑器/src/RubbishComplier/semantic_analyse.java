package RubbishComplier;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class semantic_analyse {
 	private Stack<Semantic_rule> Production_form_stack = new Stack<Semantic_rule>();
 	List<Symbols_table> symbols_table=new ArrayList<Symbols_table>();
 	List<Guadruple_Form> Guadruple_Form_list=new ArrayList<Guadruple_Form>();
 	Stack<CHAIN> chain_stack=new Stack<CHAIN>();
 	Stack<EXIT> exit_stack=new Stack<EXIT>();
 	Stack<RETURN> return_stack=new Stack<RETURN>();
 	private int id_num=0;
 	int symbols_table_index=-1;
 	int result_index=1;
 	int Guadruple_Form_index=1;
	EXIT exit_return=new EXIT();
	public semantic_analyse() {
		// TODO Auto-generated constructor stub
	}
	
	//处理每次规约后、语法分析
	public void reduction_dispose(words_analyse as,List<String> old_str,String reduction_one) {
		if(reduction_one.equals("Variable"))//如果为标识符，则入标识符栈
		{
			Semantic_rule sr=new Semantic_rule();
			sr.Name=old_str.get(0);
			Production_form_stack.add(sr);
		}
		else if(reduction_one.equals("Constant"))
		{
			Semantic_rule sr=new Semantic_rule();
			sr.val=old_str.get(0);
			Production_form_stack.add(sr);
		}
		else if(reduction_one.equals("Factor"))
		{
			Semantic_rule pf = Production_form_stack.pop();
			Semantic_rule sr=new Semantic_rule();
			sr.val=pf.val;
			sr.Name=pf.Name;
			Production_form_stack.add(sr);
		}
		else if(reduction_one.equals("Item"))
		{
			if(old_str.size()==1)
			{
				Semantic_rule pf = Production_form_stack.pop();
				Semantic_rule sr=new Semantic_rule();
				sr.val=pf.val;
				sr.Name=pf.Name;
				Production_form_stack.add(sr);
			}
			else
			{
				Semantic_rule pf1 = Production_form_stack.pop();
				Semantic_rule pf2 = Production_form_stack.pop();
				int val1=-1;
				int val2=-1;
				String name1=pf1.Name;
				String name2=pf2.Name;
				if(pf1.val!=null)
				{

					val1=Integer.valueOf(pf1.val);
				}
				else
				{
					for(int symbols_table_index=0;symbols_table_index<symbols_table.size();symbols_table_index++)
					{
						if(pf1.Name.equals(symbols_table.get(symbols_table_index).Name))
						{
							name1=symbols_table.get(symbols_table_index).Name;
							break;
						}
					}
				}
				if(pf2.val!=null)
				{

					val2=Integer.valueOf(pf2.val);
				}
				else
				{
					for(int symbols_table_index=0;symbols_table_index<symbols_table.size();symbols_table_index++)
					{
						if(pf2.Name.equals(symbols_table.get(symbols_table_index).Name))
						{
							name2=symbols_table.get(symbols_table_index).Name;
							break;
						}
					}
				}
				
				
				Guadruple_Form GF=new Guadruple_Form();
				if(name1!=null)
				{
					GF.agr1=name1;
				}
				else
				{
					GF.agr1=String.valueOf(val1);
				}
				if(name2!=null)
				{
					GF.agr2=name2;
				}
				else
				{
					GF.agr2=String.valueOf(val2);
				}
				GF.OP=old_str.get(1);
				GF.result="T"+String.valueOf(result_index++);
				GF.num=Guadruple_Form_index++;
				Guadruple_Form_list.add(GF);
				
				Semantic_rule sr=new Semantic_rule();
				switch(old_str.get(1))
				{
					case "+":
					{
						sr.val=String.valueOf(val1+val2);
						break;
					}
					case "-":
					{
						sr.val=String.valueOf(val1-val2);
						break;
					}
					case "*":
					{
						sr.val=String.valueOf(val1*val2);
						break;
					}
					case "/":
					{
						sr.val=String.valueOf(val1/val2);
						break;
					}
				}
				sr.Name=GF.result;
				Production_form_stack.add(sr);
			}
		}
		else if(reduction_one.equals("Arithmetic_expression"))
		{
			if(old_str.size()==1)
			{
				Semantic_rule pf = Production_form_stack.pop();
				Semantic_rule sr=new Semantic_rule();
				sr.val=pf.val;
				sr.Name=pf.Name;
				Production_form_stack.add(sr);
			}
			else
			{
				Semantic_rule pf1 = Production_form_stack.pop();
				Semantic_rule pf2 = Production_form_stack.pop();
				int val1 = -1;
				int val2 = -1;
				String name1=pf1.Name;
				String name2=pf2.Name;
				if(pf1.val!=null)
				{

					val1=Integer.valueOf(pf1.val);
				}
				else
				{
					for(int symbols_table_index=0;symbols_table_index<symbols_table.size();symbols_table_index++)
					{
						if(pf1.Name.equals(symbols_table.get(symbols_table_index).Name))
						{
							name1=symbols_table.get(symbols_table_index).Name;
							break;
						}
					}
				}
				
				
				Guadruple_Form GF=new Guadruple_Form();
				if(name1!=null)
				{
					GF.agr1=name1;
				}
				else
				{
					GF.agr1=String.valueOf(val1);
				}
				if(name2!=null)
				{
					GF.agr2=name2;
					
				}
				else
				{
					val2=Integer.valueOf(pf2.val);
					GF.agr2=String.valueOf(val2);
				}
				GF.OP=old_str.get(1);
				
				GF.result="T"+String.valueOf(result_index++);
				GF.num=Guadruple_Form_index++;
				Guadruple_Form_list.add(GF);
				
				Semantic_rule sr=new Semantic_rule();
				switch(old_str.get(1))
				{
					case "+":
					{
						sr.val=String.valueOf(val1+val2);
						break;
					}
					case "-":
					{
						sr.val=String.valueOf(val1-val2);
						break;
					}
					case "*":
					{
						sr.val=String.valueOf(val1*val2);
						break;
					}
					case "/":
					{
						sr.val=String.valueOf(val1/val2);
						break;
					}
				}
				sr.Name=GF.result;
				System.out.println(sr.Name);
				Production_form_stack.add(sr);
			}
		}
		else if(reduction_one.equals("Expression"))
		{
			if(chain_stack.size()!=0)
			{
				if(chain_stack.lastElement().judge_type.equals("for"))//如果当前链为for循环
				{
					if(chain_stack.lastElement().exp<3)//如果为for循环第二个语句，则加一，否则回填
					{
						if(chain_stack.lastElement().exp==1)
						{
							RETURN rt=new RETURN();
							rt.return_index=Guadruple_Form_index;
							rt.return_type="for";
							return_stack.add(rt);
						}
						chain_stack.lastElement().exp++;
					}
					else if(chain_stack.lastElement().exp==3)
					{
						chain_stack.lastElement().chain=Guadruple_Form_index+1;
						chain_stack.pop();
					}
				}
			}
			
			Semantic_rule pf = Production_form_stack.pop();
			Semantic_rule sr=new Semantic_rule();
			sr.val=pf.val;
			sr.Name=pf.Name;
			Production_form_stack.add(sr);
		}
		else if(reduction_one.equals("Arithmetic_expression_type"))
		{
			Semantic_rule pf = Production_form_stack.pop();
			Semantic_rule sr=new Semantic_rule();
			sr.val=pf.val;
			sr.Name=pf.Name;
			sr.OP=pf.OP;
			Production_form_stack.add(sr);
		}
		else if(reduction_one.equals("Initial_value_expression"))
		{
			Semantic_rule pf1 = Production_form_stack.pop();
			Semantic_rule pf2 = Production_form_stack.pop();
			int val1=-1;
			if(pf1.val!=null)
			{
				val1=Integer.valueOf(pf1.val);
			}
			int val2 = -1;
			String name1=pf1.Name;
			String name2=pf2.Name;
			
			Guadruple_Form GF=new Guadruple_Form();
			if(pf1.OP!=null)//如果为++或者--
			{
				GF.agr1=pf2.Name;
				switch(pf1.OP)
				{
					case "++":
					{
						break;
					}
					case "--":
					{
						break;
					}
				}
				GF.OP=pf1.OP;
				GF.agr2="_";
				GF.result=name2;
			}
			else//为=
			{
				if(name1!=null)
				{
					GF.agr1=name1;
				}
				else
				{
					GF.agr1=String.valueOf(val1);
				}
				GF.OP="=";
				GF.agr2="_";
				GF.result=name2;
			}
			GF.num=Guadruple_Form_index++;
			Guadruple_Form_list.add(GF);
		}
		else if(reduction_one.equals("Single_sample_variable"))
		{
			symbols_table_index++;
			Symbols_table st=new Symbols_table();
			if(old_str.size()==1)
			{
				Semantic_rule pf = Production_form_stack.pop();
				Semantic_rule sr=new Semantic_rule();
				sr.val=pf.val;
				sr.Name=pf.Name;
				st.Name=pf.Name;
				st.Entry=id_num++;
				symbols_table.add(st);
				Production_form_stack.add(sr);
			}
			else
			{
				Semantic_rule pf1 = Production_form_stack.pop();
				Semantic_rule pf2 = Production_form_stack.pop();
				int val1=-1;
				String name1=pf1.Name;
				String name2=pf2.Name;
				if(pf1.val!=null)
				{
					val1=Integer.valueOf(pf1.val);
				}
				else
				{
					for(int symbols_table_index=0;symbols_table_index<symbols_table.size();symbols_table_index++)
					{
						if(pf1.Name.equals(symbols_table.get(symbols_table_index).Name))
						{
							name1=symbols_table.get(symbols_table_index).Name;
							break;
						}
					}
				}
				
				
				Semantic_rule sr=new Semantic_rule();
				st.Val=String.valueOf(val1);
				st.Name=name2;
				st.Entry=id_num++;
				symbols_table.add(st);
				
				Guadruple_Form GF=new Guadruple_Form();
				if(name1!=null)
				{
					GF.agr1=name1;
				}
				else
				{
					GF.agr1=String.valueOf(val1);
				}
				GF.OP="=";
				GF.agr2="_";
				GF.result=name2;
				GF.num=Guadruple_Form_index++;
				Guadruple_Form_list.add(GF);
				sr.Name=GF.result;
				Production_form_stack.add(sr);
			}
			
		}
		else if(reduction_one.equals("Single_variable_declaration"))
		{
			Semantic_rule pf = Production_form_stack.pop();
			Semantic_rule sr=new Semantic_rule();
			sr.val=pf.val;
			sr.Name=pf.Name;
			Production_form_stack.add(sr);
		}
		else if(reduction_one.equals("Variable_Declaration_Table"))
		{
			symbols_table.get(symbols_table.size()-1-symbols_table_index).Type=Production_form_stack.get(Production_form_stack.size()-2-symbols_table_index).type;
			symbols_table_index--;
			Production_form_stack.pop();
//			if(old_str.size()==2)
//			{
//				symbols_table.get(symbols_table.size()-1).Type=Production_form_stack.get(Production_form_stack.size()-1).type;
//			}
//			else
//			{
//				for(int i=0;i<old_str.size();i++)
//				{
//					symbols_table.get(symbols_table.size()-1-i).Type=Production_form_stack.get(Production_form_stack.size()-1).type;
//				}
//			}
		}
		else if(reduction_one.equals("Variable_declaration"))
		{
			Semantic_rule sr=new Semantic_rule();
			sr.val=old_str.get(0);
		}
		else if(reduction_one.equals("Value_declaration"))
		{
			Semantic_rule sr=new Semantic_rule();
			sr.val=old_str.get(0);
		}
		else if(reduction_one.equals("Declaration_statement"))
		{
			Semantic_rule sr=new Semantic_rule();
			sr.val=old_str.get(0);
		}
		else if(reduction_one.equals("Sentence"))
		{
			Semantic_rule sr=new Semantic_rule();
			sr.val=old_str.get(0);
		}
		else if(reduction_one.equals("Variable_type"))//如果为声明
		{
			Semantic_rule sr=new Semantic_rule();
			sr.type=old_str.get(0);
			Production_form_stack.add(sr);
		}
		else if(reduction_one.equals("Bool_factor"))
		{
			
			Semantic_rule pf = Production_form_stack.pop();
			if(pf.add==0)
			{
				Semantic_rule sr=new Semantic_rule();
				sr.val=pf.val;
				sr.Name=pf.Name;
				Production_form_stack.add(sr);
				//两种情况之一
				Guadruple_Form GF=new Guadruple_Form();
				GF.agr1=pf.Name;
				GF.OP="jnz";
				GF.agr2="_";
				GF.num=Guadruple_Form_index++;
				GF.result=String.valueOf(GF.num+2);
				Guadruple_Form_list.add(GF);
				//两种情况之二
				Guadruple_Form GF_false=new Guadruple_Form();
				GF_false.agr1="_";
				GF_false.OP="j";
				GF_false.agr2="_";
				GF_false.result="-1";
				GF_false.chain=chain_stack.lastElement();
				GF_false.num=Guadruple_Form_index++;
				Guadruple_Form_list.add(GF_false);
			}
			
			
		}
		else if(reduction_one.equals("Relational_expression"))
		{
			Semantic_rule pf=new Semantic_rule();
			Semantic_rule pf1 = Production_form_stack.pop();
			Semantic_rule pf2 = Production_form_stack.pop();
			Semantic_rule pf3 = Production_form_stack.pop();
			//两种情况之一
			Guadruple_Form GF=new Guadruple_Form();
			if(pf3.Name!=null)
			{
				GF.agr1=pf3.Name;
			}
			else
			{
				GF.agr1=pf3.val;
			}
			if(pf1.Name!=null)
			{
				GF.agr2=pf1.Name;
			}
			else
			{
				GF.agr2=pf1.val;
			}
			GF.OP="j"+pf2.Name;
			GF.num=Guadruple_Form_index++;
			if(chain_stack.lastElement().judge_type.equals("if"))//如果为if语句，真出口就加2
			{
				GF.result=String.valueOf(GF.num+2);
			}
			else if(chain_stack.lastElement().judge_type.equals("for"))//如果为for语句，真出口在下一条语句结束
			{
				GF.chain=chain_stack.lastElement();
			}
			else if(chain_stack.lastElement().judge_type.equals("while"))//如果为while语句，真出口在下一条语句结束
			{
				GF.result=String.valueOf(GF.num+2);
			}
			Guadruple_Form_list.add(GF);
			//两种情况之二
			Guadruple_Form GF_false=new Guadruple_Form();
			GF_false.agr1="_";
			GF_false.OP="j";
			GF_false.agr2="_";
			if(chain_stack.lastElement().judge_type.equals("if"))//如果为if语句，假出口就在if语句结束
			{
				GF_false.result="-1";
				GF_false.chain=chain_stack.lastElement();
			}
			else if(chain_stack.lastElement().judge_type.equals("for"))//如果为for语句，假出口在for整个语句结束
			{
				EXIT EI=new EXIT();
				exit_stack.add(EI);
				GF_false.exit=exit_stack.lastElement();
			}
			else if(chain_stack.lastElement().judge_type.equals("while"))//如果为while语句，假出口在while整个语句结束
			{
				EXIT EI=new EXIT();
				exit_stack.add(EI);
				GF_false.exit=exit_stack.lastElement();
			}
			GF_false.num=Guadruple_Form_index++;
			Guadruple_Form_list.add(GF_false);
			pf.add=1;//判断是否已经添加符号
			Production_form_stack.add(pf);
		}
		else if(reduction_one.equals("Relational_operators"))
		{
			if(old_str.size()==1)
			{
				Semantic_rule sr=new Semantic_rule();
				sr.Name=old_str.get(0);
				Production_form_stack.add(sr);
			}
		}
		else if(reduction_one.equals("Bool_item"))
		{
			
		}
		else if(reduction_one.equals("Boolean_expression"))
		{

		}
		else if(reduction_one.equals("If_statement"))
		{
			exit_stack.lastElement().exit=Guadruple_Form_index;
			exit_stack.pop();
		}
		else if(reduction_one.equals("Initial_value"))
		{
			Semantic_rule pf = Production_form_stack.pop();
			Semantic_rule sr=new Semantic_rule();
			sr.val=pf.val;
			sr.Name=pf.Name;
			Production_form_stack.add(sr);
		}
		else if(reduction_one.equals("Double_operator"))
		{
			Semantic_rule sr=new Semantic_rule();
			sr.OP=old_str.get(0);
			Production_form_stack.add(sr);
		}
		else if(reduction_one.equals("For_statement"))
		{
			exit_stack.lastElement().exit=Guadruple_Form_index;
			exit_stack.pop();
		}
		else if(reduction_one.equals("While_statement"))
		{
			exit_stack.lastElement().exit=Guadruple_Form_index;
			exit_stack.pop();
		}
		
		else if(reduction_one.equals("Loop_statement"))
		{
			Guadruple_Form GF=new Guadruple_Form();
 			GF.agr1="_";
			GF.OP="j";
			GF.agr2="_";
			GF.result=String.valueOf(return_stack.pop().return_index);
			GF.num=Guadruple_Form_index++;
			Guadruple_Form_list.add(GF);
		}
//		else if(reduction_one.equals("Statement_table"))
//		{
//			Guadruple_Form GF=new Guadruple_Form();
// 			GF.agr1="_";
//			GF.OP="j";
//			GF.agr2="_";
//			GF.result=String.valueOf(for_return_stack.pop().for_return_index);
//			GF.num=Guadruple_Form_index++;
//			Guadruple_Form_list.add(GF);
//			
//		}
		else if(reduction_one.equals("Return_statement"))
		{
			Guadruple_Form GF=new Guadruple_Form();
 			GF.agr1="_";
			GF.OP="ret";
			GF.agr2="_";
			GF.num=Guadruple_Form_index++;
			Guadruple_Form_list.add(GF);
			if(old_str.size()<=2)
			{
				GF.result="_";
			}
			else
			{
				if(Production_form_stack.lastElement().Name!=null)
				{
					GF.result=Production_form_stack.lastElement().Name;
				}
				else if(Production_form_stack.lastElement().val!=null)
				{
					GF.result=Production_form_stack.lastElement().val;
				}
				else
				{
					GF.result="@@@";
				}
				
			}
		}
	}
}
