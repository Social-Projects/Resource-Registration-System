package org.registrator.community.enumeration;

public enum RoleType{
    USER("USER"),
    REGISTRATOR("REGISTRATOR"),
    ADMIN("ADMIN"),
    COMMISSIONER("COMMISSIONER");
    
    private final String TYPE;
    
    private RoleType(final String TYPE) {
        this.TYPE = TYPE;
    }
    
    @Override
    public String toString() {
        return TYPE;
    }
}