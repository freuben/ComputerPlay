Computer {var s, <arg1, <arg2, <arg3;

  *play {arg arg1="Music", arg2="Creativity", arg3="Code";
		^super.new.initBasicClass(arg1, arg2, arg3);
	}

	initBasicClass {arg argument1, argument2, argument3;
		arg1 = argument1;
		arg2 = argument2;
		arg3 = argument3;

    ( "Welcome to Computer.play(" ++ arg1 ++ ", " ++ arg2 ++ " & " ++ arg3 ++ ")").postln;
	}


	message1 {
	"hello there".postln;
	}

	message2 {

	}


	*initClass {

	//SynthDefs come here
    //    StartUp.add {
	//SynthDef.writeOnce(\basicSynth, {arg out=0;
	//signal = SinOsc.ar;
//	Out.ar(out, signal);
//	});
    //}

	}

}