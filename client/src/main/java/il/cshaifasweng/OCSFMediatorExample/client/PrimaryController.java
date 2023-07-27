package il.cshaifasweng.OCSFMediatorExample.client;
import javafx.scene.text.Text;
//import il.cshaifasweng.OCSFMediatorExample.entities.Student;
import il.cshaifasweng.OCSFMediatorExample.entities.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.util.Duration;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class PrimaryController {

	@FXML
	private Button gradeChange;

	@FXML
	private ListView<Student> namesList;

	@FXML
	private TextField newGrade;

	@FXML
	private Button showGrade;
	@FXML
	private Text gradeShow;
	@FXML
	void changeTheGrade(MouseEvent event) throws IOException {
		int id = namesList.getSelectionModel().getSelectedIndex();
		namesList.getItems().get(id).setGrade(Integer.valueOf(newGrade.getText()));
		String name = newGrade.getText();
		Message message1 = new Message(1, "add student");
		newGrade.clear();
		try {
			Message message = new Message(msgId, "add client");
			SimpleClient.getClient().sendToServer(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("catch");
			e.printStackTrace();
		}
		//namesList.getItems().add(name);
	}

	@FXML
	void newGradeField(ActionEvent event) {

	}

	@FXML
	void showTheGrade(MouseEvent event) {
		int id = namesList.getSelectionModel().getSelectedIndex();
		gradeShow.setText(String.valueOf(namesList.getItems().get(id).getGrade()));
	}
	@FXML
	private TextField submitterID1;

	@FXML
	private TextField submitterID2;

	@FXML
	private TextField timeTF;

	@FXML
	private TextField MessageTF;

	@FXML
	private Button SendBtn;

	@FXML
	private TextField DataFromServerTF;

	private int msgId;
	private int grade;
	private String name;

	@FXML
	void sendMessage(ActionEvent event) {
		try {
			Message message = new Message(msgId++, MessageTF.getText());
			MessageTF.clear();
			SimpleClient.getClient().sendToServer(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Subscribe
	public void setDataFromServerTF(MessageEvent event) {
		DataFromServerTF.setText(event.getMessage().getMessage());
	}

	@Subscribe
	public void setSubmittersTF(UpdateMessageEvent event) {
		submitterID1.setText(event.getMessage().getData().substring(0,9));
		submitterID2.setText(event.getMessage().getData().substring(11,20));
	}

	@Subscribe
	public void getStarterData(NewSubscriberEvent event) {
		try {
			Message message = new Message(msgId, "send Submitters IDs");
			SimpleClient.getClient().sendToServer(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Subscribe
	public void errorEvent(ErrorEvent event){
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
		Platform.runLater(() -> {
			Alert alert = new Alert(Alert.AlertType.ERROR,
					String.format("Message:\nId: %d\nData: %s\nTimestamp: %s\n",
							event.getMessage().getId(),
							event.getMessage().getMessage(),
							event.getMessage().getTimeStamp().format(dtf))
			);
			alert.setTitle("Error!");
			alert.setHeaderText("Error:");
			alert.show();
		});
	}

	@FXML
	void initialize() {
		EventBus.getDefault().register(this);
		newGrade.clear();
		//msgId=0;
		/*DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
		Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
			LocalTime currentTime = LocalTime.now();
			timeTF.setText(currentTime.format(dtf));
		}),
				new KeyFrame(Duration.seconds(1))
		);
		clock.setCycleCount(Animation.INDEFINITE);
		clock.play();*/
/*		try {
			Message message = new Message(msgId, "add client");
			SimpleClient.getClient().sendToServer(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		Student student = new Student("Rai",95);
		namesList.getItems().add(student);
		Student student2 = new Student("muhammad",17);
		namesList.getItems().add(student2);
		Student student3 = new Student("jo7a",52);
		namesList.getItems().add(student3);
		Student student4 = new Student("bndar",77);
		namesList.getItems().add(student4);
		Student student5 = new Student("3lawe",78);
		namesList.getItems().add(student5);
		Student student6 = new Student("6l7a",45);
		namesList.getItems().add(student6);
		Student student7 = new Student("sdewe",24);
		namesList.getItems().add(student7);
		try {
			Message message = new Message(msgId, "add client");
			SimpleClient.getClient().sendToServer(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
