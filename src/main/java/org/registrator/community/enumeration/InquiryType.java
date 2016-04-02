package org.registrator.community.enumeration;

public enum InquiryType {
    INPUT("INPUT"),
    OUTPUT("OUTPUT");
    
    private final String TYPE;
    
    private InquiryType(final String type) {
        this.TYPE = type;
    }
    
    @Override
    public String toString() {
        return TYPE;
    }
}
