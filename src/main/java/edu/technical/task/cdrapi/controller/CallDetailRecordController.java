package edu.technical.task.cdrapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

    private final ObjectMapper mapper;

    @Autowired
    public CallDetailRecordController(CallDetailRecordService callDetailRecordService, ObjectMapper mapper) {
        this.callDetailRecordService = callDetailRecordService;
        this.mapper = mapper;
    }

    // Get all CallDetailRecords
    @GetMapping("/api/cdrs")
    public ObjectNode getAll() {

        Collection<CallDetailRecord> callDetailRecords = this.callDetailRecordService.getAll();

        // Create and return JSON on the fly
        ObjectNode methodObjectNode = mapper.createObjectNode();
        ObjectNode innerObjectNode = mapper.createObjectNode();
        innerObjectNode.putPOJO("sortedDesc", callDetailRecords);
        methodObjectNode.set("CallDetailRecords", innerObjectNode);

        return methodObjectNode;
    }

    // Get all CallDetailRecords between given dates expressed in ISO 8601
    @GetMapping("/api/cdrs/{fromDate}/{toDate}")
    public ObjectNode getAllBetweenDates(@PathVariable String fromDate, @PathVariable String toDate) {

        Collection<CallDetailRecord> callDetailRecords = this.callDetailRecordService.getAllBetweenDates(fromDate, toDate);

        // Create and return JSON on the fly
        ObjectNode methodObjectNode = mapper.createObjectNode();
        ObjectNode innerObjectNode = mapper.createObjectNode();
        innerObjectNode.put("from", fromDate);
        innerObjectNode.put("to", toDate);
        innerObjectNode.putPOJO("sortedDesc", callDetailRecords);
        methodObjectNode.set("CallDetailRecords", innerObjectNode);

        return methodObjectNode;
    }

    // Get average calls information between given dates expressed in ISO 8601
    @GetMapping("/api/cdrs/{fromDate}/{toDate}/average")
    public ObjectNode getAverageCallInfoBetweenDates(@PathVariable String fromDate, @PathVariable String toDate) {

        // Get average call cost for given period
        double averageCallCost = this.callDetailRecordService.getAverageCallCostBetweenDates(fromDate, toDate);

        // Get average call duration for given period
        double averageCallDuration = this.callDetailRecordService.getAverageCallDurationBetweenDates(fromDate, toDate);

        // Create and return JSON on the fly
        ObjectNode methodObjectNode = mapper.createObjectNode();
        ObjectNode innerObjectNode = mapper.createObjectNode();
        innerObjectNode.put("from", fromDate);
        innerObjectNode.put("to", toDate);
        innerObjectNode.put("callCost", averageCallCost);
        innerObjectNode.put("callDuration", averageCallDuration);
        methodObjectNode.set("average", innerObjectNode);

        return methodObjectNode;
    }

    // Get average call cost between given dates expressed in ISO 8601
    @GetMapping("/api/cdrs/{fromDate}/{toDate}/average/callcost")
    public ObjectNode getAverageCallCostBetweenDates(@PathVariable String fromDate, @PathVariable String toDate) {

        double averageCallCost = this.callDetailRecordService.getAverageCallCostBetweenDates(fromDate, toDate);

        // Create and return JSON on the fly
        ObjectNode methodObjectNode = mapper.createObjectNode();
        ObjectNode innerObjectNode = mapper.createObjectNode();
        innerObjectNode.put("from", fromDate);
        innerObjectNode.put("to", toDate);
        innerObjectNode.put("callCost", averageCallCost);
        methodObjectNode.set("average", innerObjectNode);

        return methodObjectNode;
    }

    // Get average call duration between given dates expressed in ISO 8601
    @GetMapping("/api/cdrs/{fromDate}/{toDate}/average/callduration")
    public ObjectNode getAverageCallDurationBetweenDates(@PathVariable String fromDate, @PathVariable String toDate) {

        double averageCallDuration = this.callDetailRecordService.getAverageCallDurationBetweenDates(fromDate, toDate);

        // Create and return JSON on the fly
        ObjectNode methodObjectNode = mapper.createObjectNode();
        ObjectNode innerObjectNode = mapper.createObjectNode();
        innerObjectNode.put("from", fromDate);
        innerObjectNode.put("to", toDate);
        innerObjectNode.put("callDuration", averageCallDuration);
        methodObjectNode.set("average", innerObjectNode);

        return methodObjectNode;
    }

    // Get calls ordered by duration between given dates expressed in ISO 8601
    @GetMapping("/api/cdrs/{fromDate}/{toDate}/longestcalls")
    public ObjectNode getCallsBetweenDatesOrderByCallDuration(@PathVariable String fromDate, @PathVariable String toDate) {

        Collection<CallDetailRecord> callDetailRecords = this.callDetailRecordService.getCallsBetweenDatesOrderByCallDuration(fromDate, toDate);

        // Create and return JSON on the fly
        ObjectNode methodObjectNode = mapper.createObjectNode();
        ObjectNode innerObjectNode = mapper.createObjectNode();
        innerObjectNode.put("from", fromDate);
        innerObjectNode.put("to", toDate);
        innerObjectNode.putPOJO("sortedDesc", callDetailRecords);
        methodObjectNode.set("longestCalls", innerObjectNode);

        return methodObjectNode;
    }

}
