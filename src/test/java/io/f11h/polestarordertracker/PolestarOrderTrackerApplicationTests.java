package io.f11h.polestarordertracker;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest
class PolestarOrderTrackerApplicationTests {

    @MockBean
    JavaMailSender javaMailSenderMock;

    @Test
    void contextLoads() {
    }

}
