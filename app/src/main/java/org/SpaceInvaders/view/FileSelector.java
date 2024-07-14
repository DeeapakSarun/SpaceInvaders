package org.SpaceInvaders.view;

import javax.swing.JFileChooser;
import java.io.File;

public class FileSelector {
    public static String selectFile() {
        // Set the default directory to the resources folder
        String defaultDirectory = "C:\\Users\\Bhumu\\csci2300\\team-project-teamtwo\\app\\src\\main\\resources\\SaveState.dat"; // Change this to the actual path of your resources folder
        JFileChooser fileChooser = new JFileChooser(defaultDirectory);
        int result = fileChooser.showSaveDialog(null); // Change to showOpenDialog for opening files
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            return selectedFile.getAbsolutePath();
        } else {
            return null; // User canceled the operation
        }
    }
}
