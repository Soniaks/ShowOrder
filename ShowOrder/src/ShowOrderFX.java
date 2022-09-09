import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
public class ShowOrderFX extends Application {
	public File csv;
	@Override
	public void start(final Stage stage) {
		   // update this method definition to complete the First JavaFX Activity
    	stage.setTitle("Optima Show Order");
    	Label label1 = new Label("Welcome to Optima Show Order Finder!\n\n\n");
    	Label label2 = new Label( "Throughout this application you will be able to find a show order "
    			+ "for the showcase\n        that minimizes the number of quick changes based on your preferences.");
    	Label label3 = new Label("Click START below to get started!");  
    	Button start = new Button("START");
    	label2.setStyle(this.getClass().getResource("style.css").toExternalForm());
    	//label1.setTextAlignment(TextAlignment.CENTER);
    	//label1.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 30));
    	//label2.setFont(Font.font("Times New Roman", 18));
    	//label3.setFont(Font.font("Times New Roman", 18));
    	VBox vbox = new VBox(15);
    	vbox.setId("pane");
    	Scene scene = new Scene(vbox, 1200, 700);
    	vbox.getChildren().addAll(label1, label2, label3, start);
    	vbox.setPadding(new Insets(60));
    	vbox.setAlignment(Pos.TOP_CENTER);
    	scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
    	//start.setOnMouseEntered(e -> start.setStyle("-fx-background-color: steelBlue; -fx-background-radius: 10;"));
    	start.setOnMouseClicked(e -> setUp(stage));
        stage.setScene(scene);
        
        
        stage.show();
	}
	public void setUp(final Stage stage) {
		Label title = new Label("File Upload");
		title.setId("title");
		Label step1 = new Label("The first thing we need to do is load in the dances for our show! For this, all we need to do is drop a .csv file into the box below.");
		//Label step12 = new Label("For this, all we need to do is drop a .csv file into the box below.");
		Label step13 = new Label("The first column should contain the name of our dances, and the second should contain the list of dancers in the dance separated by commas.");
		Label step14 = new Label("For example, it should look something like this:");
		step1.setTextAlignment(TextAlignment.CENTER);
		//step12.setTextAlignment(TextAlignment.CENTER);
		step13.setTextAlignment(TextAlignment.CENTER);
		step14.setTextAlignment(TextAlignment.LEFT);
		VBox vbox = new VBox(15);
		ScrollPane scroll = new ScrollPane();
		scroll.setContent(vbox);
		scroll.setPrefSize(95,45);

		vbox.setAlignment(Pos.CENTER);
		vbox.setId("pane2");
		Image sheet = new Image("2022Picture.png");
		ImageView view = new ImageView();
		view.setFitWidth(900);
		view.setFitHeight(500);
		view.setPreserveRatio(true);
		view.setCache(true);
		view.setImage(sheet);
		Group group = new Group(view);
		Button upload = new Button("Select File");
		Scene scene = new Scene(vbox,1200,700);
		vbox.getChildren().addAll(title, step1, step13, step14, group, upload);
		vbox.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
		upload.setOnMouseClicked(e -> {
			save(stage);
		
		//System.out.println(csv.getName());
		if(this.csv != null) {
			upload.setText(this.csv.getName());
			upload.setId("upload");
			Label next = new Label("Great! Please press NEXT to continue");
			Button next1 = new Button("NEXT");
			next1.setLayoutX(750);
			next1.setLayoutY(500);
			
			vbox.getChildren().addAll(next, next1);
			next1.setOnMouseClicked(p -> {
		preferences(stage, csv);
		} );
		}
		});
		}


