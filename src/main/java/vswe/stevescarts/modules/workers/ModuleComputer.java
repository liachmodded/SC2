package vswe.stevescarts.modules.workers;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import vswe.stevescarts.computer.ComputerControl;
import vswe.stevescarts.computer.ComputerInfo;
import vswe.stevescarts.computer.ComputerProg;
import vswe.stevescarts.computer.ComputerTask;
import vswe.stevescarts.computer.ComputerVar;
import vswe.stevescarts.computer.IWriting;
import vswe.stevescarts.entitys.EntityMinecartModular;
import vswe.stevescarts.guis.GuiMinecart;
import vswe.stevescarts.guis.buttons.ButtonBase;
import vswe.stevescarts.guis.buttons.ButtonControlInteger;
import vswe.stevescarts.guis.buttons.ButtonControlType;
import vswe.stevescarts.guis.buttons.ButtonControlUseVar;
import vswe.stevescarts.guis.buttons.ButtonControlVar;
import vswe.stevescarts.guis.buttons.ButtonFlowConditionInteger;
import vswe.stevescarts.guis.buttons.ButtonFlowConditionOperator;
import vswe.stevescarts.guis.buttons.ButtonFlowConditionSecondVar;
import vswe.stevescarts.guis.buttons.ButtonFlowConditionUseSecondVar;
import vswe.stevescarts.guis.buttons.ButtonFlowConditionVar;
import vswe.stevescarts.guis.buttons.ButtonFlowEndType;
import vswe.stevescarts.guis.buttons.ButtonFlowForEndInteger;
import vswe.stevescarts.guis.buttons.ButtonFlowForEndVar;
import vswe.stevescarts.guis.buttons.ButtonFlowForStartInteger;
import vswe.stevescarts.guis.buttons.ButtonFlowForStartVar;
import vswe.stevescarts.guis.buttons.ButtonFlowForStep;
import vswe.stevescarts.guis.buttons.ButtonFlowForUseEndVar;
import vswe.stevescarts.guis.buttons.ButtonFlowForUseStartVar;
import vswe.stevescarts.guis.buttons.ButtonFlowForVar;
import vswe.stevescarts.guis.buttons.ButtonFlowType;
import vswe.stevescarts.guis.buttons.ButtonInfoType;
import vswe.stevescarts.guis.buttons.ButtonInfoVar;
import vswe.stevescarts.guis.buttons.ButtonKeyboard;
import vswe.stevescarts.guis.buttons.ButtonLabelId;
import vswe.stevescarts.guis.buttons.ButtonProgramAdd;
import vswe.stevescarts.guis.buttons.ButtonProgramStart;
import vswe.stevescarts.guis.buttons.ButtonTask;
import vswe.stevescarts.guis.buttons.ButtonTaskType;
import vswe.stevescarts.guis.buttons.ButtonVarAdd;
import vswe.stevescarts.guis.buttons.ButtonVarFirstInteger;
import vswe.stevescarts.guis.buttons.ButtonVarFirstVar;
import vswe.stevescarts.guis.buttons.ButtonVarSecondInteger;
import vswe.stevescarts.guis.buttons.ButtonVarSecondVar;
import vswe.stevescarts.guis.buttons.ButtonVarType;
import vswe.stevescarts.guis.buttons.ButtonVarUseFirstVar;
import vswe.stevescarts.guis.buttons.ButtonVarUseSecondVar;
import vswe.stevescarts.guis.buttons.ButtonVarVar;

public class ModuleComputer extends ModuleWorker {
	private IWriting writing;
	private short info;
	private ArrayList<ComputerProg> programs;
	private ComputerProg editProg;
	private ArrayList<ComputerTask> editTasks;
	private ComputerProg activeProg;
	private static final int headerSize = 1;
	private static final int programHeaderSize = 3;
	private static final int taskMaxCount = 256;
	private static final int varMaxCount = 63;
	private static final int taskSize = 2;
	private static final int varSize = 5;

	public ModuleComputer(final EntityMinecartModular cart) {
		super(cart);
		this.programs = new ArrayList<>();
		this.editTasks = new ArrayList<>();
	}

	@Override
	public byte getWorkPriority() {
		return 5;
	}

