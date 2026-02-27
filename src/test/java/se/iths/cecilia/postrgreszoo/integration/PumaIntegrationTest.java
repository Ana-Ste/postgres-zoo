package se.iths.cecilia.postrgreszoo.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import se.iths.cecilia.postrgreszoo.model.Puma;
import se.iths.cecilia.postrgreszoo.service.PumaService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class PumaIntegrationTest {

    @Autowired
    private PumaService pumaService;

    @Test
    void create_then_getOne_works() {
        Puma p = new Puma();
        p.setName("TestPuma");
        p.setAge(3);
        p.setWeight(40);
        p.setDangerous(true);

        Puma saved = pumaService.create(p);
        assertNotNull(saved.getId());

        Puma fromDb = pumaService.getOne(saved.getId());
        assertEquals("TestPuma", fromDb.getName());
        assertEquals(3, fromDb.getAge());
        assertEquals(40, fromDb.getWeight());
        assertTrue(fromDb.isDangerous());
    }

    @Test
    void update_works() {
        Puma p = new Puma();
        p.setName("Before");
        p.setAge(2);
        p.setWeight(30);
        p.setDangerous(false);

        Puma saved = pumaService.create(p);

        Puma updateData = new Puma();
        updateData.setName("After");
        updateData.setAge(5);
        updateData.setWeight(55);
        updateData.setDangerous(true);

        Puma updated = pumaService.update(saved.getId(), updateData);

        assertEquals("After", updated.getName());
        assertEquals(5, updated.getAge());
        assertEquals(55, updated.getWeight());
        assertTrue(updated.isDangerous());
    }

    @Test
    void delete_works() {
        Puma p = new Puma();
        p.setName("ToDelete");
        p.setAge(1);
        p.setWeight(20);
        p.setDangerous(false);

        Puma saved = pumaService.create(p);
        Long id = saved.getId();

        pumaService.delete(id);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> pumaService.getOne(id));
        assertNotNull(ex.getMessage());
    }
}