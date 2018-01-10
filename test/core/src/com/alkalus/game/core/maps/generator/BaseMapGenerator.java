package com.alkalus.game.core.maps.generator;

import com.alkalus.game.core.Constants;
import com.alkalus.game.util.array.AutoMap;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class BaseMapGenerator {

	private final TiledMap infiniteMapBase;
	private final OrthogonalTiledMapRenderer tiledMapRenderer;
	
	public BaseMapGenerator(){
		infiniteMapBase = new TmxMapLoader().load(Constants.PATH_TILEMAPS+"InfiniteWorldGeneratorBaseSmall.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(infiniteMapBase);
	}

	public OrthogonalTiledMapRenderer generateRandomInfiniteMap(){		
		return tiledMapRenderer;
	}
	
	public TiledMap getDefaultTiledMap(){		
		return infiniteMapBase;
	}
	
	
	
	
	
	
	
	
	
	

	private TiledMap singleTileTempMap;
	void run(){
		
		singleTileTempMap = new TmxMapLoader().load(Constants.PATH_TILEMAPS+"BasicTerrainSingleTile.tmx");
		
		//Get Basic Ground Object Textures
		TiledMapTileSets r = singleTileTempMap.getTileSets();
		AutoMap<TiledMapTileSet> t = new AutoMap<TiledMapTileSet>();
		while (r.iterator().hasNext()) {
			t.put(r.iterator().next());
		}
		
		/**
		 * Static Texture IDs
		 * 
		 * 0: Dirt (red)
		 * 1: Dirt (green)
		 * 2: Water (green)
		 * 3: Water (grey)
		 * 4: Hedge A
		 * 5: Hedge B
		 * 
		 */
		
        //tiledMapRenderer = new OrthogonalTiledMapRenderer(infiniteMapBase);
	}
	
}
