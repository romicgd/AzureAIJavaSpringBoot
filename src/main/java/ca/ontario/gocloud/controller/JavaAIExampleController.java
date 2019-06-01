package ca.ontario.gocloud.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.microsoft.applicationinsights.TelemetryClient;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.microsoft.applicationinsights.telemetry.Duration;

@RestController
public class JavaAIExampleController {
	private final Logger logger = LoggerFactory.getLogger(JavaAIExampleController.class);
	
    @Autowired
    TelemetryClient telemetryClient;
	
	
   @RequestMapping("/")
   public String sayHello() {
       //track a custom event  
       telemetryClient.trackEvent("Sending a custom event...");

       //trace a custom trace
       telemetryClient.trackTrace("Sending a custom trace....");

       //track a custom metric
       Map<String, String> properties = new HashMap<>();
       Random rand = new Random(); 
       int rand_id = rand.nextInt(10); 
       properties.put("ClientId",  String.valueOf(rand_id));
       Double rand_val = new Double(rand.nextInt(5)+rand_id); 
       telemetryClient. trackMetric("Test custom metric", rand_val.doubleValue(), new Integer(1), rand_val, rand_val, new Double(0), properties);

       //track a custom dependency
       telemetryClient.trackDependency("SQL", "Insert", new Duration(0, 0, 1, 1, 1), true);
      String aiMessage = System.getenv("LogMessage");
      logger.info("build message["+aiMessage+"]"); 
      //spin CPU
      spin(10);
      return "Spring Boot - TestJava AI update0007!!";
   }
   

	private static void spin(int milliseconds) {
	    long sleepTime = milliseconds*1000000L; // convert to nanoseconds
	    long startTime = System.nanoTime();
	    while ((System.nanoTime() - startTime) < sleepTime) {}
	}
}
