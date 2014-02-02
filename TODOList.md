Research/Programming TO DO List
===============================
Read First: Before working on a item below, please put your e-mail in the task so that anyone else interested 
will be able to collaborate with you. All tasks are also numbered by the amount of importance.

<h2>Researching Tasks</h2>

- Very Hard) Reunderstand neural network digit recognition system [quinnliu@vt.edu]
  + Currently doing so by building neural network digit recognition with good notes here:
    https://github.com/quinnliu/MachineLearning/tree/master/unsupervisedLearning/neuralNetworks
    - Able to get 2 layer neural network to get 95% (and 3 layer neural network to get 97%) correct on MNIST handwritten  
      digit dataset (without overfitting the data set). The principles of neural network training should also be able to be applied to training MARK I to achieve very similiar results!!!
    - Build a very large heriarchical neural network using MARK I model where each neuron does have about 10,000 synapses
      and see if it is any better.

- Hard) Read through this email and consider NEW temporal pooling ideas and structure of building vision system with CLA 
        regions in hierarchy: https://mail.google.com/mail/u/0/#inbox/143bb52fc872e539

- Hard) Decide how to implement complete vision & action pathway by drawing MARK I 
        + Detailed biological image 1: http://db.tt/T0Gs7lMe image 2: http://db.tt/IDQYLbr1 
          - Reference this neuron modeling repo: https://github.com/quinnliu/ComputationalNeuroscience
	          + circle connect from retine to LGN ? Relate back to Computational Neuroscience
            + random connect from LGN to V1 ? 
	      + input picture => active cells from bottom region to top region => motor output => next input picture! Draw 
          complete picture
        + Begin drawing detailed diagram of connections in the human brain with 2 retine
	        Retine => LGN => V1 => V2 => V4 => IT (Reference Below[1]) 

- ???) Theory of muscle control with no initial control(how does a baby control it’s bladder)
       Theory behind cascades with controlling the eye, build software eye model
       + Every region of the neocortex generates motor behavior
       + http://www.youtube.com/watch?v=1_eT5bsS4bQ
       + thoughts: muscle output goes back to the bottom of neocortex as input
       + upside tongue touching. If you flip your tongue upside down and then touch the left side your brain will process it
         is being on the right side. 
       + look into Andrew Schwartz monkey robotic arm experiment at University of Pittsburgh

- Medium) How does brian code for different shades of grey. Different greys must produce different level of cell activity.
          How do the photoreceptors do so? Red Rectangluar. Connection with No overlap, Green..., Blue... How to implement Retine.java?

<h2>Programming Tasks</h2>

- Very Hard) Implement back propagation model on MARK I model.
             + https://github.com/quinnliu/MachineLearning/tree/master/unsupervisedLearning/neuralNetworks/learningWithBackpropagation

- Medium) Create a class to show how 2 different inputs produce the same output.

- Hard) Make the image input go through the retine(simulating a difference of gaussian filter) to create a sparse input for the LGN -> V1

- ON HOLD) Temporary on HOLD since Numenta is changing their temporal pooling algorithm
           + Implement TemporalPooler class and make a release for MARK I. Requires a lot of understanding of output of
             SpatialPooler class 
           + http://www.youtube.com/watch?v=QLDlBRAlSAM
           + Throw exceptions for TemporalPooler.java class when done implementation

- ???) Learn Swarming algorithm and see if it is worth implementing for WalnutiQ 
       + https://github.com/numenta/nupic/wiki/Swarming-Algorithm

- Easy) Enforce global inhibition for performSpatialPooling(int percentOutput)

- Medium) Iterate through a directory during training instead of one image file. 
	        + http://stackoverflow.com/questions/14407040/iterating-through-a-directory-in-java

References
==========
[1] https://www.dropbox.com/s/dpdywrbf2hp6wld/AI2008.pdf
