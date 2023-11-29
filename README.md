# Cat Clicker
## Create your animal empire


- ### What will the application do?
  - This application will be an **idle game**, a genre of a game where the user clicks or makes a decision once in a while and lets the game _play itself_.
- ### Who will use it?
  - This game will rated **E for everyone**, even your grandfather will be able to play this one.
- ### Why is this project of interest to you?
  - I've always had an interest in game design, and due to the nature of the simplicity of the genre of this game, the challenge here is to actually make it fun. Some examples of past games in this genre that have done it well are Cookie Clicker, AdVenture Capitalist, AFK Arena, etc.

## User Story
  - Phase 0
    - As a user, I want to earn points while doing a simple action
    - As a user, I want to be able to view the list of available upgrades
    - As a user, I want to be able to spend those points in a shop and buy upgrades / teammates / helpers
  - Phase 1
    - As a user, I want to be able to view the list of owned upgrades on a particular save.
    - As a user, I want to be able to save my game to automatically when I close the file
    - As a user, I want to be able to load my game from file (or new game if I choose)

## Instructions for Grader

- You can generate the first required action related to the user story "adding multiple Xs to a Y" by buying an animal or an upgrade from the shop in the game.
- You can generate the second required action related to the user story "adding multiple Xs to a Y" by clicking the Buy ___ button which will change the number of animals being bought at a time.
- You can locate my visual component by looking at the data package.
- You can save the state of my application by clicking the save game button when in game.
- You can reload the state of my application by clicking the load game button in the main menu.

### Phase 4: Task 3
- In my original design of this game, I had a different design for the associations in the model class. 
- Instead of having a quantity or amount field I had it where everytime you bought something from the shop it would add a new upgradable object to the list
- I realised this was going to be problematic for my game in phase 2 so I actually just refactored it then.
- I also changed the implementation of storing the upgrades using hashmaps and sets instead because now we only needed a single upgradable object per type
- However, now that I've finished phase 4, and looking at the UML diagram I've created myself, I could certainly reduce the coupling a lot
- Especially in the ui panels, it actually is a pretty easy fix to just pass the game object from the zoogame class.
- Other than that, I could also implement the observer design pattern for the shop panel to update only when a certain score is reached instead of checking on every tick, which could cause more lag.