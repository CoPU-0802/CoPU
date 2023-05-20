package blue.endless.copu.vm;

import blue.endless.copu.vm.microcode.ExecuteAction;
import blue.endless.copu.vm.microcode.FetchAction;
import blue.endless.copu.vm.microcode.PostfixAction;
import blue.endless.copu.vm.microcode.ReadAction;
import blue.endless.copu.vm.microcode.WriteAction;

public record Instruction(String mnemonic, FetchAction loadAction, ReadAction readAction, ExecuteAction executeAction, WriteAction writeAction, PostfixAction postfixAction) {
	public static final Instruction NOP = new Instruction("nop", FetchAction.UNLATCH, ReadAction.NONE, ExecuteAction.NONE, WriteAction.NONE, PostfixAction.NONE);
}
