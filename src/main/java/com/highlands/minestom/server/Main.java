// Copyright (C) 2020 Joshua Pacifici <jpac14@outlook.com>
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU Affero General Public License as published
// by the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU Affero General Public License for more details.
//
// You should have received a copy of the GNU Affero General Public License
// along with this program. If not, see <http://www.gnu.org/licenses/>.

package com.highlands.minestom.server;

import java.util.Arrays;
import java.util.List;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.instance.Chunk;
import net.minestom.server.instance.ChunkGenerator;
import net.minestom.server.instance.ChunkPopulator;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.batch.ChunkBatch;
import net.minestom.server.instance.block.Block;
import net.minestom.server.utils.Position;
import net.minestom.server.world.biomes.Biome;

/*
 * Demo from Minestom Wiki
 */

public class Main {

  /**
   * Main function to start server.
   * @param args Arguments
   */
  public static void main(String[] args) {
    // Initialization
    final MinecraftServer minecraftServer = MinecraftServer.init();

    InstanceManager instanceManager = MinecraftServer.getInstanceManager();
    // Create the instance
    InstanceContainer instanceContainer = instanceManager.createInstanceContainer();
    // Set the ChunkGenerator
    instanceContainer.setChunkGenerator(new GeneratorDemo());
    // Enable the auto chunk loading (when players come close)
    instanceContainer.enableAutoChunkLoad(true);

    // Add an event callback to specify the spawning instance (and the spawn position)
    GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
    globalEventHandler.addEventCallback(PlayerLoginEvent.class, event -> {
      final Player player = event.getPlayer();
      event.setSpawningInstance(instanceContainer);
      player.setRespawnPoint(new Position(0, 42, 0));
    });

    // Start the server on port 25565
    minecraftServer.start("localhost", 25565);
  }

  private static class GeneratorDemo implements ChunkGenerator {

    @Override
    public void generateChunkData(ChunkBatch batch, int chunkX, int chunkZ) {
      // Set chunk blocks
      for (byte x = 0; x < Chunk.CHUNK_SIZE_X; x++) {
        for (byte z = 0; z < Chunk.CHUNK_SIZE_Z; z++) {
          for (byte y = 0; y < 40; y++) {
            batch.setBlock(x, y, z, Block.STONE);
          }
        }
      }
    }

    @Override
    public void fillBiomes(Biome[] biomes, int chunkX, int chunkZ) {
      Arrays.fill(biomes, Biome.PLAINS);
    }

    @Override
    public List<ChunkPopulator> getPopulators() {
      return null;
    }
  }

}