	@Override
	public boolean hasGui() {
		return true;
	}

	@Override
	public boolean hasSlots() {
		return false;
	}

	@Override
	public int guiWidth() {
		return 443;
	}

	@Override
	public int guiHeight() {
		return 250;
	}

	@Override
	public void drawForeground(final GuiMinecart gui) {
		if (this.isWriting()) {
			this.drawString(gui, this.getWriting().getText(), 100, 6, 4210752);
			this.drawString(gui, "Max Length: " + this.getWriting().getMaxLength(), 100, 18, 4210752);
		}
	}

	@Override
	protected void loadButtons() {
		new ButtonProgramAdd(this, ButtonBase.LOCATION.OVERVIEW);
		new ButtonProgramStart(this, ButtonBase.LOCATION.OVERVIEW);
		for (int i = 0; i < 7; ++i) {
			new ButtonTaskType(this, ButtonBase.LOCATION.PROGRAM, i);
		}
		new ButtonVarAdd(this, ButtonBase.LOCATION.PROGRAM);
		for (int i = 0; i < 11; ++i) {
			new ButtonFlowType(this, ButtonBase.LOCATION.TASK, i);
		}
		new ButtonLabelId(this, ButtonBase.LOCATION.TASK, true);
		new ButtonLabelId(this, ButtonBase.LOCATION.TASK, false);
		new ButtonFlowConditionVar(this, ButtonBase.LOCATION.TASK, false);
		new ButtonFlowConditionVar(this, ButtonBase.LOCATION.TASK, true);
		for (int i = 0; i < 6; ++i) {
			new ButtonFlowConditionOperator(this, ButtonBase.LOCATION.TASK, i);
		}
		new ButtonFlowConditionUseSecondVar(this, ButtonBase.LOCATION.TASK, false);
		new ButtonFlowConditionUseSecondVar(this, ButtonBase.LOCATION.TASK, true);
		new ButtonFlowConditionInteger(this, ButtonBase.LOCATION.TASK, 1);
		new ButtonFlowConditionInteger(this, ButtonBase.LOCATION.TASK, -1);
		new ButtonFlowConditionInteger(this, ButtonBase.LOCATION.TASK, 10);
		new ButtonFlowConditionInteger(this, ButtonBase.LOCATION.TASK, -10);
		new ButtonFlowConditionSecondVar(this, ButtonBase.LOCATION.TASK, false);
		new ButtonFlowConditionSecondVar(this, ButtonBase.LOCATION.TASK, true);
		new ButtonFlowForVar(this, ButtonBase.LOCATION.TASK, false);
		new ButtonFlowForVar(this, ButtonBase.LOCATION.TASK, true);
		new ButtonFlowForUseStartVar(this, ButtonBase.LOCATION.TASK, false);
		new ButtonFlowForUseStartVar(this, ButtonBase.LOCATION.TASK, true);
		new ButtonFlowForStartInteger(this, ButtonBase.LOCATION.TASK, 1);
		new ButtonFlowForStartInteger(this, ButtonBase.LOCATION.TASK, -1);
		new ButtonFlowForStartInteger(this, ButtonBase.LOCATION.TASK, 10);
		new ButtonFlowForStartInteger(this, ButtonBase.LOCATION.TASK, -10);
		new ButtonFlowForStartVar(this, ButtonBase.LOCATION.TASK, false);
		new ButtonFlowForStartVar(this, ButtonBase.LOCATION.TASK, true);
		new ButtonFlowForUseEndVar(this, ButtonBase.LOCATION.TASK, false);
		new ButtonFlowForUseEndVar(this, ButtonBase.LOCATION.TASK, true);
		new ButtonFlowForEndInteger(this, ButtonBase.LOCATION.TASK, 1);
		new ButtonFlowForEndInteger(this, ButtonBase.LOCATION.TASK, -1);
		new ButtonFlowForEndInteger(this, ButtonBase.LOCATION.TASK, 10);
		new ButtonFlowForEndInteger(this, ButtonBase.LOCATION.TASK, -10);
		new ButtonFlowForEndVar(this, ButtonBase.LOCATION.TASK, false);
		new ButtonFlowForEndVar(this, ButtonBase.LOCATION.TASK, true);
		new ButtonFlowForStep(this, ButtonBase.LOCATION.TASK, false);
		new ButtonFlowForStep(this, ButtonBase.LOCATION.TASK, true);
		for (int i = 0; i < 4; ++i) {
			new ButtonFlowEndType(this, ButtonBase.LOCATION.TASK, i);
		}
		for (int i = 0; i < 18; ++i) {
			new ButtonVarType(this, ButtonBase.LOCATION.TASK, i);
		}
		new ButtonVarVar(this, ButtonBase.LOCATION.TASK, false);
		new ButtonVarVar(this, ButtonBase.LOCATION.TASK, true);
		new ButtonVarUseFirstVar(this, ButtonBase.LOCATION.TASK, false);
		new ButtonVarUseFirstVar(this, ButtonBase.LOCATION.TASK, true);
		new ButtonVarFirstInteger(this, ButtonBase.LOCATION.TASK, 1);
		new ButtonVarFirstInteger(this, ButtonBase.LOCATION.TASK, -1);
		new ButtonVarFirstInteger(this, ButtonBase.LOCATION.TASK, 10);
		new ButtonVarFirstInteger(this, ButtonBase.LOCATION.TASK, -10);
		new ButtonVarFirstVar(this, ButtonBase.LOCATION.TASK, false);
		new ButtonVarFirstVar(this, ButtonBase.LOCATION.TASK, true);
		new ButtonVarUseSecondVar(this, ButtonBase.LOCATION.TASK, false);
		new ButtonVarUseSecondVar(this, ButtonBase.LOCATION.TASK, true);
		new ButtonVarSecondInteger(this, ButtonBase.LOCATION.TASK, 1);
		new ButtonVarSecondInteger(this, ButtonBase.LOCATION.TASK, -1);
		new ButtonVarSecondInteger(this, ButtonBase.LOCATION.TASK, 10);
		new ButtonVarSecondInteger(this, ButtonBase.LOCATION.TASK, -10);
		new ButtonVarSecondVar(this, ButtonBase.LOCATION.TASK, false);
		new ButtonVarSecondVar(this, ButtonBase.LOCATION.TASK, true);
		new ButtonControlType(this, ButtonBase.LOCATION.TASK, 0);
		ComputerControl.createButtons(this.getCart(), this);
		new ButtonControlUseVar(this, ButtonBase.LOCATION.TASK, false);
		new ButtonControlUseVar(this, ButtonBase.LOCATION.TASK, true);
		new ButtonControlInteger(this, ButtonBase.LOCATION.TASK, 1);
		new ButtonControlInteger(this, ButtonBase.LOCATION.TASK, -1);
		new ButtonControlInteger(this, ButtonBase.LOCATION.TASK, 10);
		new ButtonControlInteger(this, ButtonBase.LOCATION.TASK, -10);
		new ButtonControlVar(this, ButtonBase.LOCATION.TASK, false);
		new ButtonControlVar(this, ButtonBase.LOCATION.TASK, true);
		new ButtonInfoType(this, ButtonBase.LOCATION.TASK, 0);
		ComputerInfo.createButtons(this.getCart(), this);
		new ButtonInfoVar(this, ButtonBase.LOCATION.TASK, false);
		new ButtonInfoVar(this, ButtonBase.LOCATION.TASK, true);
		for (int i = 0; i < 21; ++i) {
			new ButtonTask(this, ButtonBase.LOCATION.FLOATING, i);
		}
		ButtonKeyboard.generateKeyboard(this);
	}

