package blue.endless.copu.vm;

import java.util.Map;
import java.util.HashMap;

import blue.endless.copu.vm.microcode.PipelineData;

public class CoPU {
	/*
	 * CoPU-0802 is *pipelined*.
	 * 
	 * [ Fetch ] -> [ Decode ] -> [ Execute ] -> [ Write ]
	 */
	PipelineData pipelineFetch = new PipelineData();
	PipelineData pipelineDecode = new PipelineData();
	PipelineData pipelineExecute = new PipelineData();
	PipelineData pipelineWrite = new PipelineData();
	
	
	public RegisterFile regs = new RegisterFile();
	public MemoryDevice combinedMemory;
	private Map<Integer, Instruction> opcodeTable = new HashMap<>();
	
	public CoPU() {
		opcodeTable.put(0x00, Instruction.NOP);
	}
	
	public Map<Integer, Instruction> getOpcodeTable() {
		return opcodeTable;
	}
	
	public void cycle() {
		
		boolean read = false;
		
		// WRITE - also performs postfix increment or decrement if needed
		
		pipelineWrite.instruction.writeAction().write(regs, combinedMemory, pipelineWrite.writeAddr);
		pipelineWrite.instruction.postfixAction().postfix(regs, combinedMemory, pipelineWrite.writeAddr);
		
		// EXECUTE (requires execute mask from instruction in EXECUTE pipeline location)
		
		if (pipelineExecute.instruction.readAction().read(regs, combinedMemory, pipelineExecute.address)) {
			read = true; // Stall any reads further towards the start of the pipeline
		}
		
		pipelineExecute.instruction.executeAction().execute(regs, combinedMemory, pipelineExecute);
		
		// DECODE (decode mask is set by every actual instruction)
		int opcodeNum = (pipelineDecode.decodeData >> 8) & 0xFF;
		pipelineDecode.decodeData &= 0xFF;
		pipelineDecode.instruction = opcodeTable.get(opcodeNum);
		if (pipelineDecode.instruction == null) pipelineDecode.instruction = Instruction.NOP;
		
		// FETCH (opcode is fetched by fetch unit)
		if (!read) {
			//Access the memory at PC
			pipelineFetch.decodeData = combinedMemory.read16(regs.programCounter);
			regs.programCounter += 2;
			
		} else {
			pipelineFetch.decodeData = 0x0000; //TODO: NOP instruction code here
		}
		
		// BUBBLE - not a real pipeline stage, but very similar to advancing shift registers or opening latches in
		// between pipeline executions
		
		PipelineData extra = pipelineWrite; extra.reset();
		
		pipelineWrite = pipelineExecute;
		pipelineExecute = pipelineDecode;
		pipelineDecode = pipelineFetch;
		pipelineFetch = extra;
		
	}
}