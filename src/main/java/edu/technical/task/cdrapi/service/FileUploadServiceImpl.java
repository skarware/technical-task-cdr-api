package edu.technical.task.cdrapi.service;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import edu.technical.task.cdrapi.exception.FileUploadException;
import edu.technical.task.cdrapi.model.CallDetailRecord;
import edu.technical.task.cdrapi.model.UserAccount;
import edu.technical.task.cdrapi.util.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.*;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    private final CallDetailRecordService callDetailRecordService;

    @Autowired
    public FileUploadServiceImpl(CallDetailRecordService callDetailRecordService) {
        this.callDetailRecordService = callDetailRecordService;
    }

    @Override
    public void parseCSVFileAndSaveDataIntoDB(MultipartFile file) {

        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        try {
            if (file.isEmpty()) {
                throw new FileUploadException("Failed to parse ad save empty file: " + filename);
            }

            // Get CSV lines List from a MultipartFile
            List<String[]> csvStringsList = readCSVMultipartFileIntoStringsList(file);

            // Create CallDetailRecords List from List of CSV Strings
            List<CallDetailRecord> callDetailRecords = convertCSVListToCDRList(csvStringsList);

            // Save CallDetailRecords into Database
            saveCDRListIntoDatabase(callDetailRecords);

        } catch (IOException | ParseException | CsvException e) {
            throw new FileUploadException("Failed to parse and save file: " + filename, e);
        }
    }

    /**
     * Read CSV string lines List from a MultipartFile
     */
    private static List<String[]> readCSVMultipartFileIntoStringsList(MultipartFile file) throws IOException, ParseException, CsvException {
        // Get InputStream from MultipartFile
        try (InputStream inputStream = file.getInputStream()) {
            // Make BufferedReader from InputStream
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                // Read all CSV lines into List of Strings and return
                return readAllIntoList(bufferedReader);
            }
        }
    }

    public static List<String[]> readAllIntoList(BufferedReader bufferedReader) throws IOException, CsvException {
        // Create CSVParser with parsing rules
        CSVParser parser = new CSVParserBuilder()
                .withSeparator(',')
                .withIgnoreQuotations(true)
                .build();

        // Create CSVReader from BufferedReader, set to skip the column heading and set parsing rules from CSVParser instance
        CSVReader csvReader = new CSVReaderBuilder(bufferedReader)
                .withSkipLines(1)
                .withCSVParser(parser)
                .build();

        List<String[]> csvStringsList;
        csvStringsList = csvReader.readAll();

        // Close the Reader kind to prevent memory leaks
        bufferedReader.close();
        csvReader.close();
        return csvStringsList;
    }

    /**
     * Create CallDetailRecords List from List of CSV Strings
     */
    private static List<CallDetailRecord> convertCSVListToCDRList(List<String[]> csvStringsList) throws ParseException {
        // Empty ArrayList to be filled with CallDetailRecords
        List<CallDetailRecord> callDetailRecordList = new ArrayList<>();

        // Define CallDetailRecord obj outside for block to avoid new instantiation on every iteration
        CallDetailRecord callDetailRecord = null;

        // Iterate though CSV string list, and:
        for (String[] csvLine : csvStringsList) {
            // Create CallDetailRecord obj from CSV line
            callDetailRecord = csvLineToCDRBean(csvLine);

            // Add CallDetailRecord into ArrayList to be returned
            callDetailRecordList.add(callDetailRecord);
        }

        // Return either empty or filled ArrayList of CallDetailRecords
        return callDetailRecordList;
    }

    /**
     * Create CallDetailRecord object from CSV line
     */
    private static CallDetailRecord csvLineToCDRBean(String[] csvLine) throws ParseException {
        UUID uuid = UUID.fromString(csvLine[0]);
        UserAccount account = new UserAccount(csvLine[1]);
        UserAccount destination = new UserAccount(csvLine[2]);
        /*
        STARTDATE and ENDDATE given in Unix Timestamp, in seconds, while new Date() expecting number of milliseconds
         since the standard base time known as "the epoch", hence * 1000 as a second has a thousand milliseconds.
         */
        Date startDate = new Date(Long.parseLong(csvLine[3]) * 1000);
        Date endDate = new Date(Long.parseLong(csvLine[4]) * 1000);
        boolean status = csvLine[5].equalsIgnoreCase("success");
        double costPerMinute = Double.parseDouble(NumberUtils.toDotDecimalSeparator(csvLine[6]));

        // Create and return new CallDetailRecord obj with values parsed from CSV file line
        return new CallDetailRecord(uuid, account, destination, startDate, endDate, status, costPerMinute);
    }

    /**
     * Save CallDetailRecords List into Database
     */
    private void saveCDRListIntoDatabase(List<CallDetailRecord> callDetailRecords) {
        // Iterate through CallDetailRecords List and save all into Database
        for (CallDetailRecord callDetailRecord : callDetailRecords) {
            callDetailRecordService.save(callDetailRecord);
        }
    }
}
