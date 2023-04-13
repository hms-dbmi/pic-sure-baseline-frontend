To run the regression Selenium tests on the baseline PICSURE-UI, follow the steps below:

Prerequisite: Docker must be installed 

1.) Clone the selenium-test (https://github.com/hms-dbmi/baseline-pic-sure/tree/selenium-tests) branch.

2.) Navigate to the TestConfig file https://github.com/hms-dbmi/baseline-pic-sure/tree/selenium-tests/selenium-tests/resources/testConfigsInt   (NOTE: this link will need to be update since it is the link for TestConfigInt) 

3.) If you are running the Selenium tests against baseline PIC-SURE, the tests are stored in the selenium-tests/src/test/resources/config.properties file.

4.) The config.properties file contains the mapping of the property value with the test data files path, which it will run against the test methods. 

5.) The tests and their expected values are stored in baseline-pic-sure\selenium-tests\resources\testConfigs  (There is no such file name) 

6.) All the test data files have input and expected parameters - Testname, url,authmethod,Text to Search, Expected Patient count etc. 
   (Atul where is this?) 
   
   Depending on OS:  Window: download gekodriver / (they need to grab the latest one) (https://github.com/mozilla/geckodriver/releases) 

 Linux: The path to the config.properties file can be configurable. To execute test cases on a different browsers you have to execute following 
  scripts.
  
 To change the permission, for either Google or Firefox, first run: 
 ```
 ./runchromeheadless.sh  or ./runfirefoxheadless.sh 
 ```
 and 
 ```
 chmod 777 ./runchromeheadless.sh or Chmod 777 ./runfirefoxheadless.sh 
 ```
Windows: (Atul to send to Danielle)  

7.) To execute the shell scripts you have to pass the login credentials  
 ```
  ./runchromeheadless.sh <username> <password>	or ./runfirefoxheadless.sh <username> <password>
 ```
 
8.) For each environment, such as development, test, or production the values (url, patient count etc.)
   could differ. Therefore, before we run the test automation code, ensure these values get updated correctly, or else the test result will fail.

9.) To view the results of the tests and test the run logs at the working direcrtory, see TestReportSummary.log and BDCpcisureui.log (why is BDC referenced here? I thought there was another repo for BDC? Should this repo only be the baseline tests?) 
 
  To change the logfile name and logging level (e.g. INFO,DEBUG,ALL etc) update the log4j.properties file.
  
  
