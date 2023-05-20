package blue.endless.copu.vm;

public class RegisterFile {
	public static final int STATUS_ZERO       = 0b0000_0001; // Result of operation is zero
	public static final int STATUS_NEGATIVE   = 0b0000_0010; // Result of operation is negative (or overflow)
	public static final int STATUS_HALF_CARRY = 0b0000_0100; // Result of operation carried from the low byte to high
	public static final int STATUS_CARRY      = 0b0000_1000; // Result of operation overflowed
	public static final int STATUS_PARITY     = 0b0001_0000; // xor of all bits of result, = 1 if even #, 0 if odd - an "odd parity bit"
	
	public int status;             // 16
	public int programCounter;     // 16
	public int[] generalRegisters = new int[16];
}
