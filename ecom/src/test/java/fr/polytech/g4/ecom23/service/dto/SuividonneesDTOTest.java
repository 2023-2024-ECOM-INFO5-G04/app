package fr.polytech.g4.ecom23.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fr.polytech.g4.ecom23.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SuividonneesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SuividonneesDTO.class);
        SuividonneesDTO suividonneesDTO1 = new SuividonneesDTO();
        suividonneesDTO1.setId(1L);
        SuividonneesDTO suividonneesDTO2 = new SuividonneesDTO();
        assertThat(suividonneesDTO1).isNotEqualTo(suividonneesDTO2);
        suividonneesDTO2.setId(suividonneesDTO1.getId());
        assertThat(suividonneesDTO1).isEqualTo(suividonneesDTO2);
        suividonneesDTO2.setId(2L);
        assertThat(suividonneesDTO1).isNotEqualTo(suividonneesDTO2);
        suividonneesDTO1.setId(null);
        assertThat(suividonneesDTO1).isNotEqualTo(suividonneesDTO2);
    }
}
