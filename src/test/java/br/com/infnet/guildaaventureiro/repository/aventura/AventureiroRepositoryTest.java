package br.com.infnet.guildaaventureiro.repository.aventura;

import br.com.infnet.guildaaventureiro.domain.audit.Organizacao;
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

    private Organizacao organizacao;
    private Usuario usuario;
    private Aventureiro aventureiro;

    @BeforeEach
    public void setUp() {
        this.organizacao = organizacaoRepository.findById(1L).orElse(null);
        this.usuario = usuarioRepository.findById(1L).orElse(null);
        this.aventureiro = new Aventureiro(
                organizacao,
                usuario,
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
        if (this.organizacao != null) {
            this.organizacao = null;
        }
        if (this.usuario != null) {
            this.usuario = null;
        }
    }

    @Test
    public void createAventureiroTest() {
        aventureiroRepository.save(aventureiro);

        Aventureiro aventureiroDb = aventureiroRepository
                .findById(aventureiro.getId())
                .orElseThrow();

        assertEquals("Rick Forjafogo", aventureiroDb.getNome());
        assertEquals(AventureiroClasse.ARQUEIRO, aventureiroDb.getClasse());
        assertEquals(100, aventureiroDb.getNivel());
        assertTrue(aventureiroDb.isAtivo());
    }

    @Test
    public void setCompanheiroDoAventureiroTest() {
        Companheiro companheiro = new Companheiro(
                "Fenrir",
                CompanheiroEspecie.LOBO,
                100
        );

        aventureiro.definirCompanheiro(companheiro);
        aventureiroRepository.save(aventureiro);

        Aventureiro aventureiroDb = aventureiroRepository
                .findById(aventureiro.getId())
                .orElseThrow();

        assertNotNull(aventureiroDb.getCompanheiro());
        assertEquals("Fenrir", aventureiroDb.getCompanheiro().getNome());
    }
}
