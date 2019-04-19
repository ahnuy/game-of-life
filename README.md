# Game of Life

## Description

All living things require energy to keep living. We, as humans, eat food to allow us to survive. Food chains show how living things get their food and keep the cycle of life going. In my project, I displayed a simple food chain including four different states: dirt, food, prey and predator. In order to stay alive, the prey and predators must have energy. Along with many other rules I implemented, inspired by Conway’s Game of Life, my project displays a somewhat realistic simulation of a typical food chain.

<p align="center">
  The food chain my program represents:<br>
  Dirt → Food → Prey → Predator<br>
</p>

## Possible states a cell can be in

- Dirt/dead cell [insert images]

- Food cell

- Prey cell (which exclusively feeds off the food cells)
- Predator cell (which exclusively feeds off the prey cells)

## Rules the cells follow

*Note: Rules are listed in the priority order that they are executed in.*

1. If the current cell is <u>dirt</u>

  - If there are exactly 2 prey cells around, they are able to reproduce and the current cell turns into a prey
  - If there are exactly 2 predator cells around, they are able to reproduce and the current cell turns into a predator
  - There is a 10% chance of the cell turning into food
  - If “attract” mode is turned on, this means the prey and predator cells become more intelligent and go to areas where they is more food and prey for them respectively
  - If there are exactly 4 cells with food around, the cell turns into a prey
  - If there are exactly 4 cells with prey around, the cell turns into a predator
  - If “attract” mode is off, the dirt cell doesn’t follow the 2 above rules
  - Otherwise, the cell stays as dirt

2. If the current cell is <u>food</u>

  - If there are more than 2 prey or more than 2 predator cells around, the food is eaten or destroyed by them respectively and the current cell turns into dirt
  - Otherwise, the cell stays as food

3. If the current cell is a <u>prey</u>

  - If there are 3 or more predators around the prey, it dies because the predators out number and surround the prey so it is eaten
  - If there is 1 or no prey around, the prey dies due to loneliness
  - If there are 5 or more prey around, the prey dies due to overpopulation. There is also too much competition for finding food.
  - If the prey runs out of energy, it dies
  - If there are 3 or more food cells surrounding the prey, the prey eats the food and gains energy based on how much food it ate
  - Otherwise, the cell stays as a prey but loses 10% of its initial energy since it didn’t eat food that would give it energy

4. If the current cell is a <u>predator</u>

  - If there is 1 or no predators around, the cell dies due to loneliness
  - If there are 5 or more predators around, the predator dies due to overpopulation. There is also too much competition for finding prey.
  - If the predator runs out of energy, it dies
  - If there are more than 3 prey cells surrounding the predator, the predator eats the prey and gains energy based on how many prey it ate
  - Otherwise, the cell stays as a predator but it loses 10% of its initial energy since it didn’t eat prey that would give it energy

## Screenshot of program running

[insert images]

“Attract” mode is off                                    	        	“Attract” mode is on

As you can see, when “attract” mode is off, the animals’ intelligence turns off as well. They do not go to areas where they can find the most food so they eventually run out of energy. In the end, only a few small packs of animals continue to live as they reproduce. However, they are unable to evolve.  

When “attract” mode is on, you can see that the ecosystem has a good balance of food, prey and predators. If any of the three disappear, so does the food chain. This simulation lasts “forever”, as it doesn’t have external forces acting upon it.

##  Sample generation

First Generation:                                                     	Second Generation:

[insert images] 

- Cell 1: This cell stays as food because there aren’t enough prey or predators (must be 2 or more) to eat the food

- Cell 2: This cell turns into dirt because there are 2 prey cells around that eat the food

- Cell 3: This cell dies because there aren’t enough prey around to keep it company (only has 1, needs at least 2)

- Cell 4: This cell dies because they are no predators around to keep it company (needs at least 2)

- Cell 5: This cell continues living because it has 4 fellow prey cells to keep it company; it also has 3 food cells surrounding it so it gains 3 energy points by eating a bit of each food

- Cell 6: The cell turns into dirt because it has 3 prey cells surrounding it that eat all of the food
- Cell 7: This cell only has one predator cell surrounding it which is not enough to kill it (need at least 3) so it continues living since it has 2 fellow prey to keep it company. However, it loses 10% of its initial energy because it didn’t eat any food.

- Cell 8: This cell continues living because there are 3 fellow prey cells to keep the prey company. However, it loses 10% of its initial energy because it didn’t eat any food.

- Cell 9: This cell turns into a prey since there are exactly 2 prey cells who reproduce.
