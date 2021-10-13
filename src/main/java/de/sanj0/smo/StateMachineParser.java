package de.sanj0.smo;

import java.util.*;

public class StateMachineParser {

    public static StateMachine parse(final List<String> lines) {
        final Map<String, List<StateTransition>> states = new HashMap<>();
        String initState = null;
        final Set<String> endStates = new HashSet<>();

        int index = -1;
        for (final String line : lines) {
            index++;
            if (!line.startsWith("[")) continue; // comment
            final String[] hands = line.split(":", 2); // left hand = declaration, right hand = definition of transitions
            final String declaration = hands[0].trim();
            final String definitions = hands[1];
            String stateName = null;
            if (line.startsWith("[[")) {
                if (declaration.endsWith("]]")) {
                    stateName = declaration.substring(2, declaration.length() - 2);
                    endStates.add(stateName);
                } else {
                    System.err.println("Mismatched brackets in line " + index + " near '" + declaration + "'");
                    System.exit(1);
                }
            } else if (declaration.endsWith("]")) {
                stateName = declaration.substring(1, declaration.length() - 1);
            } else {
                System.err.println("Mismatched brackets in line " + index + " near '" + declaration + "'");
                System.exit(1);
            }
            if (initState == null) initState = stateName;

            final String[] transitionDecl = definitions.split(",");
            final List<StateTransition> transitionList = new ArrayList<>(transitionDecl.length);
            for (final String decl : transitionDecl) {
                final String[] transitionHands = decl.split("->", 2);
                final String chars = transitionHands[0].trim();
                final String dst = transitionHands[1].trim();
                if (chars.isEmpty()) {
                    transitionList.add(new StateTransition.DefaultStateTransition(stateName, dst.substring(1, dst.length() - 1)));
                } else {
                    transitionList.add(new StateTransition(stateName, asList(chars), dst.substring(1, dst.length() - 1)));
                }
            }
            states.put(stateName, transitionList);
        }

        return new StateMachine(states, initState, endStates);
    }

    public static List<Character> asList(final String string) {
        return new AbstractList<>() {
            public int size() { return string.length(); }
            public Character get(int index) { return string.charAt(index); }
        };
    }
}
