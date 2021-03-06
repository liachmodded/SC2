package vswe.stevescarts.arcade.monopoly;

import java.util.EnumSet;

import vswe.stevescarts.guis.GuiMinecart;

public class CornerPlace extends Place {
	private int texture;

	public CornerPlace(final ArcadeMonopoly game, final int texture) {
		super(game);
		this.texture = texture;
	}

	@Override
	public void draw(final GuiMinecart gui, final EnumSet<PLACE_STATE> states) {
		this.game.loadTexture(gui, 2);
		this.applyColorFilter(gui, states);
		this.game.getModule().drawImage(gui, 0, 0, 122 * (this.texture % 2), 122 * (this.texture / 2), 122, 122);
	}
}
