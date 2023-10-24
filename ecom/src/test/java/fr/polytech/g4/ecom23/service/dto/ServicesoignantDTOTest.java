package fr.polytech.g4.ecom23.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fr.polytech.g4.ecom23.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ServicesoignantDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServicesoignantDTO.class);
        ServicesoignantDTO servicesoignantDTO1 = new ServicesoignantDTO();
        servicesoignantDTO1.setId(1L);
        ServicesoignantDTO servicesoignantDTO2 = new ServicesoignantDTO();
        assertThat(servicesoignantDTO1).isNotEqualTo(servicesoignantDTO2);
        servicesoignantDTO2.setId(servicesoignantDTO1.getId());
        assertThat(servicesoignantDTO1).isEqualTo(servicesoignantDTO2);
        servicesoignantDTO2.setId(2L);
        assertThat(servicesoignantDTO1).isNotEqualTo(servicesoignantDTO2);
        servicesoignantDTO1.setId(null);
        assertThat(servicesoignantDTO1).isNotEqualTo(servicesoignantDTO2);
    }
}
