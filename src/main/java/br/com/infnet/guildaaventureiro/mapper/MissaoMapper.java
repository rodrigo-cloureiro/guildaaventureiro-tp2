package br.com.infnet.guildaaventureiro.mapper;

import br.com.infnet.guildaaventureiro.domain.aventura.Missao;
import br.com.infnet.guildaaventureiro.dto.MissaoResponse;

public class MissaoMapper {
    public static MissaoResponse toResponse(Missao missao) {
        return new MissaoResponse(missao);
    }
}
