package br.com.infnet.guildaaventureiro.repository.aventura;

import br.com.infnet.guildaaventureiro.domain.Usuario;
import br.com.infnet.guildaaventureiro.domain.aventura.Aventureiro;
import br.com.infnet.guildaaventureiro.domain.aventura.enums.AventureiroClasse;
import br.com.infnet.guildaaventureiro.repository.OrganizacaoRepository;
import br.com.infnet.guildaaventureiro.repository.UsuarioRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AventureiroRepositoryTest {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private AventureiroRepository aventureiroRepository;
    @Autowired
    private OrganizacaoRepository organizacaoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void createAventureiroTest() {
        Optional<Usuario> usuario = usuarioRepository.findById(1L);
        assertTrue(usuario.isPresent());

        Aventureiro aventureiro = new Aventureiro(
                "Rick Forjafogo",
                AventureiroClasse.ARQUEIRO,
                100
        );
        aventureiro.registrar(usuario.get());

        assertNotNull(aventureiro.getNome());
        assertEquals(100, aventureiro.getNivel());
        assertNotNull(aventureiro.getClasse());
        assertNotNull(aventureiro.getOrganizacao().getNome());
        assertNotNull(aventureiro.getUsuario().getNome());
    }
}
