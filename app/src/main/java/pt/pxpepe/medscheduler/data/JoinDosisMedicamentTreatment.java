package pt.pxpepe.medscheduler.data;

public class JoinDosisMedicamentTreatment {

    private int dosisId;

    private int medicamentId;

    private String medicamentName;

    private int treatmentId;

    private String treatmentName;

    private int duration;

    private int period;

    private int pillNumber;

    private int delayNumber;

    public int getDosisId() {
        return dosisId;
    }

    public void setDosisId(int dosisId) {
        this.dosisId = dosisId;
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

    public int getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(int treatmentId) {
        this.treatmentId = treatmentId;
    }

    public String getTreatmentName() {
        return treatmentName;
    }

    public void setTreatmentName(String treatmentName) {
        this.treatmentName = treatmentName;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
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
