package pt.pxpepe.medscheduler.data;

import android.arch.persistence.room.TypeConverters;

import java.util.Date;

import pt.pxpepe.medscheduler.utilities.DateConverter;

public class JoinInstanceTreatment {

    private int instanceId;

    private int treatmentId;

    private String name;

    private int duration;

    @TypeConverters(DateConverter.class)
    private Date startDate;

    @TypeConverters(DateConverter.class)
    private Date endDate;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
