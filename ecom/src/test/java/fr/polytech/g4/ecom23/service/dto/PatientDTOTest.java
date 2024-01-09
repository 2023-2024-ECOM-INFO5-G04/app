package fr.polytech.g4.ecom23.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fr.polytech.g4.ecom23.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

class PatientDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        test0();

        PatientDTO p = new PatientDTO();
        eti1_0(p);

        p = new PatientDTO();
        eti1_1(p);

        p = new PatientDTO();
        eti1_2(p);

        p = new PatientDTO();
        eti1_3(p);

        p = new PatientDTO();
        phe2_0(p);
        p = new PatientDTO();
        phe2_1(p);
        p = new PatientDTO();
        phe2_2(p);
        p = new PatientDTO();
        phe2_3(p);

    }

    void test0() throws Exception {
        TestUtil.equalsVerifier(PatientDTO.class);
        PatientDTO patientDTO1 = new PatientDTO();
        patientDTO1.setId(1L);
        PatientDTO patientDTO2 = new PatientDTO();
        assertThat(patientDTO1).isNotEqualTo(patientDTO2);
        patientDTO2.setId(patientDTO1.getId());
        assertThat(patientDTO1).isEqualTo(patientDTO2);
        patientDTO2.setId(2L);
        assertThat(patientDTO1).isNotEqualTo(patientDTO2);
        patientDTO1.setId(null);
        assertThat(patientDTO1).isNotEqualTo(patientDTO2);
    }

    /*
    eti1
    0 : evo1 <= -loss1 evo2 < 0
    1 : evo1 > -loss1 evo2 < 0
    0 : evo1 <= -loss1 evo2 >= 0
    1 : evo1 > -loss1 evo2 >= 0

     */

    void age_0(PatientDTO p) {
        p.setAge(60);
    }

    void age_1(PatientDTO p) {
        p.setAge(80);
    }

    List<SuividonneesDTO> eti1_0(PatientDTO p) {
        Float quantitecaloriesaliments = 100f;
        LinkedList<SuividonneesDTO> list = new LinkedList<>();
        for (int i = 30; i >= 0; i--) {
            SuividonneesDTO s = new SuividonneesDTO();
            s.setQuantitecaloriesaliments(quantitecaloriesaliments);
            s.setQuantitepoidsaliments(i/30f*2000f);
            s.setDate(LocalDate.now().minusDays(i));
            s.setPatient(p);
            list.add(s);
        }
        list.sort(new SuiviComparator());
        assertThat(p.evolution(list, PatientDTO.Donnee.CALORIES, 7)).isLessThanOrEqualTo(-0.5f);
        assertThat(p.evolution(list, PatientDTO.Donnee.CALORIES, 14)).isLessThan(0);
        return list;
    }

    List<SuividonneesDTO> eti1_1(PatientDTO p) {
        Float quantitecaloriesaliments = 100f;
        LinkedList<SuividonneesDTO> list = new LinkedList<>();
        for (int i = 0; i <= 30; i++) {
            SuividonneesDTO s = new SuividonneesDTO();
            s.setQuantitecaloriesaliments(quantitecaloriesaliments);
            s.setQuantitepoidsaliments(2000f-i);
            s.setDate(LocalDate.now().minusDays(30-i));
            s.setPatient(p);
            list.add(s);
        }
        list.sort(new SuiviComparator());
        assertThat(p.evolution(list, PatientDTO.Donnee.CALORIES, 7)).isGreaterThan(-0.5f);
        assertThat(p.evolution(list, PatientDTO.Donnee.CALORIES, 14)).isLessThan(0);
        return list;
    }

    List<SuividonneesDTO> eti1_2(PatientDTO p) {
        Float quantitecaloriesaliments = 100f;
        LinkedList<SuividonneesDTO> list = new LinkedList<>();
        for (int i = 0; i <= 30; i++) {
            SuividonneesDTO s = new SuividonneesDTO();
            s.setQuantitecaloriesaliments(quantitecaloriesaliments);
            if (i < 16)
                s.setQuantitepoidsaliments(2000f);
            else if (i < 23)
                s.setQuantitepoidsaliments(4000f);
            else
                s.setQuantitepoidsaliments(1000f);
            s.setDate(LocalDate.now().minusDays(30-i));
            s.setPatient(p);
            list.add(s);
        }
        list.sort(new SuiviComparator());
        assertThat(p.evolution(list, PatientDTO.Donnee.CALORIES, 7)).isLessThanOrEqualTo(-0.5f);
        assertThat(p.evolution(list, PatientDTO.Donnee.CALORIES, 14)).isGreaterThanOrEqualTo(0);
        return list;
    }

    List<SuividonneesDTO> eti1_3(PatientDTO p) {
        Float quantitecaloriesaliments = 100f;
        LinkedList<SuividonneesDTO> list = new LinkedList<>();
        for (int i = 0; i <= 30; i++) {
            SuividonneesDTO s = new SuividonneesDTO();
            s.setQuantitecaloriesaliments(quantitecaloriesaliments);
            s.setQuantitepoidsaliments(i/30f*2000f);
            s.setDate(LocalDate.now().minusDays(30-i));
            s.setPatient(p);
            list.add(s);
        }
        list.sort(new SuiviComparator());
        assertThat(p.evolution(list, PatientDTO.Donnee.CALORIES, 7)).isGreaterThan(-0.5f);
        assertThat(p.evolution(list, PatientDTO.Donnee.CALORIES, 14)).isGreaterThanOrEqualTo(0);
        return list;
    }

    void eti2_0(PatientDTO p) {
        p.setAbsorptionreduite(false);
    }

    void eti2_1(PatientDTO p) {
        p.setAbsorptionreduite(true);
    }

    void eti3_0(PatientDTO p) {
        p.setAgression(false);
    }

    void eti3_1(PatientDTO p) {
        p.setAgression(true);
    }

    void phe1() {
        throw new RuntimeException("TODO");
    }

    void phe2_0(PatientDTO p) {
        p.setAge(60);
        p.setTaille(1.8f);
        LinkedList<SuividonneesDTO> list = new LinkedList<>();
        SuividonneesDTO s = new SuividonneesDTO();
        s.setPoids(3f);
        s.setDate(LocalDate.now());
        s.setPatient(p);
        list.add(s);
        assertThat(p.IMC(list, 18.5f)).isEqualTo(true);
    }

    void phe2_1(PatientDTO p) {
        p.setAge(60);
        p.setTaille(1.8f);
        LinkedList<SuividonneesDTO> list = new LinkedList<>();
        SuividonneesDTO s = new SuividonneesDTO();
        s.setPoids(300f);
        s.setDate(LocalDate.now());
        s.setPatient(p);
        list.add(s);
        assertThat(p.IMC(list, 18.5f)).isEqualTo(false);
    }

    void phe2_2(PatientDTO p) {
        p.setAge(80);
        p.setTaille(1.8f);
        LinkedList<SuividonneesDTO> list = new LinkedList<>();
        SuividonneesDTO s = new SuividonneesDTO();
        s.setPoids(3f);
        s.setDate(LocalDate.now());
        s.setPatient(p);
        list.add(s);
        assertThat(p.IMC(list, 22f)).isEqualTo(true);
    }

    void phe2_3(PatientDTO p) {
        p.setAge(80);
        p.setTaille(1.8f);
        LinkedList<SuividonneesDTO> list = new LinkedList<>();
        SuividonneesDTO s = new SuividonneesDTO();
        s.setPoids(300f);
        s.setDate(LocalDate.now());
        s.setPatient(p);
        list.add(s);
        assertThat(p.IMC(list, 18.5f)).isEqualTo(false);
    }

    void phe3_0(PatientDTO p) {
        p.setSarcopenie(false);
    }

    void phe3_1(PatientDTO p) {
        p.setSarcopenie(true);
    }

    void sev1() {
        throw new RuntimeException("TODO");
    }



    void sev2_0(PatientDTO p) {
        p.setAge(60);
        p.setTaille(1.8f);
        LinkedList<SuividonneesDTO> list = new LinkedList<>();
        SuividonneesDTO s = new SuividonneesDTO();
        s.setPoids(3f);
        s.setDate(LocalDate.now());
        s.setPatient(p);
        list.add(s);
        assertThat(p.IMC(list, 18.5f)).isEqualTo(true);
    }

    void sev2_1(PatientDTO p) {
        p.setAge(60);
        p.setTaille(1.8f);
        LinkedList<SuividonneesDTO> list = new LinkedList<>();
        SuividonneesDTO s = new SuividonneesDTO();
        s.setPoids(300f);
        s.setDate(LocalDate.now());
        s.setPatient(p);
        list.add(s);
        assertThat(p.IMC(list, 18.5f)).isEqualTo(false);
    }

    void sev2_2(PatientDTO p) {
        p.setAge(80);
        p.setTaille(1.8f);
        LinkedList<SuividonneesDTO> list = new LinkedList<>();
        SuividonneesDTO s = new SuividonneesDTO();
        s.setPoids(3f);
        s.setDate(LocalDate.now());
        s.setPatient(p);
        list.add(s);
        assertThat(p.IMC(list, 22f)).isEqualTo(true);
    }

    void sev2_3(PatientDTO p) {
        p.setAge(80);
        p.setTaille(1.8f);
        LinkedList<SuividonneesDTO> list = new LinkedList<>();
        SuividonneesDTO s = new SuividonneesDTO();
        s.setPoids(300f);
        s.setDate(LocalDate.now());
        s.setPatient(p);
        list.add(s);
        assertThat(p.IMC(list, 18.5f)).isEqualTo(false);
    }

}
