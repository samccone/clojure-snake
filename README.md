# clojure-snake

clojure snake a work in progress

<img width="195" alt="screen shot 2015-07-24 at 5 24 00 pm" src="https://cloud.githubusercontent.com/assets/883126/8920545/70862012-3498-11e5-93de-342268d2a871.png">

### Todo

* [x] Prevent moving ontop of self
* [x] When the snake moves out of the window bounds, the snake should wrap around
* [x] Add apples that you can eat
* [x] When you eat an apple grow the snake
* [x] When you eat an apple lay down another apple
* [x] Slowly increase speed
* [x] End the game when the snake runs into itself
* [ ] Add score to title bar ... `score = (.2 * time-alive * apples eaten)`
* [ ] Allow a user to restart the game
* [ ] Apples should not be placed on top of other apples or on top of the snake
* [ ] Add a start screen
* [ ] Add an autoplay mode
* [ ] Add an optional computer AI mode
* [ ] Add a difficulity slider (1 - 10) determines growth and speed
* [ ] Use left and right arrows to steer snake in continuous angles

### Dev

    $ lein repl
    $ (require 'clojure-snake.core)
    $ (clojure-snake.core/game)

When you make a file change run to reload

    $ (require 'clojure-snake.core :reload-all)
