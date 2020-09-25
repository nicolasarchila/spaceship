package com.javatechiue.aws.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.spaceship.aws.entity.SateliteEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class SateliteRepository {

    @Autowired
    private DynamoDBMapper mapper;
    
    public SateliteEntity addSatelite(SateliteEntity satelite) {
        mapper.save(satelite);
        return satelite;
    }

    public SateliteEntity findPersonByName(String personId) {
        return mapper.load(SateliteEntity.class, personId);
    }


}
