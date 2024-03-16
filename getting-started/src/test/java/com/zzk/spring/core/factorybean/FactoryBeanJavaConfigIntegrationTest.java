package com.zzk.spring.core.factorybean;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotSame;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = FactoryBeanAppConfig.class)
class FactoryBeanJavaConfigIntegrationTest {

    @Autowired
    private Tool tool;

    @Resource(name = "&tool")
    private ToolFactory toolFactory;

    @Test
    void testConstructWorkerByJava() throws Exception {
        assertThat(tool.getId(), equalTo(2));
        assertThat(toolFactory.getFactoryId(), equalTo(7070));

        final Tool tool2 = toolFactory.getObject();
        assertThat(tool2.getId(), equalTo(2));
        assertNotSame(tool, tool2);
    }
}