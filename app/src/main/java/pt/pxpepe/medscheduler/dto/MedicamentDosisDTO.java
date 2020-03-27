package pt.pxpepe.medscheduler.dto;

public class MedicamentDosisDTO {

    private String name;

    private int period;

    private int pillNumber;

    private int delayNumber;

    public MedicamentDosisDTO(String name, int period, int pillNumber, int delayNumber) {
        this.name = name;
        this.period = period;
        this.pillNumber = pillNumber;
        this.delayNumber = delayNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
