package micromobility;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class JourneyServiceTest {

    @Test
    void getInitDate() {
        LocalDateTime now = LocalDateTime.now();
        JourneyService journey = new JourneyService(now);
        assertEquals(now, journey.getInitDate());
    }

    @Test
    void getEndDate() {
        LocalDateTime now = LocalDateTime.now();
        JourneyService journey = new JourneyService(now);
        LocalDateTime endDate = now.plusHours(1);
        journey.setServiceFinish(endDate, 10.5f, 60, 10.5f, BigDecimal.valueOf(20));
        assertEquals(endDate, journey.getEndDate());
    }

    @Test
    void getDistance() {
        JourneyService journey = new JourneyService(LocalDateTime.now());
        journey.setServiceFinish(LocalDateTime.now(), 15.5f, 60, 15.5f, BigDecimal.valueOf(30));
        assertEquals(15.5f, journey.getDistance());
    }

    @Test
    void getDuration() {
        JourneyService journey = new JourneyService(LocalDateTime.now());
        journey.setServiceFinish(LocalDateTime.now(), 20.0f, 120, 10.0f, BigDecimal.valueOf(40));
        assertEquals(120, journey.getDuration());
    }

    @Test
    void getAvgSpeed() {
        JourneyService journey = new JourneyService(LocalDateTime.now());
        journey.setServiceFinish(LocalDateTime.now(), 50.0f, 120, 25.0f, BigDecimal.valueOf(100));
        assertEquals(25.0f, journey.getAvgSpeed());
    }

    @Test
    void getImportValue() {
        JourneyService journey = new JourneyService(LocalDateTime.now());
        journey.setServiceFinish(LocalDateTime.now(), 30.0f, 90, 20.0f, BigDecimal.valueOf(60));
        assertEquals(BigDecimal.valueOf(60), journey.getImportValue());
    }

    @Test
    void isInProgress() {
        JourneyService journey = new JourneyService(LocalDateTime.now());
        assertTrue(journey.isInProgress());
        journey.setServiceFinish(LocalDateTime.now(), 10.0f, 30, 20.0f, BigDecimal.valueOf(50));
        assertFalse(journey.isInProgress());
    }

    @Test
    void setServiceInit() {
        LocalDateTime newInitDate = LocalDateTime.now();
        JourneyService journey = new JourneyService(null);
        journey.setServiceInit(newInitDate);
        assertEquals(newInitDate, journey.getInitDate());
        assertTrue(journey.isInProgress());
    }

    @Test
    void setServiceFinish() {
        JourneyService journey = new JourneyService(LocalDateTime.now());
        LocalDateTime endDate = LocalDateTime.now().plusMinutes(45);
        journey.setServiceFinish(endDate, 15.0f, 45, 20.0f, BigDecimal.valueOf(50));

        assertEquals(endDate, journey.getEndDate());
        assertEquals(15.0f, journey.getDistance());
        assertEquals(45, journey.getDuration());
        assertEquals(20.0f, journey.getAvgSpeed());
        assertEquals(BigDecimal.valueOf(50), journey.getImportValue());
        assertFalse(journey.isInProgress());
    }

    @Test
    void setServiceFinishFailsWithInvalidDuration() {
        JourneyService journey = new JourneyService(LocalDateTime.now());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                journey.setServiceFinish(LocalDateTime.now(), 10.0f, 0, 20.0f, BigDecimal.valueOf(50))
        );
        assertEquals("Duration must be greater than 0.", exception.getMessage());
    }

    @Test
    void setServiceFinishFailsWithNullEndDate() {
        JourneyService journey = new JourneyService(LocalDateTime.now());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                journey.setServiceFinish(null, 10.0f, 30, 20.0f, BigDecimal.valueOf(50))
        );
        assertEquals("End date cannot be null.", exception.getMessage());
    }

    @Test
    void testToString() {
        JourneyService journey = new JourneyService(LocalDateTime.now());
        journey.setServiceFinish(LocalDateTime.now(), 10.0f, 30, 20.0f, BigDecimal.valueOf(50));

        String expected = "JourneyService{" +
                "initDate=" + journey.getInitDate() +
                ", endDate=" + journey.getEndDate() +
                ", distance=10.0" +
                ", duration=30" +
                ", avgSpeed=20.0" +
                ", importValue=50" +
                ", inProgress=false" +
                '}';
        assertEquals(expected, journey.toString());
    }
}
