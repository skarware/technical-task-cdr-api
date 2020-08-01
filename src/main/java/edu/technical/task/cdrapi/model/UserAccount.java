package edu.technical.task.cdrapi.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "user_accounts")
public class UserAccount {

    @Id
    @Column(name = "phone_number")
    private String phoneNumber;

    public UserAccount(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public UserAccount() {
    }
}
