package RubbishComplier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class samplelaguage_to_assemblylaguage {
	String assemblylaguage="";
	Map<String, String> s_to_a= new HashMap<String, String>();
	private List<Guadruple_Form> Guadruple_Form_list;
	private int Guadruple_Form_list_index=0;
	public samplelaguage_to_assemblylaguage(List<Guadruple_Form> Guadruple_Form_list) {
		this.Guadruple_Form_list=Guadruple_Form_list;
		for(Guadruple_Form_list_index=0;Guadruple_Form_list_index<Guadruple_Form_list.size();Guadruple_Form_list_index++)	
		{
			switch(Guadruple_Form_list.get(Guadruple_Form_list_index).OP)
			{
				case "+":
				{
					ADD();
					break;
				}
				case "-":
				{
					SUB();
					break;
				}
				case "*":
				{
					MUL();
					break;
				}
				case "/":
				{
					DIV();
					break;
				}
				case "%":
				{
					FIND_REMAINDER();
					break;
				}
				case "<":
				{
					JB_LT();
					break;
				}
				case ">=":
				{
					JMB_GE();
					break;
				}
				case ">":
				{
					JA_GT();
					break;
				}
				case "<=":
				{
					JNA_LE();
					break;
				}
				case "==":
				{
					JE_EQ();
					break;
				}
				case "!=":
				{
					JNE_NE();
					break;
				}
				case "&&":
				{
					JE_AND();
					break;
				}
				case "||":
				{
					JNE_OR();
					break;
				}
				case "!":
				{
					JE_NOT();
					break;
				}
				case "j":
				{
					JMP();
					break;
				}
				case "jz":
				{
					ZERO_JMP();
					break;
				}
				case "jnz":
				{
					NOT_ZERO_JMP();
					break;
				}
				case "ret":
				{
					if(Guadruple_Form_list.get(Guadruple_Form_list_index).result!=null)
					{
						RET_VALUE();
					}
					
					else
					{
						RET();
					}
					break;
				}
				case "=":
				{
					EVALUATION();
					break;
				}
			}
		}
		System.out.println(assemblylaguage);
	}
	private void ADD() {//加
		assemblylaguage+="MOV\t"+"AX\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).agr1+"\n";
		assemblylaguage+="ADD\t"+"AX\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).agr2+"\n";
		if(Guadruple_Form_list.get(Guadruple_Form_list_index).result!=null)
		{
			assemblylaguage+="MOV\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).result+"\tAX"+"\n";
		}
		else if(Guadruple_Form_list.get(Guadruple_Form_list_index).chain!=null)
		{
			assemblylaguage+="MOV\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).chain.chain+"\tAX"+"\n";
		}
		else if(Guadruple_Form_list.get(Guadruple_Form_list_index).exit!=null)
		{
			assemblylaguage+="MOV\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).exit.exit+"\tAX"+"\n";
		}
	}
	private void SUB() {//减
		assemblylaguage+="MOV\t"+"AX\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).agr1+"\n";
		assemblylaguage+="SUB\t"+"AX\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).agr2+"\n";
		if(Guadruple_Form_list.get(Guadruple_Form_list_index).result!=null)
		{
			assemblylaguage+="MOV\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).result+"\tAX"+"\n";
		}
		else if(Guadruple_Form_list.get(Guadruple_Form_list_index).chain!=null)
		{
			assemblylaguage+="MOV\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).chain.chain+"\tAX"+"\n";
		}
		else if(Guadruple_Form_list.get(Guadruple_Form_list_index).exit!=null)
		{
			assemblylaguage+="MOV\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).exit.exit+"\tAX"+"\n";
		}
	}
	private void MUL() {//乘
		assemblylaguage+="MOV\t"+"AX\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).agr1+"\n";
		assemblylaguage+="MOV\t"+"BX\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).agr2+"\n";
		assemblylaguage+="MUL\t"+"BX\t"+"\n";
		if(Guadruple_Form_list.get(Guadruple_Form_list_index).result!=null)
		{
			assemblylaguage+="MOV\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).result+"\tAX"+"\n";
		}
		else if(Guadruple_Form_list.get(Guadruple_Form_list_index).chain!=null)
		{
			assemblylaguage+="MOV\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).chain.chain+"\tAX"+"\n";
		}
		else if(Guadruple_Form_list.get(Guadruple_Form_list_index).exit!=null)
		{
			assemblylaguage+="MOV\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).exit.exit+"\tAX"+"\n";
		}
	}
	private void DIV() {//除
		assemblylaguage+="MOV\t"+"AX\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).agr1+"\n";
		assemblylaguage+="MOV\t"+"DX\t"+"0"+"\n";
		assemblylaguage+="MOV\t"+"BX\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).agr2+"\n";
		assemblylaguage+="MUL\t"+"BX\t"+"\n";
		if(Guadruple_Form_list.get(Guadruple_Form_list_index).result!=null)
		{
			assemblylaguage+="MOV\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).result+"\tAX"+"\n";
		}
		else if(Guadruple_Form_list.get(Guadruple_Form_list_index).chain!=null)
		{
			assemblylaguage+="MOV\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).chain.chain+"\tAX"+"\n";
		}
		else if(Guadruple_Form_list.get(Guadruple_Form_list_index).exit!=null)
		{
			assemblylaguage+="MOV\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).exit.exit+"\tAX"+"\n";
		}
	}
	private void FIND_REMAINDER() {//余
		assemblylaguage+="MOV\t"+"AX\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).agr1+"\n";
		assemblylaguage+="MOV\t"+"DX\t"+"0"+"\n";
		assemblylaguage+="MOV\t"+"BX\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).agr2+"\n";
		assemblylaguage+="DIV\t"+"BX\t"+"\n";
		if(Guadruple_Form_list.get(Guadruple_Form_list_index).result!=null)
		{
			assemblylaguage+="MOV\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).result+"\tDX"+"\n";
		}
		else if(Guadruple_Form_list.get(Guadruple_Form_list_index).chain!=null)
		{
			assemblylaguage+="MOV\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).chain.chain+"\tDX"+"\n";
		}
		else if(Guadruple_Form_list.get(Guadruple_Form_list_index).exit!=null)
		{
			assemblylaguage+="MOV\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).exit.exit+"\tDX"+"\n";
		}
	}
	private void JB_LT() {//小于
		assemblylaguage+="MOV\t"+"DX\t"+"1"+"\n";
		assemblylaguage+="MOV\t"+"AX\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).agr1+"\n";
		assemblylaguage+="CMP\t"+"BX\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).agr2+"\n";
		assemblylaguage+="JB_LT\t"+"\n";
		assemblylaguage+="MOV\t"+"DX\t"+"0"+"\n";
		if(Guadruple_Form_list.get(Guadruple_Form_list_index).result!=null)
		{
			assemblylaguage+="_LT:\t"+"MOV\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).result+"\tDX"+"\n";
		}
		else if(Guadruple_Form_list.get(Guadruple_Form_list_index).chain!=null)
		{
			assemblylaguage+="_LT:\t"+"MOV\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).chain.chain+"\tDX"+"\n";
		}
		else if(Guadruple_Form_list.get(Guadruple_Form_list_index).exit!=null)
		{
			assemblylaguage+="_LT:\t"+"MOV\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).exit.exit+"\tDX"+"\n";
		}
	}
	private void JMB_GE() {//大于等于
		assemblylaguage+="MOV\t"+"DX\t"+"1"+"\n";
		assemblylaguage+="MOV\t"+"AX\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).agr1+"\n";
		assemblylaguage+="CMP\t"+"BX\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).agr2+"\n";
		assemblylaguage+="JNB_GE\t"+"\n";
		assemblylaguage+="MOV\t"+"DX\t"+"0"+"\n";
		if(Guadruple_Form_list.get(Guadruple_Form_list_index).result!=null)
		{
			assemblylaguage+="_GE:\t"+"MOV\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).result+"\tDX"+"\n";
		}
		else if(Guadruple_Form_list.get(Guadruple_Form_list_index).chain!=null)
		{
			assemblylaguage+="_GE:\t"+"MOV\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).chain.chain+"\tDX"+"\n";
		}
		else if(Guadruple_Form_list.get(Guadruple_Form_list_index).exit!=null)
		{
			assemblylaguage+="_GE:\t"+"MOV\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).exit.exit+"\tDX"+"\n";
		}
	}
	private void JA_GT() {//大于
		assemblylaguage+="MOV\t"+"DX\t"+"1"+"\n";
		assemblylaguage+="MOV\t"+"AX\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).agr1+"\n";
		assemblylaguage+="CMP\t"+"BX\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).agr2+"\n";
		assemblylaguage+="JA_GT\t"+"\n";
		assemblylaguage+="MOV\t"+"DX\t"+"0"+"\n";
		if(Guadruple_Form_list.get(Guadruple_Form_list_index).result!=null)
		{
			assemblylaguage+="_GT:\t"+"MOV\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).result+"\tDX"+"\n";
		}
		else if(Guadruple_Form_list.get(Guadruple_Form_list_index).chain!=null)
		{
			assemblylaguage+="_GT:\t"+"MOV\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).chain.chain+"\tDX"+"\n";
		}
		else if(Guadruple_Form_list.get(Guadruple_Form_list_index).exit!=null)
		{
			assemblylaguage+="_GT:\t"+"MOV\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).exit.exit+"\tDX"+"\n";
		}
	}
	private void JNA_LE() {//小于等于
		assemblylaguage+="MOV\t"+"DX\t"+"1"+"\n";
		assemblylaguage+="MOV\t"+"AX\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).agr1+"\n";
		assemblylaguage+="CMP\t"+"BX\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).agr2+"\n";
		assemblylaguage+="JNA_LE\t"+"\n";
		assemblylaguage+="MOV\t"+"DX\t"+"0"+"\n";
		if(Guadruple_Form_list.get(Guadruple_Form_list_index).result!=null)
		{
			assemblylaguage+="_LE:\t"+"MOV\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).result+"\tDX"+"\n";
		}
		else if(Guadruple_Form_list.get(Guadruple_Form_list_index).chain!=null)
		{
			assemblylaguage+="_LE:\t"+"MOV\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).chain.chain+"\tDX"+"\n";
		}
		else if(Guadruple_Form_list.get(Guadruple_Form_list_index).exit!=null)
		{
			assemblylaguage+="_LE:\t"+"MOV\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).exit.exit+"\tDX"+"\n";
		}
	}
	private void JE_EQ() {//等于
		assemblylaguage+="MOV\t"+"DX\t"+"1"+"\n";
		assemblylaguage+="MOV\t"+"AX\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).agr1+"\n";
		assemblylaguage+="CMP\t"+"BX\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).agr2+"\n";
		assemblylaguage+="JE_EQ\t"+"\n";
		assemblylaguage+="MOV\t"+"DX\t"+"0"+"\n";
		if(Guadruple_Form_list.get(Guadruple_Form_list_index).result!=null)
		{
			assemblylaguage+="_EQ:\t"+"MOV\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).result+"\tDX"+"\n";
		}
		else if(Guadruple_Form_list.get(Guadruple_Form_list_index).chain!=null)
		{
			assemblylaguage+="_EQ:\t"+"MOV\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).chain.chain+"\tDX"+"\n";
		}
		else if(Guadruple_Form_list.get(Guadruple_Form_list_index).exit!=null)
		{
			assemblylaguage+="_EQ:\t"+"MOV\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).exit.exit+"\tDX"+"\n";
		}
	}
	private void JNE_NE() {//不等于
		assemblylaguage+="MOV\t"+"DX\t"+"1"+"\n";
		assemblylaguage+="MOV\t"+"AX\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).agr1+"\n";
		assemblylaguage+="CMP\t"+"BX\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).agr2+"\n";
		assemblylaguage+="JNE_NE\t"+"\n";
		assemblylaguage+="MOV\t"+"DX\t"+"0"+"\n";
		if(Guadruple_Form_list.get(Guadruple_Form_list_index).result!=null)
		{
			assemblylaguage+="_NE:\t"+"MOV\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).result+"\tDX"+"\n";
		}
		else if(Guadruple_Form_list.get(Guadruple_Form_list_index).chain!=null)
		{
			assemblylaguage+="_NE:\t"+"MOV\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).chain.chain+"\tDX"+"\n";
		}
		else if(Guadruple_Form_list.get(Guadruple_Form_list_index).exit!=null)
		{
			assemblylaguage+="_NE:\t"+"MOV\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).exit.exit+"\tDX"+"\n";
		}
	}
	private void JE_AND() {//与
		assemblylaguage+="MOV\t"+"DX\t"+"0"+"\n";
		assemblylaguage+="MOV\t"+"AX\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).agr1+"\n";
		assemblylaguage+="CMP\t"+"AX\t"+"0"+"\n";
		assemblylaguage+="JE_AND\t"+"\n";
		assemblylaguage+="MOV\t"+"AX\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).agr2+"\n";
		assemblylaguage+="CMP\t"+"AX\t"+"0"+"\n";
		assemblylaguage+="JE_AND\t"+"\n";
		assemblylaguage+="MOV\t"+"DX\t"+"1"+"\n";
		if(Guadruple_Form_list.get(Guadruple_Form_list_index).result!=null)
		{
			assemblylaguage+="_AND:\t"+"MOV\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).result+"\tDX"+"\n";
		}
		else if(Guadruple_Form_list.get(Guadruple_Form_list_index).chain!=null)
		{
			assemblylaguage+="_AND:\t"+"MOV\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).chain.chain+"\tDX"+"\n";
		}
		else if(Guadruple_Form_list.get(Guadruple_Form_list_index).exit!=null)
		{
			assemblylaguage+="_AND:\t"+"MOV\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).exit.exit+"\tDX"+"\n";
		}
	}
	private void JNE_OR() {//或
		assemblylaguage+="MOV\t"+"DX\t"+"1"+"\n";
		assemblylaguage+="MOV\t"+"AX\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).agr1+"\n";
		assemblylaguage+="CMP\t"+"AX\t"+"0"+"\n";
		assemblylaguage+="JNE_OR\t"+"\n";
		assemblylaguage+="MOV\t"+"AX\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).agr2+"\n";
		assemblylaguage+="CMP\t"+"AX\t"+"0"+"\n";
		assemblylaguage+="JNE_OR\t"+"\n";
		assemblylaguage+="MOV\t"+"DX\t"+"0"+"\n";
		if(Guadruple_Form_list.get(Guadruple_Form_list_index).result!=null)
		{
			assemblylaguage+="_OR:\t"+"MOV\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).result+"\tDX"+"\n";
		}
		else if(Guadruple_Form_list.get(Guadruple_Form_list_index).chain!=null)
		{
			assemblylaguage+="_OR:\t"+"MOV\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).chain.chain+"\tDX"+"\n";
		}
		else if(Guadruple_Form_list.get(Guadruple_Form_list_index).exit!=null)
		{
			assemblylaguage+="_OR:\t"+"MOV\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).exit.exit+"\tDX"+"\n";
		}
	}
	private void JE_NOT() {//非
		assemblylaguage+="MOV\t"+"DX\t"+"1"+"\n";
		assemblylaguage+="MOV\t"+"AX\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).agr1+"\n";
		assemblylaguage+="CMP\t"+"AX\t"+"0"+"\n";
		assemblylaguage+="JE_NOT\t"+"\n";
		assemblylaguage+="MOV\t"+"DX\t"+"0"+"\n";
		if(Guadruple_Form_list.get(Guadruple_Form_list_index).result!=null)
		{
			assemblylaguage+="_NOT:\t"+"MOV\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).result+"\tDX"+"\n";
		}
		else if(Guadruple_Form_list.get(Guadruple_Form_list_index).chain!=null)
		{
			assemblylaguage+="_NOT:\t"+"MOV\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).chain.chain+"\tDX"+"\n";
		}
		else if(Guadruple_Form_list.get(Guadruple_Form_list_index).exit!=null)
		{
			assemblylaguage+="_NOT:\t"+"MOV\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).exit.exit+"\tDX"+"\n";
		}
	}
	private void JMP() {//跳转
		if(Guadruple_Form_list.get(Guadruple_Form_list_index).result!=null)
		{
			
			if(!Guadruple_Form_list.get(Guadruple_Form_list_index).result.equals("-1"))
			{
				assemblylaguage+="JMP\t"+"far\t"+"ptr\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).result+"\n";
			}
			else if(Guadruple_Form_list.get(Guadruple_Form_list_index).chain!=null)
			{
				if(Guadruple_Form_list.get(Guadruple_Form_list_index).chain.chain!=-1)
				{
					assemblylaguage+="JMP\t"+"far\t"+"ptr\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).chain.chain+"\tDX"+"\n";
				}
				else if(Guadruple_Form_list.get(Guadruple_Form_list_index).exit!=null)
				{
					if(Guadruple_Form_list.get(Guadruple_Form_list_index).chain.chain!=-1)
					{
						assemblylaguage+="JMP\t"+"far\t"+"ptr\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).exit.exit+"\tDX"+"\n";
					}
				}
			}
			
		}
		
	}
	private void ZERO_JMP() {//0跳转
		assemblylaguage+="MOV\t"+"AX\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).agr1+"\n";
		assemblylaguage+="CMP\t"+"AX\t"+"0"+"\n";
		assemblylaguage+="JNE_NE\t"+"\n";
		assemblylaguage+="MOV\t"+"DX\t"+"0"+"\n";
		if(Guadruple_Form_list.get(Guadruple_Form_list_index).result!=null)
		{
			assemblylaguage+="JMP\t"+"far\t"+"ptr\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).result+"\n";
		}
		else if(Guadruple_Form_list.get(Guadruple_Form_list_index).chain!=null)
		{
			assemblylaguage+="JMP\t"+"far\t"+"ptr\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).chain.chain+"\tDX"+"\n";
		}
		else if(Guadruple_Form_list.get(Guadruple_Form_list_index).exit!=null)
		{
			assemblylaguage+="JMP\t"+"far\t"+"ptr\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).exit.exit+"\tDX"+"\n";
		}
		assemblylaguage+="_NE:\t"+"NOP\t"+"\n";
	}
	private void NOT_ZERO_JMP() {//非0跳转
		assemblylaguage+="MOV\t"+"AX\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).agr1+"\n";
		assemblylaguage+="CMP\t"+"AX\t"+"0"+"\n";
		assemblylaguage+="JE_EZ\t"+"\n";
		assemblylaguage+="MOV\t"+"DX\t"+"0"+"\n";
		if(Guadruple_Form_list.get(Guadruple_Form_list_index).result!=null)
		{
			assemblylaguage+="JMP\t"+"far\t"+"ptr\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).result+"\n";
		}
		else if(Guadruple_Form_list.get(Guadruple_Form_list_index).chain!=null)
		{
			assemblylaguage+="JMP\t"+"far\t"+"ptr\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).chain.chain+"\tDX"+"\n";
		}
		else if(Guadruple_Form_list.get(Guadruple_Form_list_index).exit!=null)
		{
			assemblylaguage+="JMP\t"+"far\t"+"ptr\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).exit.exit+"\tDX"+"\n";
		}
		assemblylaguage+="_EZ:\t"+"NOP\t"+"\n";
	}
	private void RET_VALUE() {//返回——有值
		assemblylaguage+="MOV\t"+"AX\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).agr1+"\n";
		assemblylaguage+="MOV\t"+"SP\t"+"BP"+"\n";
		assemblylaguage+="POP\t"+"BP"+"\n";
		assemblylaguage+="RET"+"\n";
	}
	private void RET() {//返回——无值
		assemblylaguage+="MOV\t"+"AX\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).agr1+"\n";
		assemblylaguage+="POP\t"+"BP"+"\n";
		assemblylaguage+="RET"+"\n";
	}
	private void EVALUATION() {//赋值无值
		assemblylaguage+="MOV\t"+"AX\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).agr1+"\n";
		if(!Guadruple_Form_list.get(Guadruple_Form_list_index).result.equals("-1"))
		{
			assemblylaguage+="MOV\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).result+"\tAX\t"+"\n";
		}
		else if(Guadruple_Form_list.get(Guadruple_Form_list_index).chain!=null)
		{
			if(Guadruple_Form_list.get(Guadruple_Form_list_index).chain.chain!=-1)
			{
				assemblylaguage+="MOV\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).chain.chain+"\tAX\t"+"\n";
			}
			else if(Guadruple_Form_list.get(Guadruple_Form_list_index).exit!=null)
			{
				if(Guadruple_Form_list.get(Guadruple_Form_list_index).exit.exit!=-1)
				{
					assemblylaguage+="MOV\t"+Guadruple_Form_list.get(Guadruple_Form_list_index).exit.exit+"\tAX\t"+"\n";
				}
			}
		}
	}
}
