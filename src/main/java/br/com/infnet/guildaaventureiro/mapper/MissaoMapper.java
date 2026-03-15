package br.com.infnet.guildaaventureiro.mapper;

import br.com.infnet.guildaaventureiro.domain.aventura.Missao;
import br.com.infnet.guildaaventureiro.dto.missao.MissaoMinimalResponse;

public class MissaoMapper {
    public static MissaoMinimalResponse toResponse(Missao missao) {
        return new MissaoMinimalResponse(missao);
    }
}
