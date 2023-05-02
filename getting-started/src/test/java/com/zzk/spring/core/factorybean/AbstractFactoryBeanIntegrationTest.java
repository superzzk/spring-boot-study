package com.zzk.spring.core.factorybean;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(locations = { "classpath:core/factorybean-abstract-spring-ctx.xml" })
public class AbstractFactoryBeanIntegrationTest {

    @Resource(name = "singleTool")
    private Tool tool1;
    @Resource(name = "singleTool")
    private Tool tool2;
    @Resource(name = "nonSingleTool")
    private Tool tool3;
    @Resource(name = "nonSingleTool")
    private Tool tool4;

    @Test
    public void testSingleToolFactory() {
        assertThat(tool1.getId(), equalTo(1));
        assertTrue(tool1 == tool2);
    }

    @Test
    public void testNonSingleToolFactory() {
        assertThat(tool3.getId(), equalTo(2));
        assertThat(tool4.getId(), equalTo(2));
        assertTrue(tool3 != tool4);
    }
}
