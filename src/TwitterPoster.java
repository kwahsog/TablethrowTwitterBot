import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class TwitterPoster {

	    public static void main(String [] args) throws TwitterException, InterruptedException 
	    {
	    	//Initialise variables
	        List<Long> prevreplyIDs = new ArrayList<Long>();
	        Random random = new Random();
	        int rand;
	        Twitter twitter = TwitterFactory.getSingleton();
	        
	        //build array of previously sent tweets 
	        //User user = twitter.verifyCredentials();
	        twitter.verifyCredentials();
            List<Status> prevtweets = twitter.getHomeTimeline();
            
            //optional print checks
            for (Status status : prevtweets) 
            {
                System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
            }            
            //create array of previous tweets
            for (Status status : prevtweets)
            {
            	prevreplyIDs.add(status.getInReplyToStatusId());
            }            	
            System.out.println(Arrays.toString(prevreplyIDs.toArray()));
            
	        
	        //ArrayList<Long> TweetIds = new ArrayList<>();	  
	        
	        ArrayList<String> Textprompts = new ArrayList<String>();
	        Textprompts.add("@tablebott");
	        Textprompts.add("#fliptable");
	        Textprompts.add("#tableflip");
	        Textprompts.add("#throwtable");
	        
	        //begin loop to send tweets. Generate random String from Textprompts array to search for, then send message to user. 
	        while(true)
	        {	        	    	
	    	rand = random.nextInt(Textprompts.size());		    	
	        Query query = new Query(Textprompts.get(rand));  
	        QueryResult results = twitter.search(query);
	            

	    	if (results.nextQuery() != null) 
	    	{
	    		
	    	Status tweetresult = results.getTweets().get(0);    
	    		
	    	if(!prevreplyIDs.contains(tweetresult.getId()))	    		
	    	{	    	
    	    //Send tweet to user, print tweet sent and store id in array.	
	    		
	        StatusUpdate update = new StatusUpdate(".@" + tweetresult.getUser().getScreenName()+" (╯°□°）╯︵ ┻━┻");
	        update.inReplyToStatusId(tweetresult.getId());
            //Status status = twitter.updateStatus(update);             
	        System.out.println(".@" + tweetresult.getUser().getScreenName()+" (╯°□°）╯︵ ┻━┻");
	        prevreplyIDs.add(tweetresult.getId());	        
	    	}
	    	
	    	else
	    	{
	    		System.out.println("Nothing found");
	    	}

	    	//poll and send tweet every 10 mins if one found.
	    	System.out.println("10 min wait...");
	    	Thread.sleep(1000*60*10);	  	    	
	        }
	    	}

	    }
}
