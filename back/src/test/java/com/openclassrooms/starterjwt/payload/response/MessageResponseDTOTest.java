package com.openclassrooms.starterjwt.payload.response;

import com.openclassrooms.starterjwt.dto.MessageResponseDTO;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class MessageResponseDTOTest {
    @Test
    void testGettersAndSetters() {
        MessageResponseDTO msg = new MessageResponseDTO("Hello!");
        assertThat(msg.getMessage()).isEqualTo("Hello!");
        msg.setMessage("Bye!");
        assertThat(msg.getMessage()).isEqualTo("Bye!");
    }
} 