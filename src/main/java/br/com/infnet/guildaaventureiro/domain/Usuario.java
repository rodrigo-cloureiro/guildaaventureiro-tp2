package br.com.infnet.guildaaventureiro.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios", schema = "audit")
@Getter
@Setter
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuarios_id")
    @SequenceGenerator(
            name = "usuarios_id",
            sequenceName = "usuarios_id_seq",
            allocationSize = 1
    )
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacao_id", nullable = false)
    private Organizacao organizacao;

    @Column(
            name = "nome",
            length = 120,
            nullable = false
    )
    private String nome;

    @Column(
            name = "email",
            length = 180,
            nullable = false
    )
    private String email;

    @Column(
            name = "senha_hash",
            length = 255,
            nullable = false
    )
    private String senhaHash;

    @Column(
            name = "status",
            length = 30,
            nullable = false
    )
    private String status;

    @Column(
            name = "ultimo_login_em",
            nullable = true
    )
    private LocalDateTime ultimoLoginEm;

    @CreationTimestamp
    @Column(
            name = "created_at",
            nullable = false,
            updatable = false
    )
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(
            name = "updated_at",
            nullable = false
    )
    private LocalDateTime updatedAt;
}
