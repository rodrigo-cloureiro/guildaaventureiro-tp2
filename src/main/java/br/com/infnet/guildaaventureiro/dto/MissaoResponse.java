package br.com.infnet.guildaaventureiro.dto;

import br.com.infnet.guildaaventureiro.domain.aventura.Missao;
import br.com.infnet.guildaaventureiro.domain.aventura.enums.NivelPerigoMissao;
import br.com.infnet.guildaaventureiro.domain.aventura.enums.StatusMissao;

public record MissaoResponse(
        String titulo,
        NivelPerigoMissao nivelPerigo,
        StatusMissao status
) {
    public MissaoResponse(Missao missao) {
        this(
                missao.getTitulo(),
                missao.getNivelPerigo(),
                missao.getStatus()
        );
    }
}
