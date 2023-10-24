package fr.polytech.g4.ecom23.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.polytech.g4.ecom23.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EtablissementTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Etablissement.class);
        Etablissement etablissement1 = new Etablissement();
        etablissement1.setId(1L);
        Etablissement etablissement2 = new Etablissement();
        etablissement2.setId(etablissement1.getId());
        assertThat(etablissement1).isEqualTo(etablissement2);
        etablissement2.setId(2L);
        assertThat(etablissement1).isNotEqualTo(etablissement2);
        etablissement1.setId(null);
        assertThat(etablissement1).isNotEqualTo(etablissement2);
    }
}
