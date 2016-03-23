package org.registrator.community.enumeration;

public enum RegistrationMethod {
       
        PERSONAL("PERSONAL"),
        MANUAL("MANUAL"),
        MIXED("MIXED");
        
        private final String REGISTRATION_METHOD;
        
        private RegistrationMethod(final String registrationMethod) {
            this.REGISTRATION_METHOD = registrationMethod;
        }
        
        @Override
        public String toString() {
            return REGISTRATION_METHOD;
        }
}

