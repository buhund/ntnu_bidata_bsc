# Paths

A game engine for non-linear text driven games.


## Features

- Non-linear story.
- Import new story files.
- Customizeable player character.

## Dependencies

- Java JDK 17
- Maven

See pom.xml in repo for plugins.

## Building and Installation

- The engine has to be run directly in an IDE that supports Java, e.g. JetBrains IntelliJ IDEA.
- Built with Java JDK 17 and Maven.

## Paths Story Format

A Story file is written in plain text, but must be written in the following format:


- A passage starts with :: (two colon) and the passage title.
- The next line contains the content of the passage.
- The last line of a block lists the links.
- Each link is on a separate line, on the form [linktext](reference)
- A block is terminated with an empty line.
- **Two empty lines marks the end of the story file.**


Example:

```paths
Haunted House

::Beginnings
You are in a small, dimly lit room. There is a door in front of you.
[Try to open the door](Another room)

::Another room
The door opens to another room. You see a desk with a large, dusty book.
[Open the book](The book of spells)
[Go back](Beginnings)


```

To make it very clearn: the //emptyLine identifies that that line should be empty, and [number] is line numbers.

```paths
[01] Haunted House
[02] //emptyLine
[03] ::Beginnings
[04] You are in a small, dimly lit room. There is a door in front of you.
[05] [Try to open the door](Another room)
[06] //emptyLine
[07] ::Another room
[08] The door opens to another room. You see a desk with a large, dusty book.
[09] [Open the book](The book of spells)
[10] [Go back](Beginnings)
[11] //emptyLine
[12] //emptyLine
```

The file must have the file ending `.paths`, e.g. `The Best Story Ever.paths`
It must be placed in the project directory `story/`, found at `src/main/resources/story/`


## Attributions and artwork

### Item icons
- "Fantasy Icon Pack" made by [Ravenmore](https://itch.io/profile/ravenmore).
- Download link: https://ravenmore.itch.io/fantasy-icon-pack

### Character models
- Character models (`/characterModels/` and `/npc/`) are original artwork, made by John I. Eriksen specifically for this project, and are released under the GPL v3.
- Boss Vapordorf was generated using DALL-E, with credits and input by John I. Eriksen.

### Background images
- Vintage japanese bird: https://pixabay.com/illustrations/vintage-japanese-watercolour-1829842/
- Forest Battle background: https://craftpix.net/freebies/free-forest-battle-backgrounds/, `game_background_1.png`
- distantWatcher 01, 02, 03, and 04 was generated using DALL-E, with credits and input by John I. Eriksen.
- characterModelViewBackground.png was generated using DALL-E, with credits and input by John I. Eriksen.

## Application icon
- Original artwork, made by John I. Eriksen specifically for this project, and are released under the GPL v3.

## Licensing

GPLv3, see [this page](https://www.gnu.org/licenses/gpl-3.0.en.html).
