package learn.solar.data;

import java.util.List;

import learn.solar.models.Panel;

public interface PanelRepository {

    List<Panel> findBySection(String section) throws DataException;

    Panel add(Panel panel) throws DataException;

    boolean update(Panel panel) throws DataException;

    boolean deleteById(int panelId) throws DataException;
}
