package org.registrator.community.dao;

import org.registrator.community.entity.Polygon;
import org.registrator.community.entity.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PolygonRepository extends JpaRepository<Polygon, Long> {

    List<Polygon> findByResource(Resource resource);

    @Query("SELECT count(p)"+
            " From Polygon p" +
            " Where p.maxLat >= :minMapLat " +
            "and p.minLat <= :maxMapLat " +
            "and p.maxLng >= :minMapLng " +
            "and p.minLng <= :maxMapLng" )
    Integer countByLimits(@Param("minMapLat")Double minMapLat,
                               @Param("maxMapLat")Double maxMapLat,
                               @Param("minMapLng")Double minMapLng,
                               @Param("maxMapLng")Double maxMapLng);

    @Query("Select p"+
            " From Polygon p" +
            " Where p.maxLat >= :minMapLat " +
            "and p.minLat <= :maxMapLat " +
            "and p.maxLng >= :minMapLng " +
            "and p.minLng <= :maxMapLng ")
    List<Polygon> findByLimits(@Param("minMapLat")Double minMapLat,
                               @Param("maxMapLat")Double maxMapLat,
                               @Param("minMapLng")Double minMapLng,
                               @Param("maxMapLng")Double maxMapLng,
                               Pageable pageable);

    @Query("Select count(p)"+
            " From Polygon p" +
            " Where p.maxLat >= :lat " +
            "and p.minLat <= :lat " +
            "and p.maxLng >= :lng " +
            "and p.minLng <= :lng" )
    Integer countByPoint(@Param("lat")Double lat,
                         @Param("lng")Double lng);

    @Query("Select p"+
            " From Polygon p" +
            " Where p.maxLat >= :lat " +
            "and p.minLat <= :lat " +
            "and p.maxLng >= :lng " +
            "and p.minLng <= :lng" )
    List<Polygon> findByPoint(@Param("lat")Double lat,
                              @Param("lng")Double lng,
                              Pageable pageable);

    Long deleteByResource(Resource resource);
}
