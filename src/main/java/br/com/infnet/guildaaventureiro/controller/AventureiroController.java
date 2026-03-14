package br.com.infnet.guildaaventureiro.controller;

import br.com.infnet.guildaaventureiro.dto.*;
import br.com.infnet.guildaaventureiro.service.AventureiroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/aventureiro")
public class AventureiroController {
    private final AventureiroService aventureiroService;

    // ===================
    // Listar Aventureiros
    // ===================
    @GetMapping(value = "")
    public ResponseEntity<List<AventureiroResponse>> listarAventureiros(
            @Valid AventureiroFiltroRequest filtro,
            @PageableDefault(page = 0, size = 10) Pageable pageable
    ) {
        Page<AventureiroResponse> aventureirosPage = aventureiroService.listar(filtro, pageable);
        return ResponseEntity.ok()
                .header("X-Page", String.valueOf(aventureirosPage.getNumber()))
                .header("X-Size", String.valueOf(aventureirosPage.getSize()))
                .header("X-Total-Count", String.valueOf(aventureirosPage.getNumberOfElements()))
                .header("X-Total-Pages", String.valueOf(aventureirosPage.getTotalPages()))
                .body(aventureirosPage.getContent());
    }

    // =========================
    // Buscar Aventureiro por ID
    // =========================
    @GetMapping(value = "/{id}")
    public ResponseEntity<AventureiroProfileResponse> perfilAventureiro(@PathVariable Long id) {
        return ResponseEntity.ok()
                .body(aventureiroService.perfil(id));
    }

    // ===========================
    // Buscar Aventureiro por Nome
    // ===========================
    @GetMapping(value = "/q")
    public ResponseEntity<PagedResponse<AventureiroResponse>> listarAventureirosPorNome(
            @RequestParam(value = "nome") String nome,
            @PageableDefault(page = 0, size = 10) Pageable pageable
    ) {
        PagedResponse<AventureiroResponse> pagedResponse = aventureiroService.buscarPorNome(nome, pageable);
        return ResponseEntity.ok()
                .header("X-Page", String.valueOf(pagedResponse.page()))
                .header("X-Size", String.valueOf(pagedResponse.size()))
                .header("X-Total-Count", String.valueOf(pagedResponse.total()))
                .header("X-Total-Pages", String.valueOf(pagedResponse.totalPages()))
                .body(pagedResponse);
    }

    // =====================
    // Registrar Aventureiro
    // =====================
    @PostMapping(value = "")
    public ResponseEntity<AventureiroResponse> registrarAventureiro(@RequestBody @Valid AventureiroCreate dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(aventureiroService.criar(dto));
    }
}