	public void save(final Stage stage) {
		FileChooser filechoose = new FileChooser();
		this.csv = filechoose.showOpenDialog(stage);
		if(csv != null) return;
	}
	public void preferences(final Stage stage, File csv) {
		try {
		DanceLoader load = new DanceLoader();
		ArrayList<String> dances = load.loadDances(csv);
		ArrayList<String> dancers = load.loadDancers(csv);
		VBox pane = new VBox();
		ScrollPane scroll = new ScrollPane();
		VBox vbox = new VBox();
		for(int i = 0; i < dances.size(); ++i) {
			TextField dance = new TextField(dances.get(i));
			vbox.getChildren().add(dance);
		}
		Label top = new Label("Your dances should be listed below, but you may check and alter anything that is incorrect.");
		Button next = new Button();
		next.setText("NEXT");
		//next.setStyle("-fx-position: CENTER");
		HBox hbox = new HBox();
		
		VBox vbox1 = new VBox();
		for(int i = 0; i < dances.size(); ++i) {
			TextField dancer = new TextField(dancers.get(i));
			dancer.setPrefWidth(1200);
			vbox1.getChildren().add(dancer);
		}
		HBox preferences = new HBox();
		Label choose = new Label("Please enter a Dance for each of the following. If you don't have a preference please type \"None\"");
		Label first = new Label("First Dance: ");
		Label last = new Label("Last Dance Before Finale: ");
		TextField firstBox = new TextField();
		TextField lastBox = new TextField();
		
		next.setOnMouseClicked(e -> {
			for(int i = 0; i < vbox.getChildren().size(); ++ i) {
				TextField check = (TextField) vbox.getChildren().get(i);
				String dance= check.getText();
				dances.set(i, dance);
				TextField checkDancer = (TextField) vbox1.getChildren().get(i);
				String dancer = checkDancer.getText();
				dancers.set(i, dancer);
				
			}
			result(stage, dances, dancers, firstBox.getText(), lastBox.getText());
		});
		hbox.getChildren().addAll(vbox, vbox1);
		preferences.getChildren().addAll(first, firstBox, last, lastBox);
		preferences.setAlignment(Pos.CENTER);
		scroll.setContent(pane);
        scroll.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        scroll.setHbarPolicy(ScrollBarPolicy.ALWAYS);
        Label title = new Label("Set Preferences");
        title.setId("title");
		pane.getChildren().addAll(title,top,hbox,choose,preferences,next);
		pane.setSpacing(20);
		pane.setAlignment(Pos.TOP_CENTER);
		scroll.setPadding(new Insets(20));
		pane.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		Scene scene = new Scene(scroll,1200,700);
		scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}finally {}
	}
	public void result(final Stage stage, ArrayList<String> dances, ArrayList<String> dancers,String first, String end) {
	
		Dance dFirst = new Dance(first);
		Dance dEnd = new Dance(end);
		ArrayList<Dance> allDances = new ArrayList<Dance>();
		PriorityQueue<Schedule2> best = new PriorityQueue<Schedule2>();
		for(int i = 0; i < dances.size(); ++i) {
			Dance add = new Dance(dances.get(i), dancers.get(i));
			if(add.toString().equalsIgnoreCase(first)) dFirst = add;
			if(add.toString().equalsIgnoreCase(end)) dEnd = add;
			allDances.add(add);
		}
		BorderPane w = new BorderPane();
		Label load = new Label("Please wait while schedules are being loaded...");
		load.getStyleClass().add("title");
		w.setCenter(load);
		w.getStyleClass().add("load");
		Scene wait = new Scene(w,1200,700);
		stage.setScene(wait);
		stage.show();
		for(int i = 0; i < 1000; ++i) {
		Collections.shuffle(allDances);
		if(first.equalsIgnoreCase("none") && end.equalsIgnoreCase("none")) {
			dFirst = allDances.get(0);
			dEnd = allDances.get(1);
		}
		else if(first.equalsIgnoreCase("none")) {
			if(!allDances.get(0).equals(dEnd)) {
				dFirst = allDances.get(0);
			}
			else {
				dFirst = allDances.get(1);
			}
		}
		else if(end.equalsIgnoreCase("none")) {
			if(!allDances.get(0).equals(dFirst)) {
				dEnd = allDances.get(0);
			}
			else {
				dEnd = allDances.get(1);
			}
		}
		ArrayList <Dance> firstHalf = new ArrayList<Dance>();
		firstHalf.add(dFirst);
		ArrayList <Dance> secondHalf = new ArrayList<Dance>();
		secondHalf.add(dEnd);
		Schedule2 finder = new Schedule2(allDances);
		for(int j = 0; j <= allDances.size()/2;++j) {
			Dance add = allDances.get(j);
			if(firstHalf.contains(allDances.get(j)) || allDances.get(j).equals(dEnd)){
			continue;
			}
			else {
				firstHalf.add(add);
			}
		}
		for(int j = (allDances.size()/2) + 1; j < allDances.size(); ++j) {
			Dance add = allDances.get(j);
			if(firstHalf.contains(allDances.get(j)) || secondHalf.contains(allDances.get(j))){
			continue;
			}
			else {
				secondHalf.add(add);
			}
		}
		finder.fullShow(firstHalf, secondHalf, dFirst, dEnd);
		//System.out.println("One option " + finder.getShow());
		if(finder.getShow() != null && (finder.getShow().size() - 2 == dances.size())) best.add(finder);

		for(int j = 0; j < allDances.size(); ++j) {
			allDances.get(j).unadd();
		}
		}
		System.out.println("BEST SIZE: " + best.size());
		ScrollPane scroll = new ScrollPane();
		//scroll.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		BorderPane pane = new BorderPane();
		scroll.setContent(pane);
        scroll.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        scroll.setHbarPolicy(ScrollBarPolicy.ALWAYS);
		Label finish = new Label ("Here are the top 5 show orders we found! Break a leg!");
		finish.setStyle("-fx-font-size: 25");
		HBox hbox = new HBox();
		Button rerun = new Button("Rerun");
		rerun.setOnMouseClicked(e -> {
			result(stage, dances, dancers, first, end);
		});
		Label warning = new Label ("Note: this program uses elements of randimization. Reruning the calculation may yield different results.");
		warning.setPadding(new Insets(10));
		hbox.getChildren().addAll(warning, rerun);
		VBox top = new VBox();
		top.getChildren().addAll(finish, hbox);
		top.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		pane.setTop(top);
		HBox orders = new HBox();
		for(int i = 1; i <= 5; ++i) {
			VBox show = new VBox();
			Label label = new Label("Option " + i);
			Label doubleNum = new Label("\nDouble Quick Changes: " + best.peek().getDoubleQuick());		
			Label singleNum = new Label("Single Quick Changes: " + best.peek().getSingleQuick() + "\n\n");
			System.out.println("Singles: " + best.peek().getSingleQuickDances());
			label.setStyle("-fx-font-size: 25");
			Label label2 = new Label(best.poll().toString());			
			show.getChildren().addAll(label, doubleNum, singleNum,label2);
			orders.getChildren().add(show);
		}
		pane.setCenter(orders);
		scroll.setStyle("-fx-background-color:linear-gradient(rgb(235, 226, 235),pink)");
		Scene scene = new Scene(scroll,1200,700);
		scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	public PriorityQueue<Schedule> bestSchedules(Dance first, Dance preInter, Dance postInter, Dance end) {
		PriorityQueue<Schedule> best = new PriorityQueue<Schedule>();
		return best;
	}
	public static void main(String [] args) {
		Application.launch();
	}

}
