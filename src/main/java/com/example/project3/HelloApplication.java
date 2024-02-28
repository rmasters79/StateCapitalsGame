//Ross Masters
//Project 3
package com.example.project3;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class HelloApplication extends Application {

    //HashMap to store states/capitals as keys/values
    HashMap<String, String> statesMap = new HashMap<>();

    int index = 0;
    int numberCorrect = 0;
    int totalGuesses = 0;

    //init() method uses Scanner to read from txt file and fills
    //hashmap with state/capital pairs at program start-up
    //txt file is located in resources - statecapitals.txt
    @Override
    public void init() throws Exception {
        File text = new File("src/main/resources/statecapitals.txt");
        System.out.println("File Found.");
        Scanner read = new Scanner(text);
        for(int i = 0; i < 18; i++){
            String state = read.nextLine();     //reads in state from lines 1, 3, 5, etc.
            String capital = read.nextLine();   //reads in capitals from lines 2, 4, 6, etc.
            statesMap.put(state, capital);      //puts key/value pairs in hashmap
        }
        System.out.println(statesMap);          //double-checks contents by printing to console

    }

    @Override
    public void start(Stage stage) throws IOException {

        //Creates a shuffled array list of all the states
        ArrayList<String> stateList = new ArrayList<>(statesMap.keySet());
        Collections.shuffle(stateList);

        //Labels for state/capital names, correctness message, and the score
        Label stateLabel = new Label("Press 'next' to begin");
        stateLabel.setFont(new Font("Calibri", 30));
        Label capitalLabel = new Label("");
        capitalLabel.setFont(new Font("Calibri", 30));
        Label correctLabel = new Label("");
        correctLabel.setFont(new Font("Calibri", 20));
        Label scoreLabel = new Label("");
        scoreLabel.setFont(new Font("Calibri", 20));

        //Text field for the user to enter their answer
        TextField answerField = new TextField();

        //Buttons to check answer and move onto the next state
        Button checkButton = new Button("Check");
        Button nextButton = new Button("Next");

        //Place all nodes in vboxes and into the scene
        VBox topLabels = new VBox(stateLabel, capitalLabel, correctLabel);
        topLabels.setAlignment(Pos.CENTER);
        VBox buttons = new VBox(checkButton, nextButton);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(20));
        buttons.setSpacing(20);
        VBox root = new VBox(topLabels, answerField, buttons, scoreLabel);
        Scene scene = new Scene(root, 400, 400);

        stage.setTitle("State Capitals Flash Card Game");
        stage.setScene(scene);
        stage.show();

        nextButton.setOnAction(e->{
            //Clear previous question
            capitalLabel.setText("");
            correctLabel.setText("");
            if(index < 18){
                //Get a state to display from the state list
                String listKey =  stateList.get(index);
                stateLabel.setText(listKey);
                index++;
            } else {
                //End of game, display message and percentage correct
                stateLabel.setText("Game over!");
                Formatter formatter = new Formatter();
                double percentCorrect = (double) numberCorrect / totalGuesses * 100;
                formatter.format("%.2f", percentCorrect);
                capitalLabel.setText("You got " + formatter + "% correct");
            }
        });

        checkButton.setOnAction(e->{
            if(index < 18){
                //Get user answer and correct one from the initial hashmap
                String currentState = stateLabel.getText();
                String userAnswer = answerField.getText();
                String correctAnswer = statesMap.get(currentState);
                //Answer field left blank
                if(answerField.getText().equals("")){
                    capitalLabel.setText("Please enter an answer");
                } else {
                    if (correctAnswer.equalsIgnoreCase(userAnswer)) {
                        numberCorrect++;
                        correctLabel.setText("Correct!");
                    } else {
                        correctLabel.setText("Incorrect! The answer was " + correctAnswer);
                    }
                    totalGuesses++;
                    capitalLabel.setText(userAnswer);
                    scoreLabel.setText("Correct: " + numberCorrect + " Total: " + totalGuesses);
                }
                answerField.setText("");
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}