package com.takeaway.challenge.producer.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.takeaway.challenge.dto.EmployeeEventDto;
import com.takeaway.challenge.producer.EventProducer;

@Service
public class EventProducerImpl implements EventProducer {

    private final Logger logger = LoggerFactory.getLogger(EventProducerImpl.class);

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    private static final String TOPIC = "EMPLOYEE_DATA";

    private static final String CREATED_ACTION = "CREATED";

    private static final String UPDATED_ACTION = "UPDATED";

    private static final String DELETED_ACTION = "DELETED";

    @Override
    public void employeeCreatedLog(Integer id) {
        logger.info(String.format("#### -> Produced message -> %s %d", CREATED_ACTION, id));
        this.kafkaTemplate.send(TOPIC, new EmployeeEventDto(id, CREATED_ACTION));
    }

    @Override
    public void employeeUpdatedLog(Integer id) {
        logger.info(String.format("#### -> Produced message -> %s %d", UPDATED_ACTION, id));
        this.kafkaTemplate.send(TOPIC, new EmployeeEventDto(id, UPDATED_ACTION));
    }

    @Override
    public void employeeDeletedLog(Integer id) {
        logger.info(String.format("#### -> Produced message -> %s %d", DELETED_ACTION, id));
        this.kafkaTemplate.send(TOPIC, new EmployeeEventDto(id, DELETED_ACTION));
    }
}
