package br.com.infnet.guildaaventureiro.service;

import br.com.infnet.guildaaventureiro.domain.audit.Organizacao;
import br.com.infnet.guildaaventureiro.domain.audit.Usuario;
import br.com.infnet.guildaaventureiro.domain.aventura.Aventureiro;
import br.com.infnet.guildaaventureiro.dto.AventureiroCreateDto;
import br.com.infnet.guildaaventureiro.dto.AventureiroFiltroRequestDto;
import br.com.infnet.guildaaventureiro.dto.AventureiroResponseDto;
import br.com.infnet.guildaaventureiro.mapper.AventureiroMapper;
import br.com.infnet.guildaaventureiro.repository.audit.UsuarioRepository;
import br.com.infnet.guildaaventureiro.repository.aventura.AventureiroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AventureiroService {
    private final AventureiroRepository aventureiroRepository;
    private final UsuarioRepository usuarioRepository;

    // ===================
    // Listar Aventureiros
    // ===================
    public Page<AventureiroResponseDto> listar(
            AventureiroFiltroRequestDto filtro,
            int pageNumber,
            int pageSize,
            Sort sort
    ) {
        Pageable pageable = PageRequest.of(
                pageNumber,
                pageSize,
                sort
        );
        return aventureiroRepository.findByStatusAndClasseAndNivelMinimo(
                filtro.status(),
                filtro.classe(),
                filtro.nivelMinimo(),
                pageable
        );
    }

    // =====================
    // Registrar Aventureiro
    // =====================
    @Transactional(readOnly = false)
    public AventureiroResponseDto criar(AventureiroCreateDto dto) {
        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(); // TODO Implementar exceção
        Organizacao organizacao = usuario.getOrganizacao();

        Aventureiro aventureiro = AventureiroMapper.toEntity(dto);
        usuario.adicionarAventureiro(aventureiro);
        organizacao.adicionarAventureiro(aventureiro);

        return AventureiroMapper.toResponse(aventureiroRepository.save(aventureiro));
    }
}
