package edu.ncsu.csc216.pack_scheduler.course.validator;

/**
 * Finite State Machine for checking whether a Course's Name is valid.
 * 
 * @author Matthew Leonard
 */
public class CourseNameValidator {
    /** The state variable keeps track of the current FSM state we are in */
    private State state;

    /**
     * Returns true if the course name is valid, based on a string matching Finite
     * State Machine.
     * 
     * The course name must match the following format: (1-3 letters)(3
     * digits)(optionally, a 1 letter suffix)
     * 
     * @param courseName the name of the course
     * @return true if the course name is valid, or false if the course name is
     *         invalid
     * @throws InvalidTransitionException when the FSM attempts an invalid
     *                                    transition
     */
    public boolean isValid(String courseName) throws InvalidTransitionException {
        // Set the state field to be the initial FSM state
        state = new InitialState();

        // Create a variable to track the current character index
        int charIndex = 0;

        // Variable to keep track of the current input character being examined
        char c;

        // Iterate through the ID, examining one character at a time
        while (charIndex < courseName.length()) {
            // Set the current character being examined
            c = courseName.charAt(charIndex);

            // Throw an exception if the string contains non alphanumeric characters

            if (Character.isLetter(c)) {
                state.onLetter();
            } else if (Character.isDigit(c)) {
                state.onDigit();
            } else {
                state.onOther();
            }

            charIndex++;

        }

        return state.getClass().equals(new DigitStateTres().getClass())
                || state.getClass().equals(new SuffixState().getClass());
    }

    /**
     * Represents a state for the course name validator FSM, which may switch
     * between states on a letter or digit but will throw an error if not expecting
     * that state, or on anything not a digit or letter.
     *
     * @author Matt
     */
    public abstract class State {
        /**
         * What to do when the state encounters a letter
         * 
         * @throws InvalidTransitionException if the state that implements this course
         *                                    can not make a valid transition when
         *                                    encountering a letter.
         */
        public abstract void onLetter() throws InvalidTransitionException;

        /**
         * What to do when the state encounters a letter
         * 
         * @throws InvalidTransitionException if the state that implements this course
         *                                    can not make a valid transition when
         *                                    encountering a digit.
         */
        public abstract void onDigit() throws InvalidTransitionException;

        /**
         * What to do when the state encounters a character that isn't a letter or a
         * digit
         * 
         * @throws InvalidTransitionException throws every time, as no state supports
         *                                    any character which isn't a character or
         *                                    letter.
         */
        public void onOther() throws InvalidTransitionException {
            throw new InvalidTransitionException("Course name can only contain letters and digits.");
        }
    }

    /**
     * Represents a state where the validator has found a first letter
     *
     * @author Matt
     */
    private class LetterStateUno extends State {
        @Override
        public void onLetter() {
            state = new LetterStateDos();
        }

        @Override
        public void onDigit() {
            state = new DigitStateUno();
        }
    }

    /**
     * Represents a state where the validator has found a second letter
     *
     * @author Matt
     */
    private class LetterStateDos extends State {
        @Override
        public void onLetter() {
            state = new LetterStateTres();
        }

        @Override
        public void onDigit() {
            state = new DigitStateUno();
        }
    }

    /**
     * Represents a state where the validator has found a third letter
     *
     * @author Matt
     */
    private class LetterStateTres extends State {
        @Override
        public void onLetter() {
            state = new LetterStateCuatro();
        }

        @Override
        public void onDigit() {
            state = new DigitStateUno();
        }
    }

    /**
     * Represents a state where the validator has found a fourth letter
     *
     * @author Matt
     */
    private class LetterStateCuatro extends State {
        @Override
        public void onLetter() throws InvalidTransitionException {
            throw new InvalidTransitionException("Course name cannot start with more than 4 letters.");
        }

        @Override
        public void onDigit() {
            state = new DigitStateUno();
        }
    }

    /**
     * Represents a state where the validator has found a first digit
     *
     * @author Matt
     */
    private class DigitStateUno extends State {
        @Override
        public void onLetter() throws InvalidTransitionException {
            throw new InvalidTransitionException("Course name must have 3 digits.");
        }

        @Override
        public void onDigit() {
            state = new DigitStateDos();
        }
    }

    /**
     * Represents a state where the validator has found a second digit
     *
     * @author Matt
     */
    private class DigitStateDos extends State {
        @Override
        public void onLetter() throws InvalidTransitionException {
            throw new InvalidTransitionException("Course name must have 3 digits.");
        }

        @Override
        public void onDigit() {
            state = new DigitStateTres();
        }
    }

    /**
     * Represents a state where the validator has found a third digit
     *
     * @author Matt
     */
    private class DigitStateTres extends State {
        @Override
        public void onLetter() {
            state = new SuffixState();
        }

        @Override
        public void onDigit() throws InvalidTransitionException {
            throw new InvalidTransitionException("Course name can only have 3 digits.");

        }
    }

    /**
     * Represents a state where the validator has just started
     *
     * @author Matt
     */
    private class InitialState extends State {
        @Override
        public void onLetter() {
            state = new LetterStateUno();

        }

        @Override
        public void onDigit() throws InvalidTransitionException {
            throw new InvalidTransitionException("Course name must start with a letter.");
        }
    }

    /**
     * Represents a state where the validator has found a suffix
     *
     * @author Matt
     */
    private class SuffixState extends State {
        @Override
        public void onLetter() throws InvalidTransitionException {
            throw new InvalidTransitionException("Course name can only have a 1 letter suffix.");
        }

        @Override
        public void onDigit() throws InvalidTransitionException {
            throw new InvalidTransitionException("Course name cannot contain digits after the suffix.");

        }
    }
}