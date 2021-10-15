package de.sanj0.smo;

import java.security.SecureRandom;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class StateMachine {

    private static final Random RNG = new SecureRandom();

    private Map<String, List<StateTransition>> states;
    private String initState;
    private String currentState;
    private Set<String> endStates;

    public StateMachine(final Map<String, List<StateTransition>> states, final String initState, final Set<String> endStates) {
        this.states = states;
        this.initState = initState;
        this.endStates = endStates;
    }

    public String generateRandom() {
        currentState = initState;
        final StringBuilder gen = new StringBuilder(15);
        while (!(endStates.contains(currentState) && RNG.nextBoolean())) {
            final List<StateTransition> transitions = states.get(currentState);
            if (transitions.isEmpty()) {
                break;
            }
            final StateTransition transition = transitions.get(RNG.nextInt(transitions.size()));
            gen.append(transition.getTransitionChars().get(RNG.nextInt(transition.getTransitionChars().size())));
            currentState = transition.getDestination();
        }
        return gen.toString();
    }

    public boolean test(final String input) {
        currentState = initState;
        final char[] inChars = input.toCharArray();
        final StringBuilder path = new StringBuilder(input.length() * 4);
        path.append("->").append(endStates.contains(initState) ? "[[" : "[").append(initState).append(endStates.contains(initState) ? "]]" : ']');
        for (final char c : inChars) {
            for (final StateTransition transition : states.get(currentState)) {
                final String dst = transition.testTransition(c);
                if (dst != null) {
                    path.append("->'").append(c).append(endStates.contains(dst) ? "'[[" : "'[").append(dst).append(endStates.contains(dst) ? "]]" : ']');
                    if (dst.equals("err")) {
                        System.err.println("error state reached after char '" + c + "' from state '" + currentState + "'");
                        System.exit(1);
                    }
                    currentState = dst;
                    break;
                }
            }
        }
        System.out.println(path);
        return endStates.contains(currentState);
    }
}
