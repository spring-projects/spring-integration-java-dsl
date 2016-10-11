/*
 * Copyright 2014-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.integration.dsl;

import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.core.ConsumerEndpointSpec;
import org.springframework.integration.filter.MessageFilter;
import org.springframework.messaging.MessageChannel;
import org.springframework.util.Assert;

/**
 * A {@link ConsumerEndpointSpec} implementation for the {@link MessageFilter}.
 *
 * @author Artem Bilan
 */
public final class FilterEndpointSpec extends ConsumerEndpointSpec<FilterEndpointSpec, MessageFilter> {

	FilterEndpointSpec(MessageFilter messageFilter) {
		super(messageFilter);
	}

	/**
	 * The default value is <code>false</code> meaning that rejected
	 * Messages will be quietly dropped or sent to the discard channel if
	 * available. Typically this value would not be <code>true</code> when
	 * a discard channel is provided, but if so, it will still apply
	 * (in such a case, the Message will be sent to the discard channel,
	 * and <em>then</em> the exception will be thrown).
	 * @param throwExceptionOnRejection the throwExceptionOnRejection.
	 * @return the endpoint spec.
	 * @see MessageFilter#setThrowExceptionOnRejection(boolean)
	 */
	public FilterEndpointSpec throwExceptionOnRejection(boolean throwExceptionOnRejection) {
		this.handler.setThrowExceptionOnRejection(throwExceptionOnRejection);
		return _this();
	}

	/**
	 * Specify a channel where rejected Messages should be sent. If the discard
	 * channel is null (the default), rejected Messages will be dropped. However,
	 * the 'throwExceptionOnRejection' flag determines whether rejected Messages
	 * trigger an exception. That value is evaluated regardless of the presence
	 * of a discard channel.
	 * @param discardChannel the discardChannel.
	 * @return the endpoint spec.
	 * @see MessageFilter#setDiscardChannel(MessageChannel)
	 */
	public FilterEndpointSpec discardChannel(MessageChannel discardChannel) {
		this.handler.setDiscardChannel(discardChannel);
		return _this();
	}

	/**
	 * Specify a channel name where rejected Messages should be sent. If the discard
	 * channel is null (the default), rejected Messages will be dropped. However,
	 * the 'throwExceptionOnRejection' flag determines whether rejected Messages
	 * trigger an exception. That value is evaluated regardless of the presence
	 * of a discard channel.
	 * @param discardChannelName the discardChannelName.
	 * @return the endpoint spec.
	 * @see MessageFilter#setDiscardChannelName(String)
	 */
	public FilterEndpointSpec discardChannel(String discardChannelName) {
		this.handler.setDiscardChannelName(discardChannelName);
		return _this();
	}

	/**
	 * Configure a subflow to run for discarded messages instead of a
	 * {@link #discardChannel(MessageChannel)}.
	 * @param discardFlow the discard flow.
	 * @return the endpoint spec.
	 */
	public FilterEndpointSpec discardFlow(IntegrationFlow discardFlow) {
		Assert.notNull(discardFlow);
		DirectChannel channel = new DirectChannel();
		IntegrationFlowBuilder flowBuilder = IntegrationFlows.from(channel);
		discardFlow.configure(flowBuilder);
		this.componentToRegister.add(flowBuilder.get());
		return discardChannel(channel);
	}

	/**
	 * Set to 'true' if you wish the discard processing to occur within any
	 * request handler advice applied to this filter. Also applies to
	 * throwing an exception on rejection. Default: true.
	 * @param discardWithinAdvice the discardWithinAdvice.
	 * @return the endpoint spec.
	 * @see MessageFilter#setDiscardWithinAdvice(boolean)
	 */
	public FilterEndpointSpec discardWithinAdvice(boolean discardWithinAdvice) {
		this.handler.setDiscardWithinAdvice(discardWithinAdvice);
		return _this();
	}

}
