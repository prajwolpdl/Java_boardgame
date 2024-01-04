package assignment1;

import javafx.animation.PauseTransition;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.io.*;

import assignment1.ThreeMusketeers.GameMode;
import assignment1.Exceptions.InvalidMoveException;
import assignment1.Piece.Type;

public class View {
	ThreeMusketeers model;
    Stage stage;
    BorderPane borderPane;

    
    
    Label successLabel = new Label("");
    
    Label timerLabel = new Label("");
    
    Button undoButton, saveButton, restartButton, tournamentButton, runButton, SuggestedmovesButton, UnsuggestmovesButton, openGameButton;
    TextField enterMoveTextField1, enterMoveTextField2;
    static TextField timefield;
	public View(ThreeMusketeers model, Stage stage) {
		this.stage = stage;
		this.model = model;
		this.model.play();
		initUI();
		System.out.println(this.model.board);
    	}
	
	 private void initUI() {
		borderPane = new BorderPane();
		 
		borderPane.setStyle("-fx-background-color: #696969;");
		 
		 
		undoButton = new Button("Undo Move");
		undoButton.setId("UndoButton");
	 	undoButton.setFont(new Font(15));
		undoButton.setPrefSize(195, 70);
		undoButton.setStyle("-fx-background-color: #20B2AA; -fx-text-fill: white;");
		undoButton.setOnAction(e -> undoMove() );
		 
		saveButton = new Button("Save Game");
		saveButton.setId("SaveButton"); 
	        saveButton.setPrefSize(195, 70);
	        saveButton.setFont(new Font(15));
	        saveButton.setStyle("-fx-background-color: #20B2AA; -fx-text-fill: white;");
	        saveButton.setOnAction(e -> saveBoard() );

	        openGameButton = new Button("Open Game");
		openGameButton.setId("SaveButton"); 
	        openGameButton.setPrefSize(195, 70);
	     	openGameButton.setFont(new Font(15));
	     	openGameButton.setStyle("-fx-background-color: #20B2AA; -fx-text-fill: white;");
	     	openGameButton.setOnAction(e -> openGame() );
	     
	     	restartButton = new Button("Restart game");
	     	restartButton.setId("restartButton");  
	     	restartButton.setPrefSize(195, 70);
	     	restartButton.setFont(new Font(15));
	     	restartButton.setStyle("-fx-background-color: #20B2AA; -fx-text-fill: white;");
	     	restartButton.setOnAction(e -> restart() );
	     
	     	tournamentButton = new Button("Tournament mode");
	     	tournamentButton.setId("TournmanetButton");  
	     	tournamentButton.setPrefSize(195, 70);
	     	tournamentButton.setFont(new Font(15));
	     	tournamentButton.setStyle("-fx-background-color: #20B2AA; -fx-text-fill: white;");
	     	tournamentButton.setOnAction(e -> tournament());
	     
	     	runButton = new Button("Run Move");
	     	runButton.setId("RunButton");  
	     	runButton.setPrefSize(145, 45);
	     	runButton.setFont(new Font(15));
	     	runButton.setStyle("-fx-background-color: #20B2AA; -fx-text-fill: white;");
	     	runButton.setOnAction(e -> runMove() );
	     
	     	enterMoveTextField1 = new TextField();
	     	enterMoveTextField1.setPrefSize(140, 45);
	     	enterMoveTextField1.setText("Starting move");
	     	enterMoveTextField1.setFont(new Font(15));
	     
	     	enterMoveTextField2 = new TextField();
	     	enterMoveTextField2.setPrefSize(140, 45);
	     	enterMoveTextField2.setText("Ending move");
	     	enterMoveTextField2.setFont(new Font(15));
	     	
	     	successLabel = new Label();
	     	successLabel.setId("SuccessLabel");
	     	successLabel.setText("Success if move runs, fail if move does not run");
	     	successLabel.setFont(new Font(20));
	     	successLabel.setStyle("-fx-text-fill: #e8e6e3");
	     
	     	SuggestedmovesButton = new Button("Turn on Suggested Moves");
	     	SuggestedmovesButton.setId("SuggestedmovesButton");  
	     	SuggestedmovesButton.setPrefSize(195, 45);
	     	SuggestedmovesButton.setFont(new Font(15));
	     	SuggestedmovesButton.setStyle("-fx-background-color: #20B2AA; -fx-text-fill: white;");
	     	SuggestedmovesButton.setOnAction(e -> suggestedmoves() );
	     
	     	UnsuggestmovesButton = new Button("Turn off Suggested Moves");
	     	UnsuggestmovesButton.setId("SuggestedmovesButton");  
	     	UnsuggestmovesButton.setPrefSize(195, 45);
	     	UnsuggestmovesButton.setFont(new Font(15));
	     	UnsuggestmovesButton.setStyle("-fx-background-color: #20B2AA; -fx-text-fill: white;");
	     	UnsuggestmovesButton.setOnAction(e -> unSuggestmoves() );
	     
	     	timerLabel.setId("timerLabel");
		    timerLabel.setText("Elasped Seconds:");
		    timerLabel.setFont(new Font(20));
		    timerLabel.setStyle("-fx-text-fill: #e8e6e3");
		    timefield = new TextField();
		    timefield.setPrefSize(100, 45);
		    timefield.setFont(new Font(15));
		    Time time = new Time();
		    time.Starttime();
	     
	     	runButton = new Button("Run Move");
	     	runButton.setId("RunButton");  
	     	runButton.setPrefSize(100, 45);
	     	runButton.setFont(new Font(15));
	     	runButton.setStyle("-fx-background-color: #20B2AA; -fx-text-fill: white;");
	     	runButton.setOnAction(e -> runMove() );
	     

	     
	     	HBox top = new HBox(5,enterMoveTextField1, enterMoveTextField2, runButton);
	     	top.setAlignment(Pos.TOP_CENTER);
	     	borderPane.setTop(top);
	     
	     	VBox buttons_1 = new VBox(15, undoButton, saveButton, openGameButton, SuggestedmovesButton);
	     	buttons_1.setAlignment(Pos.CENTER_LEFT);
	     	borderPane.setLeft(buttons_1);
	     
	     	VBox buttons_2 = new VBox(15,tournamentButton, restartButton, UnsuggestmovesButton);
	     	buttons_2.setAlignment(Pos.CENTER_RIGHT);
	     	borderPane.setRight(buttons_2);
	     
	     
	     	HBox timerStuff = new HBox (timerLabel, timefield);
		    VBox successlabel = new VBox(successLabel,timerStuff);//timerLabel
		    successlabel.setAlignment(Pos.BOTTOM_CENTER);
		    timerStuff.setAlignment(Pos.CENTER);
		    timefield.setMaxWidth(50);
		    timefield.setAlignment(Pos.CENTER);
		    borderPane.setBottom(successlabel);
	   
	     
	     	var scene = new Scene(borderPane, 450, 400);
	     	stage.setScene(scene);
	     	stage.show();
	     
	 }
	 
