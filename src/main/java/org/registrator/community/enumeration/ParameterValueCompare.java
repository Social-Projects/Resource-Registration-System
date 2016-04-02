package org.registrator.community.enumeration;

/**
 * Compare operation for resource search by parameters values
 */
public enum ParameterValueCompare {
    LESS("less") ,
    GREATER("greater"),
    EQUAL("equal"),
    LINEAR("linear");

    /**
     * String representation of enumeration instance, used to pass parameters from client
     */
    private String compareSign;

    /**
     * Constructor of enumeration instances
     * @param compareSign representation of operation on client-side
     */
    ParameterValueCompare(String compareSign) {
        this.compareSign = compareSign;
    }


    public static ParameterValueCompare from(String compareSign) {
        for (ParameterValueCompare value : values()) {
            if (value.compareSign.equalsIgnoreCase(compareSign)) {
                return value;
            }
        }
        throw new IllegalArgumentException(String.format("There is no comparison operation with compare sign '%s'",
                compareSign));
    }
}
