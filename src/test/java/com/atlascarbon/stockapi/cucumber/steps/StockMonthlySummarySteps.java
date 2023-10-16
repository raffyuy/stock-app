package com.atlascarbon.stockapi.cucumber.steps;

import com.atlascarbon.stockapi.StockApiApplication;
import com.atlascarbon.stockapi.dto.MonthlySummariesResponse;
import com.atlascarbon.stockapi.dto.MonthlySummary;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@CucumberContextConfiguration
@ContextConfiguration(classes = StockApiApplication.class, loader = SpringBootContextLoader.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StockMonthlySummarySteps  {

    @LocalServerPort
    private int port;

    MonthlySummariesResponse response;
    private RestTemplate restTemplate = new RestTemplate();
    @Given("I have loaded the monthly summary app")
    public void iHaveLoadedTheMonthlySummaryApp() {
        //This is intentionally empty since the Spring Boot app should already be started by Cucumber.
    }

    @When("I view the summary for stockId {long}")
    public void iViewTheSummaryForStockId(long stockId) {
        String baseURL = "http://localhost:" + port;
        String endpoint = "/monthly-summary/{stockId}";

        String url = baseURL + endpoint;
        response = restTemplate.getForObject(url, MonthlySummariesResponse.class, stockId);
    }



    @Then("I should see the following details for {word} on the first row of the table:")
    public void iShouldSeeTheFollowingDetailsForDecOnTheFirstRowOfTheTable(String monthYear, DataTable table) {
        assertNotEquals(0, response.summaries().size(), "No summaries retrieved");

        MonthlySummary firstRow = response.summaries().get(0);

        List<Map<String, String>> rows = table.asMaps();
        Map<String, String> expectedRow = rows.get(0);


        assertEquals(expectedRow.get("Month"), firstRow.month().format(DateTimeFormatter.ofPattern("MMM-yyyy")));
        assertEquals(Double.parseDouble(expectedRow.get("Average Rating")), firstRow.averageMonthlyRating(), 0.001);
        assertEquals(Integer.parseInt(expectedRow.get("Final Head")), firstRow.finalReadings().head());
        assertEquals(Double.parseDouble(expectedRow.get("Final Weight")), firstRow.finalReadings().weight(), 0.001);
        assertEquals(new BigDecimal(expectedRow.get("Final Price")), firstRow.finalReadings().price());
        assertEquals(Integer.parseInt(expectedRow.get("Number of Updates")), firstRow.recordCount());
        assertEquals(Integer.parseInt(expectedRow.get("Month Head Change")), firstRow.headChange());
    }


    @And("I should see a list of the remaining {int} month-years for stockId {long} with their respective summary data")
    public void iShouldSeeAListOfTheRemainingMonthYearsForStockIdWithTheirRespectiveSummaryData(int numRecords, long stockId) {
        assertEquals(numRecords, response.summaries().size() - 1); // -1 because we already checked the first record
    }
}
