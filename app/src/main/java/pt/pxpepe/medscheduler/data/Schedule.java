package pt.pxpepe.medscheduler.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.util.Date;

import pt.pxpepe.medscheduler.utilities.DateConverter;

@Entity(foreignKeys = {@ForeignKey(entity = Instance.class,
        parentColumns = "id",
        childColumns = "instanceId",
        onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = Medicament.class,
                parentColumns = "id",
                childColumns = "medicamentId",
                onDelete = ForeignKey.CASCADE)},
        indices = {@Index("instanceId"),@Index("medicamentId")})
public class Schedule {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int instanceId;

    private int medicamentId;

    @NonNull
    @TypeConverters(DateConverter.class)
    private Date instant;

    private Boolean took;
    private Boolean delay;

    public Schedule(int instanceId, int medicamentId, @NonNull Date instant) {
        this.instanceId = instanceId;
        this.medicamentId = medicamentId;
        this.instant = instant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(int instanceId) {
        this.instanceId = instanceId;
    }

    @NonNull
    public Date getInstant() {
        return instant;
    }

    public void setInstant(@NonNull Date instant) {
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

    public int getMedicamentId() {
        return medicamentId;
    }

    public void setMedicamentId(int medicamentId) {
        this.medicamentId = medicamentId;
    }
}
