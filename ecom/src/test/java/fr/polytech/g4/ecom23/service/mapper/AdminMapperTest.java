package fr.polytech.g4.ecom23.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AdminMapperTest {

    private AdminMapper adminMapper;

    @BeforeEach
    public void setUp() {
        adminMapper = new AdminMapperImpl();
    }
}
