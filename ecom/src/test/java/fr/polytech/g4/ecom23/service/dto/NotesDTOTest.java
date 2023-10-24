package fr.polytech.g4.ecom23.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fr.polytech.g4.ecom23.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NotesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NotesDTO.class);
        NotesDTO notesDTO1 = new NotesDTO();
        notesDTO1.setId(1L);
        NotesDTO notesDTO2 = new NotesDTO();
        assertThat(notesDTO1).isNotEqualTo(notesDTO2);
        notesDTO2.setId(notesDTO1.getId());
        assertThat(notesDTO1).isEqualTo(notesDTO2);
        notesDTO2.setId(2L);
        assertThat(notesDTO1).isNotEqualTo(notesDTO2);
        notesDTO1.setId(null);
        assertThat(notesDTO1).isNotEqualTo(notesDTO2);
    }
}
