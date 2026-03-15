package br.com.infnet.guildaaventureiro.mapper;

import br.com.infnet.guildaaventureiro.domain.aventura.Missao;
import br.com.infnet.guildaaventureiro.dto.missao.MissaoMinimalResponse;
import br.com.infnet.guildaaventureiro.dto.missao.MissaoResponse;

public class MissaoMapper {
    public static MissaoMinimalResponse toMinimalResponse(Missao missao) {
        return new MissaoMinimalResponse(missao);
    }

    public static MissaoResponse toResponse(Missao missao) {
        return new MissaoResponse(missao);
    }
}
