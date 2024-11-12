package learn.solar.ui;

import java.util.List;

import learn.solar.data.DataException;
import learn.solar.domain.PanelResult;
import learn.solar.domain.PanelService;
import learn.solar.models.Panel;

public class Controller {

    private final View view;
    private final PanelService service;

    public Controller(View view, PanelService service) {
        this.view = view;
        this.service = service;
    }

    public void run() {
        view.printHeader("Welcome to Solar Farm");
        try {
            runApp();
        } catch (DataException ex) {
            System.out.println("An error occurred while accessing data. Please try again." + ex);
        }
        System.out.println("Goodbye.");
    }

    private void runApp() throws DataException {

        for (int option = view.chooseOptionFromMenu(); option > 0; option = view.chooseOptionFromMenu()) {
            switch (option) {
                case 1 -> {
                    viewBySection();
                }
                case 2 -> {
                    addPanel();
                }
                case 3 -> {
                    updatePanel();
                }
                case 4 -> {
                    deletePanel();
                }
            }
        }
    }

    private void viewBySection() throws DataException {
        String section = view.readSection();
        List<Panel> panels = service.findBySection(section);
        if (panels.isEmpty()) {
            view.printHeader("No panels found in section " + section);
        } else {
            view.printPanels(section, panels);
        }
    }

    private void addPanel() throws DataException {
        Panel p = view.makePanel();
        PanelResult result = service.add(p);
        view.printResult(result);
        if (result.isSuccess()) {
            System.out.println("Panel " + view.printFormattedPanel(p) + " added.");
        }
    }

    private void updatePanel() throws DataException {
        String section = view.readSection();
        List<Panel> panels = service.findBySection(section);
        if (panels.isEmpty()) {
            System.out.println("No panels found in section " + section);
            return;
        }

        Panel panel = view.choosePanel(section, panels);
        if (panel == null) {
            view.printHeader("Panel does not exist");
        }
        Panel updatedPanel = view.update(panel);
        PanelResult result = service.update(updatedPanel);

        view.printResult(result);
        if (result.isSuccess()) {
            System.out.println("Panel " + view.printFormattedPanel(updatedPanel) + " updated.");
        }
    }

    private void deletePanel() throws DataException {
        String section = view.readSection();
        List<Panel> panels = service.findBySection(section);
        if (panels.isEmpty()) {
            System.out.println("No panels found in section " + section);
            return;
        }

        Panel panelToDelete = view.choosePanel(section, panels);

        if (panelToDelete == null) {
            System.out.println("Panel does not exist");
            return;
        }

        PanelResult result = service.deleteById(panelToDelete.getId());
        view.printResult(result);
        if (result.isSuccess()) {
            System.out.println("Panel " + view.printFormattedPanel(panelToDelete) + " removed.");
        } else {
            System.out.println("There was no panel found.");
        }
    }
}
