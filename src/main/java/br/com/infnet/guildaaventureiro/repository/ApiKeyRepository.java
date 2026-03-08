package br.com.infnet.guildaaventureiro.repository;

import br.com.infnet.guildaaventureiro.domain.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {
}
