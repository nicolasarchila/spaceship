package com.spaceship.satelite;

import java.util.ArrayList;
import org.json.JSONArray;

public interface SpaceshipInterface {
	
	StringBuilder message(ArrayList<String> list1,ArrayList<String> list2,ArrayList<String> list3);
	
	StringBuilder getMessage(JSONArray list);

	ArrayList<String> messageListToArrayList(JSONArray messages);
	
	ArrayList<String> messageStringToArrayList(String [] messages);
	
	Satelite location(Satelite satelite1,double radius1,Satelite satelite2,
    		double radius2,Satelite satelite3,double radius3);
	
}