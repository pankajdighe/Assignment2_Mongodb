package demo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kafka.SimpleProducer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import entities.Poll;

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
        	
        	ArrayList<String> messeges=get_messages(all_poll);
        	System.out.println("Count of messages is"+messeges.size());
        	for(String s:messeges){
        		System.out.println("Message from array list is"+s);
        		SimpleProducer.send_message(s);
        	}
        	
        	System.out.println("************************************************************************");
        	
        }
        
        
        
    }
	
	
public ArrayList<String> get_messages(List poll_list){
		
		ArrayList<String> messages=new ArrayList<String>();
		String message="";
		Poll poll;
		Date date_now=new Date();
		Date expired_at;
		for(int i=0;i<poll_list.size();i++){
			poll=(Poll)poll_list.get(i);
			if(poll!=null){
				expired_at=Utility.get_Date_From_String(poll.getExpired_at());
				
				 if(date_now.after(expired_at)){
					 if(!poll.isClosed()){
		                System.out.println("Poll Expired"+"POLL ID IS "+poll.getId());
		                message=poll.getModerator().getEmail()+":"+"010123451"+":"+"Poll Result[";
		                
		                	for(int j=0;j<poll.getChoice().length;j++){
		                		if(j+1==poll.getChoice().length)
		                		message=message+poll.getChoice()[j]+"="+poll.getResults()[j];
		                		else
		                			message=message+poll.getChoice()[j]+"="+poll.getResults()[j]+",";
		                	}
		                message=message+"]";	
		                System.out.println("Message is"+message);
		                messages.add(message);
		                message="";
		                poll.setClosed(true);
		                poll_repo.save(poll);
		                }
		            }
				
			}
			
			
		}
		
		
		return messages;
	}

}
