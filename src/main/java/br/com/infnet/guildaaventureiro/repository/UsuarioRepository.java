package br.com.infnet.guildaaventureiro.repository;

import br.com.infnet.guildaaventureiro.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query(value = """
             SELECT u\s
             FROM Usuario u\s
             JOIN u.organizacao o
             WHERE UPPER(o.nome) = UPPER(:organizacaoNome)
            \s""")
    List<Usuario> findByOrganizacaoNomeIgnoreCase(@Param("organizacaoNome") String organizacaoNome);

    Optional<Usuario> findByEmailIgnoreCase(String email);
}
