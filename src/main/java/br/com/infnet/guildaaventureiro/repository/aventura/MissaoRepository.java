package br.com.infnet.guildaaventureiro.repository.aventura;

import br.com.infnet.guildaaventureiro.domain.aventura.Missao;
import br.com.infnet.guildaaventureiro.domain.aventura.enums.NivelPerigoMissao;
import br.com.infnet.guildaaventureiro.domain.aventura.enums.StatusMissao;
import br.com.infnet.guildaaventureiro.dto.missao.MissaoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MissaoRepository extends JpaRepository<Missao, Long> {
    @Query(value = """
            SELECT new br.com.infnet.guildaaventureiro.dto.missao.MissaoResponse(
                        m.id,
                        m.titulo,
                        m.status,
                        m.nivelPerigo,
                        m.dataCriacao,
                        m.dataInicio,
                        m.dataTermino
            )
            FROM Missao m
            WHERE (:status IS NULL OR m.status = :status) AND
            (:nivelPerigo IS NULL OR m.nivelPerigo = :nivelPerigo)
            """)
    Page<MissaoResponse> findByFilters(
            @Param(value = "status") StatusMissao status,
            @Param(value = "nivelPerigo") NivelPerigoMissao nivelPerigo,
            Pageable pageable
    );
}
