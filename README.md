# Forest simulation
This project is about simulating animal and plant behaviour in the real world.

In the simulation there exists 4 animal species (two herbivore and two carnivore):

* Wolf
* Lynx
* Deer
* Hamster

And a general plant category:

* Plant

The forest habitat is presented as a 2D cell field where animals live, breed, hunt, sleep, move, etc.

There is also one underlying layer that cannot be observed on the map. It's the Plant layer where plants grow.

Herbivores eat plants and are being hunted by carnivores. Just like in the real world!

In this virtual world, there also exist different times of the day. For example, some animals sleep during the day and hunt at night, and others do it the other way round.

Animals can also get infected, spread diseases to other animals, and be harmed.

### How to run the simulation

To start the simulation, all you need to do is start the main method of the Simulation class.

The simulation ends when only one of the species is not extinct. If we get lucky, and the simulation becomes well balanced, it could run unlimited steps.

***

### Contributors

* Vakaris Paulavičius
* Flavio Melinte Citea

Version 1.0

2021 King's College London
