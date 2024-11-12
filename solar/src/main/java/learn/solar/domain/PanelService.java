package learn.solar.domain;

import java.time.Year;
import java.util.List;

import learn.solar.data.DataException;
import learn.solar.data.PanelRepository;
import learn.solar.models.Material;
import learn.solar.models.Panel;

public class PanelService {

    private final PanelRepository repo;

    public PanelService(PanelRepository repo) {
        this.repo = repo;
    }

    public List<Panel> findBySection(String section) throws DataException {
        return repo.findBySection(section);
    }

    public PanelResult add(Panel panel) throws DataException {
        PanelResult result = validate(panel);

        if (panel.getId() > 0) {
            result.addMessage("Panel `id` should not be set.");
        }

        boolean panelExists = doesPanelExist(panel);
        if (panelExists) {
            result.addMessage("Panel already exists");
        }

        if (result.isSuccess()) {
            panel = repo.add(panel);
            result.setPanel(panel);
        }
        return result;
    }

    public PanelResult update(Panel panel) throws DataException {
        PanelResult result = validate(panel);

        if (panel.getId() <= 0) {
            result.addMessage("Panel `id` is required.");
        }

        if (result.isSuccess()) {
            if (repo.update(panel)) {
                result.setPanel(panel);
            } else {
                String message = String.format("Panel id %s was not found.", panel.getId());
                result.addMessage(message);
            }
        }
        return result;
    }

    public PanelResult deleteById(int panelId) throws DataException {
        PanelResult result = new PanelResult();
        if (!repo.deleteById(panelId)) {
            String message = String.format("Panel id %s was not found.", panelId);
            result.addMessage(message);
        }
        return result;
    }

    private PanelResult validate(Panel panel) throws DataException {
        PanelResult result = new PanelResult();

        if (panel == null) {
            result.addMessage("Panel cannot be null.");
            return result;
        }

        String section = panel.getSection();
        if (section == null || section.isBlank()) {
            result.addMessage("Panel `section` is required");
        }

        int row = panel.getRow();
        if (row <= 0 || row > 250) {
            result.addMessage("Panel `row` must be between 1 and 250");
        }

        int column = panel.getColumn();
        if (column <= 0 || column > 250) {
            result.addMessage("Panel `column` must be between 1 and 250");
        }

        int year = panel.getInstallationYear();
        if (year >= Year.now().getValue()) {
            result.addMessage("Panel `installation year` must be in the past");
        }

        Material material = panel.getMaterial();
        if (material == null) {
            result.addMessage("Panel `material` is required");
        }

        if (!validateMaterial(material)) {
            result.addMessage("Panel `material` must be one of " + Material.values().toString());
        }

        return result;
    }

    private boolean validateMaterial(Material material) {
        try {
            for (Material m : Material.values()) {
                if (m.name().equals(material.name())) {
                    return true;
                }
            }
        } catch (NullPointerException ex) {
            return false;
        }
        return false;
    }

    private boolean doesPanelExist(Panel panel) throws DataException {
        for (Panel p : repo.findBySection(panel.getSection())) {
            if (p.getRow() == panel.getRow() && p.getColumn() == panel.getColumn()) {
                return true;
            }
        }
        return false;
    }
}
