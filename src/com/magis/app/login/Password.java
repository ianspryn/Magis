package com.magis.app.login;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.transitions.JFXFillTransition;
import com.magis.app.models.StudentModel;
import javafx.scene.layout.GridPane;
import sun.misc.BASE64Decoder;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;

public class Password {

    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public static String hash(String password, String salt) {
        try {
            KeySpec spec = new PBEKeySpec(password.toCharArray(), Base64.getDecoder().decode(salt), 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hash);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean longEnough(String password) {
        return password.length() >= 8;
    }

    public static boolean containsUpperCase(String password) {
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) return true;
        }
        return false;
    }

    public static boolean containsLowerCase(String password) {
        for (char c : password.toCharArray()) {
            if (Character.isLowerCase(c)) return true;
        }
        return false;
    }

    public static boolean containsDigit(String password) {
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) return true;
        }
        return false;
    }

    /**
     * Check of the password string matches the hashed version associated with the account
     * @param student the student to check the password against
     * @param password the password in string literal form
     * @return true if it matches, false otherwise
     */
    public static boolean passwordMatches(StudentModel.Student student, String password) {
        return student.getPasswordHash().equals(Password.hash(password, student.getSalt()));
    }

    public static boolean checkRequirements(String password, GridPane passwordVerifierGridPane) {
        JFXCheckBox longEnoughCheck = (JFXCheckBox) passwordVerifierGridPane.getChildren().get(0);
        JFXCheckBox containsUpperCaseCheck = (JFXCheckBox) passwordVerifierGridPane.getChildren().get(2);
        JFXCheckBox containsLowerCaseCheck = (JFXCheckBox) passwordVerifierGridPane.getChildren().get(4);
        JFXCheckBox containsNumberCheck = (JFXCheckBox) passwordVerifierGridPane.getChildren().get(6);

        if (longEnough(password)) longEnoughCheck.setSelected(true);
        else longEnoughCheck.setSelected(false);
        if (containsUpperCase(password)) containsUpperCaseCheck.setSelected(true);
        else containsUpperCaseCheck.setSelected(false);
        if (containsLowerCase(password)) containsLowerCaseCheck.setSelected(true);
        else containsLowerCaseCheck.setSelected(false);
        if (containsDigit(password)) containsNumberCheck.setSelected(true);
        else containsNumberCheck.setSelected(false);

        return longEnoughCheck.isSelected() && containsUpperCaseCheck.isSelected() && containsLowerCaseCheck.isSelected() && containsNumberCheck.isSelected();
    }


}
