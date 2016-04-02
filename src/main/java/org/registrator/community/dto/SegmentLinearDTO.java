package org.registrator.community.dto;

public class SegmentLinearDTO {
    private double begin;
    private double end;

    public SegmentLinearDTO() {
    }

    public SegmentLinearDTO(double begin, double end) {
        this.begin = begin;
        this.end = end;
    }

    public double getBegin() {
        return begin;
    }

    public void setBegin(double begin) {
        this.begin = begin;
    }

    public double getEnd() {
        return end;
    }

    public void setEnd(double end) {
        this.end = end;
    }

    public String toString() {
        return "\n" + "ВІД: " + begin + "ДО: " + end;
    }
}
