package TestRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"Steps"},
        plugin = {
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
                "html:target/cucumber-reports/cucumber-pretty",
                "json:target/cucumber-reports/CucumberTestReport.json",
        }
)
public class TestRunner extends AbstractTestNGCucumberTests {
        @Override
        @DataProvider(parallel = false)
        public Object[][] scenarios() {
                return super.scenarios();
        }

}
