package Config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IaConfig {

    @Value("${ia.url}")
    private String url;

    @Value("${ia.apy.key}")
    private String apiKey;

    public String getIaUrl(){
        return url + "?key=" + apiKey;
    }
}
