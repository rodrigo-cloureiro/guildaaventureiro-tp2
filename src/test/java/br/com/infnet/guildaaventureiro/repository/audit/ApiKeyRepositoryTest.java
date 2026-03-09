package br.com.infnet.guildaaventureiro.repository.audit;

import br.com.infnet.guildaaventureiro.domain.audit.ApiKey;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ApiKeyRepositoryTest {
    @Autowired
    private ApiKeyRepository apiKeyRepository;

    @Test
    public void findAllTest() {
        List<ApiKey> all = apiKeyRepository.findAll();
        all.forEach(apiKey -> {
            System.out.println(apiKey.getId());
            System.out.println(apiKey.getNome());
            System.out.println(apiKey.getKeyHash());
            System.out.println(apiKey.isAtivo());
            System.out.println(apiKey.getCreatedAt());
            System.out.println(apiKey.getLastUsedAt());
            System.out.println(apiKey.getOrganizacao());
            System.out.println("#################################");
        });
        assertFalse(all.isEmpty());
    }
}
