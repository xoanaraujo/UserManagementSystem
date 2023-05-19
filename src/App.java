import controller.StartMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.UserData;

public class App extends Application{
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        UserData.init();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("view/StartMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        ((StartMenuController)fxmlLoader.getController()).init(stage);
        stage.setTitle("User manager sytem");
        stage.setScene(scene);
        stage.show();
    }
}
