package runners;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

//"@runit,@run","~@ignore",
@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"html:reports/cucumber-html-report",
                "json:reports/cucumber.json",
                "pretty"},
        tags = {"@regression,@integration,@smoke"},
        features = {"src/test/resources"},
        glue = {"bindings"}
)

/**
 * This class runs all the tests with the specified tags.
 */
public class TestRunner {

}