package org.registrator.community.exceptions;

/**
 * Thrown when there is no resource entity by identifier
 */
public class ResourceEntityNotFound extends AbstractRegistratorException {
    private String identifier;

    public ResourceEntityNotFound(String identifier) {
        super();
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }
}
