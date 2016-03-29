package org.registrator.community.enumeration;

public enum TokenType {
	RECOVER_PASSWORD("RECOVER_PASSWORD"),
	CONFIRM_EMAIL("CONFIRM_EMAIL");
    
    private final String TYPE;
    
    private TokenType(final String TYPE) {
        this.TYPE = TYPE;
    }
    
    @Override
    public String toString() {
        return TYPE;
    }
}
