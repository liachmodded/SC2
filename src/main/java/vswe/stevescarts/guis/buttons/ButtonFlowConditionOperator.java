package vswe.stevescarts.guis.buttons;

import net.minecraft.entity.player.EntityPlayer;
import vswe.stevescarts.computer.ComputerTask;
import vswe.stevescarts.modules.workers.ModuleComputer;

public class ButtonFlowConditionOperator extends ButtonFlowCondition {
	private int typeId;

	public ButtonFlowConditionOperator(final ModuleComputer module, final LOCATION loc, final int typeId) {
		super(module, loc);
		this.typeId = typeId;
	}

	@Override
	public String toString() {
		return "Change to " + ComputerTask.getFlowOperatorName(this.typeId, true);
	}

	@Override
	public int texture() {
		return 32 + this.typeId;
	}

	@Override
	public boolean isEnabled() {
		for (final ComputerTask task : ((ModuleComputer) this.module).getSelectedTasks()) {
			if (this.typeId != task.getFlowConditionOperator()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void onServerClick(final EntityPlayer player, final int mousebutton, final boolean ctrlKey, final boolean shiftKey) {
		for (final ComputerTask task : ((ModuleComputer) this.module).getSelectedTasks()) {
			task.setFlowConditionOperator(this.typeId);
		}
	}
}
