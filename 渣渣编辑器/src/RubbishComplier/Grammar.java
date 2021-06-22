package RubbishComplier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Grammar {
	public Grammar(Map<String, List<List<List<String>>>> grammar,List<String> Vn,List<String> Vt) {
		// TODO Auto-generated constructor stub
		//添加非终结符号
		String []Terminal= {"main","(",")",",","const","int","float","char","double","=",";","[","]","+","-","400","*","/","%","{","}","500","800","if","else","for","while","do","return","break","continue","600","700",">","<",">=","<=","==","!=","++","--","+=","-=","/=","%=","*=","||","&&","!","void","switch","case",":","default"};
		String []Nonterminal= {"S","Program","Function_block","Function_definition","Function_definition_parameter_list","Function_definition_parameter","Sentence","Declaration_statement","Value_declaration","Constant_declaration","Variable_type","Constant_Declaration_Table","Variable_declaration","Variable_Declaration_Table","Single_variable_declaration","Single_sample_variable","Single_array_variable","Array_length_expression","Array_item","Initial_array","Array_list","Int_constant","Char_constant","Array_type","Execution_statement","Data_processing_statement","Assignment_statement","Function_Call_sentences","Control_statement","Complex_sentence","Statement_table","If_statement","For_statement","While_statement","Do_while_statement","Loop_statement","Compound_statements_for_loops","Loop_statement_table","Loop_execution_statement","Return_statement","Break_statement","Continue_statement","Expression","Arithmetic_expression","Item","Factor","Constant","Variable","Function_call","List_of_arguments","Arguments","Relational_expression","Relational_operators","Initial_value_expression","Initial_value","Array_value","Arithmetic_expression_type","Double_operator","Self_operator","Boolean_expression","Bool_item","Bool_factor","Function_declaration","Function_type","Function_declaration_parameter_list","Function_declaration_parameter","Switch_statement","Case_statement","Mid_case","C_Constant","Case_sentence","End_case"};
		for(int Terminal_index=0;Terminal_index<Terminal.length;Terminal_index++)
		{
			Vt.add(Terminal[Terminal_index]);
		}
		Vt.add("#");
		for(int Nonterminal_index=0;Nonterminal_index<Nonterminal.length;Nonterminal_index++)
		{
			Vn.add(Nonterminal[Nonterminal_index]);
		}
		//输入文法
		String []gramma= {"S: Program",
				"Program: Function_type 'main' '(' ')' Complex_sentence Function_block | Function_type 'main' '(' ')' Complex_sentence",
				"Function_block: Function_definition Function_block | Function_definition",
				"Function_definition: Function_type Variable '(' Function_definition_parameter_list ')' Complex_sentence | Function_type Variable '(' ')' Complex_sentence",
				"Function_definition_parameter_list: Function_definition_parameter",
				"Function_definition_parameter: Variable_type Variable | Variable_type Variable ',' Function_definition_parameter",
				"Sentence: Declaration_statement | Execution_statement",
				"Declaration_statement: Value_declaration | Function_declaration",
				"Value_declaration: Constant_declaration | Variable_declaration",
				"Constant_declaration: 'const' Variable_type Constant_Declaration_Table",
				"Variable_type: 'int' | 'float' | 'char' | 'double'",
				"Constant_Declaration_Table: Variable '=' Constant ';' | Variable '=' Constant ',' Constant_Declaration_Table",
				"Variable_declaration: Variable_type Variable_Declaration_Table",
				"Variable_Declaration_Table: Single_variable_declaration ';' | Single_variable_declaration ',' Variable_Declaration_Table",
				"Single_variable_declaration: Single_sample_variable | Single_array_variable",
				"Single_sample_variable: Variable | Variable '=' Expression",
				"Single_array_variable: Variable '[' Array_length_expression ']' | Variable '[' Array_length_expression ']' '=' Initial_array | Variable '[' ']' '=' Initial_array",
				"Array_length_expression: Array_item '+' Array_length_expression | Array_item '-' Array_length_expression | Array_item",
				"Array_item: '400' '*' Array_item | '400' '/' Array_item | '400' '%' Array_item | '400'",
				"Initial_array: '{' Array_list '}'",
				"Array_list: Int_constant | Char_constant",
				"Int_constant: Array_type | Array_type ',' Int_constant",
				"Char_constant: '500' | '500' ',' Char_constant",
				"Array_type: '400' | '800'",
				"Execution_statement: Data_processing_statement | Control_statement | Complex_sentence",
				"Data_processing_statement: Assignment_statement | Function_Call_sentences",
				"Assignment_statement: Initial_value_expression ';'",
				"Function_Call_sentences: Function_call ';'",
				"Control_statement: If_statement | For_statement | While_statement | Do_while_statement | Return_statement | Switch_statement",
				"Complex_sentence: '{' Statement_table '}' | '{' '}'",
				"Statement_table: Sentence | Sentence Statement_table",
				"If_statement: 'if' '(' Expression ')' Sentence | 'if' '(' Expression ')' Sentence 'else' Sentence|'if' '(' Expression ')' Loop_statement | 'if' '(' Expression ')' Loop_statement 'else' Loop_statement",
				"For_statement: 'for' '(' Expression ';' Expression ';' Expression ')' Loop_statement",
				"While_statement: 'while' '(' Expression ')' Loop_statement",
				"Do_while_statement: 'do' Compound_statements_for_loops 'while' '(' Expression ')' ';'",
				"Loop_statement: Declaration_statement | Loop_execution_statement | Execution_statement | Compound_statements_for_loops",
				"Compound_statements_for_loops: '{' Loop_statement_table '}' | '{' '}'",
				"Loop_statement_table: Loop_statement | Loop_statement Loop_statement_table",
				"Loop_execution_statement: If_statement | For_statement | While_statement | Do_while_statement | Return_statement | Break_statement | Continue_statement",
				//"If_statement_for_loop: 'if' '(' Expression ')' Loop_statement | 'if' '(' Expression ')' Loop_statement 'else' Loop_statement",
				"Return_statement: 'return' ';' | 'return' Expression ';'",
				"Break_statement: 'break' ';'",
				"Continue_statement: 'continue' ';'",
				"Expression: Arithmetic_expression | Relational_expression | Initial_value_expression | Boolean_expression",
				"Arithmetic_expression: Item '+' Arithmetic_expression | Item '-' Arithmetic_expression | Item | '+' Arithmetic_expression | '-' Arithmetic_expression",
				"Item: Factor '*' Item | Factor '/' Item | Factor '%' Item | Factor",
				"Factor: '(' Arithmetic_expression ')' | Constant | Variable | Function_call | Array_value",
				"Constant: '400' | '500' | '600' | '800'",
				"Variable: '700'",
				"Function_call: Variable '(' List_of_arguments ')' | Variable '(' ')'",
				"List_of_arguments: Arguments",
				"Arguments: Expression | Expression ',' Arguments",
				"Relational_expression: Arithmetic_expression Relational_operators Arithmetic_expression | '(' Arithmetic_expression Relational_operators Arithmetic_expression ')'",//add
				"Relational_operators: '>' | '<' | '>=' | '<=' | '==' | '!='",
				"Initial_value_expression: Initial_value Arithmetic_expression_type",
				"Initial_value: Variable | Array_value",
				"Array_value: Variable '[' Expression ']'",
				"Arithmetic_expression_type: '=' Expression | Double_operator | Self_operator Expression",
				"Double_operator: '++' | '--'",
				"Self_operator: '+=' | '-=' | '/=' | '%=' | '*='",
				"Boolean_expression: Bool_item '||' Boolean_expression | Bool_item",
				"Bool_item: Bool_factor '&&' Bool_item | Bool_factor",
				"Bool_factor: Arithmetic_expression | Relational_expression | '!' Boolean_expression",
				"Function_declaration: Function_type Variable '(' Function_declaration_parameter_list ')' ';' | Function_type Variable '(' ')' ';'",
				"Function_type: 'int' | 'float' | 'char' | 'void'",
				"Function_declaration_parameter_list: Function_declaration_parameter",
				"Function_declaration_parameter: Variable_type | Variable_type ',' Function_declaration_parameter",
				"Switch_statement: 'switch' '(' Expression ')' '{' Case_statement '}'",
				"Case_statement: Mid_case End_case | Mid_case",
				"Mid_case: 'case' C_Constant ':' Case_sentence Mid_case | 'case' C_Constant ':' Case_sentence",
				"C_Constant: '+' Constant | '-' Constant | Constant",
				"Case_sentence: Sentence Break_statement | Sentence",
				"End_case: 'default' ':' Case_sentence",};

		
		for(int gra_index=0;gra_index<gramma.length;gra_index++)
		{
			List<List<List<String>>> css1=new ArrayList<List<List<String>>>();
			String[] grammas= gramma[gra_index].substring(gramma[gra_index].indexOf(':')+1, gramma[gra_index].length()).replaceAll("'", "").replaceFirst(" ", "").split("\\|");
			for(int grammas_index=0;grammas_index<grammas.length;grammas_index++)
			{
				List<List<String>> cssa1= new ArrayList<List<String>>();
				String[] sentence1=grammas[grammas_index].split(" ");

				List<String> sentence=new ArrayList<String>();
				for(int sentence_index=0;sentence_index<sentence1.length;sentence_index++)
				{
					if(!sentence1[sentence_index].isBlank()) 
					{
						sentence.add(sentence1[sentence_index]);
					}
				}
				cssa1.add(sentence);

				css1.add(cssa1);
			}
			grammar.put(Vn.get(gra_index), css1);
			System.out.println(grammar.get(Vn.get(gra_index)));
	}
		grammar.get("Boolean_expression").remove(1);
		List<String> str=new ArrayList<String>();
		str.add("||");
		grammar.get("Boolean_expression").get(0).add(1,str);
	}
}