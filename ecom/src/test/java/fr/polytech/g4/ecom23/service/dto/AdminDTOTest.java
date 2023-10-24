package fr.polytech.g4.ecom23.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fr.polytech.g4.ecom23.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdminDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdminDTO.class);
        AdminDTO adminDTO1 = new AdminDTO();
        adminDTO1.setId(1L);
        AdminDTO adminDTO2 = new AdminDTO();
        assertThat(adminDTO1).isNotEqualTo(adminDTO2);
        adminDTO2.setId(adminDTO1.getId());
        assertThat(adminDTO1).isEqualTo(adminDTO2);
        adminDTO2.setId(2L);
        assertThat(adminDTO1).isNotEqualTo(adminDTO2);
        adminDTO1.setId(null);
        assertThat(adminDTO1).isNotEqualTo(adminDTO2);
    }
}
