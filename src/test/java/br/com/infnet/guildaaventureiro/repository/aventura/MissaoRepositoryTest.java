package br.com.infnet.guildaaventureiro.repository.aventura;

import br.com.infnet.guildaaventureiro.domain.audit.Usuario;
import br.com.infnet.guildaaventureiro.domain.aventura.Aventureiro;
import br.com.infnet.guildaaventureiro.domain.aventura.Missao;
import br.com.infnet.guildaaventureiro.domain.aventura.enums.AventureiroClasse;
import br.com.infnet.guildaaventureiro.domain.aventura.enums.NivelPerigoMissao;
import br.com.infnet.guildaaventureiro.domain.aventura.enums.PapelMissao;
import br.com.infnet.guildaaventureiro.repository.audit.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MissaoRepositoryTest {
    @Autowired
    private MissaoRepository missaoRepository;
    @Autowired
    private AventureiroRepository aventureiroRepository;
    @Autowired
    private ParticipacaoMissaoRepository participacaoMissaoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;
    private Aventureiro aventureiro;

    @BeforeEach
    public void setUp() {
        this.usuario = usuarioRepository.findById(1L)
                .orElseThrow();
        this.aventureiro = new Aventureiro("Rodrigo", AventureiroClasse.GUERREIRO, 100);
        this.usuario.adicionarAventureiro(this.aventureiro);
        this.usuario.getOrganizacao().adicionarAventureiro(this.aventureiro);
    }

    @Test
    public void createMissaoTest() {
        aventureiroRepository.save(aventureiro);

        Missao missao = new Missao(
                usuario.getOrganizacao(),
                "Finalizar o TP2",
                NivelPerigoMissao.CRITICO
        );
        missao.adicionarAventureiro(
                aventureiro,
                PapelMissao.EXPLORADOR,
                1_000
        );
        missaoRepository.save(missao);

        assertFalse(missaoRepository.findAll().isEmpty());
        assertFalse(participacaoMissaoRepository.findAll().isEmpty());
    }
}
