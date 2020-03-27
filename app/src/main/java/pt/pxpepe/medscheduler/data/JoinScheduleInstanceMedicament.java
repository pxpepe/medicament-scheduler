package pt.pxpepe.medscheduler.data;

import android.arch.persistence.room.TypeConverters;

import java.util.Date;

import pt.pxpepe.medscheduler.utilities.DateConverter;

public class JoinScheduleInstanceMedicament {

    private int scheduleId;

    private int instanceId;

    private int treatmentId;

    private int medicamentId;

    private String medicamentName;

    @TypeConverters(DateConverter.class)
    private Date instant;

    private Boolean took;

    private Boolean delay;

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(int instanceId) {
        this.instanceId = instanceId;
    }

    public int getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(int treatmentId) {
        this.treatmentId = treatmentId;
    }

    public int getMedicamentId() {
        return medicamentId;
    }

    public void setMedicamentId(int medicamentId) {
        this.medicamentId = medicamentId;
    }

    public String getMedicamentName() {
        return medicamentName;
    }

    public void setMedicamentName(String medicamentName) {
        this.medicamentName = medicamentName;
    }

    public Date getInstant() {
        return instant;
    }

    public void setInstant(Date instant) {
        this.instant = instant;
    }

    public Boolean getTook() {
        return took;
    }

    public void setTook(Boolean took) {
        this.took = took;
    }

    public Boolean getDelay() {
        return delay;
    }

    public void setDelay(Boolean delay) {
        this.delay = delay;
    }
}
