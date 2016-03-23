package org.registrator.community.dto;

import java.util.List;

import org.springframework.util.AutoPopulatingList;

public class PoligonAreaDTO {
	
	private List<PointAreaDTO> points = new AutoPopulatingList<PointAreaDTO>(PointAreaDTO.class);
	
	public PoligonAreaDTO() {
		
	}

	public List<PointAreaDTO> getPoints() {
		return points;
	}

	public void setPoints(List<PointAreaDTO> points) {
		this.points = points;
	}

/*	public String toString() {

       return "Список всіх точок полігону: " + "\n" +
	points.toString();

        StringBuilder result = new StringBuilder();
        result.append("---- Полігон -----\n");
        for (PointAreaDTO point : points) {
			result.append(point.toString());
		}

		return result.toString();

	}*/
}