	@Override
	public boolean useButtons() {
		return true;
	}

	public boolean isWriting() {
		return this.writing != null;
	}

	public IWriting getWriting() {
		return this.writing;
	}

	public void setWriting(final IWriting val) {
		this.writing = val;
	}

	public void flipShift() {
		this.info ^= 0x1;
	}

	public void flipCaps() {
		this.info ^= 0x2;
	}

	public boolean getShift() {
		return (this.info & 0x1) != 0x0;
	}

	public boolean getCaps() {
		return (this.info & 0x2) != 0x0;
	}

	public boolean isLower() {
		return this.getShift() == this.getCaps();
	}

	public void disableShift() {
		this.info &= 0xFFFFFFFE;
	}

	public ComputerProg getCurrentProg() {
		return this.editProg;
	}

	public ArrayList<ComputerTask> getSelectedTasks() {
		return this.editTasks;
	}

	public void setCurrentProg(final ComputerProg prog) {
		this.editProg = prog;
	}

	public void setActiveProgram(final ComputerProg prog) {
		this.activeProg = prog;
	}

	public ComputerProg getActiveProgram() {
		return this.activeProg;
	}

	@Override
	public boolean work() {
		if (this.activeProg != null) {
			if (this.doPreWork()) {
				this.startWorking(this.activeProg.getRunTime());
			} else {
				if (!this.activeProg.run()) {
					this.activeProg = null;
				}
				this.stopWorking();
			}
		}
		return true;
	}

