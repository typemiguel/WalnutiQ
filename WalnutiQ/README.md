WalnutiQ &copy;
========
  <p>
    Hi and welcome to my brain simulation research repository in Java! 
    This repository contains classes that will allow you to build your own
    brain model.MARK_II, train it on data, view it's activity on the console, and
    test it's ability to recognize objects.
  </p>
  <p>
    The two main model.MARK_II classes for this project are SpatialPooler and 
    TemporalPooler. The SpatialPooler class contains an algorithm that allows
    your brain model.MARK_II(object of Region class) to learn what a particular object
    is. The TemporalPooler class is still in the process of being implemented 
    and tested. When completed, it's algorithm will allow your brain model.MARK_II
    to predict future data after being trained on a sequence of data.
   </p>
   <p> 
    Most importantly, my research is made possible by the countless neuroscientists
    and engineers at Numenta(www.Numenta.com). Numenta has designed a exciting 
    new technology that accurately models the human neocortex(the part of the 
    brain that makes you intelligent). They have generously released the 
    pseudocode for their algorithms and this repository is my implementation of
    their algorithms.
  </p>
  
  <h5>File tree:</h5>
  <h6>src</h6>
    <ul>
        <li>model : all classes showing how MARK II fits into neurobiology</li>
        <ul>
            <li>MARK_II : classes containing the core logic for the
                          neocortex simulating learning algorithms</li>
            <li>theory : contains a MemoryClassifier(equivalent to a multiclass
                         SVM) capable of identifying what category a new image
                         is in given a neocortex that has been trained on
                         images from this category.</li>
        </ul>
    </ul>         
  <h6>tests</h6>
    <ul>
        <li>model</li>
        <ul>
            <li>MARK_II : stubs and test classes for all MARK_II classes</li>
            <li>theory : test classes for all theory classes</li>
        </ul>
    </ul>  
      
  <p>
    If you are interested in working on this challenging and exciting project 
    please contact quinnliu@vt.edu and visit the "Brain Simulation" tab at 
    www.walnutiq.com for more information. Have a nice day!
  </p>