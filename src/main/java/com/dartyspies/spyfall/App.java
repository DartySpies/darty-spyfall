package com.dartyspies.spyfall;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;

import org.eclipse.jetty.servlets.CrossOriginFilter;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class App extends Application<AppConfiguration> {
    public static void main(String[] args) throws Exception {
        new App().run(args);
    }

    @Override
    public String getName() {
        return "darty spies";
    }

    @Override
    public void initialize(Bootstrap<AppConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(AppConfiguration configuration,
                    Environment environment) {
        Games games = new Games();
		games.create();
        environment.jersey().register(new PlayerResource(games));
		environment.jersey().register(new GameResource(games));
		environment.jersey().register(new LocationResource());
        enableCORS(environment);
    }

	private void enableCORS(Environment environment) {
		final FilterRegistration.Dynamic cors = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        cors.setInitParameter("allowedMethods", "GET");
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
	}

}