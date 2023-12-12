package fr.polytech.g4.ecom23.service.dto;

import fr.polytech.g4.ecom23.domain.enumeration.Sexe;
import fr.polytech.g4.ecom23.service.SuividonneesService;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link fr.polytech.g4.ecom23.domain.Patient} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PatientDTO implements Serializable {

    @NotNull
    private Long id;

    private String nom;

    private String prenom;

    private Integer age;

    private LocalDate datearrivee;

    private Float poidsactuel;

    private Float albumine;

    private Float taille;

    private Sexe sexe;

    private AlerteDTO alerte;

    private EtablissementDTO etablissement;

    private enum Denutrition {
        NON, MOD, SEV
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDate getDatearrivee() {
        return datearrivee;
    }

    public void setDatearrivee(LocalDate datearrivee) {
        this.datearrivee = datearrivee;
    }

    public Float getPoidsactuel() {
        return poidsactuel;
    }

    public void setPoidsactuel(Float poidsactuel) {
        this.poidsactuel = poidsactuel;
    }

    public Float getAlbumine() {
        return albumine;
    }

    public void setAlbumine(Float albumine) {
        this.albumine = albumine;
    }

    public Float getTaille() {
        return taille;
    }

    public void setTaille(Float taille) {
        this.taille = taille;
    }

    public Sexe getSexe() {
        return sexe;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public AlerteDTO getAlerte() {
        return alerte;
    }

    public void setAlerte(AlerteDTO alerte) {
        this.alerte = alerte;
    }

    public EtablissementDTO getEtablissement() {
        return etablissement;
    }

    public void setEtablissement(EtablissementDTO etablissement) {
        this.etablissement = etablissement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PatientDTO)) {
            return false;
        }

        PatientDTO patientDTO = (PatientDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, patientDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PatientDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", age=" + getAge() +
            ", datearrivee='" + getDatearrivee() + "'" +
            ", poidsactuel=" + getPoidsactuel() +
            ", albumine=" + getAlbumine() +
            ", taille=" + getTaille() +
            ", sexe='" + getSexe() + "'" +
            ", alerte=" + getAlerte() +
            ", etablissement=" + getEtablissement() +
            "}";
    }

    private boolean IMC(List<SuividonneesDTO> list, float sub70, float more70) {
        for (SuividonneesDTO suivi : list) {
            Float poidsActuel = suivi.getPoids();
            if (poidsActuel != null) {
                float IMC = poidsActuel * poidsActuel / taille;
                if (age < 70)
                    return IMC < sub70;
                return IMC < more70;
            }
        }
        return false;
    }

    private Float movingAveragePoids(LocalDate d1, LocalDate d2, List<SuividonneesDTO> list) {
        List<Float> poidsList = new LinkedList<Float>();
        for (SuividonneesDTO suivi : list) {
            if (suivi.getDate().compareTo(d2) <= 0) {
                if (suivi.getDate().compareTo(d1) >= 0) {
                    Float poids = suivi.getPoids();
                    if (poids != null) {
                        poidsList.add(poids);
                    }
                }
                else {
                    break;
                }
            }
        }
        if (poidsList.isEmpty())
            return null;
        Float sum = new Float(0);
        for (Float poids : poidsList) {
            sum += poids;
        }
        return sum / poidsList.size();
    }

    private Float poidsEvolution(List<SuividonneesDTO> list, int days) {
        Float movingAverage_t, movingAverage_t1;
        LocalDate t0 = LocalDate.now();
        LocalDate t1 = LocalDate.ofEpochDay(t0.toEpochDay() - days);
        LocalDate t2 = LocalDate.ofEpochDay(t0.toEpochDay() - 2L * days);
        movingAverage_t = movingAveragePoids(t1, t0, list);
        if (movingAverage_t == null)
            return null;
        movingAverage_t1 = movingAveragePoids(t2, t1, list);
        if (movingAverage_t1 == null)
            return null;
        return (movingAverage_t - movingAverage_t1) / movingAverage_t1;
    }

    private boolean weightLoss(List<SuividonneesDTO> list, int days1, float loss1, int days2, float loss2, float loss3) {
        Float evo1 = poidsEvolution(list, days1);
        if (evo1 != null && evo1 <= -loss1)
            return true;
        Float evo2 = poidsEvolution(list, days2);
        if (evo2 != null && evo2 <= -loss2)
            return true;
        return weightLoss3(list, loss3);
    }

    private boolean weightLoss3(List<SuividonneesDTO> list, float loss3) {
        int size = list.size();
        Float firstPoids = null;
        Float lastPoids = null;
        for (int i = 0; i < size; i++) {
            if (lastPoids == null) {
                lastPoids = list.get(i).getPoids();
            }
            if (firstPoids == null) {
                int j = size - i - 1;
                if (j <= i)
                    return false;
                firstPoids = list.get(j).getPoids();
            }
            if (firstPoids != null && lastPoids != null)
                return (lastPoids - firstPoids) / firstPoids < -loss3;
        }
        return false;
    }

    private Float movingAverageCalories(LocalDate d1, LocalDate d2, List<SuividonneesDTO> list) {
        List<Float> caloriesList = new LinkedList<Float>();
        for (SuividonneesDTO suivi : list) {
            if (suivi.getDate().compareTo(d2) <= 0) {
                if (suivi.getDate().compareTo(d1) >= 0) {
                    Float quantitepoidsaliments = suivi.getQuantitepoidsaliments();
                    Float quantitecaloriesaliments = suivi.getQuantitecaloriesaliments();
                    if (quantitepoidsaliments != null && quantitecaloriesaliments != null) {
                        Float calories = quantitecaloriesaliments * quantitepoidsaliments / 100;
                        caloriesList.add(calories);
                    }
                }
                else {
                    break;
                }
            }
        }
        if (caloriesList.isEmpty())
            return null;
        Float sum = new Float(0);
        for (Float calories : caloriesList) {
            sum += calories;
        }
        return sum / caloriesList.size();
    }


    private boolean phe1(List<SuividonneesDTO> list) {
        return weightLoss(list, 30, 0.05F, 183, 0.1F, 0.1F);
    }

    private boolean phe2(List<SuividonneesDTO> list) {
        return IMC(list, 18.5f, 22);
    }

    private boolean phe3(List<SuividonneesDTO> list) {
        if (age < 70)
            throw new RuntimeException("NYI");
        else
            throw new RuntimeException("NYI");
    }

    private boolean eti1(List<SuividonneesDTO> list) {
        throw new RuntimeException("NYI");
    }

    private boolean eti2(List<SuividonneesDTO> list) {
        for (SuividonneesDTO suivi : list) {
            Boolean absorptionreduite = suivi.getAbsorptionreduite();
            if (absorptionreduite != null)
                return absorptionreduite;
        }
        return false;
    }

    private boolean eti3(List<SuividonneesDTO> list) {
        for (SuividonneesDTO suivi : list) {
            Boolean agression = suivi.getAgression();
            if (agression != null)
                return agression;
        }
        return false;
    }

    private boolean sev1(List<SuividonneesDTO> list) {
        return weightLoss(list, 30, 0.1F, 183, 0.15F, 0.15F);
    }

    private boolean sev2(List<SuividonneesDTO> list) {
        return IMC(list,17, 20);
    }

    private boolean sev3(List<SuividonneesDTO> list) {
        return albumine <= 30;
    }

    private boolean phenotypique(List<SuividonneesDTO> list) {
        return phe1(list) || phe2(list) || phe3(list);
    }

    private boolean etiologique(List<SuividonneesDTO> list) {
        return eti1(list) || eti2(list) || eti3(list);
    }

    private boolean severe(List<SuividonneesDTO> list) {
        return sev1(list) || sev2(list) || sev3(list);
    }

    public Denutrition denutrition(SuividonneesService suiviService) {
        List<SuividonneesDTO> all = suiviService.findAll();
        List<SuividonneesDTO> list = new LinkedList<SuividonneesDTO>();
        for (SuividonneesDTO suivi : all) {
            if (suivi.getPatient().getId().equals(id)) {
                list.add(suivi);
            }
        }
        if (list.isEmpty())
            return Denutrition.NON;
        Collections.sort(list);
        if (!phenotypique(list) || !etiologique(list))
            return Denutrition.NON;
        if (severe(list))
            return Denutrition.SEV;
        return Denutrition.MOD;
    }

}
