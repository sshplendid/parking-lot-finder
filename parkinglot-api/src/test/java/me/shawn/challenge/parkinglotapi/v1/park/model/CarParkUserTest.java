package me.shawn.challenge.parkinglotapi.v1.park.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

class CarParkUserTest {
    private static final Logger log = LoggerFactory.getLogger(CarParkUserTest.class);
    private Validator validator;
    private CarParkUser user;

    @BeforeEach
    void setUp() {
        Configuration<?> configuration = Validation
                .byDefaultProvider()
                .configure();
        ValidatorFactory factory = configuration.buildValidatorFactory();
        this.validator = factory.getValidator();

    }

    @Test
    void 생성자테스트_NoArgs() {
        // WHEN
        user = new CarParkUser();

        // THEN
        assertThat(user.getAddress()).isEqualTo(" ");
        assertThat(user.getRequestedAt()).isBeforeOrEqualTo(LocalDateTime.now());
        assertThat(user.getPage()).isEqualTo(1);
        assertThat(user.getPageSize()).isEqualTo(5);
    }

    @Test
    void 생성자테스트_주소가정상일때_Validation에러가_발생하지않는다() {
        // GIVEN
        String givenAddress = "마포구";

        // WHEN
        user = new CarParkUser(givenAddress);

        // THEN
        assertThat(user.getAddress()).isEqualTo(givenAddress);
        assertThat(user.getRequestedAt()).isBeforeOrEqualTo(LocalDateTime.now());
        assertThat(user.getPage()).isEqualTo(1);
        assertThat(user.getPageSize()).isEqualTo(5);
    }

    @Test
    void 생성자테스트_주소가null값일때_Validation에러가_발생한다() {
        // GIVEN
        String givenAddress = null;

        // WHEN
        CarParkUser user = new CarParkUser(givenAddress);
        Set<ConstraintViolation<CarParkUser>> violations = validator.validate(user);
        violations.forEach(violation -> {
            log.info("\tViolation: {} - '{}'", violation.getMessage(), violation.getInvalidValue());
        });

        // THEN
        assertThat(violations).hasSize(1);
        user = null;
    }

    @Test
    void 생성자테스트_주소가빈값일때_Validation에러가_발생한다() {
        // GIVEN
        String givenAddress = "";

        // WHEN
        user = new CarParkUser(givenAddress);
        Set<ConstraintViolation<CarParkUser>> violations = validator.validate(user);
        violations.forEach(violation -> {
            log.info("\tViolation: {} - '{}'", violation.getMessage(), violation.getInvalidValue());
        });

        // THEN
        assertThat(violations).hasSize(1);
        user = null;
    }

    @Test
    void 생성자테스트_주소가공백값일때_Validation에러가_발생한다() {
        // GIVEN
        String givenAddress = "";

        // WHEN
        user = new CarParkUser(givenAddress);
        Set<ConstraintViolation<CarParkUser>> violations = validator.validate(user);
        violations.forEach(violation -> {
            log.info("\tViolation: {} - '{}'", violation.getMessage(), violation.getInvalidValue());
        });

        // THEN
        assertThat(violations).hasSize(1);
        user = null;
    }

    @Test
    void getTelephoneOnlyNumber_9자리테스트() {
        // GIVEN
        String givenNumber = "02-123-4567";
        String expectedNumber = "021234567";
        user = new CarParkUser(LocalDateTime.now(), "마포구", 1, 5, "공영주차장", givenNumber, 1d, 1d);

        // WHEN
        String actualTelephoneNumber = user.getTelephoneOnlyNumber();
        log.info("Actual Tel. : {}", actualTelephoneNumber);

        // THEN
        assertThat(actualTelephoneNumber).containsPattern(Pattern.compile("^\\d{9,}$"));
        assertThat(actualTelephoneNumber).isEqualTo(expectedNumber);
    }

    @Test
    void getTelephoneOnlyNumber_10자리테스트() {
        // GIVEN
        String givenNumber = "031-123-4567";
        String expectedNumber = "0311234567";
        user = new CarParkUser(LocalDateTime.now(), "마포구", 1, 5, "공영주차장", givenNumber, 1d, 1d);

        // WHEN
        String actualTelephoneNumber = user.getTelephoneOnlyNumber();
        log.info("Actual Tel. : {}", actualTelephoneNumber);

        // THEN
        assertThat(actualTelephoneNumber).containsPattern(Pattern.compile("^\\d{9,}$"));
        assertThat(actualTelephoneNumber).isEqualTo(expectedNumber);
    }

    @Test
    void getTelephoneOnlyNumber_11자리테스트() {
        // GIVEN
        String givenNumber = "010-1239-4567";
        String expectedNumber = "01012394567";
        user = new CarParkUser(LocalDateTime.now(), "마포구", 1, 5, "공영주차장", givenNumber, 1d, 1d);

        // WHEN
        String actualTelephoneNumber = user.getTelephoneOnlyNumber();
        log.info("Actual Tel. : {}", actualTelephoneNumber);

        // THEN
        assertThat(actualTelephoneNumber).containsPattern(Pattern.compile("^\\d{9,}$"));
        assertThat(actualTelephoneNumber).isEqualTo(expectedNumber);
    }

    @Test
    void isValidUser_입력값없음() {
        // GIVEN
        user = new CarParkUser();

        // WHEN
        boolean validation = user.isValidUser();

        // THEN
        assertThat(validation).isTrue();
        assertThat(user.getAddress()).isEqualTo(" ");
        assertThat(user.getRequestedAt()).isBeforeOrEqualTo(LocalDateTime.now());
        assertThat(user.getPage()).isEqualTo(1);
        assertThat(user.getPageSize()).isEqualTo(5);
    }

    @Test
    void latitude_범위를벗어난값이들어오면_밸리데이션에러가발생한다() {
        // GIVEN
        double wrongLatitude = -210;

        // WHEN
        user = new CarParkUser(LocalDateTime.now(), "마포구", 1, 5, "공영주차장", "123-345-1234", wrongLatitude, 1d);
        Set<ConstraintViolation<CarParkUser>> violations = validator.validate(user);
        violations.forEach(violation -> log.error("\tViolation: {} - '{}'", violation.getMessage(), violation.getInvalidValue()));

        // THEN
        assertThat(violations).hasSize(1);
        user = null;
    }

    @Test
    void logitude_범위를벗어난값이들어오면_밸리데이션에러가발생한다() {
        // GIVEN
        double wrongLongitude = -210;

        // WHEN
        user = new CarParkUser(LocalDateTime.now(), "마포구", 1, 5, "공영주차장", "123-345-1234", 38.12345, wrongLongitude);
        Set<ConstraintViolation<CarParkUser>> violations = validator.validate(user);
        violations.forEach(violation -> log.error("\tViolation: {} - '{}'", violation.getMessage(), violation.getInvalidValue()));

        // THEN
        assertThat(violations).hasSize(1);
        user = null;
    }


    @AfterEach
    void tearDown_user가null이아니면_밸리데이션을수행한다() {
        if(user == null) return;

        Set<ConstraintViolation<CarParkUser>> violations = validator.validate(user);
        violations.forEach(violation -> {
            log.info("\tViolation: {} - '{}'", violation.getMessage(), violation.getInvalidValue());
        });
        assertThat(violations).hasSize(0);
    }

    @Test
    void regexTest() {
        // GIVEN
        String telephone = "031-200-1234";

        // WHEN
        boolean result = Pattern.matches("((\\d+)\\-?)+", telephone);

        // THEN
        assertThat(result).isTrue();
    }
}