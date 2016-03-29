package org.registrator.community.dto;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class WillDocumentDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date accessionDate;
    private String comment;
    
    public WillDocumentDTO() {
        
    }

    public Date getAccessionDate() {
        return accessionDate;
    }

    public void setAccessionDate(Date accessionDate) {
        this.accessionDate = accessionDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
