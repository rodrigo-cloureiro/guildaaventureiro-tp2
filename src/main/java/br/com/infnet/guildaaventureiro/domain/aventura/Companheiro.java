package br.com.infnet.guildaaventureiro.domain.aventura;

import br.com.infnet.guildaaventureiro.domain.aventura.enums.CompanheiroEspecie;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Entity
@Table(
        name = "companheiros",
        schema = "aventura",
        indexes = {
                @Index(name = "idx_companheiros_especie_lealdade", columnList = "especie, lealdade")
        }
)
@Getter
@Setter
public class Companheiro {
    @Id
    private Long id;

    @OneToOne
    @MapsId
    @PrimaryKeyJoinColumn(
            name = "aventureiro_id",
            foreignKey = @ForeignKey(name = "fk_companheiros_aventureiro")
    )
    private Aventureiro aventureiro;

    @Column(length = 120, nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CompanheiroEspecie especie;

    @Range(min = 0, max = 100, message = "A lealdade deve ser um inteiro entre 0 e 100")
    @Column(nullable = false)
    private int lealdade;

    protected Companheiro() {
    }

    public Companheiro(String nome, CompanheiroEspecie especie, int lealdade) {
        this.nome = nome;
        this.especie = especie;
        this.lealdade = lealdade;
    }

    public void definirAventureiro(Aventureiro aventureiro) {
        this.aventureiro = aventureiro;
    }
}
