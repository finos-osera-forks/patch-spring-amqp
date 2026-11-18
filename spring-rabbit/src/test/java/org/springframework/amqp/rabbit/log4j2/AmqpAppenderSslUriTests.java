/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.amqp.rabbit.log4j2;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.net.URI;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.junit.jupiter.api.Test;

import org.springframework.amqp.rabbit.connection.RabbitConnectionFactoryBean;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * @since 2.4.18
 */
public class AmqpAppenderSslUriTests {

	@Test
	void amqpsUriEnablesSsl() {
		RabbitConnectionFactoryBean bean = mock(RabbitConnectionFactoryBean.class);
		managerWithUri("amqps://user:password@rabbitmq.com/vhost").configureRabbitConnectionFactory(bean);
		verify(bean).setUseSSL(true);
	}

	@Test
	void amqpUriLeavesSslOff() {
		RabbitConnectionFactoryBean bean = mock(RabbitConnectionFactoryBean.class);
		managerWithUri("amqp://user:password@rabbitmq.com/vhost").configureRabbitConnectionFactory(bean);
		verify(bean, never()).setUseSSL(anyBoolean());
	}

	private AmqpAppender.AmqpManager managerWithUri(String uri) {
		AmqpAppender.AmqpManager manager =
				new AmqpAppender.AmqpManager((LoggerContext) LogManager.getContext(false), "sslUri-" + uri);
		ReflectionTestUtils.setField(manager, "uri", URI.create(uri));
		return manager;
	}

}
