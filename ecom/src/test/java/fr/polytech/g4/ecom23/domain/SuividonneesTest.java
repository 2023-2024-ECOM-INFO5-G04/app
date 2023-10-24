package fr.polytech.g4.ecom23.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.polytech.g4.ecom23.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SuividonneesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Suividonnees.class);
        Suividonnees suividonnees1 = new Suividonnees();
        suividonnees1.setId(1L);
        Suividonnees suividonnees2 = new Suividonnees();
        suividonnees2.setId(suividonnees1.getId());
        assertThat(suividonnees1).isEqualTo(suividonnees2);
        suividonnees2.setId(2L);
        assertThat(suividonnees1).isNotEqualTo(suividonnees2);
        suividonnees1.setId(null);
        assertThat(suividonnees1).isNotEqualTo(suividonnees2);
    }
}
