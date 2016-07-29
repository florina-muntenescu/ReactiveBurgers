# ReactiveBurgers

Code example for "The ABCs of RxJava" talk.
It contains different ways of creating RxJava ``Observable``s:

* from a static list - where every element of the list will be an emission of an ``Observable``
* from a file - where every line of the file will be an emission of an ``Observable``
* from click events, with the help of ``Subject``s
* from multiple ``Observable``s

Following the burger example from "The ABCs of RxJava", the slices of tomatoes are created from a static list,
the buns from file, the meat from click events and the burgers by ``zip``ing the tomato, meat and bun ``Observable``s.

Also, examples of manipulating a stream of data using the ``filter``, ``map`` and ``flatmap`` operators are given.

The code follows the Model-View-ViewModel pattern:
* the view is the ``BurgerActivity``.
* the view model is the ``BurgerViewModel`` - it exposes the tomato, bun and burger stream of data to the view and allows the view to notify about new pieces of raw meat available. The view model gets the tomato and bun streams of data from the data model. The view model also creates the burger stream of data.
* the data model is the ``DataModel`` - it creates the stream of tomato slices based on a list of tomatoes and the stream of buns base on the number of lines in a file that contain the word "bun".

To showcase the testability of RxJava, unit tests for the ``BurgerViewModel`` class are provided.
