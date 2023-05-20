package blue.endless.copu.vm.microcode;

import blue.endless.copu.vm.MemoryDevice;
import blue.endless.copu.vm.RegisterFile;

@FunctionalInterface
public interface WriteAction {
	public static final WriteAction NONE = (regs, mem, arg) -> {};
	
	public void write(RegisterFile registers, MemoryDevice accessibleMemory, int argument);
}
