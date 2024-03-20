package pl.pkociolek.zbik.configuration.properties.email;

import lombok.Data;

@Data
public class EmailSmtpSettings {
  private Boolean auth;
  private Boolean enableSsl;
  private Boolean startSslEnable;
  private Boolean startSslRequired;
}
