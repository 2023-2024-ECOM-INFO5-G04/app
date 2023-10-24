package fr.polytech.g4.ecom23.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.polytech.g4.ecom23.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ServicesoignantTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Servicesoignant.class);
        Servicesoignant servicesoignant1 = new Servicesoignant();
        servicesoignant1.setId(1L);
        Servicesoignant servicesoignant2 = new Servicesoignant();
        servicesoignant2.setId(servicesoignant1.getId());
        assertThat(servicesoignant1).isEqualTo(servicesoignant2);
        servicesoignant2.setId(2L);
        assertThat(servicesoignant1).isNotEqualTo(servicesoignant2);
        servicesoignant1.setId(null);
        assertThat(servicesoignant1).isNotEqualTo(servicesoignant2);
    }
}
