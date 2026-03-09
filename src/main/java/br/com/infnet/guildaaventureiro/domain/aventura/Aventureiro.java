package br.com.infnet.guildaaventureiro.domain.aventura;

import br.com.infnet.guildaaventureiro.domain.aventura.enums.AventureiroClasse;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "aventureiros",
        schema = "aventura",
        uniqueConstraints = {}, // TODO Definir unique constraints
        check = {
                @CheckConstraint(name = "ck_aventureiros_nivel", constraint = "nivel >= 1")
        },
        indexes = {} // TODO definir indexes
)
@Getter
@Setter
public class Aventureiro {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "")
    @SequenceGenerator(
            name = "",
            sequenceName = "",
            schema = "aventura",
            allocationSize = 1
    ) // TODO Definir sequence corretamente
    private Long id;

    @Column(nullable = false)
    private String organizacao; // TODO Aplicar relacionamento corretamente

    @Column(nullable = false)
    private String usuario; // TODO Aplicar relacionamento corretamente

    @Column(length = 120, nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AventureiroClasse classe;

    @Min(value = 1, message = "O nível deve ser maior ou igual a 1")
    @Column(nullable = false)
    private int nivel;

    @Column(nullable = false)
    private boolean ativo = true;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime dataAtualizacao;
}
