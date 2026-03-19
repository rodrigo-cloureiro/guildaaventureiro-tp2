package br.com.infnet.guildaaventureiro.dto.missao;

import jakarta.validation.constraints.Min;

public record RecompensarParticipante(
        @Min(value = '1', message = "A recompensa deve ser um valor positivo")
        int recompensa
) {
}
