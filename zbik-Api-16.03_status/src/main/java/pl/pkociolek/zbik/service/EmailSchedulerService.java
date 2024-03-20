package pl.pkociolek.zbik.service;

import java.util.List;
import pl.pkociolek.zbik.repository.entity.PendingEmailEntity;
import pl.pkociolek.zbik.utilities.EmailTemplate;

public interface EmailSchedulerService {
  String getDefaultHeader();

  void sendEmail(
          String userId, String topic, String content, String header, EmailTemplate template);

  void sendEmail(String userId, String topic, String content, EmailTemplate template);

  void sendEmail(String userId, String topic, String content);

  List<PendingEmailEntity> getAllValidUnsentEmails();

  List<PendingEmailEntity> getAllInvalidEmails();

  void removeEmails(List<PendingEmailEntity> emailEntities);

  void markEmailAsSent(PendingEmailEntity emailEntity);
}
