package committee.nova.aa2.client.render.entity

import committee.nova.aa2.common.item.init.ItemInit
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.client.renderer.entity.RenderSnowball

@SideOnly(Side.CLIENT)
class RenderShell extends RenderSnowball(ItemInit.itemList(ItemInit.names(3)))