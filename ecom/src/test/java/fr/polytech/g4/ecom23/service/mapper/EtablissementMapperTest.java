package fr.polytech.g4.ecom23.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EtablissementMapperTest {

    private EtablissementMapper etablissementMapper;

    @BeforeEach
    public void setUp() {
        etablissementMapper = new EtablissementMapperImpl();
    }
}