	 private MoveOperator m1 = new MoveOperator();
	 private Move move_copy;
	 private boolean tournament_mode = false;
	 private boolean continue_game = false;
	 private String board1_winner = null;
	 private String board2_winner = null;
	 private Board new_board = new Board();
	 
	 
	 public void runMove() {
		 try {
			 if(tournament_mode == true && continue_game == true) {
	    			
	    			this.model.setBoard(new_board.clone());
	    			this.continue_game = false;
	    	}
			 String from;
			 String to;
			 Coordinate coordinate1;
			 Coordinate coordinate2;
			 from = enterMoveTextField1.getText();
			 to = enterMoveTextField2.getText();
			 if (this.model.isSuggestedMovesOn() == true) {
//				 from = enterMoveTextField1.getText();
				 coordinate1 = Utils.parseUserMove(from);
//				 Cell givenCell = new Cell(coordinate1);
				 IteratorBoard itBoard = new IteratorBoard(this.model.board, coordinate1);
				 List<String> suggestedCells = itBoard.getSuggestedCells();
				 System.out.println("From " + from + " to " + suggestedCells);
			 }
			 from = enterMoveTextField1.getText();
			 to = enterMoveTextField2.getText();
			 
			 coordinate1 = Utils.parseUserMove(from);
			 coordinate2 = Utils.parseUserMove(to);
			 Cell from_cell = new Cell(coordinate1);
			 Cell to_cell = new Cell(coordinate2);
			 Move move = new Move(this.model.board,from_cell, to_cell, this.model.board.audiocontrol);
			 this.move_copy = new Move(move);
			 m1.runMove(new MoveCommand(move, from_cell, to_cell));
			 this.model.setBoard(move.getBoard());
			 System.out.println(this.model.board);
			 if(move.move_success == true){
				 this.SetLabelText("Success!");
				 this.model.board.audiocontrol.setNewFile("Sounds/mixkit-unlock-game-notification-253.wav");
					move.subject = this.model.board.audiocontrol;
					move.play();
			 }
			 else{
				 this.SetLabelText("Fail!");
			 }
		 
			 if(this.model.board.isGameOver()) {
				 System.out.println("Winner: "+ this.model.getBoard().getWinner());
				 this.runButton.setDisable(true);
				 if(tournament_mode == true) {
    				 if(this.model.getBoard().getWinner() == Type.GUARD) {
    					 	if(board1_winner != null) {
    					 		board2_winner = "Player4";
    					 	}
    						this.board1_winner = "Player2";
    					}
    				 else {
    						if(board1_winner != null) {
    					 		board2_winner = "Player3";
    					 	}
    						this.board1_winner = "Player1";}
    				 if(this.board1_winner == null || this.board2_winner == null) {
    					this.secondStage();
 		    		}
    				 else {
    					 this.finalStage();
	 		    		 this.tournament_mode =false;}
    			 }
    		 }
			 if(this.undoButton.isDisabled()) {
				 this.undoButton.setDisable(false);
			 }
	    			
	    } catch (InvalidMoveException e) {
			    System.out.println(e.getMessage());

	 }
}
	 
