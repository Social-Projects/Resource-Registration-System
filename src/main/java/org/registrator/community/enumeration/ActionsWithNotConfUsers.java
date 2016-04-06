package org.registrator.community.enumeration;

public enum ActionsWithNotConfUsers {
    DELETE("DELETE"),
    SENDEMAILAGAIN("SENDEMAILAGAIN");
    
    private final String ACTIONS;

    private ActionsWithNotConfUsers(final String actions) {
        this.ACTIONS = actions;
    }
    
    @Override
    public String toString() {
        return ACTIONS;
    }
    

}
