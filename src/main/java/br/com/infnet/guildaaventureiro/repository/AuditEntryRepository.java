package br.com.infnet.guildaaventureiro.repository;

import br.com.infnet.guildaaventureiro.domain.AuditEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditEntryRepository extends JpaRepository<AuditEntry, Long> {
}
