package dk.sdu.mmmi.cbse.main;

import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.common.util.ServiceLocator;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import static java.util.stream.Collectors.toList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author jcs
 */
@Configuration
class ModuleConfig {

    public ModuleConfig() {
    }

    @Bean
    public Game game(){
        return new Game(gamePluginServices(), entityProcessingServiceList(), postEntityProcessingServices());
    }

    @Bean
    public List<IEntityProcessingService> entityProcessingServiceList(){
        List<IEntityProcessingService> services = new ArrayList<>();
        ServiceLoader.load(IEntityProcessingService.class).stream()
                .map(ServiceLoader.Provider::get).forEach(services::add);
        services.addAll(ServiceLocator.INSTANCE.locateAll(IEntityProcessingService.class));
        return services;
    }

    @Bean
    public List<IGamePluginService> gamePluginServices() {
        List<IGamePluginService> services = new ArrayList<>();
        ServiceLoader.load(IGamePluginService.class).stream()
                .map(ServiceLoader.Provider::get).forEach(services::add);
        services.addAll(ServiceLocator.INSTANCE.locateAll(IGamePluginService.class));
        return services;
    }

    @Bean
    public List<IPostEntityProcessingService> postEntityProcessingServices() {
        List<IPostEntityProcessingService> services = new ArrayList<>();
        ServiceLoader.load(IPostEntityProcessingService.class).stream()
                .map(ServiceLoader.Provider::get).forEach(services::add);
        services.addAll(ServiceLocator.INSTANCE.locateAll(IPostEntityProcessingService.class));
        return services;
    }
}