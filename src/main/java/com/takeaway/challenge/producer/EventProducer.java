package com.takeaway.challenge.producer;

public interface EventProducer {

    public void employeeCreatedLog(Integer id);

    public void employeeUpdatedLog(Integer id);

    public void employeeDeletedLog(Integer id);
}
