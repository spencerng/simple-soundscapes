s = Server.default;
s.boot;

// Plays audio at a source directed towards the listener, at a given
// angle and distance from the front of the listener.
// Negative angles indicate a source is to the left, and positive angles for sources to the right.
// There's no differentiation between sources to the front/back
~playBinaural = { arg track, offsetAngle, distance, loop = 1.0;
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
            a = { [PlayBuf.ar(1, b, loop: loop) * (gain - dbDecrease).dbamp,
                PlayBuf.ar(1, b, startPos: delay, loop: loop) * neg(gain + dbDecrease).dbamp]}.play;
        },
        // Right side is louder
        {
            a = { [PlayBuf.ar(1, b, startPos: delay, loop: loop) * neg(gain + dbDecrease).dbamp,
                PlayBuf.ar(1, b, loop: loop) * (gain - dbDecrease).dbamp]}.play;
        }
    )
};


// Plays a sound directed towards a listener, where the
// source is located at the given xy coordinates
~playCoords = { arg track, x, y, loop = 0.0;
    var dist = sqrt(x ** 2 + y ** 2);

    var angle = neg(atan(abs(y)/abs(x)).raddeg - 90);
    if ( (x < 0), { angle = neg(angle); }, {});

    ~playBinaural.value(track, angle, dist, loop);
};

