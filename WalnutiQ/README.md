WalnutiQ &copy;
========
  <p>
    Hi and welcome to the WalnutiQ object-oriented brain model simulation 
    repository in Java! The GOAL of this repository is store the code that 
    would allow a 10 year old to understand the brain in it's entirety and allow
    a real-time full human brain simulation. If you think about it, it’s kind 
    of funny that we can identify galaxies billions of light years away, 
    understand particles at the atomic scale, but still do not understand the 3 
    pounds of matter that is between our ears. If you are interested in 
    understanding a machine no human fully understands please visit 
    www.walnutiq.com for more information :)
  </p>
  <p> 
    Here you will find classes that allow you to build a brain model (currently
    the second version called MARK II), train it on data, view it's activity as
    text output, and test it's ability to recognize objects. All of the code 
    here supports modeling of the human brain at a high level of abstraction 
    while still allowing user access to individual neurons. 
  </p>  
  <p>
    Two key classes for this project are SpatialPooler(WalnutiQ/src/
    model/MARK_II/SpatialPooler.java) and TemporalPooler(WalnutiQ/src/
    model/MARK_II/TemporalPooler.java). The SpatialPooler class contains an 
    algorithm that allows your brain model(an object of NervousSystem.java class) 
    to learn what a objects look like by training it on images. On the other hand,
    the TemporalPooler class is still in the process of being implemented 
    and tested. When completed, it's algorithm will allow your brain model
    to predict future data after being trained on a sequence of data. Note, to 
    use this code library, only 1 object of NervousSystem.java(WalnutiQ/src/
    model/NervousSystem.java) will need to be created. 
   </p>
   <p> 
    How to use all of the code: Youtube videos to be made shorty... 
  </p>
     
  <h5>What each file or folder is for:</h5>
  <h6>src</h6>
    <ul>
        <li>model = all neurobiology classes.</li>
        <ul>
            <li>MARK_II = classes containing the core logic for the brain model,
                          Includes data structures for basic brain structures 
                          and algorithms that simulate how the brain learns.</li>
            <li>theory = contains a class MemoryClassifier.java(equivalent to a 
                         multiclass SVM) capable of identifying what category a 
                         new image is in given a nervous system that has been 
                         trained on images from this category.</li>
        </ul>
    </ul>         
  <h6>tests</h6>
    <ul>
        <li>model</li>
        <ul>
            <li>MARK_II = stubs and test classes for all MARK_II classes</li>
            <li>theory = test classes for all theory classes</li>
        </ul>
    </ul>  
  <p><b>.project</b> = When writing your code use the IDE This file will allow all of
                 your files to be organized in the correct folder.</p>
  <p><b>MARKII_UML.jpg</b> = UML diagram of all classes</p>
  <p> 
    Most importantly, this research is made possible by the countless 
    neuroscientists and engineers at Numenta(www.Numenta.com). Numenta has 
    designed a exciting new technology that accurately models the human 
    neocortex(the part of the brain that makes you intelligent). They have 
    generously released the pseudocode for their algorithms and this 
    repository is an implementation of their algorithms using object-oriented 
    programming and model-view-controller.
 </p>