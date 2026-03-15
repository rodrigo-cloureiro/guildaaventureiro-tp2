package br.com.infnet.guildaaventureiro.dto.missao;

import br.com.infnet.guildaaventureiro.domain.aventura.Missao;
import br.com.infnet.guildaaventureiro.domain.aventura.enums.NivelPerigoMissao;
import br.com.infnet.guildaaventureiro.domain.aventura.enums.StatusMissao;

public record MissaoMinimalResponse(
        String titulo,
        NivelPerigoMissao nivelPerigo,
        StatusMissao status
) {
    public MissaoMinimalResponse(Missao missao) {
        this(
                missao.getTitulo(),
                missao.getNivelPerigo(),
                missao.getStatus()
        );
    }
}
