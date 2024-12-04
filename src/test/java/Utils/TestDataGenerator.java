package Utils;

import java.util.Random;
import java.util.UUID;

/**
 * Utility class for generating test data
 */
public class TestDataGenerator {

    public static String generateRandomEmail() {
        String letters = "abcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuilder prefix = new StringBuilder();
        int prefixLength = random.nextInt(10) + 5;
        for (int i = 0; i < prefixLength; i++) {
            prefix.append(letters.charAt(random.nextInt(letters.length())));
        }
        String uniqueID = UUID.randomUUID().toString().substring(0, 8);
        String[] domains = {"@exemplo.com", "@dominio.net", "@email.org"};
        String domain = domains[random.nextInt(domains.length)];
        return prefix + uniqueID + domain;
    }

    public static String generateRandomPassword(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        String specialCharacters = "@#$%&/()=?";
        java.util.Random random = new java.util.Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length - 1; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }

        sb.append(specialCharacters.charAt(random.nextInt(specialCharacters.length())));
        return sb.toString();
    }

    public static String generateName(int lastName) {
        String consonants = "bcdfghjklmnpqrstvwxyz";
        String vowels = "aeiou";
        java.util.Random randomName = new java.util.Random();
        StringBuilder sbName = new StringBuilder();
        for (int i = 0; i < lastName; i++) {
            if (i % 2 == 0) {
                sbName.append(consonants.charAt(randomName.nextInt(consonants.length())));
            } else {
                sbName.append(vowels.charAt(randomName.nextInt(vowels.length())));
            }
        }
        return sbName.toString().substring(0, 1).toUpperCase() + sbName.toString().substring(1);
    }
}
