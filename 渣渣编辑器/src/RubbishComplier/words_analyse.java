package RubbishComplier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;

//对文件或者输入的源程序进行词法分析


public class words_analyse {
	List<Token> TKS=new ArrayList<Token>();
	String[] keywords= {"main","if","then","while","do","static","int","double","struct","break","else","long","switch","case","typedef","char","return","const","float","short","continue","for","void","sizeof","ID","NUM","include"};
	String[] keywords_tc= {"1001","1002","1003","1004","1005","1006","1007","1008","1009","1010","1011","1012","1013","1014","1015","1016","1017","1018","1019","1020","1021","1022","1023","1024","1025","1026","1027"};
	List<String> keywords_list=Arrays.asList(keywords); 
	
	String[] opera_characters_first= {"+","-","*","/","*","=","<","<",">","!","&","|",":",",","%"};
	String[] opera_characters= {"+","++","-","--","*","/","**","==","<","<>","<=",">",">=","=","!=","&&","||","&","|",":",",","%"};
	String[] opera_characters_tc= {"2001","2002","2003","2004","2005","2006","2007","2008","2009","2010","2011","2012","2013","2014","2015","2016","2017","2018","2019","2020","2021","2022"};
	List<String> opera_characters_list=Arrays.asList(opera_characters); 
	
	String[] boundary_symbol= {"[","]",";","(",")","#","{","}"};
	String[] boundary_symbol_tc= {"3001","3002","3003","3004","3005","3006","3007","3008"};
	List<String> boundary_symbol_list=Arrays.asList(boundary_symbol); 
	
	String encoding="UTF-8";
	String essay="";
	int index=0;
	int row_num=0;
	
	ArrayList<String> words=new ArrayList<String>();
	ArrayList<String> Type_code=new ArrayList<String>();
	ArrayList<String> words_Type=new ArrayList<String>();
	ArrayList<String> error_words=new ArrayList<String>();
	
	public words_analyse(Tab path){
			get_filename_text(path);//获取文件信息
			text_dispose();
	}
	
	public void text_dispose() {
		for(index=0;index<essay.length();index++)
		{
			if(essay.charAt(index)=='\n')
			{
				row_num++;//如果为换行符，行数加一
			}
			else if(essay.charAt(index)>='0'&&essay.charAt(index)<='9')
			{
				get_number();//获取整数
			}
			else if(essay.charAt(index)>='a'&&essay.charAt(index)<='z'||essay.charAt(index)>='A'&&essay.charAt(index)<='Z'||essay.charAt(index)=='_')
			{
				get_variable();//获取标识符、关键字
			}
			else if(Arrays.asList(boundary_symbol).contains(String.valueOf(essay.charAt(index))))
			{
				get_boundary_symbol();//获取界符
			}
			else if(Arrays.asList(opera_characters_first).contains(String.valueOf(essay.charAt(index))))
			{
				if(((essay.charAt(index)=='/')&&essay.charAt(index+1)=='*')||((essay.charAt(index)=='/')&&essay.charAt(index+1)=='/'))
				{
					get_note();//获取注释
				}
				else
				{
					get_opera_characters();//获取运算符
				}
			}
			else if(essay.charAt(index)=='/')
			{
				get_note();//获取注释
			}
			else if(essay.charAt(index)=='&'&&essay.charAt(index+1)!='&')
			{
				words_Type.add("寻址符号：\t");
				words.add(String.valueOf(essay.charAt(index)));
				Type_code.add("0001");
			}
			else if(essay.charAt(index)=='\"')//获取字符串
			{
				get_string();
			}
			else if(essay.charAt(index)=='\'')//获取字符
			{
				get_inverted_comma();
			}
			else if(essay.charAt(index)!=' '&&essay.charAt(index)!='\n'&&essay.charAt(index)!='\t')
			{
				Token TK= new Token();
				TK.TokenCode="-1";
				TK.TokenName=String.valueOf(essay.charAt(index));
				TK.TokenRow=row_num;
				TKS.add(TK);
				error_words.add("行号:"+row_num+"\t不识别字符:"+String.valueOf(essay.charAt(index)));
				words_Type.add("不识别字符：\t");
				words.add(String.valueOf(essay.charAt(index)));
				Type_code.add("");
			}
			
		}
		//get_colnum();
		//get_characters_num();
		
	}
	
