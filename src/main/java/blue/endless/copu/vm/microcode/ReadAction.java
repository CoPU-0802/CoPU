package blue.endless.copu.vm.microcode;

import blue.endless.copu.vm.MemoryDevice;
import blue.endless.copu.vm.RegisterFile;

@FunctionalInterface
public interface ReadAction {
	public static final ReadAction NONE = (regs, mem, addr) -> false;
	
	public boolean read(RegisterFile registers, MemoryDevice memory, int addr);
}
