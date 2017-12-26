//Master Control Class

package tempUI;

import grab.*;
import java.sql.*;
import com.*;

import static javafx.application.Application.launch;

public class StartUp {
	
 	public static void main(String[] args) {
// 		launch(Main.class,args);
//		Data collection complete, please do not restart it
		grab.grabEarthquake.generateURL();
 	}

}
