package edu.technical.task.cdrapi.controller;

import edu.technical.task.cdrapi.model.CallDetailRecord;
import edu.technical.task.cdrapi.service.CallDetailRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class CallDetailRecordController {

    private final CallDetailRecordService callDetailRecordService;

    @Autowired
    public CallDetailRecordController(CallDetailRecordService callDetailRecordService) {
        this.callDetailRecordService = callDetailRecordService;
    }

    // Get all CallDetailRecords
    @GetMapping("/api/cdrs")
    public Collection<CallDetailRecord> getAll() {
        return this.callDetailRecordService.getAll();
    }

    // Get all CallDetailRecords between given dates expressed in ISO 8601
    @GetMapping("/api/cdrs/{fromDate}/{toDate}")
    public Collection<CallDetailRecord> getAllBetweenDates(@PathVariable String fromDate, @PathVariable String toDate) {
        return this.callDetailRecordService.getAllBetweenDates(fromDate, toDate);
    }

    // Get average call cost between given dates expressed in ISO 8601
    @GetMapping("/api/cdrs/{fromDate}/{toDate}/average/callcost")
    public double getAverageCallCostBetweenDates(@PathVariable String fromDate, @PathVariable String toDate) {
        return this.callDetailRecordService.getAverageCallCostBetweenDates(fromDate, toDate);
    }

    // Get average call duration between given dates expressed in ISO 8601
    @GetMapping("/api/cdrs/{fromDate}/{toDate}/average/callduration")
    public double getAverageCallDurationBetweenDates(@PathVariable String fromDate, @PathVariable String toDate) {
        return this.callDetailRecordService.getAverageCallDurationBetweenDates(fromDate, toDate);
    }

    // Get calls ordered by duration between given dates expressed in ISO 8601
    @GetMapping("/api/cdrs/{fromDate}/{toDate}/longestcalls")
    public Collection<CallDetailRecord> getCallsBetweenDatesOrderByCallDuration(@PathVariable String fromDate, @PathVariable String toDate) {
        return this.callDetailRecordService.getCallsBetweenDatesOrderByCallDuration(fromDate, toDate);
    }

}
