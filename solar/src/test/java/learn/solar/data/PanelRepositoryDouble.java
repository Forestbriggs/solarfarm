package learn.solar.data;

import java.util.ArrayList;
import java.util.List;

import learn.solar.models.Material;
import learn.solar.models.Panel;

public class PanelRepositoryDouble implements PanelRepository {

    private final ArrayList<Panel> panels = new ArrayList<>();

    public PanelRepositoryDouble() {
        panels.add(new Panel(1, "Main", 1, 1, 2023, Material.AMORPHOUS_SILICON, true));
        panels.add(new Panel(2, "Main", 1, 2, 2023, Material.CADMIUM_TELLURIDE, false));
        panels.add(new Panel(3, "Main", 1, 3, 2023, Material.COPPER_INDIUM_GALLIUM_SELENIDE, true));
    }

    @Override
    public Panel add(Panel panel) throws DataException {
        return panel; // no need to actually add panel for test purposes
    }

    @Override
    public boolean deleteById(int panelId) throws DataException {
        return panels.get(panelId) != null;
    }

    @Override
    public boolean update(Panel panel) throws DataException {
        return panels.get(panel.getId()) != null;
    }

    @Override
    public List<Panel> findBySection(String section) throws DataException {
        ArrayList<Panel> result = new ArrayList<>();
        for (Panel p : panels) {
            if (p.getSection().equals(section)) {
                result.add(p);
            }
        }
        return result;
    }

}
