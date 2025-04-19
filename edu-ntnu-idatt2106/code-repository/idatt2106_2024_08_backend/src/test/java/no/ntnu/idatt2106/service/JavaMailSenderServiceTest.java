package no.ntnu.idatt2106.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class JavaMailSenderServiceTest {

  @Autowired
  private JavaMailSenderService javaMailSenderService;

  @MockBean
  private JavaMailSenderImpl mailSenderMock;

  @Test
  void testSendSimpleEmail() {
    // Arrange
    String to = "emilmoen17@gmail.com";
    String subject = "Test Subject";
    String text = "Test Text. This is your new password: 12345678";

    // Act
    javaMailSenderService.sendSimpleEmail(to, subject, text);

    // Assert
    verify(mailSenderMock, times(1)).send(any(SimpleMailMessage.class));
  }
}