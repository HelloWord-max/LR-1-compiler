package RubbishComplier;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;

public class save_file {
	File abpath;
	String essay;
	public save_file(String path1,Tab path2) throws IOException {
		abpath=new File(path1);
		essay=((TextArea)((HBox)path2.getContent()).getChildren().get(1)).getText();
		if(abpath.exists()&&abpath.isFile()) {
			FileWriter fw = new FileWriter(abpath);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(essay);
			bw.close();
			fw.close();
		}
	}
}
