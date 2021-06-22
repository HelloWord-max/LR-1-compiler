package RubbishComplier;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;

public class text_frame {
	Tab tb = new Tab("tb1");
    TextArea ta=new TextArea();
	public text_frame(){
	    set_select_frame();
    	set_text_frame();
	}
	
    public void set_text_frame()
    {
    	ta.setTranslateY(60);
    }
    
	
	public void set_select_frame()
    {
    }

}
