Research/Programming TO DO List
===============================
Read First: 

1. Before working on a item below, please put your e-mail in the task so that anyone else interested 
will be able to collaborate with you.

2. Please only change the dates of files within the WalnutiQ repository only after major logical edits.

<h2>Researching Tasks</h2>
  - ???) Reunderstand neural network digit recognition system [quinnliu@vt.edu]

  - Hard) Decide how to implement optical pathway.
	  Detailed biological image 1: http://db.tt/T0Gs7lMe image 2: http://db.tt/IDQYLbr1 
	  + circle connect from retine to LGN    
    + random connect from LGN to V1
	  + input picture => active cells from bottom region to top region => motor output => next 
	  input picture! Draw complete picture

  - Easy) Draw MARK I with 2 regions layer 3 of lower region connecting to layer 3 of upper region

  - Hard) Begin drawing detailed diagram of connections in the human brain with 2 retine
	  Retina=>LGN =>V1 =>V2 =>V4=>IT (Reference [1]) 

  - Hard) Encorder transducer for retine. Find ted talk about it

  - Very Hard) http://math.stackexchange.com/questions/442497/combinatorics-arrangement-problem
    + partial answer from MIT combinatorics professor: http://db.tt/tnwPBW65
    + write a back tracking algorithm to calculate answer 

  - Medium) Research Mammal Audio System and think about AudioCell implementation
    Human auditory range: 64 Hertz to 15,000 Hertz (middle aged adult) research how a cochlear implant really
    works, making a sound wave in the environment into an electrical signal

  - Hard) Write up a easy/intuitive explanation of how the illusion of the Hermann grid is created
    using WalnutiQ MARK I model:
    + https://www.dropbox.com/s/03a9sujz36xj5vw/2013-06-17%2010.40.44.jpg
    + http://www.nku.edu/~issues/illusions/HermannGrid.htm

  - Medium) How does brian code for different shades of grey. Different greys must produce 
    different level of cell activity. How do the photoreceptors do so? Red Rectangluar
    Connection with No overlap, Green..., Blue... How to implement Retine.java?

  - ???) Theory of muscle control with no initial control(how does a baby control it’s bladder)
    Theory behind cascades with controlling the eye, build software eye model
	  + Every region of the neocortex generates motor behavior
	  + http://www.youtube.com/watch?v=1_eT5bsS4bQ
	  + thoughts: muscle output goes back to the bottom of neocortex as input

  - Very Hard) Write up biologically accurate explanation of ebbinghaus illusion, ponzo illusion,
	  Interesting facts: moon illusion disappears when you inverse input
	  Possible explanation: convergence micropasia

<h2>Programming Tasks</h2>
  - Medium) Running Java in ubuntu remote server Amazon ec2 t1.micro instance using gradle [quinnliu@vt.edu]
    + http://stackoverflow.com/questions/20795666/gradle-build-failing-on-amazon-ec2-t1-micro-instance-on-ubuntu-12-10 
    + http://stackoverflow.com/questions/20811785/updating-amazon-ec2-t1-micro-instance-jvm-and-gradle 

  - Very Hard) Implement TemporalPooler class and make a release for MARK I. Use 50% done implementation from MARK 0.
    (Requires a lot of understanding of output of SpatialPooler class) [quinnliu@vt.edu]
    http://www.youtube.com/watch?v=QLDlBRAlSAM
    
  - ???) Consider following ideas about interfaces: http://codereview.stackexchange.com/questions/38060/review-of-java-interface-for-constructing-brain-model/38065?noredirect=1#38065

  - ???) Learn Swarming algorithm and see if it is worth implementing for WalnutiQ 
  https://github.com/numenta/nupic/wiki/Swarming-Algorithm

  - Medium) inhibitionRadius of active Columns is not working correctly
	  + see printout of computeActiveColumns
	  + Remove all System.out.println used in Test_SpatialPooler and change to assertEquals(...)

  - Easy) Enforce global inhibition for performSpatialPooling(int percentOutput)
	
  - Medium) Iterate through a directory during training instead of one image file. 
	  + http://stackoverflow.com/questions/14407040/iterating-through-a-directory-in-java

  - Very Hard) Train MARK I on MNIST handwritten digit dataset 

  - Easy) Exceptions for Temporal class when done implementation

<h3>Computer Vision Research Comparison Chart</h3>

NOTE: All comments on below Image Recognition Techniques should be made to be objective as possible

Bag of Words/Features
  - Because only a frequency of a unique set of pixels are stored there is no spatial information being stored.
  - Does not work for images with more than one object.
  - Not capable of being unsupervised or able to learn by itself.

Support Vector Machine
  - Not visualizable after 3 features are learned(all useful support vector machines will have more than 3 features)

WalnutiQ 
  - Based on human neocortex biology theory
  - Implemented with global and local inhibition to specifically for vision research
  - Hard to find the parameters used during training to create a good image classifier
  - Only has spatial pooling implemented and not yet TemporalPooling

NuPIC
  - Based on human neocortex biology theory
  - Implemented source code is hard to understand
  
Deep Learning Neural Networks

References
==========
[1] https://www.dropbox.com/s/dpdywrbf2hp6wld/AI2008.pdf
