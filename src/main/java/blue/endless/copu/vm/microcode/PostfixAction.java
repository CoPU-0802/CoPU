package blue.endless.copu.vm.microcode;

import blue.endless.copu.vm.MemoryDevice;
import blue.endless.copu.vm.RegisterFile;

@FunctionalInterface
public interface PostfixAction {
	public static final PostfixAction NONE = (regs, mem, arg) -> {};
	
	public void postfix(RegisterFile registers, MemoryDevice accessibleMemory, int argument);
}
