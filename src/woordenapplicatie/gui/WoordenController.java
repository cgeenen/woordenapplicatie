package woordenapplicatie.gui;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author frankcoenen
 */
public class WoordenController implements Initializable {
    
   private static final String DEFAULT_TEXT =   "Een, twee, drie, vier\n" +
                                                "Hoedje van, hoedje van\n" +
                                                "Een, twee, drie, vier\n" +
                                                "Hoedje van papier\n" +
                                                "\n" +
                                                "Heb je dan geen hoedje meer\n" +
                                                "Maak er één van bordpapier\n" +
                                                "Eén, twee, drie, vier\n" +
                                                "Hoedje van papier\n" +
                                                "\n" +
                                                "Een, twee, drie, vier\n" +
                                                "Hoedje van, hoedje van\n" +
                                                "Een, twee, drie, vier\n" +
                                                "Hoedje van papier\n" +
                                                "\n" +
                                                "En als het hoedje dan niet past\n" +
                                                "Zetten we 't in de glazenkas\n" +
                                                "Een, twee, drie, vier\n" +
                                                "Hoedje van papier";
    
    @FXML
    private String[] words;
    @FXML
    private Button btAantal;
    @FXML
    private TextArea taInput;
    @FXML
    private Button btSorteer;
    @FXML
    private Button btFrequentie;
    @FXML
    private Button btConcordantie;
    @FXML
    private TextArea taOutput;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        taInput.setText(DEFAULT_TEXT);
        words = DEFAULT_TEXT.split("\\s+");
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].replaceAll("[^\\w]", "");
        }
    }
    
    @FXML
    private void aantalAction(ActionEvent event) {
        taOutput.clear();
        taOutput.setText("Totaal aantal woorden: " + words.length);
        Set<String> wordsSet = new HashSet<>(Arrays.asList(words));
        taOutput.appendText("\nAantal verschillende woorden: " + wordsSet.size());
    }

    @FXML
    private void sorteerAction(ActionEvent event) {
        taOutput.clear();
        
        Set<String> treeSet = new TreeSet<>(Collections.reverseOrder());
        treeSet.addAll(Arrays.asList(words));
        
        for(String word : treeSet){
            taOutput.appendText("\n"+word);
        }
    }

    @FXML
    private void frequentieAction(ActionEvent event) {
        Map<String, Integer> map = new HashMap<>();
        for (String w : words) {
            Integer n = map.get(w);
            n = (n == null) ? 1 : ++n;
            map.put(w, n);
        }
        
        for (Map.Entry<String,Integer> entry : map.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            
            taOutput.appendText("\n" + key + ": " + value);
        }
    }

    @FXML
    private void concordatieAction(ActionEvent event) {
        TreeMap<String, LinkedList<Integer>> concordance = new TreeMap<String, LinkedList<Integer>>();
        
        for (int i = 0; i < words.length; i++) {
            //Original
            for (String s : words[i].split("[^a-zA-Z]+")) {
            //Without words in that occur multiple times in one line
            //for (String s : new HashSet<String>(Arrays.asList(splitLines[i].split("[^a-zA-Z]+")))) {
                if (!concordance.containsKey(s) || concordance.get(s) == null) {
                    LinkedList ll = new LinkedList<>();
                    ll.add(i + 1);
                    concordance.put(s, ll);
                } else {
                    LinkedList ll = concordance.get(s);
                    ll.add(i + 1);
                }
            }
        }
        String out = "";
        for (Entry<String, LinkedList<Integer>> e : concordance.entrySet()){
            out = out + String.format("%-20s\t%s\n", e.getKey() + ":", e.getValue().toString());
        }
        taOutput.setText(out);
    }
   
}
