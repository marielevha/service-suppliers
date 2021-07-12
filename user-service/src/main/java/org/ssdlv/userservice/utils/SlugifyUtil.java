package org.ssdlv.userservice.utils;

import com.github.slugify.Slugify;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SlugifyUtil {
    public static Slugify instance = null;

    public static Slugify getInstance() {
        if (instance != null) {
            return instance;
        }
        instance = new Slugify();
        return instance;
    }
}