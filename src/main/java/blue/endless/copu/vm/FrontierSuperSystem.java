package blue.endless.copu.vm;

public class FrontierSuperSystem {
	private Object[] fileDescriptors;
	private Object[] memoryDescriptors;
	private Object registerFile;
	
	/**
	 * This field describes how the load/store unit will interact with certain other latches
	 */
	private int memoryBar = 0x00;
	private int loadStoreLatch = 0x0000;
}
