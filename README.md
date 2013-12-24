WalnutiQ 
========

[![Build Status](https://travis-ci.org/quinnliu/WalnutiQ.png)](https://travis-ci.org/quinnliu/WalnutiQ)

Welcome! WalnutiQ is a human brain model simulation in Java! 
The goal of this repository is to store the code that can simulate a 
real-time human brain. If you are interested in becoming a 
researcher/developer the only requirement is passion and a readiness to fail. 
 
Here you will find classes that allow you to build a partial human brain model (currently
the 1st version called MARK I), train it on input data, view its activity as
text and visual output, and test its ability to recognize objects. All of the code 
here supports modeling of the human brain at a high level of abstraction while still 
allowing user access to individual neuron properties. If you are lost please watch the 
following playlist to become familiar with the neuroscience behind this repository at:
http://www.youtube.com/playlist?list=PLPXsMt57rLtgddN0NQEmXP-FbF6wt2O-f

<h2>Setup WalnutiQ in Eclipse</h2>
1. Fork this repo and clone it locally. Then import it as a project into Eclipse.

2. In Eclipse, add all the libraries (.jar file) in the folder ```libs/``` by right-clicking your project in 
   the package explorer => Build Path => Add External Archives...

3. In Eclipse, also add JUnit 4 & jre7 to your build path. Then right click the ```WalnutiQ/``` folder and select "Run As" => "JUnit Test". Make sure all tests pass!

4. View an example of how all the code is used in the file: ```WalnutiQ/train/model/MARK_I/HowToUseMARK_I.java```

5. View the [TODO List](./TODOList.md) and find a task you would like to work on. Happy coding!
  
<h2>Setup WalnutiQ with Gradle in Linux/Mac/Windows</h2>
1. Install Gradle in Linux OR Mac by typing to the command line:
```
unix> wget http://services.gradle.org/distributions/gradle-1.10-bin.zip
unix> sudo unzip -q gradle-1.10-bin.zip -d /usr/local/
unix> echo "export GRADLE_HOME=/usr/local/gradle-1.10" >> .profile
unix> echo "export PATH=$PATH:$GRADLE_HOME/bin" >> .profile
```

2. Or install Gradle with these [instructions for Windows](https://db.tt/DMF3ww2D)

3. Fork this repo and clone it locally

4. Navigate into the ```WalnutiQ/``` folder

5. Type in the command line ```gradle build```. This may take a minute so no worries.

6. Go to the folder ```build/reports/tests/```. View the file ```index.html``` in a browser and make sure all tests pass!

7. View an example of how all the code is used in the file: ```WalnutiQ/train/model/MARK_I/HowToUseMARK_I.java```

8. View the [TODO List](./TODOList.md) and find a task you would like to work on. Happy coding!

<h2>What each file/folder in this repository is for:</h2>
  - images
      + digits
          - MNIST = Unique files that contain training images and testing images.
      + model = Images used in testing the MARK II model
  - libs = Contains .jar files needed to run the program
  - src
      + model
          - MARK_I = Classes containing the core logic for the brain model.MARK_I.
                     Includes data structures for basic brain structures 
                     and algorithms that simulate how the brain learns.     
            + connectTypes = Classes allow the different brain components in MARK I to 
                             connect to each other in a variety of ways
          - theory = Contains a class MemoryClassifier.java for identifying what
                     category a new image is in given a nervous system that has been 
                     trained on images from this category   
          - util = Classes that enable the brain model properties to be viewable
                   graphically and efficiently saved and opened
  - tests = test classes for important classes in src folder
      + model
          - MARK_I
            + connectTypes
          - theory 
          - util 
  - train  
      + model
          - MARK_I = Demonstrations of how to build and train a partial brain model
                     on a popular handwritten digit data set
  - .gitignore = Contains names of files not to add to this repository
  - .project = When writing your code using the IDE this file will allow all of
               your files to be organized in the correct folder
  - .travis.yml = Tells travis how to test files here
  - MARKI_UML.jpg = UML diagram of all classes
  - MARKI_UML.ucls = Creates the image file "MARKI_UML.jpg"
  - README.md = The file you are reading right now!
  - TODOList.md = List of research & programming tasks to do
  - build.gradle = Builds this project using gradle
  
Most importantly, this research is made possible by the countless 
neuroscientists and engineers at [Numenta](http://numenta.org/). Numenta has 
designed a exciting new technology that accurately models the human 
neocortex. They have generously released the pseudocode for their algorithms and this 
repository is an extensioned implementation of their algorithms using object-oriented 
programming.