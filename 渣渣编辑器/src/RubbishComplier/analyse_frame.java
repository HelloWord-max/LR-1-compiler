package RubbishComplier;


import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;

public class analyse_frame {
	TabPane tp=new TabPane();
	HBox analyse_frame_box=new HBox();
	HBox words_error_box=new HBox();
	HBox grammar_error_box=new HBox();
	HBox Intermediate_code_box=new HBox();
	HBox assemblylaguage_code_box=new HBox();
	
	Tab words_analyse = new Tab("词法分析");
	TextArea words_analyse_ta=new TextArea();
	
	Tab words_error = new Tab("词法错误");
	TextArea words_error_ta=new TextArea();
	
	Tab grammar_error = new Tab("语法分析");
	TextArea grammar_ta=new TextArea();
	
	Tab Intermediate_code = new Tab("中间代码");
	TextArea Intermediate_code_ta=new TextArea();
	
	Tab assemblylaguage_code = new Tab("汇编代码");
	TextArea assemblylaguage_code_ta=new TextArea();
	public analyse_frame(){
//		analyse_frame_box.setAlignment(Pos.CENTER);
		words_analyse.setClosable(false);
		words_analyse.setContent(analyse_frame_box);
		
//		analyse_frame_box.setAlignment(Pos.CENTER);
		words_error.setContent(words_error_box);
		words_error.setClosable(false);
		
//		analyse_frame_box.setAlignment(Pos.CENTER);
		grammar_error.setContent(grammar_error_box);
		grammar_error.setClosable(false);
		
//		analyse_frame_box.setAlignment(Pos.CENTER);
		Intermediate_code.setContent(Intermediate_code_box);
		Intermediate_code.setClosable(false);
		
//		analyse_frame_box.setAlignment(Pos.CENTER);
		assemblylaguage_code.setContent(assemblylaguage_code_box);
		assemblylaguage_code.setClosable(false);
		
        tp.getTabs().addAll(words_analyse,words_error,grammar_error,Intermediate_code,assemblylaguage_code);
		analyse_frame_box.getChildren().addAll(words_analyse_ta);
		words_error_box.getChildren().addAll(words_error_ta);
		grammar_error_box.getChildren().addAll(grammar_ta);
		Intermediate_code_box.getChildren().addAll(Intermediate_code_ta);
		assemblylaguage_code_box.getChildren().addAll(assemblylaguage_code_ta);
		set_textarea();
		set_analyse_frame_box();
	}
	public void set_textarea() {
		words_analyse_ta.setPrefWidth(300);
		words_analyse_ta.setPrefHeight(450);
		words_analyse_ta.setEditable(false);
		words_error_ta.setPrefWidth(300);
		words_error_ta.setPrefHeight(450);
		words_error_ta.setEditable(false);
		grammar_ta.setPrefWidth(300);
		grammar_ta.setPrefHeight(450);
		grammar_ta.setEditable(false);
		Intermediate_code_ta.setPrefWidth(300);
		Intermediate_code_ta.setPrefHeight(450);
		Intermediate_code_ta.setEditable(false);
		assemblylaguage_code_ta.setPrefWidth(300);
		assemblylaguage_code_ta.setPrefHeight(450);
		assemblylaguage_code_ta.setEditable(false);
	}
	
	public void set_analyse_frame_box() {
		tp.setTranslateY(30);
		tp.setTranslateX(680);
		tp.setTranslateY(30);
		tp.setTranslateX(680);
	}
}
