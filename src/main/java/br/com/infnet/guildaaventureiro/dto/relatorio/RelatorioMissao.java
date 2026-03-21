package br.com.infnet.guildaaventureiro.dto.relatorio;

import br.com.infnet.guildaaventureiro.domain.aventura.enums.NivelPerigoMissao;
import br.com.infnet.guildaaventureiro.domain.aventura.enums.StatusMissao;

public record RelatorioMissao(
        String titulo,
        StatusMissao status,
        NivelPerigoMissao nivelPerigo,
        Long quantidadeParticipantes,
        Long totalRecompensaDistribuida
) {
}
