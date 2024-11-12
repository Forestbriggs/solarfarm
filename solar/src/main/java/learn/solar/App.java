package learn.solar;

import learn.solar.data.PanelFileRepository;
import learn.solar.domain.PanelService;
import learn.solar.ui.ConsoleIO;
import learn.solar.ui.Controller;
import learn.solar.ui.View;

public class App {

    public static void main(String[] args) {
        PanelFileRepository repository = new PanelFileRepository("./solar/data/panels.txt");
        PanelService service = new PanelService(repository);

        ConsoleIO io = new ConsoleIO();
        View view = new View(io);

        Controller controller = new Controller(view, service);

        // and so it begins...1
        controller.run();
    }
}
