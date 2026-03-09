package br.com.infnet.guildaaventureiro.repository.aventura;

import br.com.infnet.guildaaventureiro.domain.audit.Usuario;
import br.com.infnet.guildaaventureiro.domain.aventura.Aventureiro;
import br.com.infnet.guildaaventureiro.domain.aventura.Companheiro;
import br.com.infnet.guildaaventureiro.domain.aventura.enums.AventureiroClasse;
import br.com.infnet.guildaaventureiro.domain.aventura.enums.CompanheiroEspecie;
import br.com.infnet.guildaaventureiro.repository.audit.OrganizacaoRepository;
import br.com.infnet.guildaaventureiro.repository.audit.UsuarioRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

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

    private Aventureiro aventureiro;

    @BeforeEach
    public void setUp() {
        this.aventureiro = new Aventureiro(
                "Rick Forjafogo",
                AventureiroClasse.ARQUEIRO,
                100
        );
    }

    @AfterEach
    public void tearDown() {
        if (this.aventureiro != null) {
            this.aventureiro = null;
        }
    }

    @Test
    public void createAventureiroTest() {
        Usuario usuario = usuarioRepository.findById(1L).orElseThrow();
        this.aventureiro.registrar(usuario);

        assertNotNull(this.aventureiro.getNome());
        assertEquals(100, this.aventureiro.getNivel());
        assertNotNull(this.aventureiro.getClasse());
        assertNotNull(this.aventureiro.getOrganizacao().getNome());
        assertNotNull(this.aventureiro.getUsuario().getNome());
    }

    @Test
    public void setCompanheiroDoAventureiroTest() {
        Usuario usuario = usuarioRepository.findById(1L).orElseThrow();
        this.aventureiro.registrar(usuario);
        this.aventureiroRepository.save(this.aventureiro);

        Companheiro companheiro = new Companheiro(
                "Fenrir",
                CompanheiroEspecie.LOBO,
                100
        );
        this.aventureiro.definirCompanheiro(companheiro);

        this.aventureiroRepository.save(this.aventureiro);

        Aventureiro aventureiroInDb = this.aventureiroRepository.findById(this.aventureiro.getId())
                .orElseThrow();

        assertNotNull(aventureiroInDb.getCompanheiro());
        assertEquals("Fenrir", aventureiroInDb.getCompanheiro().getNome());
        assertEquals(CompanheiroEspecie.LOBO, aventureiroInDb.getCompanheiro().getEspecie());
        assertEquals(100, aventureiroInDb.getCompanheiro().getLealdade());
    }
}
