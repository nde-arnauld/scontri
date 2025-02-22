package utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.time.format.DateTimeParseException;

public class DateTaker {
		
	private static Scanner scanner = new Scanner(System.in);

	public static LocalDate saisirDate(String message) {
        while (true) {
            try {
                System.out.print(message);
                String dateStr = scanner.nextLine();
                return LocalDate.parse(dateStr); 
            } catch (DateTimeParseException e) {
                System.out.println("Format invalide ! Veuillez entrer la date au format YYYY-MM-DD.");
            }
        }
    }
	
	
	public static LocalDateTime saisirDateTime(String message) {
        while (true) {
            try {
                System.out.print(message);
                String input = scanner.nextLine();
                return LocalDateTime.parse(input.replace(" ", "T")); 
            } catch (Exception e) {
                System.out.println("❌ Format invalide, veuillez entrer une date correcte (yyyy-MM-dd HH:mm).");
            }
        }
    }

}
