package resource;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

public class Utils {

	RequestSpecification res;

	/**
	 * This method is used to log the request and reponse in logging.txt file
	 * 
	 * @return
	 * @throws FileNotFoundException
	 */
	public RequestSpecification requestSpecification() throws FileNotFoundException {
		PrintStream ps = new PrintStream(new FileOutputStream("logging.txt"));
		RestAssured.baseURI = "https://api.ratesapi.io/";
		res = new RequestSpecBuilder().setBaseUri("https://api.ratesapi.io/")
				.addFilter(RequestLoggingFilter.logRequestTo(ps)).addFilter(ResponseLoggingFilter.logResponseTo(ps))
				.build();
		return res;
	}

	/**
	 * This method is used to get yesterdays date
	 * 
	 * @return
	 */
	private Date yesterday() {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return cal.getTime();
	}

	/**
	 * This method is used to formate the date
	 * 
	 * @return
	 */
	public String getYesterdayDateString() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(dateFormat.format(yesterday()) + "inside yesterday method");
		return dateFormat.format(yesterday());
	}

	private String currentDate() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}
}
