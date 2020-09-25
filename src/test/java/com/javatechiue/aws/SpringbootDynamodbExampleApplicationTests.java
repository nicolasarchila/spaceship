package com.javatechiue.aws;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.javatechiue.aws.repository.SateliteRepository;
import com.spaceship.aws.entity.SateliteEntity;

@SpringBootTest
class SpringbootDynamodbExampleApplicationTests {
	
	@Mock
	private SateliteRepository sateliteRepository;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		SateliteEntity sateliteEntity = new SateliteEntity();
		sateliteEntity.setName("test");
    	sateliteEntity.setDistance(100.0);
    	sateliteEntity.setMessage("messageTest");
		
		Mockito.when(sateliteRepository.findPersonByName("test"))
		.thenReturn(sateliteEntity);
	}

	@Test
	public void contextLoads() {
		Assertions.assertThat(sateliteRepository.findPersonByName("test").getMessage().equals("messageTest"));
	}

}
