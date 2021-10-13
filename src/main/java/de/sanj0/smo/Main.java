package de.sanj0.smo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.err.println("Expecting at least one arg (state machine file)!");
            System.exit(1);
        }
        final File stateMachineFile = new File(args[0]);
        final StateMachine stateMachine = StateMachineParser.parse(Files.readAllLines(stateMachineFile.toPath()));

        if (args.length == 2) {
            if (stateMachine.test(args[0])) {
                System.out.println("pass!");
            } else {
                System.err.println("no pass!");
            }
        } else {
            final Scanner stdin = new Scanner(System.in);
            String in = stdin.nextLine();
            while (!in.equalsIgnoreCase("exit") && !in.equalsIgnoreCase("quit")) {
                if (stateMachine.test(in)) {
                    System.out.println("pass!");
                } else {
                    System.err.println("no pass!");
                }
                in = stdin.nextLine();
            }
        }
    }
}
