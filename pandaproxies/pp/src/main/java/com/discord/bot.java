package com.discord;



import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


public class bot extends ListenerAdapter {
@Override
 public void onMessageReceived(MessageReceivedEvent event) {
	
	if(event.isFromType(ChannelType.PRIVATE) && !event.getAuthor().equals(event.getJDA().getSelfUser())) {
     String messa = event.getMessage().getContentRaw();
     String id = Long.toString(event.getAuthor().getIdLong());
     id = "pp_"+id;
     if(messa.equals("$help")) {
	 MessageChannel channel = event.getChannel();
     EmbedBuilder eb = new EmbedBuilder();
     eb.setTitle("User Commands");
     eb.setDescription("The bot's prefix is `$.` Examples below are just a general guideline for use.");
     eb.addField("View current regions", "`$regions`\n"
     		+ "Display supported regions for Residential Proxies.", true);
     eb.addField("Generate Proxies","`$gen amount region\n`"
     		+ "Generate proxies for a specific region in the quantities under 10000.",true);
     eb.addField("View your data", "`$data`\n"
     		+ "View how much data you currently have.", true);
     eb.addField("Claim your data","`$claim order_number email\n`"
     		+ "After paying on our store, you can then claim your data by invoking this command using the order number of the paid order.",true);
	 eb.setFooter("Panda Proxies", "https://cdn.discordapp.com/attachments/844118546157666375/877848475503566848/photo_2021-08-19_17-36-14.jpg");

	 channel.sendMessage(eb.build()).queue();
     }
     if(messa.equals("$data")) {
    	 MessageChannel channel = event.getChannel();
    		 Unirest.setTimeouts(0, 0);
    		 try {
    			   Unirest.setTimeouts(0, 0);
                  	HttpResponse<JsonNode> response= Unirest.get("https://basic.proxiesapi.xyz/proxy_api/v1/basic/users/get/"+id)
    					   .header("Authorization", "Bearer 69e5c480-dedb-11eb-ba80-0242ac130004")
    					   .header("Content-Type", "application/json")
    					   .asJson();
				JSONObject json = (JSONObject) response.getBody().getObject();
				String pl = json.get("data_gb").toString();
				System.out.println(pl);//changeto availableTraffic on v2
				if(response.getStatus()==200) {
					EmbedBuilder eb1 = new EmbedBuilder();
					  id = id.replace("pp_", "");
					   eb1.setTitle(""+pl+" GB");
					   eb1.setDescription("<@"+id+"> Top up now at `$claim`");
					   eb1.setFooter("Panda Proxies", "https://cdn.discordapp.com/attachments/844118546157666375/877848475503566848/photo_2021-08-19_17-36-14.jpg");
			    	   channel.sendMessage(eb1.build()).queue();
				
				return;
				}
			} catch (UnirestException e) {
				// TODO Auto-generated catch block
				EmbedBuilder eb1 = new EmbedBuilder();
				   eb1.setTitle("User not found");
				   eb1.setDescription("Claim now at `$claim`!");
				   eb1.setFooter("Panda Proxies", "https://cdn.discordapp.com/attachments/844118546157666375/877848475503566848/photo_2021-08-19_17-36-14.jpg");
		    	  channel.sendMessage(eb1.build()).queue();
		    	  return;
			}
    		
    	 
    	 EmbedBuilder eb1 = new EmbedBuilder();
		   eb1.setTitle("User not found");
		   eb1.setDescription("Claim now at `$claim`!");
		   eb1.setFooter("Panda Proxies", "https://cdn.discordapp.com/attachments/844118546157666375/877848475503566848/photo_2021-08-19_17-36-14.jpg");
  	       channel.sendMessage(eb1.build()).queue();
  	       return;
    		
    		
    			
     }
     if(messa.contains("$claim")) {
    	 MessageChannel channel = event.getChannel();
    	 String temp = messa.replace("$claim ", "");
    	 String email = temp.substring(5);
    	 String orderId= temp.replace(" "+email, "");
    	 if(email!="" && orderId!="") {
    		try {
    			
    			Unirest.setTimeouts(0, 0);
    			HttpResponse<JsonNode> response = Unirest.get("https://6d9b42ec6aac143e571f14305349883a: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@pandas-proxies.myshopify.com/admin/api/2021-07/orders.json?name="+orderId)
    			  .header("Content-Type", "application/json")
    			  .asJson();
    		            System.out.println(response.getStatus());
    			        JSONArray json = (JSONArray) response.getBody().getObject().get("orders");
    					JSONObject json2 = (JSONObject) json.get(0);
    					String userid = json2.get("id").toString();
    					System.out.println(userid);
    					String contact_email = json2.get("contact_email").toString();
    					JSONArray wifi = (JSONArray) json2.get("line_items");
    					JSONObject wifi2 = (JSONObject) wifi.get(0);	
    					String wifi3 = wifi2.get("name").toString().replace("Residential Proxies ", "");
    					wifi3 = wifi3.replace(" GB", "");
    				
				       if(email.equals(contact_email)) {
				    	    Unirest.setTimeouts(0, 0);
                           	HttpResponse<String> response2= Unirest.get("https://basic.proxiesapi.xyz/proxy_api/v1/basic/users/get/"+id)
             					   .header("Authorization", "Bearer 69e5c480-dedb-11eb-ba80-0242ac130004")
             					   .header("Content-Type", "application/json")
             					   .asString();
                           
				    	   if(response2.getStatus()==200) {
				    		   HttpResponse<JsonNode> response4 = Unirest.post("https://6d9b42ec6aac143e571f14305349883a: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@pandas-proxies.myshopify.com/admin/api/2021-07/orders/"+userid+"/close.json")
				    				   .header("Content-Type", "application/json")
				    				   .asJson();
				    		   
                             if(response4.getStatus()==200) {
                               Unirest.setTimeouts(0, 0);
                           	HttpResponse<String> response21= Unirest.post("https://basic.proxiesapi.xyz/proxy_api/v1/basic/users/add_balance")
             					   .header("Authorization", "Bearer 69e5c480-dedb-11eb-ba80-0242ac130004")
             					   .header("Content-Type", "application/json")
             					      .body("{\n    \"username\": \""+id+"\",\n    \"data_gb\": "+wifi3+"\n}")
             					      //"{\n    \"username\": \""+temp+"\",\n    \"data_gb\": "+quota+"\n}"
             					   .asString();
  				    		   System.out.println(response21.getStatus());
  				    	
                            	 if(response21.getStatus()==201) {
                            		 id = id.replace("pp_","");
                            		 System.out.println("existing user");
                            		  EmbedBuilder eb1 = new EmbedBuilder();
                            		  MessageChannel channel2 = event.getJDA().getTextChannelById("862576771923574794");
									   EmbedBuilder eb2 = new EmbedBuilder();
									   eb2.setTitle("Data deposited!");
									   eb2.setDescription("<@"+id+">  has claimed "+wifi3+"GB with "+ contact_email);
									   eb2.setFooter("Panda Proxies", "https://cdn.discordapp.com/attachments/844118546157666375/877848475503566848/photo_2021-08-19_17-36-14.jpg");
									   eb1.setTitle("Data deposited!");
									   eb1.setDescription("Data is deposited! Thanks for purchasing Panda proxies <@"+id+">! ");
									   eb1.setFooter("Panda Proxies", "https://cdn.discordapp.com/attachments/844118546157666375/877848475503566848/photo_2021-08-19_17-36-14.jpg");
									   channel2.sendMessage(eb2.build()).queue();
									   channel.sendMessage(eb1.build()).queue();
							    	  
									   return;
                            	 }
                            	  EmbedBuilder eb1 = new EmbedBuilder();
								   eb1.setTitle("An error occured!");
								   eb1.setDescription("Please try again!");
								   eb1.setFooter("Panda Proxies", "https://cdn.discordapp.com/attachments/844118546157666375/877848475503566848/photo_2021-08-19_17-36-14.jpg");
						    	   channel.sendMessage(eb1.build()).queue();
					        	   return;
                             }
                       	       EmbedBuilder eb1 = new EmbedBuilder();
							   eb1.setTitle("An error occured!");
							   eb1.setDescription("Please make sure you inputted the correct details!");
							   eb1.setFooter("Panda Proxies", "https://cdn.discordapp.com/attachments/844118546157666375/877848475503566848/photo_2021-08-19_17-36-14.jpg");
					    	   channel.sendMessage(eb1.build()).queue();
				        	   return;
				    	   }
				    	   System.out.println("here");
				    	   String SALTCHARS = "abcdefghijklmnopqrstuvwxyz1234567890";
				           StringBuilder salt = new StringBuilder();
				           Random rnd = new Random();
				           while (salt.length() < 10) { // length of the random string.
				               int index = (int) (rnd.nextFloat() * SALTCHARS.length());
				               salt.append(SALTCHARS.charAt(index));
				           }
				        
				           
				        	   System.out.println("new user");
				        	   HttpResponse<JsonNode> response4 = Unirest.post("https://6d9b42ec6aac143e571f14305349883a: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@pandas-proxies.myshopify.com/admin/api/2021-07/orders/"+userid+"/close.json")
				    				   .header("Content-Type", "application/json")
				    				   .asJson();
				    		   if(response4.getStatus()==200) {
				    			   System.out.println("creating user");
				    			   String saltStr = salt.toString();
						           String username = id;
						    	   api ae = new api();
						    	   String bot = ae.apid(username, saltStr, wifi3);
						           if(bot.equals(id)) {
						        	 	HttpResponse<String> response21= Unirest.post("https://basic.proxiesapi.xyz/proxy_api/v1/basic/users/add_balance")
				             					   .header("Authorization", "Bearer 69e5c480-dedb-11eb-ba80-0242ac130004")
				             					   .header("Content-Type", "application/json")
				             					      .body("{\n    \"username\": \""+id+"\",\n    \"data_gb\": "+wifi3+"\n}")
				             					      //"{\n    \"username\": \""+temp+"\",\n    \"data_gb\": "+quota+"\n}"
				             					   .asString();
						        	 	System.out.println(response21.getBody().toString());
				  				      if(response21.getStatus()!=201) {
				  				    	 EmbedBuilder eb1 = new EmbedBuilder();
										   eb1.setTitle("Error!");
										   eb1.setDescription("Account creation failed");
										   eb1.setFooter("Panda Proxies", "https://cdn.discordapp.com/attachments/844118546157666375/877848475503566848/photo_2021-08-19_17-36-14.jpg");
								    	   channel.sendMessage(eb1.build()).queue();
								    	   return;
				  				      }
				  				     id = id.replace("pp_","");
						     		  MessageChannel channel2 = event.getJDA().getTextChannelById("862576771923574794");
						        	   EmbedBuilder eb2 = new EmbedBuilder();
									   eb2.setTitle("New user!");
									   eb2.setDescription("<@"+id+"> new user has claimed "+wifi3+"GB using "+ contact_email);
									   eb2.setFooter("Panda Proxies", "https://cdn.discordapp.com/attachments/844118546157666375/877848475503566848/photo_2021-08-19_17-36-14.jpg");
						        	   EmbedBuilder eb1 = new EmbedBuilder();
									   eb1.setTitle("Hi there");
									   eb1.setDescription("Data claimed,welcome to Panda Proxies <@"+id+"> ! `$help` for more commands!");
									   eb1.setFooter("Panda Proxies", "https://cdn.discordapp.com/attachments/844118546157666375/877848475503566848/photo_2021-08-19_17-36-14.jpg");
									   channel2.sendMessage(eb2.build()).queue();
							    	   channel.sendMessage(eb1.build()).queue();
							    	 
						        	   return;
				    		   }
				        	  
						           EmbedBuilder eb1 = new EmbedBuilder();
								   eb1.setTitle("Error");
								   eb1.setDescription("Something happend please try again!");
								   eb1.setFooter("Panda Proxies", "https://cdn.discordapp.com/attachments/844118546157666375/877848475503566848/photo_2021-08-19_17-36-14.jpg");
						    	   channel.sendMessage(eb1.build()).queue();
						    	   return;
				    	   }
							 
						   
				           
						   EmbedBuilder eb1 = new EmbedBuilder();
						   eb1.setTitle("Error");
						   eb1.setDescription("Please make sure u input the correct details!");
						   eb1.setFooter("Panda Proxies", "https://cdn.discordapp.com/attachments/844118546157666375/877848475503566848/photo_2021-08-19_17-36-14.jpg");
				    	   channel.sendMessage(eb1.build()).queue();
				    	   return;
				       }
				       EmbedBuilder eb = new EmbedBuilder();
					   eb.setTitle("Data not found!");
					   eb.setDescription("Please make sure u input the correct details!");
					   eb.setFooter("Panda Proxies", "https://cdn.discordapp.com/attachments/844118546157666375/877848475503566848/photo_2021-08-19_17-36-14.jpg");
			    	   channel.sendMessage(eb.build()).queue();
			    	   return;
			} catch (UnirestException e) {
				EmbedBuilder eb1 = new EmbedBuilder();
				   eb1.setTitle("Error");
				   eb1.setDescription("Something happened, please try again");
				   eb1.setFooter("Panda Proxies", "https://cdn.discordapp.com/attachments/844118546157666375/877848475503566848/photo_2021-08-19_17-36-14.jpg");
		    	   channel.sendMessage(eb1.build()).queue();
			}
    	 }
    	
    	 
     }
     
     if(messa.equals("$regions")) {
    	 MessageChannel channel = event.getChannel();
    	  EmbedBuilder eb = new EmbedBuilder();
    	  eb.setTitle("Available regions");
          eb.setDescription("`Albania` `Algeria` `Andorra` `Angola Antigua and Barbuda` `Argentina` `Armenia` `Australia` `Austria` `Azerbaijan` `Bahrain` `Bangladesh` `Barbados` `Belarus` `Belgium` `Bolivia Bosnia and Herzegovina` `Botswana` `Brazil` `Bulgaria` `Burkina Faso` `Cambodia` `Cameroon` `Canada` `Cape Verde` `Chile` `China` `Colombia` `Congo` `Costa Rica` `Cote D'Ivoire` `Croatia` `Cuba` `Cyprus` `Czech Republic` `Denmark` `Dominican Republic` `Ecuador` `Egypt` `El Salvador` `Equatorial` `Guinea` `Estonia` `Ethiopia` `Finland` `France` `French` `Guiana` `Gabon` `Georgia` `Germany` `Ghana` `Greece` `Grenada` `Guatemala` `Haiti` `Honduras` `Hong Kong` `Hungary` `India` `Indonesia` `Iran`, `Islamic Republic of Iraq` `Ireland` `Israel` `Italy` `Jamaica` `Japan` `Jordan` `Kazakhstan` `Kenya` `Korea`, `Republic of Kuwait Kyrgyzstan Lao` `People's Democratic Republic Latvia Lebanon Libyan Arab` `Jamahiriya` `Lithuania` `Luxembourg` `Macao` `Macedonia`, `the Former Yugoslav` `Republic of Madagascar` `Malaysia` `Malta` `Mauritius` `Mexico` `Moldova`, `Republic of Mongolia` `Montenegro` `Morocco` `Mozambique` `Myanmar` `Namibia` `Nepal` `Netherlands` `New Caledonia` `New Zealand` `Nicaragua` `Nigeria` `Norway` `Oman` `Pakistan` `Palestine`, `State of Panama` `Paraguay` `Peru` `Philippines` `Poland` `Portugal` `Qatar` `Romania` `Russian Federation` `Saint Kitts and Nevis` `Saint Vincent and the Grenadines` `Saudi Arabia` `Senegal` `Serbia` `Singapore` `Slovakia` `Slovenia` `South Africa` `Spain` `Sri Lanka` `Sudan` `Suriname` `Sweden` `Switzerland` `Taiwan`,`China` `Tanzania`,`Thailand` `Togo` `Trinidad and Tobago` `Tunisia` `Turkey` `Ukraine` `United Arab Emirates` `United Kingdom` `United States` `Uruguay` `Uzbekistan` `Venezuela` `Viet Nam` `Yemen` `Zambia` `Zimbabwe`");
		  eb.setFooter("Panda Proxies", "https://cdn.discordapp.com/attachments/844118546157666375/877848475503566848/photo_2021-08-19_17-36-14.jpg");
          channel.sendMessage(eb.build()).queue();
     }
     if(messa.contains("$gen")) {
    	 MessageChannel channel = event.getChannel();
    	 String tempeor = messa.replace("$gen ", "");
         Pattern p = Pattern.compile("\\d+");
         Matcher m = p.matcher(tempeor);
         String generator="";
         while(m.find()) {
             generator = m.group();
         }
         int numbers = Integer.parseInt(generator);
         System.out.println(numbers);
         tempeor = tempeor.replace(generator+" ", "");
         if(numbers<10000) {
        	 csv cos = new csv();
				try {
					HttpResponse<JsonNode> response= Unirest.get("https://basic.proxiesapi.xyz/proxy_api/v1/basic/users/get/"+id)
	    					   .header("Authorization", "Bearer 69e5c480-dedb-11eb-ba80-0242ac130004")
	    					   .header("Content-Type", "application/json")
	    					   .asJson();
			  		JSONObject json = (JSONObject) response.getBody().getObject();
	        		String nama = json.get("username").toString();
	        		String kataLaluan = json.get("password").toString();
	        		String pl = json.get("data_gb").toString(); 
	        		System.out.println(nama+" "+kataLaluan);
	        		System.out.println(pl);
	        		if(response.getStatus()==200) {
	        			if(pl.contains("-") || pl.equals("0")) {
	        				id = id.replace("pp_","");
	        				EmbedBuilder eb1 = new EmbedBuilder();
							   eb1.setTitle("No data left!");
							   eb1.setDescription(" <@"+id+">, you have only "+pl+" GB of data please $claim!");
							   eb1.setFooter("Panda Proxies", "https://cdn.discordapp.com/attachments/844118546157666375/877848475503566848/photo_2021-08-19_17-36-14.jpg");
							   channel.sendMessage(eb1.build()).queue();
							   return;
	        			}
	        			utilities util = new utilities();
	        			String attachment = "";
	        			Unirest.setTimeouts(0, 0);
	        			HttpResponse<JsonNode> response3 = Unirest.get("https://dashboard.iproyal.com/api/residential/royal/reseller/access/countries")	
	        			  .header("X-Access-Token", "Bearer F0D4SA5SmEKh4Q7eLxP2OoZ00JX6S1Oc4A3HayZzsSDeU72wDKlqCppmxIT2")
	        			  .asJson();
	        			JSONArray array = (JSONArray) response3.getBody().getObject().get("countries");
	        			for(int l = 0; l<array.length(); l++) {
	        				JSONObject jeck = (JSONObject) array.get(l);
	        				String countryname = jeck.get("name").toString();
	        				if(countryname.equals(tempeor)) {
	        				   String countrycode = jeck.get("code").toString();
	        				   countrycode = countrycode.toUpperCase();
	        				   if(response3.getStatus()==200) {
	        					 
	        					  
	        						  cos.deleteFile("temp.txt");
	        			        		 attachment="penda.pandaproxies.xyz:5050:"+nama+":"+kataLaluan+"_country-";
	        			        		
	        			        		 cos.writeSpecific2(attachment, "temp.txt", numbers,countrycode);
	        			        		  channel.sendFile(new File("temp.txt"),"`Panda_Proxies.txt`").queue();
	        			        		  cos.deleteFile("temp.txt");
	        			        		 
	        			        		   return;
	        		        			
	        					}
	        				}
	        				
	        				
	        			}
	        			
	        		
	        			
	        			//connect api
	        			//if api ==200
	        			
	        			
	        		}
	        		EmbedBuilder eb1 = new EmbedBuilder();
					   eb1.setTitle("Error!");
					   eb1.setDescription("Make sure you input the correct country name including uppercase letter and spaces");
					   eb1.setFooter("Panda Proxies", "https://cdn.discordapp.com/attachments/844118546157666375/877848475503566848/photo_2021-08-19_17-36-14.jpg");
					   channel.sendMessage(eb1.build()).queue();
				} catch (UnirestException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		 
        		 
        	 
         }
    	 
     }
     
 }
	if(!event.getAuthor().equals(event.getJDA().getSelfUser()) && event.getChannel().getId().equals("877861444849385482")){
		String msg = event.getMessage().getContentRaw();
		MessageChannel mes = event.getChannel();
		if(msg.equals("$balance1")) {
			System.out.println("true");
			try {
				HttpResponse<JsonNode> response = Unirest.get("https://basic.proxiesapi.xyz/proxy_api/v1/basic/reseller")
						.header("Authorization", "Bearer 69e5c480-dedb-11eb-ba80-0242ac130004")
	   					   .header("Content-Type", "application/json")
	   					   .asJson();
				JSONObject jsong = response.getBody().getObject();
				String data = jsong.get("data_left").toString();
				System.out.println(data);
				mes.sendMessage(data).queue();
				return;
			} catch (UnirestException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(msg.contains("$viewuser1")) {
			String temp = msg.replace("$viewuser1 ", "");
			temp = "pp_"+temp;
			
			try {
				   Unirest.setTimeouts(0, 0);
                 	HttpResponse<JsonNode> response= Unirest.get("https://basic.proxiesapi.xyz/proxy_api/v1/basic/users/get/"+temp)
   					   .header("Authorization", "Bearer 69e5c480-dedb-11eb-ba80-0242ac130004")
   					   .header("Content-Type", "application/json")
   					   .asJson();
				JSONObject json = (JSONObject) response.getBody().getObject();
				String pl = json.get("data_gb").toString();//changeto availableTraffic on v2
				if(response.getStatus()==200) {
					temp= temp.replace("pp_","");
					EmbedBuilder eb1 = new EmbedBuilder();
					   eb1.setTitle("Data: "+pl);
					   eb1.setDescription("<@"+temp+"> has "+pl+"GB");
					   eb1.setFooter("Panda Proxies", "https://cdn.discordapp.com/attachments/844118546157666375/877848475503566848/photo_2021-08-19_17-36-14.jpg");
			    	   mes.sendMessage(eb1.build()).queue();
				
				return;
				}
			} catch (UnirestException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					
			
		}
		if(msg.contains("$add1")) {
			String temp = msg.replace("$add1 ", "");
			String wifi3 =  temp.substring(0, temp.length()-18).replace(" ", "");
			String discordata = temp.replace(wifi3+" ","");
			discordata = "pp_"+discordata;
			 Unirest.setTimeouts(0, 0);
         	HttpResponse<JsonNode> response = null;
			try {
				response = Unirest.get("https://basic.proxiesapi.xyz/proxy_api/v1/basic/users/get/"+discordata)
						   .header("Authorization", "Bearer 69e5c480-dedb-11eb-ba80-0242ac130004")
						   .header("Content-Type", "application/json")
						   .asJson();
				
			} catch (UnirestException e1) {
				// TODO Auto-generated catch block
				return;
			}
			JSONObject json = (JSONObject) response.getBody().getObject();
			String discordId= json.get("username").toString();
	    	   if(discordId!=null) {
	    		   System.out.println("admin add");
                   Unirest.setTimeouts(0, 0);
					try {
					    Unirest.setTimeouts(0, 0);
                       	HttpResponse<String> response212= Unirest.post("https://basic.proxiesapi.xyz/proxy_api/v1/basic/users/add_balance")
         					   .header("Authorization", "Bearer 69e5c480-dedb-11eb-ba80-0242ac130004")
         					   .header("Content-Type", "application/json")
         					      .body("{\n    \"username\": \""+discordId+"\",\n    \"data_gb\": "+wifi3+"\n}")
         					      //"{\n    \"username\": \""+temp+"\",\n    \"data_gb\": "+quota+"\n}"
         					   .asString();
				    		   System.out.println(response212.getStatus());
						  System.out.println(response212.getStatus());
		                	 if(response212.getStatus()==201) {
		                		 discordata= discordata.replace("pp_","");
		                		  System.out.println("custom data added");
		                		  EmbedBuilder eb1 = new EmbedBuilder();
								   eb1.setTitle("Data deposited!");
								   eb1.setDescription("Data is deposited for user <@"+discordata+">! ");
								   eb1.setFooter("Panda Proxies", "https://cdn.discordapp.com/attachments/844118546157666375/877848475503566848/photo_2021-08-19_17-36-14.jpg");
						    	   mes.sendMessage(eb1.build()).queue();
					        	   return;
		                	 }
		                	  EmbedBuilder eb1 = new EmbedBuilder();
							   eb1.setTitle("An error occured!");
							   eb1.setDescription("Please try again!");
							   eb1.setFooter("Panda Proxies", "https://cdn.discordapp.com/attachments/844118546157666375/877848475503566848/photo_2021-08-19_17-36-14.jpg");
					    	   mes.sendMessage(eb1.build()).queue();
				        	   return;
					} catch (UnirestException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    	
	    	   }
			
		}
		if(msg.contains("$remove1")) {
			String temp = msg.replace("$remove1 ", "");
			String wifi3 =  temp.substring(0, temp.length()-18).replace(" ", "");
			String discordata = temp.replace(wifi3+" ","");
			discordata  = "pp_"+discordata;
			HttpResponse<JsonNode> response = null;
			try {
				response = Unirest.get("https://basic.proxiesapi.xyz/proxy_api/v1/basic/users/get/"+discordata)
						   .header("Authorization", "Bearer 69e5c480-dedb-11eb-ba80-0242ac130004")
						   .header("Content-Type", "application/json")
						   .asJson();
			} catch (UnirestException e1) {
				// TODO Auto-generated catch block
				return;
			}
			JSONObject json = (JSONObject) response.getBody().getObject();
			String discordId= json.get("username").toString();
	    	   if(discordId!=null) {
	    		   System.out.println("admin dlt");
                   Unirest.setTimeouts(0, 0);
		    		   HttpResponse<String> response2;
					try {
						response2= Unirest.post("https://basic.proxiesapi.xyz/proxy_api/v1/basic/users/subtract_balance")
	         					   .header("Authorization", "Bearer 69e5c480-dedb-11eb-ba80-0242ac130004")
	         					   .header("Content-Type", "application/json")
	         					      .body("{\n    \"username\": \""+discordId+"\",\n    \"data_gb\": "+wifi3+"\n}")
	         					      .asString();
						  System.out.println(response2.getStatus());
		                	 if(response2.getStatus()==201) {
		                		 discordata= discordata.replace("pp_","");
		                		  System.out.println("custom data taken");
		                		  EmbedBuilder eb1 = new EmbedBuilder();
								   eb1.setTitle("Data removed!");
								   eb1.setDescription("Data is removed for user <@"+discordata+">! ");
								   eb1.setFooter("Panda Proxies", "https://cdn.discordapp.com/attachments/844118546157666375/877848475503566848/photo_2021-08-19_17-36-14.jpg");
						    	   mes.sendMessage(eb1.build()).queue();
					        	   return;
		                	 }
		                	  EmbedBuilder eb1 = new EmbedBuilder();
							   eb1.setTitle("An error occured!");
							   eb1.setDescription("Please try again!");
							   eb1.setFooter("Panda Proxies", "https://cdn.discordapp.com/attachments/844118546157666375/877848475503566848/photo_2021-08-19_17-36-14.jpg");
					    	   mes.sendMessage(eb1.build()).queue();
				        	   return;
					} catch (UnirestException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    	
	    	   }
		}
		
	}
	
	}
}
