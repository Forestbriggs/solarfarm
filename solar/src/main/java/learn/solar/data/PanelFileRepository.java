package learn.solar.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import learn.solar.models.Material;
import learn.solar.models.Panel;

public class PanelFileRepository implements PanelRepository {

    private final String filePath;
    private final String delimiter = "~";

    public PanelFileRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Panel> findBySection(String section) throws DataException {
        ArrayList<Panel> result = new ArrayList<>();
        for (Panel p : findAll()) {
            if (p.getSection().equals(section)) {
                result.add(p);
            }
        }
        return result;
    }

    @Override
    public Panel add(Panel panel) throws DataException {
        List<Panel> all = findAll();
        int nextId = getNextId(all);

        panel.setId(nextId);
        all.add(panel);
        writeToFile(all);
        return panel;
    }

    @Override
    public boolean update(Panel panel) throws DataException {
        List<Panel> all = findAll();
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getId() == panel.getId()) {
                all.set(i, panel);
                writeToFile(all);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteById(int panelId) throws DataException {
        List<Panel> all = findAll();
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getId() == panelId) {
                all.remove(i);
                writeToFile(all);
                return true;
            }
        }
        return false;
    }

    private List<Panel> findAll() throws DataException {
        ArrayList<Panel> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                Panel p = deserialize(line);
                if (p != null) {
                    result.add(p);
                }
            }
        } catch (FileNotFoundException ex) {
            // no panels exist yet, no worries
        } catch (IOException ex) {
            throw new DataException("Could not open file path: " + filePath, ex);
        } catch (RuntimeException ex) {
            throw new DataException(ex.getMessage(), ex);
        }
        return result;
    }

    private int getNextId(List<Panel> panels) {
        int maxId = 0;
        for (Panel p : panels) {
            if (maxId < p.getId()) {
                maxId = p.getId();
            }
        }
        return maxId + 1;
    }

    private String serialize(Panel panel) {
        StringBuilder buffer = new StringBuilder(100);
        buffer.append(panel.getId()).append(delimiter);
        buffer.append(cleanField(panel.getSection())).append(delimiter);
        buffer.append(panel.getRow()).append(delimiter);
        buffer.append(panel.getColumn()).append(delimiter);
        buffer.append(panel.getInstallationYear()).append(delimiter);
        buffer.append(cleanField(panel.getMaterial().getAbbreviatedName())).append(delimiter);
        buffer.append(panel.isTracking()).append(delimiter);
        return buffer.toString();
    }

    private Panel deserialize(String line) throws RuntimeException {
        String[] fields = line.split(delimiter);

        if (fields.length != 7) {
            return null;
        }

        Panel panel;
        try {
            panel = new Panel(
                    Integer.parseInt(fields[0]),
                    fields[1],
                    Integer.parseInt(fields[2]),
                    Integer.parseInt(fields[3]),
                    Integer.parseInt(fields[4]),
                    Material.findByAbbreviatedName(fields[5]),
                    "true".equals(fields[6])
            );
        } catch (RuntimeException ex) {
            throw ex;
        }
        return panel;
    }

    private String cleanField(String field) {
        return field.replace(delimiter, "")
                .replace("/r", "")
                .replace("/n", "");
    }

    private void writeToFile(List<Panel> panels) throws DataException {
        try {
            File file = new File(filePath);
            File parentDir = file.getParentFile();

            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }

            try (PrintWriter writer = new PrintWriter(filePath)) {
                for (Panel p : panels) {
                    writer.println(serialize(p));
                }
            }
        } catch (IOException ex) {
            throw new DataException("Could not write to file path: " + filePath, ex);
        }
    }
}
