Before reading, set tabspace to 2
	In vim:
		set ts=2

Enhancements:
	* colours were added.
			Helicopter is green.
			Cannon is oragne
			Structures is blue and have 5 monotonic variations.
	* difficulty progresses (you may want to run god mode with g, and propel mode with c)
			Beginning -- has structures on the ground
			Middle -- the sky begin to be covered by structures (or helicopter is entering a building)
			End -- there are obstacles in the middle of the screen (can be challeging if not impossible)
			* note: propel mode is part of the game mechanics and it doesn't incur score penalty
	* scores are shown at the end. The following affects scores:
			-- cannons being hit ++
			-- survival (helicopter reaches the end of the map) ++++
			-- fire a missile -
			-- use "total" brake --
		you can change these settings in include/config.h and then remake the binary
	* the map is randomly generated (press r to restart the game and see)
			-- you can affect the maximum x distance by changing the following macro in include/config.h
				#define XBLOCK_NUM 600
				though don't change YBLOCK_NUM (or you'll need to change DEFAULT_HEIGHT, too, otherwise resizing may break)

Installation:
Run the game with
	make -f run

Troubleshooting:
Please be warned that if your default library path doesn't contain X11, you have to adjust LDFLAGS in run (makefile)
	LDFLAGS = -L$(LIBRARY_PATH_OF_X11) -lX11

Similarly, you might need to set up include path (INCLUDE_DIR) if the compilation misses X11-related header files
	INCLUDE_DIR = -Iinclude -I$(INCLUDE_PATH_OF_X11)

Key controls:
For key controls, please refer to the splash screen.

Memory leak:
My codes do not use any new or malloc, and they call memory deallocation functions for each X allocation function, which are handled by the destructors. So, if there is any memory leak, it's Xlib's fault.
