Computer {

	*new {arg arg1="Music", arg2="Creativity", arg3="Code";
		^super.new.init(arg1, arg2, arg3);
	}

	init {arg arg1, arg2, arg3;
		( "Welcome to Computer.play(" ++ arg1 ++ ", " ++ arg2 ++ " & " ++ arg3 ++ ")").postln;
		this.help;
	}

	*play {arg arg1="Music", arg2="Creativity", arg3="Code";
		^super.new.init2(arg1, arg2, arg3);
	}

	init2 {arg arg1, arg2, arg3;
		this.init(arg1, arg2, arg3);
		Platform.case(
			\osx,       {
				( "Welcome to Computer dot play(" ++ arg1 ++ ", " ++
					arg2 ++ " & " ++ arg3 ++ ")").speak;
			},
			\linux,     { "Sorry, this doesn't work in Linuxs".postln },
			\windows,   { "Sorry, this doesn't work in Windows".postln }
		);
	}

	*clone {arg path, url;
		path ?? {path = Platform.userExtensionDir};
		url ?? {url = "https://github.com/freuben/ComputerPlay.git"};
		("cd " ++ path.shellQuote ++ " && git clone " ++ url.shellQuote).unixCmd;
	}

	*update {arg path;
		path ?? {path = Platform.userExtensionDir ++ "/ComputerPlay"};
		("cd " ++path.shellQuote ++ " && git fetch origin && git reset --hard origin/master")
		.unixCmd({
			/*this.help;*/
		});
	}

	*url {arg url;
		var cmd;
		Platform.case(
			\osx,       {cmd = ("open " ++ url); },
			\linux,     {cmd = ("open " ++ url); },
			\windows,   {cmd = ("start " ++ url); }
		);
		cmd.unixCmd;
	}

	//first time to start register Computer.writeRegister("Federico Reuben", "w");

	*writeRegister {arg name="student", mode="a";
		var f, g, path, string, unixFunc, input, writeString;

		unixFunc = {var a, b;
			a = "id -un".unixCmdGetStdOut;
			b = "ipconfig getifaddr en1 & ipconfig getifaddr en0".unixCmdGetStdOut;
			(a.replace(10.asAscii, " ") ++ b.replace(10.asAscii, " "));
		};

		path = Platform.userExtensionDir ++ "/ComputerPlay/files/register.txt";
		f = File(path,mode);
		Platform.case(
			\osx, {string = unixFunc.value; },
			\linux, {string = unixFunc.value; },
			\windows, {string = ("Windows "); }
		);
		input = (name ++ " " ++ string ++ Date.getDate.asString ++ 10.asAscii);
		if(mode == "w", {
			writeString = input.asciiBinaryString;
			 writeString.removeAt(0);
		}, {
			writeString = input.asciiBinaryString;
		});
		f.write(writeString);
		f.close;
	}

	* register {arg name="student", path;
		path ?? {path = Platform.userExtensionDir ++ "/ComputerPlay"};
		path = Platform.userExtensionDir ++ "/ComputerPlay";
		{
			this.writeRegister(name);
			0.1.yield;
			("cd " ++path.shellQuote ++ " && git add files/register.txt").unixCmd;
			0.1.yield;
			("cd " ++path.shellQuote ++ " && git commit -m \"" ++ name ++ " " ++ Date.getDate ++ "\" && git push" ).unixCmd;
			// ("cd " ++path.shellQuote ++ " && git add files/register.txt && git.commit -m \"" ++
			// name ++ " " ++ Date.getDate ++ "\" && git.push").unixCmd;
		}.fork;
	}

	* readRegister {arg path;
		var result, file;
path ?? {path = Platform.userExtensionDir ++ "/ComputerPlay/files/register.txt"};
file = File(path,"r");
result = file.readAllString.binaryStringAscii;
file.close;
		^result;
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

+ String {

	asciiBinaryString {var binary;
		this.ascii.do{|item| binary = binary ++ " " ++ item.asBinaryString};
		/*binary.removeAt(0)*/
		^binary;
	}

	binaryStringAscii {var result;
		this.split($ ).do{|item|
			result = (result ++ ("2r" ++ item).interpret.asAscii);
		};
		^result;

	}
}