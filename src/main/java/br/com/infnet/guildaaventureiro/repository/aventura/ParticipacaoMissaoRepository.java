package br.com.infnet.guildaaventureiro.repository.aventura;

import br.com.infnet.guildaaventureiro.domain.aventura.ParticipacaoMissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipacaoMissaoRepository extends JpaRepository<ParticipacaoMissao, Long> {
}
