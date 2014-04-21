<center>**[Eclipse Install](#setup-in-linuxmacwindows-with-eclipselike-microsoft-word-but-for-writing-code)** |
**[Gradle Install](#setup-in-linuxmacwindows-with-gradlecompiles-our-java-code-from-command-line)** |
**[How to Contribute](#how-to-contribute)** |
**[What are all the files here for?](#what-each-filefolder-in-this-repository-is-for)**</center>

# [WalnutiQ](http://walnutiq.com)

“*Say what you want to say and let the words fall out. Honestly I want to see you be brave.*”  

~ Sara Beth Bareilles

[![Build Status](https://travis-ci.org/quinnliu/WalnutiQ.png)](https://travis-ci.org/quinnliu/WalnutiQ)

Welcome! WalnutiQ is a human brain model simulation in Java. 
The goal of this repository is to store code that can 
simulate a full sized human brain in real-time. A real intelligence machine 
built on biological principles will be able to solve many of the 
problems which currently plague the world and allow us to unravel
the mystery of human conciousness. It will be a long journey but
this has the potential to dramatically change the world for the 
better.

Here you will find code that allows you to build a partial 
human brain model, train it on input data using theorized
learning algorithms, and view its activity as text output.
All of the code here supports modeling of the human 
brain at a high level of abstraction while still allowing user
access to individual neuron properties. For more information please 
watch this [video playlist](http://www.youtube.com/playlist?list=PLPXsMt57rLtgddN0NQEmXP-FbF6wt2O-f) 
to become familiar with the neuroscience behind this repository.

If you are interested in becoming a researcher/developer, the 
only requirement is interest in understanding how the brain 
really works. If you are not interested in programming there are
plenty of research tasks we need your help on [here](https://github.com/quinnliu/WalnutiQ/issues?labels=Research).
If you are interested in gaining programming experience by
building brain models I would be happy to google hangout with 
you or meet you in person regardless of your programming
experience! Feel free to e-mail me at quinnliu@vt.edu to get 
coffee/lunch/dinner to talk about how you can contribute.

Most importantly, this research is made possible by the 
neuroscientists and engineers at [Numenta](http://numenta.org/). 
Numenta has designed a exciting new technology that accurately models 
layers 3 & 4 of the human neocortex. They have generously released 
the pseudocode for their learning algorithms, and this repository is an 
extended implementation of their algorithms using object-oriented 
programming. Make sure to read Numenta's great explanation 
of their research in this [white paper](https://db.tt/FuQWQuwE) 
to better understand the theory behind this repository.

## Setup in Linux/Mac/Windows with Eclipse(like Microsoft Word but for writing code)
1. [Install Eclipse](http://wiki.eclipse.org/Eclipse/Installation)

2. Fork this repository and clone it locally. Then import it as a 
   project into Eclipse.

3. IMPORTANT: You will notice that your folders will have "red X's". 
   To fix this right click your `src` folder then hover over "New", 
   then click "Source Folder". Then give it the "Folder name:" src. 
   You will also need to do this same process for the folders 
   `experiments` and `images`. Make sure when you type the 
   "Folder name:" you put in the folder name of 
   the folder you previously just right clicked.

4. In Eclipse, add all the libraries (.jar file) in the folder 
   `referencedLibraries/` by right-clicking your project `WalnutiQ` 
   in the package explorer `=>` Build Path `=>` Add External Archives...

5. In Eclipse, add JUnit 4 by right-clicking your project `WalnutiQ` 
   in the package explorer `=>` Add Libraries... `=>` JUnit `=>`
   Next > `=>` Finish

6. In Eclipse, also add JRE System Library by right-clicking 
   your project `WalnutiQ` in the package explorer `=>` 
   Add Libraries... `=>` JRE System Library `=>` Next > `=>` Finish
  
## Setup in Linux/Mac/Windows with Gradle(compiles our Java code from command line)
1. Install Gradle in Linux OR Mac by typing to the command line:
   ```sh
   prompt> wget http://services.gradle.org/distributions/gradle-1.10-bin.zip
   prompt> sudo unzip -q gradle-1.10-bin.zip -d /usr/local/
   prompt> echo "export GRADLE_HOME=/usr/local/gradle-1.10" >> .profile
   prompt> echo "export PATH=$PATH:$GRADLE_HOME/bin" >> .profile
   ```
   
2. Or install Gradle with these [instructions for Windows](https://db.tt/DMF3ww2D)

3. Fork this repo and clone it locally. Navigate into the `WalnutiQ/` folder

4. Type in the command line `gradle build`. This may take a minute so no worries.
   Go to the folder `build/reports/tests/`. View the file `index.html` in 
   any browser and make sure all tests pass!

## How to Contribute

1. View an example of how some of the code is used in the file
   [NoiseInvarianceExperiment.java](./experiments/model/MARK_I/vision/NoiseInvarianceExperiment.java)

2. View an example of how the current model can be instantiated as a simplified human visual pathway
   in [HowMARK_I_FitsInToBrainAnatomy.java](./experiments/model/MARK_I/vision/HowMARK_I_FitsInToBrainAnatomy.java)

3. View our [issue tracker](https://github.com/quinnliu/WalnutiQ/issues?state=open) and create a new issue
   with a question if you are confused. Otherwise, asign a issue to yourself you would like to work on or suggest
   a new issue if you kind of know what you are doing. Happy coding!

## What each file/folder in this repository is for:
  - experiments  
      + model
          - MARK_I 
            + [vision](./experiments/model/MARK_I/vision) = experiments with partial 
              visual pathway models on a popular handwritten digit data
              set called MNIST
  - gradle = helps everyone use the same version of Gradle for compiling code
  - images = images used in testing the MARK I model
  - referencedLibraries = contains .jar files(of other people's code) needed to run WalnutiQ
  - src
      + main
        - java
          + model
            - [MARK_I](./src/main/java/model/MARK_I) = the core logic for the partial brain model. 
              Includes abstract data types for basic brain structures and learning 
              algorithms that simulate how the brain learns.
              + [SDR](./src/main/java/model/MARK_I/SDR) = allows construction of different WalnutiQ
                models from command line for this repo https://github.com/quinnliu/CallWalnutiQ.
              + [connectTypes](./src/main/java/model/MARK_I/connectTypes) = classes allow the different 
                brain structures in MARK I to connect to each other in a variety of ways
              + [SpatialPooler.java](./src/main/java/model/MARK_I/SpatialPooler.java) 
            - [util](./src/main/java/model/util) = classes that enable the brain model properties
              to be viewed graphically and efficiently saved and opened  
      + test = test classes for important classes in the `src/main/java/model` folder
  - .gitignore = contains names of files/folders not to add to this repository
  - .project = when writing your code using Eclipse this file will allow all of
               your files to be organized in the correct folder
  - .travis.yml = tells [our custom travis testing site](https://travis-ci.org/quinnliu/WalnutiQ) 
                  what versions of Java to test the files here
  - BrainTheories.md = list of brain theories with support and simple explanation
  - LICENSE.txt = MIT liscense saying you can do whatever you want the code here 
                  but it would be very cool of you to do something nice with it.
  - README.md = the file you are reading right now
  - build.gradle = instructions for compiling all of the code in this repository using Gradle
  - gradlew.bat = helps everyone use the same version of Gradle for compiling code

===============================================================
Please contact me at quinnliu@vt.edu if you have any questions! 