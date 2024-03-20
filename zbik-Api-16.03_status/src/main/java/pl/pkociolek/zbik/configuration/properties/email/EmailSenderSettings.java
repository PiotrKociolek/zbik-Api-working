package pl.pkociolek.zbik.configuration.properties.email;

import lombok.Data;

@Data
public class EmailSenderSettings {
  private String host;
  private Integer port;
  private String username;
  private String password;
  private String defaultEncoding;
}