	//获取单引号内容
	public void get_inverted_comma() {
		char state=0;
		String inverted_comma="";
		boolean error=false;
		while(state!=2)
		{
			switch(state)
			{
				case 0:
				{
					if(essay.charAt(index)=='\'')
					{
						state=1;
						index++;
					}
					break;
				}
				case 1:
				{
					if(essay.charAt(index)!='\'')
					{
						inverted_comma+=essay.charAt(index);
						index++;
						state=3;
					}
					else
					{
						state=3;
					}
					break;
				}
				case 3:
				{
					if(essay.charAt(index)=='\'')
					{
						state=2;
					}
					else
					{
						inverted_comma+=essay.charAt(index);
						error=true;
						index++;
					}
					break;
				}
			}
		}
		if(error==true)
		{
			error_words.add("行号:"+row_num+"\t单引号错误:"+inverted_comma);
		}
		else
		{
			Token TK= new Token();
			TK.TokenCode="500";
			TK.TokenName=inverted_comma;
			TK.TokenRow=row_num;
			TKS.add(TK);
			words_Type.add("字符：\t");
//			words.add(inverted_comma);
			words.add("500");
			Type_code.add("0200");
		}
	}
	
	//获取双引号内容
	public void get_string() {
		char state=0;
		String string="";
		boolean error=false;
		while(state!=2)
		{
			System.out.println(essay.charAt(index));
			switch(state)
			{
				case 0:
				{
					if(essay.charAt(index)=='\"')
					{
						state=1;
						index++;
					}
					break;
				}
				case 1:
				{
					if(essay.charAt(index)=='\n')
					{
						state=2;
						error=true;
						index++;
						
					}
					else if(essay.charAt(index)!='\"')
					{
						string+=essay.charAt(index);
						index++;
					}
					else
					{
						state=3;
					}
					break;
				}
				case 3:
				{
					if(essay.charAt(index)=='\"')
					{
						state=2;
						index++;
					}
					else if(essay.charAt(index)=='\n')
					{
						state=2;
						error=true;
						index++;
					}
					break;
				}
				
			}
		}
		if(error==true) 
		{
			error_words.add("行号:"+row_num+"\t双引号错误:"+string);
		}
		else
		{
			Token TK= new Token();
			TK.TokenCode="600";
			TK.TokenName=string;
			TK.TokenRow=row_num;
			TKS.add(TK);
			words_Type.add("字符串：\t");
			words.add("600");
//			words.add(string);
			Type_code.add("0100");
		}
	}
	
	
	//获取界符
	public void get_boundary_symbol() {
		Token TK= new Token();
		TK.TokenCode=boundary_symbol_tc[boundary_symbol_list.indexOf(String.valueOf(essay.charAt(index)))];
		TK.TokenName=String.valueOf(essay.charAt(index));
		TK.TokenRow=row_num;
		TKS.add(TK);
		words_Type.add("界符：\t");
		words.add(String.valueOf(essay.charAt(index)));
		Type_code.add(boundary_symbol_tc[boundary_symbol_list.indexOf(String.valueOf(essay.charAt(index)))]);
	}
	
	//获取注释
	public void get_note() {
		char state=0;
		while(state!=2)
		{
			switch(state)
			{
				case 0:
				{
					if(essay.charAt(index)=='/')
					{
						state=1;
						index++;
					}
					break;
				}
				case 1:
				{
					if(essay.charAt(index)=='/')
					{
						state=3;
						index++;
					}
					else if(essay.charAt(index)=='*')
					{
						state=4;
						index++;
					}
					break;
				}
				case 3:
				{
					while(essay.charAt(index)!='\n')
					{
						index++;
					}
					state=2;
					break;
				}
				case 4:
				{
					while(essay.charAt(index)!='*')
					{
						index++;
					}
					index++;
					state=5;
					break;
				}
				case 5:
				{
					if(essay.charAt(index)=='/')
					{
						state=2;
						index++;
					}
					else
					{
						state=4;
					}
					break;
				}
			}
		}
	}
	
