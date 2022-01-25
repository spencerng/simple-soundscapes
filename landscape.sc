s = Server.default; // create a new Server object and assign it to variable s
s.boot;

~playWithOffset = { arg track, offsetAngle;
    var maxILD = 15.0;
    var maxITD = 0.2;
    var b1 = Buffer.readChannel(s, ~track, channels: [0]);
    var b2 = Buffer.readChannel(s, ~track, channels: [1]);

    var angleMap = 0.5 - (offsetAngle / 90 / 2.22);
    var ild = abs(log(angleMap / (1 - angleMap)) * maxILD / 3).postln;
    var gain = ild / 2;
    var baselineVol = 10 ** (neg(maxILD) / 20);
    var louderVol = 10 ** (gain / 10) * baselineVol;
    var softerVol = 10 ** (neg(gain) / 10) * baselineVol;
    var ratio = (softerVol / (louderVol + softerVol)).postln;


    var itd = abs(log(angleMap / (1 - angleMap)) * maxITD / 3).postln;
    var delay = 1 / (1 / ~b1.sampleRate * itd);

    if ( (offsetAngle < 0),
        // Left side is louder
        {
            a = { [PlayBuf.ar(1, ~b1) * (1 - ratio), PlayBuf.ar(1, ~b2, startPos: ~delay) * ratio]}.play;
        },
        // Right side is louder
        {
            a = { [PlayBuf.ar(1, ~b1, startPos: ~delay) * ratio, PlayBuf.ar(1, ~b2) * (1 - ratio)]}.play;
        }
    )
};

~playWithOffset.value("C:/users/sng/downloads/05 I want to hold your hand.flac", 80.0);