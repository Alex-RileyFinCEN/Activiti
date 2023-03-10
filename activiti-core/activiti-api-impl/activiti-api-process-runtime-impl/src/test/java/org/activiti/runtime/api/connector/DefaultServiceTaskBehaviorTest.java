/*
 * Copyright 2010-2020 Alfresco Software, Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.activiti.runtime.api.connector;

import org.activiti.api.process.runtime.connector.Connector;
import org.activiti.engine.delegate.DelegateExecution;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class DefaultServiceTaskBehaviorTest {

    @InjectMocks
    private DefaultServiceTaskBehavior behavior;

    @Mock
    private ApplicationContext context;

    @Test
    public void hasConnectorBeanShouldReturnTrueIfABeanOfConnectorTypeIsFound() {
        //given
        String connectorName = "connector";
        DelegateExecution execution = ConnectorRuntimeApiTestHelper.buildExecution(connectorName);
        given(context.containsBean(connectorName)).willReturn(true);
        given(context.getBean(connectorName)).willReturn(mock(Connector.class));

        //when
        boolean hasConnectorBean = behavior.hasConnectorBean(execution);

        //then
        assertThat(hasConnectorBean).isTrue();
    }

    @Test
    public void hasConnectorBeanShouldReturnFalseIfNoBeanIsFoundWithTheGivenName() {
        //given
        String connectorName = "connector";
        DelegateExecution execution = ConnectorRuntimeApiTestHelper.buildExecution(connectorName);
        given(context.containsBean(connectorName)).willReturn(false);

        //when
        boolean hasConnectorBean = behavior.hasConnectorBean(execution);

        //then
        assertThat(hasConnectorBean).isFalse();
    }

    @Test
    public void hasConnectorBeanShouldReturnFalseIfABeanOfDifferentTypeIsFound() {
        //given
        String connectorName = "connector";
        DelegateExecution execution = ConnectorRuntimeApiTestHelper.buildExecution(connectorName);
        given(context.containsBean(connectorName)).willReturn(true);
        DelegateExecution nonConnectorBean = mock(DelegateExecution.class);
        given(context.getBean(connectorName)).willReturn(nonConnectorBean);

        //when
        boolean hasConnectorBean = behavior.hasConnectorBean(execution);

        //then
        assertThat(hasConnectorBean).isFalse();
    }

}
