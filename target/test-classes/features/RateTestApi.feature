Feature: Validating foreign Rate Api 


Scenario: verify success status is displayed correctly for latest 
	Given load Foreign Rate Api url 
	When user calls latest foreign exchange rate Get request 
	Then the API call is success with status code 200 
	And I verify defualt base response "EUR" 
	
Scenario Outline: 
	verify success status is displayed correctly for symbols and base 
	Given load Foreign Rate Api url 
	When user calls "<name>" foreign exchange rate Get request with value "<currency>" 
	Then the API call is success with status code 200 
	And I verify response contains "<currency>" 
	Examples: 
		|name|currency|
		|symbols|USD|
		|symbols|GBP|
		|base|USD|
		|base|GBP|
		

Scenario: verify invalid values for symbols 
	Given load Foreign Rate Api url 
	When user calls "symbols" foreign exchange rate Get request with value "abc" 
	Then the API call is success with status code 400 
	And I verify response contains "error"

@RUN	
Scenario: verify invalid values for base 
	Given load Foreign Rate Api url 
	When user calls "base" foreign exchange rate Get request with value "abc" 
	Then the API call is success with status code 400 
	And I verify response error message "Base 'abc' is not supported."
	
	
Scenario: verify error message is displayed for incorrect URL 
	Given load Foreign Rate Api url 
	When user calls API with incorrect url 
	Then API call the status code 400 
	And I verify response error message "time data 'TestURL' does not match format '%Y-%m-%d'" 
	
	
Scenario Outline: verify success status and response is displayed for valid Date 
	Given load Foreign Rate Api url 
	When user calls API with valid "<date>" 
	Then the API call is success with status code 200 
	And I verify response showing proper "<date>" 
	Examples: 
		|date|
		|2021-01-01|
		|2020-10-11| 
		|2021-01-05|
		
		
Scenario Outline: verify status and response is displayed for symbols and base 
	Given load Foreign Rate Api url 
	When user calls API with valid "<date>", "<name>" and "<currency>" 
	And I verify response showing proper "<date>" 
	Examples: 
		|date|name|currency|
		|2021-01-04|symbols|USD|
		|2021-01-14|symbols |GBP|
		|2021-01-05|base|USD|
		|2021-01-12|base|GBP|
		
		
Scenario Outline: verify error is displayed for invalid Date 

	Given load Foreign Rate Api url 
	When user calls API with valid "<date>" 
	Then the API call is success with status code 400 
	And I verify response error message "time data '<date>' does not match format '%Y-%m-%d'" 
	Examples: 
		|date|
		|2020-01|
		|10-11-2020|
		|202101-11|
		
		
Scenario Outline: verify data is not displayed for future dates 
	Given load Foreign Rate Api url 
	When user calls API with valid "<date>" 
	Then the API call is success with status code 200 
	And I verify response for future "<date>" 
	Examples: 
		|date|
		|2021-01-20|
		|2020-10-21| 
		|2021-01-22|
		
