package de.sanj0.smo;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class StateMachine {

    private Map<String, List<StateTransition>> states;
    private String currentState;
    private Set<String> endStates;

    public StateMachine(final Map<String, List<StateTransition>> states, final String initState, final Set<String> endStates) {
        this.states = states;
        this.currentState = initState;
        this.endStates = endStates;
    }

    public boolean test(final String input) {
        final char[] inChars = input.toCharArray();
        final StringBuilder path = new StringBuilder(input.length() * 4);
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
