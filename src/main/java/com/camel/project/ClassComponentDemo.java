package com.camel.project;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class ClassComponentDemo {

	public void printMessage(String message) {
		System.out.println(message);
	}

	public static void main(String[] args) throws Exception {

		CamelContext context = new DefaultCamelContext();

		context.addRoutes(new RouteBuilder() {

			@Override
			public void configure() throws Exception {

				from("direct:start")
				.to("class:com.camel.project.ClassComponentDemo?method=printMessage");

			}

		});

		context.start();

		ProducerTemplate producer = context.createProducerTemplate();
		producer.sendBody("direct:start", "This is a Camel class method call demo.");

		context.stop();

	}

}
