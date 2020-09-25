package com.javatechiue.aws;

import com.javatechiue.aws.repository.SateliteRepository;
import com.spaceship.aws.entity.SateliteEntity;
import com.spaceship.satelite.Satelite;
import com.spaceship.satelite.Spaceship;
import com.spaceship.satelite.SpaceshipInterface;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@EnableSwagger2
@SpringBootApplication
@RestController
public class SpringbootDynamodbExampleApplication {

    @Autowired
    private SateliteRepository repository;
    
    @RequestMapping("topsecret")
    @GetMapping(consumes = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE}
    ,produces = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})	
    ResponseEntity<Object> topsecret(@RequestBody String spaceship) {

    	try {
	        // convert to json object
	        JSONObject json = new JSONObject(spaceship);
	        
	        JSONArray list = json.getJSONArray("satelites"); 
	        
	    	SpaceshipInterface spaceshipMethod = new Spaceship();
	    	
	    	StringBuilder message = spaceshipMethod.getMessage(list);
	    	
	    	Satelite satelite1 = new Satelite(-500,-200);
	    	Satelite satelite2 = new Satelite(100,-100);
	    	Satelite satelite3 = new Satelite(500,100);
	    	Satelite spaceShip = new Satelite();
	    	
	    	json = list.getJSONObject(0);
	    	
	    	double radius1 = json.getDouble("distance");
	    	json = list.getJSONObject(1);
	    	double radius2 = json.getDouble("distance");
	    	json = list.getJSONObject(2);
	    	double radius3 = json.getDouble("distance");
	    	
	    	spaceShip = spaceshipMethod.location(satelite1,radius1,satelite2,
	        		radius2,satelite3,radius3);
	    	HashMap<String, Object> map = new HashMap<>();
	    	HashMap<String, Double> map2 = new HashMap<>();
	    	map2.put("x", spaceShip.getX());
	    	map2.put("y", spaceShip.getY());
	    	map.put("position", map2);
	    	map.put("mensaje", message);
	    	
	    	return new ResponseEntity<Object>(map, HttpStatus.OK);
    	}catch(Exception e) {
			return new ResponseEntity<Object>("No se ha podido obtener el mensaje o la ubicación de la nave", HttpStatus.NOT_FOUND);
    	}
 
    }  

    @PostMapping("/topsecret_split/{name}")
    public SateliteEntity topsecret_split(@PathVariable String name,@RequestBody String satelite) {
    	SateliteEntity sateliteEntity = new SateliteEntity();
    	JSONObject json = new JSONObject(satelite);
    	sateliteEntity.setName(name);
    	sateliteEntity.setDistance(json.getDouble("distance"));
    	 JSONArray list = json.getJSONArray("message"); 
    	sateliteEntity.setMessage(list.toString());
        return repository.addSatelite(sateliteEntity);
    }
    
    @GetMapping("/topsecret_split")
    public ResponseEntity<Object> get_topsecret_split() {
    	try {
    		ArrayList<String> list1 = new ArrayList<>();
    		ArrayList<String> list2 = new ArrayList<>();
    		ArrayList<String> list3 = new ArrayList<>();
    		Satelite satelite1 = new Satelite(-500,-200);
	    	Satelite satelite2 = new Satelite(100,-100);
	    	Satelite satelite3 = new Satelite(500,100);
	    	Satelite spaceShip = new Satelite();
	    	SpaceshipInterface spaceshipMethod = new Spaceship();

	    	JSONObject json = new JSONObject(repository.findPersonByName("kenobi"));
	    	double radius1 = json.getDouble("distance");
	    	JSONArray messages = new JSONArray(json.getString("message"));
	    	list1 = spaceshipMethod.messageListToArrayList(messages);
	    	json = new JSONObject(repository.findPersonByName("skywalker"));
	    	double radius2 = json.getDouble("distance");
	    	messages = new JSONArray(json.getString("message"));
	    	list2 = spaceshipMethod.messageListToArrayList(messages);
	    	json = new JSONObject(repository.findPersonByName("sato"));
	    	double radius3 = json.getDouble("distance");
	    	messages = new JSONArray(json.getString("message"));
	    	list3 = spaceshipMethod.messageListToArrayList(messages);
        	StringBuilder message = spaceshipMethod.message(list1, list2, list3);
        	spaceShip = spaceshipMethod.location(satelite1,radius1,satelite2,
	        		radius2,satelite3,radius3);
	    	HashMap<String, Object> map = new HashMap<>();
	    	HashMap<String, Double> map2 = new HashMap<>();
	    	map2.put("x", spaceShip.getX());
	    	map2.put("y", spaceShip.getY());
	    	map.put("position", map2);
	    	map.put("mensaje", message);
	    	return new ResponseEntity<Object>(map, HttpStatus.OK);
    	}catch(Exception e) {
    		System.out.println(e.getMessage());
			return new ResponseEntity<Object>("No hay suficiente información", HttpStatus.NOT_FOUND);
    	}
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringbootDynamodbExampleApplication.class, args);
    }

}
