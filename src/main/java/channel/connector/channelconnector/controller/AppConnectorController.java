package channel.connector.channelconnector.controller;

import java.util.Arrays;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@EnableCaching
@RestController
@RequestMapping("/big")
public class AppConnectorController {
	
	// creating a logger 
    Logger logger = LoggerFactory.getLogger(AppConnectorController.class);
    
    RestTemplate restTemplate = new RestTemplate();
    
    @Value("${responsetimeout}")
    private Integer timeout;
   

    @Cacheable("Manoj112")
    @PostMapping("/upi-validate-address-request")
    public String validateAddressRequest() {
    	logger.info("validateAddressRequest method call");
    	HttpHeaders headers = new HttpHeaders();
    	String requestJson = "{\"mobile\":\"1298989@test\"}";
    	headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
        String response =  restTemplate.exchange(
        "http://206.189.153.172:9092/big/upi-validate-address-request",
        HttpMethod.POST, entity, String.class).getBody();
        
        //wait for response
        ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);
        exec.schedule(new Runnable() {
                  public void run() {
                	  logger.info("Api response : {}", response);
                  }
        },timeout,TimeUnit.SECONDS);
         return "HTTP 200 Ok";
        
    }

}
