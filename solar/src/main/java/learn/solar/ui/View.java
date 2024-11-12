package learn.solar.ui;

import java.util.List;

import learn.solar.domain.PanelResult;
import learn.solar.models.Material;
import learn.solar.models.Panel;

public class View {

    private final TextIO io;

    public View(TextIO io) {
        this.io = io;
    }

    public int chooseOptionFromMenu() {
        printHeader("Main Menu");
        io.println("0. Exit");
        io.println("1. Find Panels by Section");
        io.println("2. Add a Panel");
        io.println("3. Update a Panel");
        io.println("4. Remove a Panel");
        return io.readInt("Choose [0-4]: ", 0, 4);
    }

    public void printHeader(String message) {
        int length = message.length();
        io.println("");
        io.println(message);
        io.println("=".repeat(length));
    }

    public void printResult(PanelResult result) {
        if (result.isSuccess()) {
            io.println("[Success]");
        } else {
            for (String message : result.getMessages()) {
                io.println("[Err] " + message);
            }
        }
    }

    public void printPanels(String sectionName, List<Panel> panels) {
        io.print("\n");
        io.printf("Panels in %s%n", sectionName);
        io.println("Row Col Year Material Tracking");
        for (Panel p : panels) {
            io.print(addPrefix(String.valueOf(p.getRow()), "Row".length()));
            io.print(addPrefix(String.valueOf(p.getColumn()), "Col".length() + 1));
            io.print(addPrefix(String.valueOf(p.getInstallationYear()), "Year".length() + 1));
            io.print(addPrefix(p.getMaterial().getAbbreviatedName(), "Material".length() + 1));
            String tracking = p.isTracking() ? "yes" : "no";
            io.print(addPrefix(tracking, "Tracking".length() + 1));
            io.print("\n");
        }
    }

    private String addPrefix(String string, int length) {
        return " ".repeat(length - string.length()) + string;
    }

    public Panel choosePanel(String sectionName, List<Panel> panels) {
        printPanels(sectionName, panels);
        int row = io.readInt("Row: ");
        int col = io.readInt("Column: ");
        for (Panel panel : panels) {
            if (panel.getRow() == row && panel.getColumn() == col) {
                return panel;
            }
        }
        return null;
    }

    public String readSection() {
        String section = io.readRequiredString("Section Name: ");
        return section;
    }

    private Material readMaterial() {
        Material material = null;
        while (material == null) {
            String input = io.readString("Material (mono-Si, multi-Si, a-Si, CdTe, CIGS): ");
            try {
                material = Material.findByAbbreviatedName(input);
            } catch (RuntimeException e) {
                io.println(e.getMessage() + " Please try again.");
            }
        }
        return material;
    }

    public Panel makePanel() {
        printHeader("Add a Panel");
        Panel result = new Panel();
        result.setSection(readSection());
        result.setRow(io.readInt("Row: ", 1, 250));
        result.setColumn(io.readInt("Column: ", 1, 250));
        result.setInstallationYear(io.readInt("Installation Year: "));
        result.setMaterial(readMaterial());
        result.setTracking(io.readBoolean("Tracked [y/n]: "));
        return result;
    }

    public Panel update(Panel panel) {
        printHeader("Update a Panel");
        io.printf("Section: %s%n", panel.getSection());
        io.printf("Row: %s%n", panel.getRow());
        io.printf("Column: %s%n", panel.getColumn());
        io.println("Press [Enter] to keep original value.");

        // Section
        String section = io.readString(String.format("Section (%s): ", panel.getSection()));
        if (!section.isBlank()) {
            panel.setSection(section);
        }

        // Row
        String rowInput = io.readString(String.format("Row (%d): ", panel.getRow()));
        if (!rowInput.isBlank()) {
            int row = Integer.parseInt(rowInput);
            panel.setRow(row);
        }

        // Column
        String columnInput = io.readString(String.format("Column (%d): ", panel.getColumn()));
        if (!columnInput.isBlank()) {
            int column = Integer.parseInt(columnInput);
            panel.setColumn(column);
        }
        // Material
        Material material = null;
        while (material == null) {
            String materialInput = io.readString(String.format("Material (%s) (mono-Si, multi-Si, a-Si, CdTe, CIGS): ", panel.getMaterial().getAbbreviatedName()));
            if (materialInput.isBlank()) {
                material = panel.getMaterial(); // Keep original if blank
            } else {
                try {
                    material = Material.findByAbbreviatedName(materialInput);
                } catch (RuntimeException e) {
                    io.println(e.getMessage() + " Please try again.");
                }
            }
        }
        panel.setMaterial(material);

        // Installation Year
        int year = -1;
        int currentYear = java.time.Year.now().getValue();

        while (year == -1) {
            String yearInput = io.readString(String.format("Installation Year (%d): ", panel.getInstallationYear()));
            if (yearInput.isBlank()) {
                year = panel.getInstallationYear(); // Keep original year if input is blank
            } else {
                try {
                    int parsedYear = Integer.parseInt(yearInput);
                    if (parsedYear < currentYear) {
                        year = parsedYear;
                    } else {
                        io.println("Installation year must be in the past. Please enter a valid year.");
                    }
                } catch (NumberFormatException e) {
                    io.println("Invalid input. Please enter a valid numeric year.");
                }
            }
        }
        panel.setInstallationYear(year);

        // Tracking
        boolean tracking = panel.isTracking(); // Default to the current tracking value
        String trackingInput;

        while (true) {
            trackingInput = io.readString(String.format("Tracked (%s) [y/n]: ", tracking ? "yes" : "no")).trim();
            if (trackingInput.isBlank()) {
                // Keep the original tracking value if the input is blank
                break;
            } else if (trackingInput.equalsIgnoreCase("y")) {
                tracking = true;
                break;
            } else if (trackingInput.equalsIgnoreCase("n")) {
                tracking = false;
                break;
            } else {
                io.println("Invalid input. Please enter 'y' for yes or 'n' for no.");
            }
        }
        panel.setTracking(tracking);

        return panel;
    }

    public String printFormattedPanel(Panel panel) {
        return String.format("%s-%d-%d",
                panel.getSection(), panel.getRow(), panel.getColumn());
    }
}
