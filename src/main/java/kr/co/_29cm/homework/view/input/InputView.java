package kr.co._29cm.homework.view.input;

import java.util.Scanner;

public class InputView {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static String input() {
        return SCANNER.nextLine();
    }
}
