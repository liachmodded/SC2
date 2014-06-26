package vswe.stevesvehicles.old.Interfaces;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import vswe.stevesvehicles.client.interfaces.GuiBase;
import vswe.stevesvehicles.old.Helpers.Localization;
import vswe.stevesvehicles.network.PacketHandler;
import vswe.stevesvehicles.old.Containers.ContainerActivator;
import vswe.stevesvehicles.old.Helpers.ActivatorOption;
import vswe.stevesvehicles.old.Helpers.ResourceHelper;
import vswe.stevesvehicles.old.TileEntities.TileEntityActivator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiActivator extends GuiBase
{
    public GuiActivator(InventoryPlayer invPlayer, TileEntityActivator activator)
    {
        super(new ContainerActivator(invPlayer, activator));
        this.invPlayer = invPlayer;
        setXSize(255);
        setYSize(222);
		this.activator = activator;
    }

    public void drawGuiForeground(int x, int y)
    {
		GL11.glDisable(GL11.GL_LIGHTING);
	
        getFontRenderer().drawString(Localization.GUI.TOGGLER.TITLE.translate(), 8, 6, 0x404040);
		
		for (int i = 0; i < activator.getOptions().size(); i++) {
			ActivatorOption option = activator.getOptions().get(i);

			int[] box = getBoxRect(i);
			
			getFontRenderer().drawString(option.getName(), box[0] + box[2] + 6, box[1] + 4, 0x404040);
		}			
		
		for (int i = 0; i < activator.getOptions().size(); i++) {
			ActivatorOption option = activator.getOptions().get(i);

			int[] box = getBoxRect(i);

			drawMouseMover(option.getInfo(),x,y,box);
		}	
		
 		GL11.glEnable(GL11.GL_LIGHTING);
    }
	
	private void drawMouseMover(String str, int x, int y, int[] rect) {
		if (inRect(x-getGuiLeft(),y-getGuiTop(),rect)) {
			drawMouseOver(str, x-getGuiLeft(), y-getGuiTop());
		}	
	}

	private static ResourceLocation texture = ResourceHelper.getResource("/gui/activator.png");
    public void drawGuiBackground(float f, int x, int y)
    {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);


        int j = getGuiLeft();
        int k = getGuiTop();
        ResourceHelper.bindResource(texture);
        drawTexturedModalRect(j , k, 0, 0, xSize, ySize);

		x-= getGuiLeft();
		y-= getGuiTop();		
		
		for (int i = 0; i < activator.getOptions().size(); i++) {
			ActivatorOption option = activator.getOptions().get(i);
			
			int[] box = getBoxRect(i);
			int srcX = 0;
			
			if (inRect(x,y, box)) {
				srcX = 16;
			}
			
			drawTexturedModalRect(j + box[0], k + box[1], srcX, ySize, box[2], box[3]);
			drawTexturedModalRect(j + box[0]+1, k + box[1]+1, (box[2]-2) * option.getOption(), ySize + box[3], box[2]-2, box[3]-2);
		}
	

    }
	
	private int[] getBoxRect(int i) {
		return new int[] {20, 22 + i * 20, 16,16};
	}

 
    public void mouseClick(int x, int y, int button)
    {
        super.mouseClick(x, y, button);
		x-= getGuiLeft();
		y-= getGuiTop();

 		for (int i = 0; i < activator.getOptions().size(); i++) {
			int[] box = getBoxRect(i);

			if (inRect(x,y, box)) {
				byte data = (byte)((button == 0) ? 0 : 1);
				data |= i << 1;
				PacketHandler.sendPacket(0,new byte [] {data});
			}
		}
    }
	
	TileEntityActivator activator;
	InventoryPlayer invPlayer;
}