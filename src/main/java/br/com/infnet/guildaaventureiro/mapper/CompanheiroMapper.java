package br.com.infnet.guildaaventureiro.mapper;

import br.com.infnet.guildaaventureiro.domain.aventura.Companheiro;
import br.com.infnet.guildaaventureiro.dto.CompanheiroResponse;

public class CompanheiroMapper {
    public static CompanheiroResponse toResponse(Companheiro companheiro) {
        return new CompanheiroResponse(companheiro);
    }
}
