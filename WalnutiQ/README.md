WalnutiQ 
========
Hi and welcome to the WalnutiQ, a brain model.MARK_II simulation 
in Java! The GOAL of this repository is store the code that 
would allow a 10 year old to understand the brain in it's entirety and allow
a real-time human brain simulation. If you are interested in 
understanding a machine no human fully understands please visit 
www.walnutiq.com for more information :)
 
Here you will find classes that allow you to build a brain model.MARK_II (currently
the second version called MARK II), train it on data, view it's activity as
text and visual output, and test it's ability to recognize objects. All of the code 
here supports modeling of the human brain at a high level of abstraction 
while still allowing user access to individual neurons. Follow the following steps
to begin understanding how to use this repository:
    
1. Please watch the following playlist to become familiar with the neuroscience
behind this repository at http://www.youtube.com/playlist?list=PLPXsMt57rLtgddN0NQEmXP-FbF6wt2O-f
 
2. Please download the following JSON file saver/opener library to allow MARK II 
objects to be saved and opened: http://db.tt/1hwFbzcq
    
3. Add this library(.jar file) by right-clicking your 
project in the package explorer --> Build Path --> Add External Archives...
  
4. Pull code and go to the folder WalnutiQ/train/model.MARK_II/HowToUseMARK_II.java to view an
example of how to use all of the code.
     
  <h5>What each file or folder is for:</h5>
  - images
      + model = images to be used for training. 
  - src
      + model
          - MARK_II = classes containing the core logic for the brain model.MARK_II,
                      Includes data structures for basic brain structures 
                      and algorithms that simulate how the brain learns.        
          - ConnectTypes = classes allow the different brain components in MARK II to 
                           connect to each other in a variety of ways.
          - theory = contains a class MemoryClassifier.java(equivalent to a 
                     multiclass SVM) capable of identifying what category a 
                     new image is in given a nervous system that has been 
                     trained on images from this category.    
          - util = classes that enable the brain model properties to be viewable
               graphically and efficiently saved and opened.  
  - tests = test classes for important classes in src folder
      + model
          - MARK_II 
          - ConnectTypes 
          - theory 
          - util 
  - train  
      + model
          - MARK_II = demonstrations of how to build and train a partial brain model
                      on a popular handwritten digit data set.
  - images
      + MNIST = contains a popular handwritten digit data set
      + model = a small set of handwritten digits used in the classes in folder tests
  - .project = When writing your code using the IDE this file will allow all of
                     your files to be organized in the correct folder.
  - MARKII_UML.jpg = UML diagram of all classes
  
Most importantly, this research is made possible by the countless 
neuroscientists and engineers at Numenta(www.Numenta.com). Numenta has 
designed a exciting new technology that accurately models the human 
neocortex(the part of the brain that makes you intelligent). They have 
generously released the pseudocode for their algorithms and this 
repository is an implementation of their algorithms using object-oriented 
programming and model.MARK_II-view-controller.
