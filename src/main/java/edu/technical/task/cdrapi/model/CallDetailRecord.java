package edu.technical.task.cdrapi.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "call_detail_records")
public class CallDetailRecord {

    @Id
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "account", referencedColumnName = "phone_number", nullable = false)
    private UserAccount account;

    @ManyToOne
    @JoinColumn(name = "destination", referencedColumnName = "phone_number", nullable = false)
    private UserAccount destination;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_date", columnDefinition = "TIMESTAMP", nullable = false)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date", columnDefinition = "TIMESTAMP", nullable = false)
    private Date endDate;

    @Column(name = "status")
    private boolean status;

    @Column(name = "cost_per_minute")
    private double costPerMinute;

    public CallDetailRecord(UUID id, UserAccount account, UserAccount destination, Date startDate, Date endDate, boolean status, double costPerMinute) {
        this.id = id;
        this.account = account;
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.costPerMinute = costPerMinute;
    }

    public CallDetailRecord() {
    }
}
