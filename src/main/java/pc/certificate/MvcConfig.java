package pc.certificate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by wu on 17-8-28.
 */


    @Configuration
    @EnableWebMvc
    public class MvcConfig extends WebMvcConfigurerAdapter {

        @Value("${upload.path}")
        private String image;


        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry
                    .addResourceHandler("/resources/**")
                    .addResourceLocations("file:"+image);
        }

    }
