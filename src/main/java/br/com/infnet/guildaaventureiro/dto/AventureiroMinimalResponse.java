package br.com.infnet.guildaaventureiro.dto;

import br.com.infnet.guildaaventureiro.domain.aventura.Aventureiro;
import br.com.infnet.guildaaventureiro.domain.aventura.enums.AventureiroClasse;

public record AventureiroMinimalResponse(
        Long id,
        String nome,
        AventureiroClasse classe,
        String nomeOrganizacao
) {
    public AventureiroMinimalResponse(Aventureiro aventureiro) {
        this(
                aventureiro.getId(),
                aventureiro.getNome(),
                aventureiro.getClasse(),
                aventureiro.getOrganizacao().getNome()
        );
    }
}
