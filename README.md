# Soundscapes, Simplified

Create live soundscapes in SuperCollider, powered by interaural differences in timing and intensity to simulate localized sound sources using just a pair of headphones.

This repo is a work in progress.

## Usage

Load `binaural.sc` into SuperCollider, then evaluate the file. This loads two functions that can be called to play and localize sound sources:

* `playBinaural { track, offsetAngle, distance, loop = 0.0 }`: pass the path of the track you want to play to `track`, with the given distance (in feet) away from the listener and angle offset (-90° to 90°) from the listener. A negative offset indicates a source is to the left of the listener, while a positive offset indicates a source is to the right of the listener. The baseline 0 dB point is at a distance of 4 feet.
  * Example usage: `~playBinaural.value("creek.wav", -30, 8)` plays the track at an offset of 30° to the left of the listener, at 8 feet away
* `playCoords { track, x, y, loop = 0.0 }`: pass the path of the track, and simulate playback at the location specified by `x` and `y`, with the listener at (0, 0).
  * Example usage: `~playCoords.value("creek.wav", -15, 8, 1.0)` plays the track 15 feet to the left and 8 feet in front of the listener while looping playback

## Examples

A nature soundscape, with source files from [freesound.org](https://freesound.org/) and cued through `nature.sc`, is rendered at `nature-soundscape.wav`.
