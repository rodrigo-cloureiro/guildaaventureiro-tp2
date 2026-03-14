package br.com.infnet.guildaaventureiro.service;

import br.com.infnet.guildaaventureiro.domain.audit.Organizacao;
import br.com.infnet.guildaaventureiro.domain.audit.Usuario;
import br.com.infnet.guildaaventureiro.domain.aventura.Aventureiro;
import br.com.infnet.guildaaventureiro.domain.aventura.Missao;
import br.com.infnet.guildaaventureiro.domain.aventura.ParticipacaoMissao;
import br.com.infnet.guildaaventureiro.dto.AventureiroCreate;
import br.com.infnet.guildaaventureiro.dto.AventureiroFiltroRequest;
import br.com.infnet.guildaaventureiro.dto.AventureiroProfileResponse;
import br.com.infnet.guildaaventureiro.dto.AventureiroResponse;
import br.com.infnet.guildaaventureiro.mapper.AventureiroMapper;
import br.com.infnet.guildaaventureiro.repository.audit.UsuarioRepository;
import br.com.infnet.guildaaventureiro.repository.aventura.AventureiroRepository;
import br.com.infnet.guildaaventureiro.repository.aventura.ParticipacaoMissaoRepository;
import jakarta.persistence.EntityNotFoundException;
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
    private final ParticipacaoMissaoRepository participacaoMissaoRepository;

    // ===================
    // Listar Aventureiros
    // ===================
    public Page<AventureiroResponse> listar(
            AventureiroFiltroRequest filtro,
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

    // =========================
    // Buscar Aventureiro por ID
    // =========================
    @Transactional(readOnly = true)
    public AventureiroProfileResponse perfil(Long id) {
        Aventureiro aventureiro = aventureiroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aventureiro não encontrado"));

        long totalParticipacoes = participacaoMissaoRepository.countByAventureiroId(id);

        ParticipacaoMissao ultimaParticipacao = participacaoMissaoRepository
                .findTopByAventureiroIdOrderByDataRegistroDesc(id)
                .orElse(null);

        Missao ultimaMissao = ultimaParticipacao != null ? ultimaParticipacao.getMissao() : null;

        return AventureiroMapper.toProfileResponse(aventureiro, totalParticipacoes, ultimaMissao);
    }

    // =====================
    // Registrar Aventureiro
    // =====================
    @Transactional(readOnly = false)
    public AventureiroResponse criar(AventureiroCreate dto) {
        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(); // TODO Implementar exceção
        Organizacao organizacao = usuario.getOrganizacao();

        Aventureiro aventureiro = AventureiroMapper.toEntity(dto);
        usuario.adicionarAventureiro(aventureiro);
        organizacao.adicionarAventureiro(aventureiro);

        return AventureiroMapper.toResponse(aventureiroRepository.save(aventureiro));
    }
}
