package api.test;

import static io.restassured.RestAssured.*;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.*;

import com.github.javafaker.Faker;
import com.aventstack.extentreports.*;

import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.ExtentReportManager;
import io.restassured.response.Response;

@Listeners(api.utilities.TestLoggerListener.class)
public class UserTests {

    Faker fake;
    User userData;

    ExtentReports extent;
    ExtentTest test;

    @BeforeClass
    public void setUpData() {
        extent = ExtentReportManager.getInstance();

        fake = new Faker();
        userData = new User();

        userData.setId(fake.idNumber().hashCode());
        userData.setUsername(fake.name().username());
        userData.setFirstName(fake.name().firstName());
        userData.setLastName(fake.name().lastName());
        userData.setEmail(fake.internet().safeEmailAddress());
        userData.setPassword(fake.internet().password());
        userData.setPhone(fake.phoneNumber().cellPhone());
    }

    @AfterClass
    public void tearDown() {
        extent.flush();
    }

    @Test(priority = 1)
    public void postUser() throws IOException {
        test = extent.createTest("POST User Test");
        try {
            Response res = UserEndPoints.createUser(userData);
            res.then().log().all();
            Assert.assertEquals(res.getStatusCode(), 200);
            test.pass("User created successfully with status code 200");
        } catch (AssertionError | Exception e) {
            test.fail("POST User Test failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 2)
    public void getUser() throws InterruptedException, IOException {
        test = extent.createTest("GET User Test");
        try {
            waitForUserToBeAvailable(userData.getUsername());

            Response res = UserEndPoints.readUser(userData.getUsername());
            res.then().log().all();

            Assert.assertEquals(res.getStatusCode(), 200);
            test.pass("User fetched successfully");
        } catch (AssertionError | Exception e) {
            test.fail("GET User Test failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 3)
    public void updateUserByName() throws InterruptedException, IOException {
        test = extent.createTest("PUT Update User Test");
        try {
            waitForUserToBeAvailable(userData.getUsername());

            // Update some user fields
            userData.setFirstName(fake.name().firstName());
            userData.setLastName(fake.name().lastName());
            userData.setEmail(fake.internet().safeEmailAddress());

            Response res = UserEndPoints.updateUser(userData.getUsername(), userData);
            res.then().log().all();

            Assert.assertEquals(res.getStatusCode(), 200);

            // Verify user is updated
            Response updatedData = UserEndPoints.readUser(userData.getUsername());
            updatedData.then().log().all();
            Assert.assertEquals(updatedData.getStatusCode(), 200);

            test.pass("User updated and verified successfully");
        } catch (AssertionError | Exception e) {
            test.fail("PUT Update User Test failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 4)
    public void deleteUser() throws InterruptedException, IOException {
        test = extent.createTest("DELETE User Test");
        try {
            waitForUserToBeAvailable(userData.getUsername());

            Response res = UserEndPoints.deleteUser(userData.getUsername());
            res.then().log().all();

            Assert.assertEquals(res.getStatusCode(), 200);
            test.pass("User deleted successfully");
        } catch (AssertionError | Exception e) {
            test.fail("DELETE User Test failed: " + e.getMessage());
            throw e;
        }
    }

    private void waitForUserToBeAvailable(String username) throws InterruptedException, IOException {
        int retries = 6;
        int waitTime = 2000;
        for (int i = 0; i < retries; i++) {
            Response res = UserEndPoints.readUser(username);
            int status = res.getStatusCode();
            System.out.println("Attempt " + (i + 1) + ": Status - " + status);
            if (status == 200) return;
            Thread.sleep(waitTime);
        }
        throw new RuntimeException("User not found after " + retries + " retries");
    }
}
