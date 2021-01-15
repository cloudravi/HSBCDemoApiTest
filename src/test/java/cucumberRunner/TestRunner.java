package cucumberRunner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/features", glue = { "stepDefinations" }, 
plugin = { "pretty","json:target/cucumber-reports/Cucumber.json",
		"html:target/cucumber-reports/Cucumber.html" },
tags = "@RUN",
monochrome = true)

public class TestRunner {

}
