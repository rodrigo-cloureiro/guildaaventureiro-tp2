package br.com.infnet.guildaaventureiro.dto;

import br.com.infnet.guildaaventureiro.domain.aventura.enums.AventureiroClasse;
import br.com.infnet.guildaaventureiro.domain.aventura.enums.PapelMissao;

public record AventureiroMissaoResponse(
        String nome,
        AventureiroClasse classe,
        int nivel,
        PapelMissao papelNaMissao,
        Integer valorRecompensaRecebida,
        Boolean destaque
) {
}
