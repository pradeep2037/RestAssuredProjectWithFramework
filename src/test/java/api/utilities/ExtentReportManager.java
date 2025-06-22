
package api.utilities;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportManager {

    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            new File("Reports").mkdirs();

            // ⏰ Add timestamp to avoid overwriting
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String reportPath = "Reports/ExtentReport_" + timestamp + ".html";

            ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
            reporter.config().setReportName("API Test Report");
            reporter.config().setDocumentTitle("REST Assured Results");

            extent = new ExtentReports();
            extent.attachReporter(reporter);
            extent.setSystemInfo("Tester", "Pradeep");
            extent.setSystemInfo("Environment", "QA");
        }
        return extent;
    }
}
