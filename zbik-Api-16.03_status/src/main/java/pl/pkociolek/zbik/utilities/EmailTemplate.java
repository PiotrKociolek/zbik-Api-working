package pl.pkociolek.zbik.utilities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EmailTemplate {
  RESET_TEMPLATE("zbik-default.html"),
  ACTIVATE_TEMPLATE("zbik-activate.html");
  private final String name;
}
