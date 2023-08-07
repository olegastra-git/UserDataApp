import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class UserDataApp {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите данные в формате: Фамилия Имя Отчество датарождения номертелефона пол");
            String input = scanner.nextLine();

            String[] data = input.split(" ");
            if (data.length != 6) {
                throw new IllegalArgumentException("Введено неверное количество данных");
            }

            String lastName = data[0];
            String firstName = data[1];
            String middleName = data[2];
            String birthDateStr = data[3];
            String phoneNumberStr = data[4];
            String genderStr = data[5];

            LocalDate birthDate = parseBirthDate(birthDateStr);
            long phoneNumber = parsePhoneNumber(phoneNumberStr);
            Gender gender = parseGender(genderStr);

            String fileName = lastName + ".txt";
            String fileData = lastName + firstName + middleName + birthDateStr + " " + phoneNumberStr + genderStr;

            saveToFile(fileName, fileData);

            System.out.println("Данные успешно сохранены в файл " + fileName);
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static LocalDate parseBirthDate(String birthDateStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            return LocalDate.parse(birthDateStr, formatter);
        } catch (Exception e) {
            throw new IllegalArgumentException("Некорректный формат даты рождения");
        }
    }

    private static long parsePhoneNumber(String phoneNumberStr) {
        try {
            return Long.parseLong(phoneNumberStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Некорректный формат номера телефона");
        }
    }

    private static Gender parseGender(String genderStr) {
        if (genderStr.equalsIgnoreCase("m")) {
            return Gender.MALE;
        } else if (genderStr.equalsIgnoreCase("f")) {
            return Gender.FEMALE;
        } else {
            throw new IllegalArgumentException("Некорректное значение пола");
        }
    }

    private static void saveToFile(String fileName, String fileData) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(fileData);
            writer.newLine();
        }
    }

    private enum Gender {
        MALE, FEMALE
    }
}