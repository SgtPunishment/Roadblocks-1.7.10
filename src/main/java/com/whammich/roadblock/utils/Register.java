package com.whammich.roadblock.utils;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

import com.whammich.roadblock.block.BlockDecor;
import com.whammich.roadblock.block.BlockRoadBase;
import com.whammich.roadblock.item.ItemMallet;
import com.whammich.roadblock.item.blocks.ItemBlockDecor;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;

public class Register {

	public static Achievement buildRoad;
	
	// Default Mallets
	public static Item ironMallet;
	public static Item goldMallet;
	public static Item diamondMallet;

	// Mod Mallets
	public static Item souliumMallet;
	public static Item manaMallet;
	public static Item terraMallet;
	public static Item elementMallet;

	public static Block roadblockDefault;
	public static Block wornpath;
	
	public static Block[] roadblockConfig;
	public static int configBlockCount = 0;

	public static void registerItems() {
		ironMallet = new ItemMallet(ToolMaterial.IRON, "ingotIron");
		goldMallet = new ItemMallet(ToolMaterial.GOLD, "ingotGold");
		diamondMallet = new ItemMallet(ToolMaterial.EMERALD, "gemDiamond");

		if (Loader.isModLoaded("SSTOW"))
			souliumMallet = new ItemMallet(com.whammich.sstow.utils.Register.SOULIUM, "ingotSoulium");
		if(Loader.isModLoaded("Botania")) {
			manaMallet = new ItemMallet(vazkii.botania.api.BotaniaAPI.manasteelToolMaterial, "ingotManasteel");
			terraMallet = new ItemMallet(vazkii.botania.api.BotaniaAPI.terrasteelToolMaterial, "ingotTerrasteel");
			elementMallet = new ItemMallet(vazkii.botania.api.BotaniaAPI.elementiumToolMaterial, "ingotElvenElementium");
		}
	}

	public static void registerBlocks() {
		
		wornpath = new BlockDecor(Material.ground, "worn_dirt_path");
		GameRegistry.registerBlock(wornpath, ItemBlockDecor.class, "BlockDecor").setStepSound(Block.soundTypeGravel);
		
		roadblockConfig = new Block[Config.roadblocks.length];
		for (int i = 0; i < Config.roadblocks.length; i++) {
			String[] split = Config.roadblocks[i].split(":");
			LogHelper.info(Config.roadblocks[i]);
			switch (split.length) {
			case 2: {
				Block block = GameRegistry.findBlock(split[0], split[1]);
				int meta = 0;
				if (block != null) {
					roadblockConfig[configBlockCount] = new BlockRoadBase(block, meta, Block.soundTypeStone);
					//roadblockConfig[configBlockCount] = new BlockSlabBase(false, block, meta, Block.soundTypeStone);
				}
				LogHelper.info(block + ": " + meta);
				configBlockCount++;
				break;
			}
			case 3: {
				Block block = GameRegistry.findBlock(split[0], split[1]);
				int meta = Integer.parseInt(split[2]);
				if (block != null) {
					roadblockConfig[configBlockCount] = new BlockRoadBase(block, meta, Block.soundTypeStone);
					//roadblockConfig[configBlockCount] = new BlockSlabBase(false, block, meta, Block.soundTypeStone);
				}
				configBlockCount++;
				break;
			}
			case 4: {
				Block block = GameRegistry.findBlock(split[0], split[1]);
				int meta = Integer.parseInt(split[2]);
				float light = Float.parseFloat(split[3]);
				if (block != null) {
					roadblockConfig[configBlockCount] = new BlockRoadBase(block, meta, Block.soundTypeStone, light);
				}
				configBlockCount++;
				break;
			}
			}

		}

		roadblockDefault = new BlockRoadBase("default", Reference.modid + ":default", Material.ground, Block.soundTypeStone);
		GameRegistry.registerBlock(roadblockDefault, "BlockRoadDefault");
		//roadblockDefault = new BlockSlabBase(false, "default", Reference.modid + ":default", Material.ground, Block.soundTypeStone);
		//GameRegistry.registerBlock(roadblockDefault, "BlockSlabDefault");
		
	}

	public static void Achievements() {
		LogHelper.info("Registering Achievement");
		buildRoad = new Achievement("achievement.buildRoad", "buildRoad", 0, 0,
				roadblockDefault, (Achievement) null).initIndependentStat()
				.registerStat();

		LogHelper.info("Registering Achievement Page");
		AchievementPage.registerAchievementPage(new AchievementPage(
				"Roadblocks", buildRoad));
	}
}