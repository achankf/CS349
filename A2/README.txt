Run with ``make run''

I have tried my best..... spent like 13+ hours per day for several days and skipped several meals for arm rotation, objects manipulation, and the math... but physics, geometry, and linear algebra are too difficult for me... so I skipped most physics requirements and instead focused on refining of the gameplay.

That being said, you still need to directly manipulate the machine and its arms though the mouse.

Imagine this game as a variant of Tetris, where candy blocks are dropped from the sky, and the doozer have to move the candy blocks so that you can get as many candies as possible... until the bag (screen) is full of candies then the game is over (you have 10 seconds to move the candies for more room).

This game is much more difficult that normal Tetris because you have to directly control the movement of the candies (blocks), and the candies STAYS forever (i.e. no block will disappear if you complete a "line").

This game is meant to be at most 5 minute long (if you survive), and your first try will probably be game over very soon.

I challenge you to play over 100 seconds, and if you are a pro then 200 seconds.
P.S. My best record was 250.48 seconds (~ 4 min).

Enhancement:
	* Tetris-like gameplay
	* scoring based on time elapsed (one of the rare moments that I code with concurrent programming)
	* scalable window (event during the gameplay)

Controls:
	* With mouse click:
		-- the body: move left and right (back and forth)
		-- the arms: rotate them
		-- the magnet: has two states: on and off, which allows you to pick up or release a candy

Hint:
	* the dot in front of the magnet indicates where you can pick up a block (it disappears when you have picked up a candy)
	* you can only move each arm for 180 degree
	* candies only fall from the right of the window, so try to move the candies to the left
	* you can pick ANY candy you want! If there is nothing below a candy, it will fall down to fill in the gaps.