	//运算符
	public void get_opera_characters() {
		char state=0;
		String opera_character="";
		while(state!=2)
		{
			switch(state)
			{
				case 0:
				{
					if(essay.charAt(index)=='+')
					{
						state=9;
						opera_character+=essay.charAt(index);
						index++;
					}
					else if(essay.charAt(index)=='-')
					{
						state=10;
						opera_character+=essay.charAt(index);
						index++;
					}
					else if(essay.charAt(index)=='*')
					{
						state=1;
						opera_character+=essay.charAt(index);
						index++;
					}
					else if(essay.charAt(index)=='/')
					{
						state=2;
						opera_character+=essay.charAt(index);
					}
					else if(essay.charAt(index)=='>')
					{
						state=3;
						opera_character+=essay.charAt(index);
						index++;
					}
					else if(essay.charAt(index)=='<')
					{
						state=4;
						opera_character+=essay.charAt(index);
						index++;
					}
					else if(essay.charAt(index)=='=')
					{
						state=5;
						opera_character+=essay.charAt(index);
						index++;
					}
					else if(essay.charAt(index)=='|')
					{
						state=6;
						opera_character+=essay.charAt(index);
						index++;
					}
					else if(essay.charAt(index)=='&')
					{
						state=7;
						opera_character+=essay.charAt(index);
						index++;
					}
					else if(essay.charAt(index)=='!')
					{
						state=8;
						opera_character+=essay.charAt(index);
						index++;
					}
					else if(essay.charAt(index)==':')
					{
						state=2;
						opera_character+=essay.charAt(index);
						index++;
					}
					else if(essay.charAt(index)=='%')
					{
						state=2;
						opera_character+=essay.charAt(index);
						index++;
					}
					else if(essay.charAt(index)==',')
					{
						state=2;
						opera_character+=essay.charAt(index);
					}
					else
					{
						//错误处理
						System.out.println(essay.charAt(index));
						error();
					}
					break;
				}
				case 1:
				{
					if(essay.charAt(index)=='*')
					{
						state=2;
						opera_character+=essay.charAt(index);
					}
					else
					{
						state=2;
						index--;
					}
					break;
				}
				case 3:
				{
					if(essay.charAt(index)=='=')
					{
						state=2;
						opera_character+=essay.charAt(index);
					}
					else
					{
						state=2;
						index--;
					}
					break;
				}
				case 4:
				{
					if(essay.charAt(index)=='=')
					{
						state=2;
						opera_character+=essay.charAt(index);
					}
					else if(essay.charAt(index)=='>')
					{
						state=2;
						index++;
					}
					else
					{
						state=2;
						index--;
					}
					break;
				}
				case 5:
				{
					if(essay.charAt(index)=='=')
					{
						state=2;
						opera_character+=essay.charAt(index);
					}
					else
					{
						state=2;
						index--;
					}
					break;
				}
				case 6:
				{
					if(essay.charAt(index)=='|')
					{
						state=2;
						opera_character+=essay.charAt(index);
					}
					else
					{
						state=2;
						index--;
					}
					break;
				}
				case 7:
				{
					if(essay.charAt(index)=='&')
					{
						state=2;
						opera_character+=essay.charAt(index);
					}
					else
					{
						state=2;
						index--;
					}
					break;
				}
				case 8:
				{
					if(essay.charAt(index)=='=')
					{
						state=2;
						opera_character+=essay.charAt(index);
					}
					else
					{
						state=2;
						index--;
					}
					break;
				}
				case 9:
				{
					if(essay.charAt(index)=='+')
					{
						state=2;
						opera_character+=essay.charAt(index);
					}
					else
					{
						state=2;
						index--;
					}
					break;
				}
				case 10:
				{
					if(essay.charAt(index)=='-')
					{
						state=2;
						opera_character+=essay.charAt(index);
					}
					else
					{
						state=2;
						index--;
					}
					break;
				}
			}
		}
		
		Token TK= new Token();
		TK.TokenCode=opera_characters_tc[opera_characters_list.indexOf(opera_character)];
		TK.TokenName=opera_character;
		TK.TokenRow=row_num;
		System.out.println("运算符：\t"+opera_character);
		words_Type.add("运算符：\t");
		words.add(opera_character);
		Type_code.add(opera_characters_tc[opera_characters_list.indexOf(opera_character)]);
		TKS.add(TK);

	}
	
	//字符串(变量)识别
	public void get_variable() {
		char state=0;
		String variable="";
		while(state!=2)
		{
			switch(state)
			{
				case 0:
				{
					if(essay.charAt(index)>='a'&&essay.charAt(index)<='z'||essay.charAt(index)>='A'&&essay.charAt(index)<='Z'||essay.charAt(index)=='_')
					{
						state=1;
						variable+=essay.charAt(index);
						index++;
					}
					else
					{
						//错误处理
						error();
					}
					break;
				}
				case 1:
				{
					if(essay.charAt(index)>='a'&&essay.charAt(index)<='z'||essay.charAt(index)>='A'&&essay.charAt(index)<='Z'||essay.charAt(index)=='_'||essay.charAt(index)>='0'&&essay.charAt(index)<='9')
					{
						variable+=essay.charAt(index);
						index++;
						continue;
					}
					else
					{
						state=2;
						index--;
					}
					break;
				}
			}
		}
		Token TK= new Token();
		if(Arrays.asList(keywords).contains(variable))
		{
			TK.TokenCode=keywords_tc[keywords_list.indexOf(variable)];
			TK.TokenName=variable;
			TK.TokenRow=row_num;
			words_Type.add("关键字：\t");
			words.add(variable);
			System.out.println("关键字：\t"+variable);
			Type_code.add(keywords_tc[keywords_list.indexOf(variable)]);
		}
		else
		{
			TK.TokenCode="700";
			TK.TokenName=variable;
			words_Type.add("标识符：\t");
			words.add("700");
//			words.add(variable);
			Type_code.add("10101");
			System.out.println("标识符：\t"+variable);
		}
		TKS.add(TK);
		
		

	}
	
