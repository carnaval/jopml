#!/bin/sh
SDIR=$0
SORIG=`pwd`
S=$1
cd `dirname $SDIR`
echo "> Building proc"
#make clean > /dev/null
make > /dev/null
echo "> Generating netlist."
circuits/proc netlists/proc.nl
echo "> Netlist: netlists/$S.nl"
echo "> Input (netlists/$S.nl.input) :"
cat netlists/proc.nl.input | tail -n +2
echo "> Simulation:"
java -jar simulator/dist/JOP_Simulator.jar $* -brief -printram -f netlists/proc.nl.input -ram program_rom=./prog.rom netlists/proc.nl
cd $SORIG
