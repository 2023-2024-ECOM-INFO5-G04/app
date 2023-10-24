package fr.polytech.g4.ecom23.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.polytech.g4.ecom23.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SoignantTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Soignant.class);
        Soignant soignant1 = new Soignant();
        soignant1.setId(1L);
        Soignant soignant2 = new Soignant();
        soignant2.setId(soignant1.getId());
        assertThat(soignant1).isEqualTo(soignant2);
        soignant2.setId(2L);
        assertThat(soignant1).isNotEqualTo(soignant2);
        soignant1.setId(null);
        assertThat(soignant1).isNotEqualTo(soignant2);
    }
}
