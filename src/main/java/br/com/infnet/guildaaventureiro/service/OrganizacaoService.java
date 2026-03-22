package br.com.infnet.guildaaventureiro.service;

import br.com.infnet.guildaaventureiro.domain.audit.Organizacao;
import br.com.infnet.guildaaventureiro.repository.audit.OrganizacaoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrganizacaoService {
    private final OrganizacaoRepository organizacaoRepository;

    @Transactional(readOnly = true)
    protected Organizacao findById(Long id) {
        return organizacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Organização não encontrada"));
    }
}
