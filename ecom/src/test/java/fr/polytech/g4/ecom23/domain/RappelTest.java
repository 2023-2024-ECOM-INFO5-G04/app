package fr.polytech.g4.ecom23.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.polytech.g4.ecom23.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RappelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rappel.class);
        Rappel rappel1 = new Rappel();
        rappel1.setId(1L);
        Rappel rappel2 = new Rappel();
        rappel2.setId(rappel1.getId());
        assertThat(rappel1).isEqualTo(rappel2);
        rappel2.setId(2L);
        assertThat(rappel1).isNotEqualTo(rappel2);
        rappel1.setId(null);
        assertThat(rappel1).isNotEqualTo(rappel2);
    }
}
