package fr.polytech.g4.ecom23.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.polytech.g4.ecom23.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdministrateurTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Administrateur.class);
        Administrateur administrateur1 = new Administrateur();
        administrateur1.setId(1L);
        Administrateur administrateur2 = new Administrateur();
        administrateur2.setId(administrateur1.getId());
        assertThat(administrateur1).isEqualTo(administrateur2);
        administrateur2.setId(2L);
        assertThat(administrateur1).isNotEqualTo(administrateur2);
        administrateur1.setId(null);
        assertThat(administrateur1).isNotEqualTo(administrateur2);
    }
}
