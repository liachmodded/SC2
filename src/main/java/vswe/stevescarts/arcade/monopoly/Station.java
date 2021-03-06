package vswe.stevescarts.arcade.monopoly;

import java.util.EnumSet;

import vswe.stevescarts.guis.GuiMinecart;

public class Station extends Property {
	private String name;
	private int stationId;

	public Station(final ArcadeMonopoly game, final PropertyGroup group, final int stationId, final String name) {
		super(game, group, name, 200);
		this.stationId = stationId;
		this.name = name;
	}

	@Override
	protected int getTextureId() {
		return 1 + this.stationId;
	}

	@Override
	public void draw(final GuiMinecart gui, final EnumSet<PLACE_STATE> states) {
		super.draw(gui, states);
		this.drawValue(gui);
	}

	@Override
	protected int getTextY() {
		return 10;
	}

	public int getRentCost(final int ownedStations) {
		return 25 * (int) Math.pow(2.0, ownedStations - 1);
	}

	@Override
	public int getRentCost() {
		return this.getRentCost(this.getOwnedInGroup());
	}
}
