package pl.pkociolek.zbik.controlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pkociolek.zbik.repository.entity.PendingEmailEntity;
import pl.pkociolek.zbik.service.EmailSchedulerService;
import pl.pkociolek.zbik.utilities.EmailTemplate;

import java.util.List;

@RestController
@RequestMapping("/emails")
public class EmailSchedulerController {

       final EmailSchedulerService emailSchedulerService;

    public EmailSchedulerController(EmailSchedulerService emailSchedulerService) {
        this.emailSchedulerService = emailSchedulerService;
    }

    @GetMapping("/valid-unsent")
    public ResponseEntity<List<PendingEmailEntity>> getAllValidUnsentEmails() {
        List<PendingEmailEntity> emails = emailSchedulerService.getAllValidUnsentEmails();
        return ResponseEntity.ok(emails);
    }

    @GetMapping("/invalid")
    public ResponseEntity<List<PendingEmailEntity>> getAllInvalidEmails() {
        List<PendingEmailEntity> emails = emailSchedulerService.getAllInvalidEmails();
        return ResponseEntity.ok(emails);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeEmails(@RequestBody List<PendingEmailEntity> emailEntities) {
        emailSchedulerService.removeEmails(emailEntities);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/send")
    public ResponseEntity<Void> sendEmail(
            @RequestParam String userId,
            @RequestParam String topic,
            @RequestParam String content,
            @RequestParam(required = false) String header,
            @RequestParam(required = false) String template) {

        if (header == null) {
            header = emailSchedulerService.getDefaultHeader();
        }

        emailSchedulerService.sendEmail(userId, topic, content, header, EmailTemplate.DEFAULT);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
