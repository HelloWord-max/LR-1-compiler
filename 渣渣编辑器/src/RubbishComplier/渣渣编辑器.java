package RubbishComplier;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class 渣渣编辑器 extends Application {
	String encoding="UTF-8";
	MenuBar menuBar = new MenuBar();
	Text text = new Text("JavaFX");
	Tab path=null;
	String abpath=null;
    BorderPane borderPane = new BorderPane();
    AnchorPane anchorPane = new AnchorPane();
    double primaryStage_height;
    TabPane tp=new TabPane();
    analyse_frame af=new analyse_frame();
    words_analyse as=null;
//    LL1 l1=null;
    LR1 lr=null;
//    Button[] FN= {};
//    List<Button> fn=Arrays.asList(FN);
//    TextArea[] TA= {};
//    List<TextArea> ta=Arrays.asList(TA);
    
    EventHandler<ActionEvent> MEHandler;
    @Override
    public void start(Stage primaryStage) throws Exception {
    	create_action();
    	set_menu();
    	set_tab();
    	set_analyse_frame();
    	anchorPane.getChildren().addAll(borderPane,tp);
        Scene scene = new Scene(anchorPane,1000,500);
        URL cssurl=this.getClass().getClassLoader().getResource("mycss/mycss.css");		
        scene.getStylesheets().add(cssurl.toExternalForm());
        primaryStage.setTitle("渣渣编辑器");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    
    public void set_analyse_frame() {
		anchorPane.getChildren().addAll(af.tp);
	}
    

    public void set_tab() {
    	tp.setTranslateY(25);
    	tp.getSelectionModel().selectedItemProperty().addListener((obs,ov,nv)->{
    		path=tp.getSelectionModel().getSelectedItem();
    		abpath=tp.getSelectionModel().getSelectedItem().getId();
        });

    	
	}




	//创建一个监听事件对象
	public void create_action()
    {
    	MEHandler= new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                String name = ((MenuItem)e.getTarget()).getText();
                //如果选中退出，那么程序就 退出
//                System.out.printf(name);
                if((name.equals("退出"))){
                    Platform.exit();
                }
                else if((name.equals("打开"))) {
                	open_file();
                }
                else if((name.equals("保存"))) {
                	try {
						new save_file(abpath,path);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                }
                else if((name.equals("词法分析")))
                {
                	words_analyse_show();
//                	new analyse_lex(path);
                	System.out.println(as.words);
                }
                else if((name.equals("语法分析")))
                {
                	words_analyse_show();
                	System.out.println(as.words);
					lr=new LR1(as);
                	af.grammar_ta.setText(lr.grammar_error);
//                	l1=new LL1(as);
                }
                else if((name.equals("中间代码")))
                {
                	af.Intermediate_code_ta.setText(lr.Intermediate_code);
                }
                else if((name.equals("汇编代码")))
                {
                	af.assemblylaguage_code_ta.setText(lr.assemblylaguage_cade);
                }
                text.setText(name+"：被选中");
            }
        };
        
    }
	
	//词法分析结果放在
	public void words_analyse_show() {
		String words_error_tc="";
    	String words_tc="";
    	System.out.println("正在编译");
    	as=new words_analyse(path);
    	for(int i=0;i<as.words.size();i++)
    	{
    		words_tc+=String.valueOf(as.words_Type.get(i));
    		words_tc+=String.valueOf(as.words.get(i));
    		words_tc+="\t";
    		words_tc+="种别码:\t";
    		words_tc+=as.Type_code.get(i);
    		words_tc+="\n";
    	}
    	for(int i=0;i<as.error_words.size();i++)
    	{
    		words_error_tc+=as.error_words.get(i);
    		words_error_tc+="\n";
    	}
    	af.words_analyse_ta.setText(words_tc);
    	af.words_error_ta.setText(words_error_tc);
	}
	
	//打开文件
	public void open_file() {
		Stage stage=new Stage();
    	
    	FileChooser fc=new FileChooser();
    	
    	fc.setInitialDirectory(new File("C:\\Users\\JUNFU\\Desktop"));
    	
    	File file= fc.showOpenDialog(stage);
    	
    	
    	//读取文件，将文件类容加载到内存，并且打印出来
    	try {
    		if(file.isFile() && file.exists()){ //判断文件是否存在
                TextArea ta= get_filename_text(file);//获取文件信息
                
                set_list_view(ta);//创建视窗，行号监听器
                
                }
		} catch (Exception e2) {
			 System.out.println("读取文件内容出错");
		}
    	
	}
	
	//创建文本框
	public TextArea get_filename_text(File file) throws IOException {
		InputStreamReader read = new InputStreamReader(
                new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                String essay="";
                while((lineTxt = bufferedReader.readLine()) != null){
//                    System.out.println(lineTxt);
                    essay=essay+lineTxt+"\n";
                }

                read.close();
                
		Tab tb = new Tab("");
		
		tb.setId(file.getAbsolutePath());
		
        HBox hbox = new HBox();
        
        
        TextArea ta=new TextArea(essay);
        
        ta.setMaxWidth(600);
        
        VBox vbox= set_list_view(ta);//创建视窗，行号
        
        hbox.getChildren().add(vbox);
        
        hbox.getChildren().add(ta);
        hbox.setAlignment(Pos.CENTER);
        tb.setContent(hbox);
        tb.setClosable(true);
        tp.getTabs().add(tb);
        
        tb.setText(file.getName().toString());
        
        return ta;
	}
	
	
	//创建行号
	public VBox set_list_view(TextArea ta) {
		
		int col_lenth=0;
		for(int i=0;i<ta.getText().length();i++)
		{
			if(ta.getText().charAt(i)=='\n')
			{
				col_lenth++;
			}
		}
		
		VBox vbox=new VBox();
		
		ObservableList<Integer> list = FXCollections.observableArrayList();
		
		ListView<Integer> listview=new ListView<Integer>();
		
		listview.setId("listview");
		
		int col_lenth_3=col_lenth;
		int setwidth=0;
		
		while(col_lenth_3!=0)
		{
			col_lenth_3=col_lenth_3/10;
			setwidth+=1;
		}
		listview.setPrefWidth(setwidth*10+20);
//		ta.setFont(new Font(15));
		ta.setId("ta");
		for(int i=1;i<=col_lenth;i++)
		{
			list.add(i);
		}
		listview.setItems(list);
		
		
		vbox.getChildren().add(listview);
		
		//同步文本框以及行号位置
		ta.scrollTopProperty().addListener(new ChangeListener<Number>(){
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				// TODO Auto-generated method stub
				ta.setScrollTop(((int)ta.scrollTopProperty().get()/25)*25);
				listview.scrollTo((int) (ta.scrollTopProperty().get()/25));
			}
			
		});
		
		//监听器，动态控制行号
		ta.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				// TODO Auto-generated method stub
				int col_lenth_2=1;
				for(int i=0;i<ta.getText().length();i++)
				{
					if(ta.getText().charAt(i)=='\n')
					{
						col_lenth_2++;
					}
				}
				
				//计算当前设置行号宽度
				int col_lenth_3=col_lenth_2;
				int setwidth=0;
				while(col_lenth_3!=0)
				{
					col_lenth_3=col_lenth_3/10;
					setwidth+=1;
				}
				listview.setPrefWidth(setwidth*10+20);
				
//				System.out.println(list.size());
				
				if(list.size()>col_lenth_2)
				{
					for(int i=list.size();i>col_lenth_2;i--)
					{
						list.remove(i-1);
					}
				}
				else
				{
					for(int i=list.size();i<col_lenth_2;i++)
					{
						list.add(i+1);
					}
				}
				
			}
        	
		});
		
		return vbox;
	}

    //创建菜单栏
    public void set_menu()
    {
    	System.out.println();
    	borderPane.setPrefSize(1000,10);
        borderPane.setTop(menuBar);
        //创建菜单并设置字符F为热键（也就是说按下F键会自动展开文件对应的下拉菜单）
        Menu fileM = new Menu("(_F)文件");
        //设置热键有效
        fileM.setMnemonicParsing(true);
        /*
        设置带有名称的菜单项,如果菜单项想要有图片可定义：
        MenuItem menuItem = new MenuItem(String text, Node graphic)
        其中graphic指的是ImageView实现的图片
         */
        MenuItem miNew = new MenuItem("新建");
        MenuItem miOpen = new MenuItem("打开");
        //设置快捷键
        miOpen.setAccelerator(KeyCombination.keyCombination("CTRL+O"));
        MenuItem miSave = new MenuItem("保存");
        MenuItem miExit = new MenuItem("退出");
        miSave.setAccelerator(KeyCombination.keyCombination("CTRL+S"));
        miExit.setAccelerator(KeyCombination.keyCombination("CTRL+X"));
        miOpen.setOnAction(MEHandler);
        miExit.setOnAction(MEHandler);
        miNew.setOnAction(MEHandler);
        miSave.setOnAction(MEHandler);
        //将菜单项添加到菜单面板中
        fileM.getItems().addAll(miNew,miOpen,miSave,miExit);
        Menu run=new Menu("编译");
      //设置热键有效
        run.setMnemonicParsing(true);
        MenuItem words_analyse = new MenuItem("词法分析");
        MenuItem sentence_analyse = new MenuItem("语法分析");
        MenuItem Intermediate_code = new MenuItem("中间代码");
        MenuItem assemblylaguage_code = new MenuItem("汇编代码");
        words_analyse.setAccelerator(KeyCombination.keyCombination("CTRL+5"));
        sentence_analyse.setAccelerator(KeyCombination.keyCombination("CTRL+6"));
        Intermediate_code.setAccelerator(KeyCombination.keyCombination("CTRL+7"));
        run.getItems().addAll(words_analyse,sentence_analyse,Intermediate_code,assemblylaguage_code);
        run.setOnAction(MEHandler);
        menuBar.getMenus().addAll(fileM,run);
    }


    public static void main( String[] args )
    {
        launch();
    }
}