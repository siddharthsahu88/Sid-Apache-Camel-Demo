package com.camel.activemq;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class ObjectToActiveMQ {

	public static void main(String[] args) throws Exception {

		CamelContext context = new DefaultCamelContext();

		ConnectionFactory confactory = new ActiveMQConnectionFactory();

		context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(confactory));

		context.addRoutes(new RouteBuilder() {

			@Override
			public void configure() throws Exception {

				from("direct:start").to("activemq:queue:sidtestq");

			}

		});

		context.start();

		ProducerTemplate producer = context.createProducerTemplate();
		producer.sendBody("direct:start", new String("Send through Active MQ."));

		context.stop();

	}

}
