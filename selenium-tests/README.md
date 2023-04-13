To run the regression Selenium tests on the baseline PICSURE-UI, follow the steps below:

Prereq: The user needs to have Docker installed  (

1.) Clone the XX branch and navigate to the (https://github.com/hms-dbmi/baseline-pic-sure/tree/update-selenium-read.me/selenium-tests/resources/testConfigsInt) (this link will change) 

1. If you are running the Selenium tests against baseline PIC-SURE, the tests are stored in the selenium-tests/src/test/resources/config.properties file.

2. The config.properties file contains the mapping of the property value with the test data files path, which it will run against the test methods. 

3. The tests and their expected values are stored in baseline-pic-sure\selenium-tests\resources\testConfigs  (Atul: There is no such file name) 

4. All the test data files have input and expected parameters - Testname, url,authmethod,Text to Search, Expected Patient count etc. 
   (Atul where is this?) 
   
   Depending on OS:  Window: download gekodriver / (they need to grab the latest one) (https://github.com/mozilla/geckodriver/releases) 

5. Linux: The path to the config.properties file can be configurable. To execute test cases on a different browsers you have to execute following 
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

6. To execute the shell scripts you have to pass the login credentials  
 ```
  ./runchromeheadless.sh <username> <password>	or ./runfirefoxheadless.sh <username> <password>
 ```
 
7. For each environment, such as development, test, or production the values (url, patient count etc.)
   could differ. Therefore, before we run the test automation code, ensure these values get updated correctly, or else the test result will fail.

8. To view the results of the tests and test the run logs at the working direcrtory, see TestReportSummary.log and BDCpcisureui.log (why is BDC referenced here? I thought there was another repo for BDC? Should this repo only be the baseline tests?) 
 
  To change the logfile name and logging level (e.g. INFO,DEBUG,ALL etc) update the log4j.properties file.
  
  
