package se.iths.cecilia.postrgreszoo.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.iths.cecilia.postrgreszoo.model.Puma;
import se.iths.cecilia.postrgreszoo.repository.PumaRepository;
import se.iths.cecilia.postrgreszoo.validator.PumaValidator;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PumaServiceTest {

    @Mock
    PumaRepository repository;

    @Mock
    PumaValidator validator;

    @InjectMocks
    PumaService service;

    @Test
    void createPumaTest() {

        Puma puma = new Puma();
        puma.setId(1L);
        puma.setName("Puma");
        puma.setAge(5);
        puma.setWeight(60);

        when(repository.save(any(Puma.class))).thenReturn(puma);

        Puma saved = service.create(puma);

        verify(validator).validate(puma);
        verify(repository).save(puma);
    }

    @Test
    void getPumaByIdTest() {

        Puma puma = new Puma();
        puma.setId(1L);
        puma.setName("Test");

        when(repository.findById(1L)).thenReturn(Optional.of(puma));

        Puma result = service.getOne(1L);

        assertEquals("Test", result.getName());

        verify(repository).findById(1L);
    }

    @Test
    void getAllPumasTest() {

        Puma p1 = new Puma();
        p1.setName("Puma1");

        Puma p2 = new Puma();
        p2.setName("Puma2");

        when(repository.findAll()).thenReturn(List.of(p1, p2));

        List<Puma> result = service.getAll();

        assertEquals(2, result.size());

        verify(repository).findAll();
    }

    @Test
    void updatePumaTest() {

        Puma existing = new Puma();
        existing.setId(1L);
        existing.setName("Old");

        Puma updated = new Puma();
        updated.setName("New");
        updated.setAge(10);
        updated.setWeight(70);

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(existing);

        Puma result = service.update(1L, updated);

        verify(validator).validate(updated);
        verify(repository).findById(1L);
        verify(repository).save(existing);

        assertEquals("New", result.getName());
        assertEquals(10, result.getAge());
        assertEquals(70, result.getWeight());
    }

    @Test
    void deletePumaTest() {

        Puma puma = new Puma();
        puma.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(puma));

        service.delete(1L);

        verify(repository).findById(1L);
        verify(repository).deleteById(1L);
    }

    @Test
    void getPumaByIdExceptionTest() {

        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> service.getOne(1L));

        verify(repository).findById(1L);
    }

}