package pt.pxpepe.medscheduler.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(entity = Medicament.class,
        parentColumns = "id",
        childColumns = "medicamentId"),
        @ForeignKey(entity = Treatment.class,
                parentColumns = "id",
                childColumns = "treatmentId",
                onDelete = ForeignKey.CASCADE)},
        indices = {@Index(value = {"medicamentId", "treatmentId"}, unique = true),
                @Index("medicamentId"),
                @Index("treatmentId")})
public class Dosis {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int medicamentId;

    private int treatmentId;

    private int period = 4; // (default 4h)

    private int pillNumber = 1; // Number of pills we should took

    private int delayNumber = 10; // delay time in minutes

    public Dosis(int medicamentId, int treatmentId, int period, int pillNumber, int delayNumber) {
        this.medicamentId = medicamentId;
        this.treatmentId = treatmentId;
        this.period = period;
        this.pillNumber = pillNumber;
        this.delayNumber = delayNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMedicamentId() {
        return medicamentId;
    }

    public void setMedicamentId(int medicamentId) {
        this.medicamentId = medicamentId;
    }

    public int getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(int treatmentId) {
        this.treatmentId = treatmentId;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public int getPillNumber() {
        return pillNumber;
    }

    public void setPillNumber(int pillNumber) {
        this.pillNumber = pillNumber;
    }

    public int getDelayNumber() {
        return delayNumber;
    }

    public void setDelayNumber(int delayNumber) {
        this.delayNumber = delayNumber;
    }
}
