package blue.endless.copu.vm.microcode;

import blue.endless.copu.vm.Instruction;

public class PipelineData {
	// Fetch-Decode
	public int address = 0x0000;
	public Instruction instruction = Instruction.NOP;
	public int decodeData = 0x00;
	
	//Execute
	public int readAddr = 0x0000;
	public int dataA = 0x0000;
	public int dataB = 0x0000;
	public int result = 0x0000;
	
	//Write
	public int writeAddr = 0x0000;
	
	public void reset() {
		address = 0x0000;
		instruction = Instruction.NOP;
		decodeData  = 0x00;
		dataA   = 0x0000;
		dataB   = 0x0000;
		result  = 0x0000;
	}
}