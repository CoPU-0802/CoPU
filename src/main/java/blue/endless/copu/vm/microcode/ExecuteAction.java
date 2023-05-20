package blue.endless.copu.vm.microcode;

import blue.endless.copu.vm.MemoryDevice;
import blue.endless.copu.vm.RegisterFile;

@FunctionalInterface
public interface ExecuteAction {
	public static final ExecuteAction NONE = (regs, mem, arg) -> {};
	
	public void execute(RegisterFile registers, MemoryDevice accessibleMemory, PipelineData data);
}
