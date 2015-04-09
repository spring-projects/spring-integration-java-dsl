/*
 * Copyright 2015 the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.integration.dsl.kafka;

import java.util.Properties;

import org.springframework.integration.dsl.support.Consumer;
import org.springframework.integration.dsl.support.PropertiesBuilder;
import org.springframework.integration.kafka.core.ConnectionFactory;
import org.springframework.integration.kafka.core.Partition;
import org.springframework.integration.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.integration.kafka.support.ZookeeperConnect;
import org.springframework.util.Assert;

/**
 * @author Artem Bilan
 * @since 1.1
 */
public abstract class Kafka {

	public static KafkaHighLevelConsumerMessageSourceSpec inboundChannelAdapter(ZookeeperConnect zookeeperConnect) {
		return new KafkaHighLevelConsumerMessageSourceSpec(zookeeperConnect);
	}

	public static KafkaProducerMessageHandlerSpec outboundChannelAdapter() {
		return outboundChannelAdapter((Properties) null);
	}

	public static KafkaProducerMessageHandlerSpec outboundChannelAdapter(
			Consumer<PropertiesBuilder> producerProperties) {
		Assert.notNull(producerProperties);
		PropertiesBuilder properties = new PropertiesBuilder();
		producerProperties.accept(properties);
		return outboundChannelAdapter(properties.get());
	}

	public static KafkaProducerMessageHandlerSpec outboundChannelAdapter(Properties producerProperties) {
		return new KafkaProducerMessageHandlerSpec(producerProperties);
	}

	@SuppressWarnings("rawtypes")
	public static KafkaMessageDrivenChannelAdapterSpec messageDriverChannelAdapter(
			KafkaMessageListenerContainer messageListenerContainer) {
		return new KafkaMessageDrivenChannelAdapterSpec(messageListenerContainer);
	}

	public static KafkaMessageDrivenChannelAdapterSpec.KafkaMessageDrivenChannelAdapterListenerContainerSpec
	messageDriverChannelAdapter(ConnectionFactory connectionFactory, Partition... partitions) {
		return messageDriverChannelAdapter(
				new KafkaMessageDrivenChannelAdapterSpec.KafkaMessageListenerContainerSpec(connectionFactory, partitions));
	}

	public static KafkaMessageDrivenChannelAdapterSpec.KafkaMessageDrivenChannelAdapterListenerContainerSpec
	messageDriverChannelAdapter(ConnectionFactory connectionFactory, String... topics) {
		return messageDriverChannelAdapter(
				new KafkaMessageDrivenChannelAdapterSpec.KafkaMessageListenerContainerSpec(connectionFactory, topics));
	}

	private static KafkaMessageDrivenChannelAdapterSpec.KafkaMessageDrivenChannelAdapterListenerContainerSpec
	messageDriverChannelAdapter(KafkaMessageDrivenChannelAdapterSpec.KafkaMessageListenerContainerSpec spec) {
		return new KafkaMessageDrivenChannelAdapterSpec.KafkaMessageDrivenChannelAdapterListenerContainerSpec(spec);
	}

}