	//数字的识别
	public void get_number() {
		char state=0;
		String number="";
		int point_num=0;
		String num_type="十进制";
		while(state!=2)
		{
			switch(state)
			{
				case 0:
				{
					if(essay.charAt(index)>='1'&&essay.charAt(index)<='9')
					{
						number+=essay.charAt(index);
						index++;
						state=1;
					}
					else if(essay.charAt(index)=='0')
					{
						number+=essay.charAt(index);
						index++;
						state=3;
					}
					else
					{
						//错误处理
						error();
					}
					break;
				}
				case 1:
				{
					if(essay.charAt(index)>='0'&&essay.charAt(index)<='9')
					{
						number+=essay.charAt(index);
						index++;
					}
					else if(essay.charAt(index)=='.')
					{
						number+=essay.charAt(index);
						state=4;
						index++;
						point_num++;
					}
					else
					{
						state=2;
						index--;
					}
					break;
				}
				case 3:
				{
					if(essay.charAt(index)=='x'||essay.charAt(index)=='X')
					{
						num_type="十六进制";
						number+=essay.charAt(index);
						state=5;
						index++;
					}
					else if(essay.charAt(index)=='o'||essay.charAt(index)=='O')
					{
						num_type="八进制";
						number+=essay.charAt(index);
						state=6;
						index++;
					}
					else if(essay.charAt(index)=='0')
					{
						number+=essay.charAt(index);
						state=3;
						index++;
					}
					else if(essay.charAt(index)>='1'&&essay.charAt(index)<='9')
					{
						number+=essay.charAt(index);
						state=1;
						index++;
					}
					else
					{
						state=2;
						index--;
					}
					break;
				}
				case 4:
				{
					if(essay.charAt(index)>='0'&&essay.charAt(index)<='9')
					{
						number+=essay.charAt(index);
						index++;
					}
					else if(essay.charAt(index)=='.')
					{
						number+=essay.charAt(index);
						point_num++;
						index++;
					}
					else
					{
						state=2;
					}
					break;
				}
				case 5:
				{
					if(essay.charAt(index)>='0'&&essay.charAt(index)<='9'||essay.charAt(index)>='a'&&essay.charAt(index)<='f'||essay.charAt(index)>='A'&&essay.charAt(index)<='F')
					{
						number+=essay.charAt(index);
						index++;
					}
					else if(essay.charAt(index)=='.')
					{
						number+=essay.charAt(index);
						point_num++;
						index++;
					}
					else
					{
						state=2;
					}
					break;
				}
				case 6:
				{
					if(essay.charAt(index)>='0'&&essay.charAt(index)<='7')
					{
						number+=essay.charAt(index);
						index++;
					}
					else if(essay.charAt(index)=='.')
					{
						number+=essay.charAt(index);
						point_num++;
						index++;
					}
					else
					{
						state=2;
					}
					break;
				}
			}
		}
		Token TK= new Token();
		if(num_type.equals("十进制"))
		{
			if(point_num==0)
			{
				TK.TokenCode="400";
				TK.TokenName=String.valueOf(Integer.valueOf(number));
				TK.TokenRow=row_num;
				words_Type.add(num_type+"：\t");
				words.add("400");
//				words.add(String.valueOf(Integer.valueOf(number)));
//				words.add(number+"("+String.valueOf(Integer.valueOf(number))+")");
				Type_code.add("10011");
				System.out.println(num_type+"：\t"+Integer.valueOf(number));
			}
			else if(point_num==1)
			{
				TK.TokenCode="800";
				TK.TokenName=String.valueOf(Integer.valueOf(number));
				TK.TokenRow=row_num;
				words_Type.add("浮点数：\t");
				words.add(number+"("+String.valueOf(Float.valueOf(number))+")");
				Type_code.add("10012");
				System.out.println("浮点数：\t"+Float.valueOf(number));
			}
			else if(point_num>1)
			{
				error_words.add("行号:"+row_num+"\t数字错误:"+number);
			}
		}
		else if(num_type.equals("十六进制"))
		{
			words_Type.add(num_type+"：\t");
			words.add(number+"("+number+")");
			Type_code.add("10013");
		}
		else if(num_type.equals("八进制"))
		{
			words_Type.add(num_type+"：\t");
			words.add(number+"("+number+")");
			Type_code.add("10014");
		}
		if(!TK.getTokenName().equals(""))
		{
			TKS.add(TK);
		}
		
	}
	
	public void error()
	{
		System.out.println("分析错误");
	}
	
	public void get_filename_text(Tab path)
	{
		essay=((TextArea)((HBox)path.getContent()).getChildren().get(1)).getText();
//        System.out.println(essay);
	}

}
