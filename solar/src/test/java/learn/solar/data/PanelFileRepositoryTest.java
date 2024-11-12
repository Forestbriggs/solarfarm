package learn.solar.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import learn.solar.models.Material;
import learn.solar.models.Panel;

public class PanelFileRepositoryTest {

    static final String SEED_FILE_PATH = "./data/panels-seed.txt";
    static final String TEST_FILE_PATH = "./data/panels-test.txt";

    PanelFileRepository repo = new PanelFileRepository(TEST_FILE_PATH);

    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);

        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void testAdd() throws DataException {
        Panel panel = new Panel();
        panel.setSection("Aux");
        panel.setRow(1);
        panel.setColumn(2);
        panel.setInstallationYear(2023);
        panel.setMaterial(Material.AMORPHOUS_SILICON);

        Panel actual = repo.add(panel); // panel that was added
        assertEquals(4, actual.getId()); // confirm the fields are accurate
        assertEquals("Aux", actual.getSection());
        assertEquals(1, actual.getRow());
        assertEquals(2, actual.getColumn());
        assertEquals(2023, actual.getInstallationYear());
        assertEquals(Material.AMORPHOUS_SILICON.getAbbreviatedName(), actual.getMaterial().getAbbreviatedName());
        assertFalse(actual.isTracking());
    }

    @Test
    void testFindBySection() throws DataException {
        List<Panel> actual = repo.findBySection("Main");
        assertEquals(2, actual.size()); // seed data has 2 panels in "Main" section

        actual = repo.findBySection("Backup");
        assertEquals(1, actual.size()); // seed data has 1 panel in "Backup section"

        actual = repo.findBySection("Invalid");
        assertEquals(0, actual.size()); // should return empty list if section doesn't exist
    }

    @Test
    void testUpdate() throws DataException {
        Panel panel = repo.findBySection("Main").get(0); // 1~Main~1~1~2018~mono-Si~true
        panel.setColumn(4);
        panel.setTracking(false);
        panel.setMaterial(Material.COPPER_INDIUM_GALLIUM_SELENIDE);
        assertTrue(repo.update(panel));

        panel = repo.findBySection("Main").get(0);
        assertNotNull(panel); // confirm panel exists and all data was updated
        assertEquals(4, panel.getColumn());
        assertEquals(Material.COPPER_INDIUM_GALLIUM_SELENIDE, panel.getMaterial());
        assertFalse(panel.isTracking());

        Panel doesNotExist = new Panel();
        doesNotExist.setId(1000);
        assertFalse(repo.update(doesNotExist)); // can't update panel that doesn't exist
    }

    @Test
    void testDeleteById() throws DataException {
        int count = repo.findBySection("Main").size(); // Main has 2 panels
        assertTrue(repo.deleteById(1));
        assertFalse(repo.deleteById(1000));
        assertEquals(count - 1, repo.findBySection("Main").size());
    }
}
