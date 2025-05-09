package com.openclassrooms.starterjwt.payload.response;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class MessageResponseTest {
    @Test
    void testGettersAndSetters() {
        MessageResponse msg = new MessageResponse("Hello!");
        assertThat(msg.getMessage()).isEqualTo("Hello!");
        msg.setMessage("Bye!");
        assertThat(msg.getMessage()).isEqualTo("Bye!");
    }
} 