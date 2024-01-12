package fr.polytech.g4.ecom23.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fr.polytech.g4.ecom23.domain.Patient;
import fr.polytech.g4.ecom23.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

class PatientDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        test0();

        testEti1();
        testEti2();
        testEti3();

        testPhe1();
        testPhe2();
        testPhe3();

        testSev1();
        testSev2();
        testSev3();

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

    void testEti1() {
        testEti1_1();
        testEti1_2();
        testEti1_3();
        testEti1_4();
    }
    void testEti2() {
        testEti2_1();
        testEti2_2();
    }
    void testEti3() {
        testEti3_1();
        testEti3_2();
    }
    void testPhe1() {
        testPhe1_1();
        testPhe1_2();
        testPhe1_3();
        testPhe1_4();
    }
    void testPhe2() {
        testPhe2_1();
        testPhe2_2();
        testPhe2_3();
        testPhe2_4();
    }
    void testPhe3() {
        testPhe3_1();
        testPhe3_2();
    }
    void testSev1() {
        testSev1_1();
        testSev1_2();
        testSev1_3();
        testSev1_4();
    }
    void testSev2() {
        testSev2_1();
        testSev2_2();
        testSev2_3();
        testSev2_4();
    }
    void testSev3() {
        testSev3_1();
        testSev3_2();
    }

    void testEti1_1() {
        PatientDTO p = new PatientDTO();
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
        assertThat(p.eti1(list)).isEqualTo(true);
    }

    void testEti1_2() {
        PatientDTO p = new PatientDTO();
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
        assertThat(p.eti1(list)).isEqualTo(true);
    }

    void testEti1_3() {
        PatientDTO p = new PatientDTO();
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
        assertThat(p.eti1(list)).isEqualTo(true);
    }

    void testEti1_4() {
        PatientDTO p = new PatientDTO();
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
        assertThat(p.eti1(list)).isEqualTo(false);
    }

    void testEti2_1() {
        PatientDTO p = new PatientDTO();
        p.setAbsorptionreduite(false);
        assertThat(p.eti2()).isEqualTo(false);
    }

    void testEti2_2() {
        PatientDTO p = new PatientDTO();
        p.setAbsorptionreduite(true);
        assertThat(p.eti2()).isEqualTo(true);
    }

    void testEti3_1() {
        PatientDTO p = new PatientDTO();
        p.setAgression(false);
        assertThat(p.eti3()).isEqualTo(false);
    }

    void testEti3_2() {
        PatientDTO p = new PatientDTO();
        p.setAgression(true);
        assertThat(p.eti3()).isEqualTo(true);
    }

    void testPhe1_1() {
        PatientDTO p = new PatientDTO();
        LinkedList<SuividonneesDTO> list = new LinkedList<>();
        for (int i = 0; i <= 300; i++) {
            SuividonneesDTO s = new SuividonneesDTO();
            s.setPoids((float)i);
            s.setDate(LocalDate.now().minusDays(300-i));
            s.setPatient(p);
            list.add(s);
        }
        list.sort(new SuiviComparator());
        assertThat(p.evolution(list, PatientDTO.Donnee.POIDS, 30)).isGreaterThan(-0.05f);
        assertThat(p.evolution(list, PatientDTO.Donnee.POIDS, 183)).isGreaterThan(-0.1f);
        assertThat(p.weightLoss3(list, 0.1f)).isEqualTo(false);
        assertThat(p.weightLoss(list, 30, 0.05f, 183, 0.1f, 0.1f)).isEqualTo(false);
        assertThat(p.phe1(list)).isEqualTo(false);
    }

    void testPhe1_2() {
        PatientDTO p = new PatientDTO();
        LinkedList<SuividonneesDTO> list = new LinkedList<>();
        for (int i = 0; i <= 300; i++) {
            SuividonneesDTO s = new SuividonneesDTO();
            if (i > 270)
                s.setPoids(60f);
            else
                s.setPoids((float)(300-i));
            s.setDate(LocalDate.now().minusDays(300-i));
            s.setPatient(p);
            list.add(s);
        }
        list.sort(new SuiviComparator());
        assertThat(p.evolution(list, PatientDTO.Donnee.POIDS, 30)).isGreaterThan(-0.05f);
        assertThat(p.evolution(list, PatientDTO.Donnee.POIDS, 183)).isLessThanOrEqualTo(-0.1f);
        assertThat(p.weightLoss3(list, 0.1f)).isEqualTo(true);
        assertThat(p.weightLoss(list, 30, 0.05f, 183, 0.1f, 0.1f)).isEqualTo(true);
        assertThat(p.phe1(list)).isEqualTo(true);
    }

    void testPhe1_3() {
        PatientDTO p = new PatientDTO();
        LinkedList<SuividonneesDTO> list = new LinkedList<>();
        for (int i = 0; i <= 300; i++) {
            SuividonneesDTO s = new SuividonneesDTO();
            if (i < 300)
                s.setPoids(300f-i);
            else
                s.setPoids(301f);

            s.setDate(LocalDate.now().minusDays(300-i));
            s.setPatient(p);
            list.add(s);
        }
        list.sort(new SuiviComparator());
        assertThat(p.evolution(list, PatientDTO.Donnee.POIDS, 30)).isLessThanOrEqualTo(-0.05f);
        assertThat(p.evolution(list, PatientDTO.Donnee.POIDS, 183)).isLessThanOrEqualTo(-0.1f);
        assertThat(p.weightLoss3(list, 0.1f)).isEqualTo(false);
        assertThat(p.weightLoss(list, 30, 0.05f, 183, 0.1f, 0.1f)).isEqualTo(true);
        assertThat(p.phe1(list)).isEqualTo(true);
    }

    void testPhe1_4() {
        PatientDTO p = new PatientDTO();
        LinkedList<SuividonneesDTO> list = new LinkedList<>();
        for (int i = 0; i <= 300; i++) {
            SuividonneesDTO s = new SuividonneesDTO();
            if (i == 0)
                s.setPoids(1000f);
            else if (i < 240)
                s.setPoids(0f);
            else if (i < 270)
                s.setPoids(300f);
            else
                s.setPoids(200f);
            s.setDate(LocalDate.now().minusDays(300-i));
            s.setPatient(p);
            list.add(s);
        }
        list.sort(new SuiviComparator());
        assertThat(p.evolution(list, PatientDTO.Donnee.POIDS, 30)).isLessThanOrEqualTo(-0.05f);
        assertThat(p.evolution(list, PatientDTO.Donnee.POIDS, 183)).isGreaterThan(-0.1f);
        assertThat(p.weightLoss3(list, 0.1f)).isEqualTo(true);
        assertThat(p.weightLoss(list, 30, 0.05f, 183, 0.1f, 0.1f)).isEqualTo(true);
        assertThat(p.phe1(list)).isEqualTo(true);
    }

    void testPhe2_1() {
        PatientDTO p = new PatientDTO();
        p.setAge(60);
        p.setTaille(1.8f);
        LinkedList<SuividonneesDTO> list = new LinkedList<>();
        SuividonneesDTO s = new SuividonneesDTO();
        s.setPoids(1f);
        s.setDate(LocalDate.now());
        s.setPatient(p);
        list.add(s);
        assertThat(p.IMC(list, 18.5f)).isEqualTo(true);
        assertThat(p.phe2(list)).isEqualTo(true);
    }

    void testPhe2_2() {
        PatientDTO p = new PatientDTO();
        p.setAge(60);
        p.setTaille(1.8f);
        LinkedList<SuividonneesDTO> list = new LinkedList<>();
        SuividonneesDTO s = new SuividonneesDTO();
        s.setPoids(100f);
        s.setDate(LocalDate.now());
        s.setPatient(p);
        list.add(s);
        assertThat(p.IMC(list, 18.5f)).isEqualTo(false);
        assertThat(p.phe2(list)).isEqualTo(false);
    }

    void testPhe2_3() {
        PatientDTO p = new PatientDTO();
        p.setAge(80);
        p.setTaille(1.8f);
        LinkedList<SuividonneesDTO> list = new LinkedList<>();
        SuividonneesDTO s = new SuividonneesDTO();
        s.setPoids(1f);
        s.setDate(LocalDate.now());
        s.setPatient(p);
        list.add(s);
        assertThat(p.IMC(list, 22f)).isEqualTo(true);
        assertThat(p.phe2(list)).isEqualTo(true);
    }

    void testPhe2_4() {
        PatientDTO p = new PatientDTO();
        p.setAge(80);
        p.setTaille(1.8f);
        LinkedList<SuividonneesDTO> list = new LinkedList<>();
        SuividonneesDTO s = new SuividonneesDTO();
        s.setPoids(100f);
        s.setDate(LocalDate.now());
        s.setPatient(p);
        list.add(s);
        assertThat(p.IMC(list, 22f)).isEqualTo(false);
        assertThat(p.phe2(list)).isEqualTo(false);
    }

    void testPhe3_1() {
        PatientDTO p = new PatientDTO();
        p.setSarcopenie(false);
        assertThat(p.phe3()).isEqualTo(false);
    }

    void testPhe3_2() {
        PatientDTO p = new PatientDTO();
        p.setSarcopenie(true);
        assertThat(p.phe3()).isEqualTo(true);
    }

    void testSev1_1() {
        PatientDTO p = new PatientDTO();
        LinkedList<SuividonneesDTO> list = new LinkedList<>();
        for (int i = 0; i <= 300; i++) {
            SuividonneesDTO s = new SuividonneesDTO();
            s.setPoids((float)i);
            s.setDate(LocalDate.now().minusDays(300-i));
            s.setPatient(p);
            list.add(s);
        }
        list.sort(new SuiviComparator());
        assertThat(p.evolution(list, PatientDTO.Donnee.POIDS, 30)).isGreaterThan(-0.1f);
        assertThat(p.evolution(list, PatientDTO.Donnee.POIDS, 183)).isGreaterThan(-0.15f);
        assertThat(p.weightLoss3(list, 0.15f)).isEqualTo(false);
        assertThat(p.weightLoss(list, 30, 0.1f, 183, 0.15f, 0.15f)).isEqualTo(false);
        assertThat(p.sev1(list)).isEqualTo(false);
    }

    void testSev1_2() {
        PatientDTO p = new PatientDTO();
        LinkedList<SuividonneesDTO> list = new LinkedList<>();
        for (int i = 0; i <= 300; i++) {
            SuividonneesDTO s = new SuividonneesDTO();
            if (i > 270)
                s.setPoids(60f);
            else
                s.setPoids((float)(300-i));
            s.setDate(LocalDate.now().minusDays(300-i));
            s.setPatient(p);
            list.add(s);
        }
        list.sort(new SuiviComparator());
        assertThat(p.evolution(list, PatientDTO.Donnee.POIDS, 30)).isGreaterThan(-0.1f);
        assertThat(p.evolution(list, PatientDTO.Donnee.POIDS, 183)).isLessThanOrEqualTo(-0.15f);
        assertThat(p.weightLoss3(list, 0.15f)).isEqualTo(true);
        assertThat(p.weightLoss(list, 30, 0.1f, 183, 0.15f, 0.15f)).isEqualTo(true);
        assertThat(p.sev1(list)).isEqualTo(true);
    }

    void testSev1_3() {
        PatientDTO p = new PatientDTO();
        LinkedList<SuividonneesDTO> list = new LinkedList<>();
        for (int i = 0; i <= 300; i++) {
            SuividonneesDTO s = new SuividonneesDTO();
            if (i < 300)
                s.setPoids(300f-i);
            else
                s.setPoids(301f);

            s.setDate(LocalDate.now().minusDays(300-i));
            s.setPatient(p);
            list.add(s);
        }
        list.sort(new SuiviComparator());
        assertThat(p.evolution(list, PatientDTO.Donnee.POIDS, 30)).isLessThanOrEqualTo(-0.1f);
        assertThat(p.evolution(list, PatientDTO.Donnee.POIDS, 183)).isLessThanOrEqualTo(-0.15f);
        assertThat(p.weightLoss3(list, 0.15f)).isEqualTo(false);
        assertThat(p.weightLoss(list, 30, 0.1f, 183, 0.15f, 0.15f)).isEqualTo(true);
        assertThat(p.sev1(list)).isEqualTo(true);
    }

    void testSev1_4() {
        PatientDTO p = new PatientDTO();
        LinkedList<SuividonneesDTO> list = new LinkedList<>();
        for (int i = 0; i <= 300; i++) {
            SuividonneesDTO s = new SuividonneesDTO();
            if (i == 0)
                s.setPoids(1000f);
            else if (i < 240)
                s.setPoids(0f);
            else if (i < 270)
                s.setPoids(300f);
            else
                s.setPoids(200f);
            s.setDate(LocalDate.now().minusDays(300-i));
            s.setPatient(p);
            list.add(s);
        }
        list.sort(new SuiviComparator());
        assertThat(p.evolution(list, PatientDTO.Donnee.POIDS, 30)).isLessThanOrEqualTo(-0.1f);
        assertThat(p.evolution(list, PatientDTO.Donnee.POIDS, 183)).isGreaterThan(-0.15f);
        assertThat(p.weightLoss3(list, 0.15f)).isEqualTo(true);
        assertThat(p.weightLoss(list, 30, 0.1f, 183, 0.15f, 0.15f)).isEqualTo(true);
        assertThat(p.sev1(list)).isEqualTo(true);
    }

    void testSev2_1() {
        PatientDTO p = new PatientDTO();
        p.setAge(60);
        p.setTaille(1.8f);
        LinkedList<SuividonneesDTO> list = new LinkedList<>();
        SuividonneesDTO s = new SuividonneesDTO();
        s.setPoids(1f);
        s.setDate(LocalDate.now());
        s.setPatient(p);
        list.add(s);
        assertThat(p.IMC(list, 17f)).isEqualTo(true);
        assertThat(p.phe2(list)).isEqualTo(true);
    }

    void testSev2_2() {
        PatientDTO p = new PatientDTO();
        p.setAge(60);
        p.setTaille(1.8f);
        LinkedList<SuividonneesDTO> list = new LinkedList<>();
        SuividonneesDTO s = new SuividonneesDTO();
        s.setPoids(100f);
        s.setDate(LocalDate.now());
        s.setPatient(p);
        list.add(s);
        assertThat(p.IMC(list, 17f)).isEqualTo(false);
        assertThat(p.phe2(list)).isEqualTo(false);
    }

    void testSev2_3() {
        PatientDTO p = new PatientDTO();
        p.setAge(80);
        p.setTaille(1.8f);
        LinkedList<SuividonneesDTO> list = new LinkedList<>();
        SuividonneesDTO s = new SuividonneesDTO();
        s.setPoids(1f);
        s.setDate(LocalDate.now());
        s.setPatient(p);
        list.add(s);
        assertThat(p.IMC(list, 20f)).isEqualTo(true);
        assertThat(p.phe2(list)).isEqualTo(true);
    }

    void testSev2_4() {
        PatientDTO p = new PatientDTO();
        p.setAge(80);
        p.setTaille(1.8f);
        LinkedList<SuividonneesDTO> list = new LinkedList<>();
        SuividonneesDTO s = new SuividonneesDTO();
        s.setPoids(100f);
        s.setDate(LocalDate.now());
        s.setPatient(p);
        list.add(s);
        assertThat(p.IMC(list, 20f)).isEqualTo(false);
        assertThat(p.phe2(list)).isEqualTo(false);
    }

    void testSev3_1() {
        PatientDTO p = new PatientDTO();
        p.setAlbumine(20f);
        assertThat(p.sev3()).isEqualTo(true);
    }

    void testSev3_2() {
        PatientDTO p = new PatientDTO();
        p.setAlbumine(40f);
        assertThat(p.sev3()).isEqualTo(false);
    }

}
