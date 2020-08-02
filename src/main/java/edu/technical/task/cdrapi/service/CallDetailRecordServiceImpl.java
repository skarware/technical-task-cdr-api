package edu.technical.task.cdrapi.service;

import edu.technical.task.cdrapi.model.CallDetailRecord;
import edu.technical.task.cdrapi.repository.CallDetailRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
