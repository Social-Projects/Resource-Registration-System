package org.registrator.community.exceptions;

/**
 * Thrown when there is no resource entity by identifier
 */
public class ResourceEntityNotFound extends RegistratorException {
    private String identifier;

    public ResourceEntityNotFound(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }
}
