Install with **[Eclipse](#install-in-linuxmacwindows-with-eclipse),**
**[IntelliJ](#install-in-linuxmacwindows-with-intellij),** or
**[Gradle](#install-in-linuxmacwindows-with-gradle) |**
**[How to contribute](#how-to-contribute) |**
**[What are all the files here for?](#what-are-all-the-files-here-for) |**
**[Important brain theories in use](#important-brain-theories-in-use) |**
**[spatial pooling](#object-oriented-spatial-pooling-algorithm) |**
**[temporal pooling](#object-oriented-temporal-pooling-algorithm)**


# [WalnutiQ](http://walnutiq.com)
"*When we hit our lowest point, we are open to the greatest change.*"  ~ Avatar Aang

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
1. You need to be able to use Git & Github.com. If you don't know how I created a easy to follow 1.5 hour 
   playlist on how to use Git & Github 
   [here](https://www.youtube.com/watch?v=44E8o-xuxWo&list=PLPXsMt57rLtgpwFBqZq4QKxrD9Hhc_8L4). 

2. Here is some example code of how part of the theorized prediction algorithm works. You do NOT need to understand
   the following code to make meaningful contributions to this repository but it is a beautiful summary of how columns 
   of neurons in your brain are probably working to encode what you see. The following are the three images the
   retina will be looking at:
   
   ![alt text](https://db.tt/8ZAdcAbM)
   
   ```java
   retina.seeBMPImage("2.bmp");
   spatialPooler.performPooling();
   assertEquals("((6, 2), (1, 5))", 
       Formatter.format(spatialPooler.getActiveColumnPositions()));
 
   retina.seeBMPImage("2_with_some_noise.bmp");
   spatialPooler.performPooling();
   assertEquals("((6, 2), (1, 5))", 
       Formatter.format(spatialPooler.getActiveColumnPositions()));
 
   retina.seeBMPImage("2_with_a_lot_of_noise.bmp");
   spatialPooler.performPooling();
   // when there is a lot of noise notice how the active columns are 
   // no longer the same?
   assertEquals("((6, 2), (2, 5))", 
       Formatter.format(spatialPooler.getActiveColumnPositions()));
   ```

   You can view the entire file in [NoiseInvarianceExperiment.java](./experiments/model/MARK_I/vision/NoiseInvarianceExperiment.java).
   Please do not be afraid to ask a question if you are confused! This stuff took me several months to fully understand
   but it is really beautiful after you understand it.

3. View our [issue tracker](https://github.com/quinnliu/WalnutiQ/issues?state=open) 
   and create a new issue with a question if you are confused. Otherwise, 
   assign a issue to yourself you would like to work on or suggest
   a new issue if you kinda know what you are doing. 

4. For now we are using the Git workflow model described 
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
  - LICENSE.txt = GNU General Public License version 3
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

# Object oriented spatial pooling algorithm

The following section will not make sense until you have first read and tried to understand the spatial pooling
algorithm explained in detail in this [white paper](https://db.tt/FuQWQuwE).

The following is the spatial pooling algorithm pseudocode in the white paper pages 34-38:

<b>Phase 1: Overlap</b>
```
for c in columns // line 1

    overlap(c) = 0
    for s in connectedSynapses(c)
        overlap(c) = overlap(c) + input(t, s.sourceInput)

    if overlap(c) < minOverlap then
        overlap(c) = 0
    else
        overlap(c) = overlap(c) * boost(c) // line 10
```

<b>Phase 2: Inhibition</b>
```
for c in columns // line 11

    minLocalActivity = kthScore(neighbors(c), desiredLocalActivity)

    if overlap(c) > 0 and overlap(c) >= minLocalActivity then
        activeColumns(t).append(c) // line 16
```

<b>Phase 3: Learning</b>
```
for c in activeColumns(t) // line 18

    for s in potentialSynapses(c)
        if active(s) then
            s.permanence += permanenceInc
            s.permanence = min(1.0, s.permanence)
        else
            s.permanence -= permanenceDec
            s.permanece = max(0.0, s.permanence) // line 26

for c in columns // line 28

    minDutyCycle(c) = 0.01 * maxDutyCycle(neighbors(c))
    activeDutyCycle(c) = updateActiveDutyCycle(c)
    boost(c) = boostFunction(activeDutyCycle(c), minDutyCycle(c))

    overlapDutyCycle(c) = updateOverlapDutyCycle(c)
    if overlapDutyCycle(c) < minDutyCycle(c) then
        increasePermanences(c, 0.1 * connectedPerm) // line 36

inhibitionRadius = averageReceptiveFieldSize() // line 38
```

The following is the spatial pooling algorithm pseudocode in the white paper
implemented using object oriented design. Notice how the pseudocode from above is
placed immediately above the object oriented Java code that is equivalent to the
pseudocode and always begins with `///` to differentiate from regular comments.

The spatial pooling algorithm is run once my creating a `SpatialPooler` class 
object and calling the `performPooling()` method on that object.

```java
public Set<Column> performPooling() {
    /// for c in columns <== this pseudocode is from line 1 above
    Column[][] columns = this.region.getColumns();
    for (int row = 0; row < columns.length; row++) {
        for (int column = 0; column < columns[0].length; column++) {
            this.computeColumnOverlapScore(columns[row][column]); 
            // ^ let's take a look inside this method for the 
            // remaining Phase 1 pseudocode
        }
    }

    // a sparse set of Columns become active after local inhibition
    this.computeActiveColumnsOfRegion();

    // simulate learning by boosting specific Synapses
    this.regionLearnOneTimeStep();

    return this.activeColumns;
}
```

<b>Phase 1: Overlap pseudocode implemented using object oriented design</b>
```java
void computeColumnOverlapScore(Column column) {
    /// overlap(c) = 0
    int newOverlapScore = column.getProximalSegment()
        /// for s in connectedSynapses(c)
        ///     overlap(c) = overlap(c) + input(t, s.sourceInput)
        .getNumberOfActiveSynapses();

    // compute minimumOverlapScore assuming all proximalSegments are
    // connected to the same number of synapses
    Column[][] columns = this.region.getColumns();
    int regionMinimumOverlapScore = this.region.getMinimumOverlapScore();

    /// if overlap(c) < minOverlap then
    if (newOverlapScore < regionMinimumOverlapScore) {
        /// overlap(c) = 0
        newOverlapScore = 0;
    } else {
        /// overlap(c) = overlap(c) * boost(c)
        newOverlapScore = (int) (newOverlapScore * column.getBoostValue());
    }
    column.setOverlapScore(newOverlapScore);
}
```

<b>Phase 2: Inhibition pseudocode implemented using object oriented design</b>
```java
void computeActiveColumnsOfRegion() {
    Column[][] columns = this.region.getColumns();
    /// for c in columns
    for (int x = 0; x < columns.length; x++) {
        for (int y = 0; y < columns[0].length; y++) {
            columns[x][y].setActiveState(false);
            this.updateNeighborColumns(x, y);

            // necessary for calculating kthScoreOfColumns
            List<ColumnPosition> neighborColumnPositions = 
                new ArrayList<ColumnPosition>();
            neighborColumnPositions = columns[x][y].getNeighborColumns();
            List<Column> neighborColumns = new ArrayList<Column>();
            for (ColumnPosition columnPosition : neighborColumnPositions) {
                neighborColumns
                        .add(columns[columnPosition.getRow()][columnPosition
                                .getColumn()]);
            }

            /// minLocalActivity = kthScore(neighbors(c), desiredLocalActivity)
            int minimumLocalOverlapScore = this.kthScoreOfColumns(
                    neighborColumns, this.region.getDesiredLocalActivity());

            // more than (this.region.desiredLocalActivity) number of
            // columns can become active since it is applied to each
            // Column object's neighborColumns

            /// if overlap(c) > 0 and overlap(c) >= minLocalActivity then
            if (columns[x][y].getOverlapScore() > 0
                    && columns[x][y].getOverlapScore() >= minimumLocalOverlapScore) {
                /// activeColumns(t).append(c)
                columns[x][y].setActiveState(true);

                this.addActiveColumn(columns[x][y]);
                this.activeColumnPositions.add(new ColumnPosition(x, y));
            }
        }
    }
}
```

<b>Phase 3: Learning pseudocode implemented using object oriented design</b>  
The pseudocode in Phase 3 is split into 3 separate methods that describe what that
part of the algorithm is doing biologically.

```java
void regionLearnOneTimeStep() {
    this.modelLongTermPotentiationAndDepression(); // implements lines 18-26

    this.boostSynapsesBasedOnActiveAndOverlapDutyCycle(); // implements lines 28-36

    /// inhibitionRadius = averageReceptiveFieldSize()
    this.region
            .setInhibitionRadius((int) averageReceptiveFieldSizeOfRegion());
}
```

```java
void modelLongTermPotentiationAndDepression() {
    Column[][] columns = this.region.getColumns();

    if (super.getLearningState()) {
        /// for c in activeColumns(t)
        for (int x = 0; x < columns.length; x++) {
            for (int y = 0; y < columns[0].length; y++) {
                if (columns[x][y].getActiveState()) {
                    // increase and decrease of proximal segment synapses
                    // based on each Synapses's activeState
                    Set<Synapse<Cell>> synapses = columns[x][y]
                            .getProximalSegment().getSynapses();

                    /// for s in potentialSynapses(c)
                    for (Synapse<Cell> synapse : synapses) {
                        /// if active(s) then
                        if (synapse.getConnectedCell() != null
                                && synapse.getConnectedCell()
                                .getActiveState()) {
                            // model long term potentiation
                            /// s.permanence += permanenceInc
                            /// s.permanence = min(1.0, s.permanence)
                            synapse.increasePermanence();
                        } else {
                            // model long term depression
                            /// s.permanence -= permanenceDec
                            /// s.permanence = max(0.0, s.permanence)
                            synapse.decreasePermanence();
                        }
                    }
                }
            }
        }
    }
}
```

```java
void boostSynapsesBasedOnActiveAndOverlapDutyCycle() {
    Column[][] columns = this.region.getColumns();
    
     /// for c in columns
     for (int row = 0; row < columns.length; row++) {
         for (int column = 0; column < columns[0].length; column++) {
             if (columns[row][column].getActiveState()) {
                 // increase and decrease of proximal Segment Synapses based
                 // on each Synapses's activeState
                 // columns[row][column].performBoosting();
    
                 // 2 methods to help a Column's proximal Segment
                 // Synapses learn connections:
                 //
                 // 1) If activeDutyCycle(measures winning rate) is too low.
                 // The overall boost value of the Columns is increased.
                 //
                 // 2) If overlapDutyCycle(measures connected Synapses with
                 // inputs) is too low, the permanence values of the
                 // Column's Synapses are boosted.
    
                 // neighborColumns are already up to date.
                 List<ColumnPosition> neighborColumnPositions = columns[row][column]
                         .getNeighborColumns();
    
                 List<Column> neighborColumns = new ArrayList<Column>();
                 for (ColumnPosition columnPosition : neighborColumnPositions) {
                     // add the Column object to neighborColumns
                     neighborColumns
                             .add(columns[columnPosition.getRow()][columnPosition
                                     .getColumn()]);
                 }
    
                 float maximumActiveDutyCycle = this.region
                         .maximumActiveDutyCycle(neighborColumns);
                 if (maximumActiveDutyCycle == 0) {
                     maximumActiveDutyCycle = 0.1f;
                 }
    
                 // neighborColumns are no longer necessary for calculations
                 // in this time step
                 columns[row][column].clearNeighborColumns();
    
                 // minDutyCycle represents the minimum desired firing rate
                 // for a Column(number of times it becomes active over some
                 // number of iterations).
                 // If a Column's firing rate falls below this value, it will
                 // be boosted.
                 /// minDutyCycle(c) = 0.01 * maxDutyCycle(neighbors(c))
                 float minimumActiveDutyCycle = this.MINIMUM_COLUMN_FIRING_RATE
                         * maximumActiveDutyCycle;
    
                 // 1) boost if activeDutyCycle is too low
                 /// activeDutyCycle(c) = updateActiveDutyCycle(c)
                 columns[row][column].updateActiveDutyCycle();
    
                 /// boost(c) = boostFunction(activeDutyCycle(c), minDutyCycle(c))
                 columns[row][column].setBoostValue(columns[row][column]
                         .boostFunction(minimumActiveDutyCycle));
    
                 // 2) boost if overlapDutyCycle is too low
                 /// overlapDutyCycle(c) = updateOverlapDutyCycle(c)
                 this.updateOverlapDutyCycle(row, column);
    
                 /// if overlapDutyCycle(c) < minDutyCycle(c) then
                 if (columns[row][column].getOverlapDutyCycle() < minimumActiveDutyCycle
                         && this.getLearningState()) {
                     /// increasePermanences(c, 0.1*connectedPerm)
                     columns[row][column]
                             .increaseProximalSegmentSynapsePermanences(1);
                 }
             }
         }
     }
}
```

The actual [SpatialPooler.java](./src/main/java/model/MARK_II/SpatialPooler.java) class contains 
the above code and additional code and clarifying comments.

# Object oriented temporal pooling algorithm

The following section will not make sense until you have first read and tried to understand the temporal pooling
algorithm explained in detail in this [white paper](https://db.tt/FuQWQuwE).

The following is the temporal pooling algorithm(combined inference and learning) pseudocode in the 
white paper pages 39-46:

<b>Phase 1</b>
```
for c in activeColumns(t) // line 18
 
    buPredicted = false 
    lcChosen = false
    for i = 0 to cellsPerColumn - 1 
        if predictiveState(c, i, t-1) == true then 
            s = getActiveSegment(c, i, t-1, activeState) 
            if s.sequenceSegment == true then 
                buPredicted = true 
                activeState(c, i, t) = 1 
                if segmentActive(s, t-1, learnState) then 
                    lcChosen = true 
                    learnState(c, i, t) = 1 

 
    if buPredicted == false then 
        for i = 0 to cellsPerColumn - 1 
            activeState(c, i, t) = 1 
    
    if lcChosen == false then 
        i,s = getBestMatchingCell(c, t-1) 
        learnState(c, i, t) = 1 
        sUpdate = getSegmentActiveSynapses (c, i, s, t-1, true) 
        sUpdate.sequenceSegment = true 
        segmentUpdateList.add(sUpdate) // line 41
```

<b>Phase 2</b>
```
for c, i in cells // line 42
    for s in segments(c, i) 
        if segmentActive(s, t, activeState) then 
            predictiveState(c, i, t) = 1 
 
            activeUpdate = getSegmentActiveSynapses (c, i, s, t, false) 
            segmentUpdateList.add(activeUpdate) 
 
            predSegment = getBestMatchingSegment(c, i, t-1) 
            predUpdate = getSegmentActiveSynapses( 
                              c, i, predSegment, t-1, true) 
            segmentUpdateList.add(predUpdate) // line 53
```

<b>Phase 3</b>
```
for c, i in cells // line 54
    if learnState(s, i, t) == 1 then 
        adaptSegments (segmentUpdateList(c, i), true) 
        segmentUpdateList(c, i).delete() 
    else if predictiveState(c, i, t) == 0 and predictiveState(c, i, t-1)==1 then 
        adaptSegments (segmentUpdateList(c,i), false) 
        segmentUpdateList(c, i).delete()  // line 60
```

The following is the temporal pooling algorithm pseudocode in the white paper
implemented using object oriented design. Notice how the pseudocode from above is
placed immediately above the object oriented Java code that is equivalent to the
pseudocode and always begins with `///` to differentiate from regular comments.

The temporal pooling algorithm is run once my creating a `TemporalPooler` class 
object and calling the `performPooling()` method on that object.

```java
public void performPooling() {
    Set<Column> activeColumns = this.spatialPooler.getActiveColumns();
    if (super.getLearningState()) {
        this.phaseOne(activeColumns);
        this.phaseTwo(activeColumns);
        this.phaseThree(activeColumns);
    } else {
        this.computeActiveStateOfAllNeuronsInActiveColumn(activeColumns);
        this.computePredictiveStateOfAllNeurons(activeColumns);
    }
}
```

<b>Phase 1: pseudocode implemented using object oriented design</b>

```java
void phaseOne(Set<Column> activeColumns) {
    /// for c in activeColumns(t)
    for (Column column : activeColumns) {
        /// buPredicted = false
        boolean bottomUpPredicted = false;
        /// lcChosen = false
        boolean learningCellChosen = false;

        Neuron[] neurons = column.getNeurons();
        /// for i = 0 to cellsPerColumn - 1
        for (int i = 0; i < neurons.length; i++) {
            /// predictiveState(c, i, t-1) == true then
            if (neurons[i].getPreviousActiveState() == true) {
                /// s = getActiveSegment(c, i, t-1, activeState)
                DistalSegment bestSegment = neurons[i]
                        .getBestPreviousActiveSegment();

                /// if s.sequenceSegment == true then
                if (bestSegment != null
                        && bestSegment
                        .getSequenceStatePredictsFeedFowardInputOnNextStep()) {
                    /// buPredicted = true
                    bottomUpPredicted = true;
                    /// activeState(c, i, t) = 1
                    neurons[i].setActiveState(true);

                    /// if segmentActive(s, t-1, learnState) then
                    if (bestSegment.getPreviousActiveState()) {
                        /// lcChosen = true
                        learningCellChosen = true;
                        /// learnState(c, i, t) = 1
                        column.setLearningNeuronPosition(i);
                        this.currentLearningNeurons.add(neurons[i]);
                    }
                }
            }
        }
        /// if buPredicted == false then
        if (bottomUpPredicted == false) {
            /// for i = 0 to cellsPerColumn - 1
            for (Neuron neuron : column.getNeurons()) {
                /// activeState(c, i, t) = 1
                neuron.setActiveState(true);
            }
        }

        /// if lcChosen == false then
        if (learningCellChosen == false) {
            /// l,s = getBestMatchingCell(c, t-1)
            int bestNeuronIndex = this.getBestMatchingNeuronIndex(column);
            /// learnState(c, i, t) = 1
            column.setLearningNeuronPosition(bestNeuronIndex);
            this.currentLearningNeurons.add(column
                    .getNeuron(bestNeuronIndex));

            DistalSegment segment = neurons[bestNeuronIndex]
                    .getBestPreviousActiveSegment();
            /// sUpdate = getSegmentActiveSynapses(c, i, s, t-1, true)
            SegmentUpdate segmentUpdate = this.getSegmentActiveSynapses(
                    column.getCurrentPosition(), bestNeuronIndex, segment,
                    true, true);
            /// sUpdate.sequenceSegment = true
            segmentUpdate.setSequenceState(true);
            segment.setSequenceState(true);

            /// segmentUpdateList.add(sUpdate)
            this.segmentUpdateList.add(segmentUpdate);
        }
    }
}
```

<b>Phase 2: pseudocode implemented using object oriented design</b>

```java
void phaseTwo(Set<Column> activeColumns) {
    /// for c, i in cells
    for (Column column : activeColumns) {
        Neuron[] neurons = column.getNeurons();
        for (int i = 0; i < neurons.length; i++) {
            // we must compute the best segment here because
            // if we compute it where it is commented out below
            // then we would be iterating over the neuron's list
            // of segments again
            Segment predictingSegment = neurons[i]
                    .getBestPreviousActiveSegment();

            /// for s in segments(c, i)
            for (Segment segment : neurons[i].getDistalSegments()) {
                // NOTE: segment may become active during the spatial pooling
                // between temporal pooling iterations
                /// if segmentActive(s, t, activeState) then
                if (segment.getActiveState()) {
                    /// predictiveState(c, i, t) = 1
                    neurons[i].setPredictingState(true);

                    /// activeUpdate = getSegmentActiveSynapses(c, i, s, t, false)
                    SegmentUpdate activeUpdate = this
                            .getSegmentActiveSynapses(
                                    column.getCurrentPosition(), i,
                                    segment, false, false);
                    /// segmentUpdateList.add(activeUpdate)
                    this.segmentUpdateList.add(activeUpdate);
                    // Segment predictingSegment = neurons[i]
                    // .getBestPreviousActiveSegment();

                    /// predSegment = getBestMatchingSegment(c, i, t-1)
                    /// predUpdate = getSegmentActiveSynapses(c, i, predSegment, t-1, true)
                    SegmentUpdate predictionUpdate = this
                            .getSegmentActiveSynapses(
                                    column.getCurrentPosition(), i,
                                    predictingSegment, true, true);
                    /// segmentUpdateList.add(predUpdate)
                    this.segmentUpdateList.add(predictionUpdate);
                }
            }
        }
    }
}
```

<b>Phase 3: pseudocode implemented using object oriented design</b>

```java
void phaseThree(Set<Column> activeColumns) {
    /// for c, i in cells
    for (Column column : activeColumns) {
        ColumnPosition c = column.getCurrentPosition();
        Neuron[] neurons = column.getNeurons();
        for (int i = 0; i < neurons.length; i++) {
            /// if learnState(s, i, t) == 1 then
            if (i == column.getLearningNeuronPosition()) {
                /// adaptSegments(segmentUpdateList(c, i), true)
                this.adaptSegments(
                        this.segmentUpdateList.getSegmentUpdate(c, i), true);
                /// segmentUpdateList(c, i).delete()
                this.segmentUpdateList.deleteSegmentUpdate(c, i);
            /// else if predictiveState(c, i, t) == 0 and predictiveState(c, i, t-1)==1 then
            } else if (neurons[i].getPredictingState() == false
                    && neurons[i].getPreviousPredictingState() == true) {
                /// adaptSegments(segmentUpdateList(c, i), false)
                this.adaptSegments(
                        this.segmentUpdateList.getSegmentUpdate(c, i),
                        false);
                /// segmentUpdateList(c, i).delete()
                this.segmentUpdateList.deleteSegmentUpdate(c, i);
            }
        }
    }
}
```

The actual [TemporalPooler.java](./src/main/java/model/MARK_II/TemporalPooler.java) class contains 
the above code and additional code and clarifying comments.
