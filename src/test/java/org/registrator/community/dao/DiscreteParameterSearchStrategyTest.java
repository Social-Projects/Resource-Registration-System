package org.registrator.community.dao;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.registrator.community.dto.json.ResourceSearchParameterJson;
import org.registrator.community.entity.Resource;
import org.registrator.community.entity.ResourceDiscreteValue;
import org.registrator.community.enumeration.ParameterValueCompare;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;

import java.util.*;

import static java.util.Arrays.asList;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

/**
 * Unit test for DiscreteParameterSearchStrategy
 */
public class DiscreteParameterSearchStrategyTest {
    @InjectMocks
    private ResourceSearchStrategy discreteSearch;

    @Mock
    private EntityManager entityManager;

    @Mock
    private DiscreteParameterRepository discreteParameterRepository;
    private Root<Resource> resourceRoot;
    private CriteriaQuery<Resource> selection;
    private CriteriaBuilder criteriaBuilder;
    private Join join;

    @BeforeMethod
    @SuppressWarnings("unchecked")
    public void initMocks() throws Exception {
        discreteSearch = new DiscreteParameterSearchStrategy();
        MockitoAnnotations.initMocks(this);

        resourceRoot = mock(Root.class);
        selection = mock(CriteriaQuery.class);
        criteriaBuilder = mock(CriteriaBuilder.class);
        join = mock(Join.class);

        Set<Root<?>> roots = new HashSet<>();
        roots.add(resourceRoot);
        when(selection.getRoots()).thenReturn(roots);

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(resourceRoot.join(any(String.class))).thenReturn(join);

    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void testAddCriteriaRestrictionThrowsUnsupportedOperationException() throws Exception {
        ResourceSearchParameterJson parameterJson = new ResourceSearchParameterJson();
        parameterJson.setCompare(ParameterValueCompare.LINEAR);
        discreteSearch.addCriteriaRestriction(asList(parameterJson), selection);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testAddCriteriaRestrictionEmptyParameters() throws Exception {

        discreteSearch.addCriteriaRestriction(Collections.emptyList(), selection);

        verify(resourceRoot, times(0)).join(any(String.class));
        verify(join, times(0)).get(any(String.class));
        verify(criteriaBuilder, times(0)).lessThan(any(Expression.class), any(Expression.class));
        verify(criteriaBuilder, times(0)).greaterThan(any(Expression.class), any(Expression.class));
        verify(criteriaBuilder, times(0)).equal(any(Expression.class), any(Expression.class));

    }
}