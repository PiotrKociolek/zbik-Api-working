package pl.pkociolek.zbik.configuration;

import lombok.RequiredArgsConstructor;
import pl.pkociolek.zbik.repository.RoleRepository;
import pl.pkociolek.zbik.repository.UserRepository;

@RequiredArgsConstructor
public class DevDatabaseConfiguration {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public void initialize() {}
}
