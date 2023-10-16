package com.atlascarbon.stockapi.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = {"src/test/resources/features"},
                     glue = {"com.atlascarbon.stockapi.cucumber.steps"},
                   plugin = {"pretty", "html:cucumber-report.html"})
public class CucumberIntegrationTestIT {

}
