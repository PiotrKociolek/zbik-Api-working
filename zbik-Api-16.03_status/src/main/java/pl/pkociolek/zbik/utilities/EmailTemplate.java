package pl.pkociolek.zbik.utilities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EmailTemplate {
  DEFAULT("zbik-default.html");
  private final String name;
}
