Computer {classvar <>fileRegister;

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
		.unixCmd;
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

	* register {arg name, ip, port;
		var network;
		ip ?? {ip = "192.168.0.2" };
		port ?? {port = 57120};
		network = NetAddr(ip, 57120); // loopback
		network.sendMsg("/chat", name);
	}

	* readRegister {arg path;
		var name, ip;
		if(NetAddr.langPort != 57120, {
			"Language Port is not 57120!!!!! restart SC please".warn;
		}, {
			path ?? {path = "~/Desktop/ComputerPlay/register/".standardizePath;};
			name = Date.getDate.asString ++ ".txt";
			fileRegister = File(path ++ name, "w");

			{
				while ( {ip = "ipconfig getifaddr en6".unixCmdGetStdOut;
					if(ip.ascii.isEmpty.not, {
						ip = ip.replace(10.asAscii);
						//here goes the code
						thisProcess.openUDPPort(57120);
						OSCdef(\register, {|msg, time, addr, recvPort|
							fileRegister.write((msg[1].asString ++ " " ++
								Date.getDate ++ 10.asAscii));
							msg[1].asString.postln;
						}, '/chat');
						"Ready to go".postln;
						("IP address is: " ++ ip).postln;
						if(ip.replace(10.asAscii) != "192.168.0.2", {
							"IP address does not match 192.168.0.2".warn;
						});
						//code ends
					});
					ip.ascii.isEmpty }, { "waiting for IP".postln; 0.5.yield; });
			}.fork;

		});
	}

	*writeRegister {
		if(fileRegister.notNil, {
			fileRegister.close;
			OSCdef(\register).free;
		});
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

+ SequenceableCollection {

asAscii {  ^String.fill(this.size,
		{|i| {this[i].asInteger.asAscii}.try ? "" }
	)} // part of wslib

}