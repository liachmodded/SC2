package vswe.stevescarts.handlers;

import java.util.Random;

import net.minecraft.entity.passive.EntityVillager.ITradeList;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerCareer;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;
import vswe.stevescarts.helpers.ComponentTypes;
import vswe.stevescarts.helpers.ResourceHelper;
import vswe.stevescarts.items.ModItems;

public class TradeHandler implements ITradeList {

	public static VillagerProfession santaProfession;

	public TradeHandler() {
		santaProfession = new VillagerProfession("stevecarts:santa", ResourceHelper.getResource("/models/santa.png").toString(), ResourceHelper.getResource("/models/santa_zombie.png").toString());
		VillagerCareer career = new VillagerCareer(santaProfession, "santa");
		VillagerRegistry.instance().register(santaProfession);
		career.addTrade(1, this);
	}

	@Override
	public void modifyMerchantRecipeList(MerchantRecipeList recipeList, Random random) {
		recipeList.add(new MerchantRecipe(new ItemStack(ModItems.component, 3, ComponentTypes.STOLEN_PRESENT.getId()), new ItemStack(ModItems.component, 1, ComponentTypes.GREEN_WRAPPING_PAPER.getId())));
	}
}
