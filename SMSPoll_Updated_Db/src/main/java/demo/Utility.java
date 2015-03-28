package demo;

import java.text.DateFormat;
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
	
	public String getISO_Date(){
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.ms'Z'");
	    String nowAsISO = df.format(new Date());
	    return nowAsISO;
	}

}
