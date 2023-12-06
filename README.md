
# Mobile Photo Editor

Mobile Photo Editor is a project for the "Multimedia Interactive Systems" course at Gdańsk University of Technology. 

The idea was to develop a mobile app, that would allow it's user to add various filters to selected photos. A side goal was for me to learn Android app development using Kotlin and explore it's features.

## Implementation features
These are a few features that I found beneficial in terms of gained knowledge
- The UI is built as a single activity with a fragment container as a placeholder for custom fragments corresponding to different actions, such as effects menu, effect config menu, etc. 
- To speed up pixel-wise operations, the app makes use of [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html#documentation "Kotlin Coroutines documentation") mechanism for asynchronous processing.
- The effects logic is built based on a factory pattern to make adding new effects easier and purely as an exercise in using design patterns.

## Features

- Selecting a photo from device's memory or taking a new one inside the app
- Effects:
    - Grayscale
    - Brightness
    - Blur (using Gaussian kernel)
    - Sharpness (using Laplacian kernel)
    - Contrast
    - Gamma correction
    - Colour balance


