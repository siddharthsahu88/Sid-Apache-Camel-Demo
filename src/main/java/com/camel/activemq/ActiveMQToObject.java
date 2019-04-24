package com.camel.activemq;

import javax.jms.ConnectionFactory;

import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class ActiveMQToObject {

	public static void main(String[] args) throws Exception {

		CamelContext context = new DefaultCamelContext();

		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory();

		context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

		context.addRoutes(new RouteBuilder() {

			@Override
			public void configure() throws Exception {

				from("activemq:queue:sidtestq").to("seda:end");

			}

		});

		context.start();

		ConsumerTemplate consumer = context.createConsumerTemplate();
		System.out.println(consumer.receiveBody("seda:end", String.class));

		context.stop();

	}

}
