package br.com.infnet.guildaaventureiro.service;

import br.com.infnet.guildaaventureiro.dto.PagedResponse;
import br.com.infnet.guildaaventureiro.dto.missao.MissaoFiltroRequest;
import br.com.infnet.guildaaventureiro.dto.missao.MissaoResponse;
import br.com.infnet.guildaaventureiro.repository.aventura.MissaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MissaoService {
    private final MissaoRepository missaoRepository;

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
}
