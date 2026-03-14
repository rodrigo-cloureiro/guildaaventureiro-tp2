package br.com.infnet.guildaaventureiro.repository.aventura;

import br.com.infnet.guildaaventureiro.domain.aventura.Aventureiro;
import br.com.infnet.guildaaventureiro.domain.aventura.enums.AventureiroClasse;
import br.com.infnet.guildaaventureiro.dto.AventureiroResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AventureiroRepository extends JpaRepository<Aventureiro, Long> {
    @Query(value = """
            SELECT new br.com.infnet.guildaaventureiro.dto.AventureiroResponse(
                        a.id, a.nome, a.classe, a.nivel, a.ativo
            )
            FROM Aventureiro a
            WHERE (:status IS NULL OR a.ativo = :status) AND
            (:classe IS NULL OR a.classe = :classe) AND
            (:nivelMinimo IS NULL OR a.nivel >= :nivelMinimo)
            """)
    Page<AventureiroResponse> findByStatusAndClasseAndNivelMinimo(
            @Param(value = "status") Boolean status,
            @Param(value = "classe") AventureiroClasse classe,
            @Param(value = "nivelMinimo") Integer nivelMinimoInteger,
            Pageable pageable
    );

    Page<AventureiroResponse> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}
