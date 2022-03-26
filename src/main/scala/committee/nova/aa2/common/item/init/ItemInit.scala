package committee.nova.aa2.common.item.init

import committee.nova.aa2.common.item.base.ItemBase
import committee.nova.aa2.common.item.impl.ItemPortableLauncher
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.item.Item

object ItemInit {

  val names: Array[String] = Array("pl_0", "pl_1", "pl_2", "generic_shell")
  val itemList: Map[String, Item] =
    Map(
      names(0) -> new ItemPortableLauncher(names(0), 1),
      names(1) -> new ItemPortableLauncher(names(1), 3),
      names(2) -> new ItemPortableLauncher(names(2), 6),
      names(3) -> new ItemBase(names(3)).setTextureName("aa2:ammunition/" + names(3))
    )


  def init(): Unit = {
    itemList.foreach(list => register(list._1, list._2))
  }

  private def register(id: String, item: Item): Unit = {
    GameRegistry.registerItem(item, id)
  }
}
