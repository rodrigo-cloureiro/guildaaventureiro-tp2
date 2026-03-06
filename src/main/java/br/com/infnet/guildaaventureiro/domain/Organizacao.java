package br.com.infnet.guildaaventureiro.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "organizacoes", schema = "audit")
@Getter
@Setter
public class Organizacao {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "organizacoes_id")
    @SequenceGenerator(
            name = "organizacoes_id",
            sequenceName = "organizacoes_id_seq",
            allocationSize = 1
    )
    private Long id;

    @Column(
            name = "nome",
            length = 120,
            nullable = false
    )
    private String nome;

    @Column(
            name = "ativo",
            nullable = false
    )
    private boolean ativo = true;

    @CreationTimestamp
    @Column(
            name = "created_at",
            nullable = false,
            updatable = false
    )
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "organizacao", fetch = FetchType.LAZY)
    private List<Usuario> usuarios;
}
