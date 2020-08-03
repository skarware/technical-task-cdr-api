create table USER_ACCOUNTS
(
    PHONE_NUMBER VARCHAR(255) not null primary key
);

create table CALL_DETAIL_RECORDS
(
    ID              UUID                     not null primary key,
    COST_PER_MINUTE DOUBLE,
    END_DATE        TIMESTAMP WITH TIME ZONE not null,
    START_DATE      TIMESTAMP WITH TIME ZONE not null,
    STATUS          BOOLEAN,
    ACCOUNT         VARCHAR(255)             not null,
    DESTINATION     VARCHAR(255)             not null,
    constraint FKHM6UM5Y8NXYLF8T8U1E5XT6FF
        foreign key (DESTINATION) references USER_ACCOUNTS (PHONE_NUMBER),
    constraint FKK692W4696EFC829TLSR72LRQ9
        foreign key (ACCOUNT) references USER_ACCOUNTS (PHONE_NUMBER)
);
