package de.sanj0.smo;

import java.util.ArrayList;
import java.util.List;

public class StateTransition {
    private final String origin;
    private final List<Character> transitionChars;
    private final String destination;

    public StateTransition(final String origin, final List<Character> transitionChars, final String destination) {
        this.origin = origin;
        this.transitionChars = transitionChars;
        this.destination = destination;
    }

    // returns the destination if c equals the transition char
    // and null if it doesn't
    public String testTransition(final char c) {
        if (transitionChars.contains(c)) {
            return destination;
        } else {
            return null;
        }
    }

    /**
     * Gets {@link #origin}.
     *
     * @return the value of {@link #origin}
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * Gets {@link #transitionChars}.
     *
     * @return the value of {@link #transitionChars}
     */
    public List<Character> getTransitionChars() {
        return transitionChars;
    }

    /**
     * Gets {@link #destination}.
     *
     * @return the value of {@link #destination}
     */
    public String getDestination() {
        return destination;
    }

    // always passes
    public static class DefaultStateTransition extends StateTransition {
        public DefaultStateTransition(final String origin, final String destination) {
            super(origin, new ArrayList<>(), destination);
        }

        @Override
        public String testTransition(final char c) {
            return getDestination();
        }
    }
}
