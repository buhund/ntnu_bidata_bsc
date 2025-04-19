## Requirements

Operating system (tested):
- Linux (Kubuntu 22.04 LTS, Ubuntu 22.04 LTS)
- Mac OS (Ventura)
- Windows 11.

_The application should be able to run on any modern computer and operating system that can install either JRE or JDK, but due to a lack of resources, the platforms above are the only ones that have been tested._

#### How to run and/or install

The application can be installed and run as an executable .jar file, or it can be run directly from source. Follow the instructions according to which method you want to use:

- [Install and run as executable](https://gitlab.stud.idi.ntnu.no/team_01-idatt1002/project-assignment-idatt1002-y2023_spring-t01/-/wikis/Installation-Manual#install-and-run-as-executable)
- [Build and run directly from source](https://gitlab.stud.idi.ntnu.no/team_01-idatt1002/project-assignment-idatt1002-y2023_spring-t01/-/wikis/Installation-Manual#build-and-run-from-source)


## Install and run as executable

#### Software requirements

- Java Runtime Environment (JRE) version 8 (or newer) 
   - [Download from Java.com](https://www.java.com/en/download/) or install via your preferred package manager.


#### Installing
- Download the Baecon.zip archive, for your operating system (Linux, Mac or Windows), either from Inspera or from the projects [OneDrive (link)](https://studntnu-my.sharepoint.com/:f:/g/personal/jierikse_ntnu_no1/EpYrgFTRTDtBr85oTRZC4i0B5VVj5MwGV4pTgRca2CuWGA?e=Fx4Cbz).
- Move to the archive, to a suitable location, if you want to keep it somewhere specific.
- Extract the archive contents (unpack).
- This will extract the archive contents into a new directory, "Baecon", inside the same directory as the archive is located.
- Navigate inside the newly created Baecon directory.
- Run the .bat script, or .sh script to launch the application.
   - You may need to adjust file permissions to run the scrips. See below.
- Alternatively, run the Baecon.jar file to launch the program (double-click, or mark the file and press "Enter").

#### Linux and Mac OS

You may need to update permissions and make the file executable to run. There are a few ways to do this.

**In the terminal:**

In the terminal, navigate to the directory containing the Baecon.jar file. Probably inside `home/USER/Downloads`

`cd ~/Downloads/`

Run the following command so mark file as `executable`:

`chmod +x Baecon.jar`

Then you can execute it by running `./Baecon.jar`, or run it via GUI action.

**In the GUI, Linux** (here: Dolphin file manager):
- Navigate to the directory containing the Baecon.jar file.
- Right-click (alt + enter) the file to open the properties dialog window.
- Select the Permissions tab.
- Check the "Is Executable" checkbox.
- Click "OK" button to close the window.
- You can now run the file.



## Build and run from source


#### Software requirements:

**Additional components are required for building and running from source:**

- Java Development Kit (JDK) version 17 LTS (or newer)
   - [Download from Java.com](https://www.java.com/en/download/) or install via your preferred package manager.
- Maven version 4.0.0 (or newer)
- JavaFX version 17.0.6 (or newer)
- Java compatible IDE or terminal emulator

#### Clone project repository

Use Git to clone the repository (or download archive [directly](https://gitlab.stud.idi.ntnu.no/team_01-idatt1002/project-assignment-idatt1002-y2023_spring-t01/-/archive/main/project-assignment-idatt1002-y2023_spring-t01-main.zip) (zip)).

Clone with SSH:

`git clone git@gitlab.stud.idi.ntnu.no:team_01-idatt1002/project-assignment-idatt1002-y2023_spring-t01.git`

Clone with HTTPS:

`git clone https://gitlab.stud.idi.ntnu.no/team_01-idatt1002/project-assignment-idatt1002-y2023_spring-t01.git`



#### Run: Terminal

- Navigate to the project root directory (containing amon others the `src` directory).
- Run the following command in your preferred terminal emulator:

`mvn clean install`

`mvn javafx run`


#### Run: IDE (GUI and terminal)

- Load the project into a compatible IDE (e.g. IntelliJ, Eclipse or Netbeans).
- Press the "Run" (or "Play") button to compile and run.

Altneratively, run via the IDE terminal:

`mvn clean install`

`mvn javafx run`


