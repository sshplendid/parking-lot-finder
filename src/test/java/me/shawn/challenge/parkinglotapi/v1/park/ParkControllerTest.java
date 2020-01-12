package me.shawn.challenge.parkinglotapi.v1.park;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class ParkControllerTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    private RestDocumentationResultHandler document;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        this.document = document(
                "{class-name}/{method-name}",
                preprocessResponse(prettyPrint())
        );
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
                .apply(documentationConfiguration(restDocumentation)
                    .uris().withHost("sshplendid.github.io").withScheme("https").withPort(443))
                .alwaysDo(document)
                .build();
    }

    @Test
    void getAllParkingLotsByAddress() throws Exception {
        // GIVEN
        String address = "마포";


        // WHEN and THEN
        mockMvc.perform(RestDocumentationRequestBuilders.get("/parks/{address}", address)
                    .accept(MediaType.APPLICATION_JSON)
                    .param("rowStartAt", "1")
                    .param("rowEndAt", "30")
                    .param("tel", "02-300-5052")
                    .param("parkingName", "공영주차장")
                )
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.status").isString())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.size").isNumber())
                .andDo(print())
                .andDo(document.document(
                        pathParameters(
                          parameterWithName("address").description("주소 (e.g. '마포', '망원')")
                        ),
                        requestParameters(
                                parameterWithName("rowStartAt").description("검색 인덱스 시작위치 (default: 1)").optional(),
                                parameterWithName("rowEndAt").description("검색 인덱스 종료 위치 (default: 30)").optional(),
                                parameterWithName("tel").description("주차장 전화번호").optional(),
                                parameterWithName("parkingName").description("주차장 이름").optional()
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태코드"),
                                fieldWithPath("message").description("응답 메세지"),
                                fieldWithPath("size").description("데이터의 사이즈"),
                                fieldWithPath("data[].parkingName").description("주차장명"),
                                fieldWithPath("data[].addr").description("주소"),
                                fieldWithPath("data[].parkingFeePerHour").description("시간당 주차 요금"),
                                fieldWithPath("data[].parkingCode").description("주차장코드"),
                                fieldWithPath("data[].parkingType").description("주차장 종류"),
                                fieldWithPath("data[].parkingTypeNm").description("주차장 종류명"),
                                fieldWithPath("data[].operationRule").description("운영구분"),
                                fieldWithPath("data[].operationRuleNm").description("운영구분명"),
                                fieldWithPath("data[].tel").description("전화번호"),
                                fieldWithPath("data[].queStatus").description("주차현황 정보 제공여부"),
                                fieldWithPath("data[].queStatusNm").description("주차현황 정보 제공여부명"),
                                fieldWithPath("data[].capacity").description("주차 면(주차 가능 차량 수)"),
                                fieldWithPath("data[].curParking").description("현재 주차중인 대수"),
                                fieldWithPath("data[].curParkingTime").description("현재 주차 차량 업데이트 시간"),
                                fieldWithPath("data[].payYn").description("유무료구분"),
                                fieldWithPath("data[].payNm").description("유무료구분명"),
                                fieldWithPath("data[].nightFreeOpen").description("야간무료개방여부"),
                                fieldWithPath("data[].nightFreeOpenNm").description("야간무료개방여부명"),
                                fieldWithPath("data[].weekdayBeginTime").description("평일 운영 시작시각(HHMM)"),
                                fieldWithPath("data[].weekdayEndTime").description("평일 운영 종료시각(HHMM)"),
                                fieldWithPath("data[].weekendBeginTime").description("주말 운영 시작시각(HHMM)"),
                                fieldWithPath("data[].weekendEndTime").description("주말 운영 종료시각(HHMM)"),
                                fieldWithPath("data[].holidayBeginTime").description("공휴일 운영 시작시각(HHMM)"),
                                fieldWithPath("data[].holidayEndTime").description("공휴일 운영 종료시각(HHMM)"),
                                fieldWithPath("data[].syncTime").description("최종데이터 동기화 시간"),
                                fieldWithPath("data[].saturdayPayYn").description("토요일 유,무료 구분"),
                                fieldWithPath("data[].saturdayPayNm").description("토요일 유,무료 구분명"),
                                fieldWithPath("data[].holidayPayYn").description("공휴일 유,무료 구분"),
                                fieldWithPath("data[].holidayPayNm").description("공휴일 유,무료 구분명"),
                                fieldWithPath("data[].fulltimeMonthly").description("월 정기권 금액"),
                                fieldWithPath("data[].grpParknm").description("노상 주차장 관리그룹번호"),
                                fieldWithPath("data[].rates").description("기본 주차 요금"),
                                fieldWithPath("data[].timeRate").description("기본 주차 시간(분 단위)"),
                                fieldWithPath("data[].addRates").description("추가 단위 요금"),
                                fieldWithPath("data[].addTimeRate").description("추가 단위 시간(분 단위)"),
                                fieldWithPath("data[].busRates").description("버스 기본 주차 요금"),
                                fieldWithPath("data[].busTimeRate").description("버스 기본 주차 시간(분 단위)"),
                                fieldWithPath("data[].busAddTimeRate").description("버스 추가 단위 시간(분 단위)"),
                                fieldWithPath("data[].busAddRates").description("버스 추가 단위 요금"),
                                fieldWithPath("data[].dayMaximum").description("일 최대 요금"),
                                fieldWithPath("data[].lat").description("주차장 위치 좌표 위도"),
                                fieldWithPath("data[].lng").description("주차장 위치 좌표 경도"),
                                fieldWithPath("data[].assignCode").description("배정코드"),
                                fieldWithPath("data[].assignCodeNm").description("배정코드명")
                        )
                ))
                .andExpect(status().isOk());
    }
}