	 public void tournament(){
			this.tournament_mode = true;
			this.continue_game = true;
			System.out.println("Welcome to the ThreeMusketeers tournament, Player1 and Player2 go first");
		 	System.out.println(this.new_board);
		 }
	 
	 public void secondStage() {
		 this.model.setBoard(new_board.clone());
		 this.runButton.setDisable(false);
		 System.out.println("Welcome to the ThreeMusketeers tournament, now Player3 and Player4 turns");
		 System.out.println(this.model.board);
		

	 }
	 
	 public void finalStage() {
		 this.model.setBoard(new_board.clone());
		 this.runButton.setDisable(false);
		 System.out.println("Welcome to the ThreeMusketeers tournament finals: "+ this.board1_winner + " and " + this.board2_winner);
		 System.out.println(this.model.board);
		

	 }
		 
	 
	 public void undoMove() {
		 if(m1.move_commands.isEmpty()) {
			 this.undoButton.setDisable(true);
		 }
		 else {
			 System.out.print("run");
			 m1.undoMove();
			 this.model.setBoard(this.move_copy.getBoard());
			 System.out.println(this.model.board);
		 }
	 }
	 
	 public void saveBoard() {
		 String fileName = "boards\\";
		 JOptionPane saveFile = new JOptionPane();
		 fileName += JOptionPane.showInputDialog("Save file as:");
		 fileName += ".txt";
		 
		 this.model.board.saveBoard(fileName);
	 }
	 
	 public void openGame() {
		 String fileName = "boards\\";
		 JOptionPane getFileName = new JOptionPane();
		 fileName += JOptionPane.showInputDialog("Enter the file name (exclude .txt):");
		 fileName += ".txt";
		 
		 Boolean inBoards = false;
		 
		 
		 ListView<String> files = new ListView();
		 Path p = FileSystems.getDefault().getPath("boards");
		 List<String> fs;
		 try {
			 Stream<Path> s = Files.list(p);
			 List<Path> lst = s.toList();
			 for (int i = 0; i < lst.size(); i++) {
				 String string = lst.get(i).toString();
//				 System.out.println(string + " == " + fileName + ": " + string == fileName);
				 if (string.compareTo(fileName) == 0) {
					 inBoards = true;
				 }
			 }
			 
			 if (inBoards == true) {
				 this.model.board = new Board(fileName);
				 System.out.println(this.model.board);
			 }else if (inBoards == false) {
				 System.out.print("File not found. Please try again.");
			 }
		 } catch (IOException e) {
			 System.out.print("File not found. Please try again.");
		 }
		 
		 
		 
	 }
	 
	 public void restart() {
		 this.model.board = new Board("Boards/Starter.txt");
		 System.out.println(this.model.board);
	 }
	 
	 public void suggestedmoves() {
		 this.model.turnOnSuggestedMoves();
		 System.out.println("Suggested moves is turned on.");
	 }
	 
	 public void unSuggestmoves() {
		 this.model.turnOffSuggestedMoves();
		 System.out.println("Suggested moves is turned off.");
	 }
	 
	 public static void setTimerText(String label) {
		 timefield.setText(label);
	 }
	 
	 public void SetLabelText(String label) {
		 this.successLabel.setText(label);
	 }
	 

}
