package blue.endless.copu.vm.microcode;

import blue.endless.copu.vm.CoPU;

@FunctionalInterface
public interface FetchAction {
	public static final FetchAction UNLATCH     = (pipeline, machine) -> false;            // FOO
	public static final FetchAction UNARY_CONST8= FetchAction::unaryConst8;                // FOO    0x00
	public static final FetchAction UNARY_ZP8   = FetchAction::unaryZeropage8;             // FOO.B  A,   [0x00]
	public static final FetchAction REG_CONST4  = FetchAction::registerConst4;             // FOO    A,    0x0
	public static final FetchAction REG_CONST16 = new RegisterConst16();                   // FOO    A,    0x0000 ; additional latency
	public static final FetchAction REG_REG     = FetchAction::registerRegister;           // FOO    A,    B
	public static final FetchAction REG_MEM8    = FetchAction::registerMemory8;            // FOO.B  A,   [B]
	public static final FetchAction REG_MEM16   = FetchAction::registerMemory16;           // FOO.W  A,   [B]
	
	public static final FetchAction REG_IND8    = new RegisterIndirect8();                 // FOO.B  A,  ([B])
	public static final FetchAction REG_IND16   = new RegisterIndirect16();                // FOO.W  A,  ([B])
	
	public boolean fetch(PipelineData pipeline, CoPU machine);
	public default void additionalFetch(PipelineData pipeline, CoPU machine) {};
	
	
	public static boolean registerRegister(PipelineData pipeline, CoPU machine) {
		int rA = pipeline.decodeData & 0x0F;
		int rB = (pipeline.decodeData >> 4) & 0x0F;
		pipeline.dataA = machine.regs.generalRegisters[rA];
		pipeline.dataB = machine.regs.generalRegisters[rB];
		
		return false;
	}
	
	public static boolean registerMemory8(PipelineData pipeline, CoPU machine) {
		registerRegister(pipeline, machine);
		pipeline.dataB = machine.combinedMemory.read8(pipeline.dataB);
		return false;
	}
	
	public static boolean registerMemory16(PipelineData pipeline, CoPU machine) {
		registerRegister(pipeline, machine);
		pipeline.dataB = machine.combinedMemory.read16(pipeline.dataB);
		return false;
	}
	
	public static boolean registerConst4(PipelineData pipeline, CoPU machine) {
		int rA = pipeline.decodeData & 0x0F;
		int c = (pipeline.decodeData >> 4) & 0x0F;
		
		pipeline.dataA = machine.regs.generalRegisters[rA];
		pipeline.dataB = c;
		return false;
	}
	
	public static class RegisterConst16 implements FetchAction {
		@Override
		public boolean fetch(PipelineData pipeline, CoPU machine) {
			int rA = pipeline.decodeData & 0x0F;
			pipeline.dataA = machine.regs.generalRegisters[rA];
			return true;
		}
		
		@Override
		public void additionalFetch(PipelineData pipeline, CoPU machine) {
			pipeline.dataB = machine.combinedMemory.read16(machine.regs.programCounter);
			machine.regs.programCounter += 2;
		}	
	}
	
	public static boolean unaryConst8(PipelineData pipeline, CoPU machine) {
		pipeline.dataA = pipeline.decodeData;
		return false;
	}
	
	public static boolean unaryZeropage8(PipelineData pipeline, CoPU machine) {
		pipeline.dataA = machine.combinedMemory.read8(pipeline.decodeData);
		return false;
	}
	
	public class RegisterIndirect8 implements FetchAction {
		@Override
		public boolean fetch(PipelineData pipeline, CoPU machine) {
			registerRegister(pipeline, machine);
			return true;
		}
		
		@Override
		public void additionalFetch(PipelineData pipeline, CoPU machine) {
			pipeline.dataB = machine.combinedMemory.read8(pipeline.dataB);
		}
	}
	
	public class RegisterIndirect16 implements FetchAction {
		@Override
		public boolean fetch(PipelineData pipeline, CoPU machine) {
			registerRegister(pipeline, machine);
			return true;
		}
		
		@Override
		public void additionalFetch(PipelineData pipeline, CoPU machine) {
			pipeline.dataB = machine.combinedMemory.read16(pipeline.dataB);
		}
	}
}
