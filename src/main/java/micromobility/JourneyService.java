package micromobility;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class JourneyService {
    private LocalDateTime initDate;
    private LocalDateTime endDate;
    private float distance;
    private int duration; // en minutos
    private float avgSpeed;
    private BigDecimal importValue; // Importe del trayecto
    private boolean inProgress;

    public JourneyService(LocalDateTime initDate) {
        this.initDate = (initDate != null) ? initDate : LocalDateTime.now();
        this.inProgress = true;
    }

    public LocalDateTime getInitDate() {
        return initDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public float getDistance() {
        return distance;
    }

    public int getDuration() {
        return duration;
    }

    public float getAvgSpeed() {
        return avgSpeed;
    }

    public BigDecimal getImportValue() {
        return importValue;
    }

    public boolean isInProgress() {
        return inProgress;
    }

    public void setServiceInit(LocalDateTime initDate) {
        if (initDate == null) {
            throw new IllegalArgumentException("Initial date cannot be null.");
        }
        this.initDate = initDate;
        this.inProgress = true;
    }

    public void setServiceFinish(LocalDateTime endDate, float distance, int duration, float avgSpeed, BigDecimal importValue) {
        if (endDate == null) {
            throw new IllegalArgumentException("End date cannot be null.");
        }
        if (duration <= 0) {
            throw new IllegalArgumentException("Duration must be greater than 0.");
        }
        if (distance < 0) {
            throw new IllegalArgumentException("Distance cannot be negative.");
        }
        if (avgSpeed < 0) {
            throw new IllegalArgumentException("Average speed cannot be negative.");
        }
        if (importValue == null || importValue.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Import value must be non-negative.");
        }
        this.endDate = endDate;
        this.distance = distance;
        this.duration = duration;
        this.avgSpeed = avgSpeed;
        this.importValue = importValue;
        this.inProgress = false;
    }


    @Override
    public String toString() {
        return "JourneyService{" +
                "initDate=" + initDate +
                ", endDate=" + endDate +
                ", distance=" + distance +
                ", duration=" + duration +
                ", avgSpeed=" + avgSpeed +
                ", importValue=" + importValue +
                ", inProgress=" + inProgress +
                '}';
    }
}
