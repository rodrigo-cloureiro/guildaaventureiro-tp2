package br.com.infnet.guildaaventureiro.mapper;

import br.com.infnet.guildaaventureiro.domain.aventura.Aventureiro;
import br.com.infnet.guildaaventureiro.dto.AventureiroCreateDto;
import br.com.infnet.guildaaventureiro.dto.AventureiroResponseDto;
import org.springframework.stereotype.Component;

@Component
public class AventureiroMapper {
    public static Aventureiro toEntity(AventureiroCreateDto dto) {
        return new Aventureiro(
                dto.nome(),
                dto.classe(),
                dto.nivel()
        );
    }

    public static AventureiroResponseDto toResponse(Aventureiro aventureiro) {
        return new AventureiroResponseDto(
                aventureiro.getId(),
                aventureiro.getNome(),
                aventureiro.getClasse(),
                aventureiro.getNivel(),
                aventureiro.isAtivo()
        );
    }
}
