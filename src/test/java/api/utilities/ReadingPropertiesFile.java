package api.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.BeforeTest;

public class ReadingPropertiesFile {

	static Properties pr;
	@BeforeTest
	public static Properties fileReading() throws IOException {
		
		FileInputStream file = new FileInputStream("C:\\Users\\kravm\\eclipse-workspace\\another -workspace\\RestAssuredProjectWithFramework\\routes.properties");
		
		pr = new Properties();
		pr.load(file);
		return pr;
		
		
	}
}
