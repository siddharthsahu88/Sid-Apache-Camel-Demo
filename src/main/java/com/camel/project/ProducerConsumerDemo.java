package com.camel.project;

import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class ProducerConsumerDemo {

	public static void main(String[] args) throws Exception {

		CamelContext ccontext = new DefaultCamelContext();

		ccontext.addRoutes(new RouteBuilder() {

			@Override
			public void configure() throws Exception {

				from("direct:start")
				.process(new Processor() {
					
					public void process(Exchange exchange) throws Exception {
						
						System.out.println("Processor called.");
						
						String message = exchange.getIn().getBody(String.class);
						
						message = message + "? I am Exchange. I have processed your message.";
						
						exchange.getOut().setBody(message);
					}
				})
				.to("seda:end");

			}

		});
		
		ccontext.start();
		
		ProducerTemplate producer = ccontext.createProducerTemplate();
		producer.sendBody("direct:start", "hi! how are you");

		ConsumerTemplate consumer = ccontext.createConsumerTemplate();
		String message = consumer.receiveBody("seda:end", String.class);

		System.out.println(message);
		
		ccontext.stop();

	}

}
