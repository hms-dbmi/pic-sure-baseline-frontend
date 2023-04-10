To Run the regression selenium tests on PICSUREUI, we should have some prerequisites. Please check the below steps.


1.Create Config.properties file, it has mapping of property value with  test data files path 
which is going to run against  test methods.

2. TestData is stored in baseline-pic-sure/selenium-tests/resources/testConfigs/


3.All the test data files have input and expected parameters - Testname, url,authmethod,Text to Search, Expected Patient count etc.

 
4.Path to Config.properties can be configurable; To Execute test cases on different browsers you have to execute following 
  scripts.
  
 To run ./runchromeheadless.sh  or ./runfirefoxheadless.sh first change the permission  
   
  chmod 777 ./runchromeheadless.sh or Chmod 777 ./runfirefoxheadless.sh 
  
5.To execute shell scripts you have to pass login credentials  
 
  
 E.g.   ./runchromeheadless.sh <username> <password>
 		
 		or 
 		
 		./runfirefoxheadless.sh <username> <password>
 
 
6.For each environment like integration, preprod or production the values like url, patient count etc.
  differs, so before we run the test automation code please make sure these value should get updated correctly, 
  else the test result will be "failed".

7.You can check the result and test run logs at working direcrtory TestReportSummary.log and BDCpcisureui.log
 
  You can change the logfile name and logging level (e.g. INFO,DEBUG,ALL etc) as you wish by updating
  log4j.properties file.
  
  
