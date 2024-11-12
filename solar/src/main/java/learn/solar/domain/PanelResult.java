package learn.solar.domain;

import java.util.ArrayList;
import java.util.List;

import learn.solar.models.Panel;

public class PanelResult {

    private final ArrayList<String> messages = new ArrayList<>();
    private Panel panel;

    public boolean isSuccess() {
        // if an error messages exists the operation failed
        return messages.isEmpty();
    }

    public List<String> getMessages() {
        return new ArrayList<>(messages);
    }

    public void addMessage(String message) {
        messages.add(message);
    }

    public Panel getPanel() {
        return panel;
    }

    public void setPanel(Panel panel) {
        this.panel = panel;
    }
}
