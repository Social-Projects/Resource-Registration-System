package org.registrator.community.enumeration;

public enum UserStatus {
    BLOCK("BLOCK"),
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE"),
	NOTCOMFIRMED("NOTCOMFIRMED");
    
    private final String STATUS;
    
    private UserStatus(final String status) {
        this.STATUS = status;
    }
    
    @Override
    public String toString() {
        return STATUS;
    }
}