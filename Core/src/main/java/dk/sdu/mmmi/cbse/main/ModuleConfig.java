package dk.sdu.mmmi.cbse.main;

import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import java.util.List;
import java.util.ServiceLoader;
import static java.util.stream.Collectors.toList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * ModuleConfig is the Spring configuration class for the Asteroids game.
 * Responsible for discovering all game components via ServiceLoader and
 * wiring them together as Spring beans for injection into Game.
 *
 * Uses the JPMS ServiceLoader mechanism to discover all registered implementations
 * of IGamePluginService, IEntityProcessingService and IPostEntityProcessingService
 * from the module path at startup.
 *
 * @author jcs
 */
@Configuration
class ModuleConfig
{


    ////////////////////////////////////////////////////////////////
    ////////////////////    Constructor    ////////////////////
    ///


    /**
     * Default constructor for ModuleConfig.
     * Required by Spring for configuration class instantiation.
     */
    public ModuleConfig()
    {
    }




    ////////////////////////////////////////////////////////////////
    ////////////////////    Bean Methods    ////////////////////
    ///


    /**
     * Creates and returns the Game bean.
     * Injects all discovered plugins and processors into the Game instance.
     * @return the fully wired Game instance.
     */
    @Bean
    public Game game()
    {
        // Create the Game instance with all discovered plugins and processors injected.
        return new Game(gamePluginServices(), entityProcessingServiceList(), postEntityProcessingServices());
    }


    /**
     * Discovers and returns all IEntityProcessingService implementations via ServiceLoader.
     * Called once at startup by the Spring ApplicationContext.
     * @return a list of all discovered IEntityProcessingService implementations.
     */
    @Bean
    public List<IEntityProcessingService> entityProcessingServiceList()
    {
        // Discover all registered IEntityProcessingService implementations from the module path.
        return ServiceLoader.load(IEntityProcessingService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }


    /**
     * Discovers and returns all IGamePluginService implementations via ServiceLoader.
     * Called once at startup by the Spring ApplicationContext.
     * @return a list of all discovered IGamePluginService implementations.
     */
    @Bean
    public List<IGamePluginService> gamePluginServices()
    {
        // Discover all registered IGamePluginService implementations from the module path.
        return ServiceLoader.load(IGamePluginService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }


    /**
     * Discovers and returns all IPostEntityProcessingService implementations via ServiceLoader.
     * Called once at startup by the Spring ApplicationContext.
     * @return a list of all discovered IPostEntityProcessingService implementations.
     */
    @Bean
    public List<IPostEntityProcessingService> postEntityProcessingServices()
    {
        // Discover all registered IPostEntityProcessingService implementations from the module path.
        return ServiceLoader.load(IPostEntityProcessingService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }


}