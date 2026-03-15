package br.com.infnet.guildaaventureiro.service;

import br.com.infnet.guildaaventureiro.domain.aventura.Missao;
import br.com.infnet.guildaaventureiro.dto.AventureiroMissaoResponse;
import br.com.infnet.guildaaventureiro.dto.PagedResponse;
import br.com.infnet.guildaaventureiro.dto.missao.MissaoDetailedResponse;
import br.com.infnet.guildaaventureiro.dto.missao.MissaoFiltroRequest;
import br.com.infnet.guildaaventureiro.dto.missao.MissaoResponse;
import br.com.infnet.guildaaventureiro.mapper.MissaoMapper;
import br.com.infnet.guildaaventureiro.repository.aventura.MissaoRepository;
import br.com.infnet.guildaaventureiro.repository.aventura.ParticipacaoMissaoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MissaoService {
    private final MissaoRepository missaoRepository;
    private final ParticipacaoMissaoRepository participacaoMissaoRepository;

    // ==============
    // Listar Missões
    // ==============
    public PagedResponse<MissaoResponse> listar(MissaoFiltroRequest filtro, Pageable pageable) {
        Page<MissaoResponse> responsePage = missaoRepository.findByFilters(
                filtro.status(),
                filtro.nivelPerigo(),
                pageable
        );
        return new PagedResponse<>(
                responsePage.getNumber(),
                responsePage.getSize(),
                responsePage.getTotalElements(),
                responsePage.getTotalPages(),
                responsePage.getContent()
        );
    }

    // ====================
    // Buscar Missão por ID
    // ====================
    @Transactional(readOnly = true)
    public MissaoDetailedResponse missaoDetalhada(Long id) {
        Missao missao = missaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Missão não encontrada"));

        List<AventureiroMissaoResponse> participantes = participacaoMissaoRepository.findParticipantesByMissaoId(id);

        return new MissaoDetailedResponse(MissaoMapper.toResponse(missao), participantes);
    }
}
