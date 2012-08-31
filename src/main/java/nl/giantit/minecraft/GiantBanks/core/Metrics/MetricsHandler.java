package nl.giantit.minecraft.GiantBanks.core.Metrics;

import nl.giantit.minecraft.GiantBanks.GiantBanks;
import nl.giantit.minecraft.GiantBanks.core.config;
import nl.giantit.minecraft.GiantBanks.core.Database.Database;
import nl.giantit.minecraft.GiantBanks.core.Metrics.Metrics.Graph;

import java.io.IOException;

public class MetricsHandler {

	public MetricsHandler(final GiantBanks plugin) {
		final config conf = config.Obtain();
	    
		try {
			Metrics metrics = new Metrics(plugin);
			
			if(conf.getBoolean(plugin.getName() + ".metrics.send.database")) {
				Graph graph = metrics.createGraph("Database Engine");
				graph.addPlotter(new Metrics.Plotter(Database.Obtain().getType()) {
					
					@Override
					public int getValue() {
						return 1;
					}
				});
			}

			if(conf.getBoolean(plugin.getName() + ".metrics.send.updater")) {
				Graph graph = metrics.createGraph("Update warning");
				graph.addPlotter(new Metrics.Plotter((conf.getBoolean(plugin.getName() + ".Updater.checkForUpdates") ? "Enabled" : "Disabled")) {
					
					@Override
					public int getValue() {
						return 1;
					}
				});
			}

			if(conf.getBoolean(plugin.getName() + ".metrics.send.logging")) {
				Graph graph = metrics.createGraph("Use logging");
				graph.addPlotter(new Metrics.Plotter((conf.getBoolean(plugin.getName() + ".log.useLogging") ? "Yes" : "No")) {
					
					@Override
					public int getValue() {
						return 1;
					}
				});
			}

			if(conf.getBoolean(plugin.getName() + ".metrics.send.cache")) {
				Graph graph = metrics.createGraph("Using caching");
				graph.addPlotter(new Metrics.Plotter((conf.getBoolean(plugin.getName() + ".cache.useCache") ? "Yes" : "No")) {
					
					@Override
					public int getValue() {
						return 1;
					}
				});
				
				graph = metrics.createGraph("Cache");
				graph.addPlotter(new Metrics.Plotter("Banks") {
					
					@Override
					public int getValue() {
						return (conf.getBoolean(plugin.getName() + ".cache.cache.banks") ? 1 : 0);
					}
				});

				graph.addPlotter(new Metrics.Plotter("Accounts") {
					
					@Override
					public int getValue() {
						return (conf.getBoolean(plugin.getName() + ".cache.cache.accounts") ? 1 : 0);
					}
				});

				graph.addPlotter(new Metrics.Plotter("Types") {
					
					@Override
					public int getValue() {
						return (conf.getBoolean(plugin.getName() + ".cache.cache.types") ? 1 : 0);
					}
				});
			}

			if(conf.getBoolean(plugin.getName() + ".metrics.send.permEngine")) {
				Graph graph = metrics.createGraph("Permissions engine");
				graph.addPlotter(new Metrics.Plotter(plugin.getPermHandler(true).getEngineName()) {
					
					@Override
					public int getValue() {
						return 1;
					}
				});
			}

			/*if(conf.getBoolean(plugin.getName() + ".metrics.send.ecoEngine")) {
				Graph graph = metrics.createGraph("Economy engine");
				graph.addPlotter(new Metrics.Plotter(plugin.getEcoHandler().getEngineName()) {
					
					@Override
					public int getValue() {
						return 1;
					}
				});
			}*/

			/*if(conf.getBoolean(plugin.getName() + ".metrics.send.gbl")) {
				Graph graph = metrics.createGraph("Using GiantBanks Location");
				graph.addPlotter(new Metrics.Plotter((plugin.useLocation() ? "Yes" : "No")) {
					
					@Override
					public int getValue() {
						return 1;
					}
				});
			}*/
			
			metrics.start();
		} catch (IOException e) {
			plugin.getLogger().warning("Failed to load metrics!");
		    if(conf.getBoolean(plugin.getName() + ".global.debug")) {
		    	e.printStackTrace();
		    }
		}
	}
}
