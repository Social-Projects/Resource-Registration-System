package org.registrator.community.enumeration;
public enum ResourceStatus{
    ACTIVE("ACTIVE"),
    UNCHECKED("UNCHECKED"),
    DENIDED("DENIDED"),
    OBSOLETE("OBSOLETE"),;
    
    private final String STATUS;
    
    private ResourceStatus(final String status) {
        this.STATUS = status;
    }
    
    @Override
    public String toString() {
        return STATUS;
    }
}