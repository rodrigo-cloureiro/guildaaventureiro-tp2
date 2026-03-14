package br.com.infnet.guildaaventureiro.controller;

import br.com.infnet.guildaaventureiro.dto.AventureiroCreateDto;
import br.com.infnet.guildaaventureiro.dto.AventureiroFiltroRequestDto;
import br.com.infnet.guildaaventureiro.dto.AventureiroProfileResponse;
import br.com.infnet.guildaaventureiro.dto.AventureiroResponseDto;
import br.com.infnet.guildaaventureiro.service.AventureiroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
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
    public ResponseEntity<List<AventureiroResponseDto>> listarAventureiros(
            @Valid AventureiroFiltroRequestDto filtro,
            @RequestHeader(value = "X-Page", required = false, defaultValue = "0")
            int page,
            @RequestHeader(value = "X-Size", required = false, defaultValue = "10")
            int size,
            @RequestHeader(value = "X-Sort-By", required = false, defaultValue = "id")
            String sortBy,
            @RequestHeader(value = "X-Sort-Direction", required = false, defaultValue = "ASC")
            String sortDirection
    ) {
        Sort sort = Sort.by(
                sortDirection.equalsIgnoreCase("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC,
                sortBy
        );
        Page<AventureiroResponseDto> aventureiroPage = aventureiroService.listar(filtro, page, size, sort);
        return ResponseEntity.ok()
                .header("X-Page", String.valueOf(aventureiroPage.getNumber()))
                .header("X-Size", String.valueOf(aventureiroPage.getSize()))
                .header("X-Total-Count", String.valueOf(aventureiroPage.getNumberOfElements()))
                .header("X-Total-Pages", String.valueOf(aventureiroPage.getTotalPages()))
                .body(aventureiroPage.getContent());
    }

    // =========================
    // Buscar Aventureiro por ID
    // =========================
    @GetMapping(value = "/{id}")
    public ResponseEntity<AventureiroProfileResponse> perfilAventureiro(@PathVariable Long id) {
        return ResponseEntity.ok()
                .body(aventureiroService.perfil(id));
    }

    // =====================
    // Registrar Aventureiro
    // =====================
    @PostMapping(value = "")
    public ResponseEntity<AventureiroResponseDto> registrarAventureiro(@RequestBody @Valid AventureiroCreateDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(aventureiroService.criar(dto));
    }
}
