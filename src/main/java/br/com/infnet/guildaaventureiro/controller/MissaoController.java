package br.com.infnet.guildaaventureiro.controller;

import br.com.infnet.guildaaventureiro.dto.MissaoCreate;
import br.com.infnet.guildaaventureiro.dto.PagedResponse;
import br.com.infnet.guildaaventureiro.dto.missao.MissaoDetailedResponse;
import br.com.infnet.guildaaventureiro.dto.missao.MissaoFiltroRequest;
import br.com.infnet.guildaaventureiro.dto.missao.MissaoResponse;
import br.com.infnet.guildaaventureiro.service.MissaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/missoes")
public class MissaoController {
    private final MissaoService missaoService;

    // ==============
    // Listar Missões
    // ==============
    @GetMapping(value = "")
    public ResponseEntity<PagedResponse<MissaoResponse>> listarMissoes(
            @Valid MissaoFiltroRequest filtro,
            @PageableDefault(page = 0, size = 10) Pageable pageable
    ) {
        PagedResponse<MissaoResponse> pagedResponse = missaoService.listar(filtro, pageable);
        return ResponseEntity.ok()
                .header("X-Page", String.valueOf(pagedResponse.page()))
                .header("X-Size", String.valueOf(pagedResponse.size()))
                .header("X-Total-Count", String.valueOf(pagedResponse.total()))
                .header("X-Total-Pages", String.valueOf(pagedResponse.totalPages()))
                .body(pagedResponse);
    }

    // ====================
    // Buscar Missão por ID
    // ====================
    @GetMapping(value = "/{id}")
    public ResponseEntity<MissaoDetailedResponse> detalharMissao(@PathVariable Long id) {
        return ResponseEntity.ok()
                .body(missaoService.missaoDetalhada(id));
    }

    // ================
    // Registrar Missão
    // ================
    @PostMapping(value = "")
    public ResponseEntity<MissaoResponse> registrarMissao(@RequestBody @Valid MissaoCreate dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(missaoService.criar(dto));
    }
}
