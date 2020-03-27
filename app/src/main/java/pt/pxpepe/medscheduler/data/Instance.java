package pt.pxpepe.medscheduler.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.util.Date;

import pt.pxpepe.medscheduler.utilities.DateConverter;

@Entity(foreignKeys = @ForeignKey(entity = Treatment.class,
                parentColumns = "id",
                childColumns = "treatmentId",
        onDelete = ForeignKey.CASCADE),
        indices = @Index(value = "treatmentId", unique=true))
public class Instance {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int treatmentId;

    @NonNull
    @TypeConverters(DateConverter.class)
    private Date startDate;

    @TypeConverters(DateConverter.class)
    private Date endDate; // can be null, if its forever

    public Instance(int treatmentId, @NonNull Date startDate, Date endDate) {
        this.treatmentId = treatmentId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(int treatmentId) {
        this.treatmentId = treatmentId;
    }

    @NonNull
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(@NonNull Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }



}
