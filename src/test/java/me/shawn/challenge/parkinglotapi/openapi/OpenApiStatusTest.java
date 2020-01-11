package me.shawn.challenge.parkinglotapi.openapi;

import me.shawn.challenge.parkinglotapi.openapi.model.OpenApiStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OpenApiStatusTest {

    @Test
    void resolve_UNKNOWN() {
        // GIVEN
        String code = "UNKNOWN";

        // WHEN
        OpenApiStatus openApiStatus = OpenApiStatus.resolve(code);

        // TEST
        assertThat(openApiStatus).isEqualTo(OpenApiStatus.UNKNOWN);
    }

    @Test
    void resolve_null() {
        // GIVEN
        String code = null;

        // WHEN
        OpenApiStatus openApiStatus = OpenApiStatus.resolve(code);

        // TEST
        assertThat(openApiStatus).isEqualTo(OpenApiStatus.UNKNOWN);
    }

    @Test
    void resolve_OK() {
        // GIVEN
        String code = "INFO-000";

        // WHEN
        OpenApiStatus openApiStatus = OpenApiStatus.resolve(code);

        // TEST
        assertThat(openApiStatus).isEqualTo(OpenApiStatus.OK);
    }
}