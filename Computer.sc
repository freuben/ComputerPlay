Computer {var <localPath, <gitUrl, <gitPath;

  *new {arg arg1="Music", arg2="Creativity", arg3="Code";
    ^super.new.init(arg1, arg2, arg3);
	}

	init {arg arg1, arg2, arg3;

    localPath = Platform.userExtensionDir;
    gitUrl = "https://github.com/freuben/ComputerPlay.git";
    gitPath = localPath ++ "/ComputerPlay";

    ( "Welcome to Computer.play(" ++ arg1 ++ ", " ++ arg2 ++ " & " ++ arg3 ++ ")").postln;
	}

 *play {arg arg1="Music", arg2="Creativity", arg3="Code";
    ^super.new.init2(arg1, arg2, arg3);
 }

  init2 {arg arg1, arg2, arg3;
    this.init(arg1, arg2, arg3);
    Platform.case(
    \osx,       {     ( "Welcome to Computer dot play(" ++ arg1 ++ ", " ++ arg2 ++ " & " ++ arg3 ++ ")").speak; },
          \linux,     { "Sorry, this doesn't work in Linus".postln },
    \windows,   { "Sorry, this doesn't work in Windows".postln }
);
	}

  // *clone {
  //   this.play;
  //   ("cd " ++ localPath.shellQuote ++ " && git clone " ++ gitUrl.shellQuote).unixCmd;
  // }
  //
  // *update {
  //   this.play;
  //   ("cd " ++ ~gitPath.shellQuote ++ " && git fetch origin && git reset --hard origin/master").unixCmd;
  // }

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