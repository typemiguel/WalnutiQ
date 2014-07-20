Install with **[Eclipse](#install-in-linuxmacwindows-with-eclipse),**
**[IntelliJ](#install-in-linuxmacwindows-with-intellij),** or
**[Gradle](#install-in-linuxmacwindows-with-gradle) |**
**[How to contribute](#how-to-contribute) |**
**[What are all the files here for?](#what-are-all-the-files-here-for) |**
**[Important brain theories in use](#important-brain-theories-in-use)**

# [WalnutiQ](http://walnutiq.com)

"*Most doors in the world are closed, so if you find one that you want to get into, you damn well better have an 
  interesting knock.*"    ~ Sam Harper

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
learning algorithms, and view its activity.
All of the code here supports modeling of the human 
brain at a high level of abstraction while still allowing user
access to individual neuron properties. 

If you are interested in becoming a researcher/developer, the 
only requirement is interest in understanding how the brain 
really works. Please e-mail me at quinnliu@vt.edu to talk about how you can get involved!

Most importantly, this research is made possible by everyone at [Numenta](http://numenta.org/). 
Numenta has theorized and tested algorithms that model layers 3 & 4 of the human neocortex. 
They have generously released the pseudocode for their learning algorithms, 
and this repository is an extended implementation of their algorithms using object-oriented 
programming. For more information please:

- Watch this [video playlist](http://www.youtube.com/playlist?list=PLPXsMt57rLtgddN0NQEmXP-FbF6wt2O-f) to become 
  familiar with the neuroscience behind this repository.
- Read Numenta's great explanation of their research in this [white paper](https://db.tt/FuQWQuwE) 
  to better understand the theory behind this repository.

## Install in Linux/Mac/Windows with Eclipse
1. [Install Eclipse Standard 4.3.2](https://www.eclipse.org/downloads/) or use
   any other version of Eclipse if you already have it installed.

2. Go to the top right of this page and hit the `Fork` button. Then clone your 
   forked WalnutiQ repository locally. Then import it as a Git
   project into Eclipse. Right-click your package explorer `=>` Import...
   `=>` Git `=>` Projects from Git `=>` Next > `=>` Existing local repository
   `=>` Next > `=>` Add... `=>` Browse to the WalnutiQ folder you cloned locally
   and finish.

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
   `referencedLibraries/` by right-clicking your folder `WalnutiQ` 
   in the package explorer `=>` Build Path `=>` Add External Archives...

5. In Eclipse, add JUnit 4 by right-clicking your folder `WalnutiQ` 
   in the package explorer `=>` Build Path `=>` Add Libraries... `=>` JUnit `=>`
   Next > `=>` Finish

6. In Eclipse, also add JRE System Library by right-clicking 
   your folder `WalnutiQ` in the package explorer `=>` Build Path `=>` 
   Add Libraries... `=>` JRE System Library `=>` Next > `=>` Finish

7. Right click your folder `WalnutiQ` `=>` Run As `=>` JUnit Test `=>` ALL TESTS PASS!

## Install in Linux/Mac/Windows with IntelliJ
1. [Install IntelliJ IDEA 13.1 FREE Community Edition](http://www.jetbrains.com/idea/download/)
   or use any other version of IntelliJ if you already have it installed.

2. Go to the top right of this page and hit the `Fork` button. Then clone 
   your forked WalnutiQ repository locally.  

3. Open up IntelliJ and click "Import Project" `=>` Select "Gradle" `=>` Next `=>` 
   Select "Use default gradle wrapper (recommended)" `=>` Finish

4. In IntelliJ right-click the `WalnutiQ/` folder and select "Run 'All Tests'". 
  
## Install in Linux/Mac/Windows with Gradle
1. Make sure you have java version 1.7. To check open up a new terminal and type:
   ```sh
   prompt> java -version
   java version "1.7.0_60" # it's only important to see the "1.7" part
   ```  
   If you don't have java 1.7 install it by going [
   here](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html). After installing 
   java 1.7 open up a new terminal to check if java 1.7 is installed by retyping `java -version` in the terminal.
   
2. Go to the top right of this page and hit the `Fork` button. Then clone 
   your forked WalnutiQ repository locally. Navigate into the `WalnutiQ/` folder.

3. To run all of the code in the Linux or Mac terminal type:
   ```sh
   prompt> ./gradlew build
   :compileJava UP-TO-DATE
   # some other stuff...
   :build UP-TO-DATE
   BUILD SUCCESSFUL # If you see `BUILD SUCCESSFUL` all of the tests have passed! 
   ```

4. To run all of the code in the Windows terminal type:
   ```sh
   prompt> gradlew.bat
   # some other stuff...
   BUILD SUCCESSFUL # If you see `BUILD SUCCESSFUL` all of the tests have passed! 
   ```
   
5. In the future after editing some code make sure to clean your old compiled code before rerunning the tests by typing:
   ```sh
   prompt> ./gradlew clean # removes your old compiled code
   prompt> ./gradlew build
   # hopefully all the tests still pass... :)
   ```

## How to contribute
1. Here is some example code of how part of the theorized learning algorithm works:
   ```java
   public void test_NoiseInvarianceExperiment() {
        
        // View all three images of digit 2 @ https://db.tt/ElvG0WLM
        // ----------------------------------"2.bmp"------------------------------------
        this.retina.seeBMPImage("2.bmp");

        this.spatialPooler.performSpatialPoolingOnRegionWithoutInhibitionRadiusUpdate();

        assertEquals("((6, 2), (1, 3), (1, 5), (4, 4))",
                this.spatialPooler.getActiveColumnPositionsAsString());

        // ---------------------------"2_with_some_noise.bmp"---------------------------
        this.retina.seeBMPImage("2_with_some_noise.bmp");

        this.spatialPooler.performSpatialPoolingOnRegionWithoutInhibitionRadiusUpdate();

        assertEquals("((6, 2), (1, 3), (1, 5), (4, 4))",
                this.spatialPooler.getActiveColumnPositionsAsString());

        // --------------------------"2_with_alot_of_noise.bmp"-------------------------
        this.retina.seeBMPImage("2_with_alot_of_noise.bmp");

        this.spatialPooler.performSpatialPoolingOnRegionWithoutInhibitionRadiusUpdate();

        assertEquals("((6, 2), (1, 3), (2, 5))",
                this.spatialPooler.getActiveColumnPositionsAsString());
   }
   ```

   You can view all the entire file by clicking [NoiseInvarianceExperiment.java](./experiments/model/MARK_I/vision/NoiseInvarianceExperiment.java).
   Please do not be afraid to ask a question if you are confused! This stuff took me several months to fully understand
   but it is really beautiful after you understand it.

2. View our [issue tracker](https://github.com/quinnliu/WalnutiQ/issues?state=open) 
   and create a new issue with a question if you are confused. Otherwise, 
   assign a issue to yourself you would like to work on or suggest
   a new issue if you kinda know what you are doing. 

3. For now we are using the Git workflow model described 
   [here](https://github.com/quinnliu/WalnutiQ/issues/62) to contribute to this
   repository effectively. Happy coding!

## What are all the files here for
  - experiments  
      + model
          - MARK_I 
            + [vision](./experiments/model/MARK_I/vision) = experiments with partial 
              visual pathway models on a popular handwritten digit data
              set called MNIST
  - gradle = the actual Gradle code for building our Java code
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
              + **[SpatialPooler.java](./src/main/java/model/MARK_II/SpatialPooler.java) 
                = models the sparse & distributed spiking activity of neurons seen in the neocortex 
                  and models long term potentiation and depression on synapses of proximal dendrites**
              + **[TemporalPooler.java](./src/main/java/model/MARK_II/TemporalPooler.java) 
                = models neocortex's ability to predict future input using long term potentiation 
                  and depression on synapses of distal dendrites**
            - [util](./src/main/java/model/util) = classes that enable the brain model properties
              to be viewed graphically and efficiently saved and opened  
      + test = test classes for important classes in the `src/main/java/model` folder
  - .gitignore = contains names of files/folders not to add to this repository but keep in your local WalnutiQ folder
  - .project = when writing your code using Eclipse this file will allow all of
               your files to be organized in the correct folder
  - .travis.yml = tells [our custom travis testing site](https://travis-ci.org/quinnliu/WalnutiQ) 
                  what versions of Java to test the files here
  - LICENSE.txt = MIT liscense saying you can do whatever you want the code here 
                  but it would be very cool of you to do something nice with it.
  - README.md = the file you are reading right now
  - build.gradle = compiles all of the code in this repository using Gradle
  - gradlew = allows you to use Gradle to run all of the code in this repository in Linux & Mac
  - gradlew.bat = allows you to use Gradle to run all of the code in this repository in Windows

# Important brain theories in use 

1. **Theory: 1 learning/predicting algorithm in the neocortex of the brain**
   - Experiments that support this theory:
     + A 1992 experiment summary: If you cut the wires from the ear to the auditory cortex and rewire the optic nerve to the 
       auditory cortex, then the auditory cortex learns to see. Published paper describing details of experiment 
       viewable [here](http://diyhpl.us/~bryan/papers2/paperbot/Visual%20projections%20routed%20to%20the%20auditory%20pathway%20in%20ferrets:%20receptive%20fields%20of%20visual%20neurons%20in%20primary%20auditory%20cortex.pdf) 
     + A 1989 experiment summary: If you make the wires from the optic nerve connect to the somatosensory cortex then the 
       somatosensory cortex learns to see. Published paper describing details of experiment viewable 
       [here](http://www.pnas.org/content/86/1/357.full.pdf)
   - Experiments that do NOT support this theory: 
   - Conclusion: If different parts of the neocortex (contains auditory cortex, somatosensory, and others..)
     can be given new input and learn to process this new input, then we can guess there is a single 
     learning/predicting algorithm in all parts of the neocortex.

2. **Theory: Orientation selectivity is learned & muscle movement is crucial for this process**
   - Experiments that support this theory:
     + A 1970 experiment summary: Kittens where raised in either a horizontally or vertically stripped environment for 
       five hours per day for five months. The environments forced the kitten's to only look forward at vertical 
       stripes or horizontal stripes. The remaining 19 hours each day the kittens were raised in darkness.
       At five months the kittens were tested for line recognition. Those kittens raised in horizontal environments
       could not detect vertical aligned objects, and vice-versa. Published paper describing details of experiment 
       viewable [here](http://www.ncbi.nlm.nih.gov/pmc/articles/PMC1307838/pdf/jphysiol00822-0028.pdf)
   - Experiments that do NOT support this theory: 
   - Conclusion: Reality creates a representation in your brain as neuron activity which creates muscle movement(within the eyes,
     arms, other muscles) causing the next image to appear on your retina to be predictable by the temporal pooling
     algorithm in your neocortex. As you continue to see, columns in your neocortex become tuned to specific directions
     and phases. 

3. **Theory: Spatial pooling, sequence memory, & temporal pooling are deduced algorithms that are occurring**
   in the human neocortex layers 3 & 4 in a similar form giving the brain the ability to predict future input.
   - Experiments that support this theory:
     + Noise invariance experiment of vision input viewable in 
       [NoiseInvarianceExperiment.java](./experiments/model/MARK_I/vision/NoiseInvarianceExperiment.java). Paper that 
       describes details of theory behind experiment viewable [here](https://dl.dropboxusercontent.com/u/106853306/Brain/HTM_CorticalLearningAlgorithms.pdf)
     + A 2011 experiment summary: Neurons higher in the hierarchy are more stable & selective to input. Published paper 
       describing details of experiment viewable [here](http://www.ncbi.nlm.nih.gov/pmc/articles/PMC2975390/)
   - Experiments that do NOT support this theory: 
   - Conclusion: 
     + Spatial Pooling = Creates a sparse distributed representation activity of columns to efficiently represent 
       a larger input layer. Causes columns of neurons to become selectively tuned for specific input
       by strengthening & weakening the connections between cells/neurons.
     + Sequence Memory = Create even more sparse encoding by forcing specific neurons(within the set of active columns
       produced by spatial pooling) that best predicted the input to become "learning neurons". 
       This allows sensory input to be encoded within context. For example "squash" can mean the
       sport, the vegetable, and to step with force on something. How does your brain not get confused?
       Sequence memory provides an elegant solution to the brain's ability to do this.
     + Temporal Pooling = The act of strengthening & weakening the connections between cells/neurons to allow
       neurons to become more predictive of it's input.
   
4. **Theory: Information flow in the brain**
   - Path 1) synapseOnAxonOfNeuronA `=>` dendriteOfNeuronB `=>` cellBodyOfNeuronB `=>` axonOfNeuronB `=>` synapseOfNeuronC
   - Path 2) Read somewhere that dendrites can also be output devices to synapses(cellBody `=>` dendrite `=>` synapse)
   - Experiments that support this theory:
   - Experiments that do NOT support this theory: 
   
5. **Theory: A lot of what we call intelligence is developed during the first 2 years of life**
   - Reasoning: About 10^14 synapses in the brain by 2 years of age. About 10^8 seconds in 2 years. That means on average
                10^6 synapses were formed per second while you were becoming 2 years old.
   - Experiments that support this theory:
   - Experiments that do NOT support this theory: 
