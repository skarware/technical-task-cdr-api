package edu.technical.task.cdrapi.repository;

import edu.technical.task.cdrapi.model.CallDetailRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CallDetailRecordRepository extends JpaRepository<CallDetailRecord, UUID> {
}
