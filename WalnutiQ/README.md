WalnutiQ 
========
  <p>
    Hi and welcome to the WalnutiQ, a brain model simulation 
    in Java! The GOAL of this repository is store the code that 
    would allow a 10 year old to understand the brain in it's entirety and allow
    a real-time human brain simulation. If you are interested in 
    understanding a machine no human fully understands please visit 
    www.walnutiq.com for more information :)
  </p>
  <p> 
    Here you will find classes that allow you to build a brain model (currently
    the second version called MARK II), train it on data, view it's activity as
    text and visual output, and test it's ability to recognize objects. All of the code 
    here supports modeling of the human brain at a high level of abstraction 
    while still allowing user access to individual neurons.
    
    Please watch the following playlist to become familiar with the neuroscience
    behind this repository at http://www.youtube.com/playlist?list=PLPXsMt57rLtgddN0NQEmXP-FbF6wt2O-f
  </p>  
  <p> 
    Please go to the folder WalnutiQ/train/model/HowToUseMARK_II.java to view an
    example of how to use all of the code.
  </p>
     
  <h5>What each file or folder is for:</h5>
  <h6>src</h6>
    <ul>
      <li>model = all neurobiology classes.</li>
        <ul>
          <li>MARK_II = classes containing the core logic for the brain model,
                        Includes data structures for basic brain structures 
                        and algorithms that simulate how the brain learns.</li>
            <ul>
              <li>ConnectTypes = classes allow the different brain 
                        components in MARK II to connect to each other in a
                        variety of ways.</li>
            </ul>
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
            <li>MARK_II = test classes for all model classes</li>
            <ul>
              <li>ConnectTypes = test classes for all connect methods</li>
            </ul>
            <li>theory = test classes for all theory classes</li>
        </ul>
    </ul>  
  <h6>train</h6>  
    <ul>
      <li>model</li>
      <ul>
        <li>MARK_II = contains a class that demonstrates how to use the
                      classes currently in the MARK II design.</li>
      </ul>
    </ul> 
  <h6>images</h6>
    <ul>
      <li>model = images to be used for training.</li>    
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