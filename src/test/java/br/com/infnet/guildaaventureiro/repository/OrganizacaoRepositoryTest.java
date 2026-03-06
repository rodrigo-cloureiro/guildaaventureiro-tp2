package br.com.infnet.guildaaventureiro.repository;

import br.com.infnet.guildaaventureiro.domain.Organizacao;
import br.com.infnet.guildaaventureiro.domain.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrganizacaoRepositoryTest {
    @Autowired
    private OrganizacaoRepository organizacaoRepository;

    @Test
    public void findAllTest() {
        List<Organizacao> all = organizacaoRepository.findAll();
        all.forEach(System.out::println);
        assertFalse(all.isEmpty());
    }
}
