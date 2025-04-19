# Example game with SFML, imgui, older OpenGL, and Bullet Physics

## Prerequisites
The C++ IDE [juCi++](https://gitlab.com/cppit/jucipp) should be installed.

## Installing dependencies

### Debian based distributions
`sudo apt-get install libsfml-dev libglm-dev libbullet-dev`

In case of compilation issues, try install `libglu1-mesa-dev` as well.

### Arch Linux based distributions
`sudo pacman -S sfml glm bullet`

### MacOS
`brew install sfml glm bullet`

## Compiling and running
```sh
git clone --recursive https://gitlab.com/ntnu-iini4003/game
juci game
```

Choose Compile and Run in the Project menu.
