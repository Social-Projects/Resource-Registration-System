package org.registrator.community.exceptions;

/**
 * Thrown when there is no resource entity by identifier
 */
public class ResourceEntityNotFound extends AbstractRegistratorException {
    private String identifier;

    public ResourceEntityNotFound(String identifier) {
        this.identifier = identifier;
    }

    @Override
    protected String getLocalizedMessageKey() {
        return "label.resource.notFound";
    }

    @Override
    protected Object[] getMessageParameters() {
        return new Object[]{identifier};
    }

    public String getIdentifier() {
        return identifier;
    }
}
