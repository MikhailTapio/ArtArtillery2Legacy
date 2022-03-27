package committee.nova.aa2.common.item.recipe

import committee.nova.aa2.common.item.init.ItemInit
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.init.{Blocks, Items}
import net.minecraft.item.ItemStack

object RecipeInit {
  def init(): Unit = {
    addCraftingRecipes()
  }

  private def addCraftingRecipes(): Unit = {
    val pl0 = ItemInit.itemList(ItemInit.names(0))
    val pl1 = ItemInit.itemList(ItemInit.names(1))
    val pl2 = ItemInit.itemList(ItemInit.names(2))
    val shell = ItemInit.itemList(ItemInit.names(3))
    val piston = new ItemStack(Blocks.piston).getItem
    GameRegistry.addShapedRecipe(new ItemStack(pl0),
      "IIW", "IID", "  L", jC('I'), Items.iron_ingot, jC('W'), Items.water_bucket, jC('D'), new ItemStack(Blocks.dispenser).getItem, jC('L'), Items.leather
    )
    GameRegistry.addShapedRecipe(new ItemStack(pl1),
      "GGG", " AP", "SSS", jC('G'), Items.gold_ingot, jC('A'), pl0, jC('P'), piston, jC('S'), new ItemStack(Blocks.stone).getItem
    )
    GameRegistry.addShapedRecipe(new ItemStack(pl2),
      "DDD", " AP", "III", jC('D'), Items.diamond, jC('A'), pl1, jC('P'), piston, jC('I'), Items.iron_ingot
    )
    GameRegistry.addShapedRecipe(new ItemStack(shell, 8),
      "SSS", "SPS", "SSS", jC('S'), new ItemStack(Blocks.cobblestone).getItem, jC('P'), Items.gunpowder
    )
  }

  def jC(char: Char): Character = char.asInstanceOf[Character]
}
