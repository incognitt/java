import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;
import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random rand = new Random();
        // String baseString = scanner.toString();
        String baseString = "EARTH";
        String chifrovStroka;
        String deShifrStroka;
        System.out.println("RSA Algorithm");
        System.out.println("Enter two numbers:");
        int numb_1, numb_2, result, phi, e;
        System.out.print("Number 1 = ");
        //numb_1 = scanner.nextInt();
        numb_1=11;
        double d;
        System.out.print("Number 2 = ");
        //numb_2 = scanner.nextInt();
        numb_2=7;

        // 2
        result = numb_1 * numb_2;
        System.out.println(result);

        // 3
        phi = (numb_1 - 1) * (numb_2 - 1);
        System.out.println("φ(n)=" + phi);

        // 4 Calculate the value of d
        Random random = new Random();
        e = random.nextInt(phi + 1); // Генерирует случайное число от 0 до phi включительно

        while (gcd(e, phi) != 1) {
            e = random.nextInt(phi + 1);
        }

        int dInt = modInverse(e, phi);

        System.out.println("Value of d: " + dInt);
        chifrovStroka = stroka(baseString, baseString.length());

        // Encryption
        int[] message = new int[chifrovStroka.length()];
        for (int i = 0; i < chifrovStroka.length(); i++) {
            message[i] = chifrovStroka.charAt(i) - 'A' + 1; // Преобразование символов в числа (A=1, B=2, ...)
        }

        int[] encrypted = encrypt(message, new int[]{e, result});
        System.out.println("Encrypted message: " + Arrays.toString(encrypted));

        // Decryption
        int[] decrypted = decrypt(encrypted, new int[]{dInt, result});
        System.out.println("Decrypted message: " + Arrays.toString(decrypted));
        System.out.print("зашифрованная ");
        printMessage(encrypted);
        System.out.print("расшифрованная");
        printMessage(decrypted);
    }

    // ... остальной код остается без изменений


    public static int modInverse(int a, int m) {
        int m0 = m;
        int x0 = 0;
        int x1 = 1;

        if (m == 1) {
            return 0;
        }

        if (m == 0) {
            throw new ArithmeticException("Modulus m cannot be zero.");
        }

        while (a > 1) {
            int q = a / m;
            int t = m;

            m = a % m;
            a = t;
            t = x0;

            x0 = x1 - q * x0;
            x1 = t;
        }

        if (x1 < 0) {
            x1 += m0;
        }

        return x1;
    }

    public static String stroka(String str, int size) {
        char shifr_stroka[] = str.toCharArray();
        char itogovayaStroka[] = new char[size];
        char stringAllSimbols[] = new char[52];
        int indexArray[] = new int[size];
        for (int c = 0; c < 26; c++) {
            char startHighChar = (char) ('A' + c);
            stringAllSimbols[c] = startHighChar;

            char startLowChar = (char) ('a' + c);
            stringAllSimbols[c + 26] = startLowChar;
        }
        for (int i = 0; i < 52; i++) {
            System.out.print(stringAllSimbols[i] + " ");
        }
        System.out.println("\nInitial string:");
        for (int i = 0; i < size; i++) {
            System.out.print(shifr_stroka[i] + " ");
        }

        String originalString = new String(shifr_stroka); // Сохраняем исходную строку

        Arrays.sort(shifr_stroka); // Сортируем строку для шифрования

        System.out.println("\nString ready for encryption:");

        StringBuilder stringBuilder = new StringBuilder();

        for (char symbol : shifr_stroka) {
            stringBuilder.append(symbol);
        }

        String sortedString = stringBuilder.toString();
        System.out.println("Sorted string: " + sortedString);

        return originalString; // Возвращаем исходную строку
    }



        // RSA Encryption
    public static int[] encrypt(int[] message, int[] key) {
        int[] encrypted = new int[message.length];
        for (int i = 0; i < message.length; i++) {
            encrypted[i] = modPow(message[i], key[0], key[1]);
        }
        return encrypted;
    }

    // RSA Decryption
    public static int[] decrypt(int[] encrypted, int[] key) {
        int[] decrypted = new int[encrypted.length];
        for (int i = 0; i < encrypted.length; i++) {
            decrypted[i] = modPow(encrypted[i], key[0], key[1]);
        }
        return decrypted;
    }

    // Helper function for modular exponentiation
    public static int modPow(int base, int exponent, int modulus) {
        int result = 1;
        while (exponent > 0) {
            if (exponent % 2 == 1) {
                result = (result * base) % modulus;
            }
            base = (base * base) % modulus;
            exponent = exponent / 2;
        }
        return result;
    }

    public static void printMessage(int[] message) {
        StringBuilder messageBuilder = new StringBuilder();

        for (int i = 0; i < message.length; i++) {
            char character = (char) ('A' + message[i] - 1); // Предполагаем, что 'A' соответствует 1, 'B' соответствует 2 и так далее
            messageBuilder.append(character);
        }

        System.out.println("строка: " + messageBuilder.toString());
    }

    public static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

}
