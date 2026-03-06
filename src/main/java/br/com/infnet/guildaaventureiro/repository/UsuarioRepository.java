package br.com.infnet.guildaaventureiro.repository;

import br.com.infnet.guildaaventureiro.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query(value = """
            SELECT u\s
            FROM Usuario u\s
            JOIN u.organizacao o
            WHERE UPPER(o.nome) = UPPER(:organizacaoName)
           \s""")
    List<Usuario> findByOrganizacaoNomeIgnoreCase(String organizacaoName);
}
