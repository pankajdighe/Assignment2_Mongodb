package demo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kafka.SimpleProducer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.ms'Z'");
	
	@Autowired
	Utility utility;
	
	@Autowired
	private PollRepository poll_repo;

	@Scheduled(fixedRate = 5000)
    public void check_expired_polls() {
		
		
        System.out.println("The time is now " + dateFormat.format(new Date()));
        List all_poll;
        if(poll_repo!=null){
        	all_poll=poll_repo.findAll();
        	
        	ArrayList<String> messeges=utility.get_messages(all_poll);
        	System.out.println("Count of messages is"+messeges.size());
        	for(String s:messeges){
        		System.out.println("Message from array list is"+s);
        		SimpleProducer.send_message(s);
        	}
        	
        	System.out.println("************************************************************************");
        	
        }
        
        
        
    }

}
