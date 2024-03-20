package pl.pkociolek.zbik.service;

public interface TokenService {
    public String generateResetToken();
    public String generateActivationToken();
    public boolean verifyResetToken(String token);
    public boolean verifyActivationToken(String token);
}
