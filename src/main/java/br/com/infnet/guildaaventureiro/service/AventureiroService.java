package br.com.infnet.guildaaventureiro.service;

import br.com.infnet.guildaaventureiro.domain.audit.Organizacao;
import br.com.infnet.guildaaventureiro.domain.audit.Usuario;
import br.com.infnet.guildaaventureiro.domain.aventura.Aventureiro;
import br.com.infnet.guildaaventureiro.domain.aventura.Companheiro;
import br.com.infnet.guildaaventureiro.domain.aventura.Missao;
import br.com.infnet.guildaaventureiro.domain.aventura.ParticipacaoMissao;
import br.com.infnet.guildaaventureiro.dto.*;
import br.com.infnet.guildaaventureiro.dto.aventureiro.*;
import br.com.infnet.guildaaventureiro.dto.companheiro.CompanheiroCreate;
import br.com.infnet.guildaaventureiro.exception.aventura.CompanheiroException;
import br.com.infnet.guildaaventureiro.mapper.AventureiroMapper;
import br.com.infnet.guildaaventureiro.repository.audit.UsuarioRepository;
import br.com.infnet.guildaaventureiro.repository.aventura.AventureiroRepository;
import br.com.infnet.guildaaventureiro.repository.aventura.ParticipacaoMissaoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public PagedResponse<AventureiroResponse> listar(AventureiroFiltroRequest filtro, Pageable pageable) {
        Page<AventureiroResponse> responsePage = aventureiroRepository.findByStatusAndClasseAndNivelMinimo(
                filtro.status(),
                filtro.classe(),
                filtro.nivelMinimo(),
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

    // ===========================
    // Buscar Aventureiro por Nome
    // ===========================
    @Transactional(readOnly = true)
    public PagedResponse<AventureiroMinimalResponse> buscarPorNome(String nome, Pageable pageable) {
        Page<AventureiroMinimalResponse> responsePage = aventureiroRepository
                .findByNomeContainingIgnoreCase(nome, pageable);
        return new PagedResponse<>(
                responsePage.getNumber(),
                responsePage.getSize(),
                responsePage.getTotalElements(),
                responsePage.getTotalPages(),
                responsePage.getContent()
        );
    }

    // =====================
    // Registrar Aventureiro
    // =====================
    @Transactional(readOnly = false)
    public AventureiroResponse criar(AventureiroCreate dto) {
        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        Organizacao organizacao = usuario.getOrganizacao();

        Aventureiro aventureiro = AventureiroMapper.toEntity(dto);
        usuario.adicionarAventureiro(aventureiro);
        organizacao.adicionarAventureiro(aventureiro);

        return AventureiroMapper.toResponse(aventureiroRepository.save(aventureiro));
    }

    // ================================
    // Encerrar vinculo com Aventureiro
    // ================================
    @Transactional(readOnly = false)
    public void encerrarVinculo(Long id) {
        Aventureiro aventureiro = findById(id);
        aventureiro.desativar();
    }

    // ==============================
    // Recrutar Aventureiro novamente
    // ==============================
    @Transactional(readOnly = false)
    public void recrutarNovamente(Long id) {
        Aventureiro aventureiro = findById(id);
        aventureiro.ativar();
    }

    // ==================================
    // Definir Companheiro do Aventureiro
    // ==================================
    @Transactional(readOnly = false)
    public AventureiroCompanheiroResponse definirCompanheiro(Long id, CompanheiroCreate dto) {
        Aventureiro aventureiro = findById(id);

        if (aventureiro.getCompanheiro() != null) {
            throw new CompanheiroException("O aventureiro já possui companheiro");
        }

        aventureiro.definirCompanheiro(new Companheiro(
                dto.nome(),
                dto.especie(),
                dto.lealdade()
        ));

        return AventureiroMapper.toCompanheiroResponse(
                aventureiro.getNome(),
                aventureiro.getCompanheiro()
        );
    }

    // ==================================
    // Remover Companheiro do Aventureiro
    // ==================================
    @Transactional(readOnly = false)
    public void removerCompanheiro(Long id) {
        Aventureiro aventureiro = findById(id);

        if (aventureiro.getCompanheiro() == null) {
            throw new CompanheiroException("O aventureiro não possui companheiro");
        }

        aventureiro.removerCompanheiro();
    }

    private Aventureiro findById(Long id) {
        return aventureiroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aventureiro não encontrado"));
    }
}
