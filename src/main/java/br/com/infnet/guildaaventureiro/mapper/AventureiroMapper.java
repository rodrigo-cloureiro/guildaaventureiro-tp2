package br.com.infnet.guildaaventureiro.mapper;

import br.com.infnet.guildaaventureiro.domain.aventura.Aventureiro;
import br.com.infnet.guildaaventureiro.domain.aventura.Missao;
import br.com.infnet.guildaaventureiro.dto.AventureiroCreate;
import br.com.infnet.guildaaventureiro.dto.AventureiroProfileResponse;
import br.com.infnet.guildaaventureiro.dto.AventureiroResponse;
import org.springframework.stereotype.Component;

@Component
public class AventureiroMapper {
    public static Aventureiro toEntity(AventureiroCreate dto) {
        return new Aventureiro(
                dto.nome(),
                dto.classe(),
                dto.nivel()
        );
    }

    public static AventureiroResponse toResponse(Aventureiro aventureiro) {
        return new AventureiroResponse(aventureiro);
    }

    public static AventureiroProfileResponse toProfileResponse(
            Aventureiro aventureiro,
            long totalParticipacoesEmMissao,
            Missao missao
    ) {
        return new AventureiroProfileResponse(
                toResponse(aventureiro),
                aventureiro.getCompanheiro() != null ?
                        CompanheiroMapper.toResponse(aventureiro.getCompanheiro()) :
                        null,
                totalParticipacoesEmMissao,
                missao != null ? MissaoMapper.toResponse(missao) : null
        );
    }
}
