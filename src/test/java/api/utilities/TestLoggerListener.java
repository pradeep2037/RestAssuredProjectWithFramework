package api.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.*;

public class TestLoggerListener implements ITestListener {

    private static final Logger logger = LogManager.getLogger(TestLoggerListener.class);

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("FAILED: " + result.getName());
        logger.error("REASON: ", result.getThrowable());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("PASSED: " + result.getName());
    }

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("STARTED: " + result.getName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("SKIPPED: " + result.getName());
    }

    @Override
    public void onStart(ITestContext context) {
        logger.info("Test Suite Started: " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info("Test Suite Finished: " + context.getName());
    }
}
