package br.com.infnet.guildaaventureiro.dto.missao;

import br.com.infnet.guildaaventureiro.domain.aventura.enums.NivelPerigoMissao;
import br.com.infnet.guildaaventureiro.domain.aventura.enums.StatusMissao;

public record MissaoFiltroRequest(
        StatusMissao status,
        NivelPerigoMissao nivelPerigo
) {
}
