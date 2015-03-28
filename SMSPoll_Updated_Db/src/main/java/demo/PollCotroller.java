package demo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import entities.Moderator;
import entities.Poll;
import entities.View;

@RestController
@RequestMapping("/api/v1")
@EnableWebSecurity
@ComponentScan
public class PollCotroller {

	ArrayList moderator_list = new ArrayList();
	ArrayList poll_list = new ArrayList();

	//Utility utility = new Utility();
	
	@Autowired
	Utility utility;
	
	
	@Autowired
	private ModeratorRepository moderator_repo;
	
	@Autowired
	private PollRepository poll_repo;

	//private final AtomicInteger moderator_counter = new AtomicInteger();
	//private final AtomicInteger poll_counter = new AtomicInteger();
	



	@RequestMapping(value = "/moderators/{moderatorId}", method = { RequestMethod.GET })
	public @ResponseBody Moderator getModerator(@PathVariable String moderatorId)
			throws Exception {
		System.out.println("In get Moderator");
		if (moderator_repo!=null)
		if (moderator_repo.count()>0)
			{
				System.out.println("I got Moderator List");
				Moderator m = moderator_repo.findOne(moderatorId);
				if (m != null)
					return m;
			}

		return null;

	}

	@RequestMapping(value = "/moderators", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Moderator> createModerator(
			@RequestBody @Validated({Validation.ValidateAll.class}) Moderator moderator) {
		String count_string=String.valueOf(moderator_repo.count()+1) ;
		Moderator m = new Moderator(count_string,
				moderator.getName(), moderator.getEmail(),
				moderator.getPassword(), utility.getISO_Date());
		//code to insert moderator in MongoDB
		moderator_repo.save(m);
		//end of code for mondo db
		return new ResponseEntity<Moderator>(m, HttpStatus.CREATED);

	}

	@RequestMapping(value = "/moderators/{moderatorId}", method = { RequestMethod.PUT })
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseEntity<Moderator> updateModerator(
			@PathVariable String moderatorId, @RequestBody @Validated({Validation.ValidateFields.class}) Moderator moderator) {
		Moderator m_old = null;
		if (moderator_repo!=null)
		if (moderator_repo.count()>0)
			{
			m_old = moderator_repo.findOne(moderatorId);
			if(m_old!=null){
				m_old.setEmail(moderator.getEmail()); 
				m_old.setPassword(moderator.getPassword());
				moderator_repo.save(m_old);
				return new ResponseEntity<Moderator>(m_old, HttpStatus.OK);
			}
				
			}

		return new ResponseEntity<Moderator>(m_old, HttpStatus.BAD_REQUEST);

	}

	@JsonView(View.WithoutResult.class)
	@RequestMapping(value = "/moderators/{moderatorId}/polls", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Poll> createPoll(@PathVariable String moderatorId,
			@RequestBody @Valid Poll poll) {
		Poll p = null;
		if (moderator_repo!=null)
		if (moderator_repo.count()>0)
			 {
				Moderator m = moderator_repo.findOne(moderatorId);
				if (m != null) {
					Date date=new Date();
					String count_string =Long.toString (date.hashCode(), 36);
					p = new Poll("P" + count_string+poll_repo.count(),
							poll.getQuestion(), poll.getStarted_at(),
							poll.getExpired_at(), poll.getChoice());
					p.setModerator(m);
					poll_repo.save(p);
					System.out.println("Added POLL"+"counter is "+count_string);
					return new ResponseEntity<Poll>(p, HttpStatus.CREATED);
				}
			}

		return new ResponseEntity<Poll>(p, HttpStatus.BAD_REQUEST);

	}

	@JsonView(View.WithoutResult.class)
	@RequestMapping(value = "/polls/{pollId}", method = { RequestMethod.GET })
	public @ResponseBody Poll getPoll_without_result(@PathVariable String pollId)
			throws Exception {
		if (poll_repo!=null)
		if (poll_repo.count()>0)
			 {
				Poll p = poll_repo.findOne(pollId);
				if (p != null)
					return p;
			}

		return null;

	}

	@RequestMapping(value = "/moderators/{moderatorId}/polls/{pollId}", method = { RequestMethod.GET })
	public @ResponseBody Poll getPoll_with_result(
			@PathVariable String moderatorId, @PathVariable String pollId)
			throws Exception {
		if (moderator_repo!=null)
		if (moderator_repo.count()>0)
			 {
				Moderator m = moderator_repo.findOne(moderatorId);
				if (m != null) {
					if (poll_repo.count()>0)
						 {
							Poll p =poll_repo.findOne(pollId);
							if (p != null && p.getModerator().getId().equals(m.getId()) )
								return p;
						}
				}
			}
		return null;

	}

	@RequestMapping(value = "/moderators/{moderatorId}/polls", method = { RequestMethod.GET })
	public @ResponseBody List<Poll> getList_Polls(
			@PathVariable String moderatorId) throws Exception {
		if (moderator_repo!=null)
			if (moderator_repo.count() > 0) {
				Moderator m = moderator_repo.findOne(moderatorId);
				if (m != null) {

					if (poll_repo != null) {

						if (poll_repo.count() > 0) {
							
							List all_poll=poll_repo.findAll();
							List poll = utility.get_moderator_polls(all_poll, m);
							return poll;
						}

					}

				}
			}

		return null;

	}

	@RequestMapping(value = "/moderators/{moderatorId}/polls/{pollId}", method = { RequestMethod.DELETE })
	public @ResponseBody ResponseEntity<String> delete_Polls(
			@PathVariable String moderatorId, @PathVariable String pollId)
			throws Exception {
		if (moderator_repo!=null)
			if (moderator_repo.count() > 0) {
				Moderator m = moderator_repo.findOne(moderatorId);
				if (m != null) {

					if (poll_repo != null && !pollId.equals(""))
						if (poll_repo.count() > 0) {
							Poll p = poll_repo.findOne(pollId);//utility.search_poll_by_id(poll_list,pollId);
							if (p != null && p.getModerator().getId().equals(m.getId())) {
								poll_repo.delete(pollId);
								return new ResponseEntity<String>(
										"Record Deleted!!",
										HttpStatus.NO_CONTENT);
							}else{
								return new ResponseEntity<String>(
										"You dont have access to Delete this record!!",
										HttpStatus.NO_CONTENT);
							}
						}

				}
			}

		return new ResponseEntity<String>("No Record Found/Deleted!!",
				HttpStatus.NO_CONTENT);

	}

	@RequestMapping(value = "/polls/{pollId}", method = { RequestMethod.PUT })
	public ResponseEntity<String> vote(@PathVariable String pollId,
			@RequestParam Integer choice) {
		if (poll_repo != null && !pollId.equals(""))
			if (poll_repo.count()> 0) {
				Poll p = poll_repo.findOne(pollId);
				if (p != null && choice<p.getResults().length) {
					p.getResults()[choice]++;
					poll_repo.save(p);
					return new ResponseEntity<String>("Voting Completed!!",
							HttpStatus.NO_CONTENT);
				}
			}
		return new ResponseEntity<String>("No Voting don!!",
				HttpStatus.NO_CONTENT);

	}

}
