package cd4017be.rs_ctr.tileentity;

import cd4017be.rs_ctr.api.com.BlockReference;
import cd4017be.rs_ctr.api.com.BlockReference.BlockHandler;
import cd4017be.rs_ctr.api.com.SignalHandler;
import cd4017be.rs_ctr.api.signal.MountedSignalPort;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

/**
 * @author CD4017BE
 *
 */
public abstract class Sensor extends WallMountGate implements BlockHandler, SignalHandler {

	protected SignalHandler out;
	protected BlockReference blockRef;
	protected int clock, value;

	{
		ports = new MountedSignalPort[] {
			new MountedSignalPort(this, 0, BlockHandler.class, false).setName("port.rs_ctr.bi"),
			new MountedSignalPort(this, 1, SignalHandler.class, false).setName("port.rs_ctr.clk"),
			new MountedSignalPort(this, 2, SignalHandler.class, true).setName("port.rs_ctr.o")
		};
	}

	@Override
	public SignalHandler getPortCallback(int pin) {
		return this;
	}

	@Override
	public void setPortCallback(int pin, Object callback) {
		out = callback instanceof SignalHandler ? (SignalHandler)callback : null;
		if (out != null) out.updateSignal(value);
	}

	@Override
	protected void resetPin(int pin) {
		if (pin == 0) blockRef = null;
		else clock = 0;
	}

	@Override
	public void updateBlock(BlockReference ref) {
		blockRef = ref;
	}

	@Override
	public void updateSignal(int val) {
		if (val == clock) return;
		clock = val;
		if (blockRef == null || !blockRef.isLoaded()) return;
		if ((val = readValue(blockRef)) == value) return;
		value = val;
		if (out != null) out.updateSignal(val);
	}

	protected abstract int readValue(BlockReference ref);

	@Override
	protected void storeState(NBTTagCompound nbt, int mode) {
		super.storeState(nbt, mode);
		if (mode == SAVE) {
			nbt.setInteger("clk", clock);
			nbt.setInteger("val", value);
		}
	}

	@Override
	protected void loadState(NBTTagCompound nbt, int mode) {
		super.loadState(nbt, mode);
		if (mode == SAVE) {
			clock = nbt.getInteger("clk");
			value = nbt.getInteger("val");
			blockRef = null;
		}
	}

	@Override
	protected void orient() {
		ports[0].setLocation(0.25F, 0.25F, 0.125F, EnumFacing.WEST, o);
		ports[1].setLocation(0.25F, 0.75F, 0.125F, EnumFacing.WEST, o);
		ports[2].setLocation(0.75F, 0.5F, 0.125F, EnumFacing.EAST, o);
	}

}
