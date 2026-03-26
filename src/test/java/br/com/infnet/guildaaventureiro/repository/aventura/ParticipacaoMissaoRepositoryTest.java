package br.com.infnet.guildaaventureiro.repository.aventura;

import br.com.infnet.guildaaventureiro.domain.aventura.Missao;
import br.com.infnet.guildaaventureiro.domain.aventura.enums.StatusMissao;
import br.com.infnet.guildaaventureiro.dto.aventureiro.AventureiroMissaoResponse;
import br.com.infnet.guildaaventureiro.dto.relatorio.RankingParticipacao;

import br.com.infnet.guildaaventureiro.dto.relatorio.RelatorioMissao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ParticipacaoMissaoRepositoryTest {
    @Autowired
    private ParticipacaoMissaoRepository participacaoMissaoRepository;
    @Autowired
    private MissaoRepository missaoRepository;

    @Test
    @DisplayName("Deve retornar ranking quando há participações no período")
    public void shouldReturnRankingWhenThereAreParticipationsInThePeriod() {
        List<RankingParticipacao> resultado = participacaoMissaoRepository.rankingParticipacao(
                null,
                LocalDateTime.now().minusDays(30),
                LocalDateTime.now()
        );
        assertFalse(resultado.isEmpty());
    }

    @Test
    @DisplayName("Deve retornar ranking quando há participações no período apenas de missões em andamento")
    public void shouldReturnRankingWhenThereAreParticipationsOnlyForMissionsInProgress() {
        List<RankingParticipacao> resultado = participacaoMissaoRepository.rankingParticipacao(
                StatusMissao.EM_ANDAMENTO,
                LocalDateTime.now().minusDays(30),
                LocalDateTime.now()
        );
        assertFalse(resultado.isEmpty());
    }

    @Test
    @DisplayName("Deve retornar relatório com período válido")
    public void shouldReturnReport() {
        List<RelatorioMissao> resultado = participacaoMissaoRepository.relatorioMissoes(
                LocalDateTime.now().minusDays(30),
                LocalDateTime.now()
        );
        assertFalse(resultado.isEmpty());
        resultado.forEach(r -> assertTrue(r.quantidadeParticipantes() >= 1));
    }

    @Test
    @DisplayName("Deve retornar a missão e seus participantes")
    public void shouldReturnParticipantsWhenMissionHasParticipants() {
        Missao missao = missaoRepository.findById(1L).orElseThrow();
        List<AventureiroMissaoResponse> participantes = participacaoMissaoRepository
                .findParticipantesByMissaoId(1L);

        assertNotNull(missao);
        assertFalse(participantes.isEmpty());
    }

    @Test
    @DisplayName("Deve retornar a missão corretamente e lista vazia de participantes quando não há participantes")
    public void shouldReturnEmptyListWhenMissionHasNoParticipants() {
        Missao missao = missaoRepository.findById(12L).orElseThrow();
        List<AventureiroMissaoResponse> participantes = participacaoMissaoRepository
                .findParticipantesByMissaoId(12L);

        assertNotNull(missao);
        assertTrue(participantes.isEmpty());
    }
}
