package br.com.infnet.guildaaventureiro.repository;

import br.com.infnet.guildaaventureiro.domain.Organizacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizacaoRepository extends JpaRepository<Organizacao, Long> {
}
