package fr.polytech.g4.ecom23.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fr.polytech.g4.ecom23.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SoignantDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SoignantDTO.class);
        SoignantDTO soignantDTO1 = new SoignantDTO();
        soignantDTO1.setId(1L);
        SoignantDTO soignantDTO2 = new SoignantDTO();
        assertThat(soignantDTO1).isNotEqualTo(soignantDTO2);
        soignantDTO2.setId(soignantDTO1.getId());
        assertThat(soignantDTO1).isEqualTo(soignantDTO2);
        soignantDTO2.setId(2L);
        assertThat(soignantDTO1).isNotEqualTo(soignantDTO2);
        soignantDTO1.setId(null);
        assertThat(soignantDTO1).isNotEqualTo(soignantDTO2);
    }
}
