package org.registrator.community.dto.json;

import org.registrator.community.enumeration.ActionsWithNotConfUsers;

public class UsersDataNotConfJson {
    
    private ActionsWithNotConfUsers actions;
    
    private String logins;

    public UsersDataNotConfJson(ActionsWithNotConfUsers actions, String logins) {
        super();
        this.actions = actions;
        this.logins = logins;
    }

    public UsersDataNotConfJson() {
        super();
    }

    public ActionsWithNotConfUsers getActions() {
        return actions;
    }

    public void setActions(ActionsWithNotConfUsers actions) {
        this.actions = actions;
    }

    public String getLogins() {
        return logins;
    }

    public void setLogins(String logins) {
        this.logins = logins;
    }

    @Override
    public String toString() {
        return "UsersDataNotConfJson [actions=" + actions + ", logins=" + logins + "]";
    }
    
    
   

    
}
