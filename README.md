# clojure-snake

clojure snake a work in progress

<img width="195" alt="screen shot 2015-07-24 at 5 24 00 pm" src="https://cloud.githubusercontent.com/assets/883126/8884925/dad3c040-3228-11e5-93ad-58bea4574ce5.png">

### Todo

* [x] Prevent moving ontop of self
* [ ] When the snake moves out of the window bounds, the snake should wrap around
* [ ] Add apples thatyou can eat
* [ ] When you eat an apple grow the snake
* [ ] When you eat an apple lay down another apple
* [ ] Slowly increase speed
* [ ] End the game when the snake runs into itself
* [ ] Allow a user to restart the game
* [ ] Apples should not be placed on top of other apples or on top of the snake

### Dev

    $ lein repl
    $ (require 'clojure-snake.core)
    $ (clojure-snake.core/game)

When you make a file change run to reload

    $ (require 'clojure-snake.core :reload-all)

