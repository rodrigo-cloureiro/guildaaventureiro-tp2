package br.com.infnet.guildaaventureiro.controller;

import br.com.infnet.guildaaventureiro.dto.relatorio.RankingParticipacao;
import br.com.infnet.guildaaventureiro.dto.relatorio.RankingPartipacaoFiltroRequest;
import br.com.infnet.guildaaventureiro.dto.relatorio.RelatorioMissao;
import br.com.infnet.guildaaventureiro.dto.relatorio.RelatorioMissaoFiltroRequest;
import br.com.infnet.guildaaventureiro.service.RelatorioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/relatorios")
public class RelatorioController {
    private final RelatorioService relatorioService;

    // =======================
    // Ranking de Participação
    // =======================
    @GetMapping(value = "/ranking-participacao")
    public ResponseEntity<List<RankingParticipacao>> rankingParticipacao(
            @Valid RankingPartipacaoFiltroRequest filtro
    ) {
        return ResponseEntity.ok()
                .body(relatorioService.rankingParticipacao(filtro));
    }

    // ====================
    // Relatório de Missões
    // ====================
    @GetMapping(value = "/missoes")
    public ResponseEntity<List<RelatorioMissao>> relatorioMissoes(@Valid RelatorioMissaoFiltroRequest filtro) {
        return ResponseEntity.ok()
                .body(relatorioService.relatorioMissoes(filtro));
    }
}
