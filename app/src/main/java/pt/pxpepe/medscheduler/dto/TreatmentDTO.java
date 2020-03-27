package pt.pxpepe.medscheduler.dto;

import java.util.List;

public class TreatmentDTO {

    private String name;

    private int duration;

    List<MedicamentDosisDTO> medicamentDosisList;

    public TreatmentDTO(String name, int duration, List<MedicamentDosisDTO> medicamentDosisList) {
        this.name = name;
        this.duration = duration;
        this.medicamentDosisList = medicamentDosisList;
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

    public List<MedicamentDosisDTO> getMedicamentDosisList() {
        return medicamentDosisList;
    }

    public void setMedicamentDosisList(List<MedicamentDosisDTO> medicamentDosisList) {
        this.medicamentDosisList = medicamentDosisList;
    }
}
