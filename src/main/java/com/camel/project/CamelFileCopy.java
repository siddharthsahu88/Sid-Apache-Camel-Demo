package com.camel.project;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class CamelFileCopy {

	public static void main(String[] args) throws Exception {

		CamelContext ccontext = new DefaultCamelContext();

		ccontext.addRoutes(new RouteBuilder() {

			@Override
			public void configure() throws Exception {

				from("file:input?noop=true")
				.to("file:output");

			}

		});

		while (true)
			ccontext.start();

	}

}
