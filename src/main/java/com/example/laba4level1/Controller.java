package com.example.laba4level1;

import javafx.fxml.FXML;
import javafx.print.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.Scanner;

public class Controller {
    Clipboard clipboard = Clipboard.getSystemClipboard();
    ClipboardContent content = new ClipboardContent();
    @FXML
    private TextArea textArea;
    String currentText;
    String pathToFile;
    FileChooser fc = new FileChooser();
    public void ClickOnOpenFile(){
        File file = fc.showOpenDialog(new Stage());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()){
                textArea.appendText(scanner.nextLine() + "\n");
            }
            currentText = textArea.getText();
            pathToFile = file.getPath();
        }
        catch (FileNotFoundException exception){
            exception.printStackTrace();
        }
    }
    public void ClickOnSaveFile(){
        if (pathToFile == null){
            ClickOnSaveAsFile();
            return;
        }
        File file = new File(pathToFile);
        doSave(file);
    }

    public void ClickOnSaveAsFile(){
        File file = fc.showSaveDialog(new Stage());
        if (file != null){
            doSave(file);
        }

    }

    void doSave(File file){
        try {
            PrintWriter writer = new PrintWriter(file);
            writer.write(textArea.getText());
            currentText = textArea.getText();
            writer.close();
        }
        catch (FileNotFoundException exception){
            exception.printStackTrace();
        }
    }

    public void ClickOnCloseFile(){
        if(isSaveChange()){
            textArea.setText("");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert");
        alert.setHeaderText("Changes are not save!");
        ButtonType type = new ButtonType("Save?", ButtonBar.ButtonData.YES);
        alert.getDialogPane().getButtonTypes().add(type);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get().getButtonData() == ButtonBar.ButtonData.YES){
            if (pathToFile == null){
                ClickOnSaveFile();
            }
            ClickOnSaveAsFile();
        }
    }

    public void ClickOnCopyText(){
        content.putString(textArea.getText());
        clipboard.setContent(content);
        ;
    }

    public void ClickOnCutText(){
        ClickOnCopyText();
        textArea.setText("");
    }
    public void ClickOnPasteText(){
        if (clipboard.hasContent(DataFormat.PLAIN_TEXT)){
            textArea.appendText(clipboard.getString());
        }
    }
    public void ClickOnPrintersInfo(){

    }
    public void ClickOnAbout(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Information about program");
        alert.setContentText("Version: 1.0\n" +
                "Text editor is created and can doing some actions on the text such as edit, cut, copy and paste\n" +
                "Also editor can read and save files");
        alert.show();
    }

    private boolean isSaveChange(){
        return textArea.getText().equals(currentText);
    }
}