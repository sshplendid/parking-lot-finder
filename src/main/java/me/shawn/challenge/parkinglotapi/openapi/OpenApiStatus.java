package me.shawn.challenge.parkinglotapi.openapi;

public enum OpenApiStatus {
    OK("INFO-000", "정상 처리되었습니다"),
    TOO_MANY_REQUESTS("ERROR-336", "데이터요청은 한번에 최대 1000건을 넘을 수 없습니다. 요청종료위치에서 요청시작위치를 뺀 값이 1000을 넘지 않도록 수정하세요."),
    NO_DATA("INFO-200", "해당하는 데이터가 없습니다."),
    SERVER_ERROR("ERROR-500", "서버 오류입니다. 지속적으로 발생시 열린 데이터 광장으로 문의(Q&A) 바랍니다.");

    final String code;
    final String message;

    OpenApiStatus(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static OpenApiStatus resolve(String code) {
        for (OpenApiStatus message : values()) {
            if (message.code.equals(code)) {
                return message;
            }
        }
        throw new IllegalArgumentException("No matching constant for [" + code + "]");
    }
}