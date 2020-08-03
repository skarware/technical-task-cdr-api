package edu.technical.task.cdrapi.service;

import edu.technical.task.cdrapi.model.CallDetailRecord;
import edu.technical.task.cdrapi.repository.CallDetailRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class CallDetailRecordServiceImpl implements CallDetailRecordService {

    private final CallDetailRecordRepository callDetailRecordRepository;

    @Autowired
    public CallDetailRecordServiceImpl(CallDetailRecordRepository callDetailRecordRepository) {
        this.callDetailRecordRepository = callDetailRecordRepository;
    }

    @Override
    public CallDetailRecord save(CallDetailRecord callDetailRecord) {
        return callDetailRecordRepository.save(callDetailRecord);
    }

    @Override
    public Collection<CallDetailRecord> getAll() {
        return callDetailRecordRepository.getAllByOrderByStartDateDesc();
    }

    @Override
    public Collection<CallDetailRecord> getAllBetweenDates(String fromStr, String toStr) {
        return callDetailRecordRepository.getAllByStartDateBetweenOrderByStartDateDesc(Instant.parse(fromStr), Instant.parse(toStr));
    }

    @Override
    public double getAverageCallCostBetweenDates(String fromStr, String toStr) {
        // Get CDRs for given dates
        Collection<CallDetailRecord> callDetailRecords = getAllBetweenDates(fromStr, toStr);
        // Calc and return average getCostPerMinute
        return callDetailRecords
                .parallelStream()
                .mapToDouble(CallDetailRecord::getCostPerMinute)
                .average()
                .getAsDouble();
    }

    @Override
    public double getAverageCallDurationBetweenDates(String fromStr, String toStr) {
        // Get CDRs for given dates
        Collection<CallDetailRecord> callDetailRecords = getAllBetweenDates(fromStr, toStr);
        // Calc and return average call duration
        return callDetailRecords
                .parallelStream()
                .mapToDouble(el -> el.getEndDate().getEpochSecond() - el.getStartDate().getEpochSecond())
                .average()
                .getAsDouble();
    }

    @Override
    public Collection<CallDetailRecord> getCallsBetweenDatesOrderByCallDuration(String fromStr, String toStr) {
        // Get CDRs for given dates
        Collection<CallDetailRecord> callDetailRecords = getAllBetweenDates(fromStr, toStr);
        // Return ordered by call duration
        return callDetailRecords
                .parallelStream()
                .sorted((cdr1, cdr2) -> (int) ((cdr2.getEndDate().getEpochSecond()-cdr2.getStartDate().getEpochSecond()) - ((cdr1.getEndDate().getEpochSecond()-cdr1.getStartDate().getEpochSecond()))))
                .collect(Collectors.toUnmodifiableList());
    }

}
