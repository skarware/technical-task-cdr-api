package edu.technical.task.cdrapi.service;

import edu.technical.task.cdrapi.model.CallDetailRecord;

import java.util.Collection;

public interface CallDetailRecordService {

    CallDetailRecord save(CallDetailRecord callDetailRecord);

    Collection<CallDetailRecord> getAll();

    Collection<CallDetailRecord> getAllBetweenDates(String fromStr, String toStr);

    double getAverageCallCostBetweenDates(String fromStr, String toStr);

    double getAverageCallDurationBetweenDates(String fromStr, String toStr);

    Collection<CallDetailRecord> getCallsBetweenDatesOrderByCallDuration(String fromStr, String toStr);

}