	@Override
	public void update() {
		super.update();
	}

	@Override
	protected void receivePacket(final int id, final byte[] data, final EntityPlayer player) {
	}

	@Override
	public int numberOfPackets() {
		return 0;
	}

	@Override
	public int numberOfGuiData() {
		return 831;
	}

	public void activationChanged() {
		this.editTasks.clear();
		if (this.editProg != null) {
			for (final ComputerTask task : this.editProg.getTasks()) {
				if (task.getIsActivated()) {
					this.editTasks.add(task);
				}
			}
		}
	}

	@Override
	protected void checkGuiData(final Object[] info) {
		this.updateGuiData(info, 0, this.info);
		if (this.editProg != null) {
			this.updateGuiData(info, 1, this.editProg.getInfo());
			final int tasks = this.editProg.getTasks().size();
			final int vars = this.editProg.getVars().size();
			this.updateGuiData(info, 2, (short) (tasks << 8 | vars));
			if (this.editProg == this.activeProg) {
				this.updateGuiData(info, 3, (short) this.activeProg.getActiveId());
			} else {
				this.updateGuiData(info, 3, (short) 256);
			}
			for (int taskId = 0; taskId < tasks; ++taskId) {
				final ComputerTask theTask = this.editProg.getTasks().get(taskId);
				for (int internalId = 0; internalId < 2; ++internalId) {
					this.updateGuiData(info, 4 + taskId * 2 + internalId, theTask.getInfo(internalId));
				}
			}
			for (int varId = 0; varId < vars; ++varId) {
				final ComputerVar theVar = this.editProg.getVars().get(varId);
				for (int internalId = 0; internalId < 5; ++internalId) {
					this.updateGuiData(info, 516 + varId * 5 + internalId, theVar.getInfo(internalId));
				}
			}
		} else {
			this.updateGuiData(info, 1, (short) 0);
		}
	}

	@Override
	public void receiveGuiData(final int id, final short data) {
		System.out.println("ID " + id + " Data " + data);
		if (id == 0) {
			this.info = data;
		} else if (id == 1) {
			if (data == 0) {
				this.editProg = null;
			} else {
				if (this.editProg == null) {
					this.editProg = new ComputerProg(this);
				}
				this.editProg.setInfo(data);
			}
		} else if (this.editProg != null) {
			if (id == 2) {
				final int tasks = data >> 8 & 0xFF;
				final int vars = data & 0xFF;
				this.editProg.setTaskCount(tasks);
				this.editProg.setVarCount(vars);
			} else if (id == 3) {
				if (data >= 0 && data < 256) {
					this.activeProg = this.editProg;
					this.editProg.setActiveId(data);
				} else {
					this.activeProg = null;
					this.editProg.setActiveId(0);
				}
			} else {
				final int taskId = id - 1 - 3;
				if (taskId < 512) {
					final int task = taskId / 2;
					final int taskInternalPos = taskId % 2;
					if (task >= 0 && task < this.editProg.getTasks().size()) {
						final ComputerTask theTask = this.editProg.getTasks().get(task);
						theTask.setInfo(taskInternalPos, data);
					}
				} else {
					final int varId = taskId - 512;
					final int var = varId / 5;
					final int varInternalPos = varId % 5;
					if (var >= 0 && var < this.editProg.getVars().size()) {
						final ComputerVar theVar = this.editProg.getVars().get(var);
						theVar.setInfo(varInternalPos, data);
					}
				}
			}
		}
	}
}
