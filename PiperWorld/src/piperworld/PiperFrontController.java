/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package piperworld;

import com.sun.deploy.config.Platform;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JFileChooser;

/**
 * FXML Controller class
 *
 * @author SUNDAY
 */
public class PiperFrontController implements Initializable {
    @FXML
    TextField fileEnterTextField;       //access the file textField
    
    @FXML
    TextArea viewFileTextArea;      //access the view for the file
    @FXML TextArea searchResultTextArea;    //Display result of search
    
//    @FXML
//    Label searchResultLabel;        //the search result is deplayed
    
    @FXML TextField searchKeyTextField;
    
    public File file=null;
    
    public String text = null;
    public String key;
    
    Scanner scan;
    
    int counter = 0;
    
    
    public FileReader reader;
    
    private Stage stage;
    
    FileChooser fileChoose;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO        
    }    
    /**
     * This initializes the stage of operation
     */    
    
    public void init(Stage stage) {        
        this.stage = stage;
    }
    /**
     * This method opens file chooser and pass the file into file
     */
    
    public void openFile() throws FileNotFoundException{
        fileChoose = new FileChooser();
//        fileChoose.setInitialDirectory(new File("C:\\Users\\SUNDAY\\Documents\\NetBeansProjects\\IOStream\\src"));
        fileChoose.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("Doc Files", "*.docx"),
                new FileChooser.ExtensionFilter("pdf", "*.pdf")
        );
        fileChoose.setTitle("Select File");
        file = fileChoose.showOpenDialog(stage);
        
        FileReader ot = new FileReader(file);
        
    }
    
    /**
     * This method searches the text and returns the no of time the key shows
     * @param text
     * @param key
     * @return j
     */
    public int checkInFile(String text, String key){
        this.text = text;
        this.key = key;
        
        String textSplit [] = text.split(" ");
        int i,j=0;
        for(i=0;i<textSplit.length;i++){
            if (textSplit[i].equalsIgnoreCase(key)){
                j++;
            }            
        }
        return j;
    }
    
    /**
     * Gets the file from the location and print it on the textArea
     * @param event 
     */
    public void getFileAction(ActionEvent event) throws FileNotFoundException{
        viewFileTextArea.clear();   //clears the Area
        openFile(); //opens File and save it as file
        fileEnterTextField.setText(file.getAbsolutePath());
        
        while(!file.isFile()){
            viewFileTextArea.appendText("File is not a text file or does not exist");
            return;
        }
        
        int lineNo = 1;
        
        scan = new Scanner(file);   //Scans the file to read the line of code
        while(scan.hasNextLine()){
            text = scan.nextLine();
            viewFileTextArea.appendText("\n"+lineNo++ +" "+text);
//            System.out.println(checkInFile(text,"people"));
        }
    }
        
    /**
     * Get the keyword to be searched and searches it
     */
    public void getSearchQuery(ActionEvent event) throws FileNotFoundException{
        //What happens when a search entry is typed
        searchResultTextArea.clear();
        int totalCount = 0;
        int lineNo = 1;
        scan = new Scanner(file);
        key = searchKeyTextField.getText();
        searchResultTextArea.appendText("keyword: "+key+"\n");
        while(scan.hasNextLine()){
            text = scan.nextLine();
            counter = checkInFile(text,key);
            totalCount = totalCount+ counter;
            searchResultTextArea.appendText(" On Line "+lineNo++ +", "+" appears "+counter+" times \n");
        }
        if(totalCount == 0){
           searchResultTextArea.clear();
           searchResultTextArea.appendText("Not Found,");
        }
        searchResultTextArea.appendText(" Total key Found: "+totalCount);
    }
    
}
