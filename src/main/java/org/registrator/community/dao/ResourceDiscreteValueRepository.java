package org.registrator.community.dao;

import java.util.List;

import org.registrator.community.entity.DiscreteParameter;
import org.registrator.community.entity.Resource;
import org.registrator.community.entity.ResourceDiscreteValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ResourceDiscreteValueRepository extends JpaRepository<ResourceDiscreteValue, Integer>{
    
    @Query("Select dv"+
            " From ResourceDiscreteValue dv" +
            " Where dv.resource = :resource" )
    List<ResourceDiscreteValue> findByResource(@Param("resource")Resource resource);

    List<ResourceDiscreteValue> findAllByDiscreteParameter (DiscreteParameter discreteParameter);

    @Query("Select dv"+
            " From ResourceDiscreteValue dv" +
            " Where dv.value = :val" +
            " and dv.discreteParameter = :discreteParameter" )
    List<ResourceDiscreteValue> findAllByValueAndDiscreteParameter (
            @Param("val") Double d,
            @Param("discreteParameter") DiscreteParameter discreteParameter);

    @Query("Select dv"+
            " From ResourceDiscreteValue dv" +
            " Where dv.value > :val" +
            " and dv.discreteParameter = :discreteParameter" )
    List<ResourceDiscreteValue> findAllByBiggerValueAndDiscreteParameter (
            @Param("val") Double d,
            @Param("discreteParameter") DiscreteParameter discreteParameter);

    @Query("Select dv"+
            " From ResourceDiscreteValue dv" +
            " Where dv.value < :val" +
            " and dv.discreteParameter = :discreteParameter" )
    List<ResourceDiscreteValue> findAllBySmallerValueAndDiscreteParameter (
            @Param("val") Double d,
            @Param("discreteParameter") DiscreteParameter discreteParameter);

    Long deleteByResource(Resource resource);
}
