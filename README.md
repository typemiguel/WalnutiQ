**[Eclipse Install](#setup-in-linuxmacwindows-with-eclipse)** |
**[Gradle Install](#setup-in-linuxmacwindows-with-gradle)** |
**[How to Contribute](#how-to-contribute)** |
**[What are all the files here for?](#what-each-filefolder-in-this-repository-is-for)**

# [WalnutiQ](http://walnutiq.com)

"*Time is all you have. And you may find one day that you have less than you think.*"  
~ Randy Pausch

[![Build Status](https://travis-ci.org/WalnutiQ/WalnutiQ.png)](https://travis-ci.org/WalnutiQ/WalnutiQ)

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

## Setup in Linux/Mac/Windows with Eclipse
1. [Install Eclipse Standard 4.3.2](https://www.eclipse.org/downloads/) or use
   any other version of Eclipse if you already have it installed.

2. Fork this repository and clone it locally. Then import it as a 
   project into Eclipse.

3. IMPORTANT: You will notice that your folders will have red X's everywhere. 
   To fix this right click your `src` folder then hover over "New", 
   then click "Source Folder". Then give it the "Folder name:" `src/main/java`. 
   Right click your `src` folder again and hover over "New", then click
   "Source Folder". Then give it the "Folder name:" `src/test/java`
   Similarily for the folder `experiments` right click the folder and go to
   New `=>` Source Folder `=>` Folder name: `experiments`. 
   Finally for the folder `images` right click the folder and go to
   New `=>` Source Folder `=>` Folder name: `images`.

4. In Eclipse, add all the libraries (.jar file) in the folder 
   `referencedLibraries/` by right-clicking your project `WalnutiQ` 
   in the package explorer `=>` Build Path `=>` Add External Archives...

5. In Eclipse, add JUnit 4 by right-clicking your project `WalnutiQ` 
   in the package explorer `=>` Add Libraries... `=>` JUnit `=>`
   Next > `=>` Finish

6. In Eclipse, also add JRE System Library by right-clicking 
   your project `WalnutiQ` in the package explorer `=>` 
   Add Libraries... `=>` JRE System Library `=>` Next > `=>` Finish

7. Right click your project `=>` Run As `=>` JUnit Test `=>` ALL TESTS PASS!
  
## Setup in Linux/Mac/Windows with Gradle
1. Install Gradle in Linux OR Mac by typing to the command line:
   ```sh
   prompt> wget http://services.gradle.org/distributions/gradle-1.10-bin.zip
   prompt> sudo unzip -q gradle-1.10-bin.zip -d /usr/local/
   prompt> echo "export GRADLE_HOME=/usr/local/gradle-1.10" >> .profile
   prompt> echo "export PATH=$PATH:$GRADLE_HOME/bin" >> .profile
   ```
   
2. Or install Gradle with these [instructions for Windows](https://db.tt/DMF3ww2D)

3. Fork this repo and clone it locally. Navigate into the `WalnutiQ/` folder

4. Type in the command line `gradle build`. This may take a minute the first
   time so no worries. Go to the folder `build/reports/tests/`. 
   View the file `index.html` in any browser and make sure all tests pass!

## How to Contribute
1. View an example of how some of the code is used in the file
   [NoiseInvarianceExperiment.java](./experiments/model/MARK_I/vision/NoiseInvarianceExperiment.java).
   Please do not be afriad to ask a question if you are confused!

2. View an example of how the current model can be instantiated as a simplified human visual pathway
   in [HowMARK_I_FitsInToBrainAnatomy.java](./experiments/model/MARK_I/vision/HowMARK_I_FitsInToBrainAnatomy.java)

3. View our [issue tracker](https://github.com/quinnliu/WalnutiQ/issues?state=open) and create a new issue
   with a question if you are confused. Otherwise, asign a issue to yourself you would like to work on or suggest
   a new issue if you kinda know what you are doing. 

4. For now we are using the git workflow model described 
   [here](https://github.com/quinnliu/WalnutiQ/issues/62). Happy coding!

## What each file/folder in this repository is for:
  - experiments  
      + model
          - MARK_I 
            + [vision](./experiments/model/MARK_I/vision) = experiments with partial 
              visual pathway models on a popular handwritten digit data
              set called MNIST
  - images = images used in training & testing the partial brain model
  - referencedLibraries = contains .jar files(of other people's code) needed to run WalnutiQ
  - src
      + main
        - java
          + model
            - [MARK_II](./src/main/java/model/MARK_II) = the core logic for the partial brain model. 
              Includes abstract data types for basic brain structures and learning 
              algorithms that simulate how the brain learns.
              + [connectTypes](./src/main/java/model/MARK_II/connectTypes) = allow the different 
                brain structures to connect to each other in a variety of ways
              + [parameters](./src/main/java/model/MARK_II/parameters) = allows construction of different WalnutiQ
                models from command line for this repo https://github.com/quinnliu/CallWalnutiQ
              + [SpatialPooler.java](./src/main/java/model/MARK_II/SpatialPooler.java) 
                = models the sparse & distributed spiking activity of neurons seen in the neocortex 
                  and models long term potentiation and depression on synapses of proximal dendrites
              + [TemporalPooler.java](./src/main/java/model/MARK_II/TemporalPooler.java) 
                = models neocortex's ability to predict future input using long term potentiation 
                and depression on synapses of distal dendrites

            - [util](./src/main/java/model/util) = classes that enable the brain model properties
              to be viewed graphically and efficiently saved and opened  
      + test = test classes for important classes in the `src/main/java/model` folder
  - .gitignore = contains names of files/folders not to add to this repository but keep in your local WalnutiQ folder
  - .project = when writing your code using Eclipse this file will allow all of
               your files to be organized in the correct folder
  - .travis.yml = tells [our custom travis testing site](https://travis-ci.org/quinnliu/WalnutiQ) 
                  what versions of Java to test the files here
  - BrainTheories.md = list of important brain theories with support and simple explanation
  - LICENSE.txt = MIT liscense saying you can do whatever you want the code here 
                  but it would be very cool of you to do something nice with it.
  - README.md = the file you are reading right now
  - build.gradle = compiles all of the code in this repository using Gradle

===============================================================
Please contact me at quinnliu@vt.edu if you have any questions! 