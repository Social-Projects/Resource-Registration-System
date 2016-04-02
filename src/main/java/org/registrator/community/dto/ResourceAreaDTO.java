package org.registrator.community.dto;

import java.util.List;

import org.springframework.util.AutoPopulatingList;

public class ResourceAreaDTO {

    private List<PoligonAreaDTO> poligons = new AutoPopulatingList<PoligonAreaDTO>(PoligonAreaDTO.class);

    public ResourceAreaDTO() {

    }
    public List<PoligonAreaDTO> getPoligons() {
    return poligons;
    }

    public void setPoligons(List<PoligonAreaDTO> poligons) {
    this.poligons = poligons;
    }
}
