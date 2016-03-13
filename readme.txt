java version "1.8.0_66"
This was tested on Linux (Ubuntu).

Note: The state of the application is saved in laststate.txt.
The first line in laststate.txt saves the number of images.
If you want to reset the app (no images loaded), set the first line to 0.

When files are loaded and at least one is not an image, then a dialog will
appear explaining the error and no images will be loaded.

When files are loaded (and they are all images), then the images with the
same full path as an image already in the model will not be loaded again.
