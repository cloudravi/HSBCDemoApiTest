package stepDefinations;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import resource.Utils;
import io.restassured.filter.Filter;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;

import org.junit.Assert;

public class StepDefination extends Utils {
	RequestSpecification res;
	Response response;

	@Given("load Foreign Rate Api url")
	public void load_foreign_rate_api_url() throws FileNotFoundException {
		RestAssured.baseURI = "https://api.ratesapi.io/";
		res = RestAssured.given().log().all();
		// requestSpecification();
	}

	@When("user calls latest foreign exchange rate Get request")
	public void user_calls_latest_foreign_exchange_rate_get_request() {
		response = RestAssured.when().get("api/latest");
	}

	@When("user calls API with incorrect url")
	public void user_calls_API_with_Incorrect_url() {
		response = RestAssured.when().get("api/TestURL");
	}

	@When("user calls {string} foreign exchange rate Get request with value {string}")
	public void user_calls_foreign_exchange_rate_get_request(String key, String value) throws FileNotFoundException {
		response = res.queryParam(key, value).get("api/latest?" + key + "=");
	}

	@When("user calls API with valid {string}")
	public void user_calls_api_with_valid(String date) {
		response = RestAssured.when().get("api/" + date);
	}

	@When("user calls API with valid {string}, {string} and {string}")
	public void user_calls_api_with_valid_and(String date, String name, String currency) {
		response = res.queryParam(name, currency).get("api/" + date + "?" + name + "=");
	}

	@Then("the API call is success with status code {int}")
	public void the_api_call_is_success_with_status_code(int int1) {
		int status = response.getStatusCode();
		Assert.assertEquals(int1, status);
	}

	@Then("I verify response contains {string}")
	public void in_reponse_body_is(String expectedValue) {

		String resp = response.asString();
		JsonPath js = new JsonPath(resp);
		Assert.assertTrue(resp.contains(expectedValue));
		Assert.assertEquals(getYesterdayDateString(), js.get("date").toString());
	}

	@Then("API call the status code {int}")
	public void api_call_the_status_code(int int1) {
		int status = response.getStatusCode();
		Assert.assertEquals(int1, status);
	}

	@Then("I verify response error message {string}")
	public void i_verify_response_error_message(String expectedMessage) {
		String resp = response.asString();
		JsonPath js = new JsonPath(resp);
		Assert.assertEquals(expectedMessage, js.get("error").toString());
	}

	@Then("I verify response showing proper {string}")
	public void i_verify_response_showing_proper(String expectedDate) {
		String resp = null;
		if (response.getStatusCode() == 200) {
			resp = response.asString();
			JsonPath js = new JsonPath(resp);
			Assert.assertEquals(expectedDate, js.get("date").toString());
		} else {
			resp = response.asString();
			JsonPath js = new JsonPath(resp);
			Assert.fail(js.get("error").toString());
		}
	}

	@Then("I verify response for future {string}")
	public void i_verify_response_for_future(String expectedDate) {
		String resp = response.asString();
		JsonPath js = new JsonPath(resp);
		Assert.assertNotEquals(expectedDate, js.get("date").toString());
	}

}
