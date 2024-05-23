package pl.pkociolek.zbik.service.impl;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.pkociolek.zbik.repository.PendingEmailRepository;
import pl.pkociolek.zbik.repository.entity.PendingEmailEntity;
import pl.pkociolek.zbik.service.EmailSchedulerService;
import pl.pkociolek.zbik.service.UserService;
import pl.pkociolek.zbik.utilities.EmailTemplate;

@Slf4j
@Service

@RequiredArgsConstructor
class EmailSchedulerServiceImpl implements EmailSchedulerService {
  private final PendingEmailRepository emailRepository;
  private final UserService userService;
  private final String DEFAULT_HEADER = "Koło Łowieckie Żbik";

  @Override
  public String getDefaultHeader() {
    return DEFAULT_HEADER;
  }

  @Override
  public void sendEmail(
          final String userId,
          final String topic,
          final String content,
          final String header,
          final EmailTemplate template) {
    addEmailToSenderQueue(userId, topic, content, header, template);
  }

  @Override
  public void sendEmail(
          final String userId, final String topic, final String content, final EmailTemplate template) {
    addEmailToSenderQueue(userId, topic, content, DEFAULT_HEADER, template);
  }

  @Override
  public void sendEmail(final String userId, final String topic, final String content) {
    addEmailToSenderQueue(userId, topic, content, DEFAULT_HEADER, EmailTemplate.RESET_TEMPLATE);
  }

  @Override
  public List<PendingEmailEntity> getAllValidUnsentEmails() {
    return emailRepository.findAllByAlreadySent(false).stream()
            .filter(
                    email ->
                            email
                                    .getCreatingDateTime()
                                    .isBefore(email.getCreatingDateTime().plus(1, ChronoUnit.DAYS)))
            .toList();
  }

  @Override
  public List<PendingEmailEntity> getAllInvalidEmails() {
    return emailRepository.findAll().stream()
            .filter(
                    email ->
                            email
                                    .getCreatingDateTime()
                                    .isAfter(email.getCreatingDateTime().plus(1, ChronoUnit.DAYS)))
            .toList();
  }

  @Override
  public void removeEmails(final List<PendingEmailEntity> emailEntities) {
    emailRepository.deleteAll(emailEntities);
  }

  @Override
  public void markEmailAsSent(final PendingEmailEntity emailEntity) {
    emailEntity.setAlreadySent(true);
    emailRepository.save(emailEntity);
  }

  private void addEmailToSenderQueue(
          final String userId,
          final String topic,
          final String content,
          final String header,
          final EmailTemplate template) {
    final String userEmail = userService.getUserById(userId).getEmail();
    final PendingEmailEntity emailEntity = new PendingEmailEntity();
    emailEntity.setId(null);
    emailEntity.setRecipient(userEmail);
    emailEntity.setHeader(header);
    emailEntity.setContent(content);
    emailEntity.setTopic(topic);
    emailEntity.setTemplate(template.getName());
    emailEntity.setCreatingDateTime(Instant.now());
    emailEntity.setAlreadySent(false);
    emailRepository.save(emailEntity);

    log.trace(
            "Adding email to queue: userId="
                    + userId
                    + ", topic="
                    + topic
                    + ", content="
                    + content
                    + ", header="
                    + header
                    + ", template="
                    + template.getName());
  }
}
