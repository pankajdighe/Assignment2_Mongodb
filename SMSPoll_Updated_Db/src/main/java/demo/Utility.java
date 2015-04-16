package demo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import entities.Moderator;
import entities.Poll;

@Component
public class Utility {
	
	public Moderator search_moderator_by_id(ArrayList moderator_list,String id){
		Moderator m,result=null;
		if(moderator_list!=null)
		{
			for(int i=0;i<moderator_list.size();i++){
				m=(Moderator)moderator_list.get(i);
				if(m.getId().equals(id)){
					System.out.println("Found at position "+i);
					result=m;
				}
				
			}
		}
		return result;
	}
	
	public Moderator update_moderator_by_id(ArrayList moderator_list,String id,String new_email,String new_password){
		Moderator m,result=null;
		if(moderator_list!=null)
		{
			for(int i=0;i<moderator_list.size();i++){
				m=(Moderator)moderator_list.get(i);
				if(m.getId().equals(id)){
					System.out.println("Found at position "+i);
					m.setEmail(new_email);
					m.setPassword(new_password);
					result=m;
				}
				
			}
		}
		return result;
	}
	
	public Poll search_poll_by_id(ArrayList poll_list,String id){
		Poll p,result=null;
		if(poll_list!=null)
		{
			for(int i=0;i<poll_list.size();i++){
				p=(Poll)poll_list.get(i);
				if(p.getId().toString().equals(id)){
					result=p;
				}
				
			}
		}
		return result;
	}
	
	public void update_result(Poll poll,int choice){
		
	}
	
	public ArrayList get_moderator_polls(List poll_list,Moderator moderator){
		
		ArrayList moderator_poll=new ArrayList();
		Moderator poll_moderator=null;
		Poll poll;
		for(int i=0;i<poll_list.size();i++){
			poll=(Poll)poll_list.get(i);
			poll_moderator=poll.getModerator();
			if(moderator.getId().equals(poll_moderator.getId())){
				moderator_poll.add(poll);
			}
		}
		return moderator_poll;
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
		                }
		            }
				
			}
			
			
		}
		
		
		return messages;
	}
	
	
	
	public String getISO_Date(){
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.ms'Z'");
	    String nowAsISO = df.format(new Date());
	    return nowAsISO;
	}
	
	public static Date get_Date_From_String(String s){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.ms'Z'");
	    Date nowAsISO=null;
		try {
			nowAsISO = df.parse(s);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return nowAsISO;
	}

}
