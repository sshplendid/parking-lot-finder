drop table if exists parking_lot;

create table parking_lot (
    SEQ INT not null auto_increment primary key,
    PARKING_NAME varchar(200),
    ADDR varchar(500),
    PARKING_CODE varchar(200),
    PARKING_TYPE varchar(200),
    PARKING_TYPE_NM varchar(200),
    OPERATION_RULE varchar(200),
    OPERATION_RULE_NM varchar(200),
    TEL varchar(200),
    QUE_STATUS varchar(200),
    QUE_STATUS_NM varchar(200),
    CAPACITY INT,
    CUR_PARKING INT,
    CUR_PARKING_TIME varchar(200),
    PAY_YN varchar(200),
    PAY_NM varchar(200),
    NIGHT_FREE_OPEN varchar(200),
    NIGHT_FREE_OPEN_NM varchar(200),
    WEEKDAY_BEGIN_TIME varchar(200),
    WEEKDAY_END_TIME varchar(200),
    WEEKEND_BEGIN_TIME varchar(200),
    WEEKEND_END_TIME varchar(200),
    HOLIDAY_BEGIN_TIME varchar(200),
    HOLIDAY_END_TIME varchar(200),
    SYNC_TIME varchar(200),
    SATURDAY_PAY_YN varchar(200),
    SATURDAY_PAY_NM varchar(200),
    HOLIDAY_PAY_YN varchar(200),
    HOLIDAY_PAY_NM varchar(200),
    FULLTIME_MONTHLY varchar(200),
    GRP_PARKNM varchar(200),
    RATES INT,
    TIME_RATE INT,
    ADD_RATES INT,
    ADD_TIME_RATE INT,
    BUS_RATES INT,
    BUS_TIME_RATE INT,
    BUS_ADD_TIME_RATE INT,
    BUS_ADD_RATES INT,
    DAY_MAXIMUM INT,
    LAT INT,
    LNG INT,
    ASSIGN_CODE varchar(200),
    ASSIGN_CODE_NM varchar(200)
);

