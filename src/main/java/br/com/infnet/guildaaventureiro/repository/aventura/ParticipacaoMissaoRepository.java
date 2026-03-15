package br.com.infnet.guildaaventureiro.repository.aventura;

import br.com.infnet.guildaaventureiro.domain.aventura.ParticipacaoMissao;
import br.com.infnet.guildaaventureiro.dto.AventureiroMissaoResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipacaoMissaoRepository extends JpaRepository<ParticipacaoMissao, Long> {
    long countByAventureiroId(Long id);

    Optional<ParticipacaoMissao> findTopByAventureiroIdOrderByDataRegistroDesc(Long id);

    @Query(value = """
            SELECT new br.com.infnet.guildaaventureiro.dto.AventureiroMissaoResponse(
                        a.nome,
                        a.classe,
                        a.nivel,
                        pm.papel,
                        pm.recompensaEmOuro,
                        pm.mvp
            )
            FROM ParticipacaoMissao pm
            JOIN pm.aventureiro a
            WHERE pm.missao.id = :id
            """)
    List<AventureiroMissaoResponse> findParticipantesByMissaoId(@Param(value = "id") Long id);
}
