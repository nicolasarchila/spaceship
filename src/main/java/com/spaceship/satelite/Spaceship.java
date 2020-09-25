package com.spaceship.satelite;


import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;

public class Spaceship implements SpaceshipInterface{
	
	
	public StringBuilder message(ArrayList<String> list1,ArrayList<String> list2,ArrayList<String> list3) {
		
		StringBuilder result;
		
		AtomicInteger index = new AtomicInteger();
    	index.decrementAndGet();
        
        String[] stringArraylist1= list1.toArray(new String[0]);
        
        Stream.of(stringArraylist1)
        		.filter(i -> !i.equals(list2.get(index.incrementAndGet()))
        			|| !i.equals(list3.get(index.get())))
        		.filter(y -> y.trim().equals(""))
        		.forEach((x)->{
        			if(!list2.get(index.get()).trim().equals("")) {
        				list1.remove(index.get());
        				list1.add(index.get(),list2.get(index.get()));
        			}else {
        				if(!list3.get(index.get()).trim().equals("")) {
        					list1.remove(index.get());
        					list1.add(index.get(),list3.get(index.get()));
        				}
        			}
        	});
        
        stringArraylist1= list1.toArray(new String[0]);
        
        result = this.text(stringArraylist1);      
        
		return result;
	}
	
	
	
	StringBuilder text(String[] stringArraylist1) {
		StringBuilder result = new StringBuilder();
		
		Stream.of(stringArraylist1)
        .forEach((x)->{
        	result.append(" ").append(x);
        });
        
        
		return result;
	}
	
	public ArrayList<String> messageListToArrayList(JSONArray messages){
		ArrayList<String> list = new ArrayList<>();
		
    	for (int j = 0; j < messages.length(); j++) {
    		list.add(messages.getString(j));
    	}
    	
    	return list;
	}
	
	public ArrayList<String> messageStringToArrayList(String [] messages){
		ArrayList<String> list = new ArrayList<>();
		
    	for (int j = 0; j < messages.length; j++) {
    		System.out.println(messages[j]);
    		list.add(messages[j]);
    	}
    	
    	return list;
	}



	@Override
	public StringBuilder getMessage(JSONArray list) {
		
		ArrayList<String> list1 = new ArrayList<>();
    	ArrayList<String> list2 = new ArrayList<>();
    	ArrayList<String> list3 = new ArrayList<>();
        // print value
    	JSONObject json;
    	
    	json = list.getJSONObject(0);
    	JSONArray messages = json.getJSONArray("message"); 
    	list1 = this.messageListToArrayList(messages);
        
        json = list.getJSONObject(1);
    	messages = json.getJSONArray("message"); 
    	list2 = this.messageListToArrayList(messages);
        
        json = list.getJSONObject(2);
    	messages = json.getJSONArray("message"); 
    	list3 = this.messageListToArrayList(messages);

    	return this.message(list1, list2, list3);
	}
	
	public Satelite location(Satelite satelite1,double radius1,Satelite satelite2,
    		double radius2,Satelite satelite3,double radius3) {
    	
    	//Correr hacia el origen
    	
    	if(satelite2.x == 0 && satelite3.x == 0) {
    		satelite1 = satelite3 ; 
    	}
    	
    	double originx = satelite1.x * -1;
    	double originy = satelite1.y * -1;
    	double angle = 0;
    	boolean madeRotation = false;
    	boolean yIsPositive = false;
    	double norma2 = 0;
    	double norma3 = 0;
    	
    	satelite1 = this.moveSatelite(originx,originy,satelite1);
    	
    	satelite2 = this.moveSatelite(originx,originy,satelite2);
    	
    	satelite3 = this.moveSatelite(originx,originy,satelite3);
    	
    	if(satelite3.y == 0 || satelite2.y == 0) {
    		if(satelite3.y == 0) {
				satelite2 = satelite3;
				satelite3 = satelite2;
				radius2 = radius3;
				radius3 = radius2;
    		}
		}else{
			madeRotation = true;
	    	if(satelite2.x == 0 && satelite3.x == 0) {
	    		angle = 90;
	    	}else {
		    	angle = Math.atan(satelite2.y/satelite2.x);
	    	}
	    	
	    	norma2 = this.norma(satelite2);
	    	norma3 = this.norma(satelite3);
	    	satelite2 = this.identifyNewX(satelite2,angle);
	    	satelite3 = this.identifyNewX(satelite3,angle);
	    	double y2 = Math.sin(angle) * norma2;
	    	double y3 = Math.sin(angle) * norma3;
	    	
	    	if(satelite2.y > 0) {
	    		yIsPositive = true;
	    		y2 *= -1;
	    		y3 *= -1;
	    	}
    		satelite2.y += y2;
    		satelite3.y += y3;
	    	System.out.println("Y = 0 es: "+satelite2.y );
		}
    	
        return identifyCoordinates(radius1, radius2, radius3, satelite2, satelite3, madeRotation, 
        	 yIsPositive, angle, originx, originy);
    }
	
	Satelite moveSatelite(double originx,double originy,Satelite satelite){
		satelite.x += originx;
    	satelite.y += originy;
		return satelite;
	}
	
	Satelite identifyNewX(Satelite satelite, double angle) {
		double norma = this.norma(satelite);
		satelite.x = Math.cos(angle) * norma;
		return satelite;
	}
	
	double norma(Satelite satelite) {
		return Math.sqrt(Math.pow(satelite.x, 2)+Math.pow(satelite.y, 2));
	}
	
	Satelite identifyCoordinates(double radius1, double radius2, double radius3,
			Satelite satelite2,Satelite satelite3, boolean madeRotation, boolean yIsPositive, double angle,
			double originx, double originy) {
		
		Satelite spaceship = new Satelite();
		double ySpaceship = 0;
		spaceship.x = (Math.pow(radius1, 2) - Math.pow(radius2, 2) + Math.pow(satelite2.x, 2)) /
    			(2 * satelite2.x);
		spaceship.y = ((Math.pow(radius1, 2) - Math.pow(radius3, 2) + Math.pow(satelite3.x, 2)
    		+ Math.pow(satelite3.y, 2)) / 
			(2*satelite3.y)) - ((satelite3.x/satelite3.y)*spaceship.x);
    	
    	if(madeRotation) {
    		ySpaceship = Math.cos(angle) * radius1;
    		ySpaceship = Math.sin(angle) * radius1;
    		if(yIsPositive) {
    			ySpaceship *= -1;	
    		}
    		spaceship.y += ySpaceship;
    	}
    	originx *= -1;
    	originy *= -1;
    	
    	spaceship.x += originx;
    	spaceship.y += originy;
		return spaceship;
	}



}

