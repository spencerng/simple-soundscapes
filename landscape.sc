// s = Server.default; // create a new Server object and assign it to variable s
// s.boot;

~playWithOffset = { arg track, offsetAngle, distance;
    var maxILD = 15.0; // dB
    var maxITD = 0.00066; // seconds

    // Decrease in dB due to distance, with baseline
    // at 0 dB at 4 ft
    var dbDecrease = abs(20 * log10(4 / distance));

    var b = Buffer.read(s, track).normalize();


    var angleMap = 0.5 - (offsetAngle / 90 / 2.22);
    var ild = abs(log(angleMap / (1 - angleMap)) * maxILD / 3).postln;
    var gain = ild / 2;
    var difference = neg(ild).dbamp.postln;

    var itd = abs(log(angleMap / (1 - angleMap)) * maxITD / 3).postln;
    var delay = itd / ((1 / 48000));

    if ( (offsetAngle < 0),
        // Left side is louder
        {
            a = { [PlayBuf.ar(1, b) * (gain - dbDecrease).dbamp,
                PlayBuf.ar(1, b, startPos: delay) * neg(gain + dbDecrease).dbamp]}.play;
        },
        // Right side is louder
        {
            a = { [PlayBuf.ar(1, b, startPos: delay) * neg(gain + dbDecrease).dbamp,
                PlayBuf.ar(1, b) * (gain - dbDecrease).dbamp]}.play;
        }
    )
};

~playCoordinates = { arg x, y;


}


~playWithOffset.value("C:/users/sng/downloads/05 I want to hold your hand.flac", 80.0, 15.0);