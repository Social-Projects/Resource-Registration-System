package org.registrator.community.entity;

import org.registrator.community.components.ResourceListener;
import org.registrator.community.enumeration.ResourceStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "list_of_resouces")
@EntityListeners(ResourceListener.class)
public class Resource implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "resources_id")
    @GeneratedValue
    private Integer resourcesId;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "resource_type_id", nullable = false)
    private ResourceType type;

    @Column(name = "identifier", unique = true, nullable = false)
    private String identifier;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "registrator_id", nullable = false)
    private User registrator;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ResourceStatus status;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "tome_id", nullable = false)
    private Tome tome;

    @Column(name = "reason_inclusion", nullable = false)
    private String reasonInclusion;

    @Column(insertable = true, updatable = false)
    @Temporal(TemporalType.DATE)
    private Calendar createdAt;

    //for deleting the resource with its childs
    @OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.REMOVE, mappedBy="resource")
    public List<Polygon> polygons;

    @OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.REMOVE, mappedBy="resource")
    public List<ResourceDiscreteValue> resourceDiscreteValues;

    @OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.REMOVE, mappedBy="resource")
    public List<ResourceLinearValue> resourceLinearValues;

    @OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.REMOVE, mappedBy="resource")
    public List<Inquiry> inquiries;


    public Resource() {

    }

    public Resource(ResourceType type, String identifier, String description, User registrator, Date date,
                    String status, Tome tome, String reasonInclusion) {
        this.type = type;
        this.identifier = identifier;
        this.description = description;
        this.registrator = registrator;
        this.date = date;
        this.status = ResourceStatus.valueOf(status.toUpperCase());
        this.tome = tome;
        this.reasonInclusion = reasonInclusion;
    }

    public Integer getResourcesId() {
        return resourcesId;
    }


    public void setResourcesId(Integer resourcesId) {
        this.resourcesId = resourcesId;
    }


    public ResourceType getType() {
        return type;
    }


    public void setType(ResourceType type) {
        this.type = type;
    }


    public String getIdentifier() {
        return identifier;
    }


    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getRegistrator() {
        return registrator;
    }

    public void setRegistrator(User registrator) {
        this.registrator = registrator;
    }

    public Date getDate() {
        return date;
    }


    public void setDate(Date date) {
        this.date = date;
    }


    public ResourceStatus getStatus() {
        return status;
    }


    public void setStatus(ResourceStatus status) {
        this.status = status;
    }


    public Tome getTome() {
        return tome;
    }


    public void setTome(Tome tome) {
        this.tome = tome;
    }


    public String getReasonInclusion() {
        return reasonInclusion;
    }


    public void setReasonInclusion(String reasonInclusion) {
        this.reasonInclusion = reasonInclusion;
    }

    //    Created for testing
    @Override
    public String toString() {
        return "id: " + getResourcesId()
                + ",typeId: " + getType().getTypeId()
                + ",identifier: " + getIdentifier()
                + ",description: " + getDescription()
                + ",registrator: " + getRegistrator().getUserId()
                + ",date: " + getDate().toString()
                + ",status: " + getStatus().toString()
                + ",tome: " + getTome().getTomeId()
                + ",reason: " + getReasonInclusion();
    }

    @Override
    public int hashCode() {
        return resourcesId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Resource))
            return false;
        Resource other = (Resource) obj;
        return this.resourcesId.equals(other.resourcesId);
    }

    public Calendar getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Calendar createdAt) {
        this.createdAt = createdAt;
    }

    public List<Polygon> getPolygons() {
        return polygons;
    }

    public void setPolygons(List<Polygon> polygons) {
        this.polygons = polygons;
    }

    public List<ResourceDiscreteValue> getResourceDiscreteValues() {
        return resourceDiscreteValues;
    }

    public void setResourceDiscreteValues(List<ResourceDiscreteValue> resourceDiscreteValues) {
        this.resourceDiscreteValues = resourceDiscreteValues;
    }

    public List<ResourceLinearValue> getResourceLinearValues() {
        return resourceLinearValues;
    }

    public void setResourceLinearValues(List<ResourceLinearValue> resourceLinearValues) {
        this.resourceLinearValues = resourceLinearValues;
    }

}


