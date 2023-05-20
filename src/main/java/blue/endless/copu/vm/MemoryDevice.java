package blue.endless.copu.vm;


public interface MemoryDevice {
	public int read8(int address);
	public int read16(int address);
	public void write8(int address, int value);
	public void write16(int address, int value);
}
