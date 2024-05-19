package pl.pkociolek.zbik.components;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.pkociolek.zbik.repository.entity.PendingEmailEntity;
import pl.pkociolek.zbik.service.EmailSchedulerService;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailSchedulerResolver {
  private final EmailSchedulerService emailSchedulerService;
  private final TemplateEngine templateEngine;
  private final JavaMailSender javaMailSender;

  @Async
  @Scheduled(fixedRate = 30000, initialDelay = 120000)
  public void sendEmailsFromQueue() {
    log.trace("Fetching unsent emails...");
    emailSchedulerService.getAllValidUnsentEmails().forEach(this::sendPendingEmail);
  }

  @Scheduled(fixedDelay = 21600000, initialDelay = 120000)
  public void removeOldEmailsFormQueue() {
    log.trace("Fetching outdated emails...");
    emailSchedulerService.removeEmails(emailSchedulerService.getAllInvalidEmails());
  }

  private void sendPendingEmail(final PendingEmailEntity emailEntity) {
    final MimeMessage email = javaMailSender.createMimeMessage();
    try {
      final Context context = new Context();
      context.setVariable("header", emailSchedulerService.getDefaultHeader());
      context.setVariable("title", emailEntity.getTopic());
      context.setVariable("description", emailEntity.getContent());
      final String body = templateEngine.process(emailEntity.getTemplate(), context);
      final MimeMessageHelper helper = getMimeMessageHelper(emailEntity, email);
      helper.setText(body, true);
      javaMailSender.send(email);
      emailSchedulerService.markEmailAsSent(emailEntity);
      log.trace("Email sent to: " + emailEntity.getRecipient());
    } catch (final MessagingException e) {
      log.error("Exception occurred when sending email", e);
    }
  }

  private MimeMessageHelper getMimeMessageHelper(
          final PendingEmailEntity emailEntity, final MimeMessage email) throws MessagingException {
    final MimeMessageHelper helper = new MimeMessageHelper(email, true);
    helper.setTo(emailEntity.getRecipient());
    final String DEFAULT_REPLY_TO_EMAIL =
            String.format("%s <EMAIL_HERE>", emailSchedulerService.getDefaultHeader());
    helper.setReplyTo(DEFAULT_REPLY_TO_EMAIL);
    final String DEFAULT_FROM_EMAIL =
            String.format("%s <EMAIL_HERE>", emailSchedulerService.getDefaultHeader());
    helper.setFrom(DEFAULT_FROM_EMAIL);
    helper.setSubject(emailEntity.getTopic());
    return helper;
  }


}
