package br.com.fiap.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
@Primary
public class SwaggerConfig implements SwaggerResourcesProvider {

    @Autowired
    RouteLocator routeLocator;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any()).build().pathMapping("/")
                ;
    }

    @Override
    public List<SwaggerResource> get() {
        //Dynamic introduction of micro services using routeLocator
        List<SwaggerResource> resources = new ArrayList<>();
        resources.add(swaggerResource("zuul-gateway","/v2/api-docs","1.0"));
        //Recycling Lambda expressions to simplify code
        routeLocator.getRoutes().forEach(route -> {
            //Dynamic acquisition
            if (route.getId().endsWith("-api-docs"))
                resources.add(
                    swaggerResource(
                        route.getId(),

                        //"/api-docs/v1/" + route.getId(),
                            route.getFullPath().replace("**", "v2/api-docs"),
                        "1.0"
                    )
                );
        });
        //You can also directly inherit the Consumer interface
//		routeLocator.getRoutes().forEach(new Consumer<Route>() {
//
//			@Override
//			public void accept(Route t) {
//				// TODO Auto-generated method stub
//
//			}
//		});
        return resources;
    }

    private SwaggerResource swaggerResource(String name,String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }
}