package learn.solar.domain;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import learn.solar.data.DataException;
import learn.solar.data.PanelRepositoryDouble;
import learn.solar.models.Material;
import learn.solar.models.Panel;

public class PanelServiceTest {

    PanelService service;

    @BeforeEach
    void setup() {
        PanelRepositoryDouble repo = new PanelRepositoryDouble();
        service = new PanelService(repo);
    }

    @Test
    void shouldFindThreePanelsInMainSection() throws DataException {
        List<Panel> panels = service.findBySection("Main");
        assertEquals(3, panels.size());
    }

    @Test
    void shouldCatchNullSection() throws DataException {
        Panel panel = new Panel();
        panel.setRow(1);
        panel.setColumn(2);
        panel.setInstallationYear(2023);
        panel.setMaterial(Material.AMORPHOUS_SILICON);
        panel.setTracking(true);

        PanelResult result = service.add(panel);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertTrue(result.getMessages().get(0).contains("`section`"));
    }

    @Test
    void shouldCatchIdOnAdd() throws DataException {
        Panel panel = new Panel(10, "Test", 1, 2, 2023, Material.AMORPHOUS_SILICON, true);

        PanelResult result = service.add(panel);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertTrue(result.getMessages().get(0).contains("`id`"));
    }

    @Test
    void shouldCatchLowRow() throws DataException {
        Panel panel = new Panel();
        panel.setSection("Test");
        panel.setRow(-100);
        panel.setColumn(2);
        panel.setInstallationYear(2023);
        panel.setMaterial(Material.AMORPHOUS_SILICON);
        panel.setTracking(true);

        PanelResult result = service.add(panel);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertTrue(result.getMessages().get(0).contains("`row`"));
    }

    @Test
    void shouldCatchHighRow() throws DataException {
        Panel panel = new Panel();
        panel.setSection("Test");
        panel.setRow(1000);
        panel.setColumn(2);
        panel.setInstallationYear(2023);
        panel.setMaterial(Material.AMORPHOUS_SILICON);
        panel.setTracking(true);

        PanelResult result = service.add(panel);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertTrue(result.getMessages().get(0).contains("`row`"));
    }

    @Test
    void shouldCatchLowColumn() throws DataException {
        Panel panel = new Panel();
        panel.setSection("Test");
        panel.setRow(1);
        panel.setColumn(-100);
        panel.setInstallationYear(2023);
        panel.setMaterial(Material.AMORPHOUS_SILICON);
        panel.setTracking(true);

        PanelResult result = service.add(panel);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertTrue(result.getMessages().get(0).contains("`column`"));
    }

    @Test
    void shouldCatchHighColumn() throws DataException {
        Panel panel = new Panel();
        panel.setSection("Test");
        panel.setRow(1);
        panel.setColumn(1000);
        panel.setInstallationYear(2023);
        panel.setMaterial(Material.AMORPHOUS_SILICON);
        panel.setTracking(true);

        PanelResult result = service.add(panel);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertTrue(result.getMessages().get(0).contains("`column`"));
    }

    @Test
    void shouldCatchFutureInstallationYear() throws DataException {
        Panel panel = new Panel();
        panel.setSection("Test");
        panel.setRow(1);
        panel.setColumn(1);
        panel.setInstallationYear(2030);
        panel.setMaterial(Material.AMORPHOUS_SILICON);
        panel.setTracking(true);

        PanelResult result = service.add(panel);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertTrue(result.getMessages().get(0).contains("`installation year`"));
    }

    @Test
    void shouldCatchNullMaterial() throws DataException {
        Panel panel = new Panel();
        panel.setSection("Test");
        panel.setRow(1);
        panel.setColumn(1);
        panel.setInstallationYear(2023);
        panel.setTracking(true);

        PanelResult result = service.add(panel);

        assertFalse(result.isSuccess());
        assertEquals(2, result.getMessages().size()); // should receive both possible material error messages
        assertTrue(result.getMessages().get(0).contains("`material`"));
        assertTrue(result.getMessages().get(1).contains("`material`"));
    }

    @Test
    void shouldAdd() throws DataException {
        Panel panel = new Panel();
        panel.setSection("Test");
        panel.setRow(1);
        panel.setColumn(1);
        panel.setInstallationYear(2023);
        panel.setMaterial(Material.AMORPHOUS_SILICON);
        panel.setTracking(true);

        PanelResult result = service.add(panel);

        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotUpdateEmptySection() throws DataException {
        Panel panel = service.findBySection("Main").get(0);
        panel.setSection("\t\n ");

        PanelResult result = service.update(panel);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertTrue(result.getMessages().get(0).contains("`section`"));
    }

    @Test
    void shouldUpdate() throws DataException {
        Panel panel = service.findBySection("Main").get(0);
        panel.setSection("New Section");

        PanelResult result = service.update(panel);

        assertTrue(result.isSuccess());
    }

    @Test
    void shouldCatchDuplicatePanels() throws DataException {
        Panel panel = new Panel();
        panel.setSection("Main");
        panel.setRow(1);
        panel.setColumn(1);
        panel.setInstallationYear(2023);
        panel.setMaterial(Material.AMORPHOUS_SILICON);
        panel.setTracking(true);

        PanelResult result = service.add(panel);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertTrue(result.getMessages().get(0).equals("Panel already exists"));
    }

    @Test
    void shouldDelete() throws DataException {
        PanelResult result = service.deleteById(1);

        assertTrue(result.isSuccess());
    }
}
