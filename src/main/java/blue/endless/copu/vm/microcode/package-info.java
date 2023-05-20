/**
 * <h1>Microcode
 * 
 * This package includes behavior smaller than one instruction - a single pipeline stage for an instruction, usually.
 * Breaking up instructions this way exposes commonalities between instructions in a way where more code sharing can
 * happen. DRY code helps ensure consistent behavior throughout the machine.
 */

package blue.endless.copu.vm.microcode;