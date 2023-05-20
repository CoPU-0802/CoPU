# CoPU-0802

User Manual

Copyright 2023, The Chipper Team
Released Under Creative Commons Zero (CC0) 1.0 - https://creativecommons.org/publicdomain/zero/1.0/

## Life Support

**Warning** - DO NOT USE THIS PRODUCT IN CRITICAL SYSTEMS SUCH AS LIFE SUPPORT OR SPACE TRAVEL.

CHIPPER PRODUCTS ARE NOT AUTHORIZED FOR USE AS CRITICAL COMPONENTS IN LIFE SUPPORT DEVICES OR
HARDENED SPACEFLIGHT SYSTEMS WITHOUT THE EXPRESS PRIOR WRITTEN APPROVAL OF CHIPPER. Incorporation
into such systems can reasonably be expected to cause significant injury to the user and equipment.


## Architectural Overview

The 0802 is the first of the second generation CoPU units. It features a higher throughput, pipelined
design, and more efficient memory utilization than comparable first-generation units.

The 0802 has sixteen general-purpose 16-bit registers which can be used for both data and addresses.
It also has a Stack Pointer (SP), Base Pointer (BP), Program Counter (PC), a Status Register (F).


( TODO: Alan please add block diagram )


## Arithmetic & Logic Unit

The 16-bit arithmetic and logical operations in the CPU are executed by the ALU. The ALU communicates
with the registers and the system memory bus in order to accomplish these tasks. The operations
available to the ALU are:

```
ADD   SUB   AND   OR
ADC   SBC   XOR   CMP
SHL   SHR   INC   DEC
SET   RST   TST
```

These operations are explained in detail later.


## Internal Registers and CoPU Control

The CoPU has an internal 16-bit register used to decode instructions (IR). Data is fetched from system
memory at the Program Counter address into IR, and the Program Counter is incremented by 2. The
IR is then used to supply all control signals required to access registers and memory, instruct
the ALU, and provide any external control signals required. An additional machine word may then be
fetched into IR to supplement operand data, incrementing PC again by 2.

Two internal 16-bit accumulator registers, A and B, are used to retain data within the ALU. These
registers are wired directly into the operand inputs for its arithmetic and logical functions.

## Pin Configuration

The CoPU is offered in a 40-pin DIP configuration, a 44-pin square-LPC package, and a 64-pin LGA SoC. Power
for most configurations is a single +3.3V and GND pair.


( TODO: Add pin configuration images )

## TODO: Memory Read / Write Section

## TODO: Timings

## TODO: Interrupts

## Instructions

### Instruction Types

Load / Store

Arithmetic and Logical

Control Flow

Input / Output

CoPU Control

### Addressing Modes

4-Bit Immediate
: A 4-bit value is embedded in the instruction word and will be used as the source operand.
: No overhead
: Example - `MOV 0x4, R0`

Immediate
: The instruction word is followed by a 16-bit value which will be used as the source operand.
: 2 cycle overhead
: Example - `AND 0x1000, R0`

Relative
: An 8-bit signed displacement is embedded in the instruction word which is added to the Program Counter.
: No overhead
: Example - `JMP -12`

Absolute
: The instruction word is followed by a 16-bit address which is used as either a source or destination operand.
: 2 cycle overhead
: Example - `MOV [0x3008], R0`, 

Indexed+Immediate
: Embedded within the instruction word is an address register and an index register, which is added to the address to produce the destination operand.
: 2 cycle overhead
: Example - `MOV 0x0000, [R0]+R1`

**RM+REG and REG+RM Modes**

Embedded within the instruction word is a register operand and an R/M field. A second instruction word contains the other operand.
All of these modes require a 2 cycle base overhead, and may incur further delays from additional memory reads.

Absolute
: The RM operand is a 16-bit absolute memory address
: Additional overhead of 2 cycles
: Example - `MOV [0x3008], R0`

Indirect Absolute
: The RM operand is a 16-bit absolute memory address which, when dereferenced, will produce the address of the operand
: Additional overhead of 4 cycles
: Example - `MOV ([0x3008]), R0`

Indexed w/Postfix
: The RM operand contains an address register and an index register. The index register may be optionally incremented or decremented after the operation.
: Additional overhead of 2 cycles if an operand read occurs
: Example - `ADD R3, [R0]+R1 ++`, `ADD R3, [R0]+R1 --`
