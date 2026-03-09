package br.com.infnet.guildaaventureiro.repository.audit;

import br.com.infnet.guildaaventureiro.domain.audit.AuditEntry;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuditEntryRepositoryTest {
    @Autowired
    private AuditEntryRepository auditEntryRepository;

    @Test
    public void findAllTest() {
        List<AuditEntry> all = auditEntryRepository.findAll();
        all.forEach(System.out::println);
        assertFalse(all.isEmpty());
    }
}
