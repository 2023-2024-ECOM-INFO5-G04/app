package fr.polytech.g4.ecom23.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fr.polytech.g4.ecom23.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RappelDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RappelDTO.class);
        RappelDTO rappelDTO1 = new RappelDTO();
        rappelDTO1.setId(1L);
        RappelDTO rappelDTO2 = new RappelDTO();
        assertThat(rappelDTO1).isNotEqualTo(rappelDTO2);
        rappelDTO2.setId(rappelDTO1.getId());
        assertThat(rappelDTO1).isEqualTo(rappelDTO2);
        rappelDTO2.setId(2L);
        assertThat(rappelDTO1).isNotEqualTo(rappelDTO2);
        rappelDTO1.setId(null);
        assertThat(rappelDTO1).isNotEqualTo(rappelDTO2);
    }
}
