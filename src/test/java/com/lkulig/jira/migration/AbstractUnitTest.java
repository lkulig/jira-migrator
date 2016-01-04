package com.lkulig.jira.migration;

import org.junit.Before;
import org.mockito.MockitoAnnotations;

public class AbstractUnitTest {

    @Before
    public void initialize() {
        MockitoAnnotations.initMocks(this);
    }

}
