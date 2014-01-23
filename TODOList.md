Research/Programming TO DO List
===============================
Read First: Before working on a item below, please put your e-mail in the task so that anyone else interested 
will be able to collaborate with you. All tasks are also numbered by the amount of importance.

<h2>Researching Tasks</h2>  
  - 1. Very Hard) Reunderstand neural network digit recognition system [quinnliu@vt.edu]
       + Currently doing so by building neural network digit recognition with good notes here:
         https://github.com/quinnliu/MachineLearning/tree/master/unsupervisedLearning/neuralNetworks
           - Able to get 2 layer neural network to get 95% (and 3 layer neural network to get 97%) correct on MNIST handwritten  
             digit dataset (without overfitting the data set). The principles of neural network training should also be able to be applied to training MARK I to achieve very similiar results!!!
           - Build a very large heriarchical neural network using MARK I model where each neuron does have about 10,000 synapses
             and see if it is any better.

  - 2. Hard) Read through this email and consider NEW temporal pooling ideas and structure of building vision system with CLA 
             regions in hierarchy: https://mail.google.com/mail/u/0/#inbox/143bb52fc872e539

  - 3. Hard) Decide how to implement complete vision & action pathway by drawing MARK I 
             + Detailed biological image 1: http://db.tt/T0Gs7lMe image 2: http://db.tt/IDQYLbr1 
               - Reference this neuron modeling repo: https://github.com/quinnliu/ComputationalNeuroscience
	               + circle connect from retine to LGN ? Relate back to Computational Neuroscience
                 + random connect from LGN to V1 ? 
	           + input picture => active cells from bottom region to top region => motor output => next input picture! Draw 
               complete picture
             + Begin drawing detailed diagram of connections in the human brain with 2 retine
	             Retine => LGN => V1 => V2 => V4 => IT (Reference [1]) 

  - 4. ???) Theory of muscle control with no initial control(how does a baby control it’s bladder)
            Theory behind cascades with controlling the eye, build software eye model
            + Every region of the neocortex generates motor behavior
            + http://www.youtube.com/watch?v=1_eT5bsS4bQ
            + thoughts: muscle output goes back to the bottom of neocortex as input
            + upside tongue touching. If you flip your tongue upside down and then touch the left side your brain will process it
              is being on the right side. 
            + look into Andrew Schwartz monkey robotic arm experiment at University of Pittsburgh

  - 5. Hard) Encorder transducer for retine? Find ted talk about it

  - 6. Very Hard) http://math.stackexchange.com/questions/442497/combinatorics-arrangement-problem
                  + partial answer from MIT combinatorics professor: http://db.tt/tnwPBW65
                  + code a back tracking algorithm to calculate answer 

  - 7. Medium) Research Mammal Audio System and think about AudioCell implementation
               Human auditory range: 64 Hertz to 15,000 Hertz (middle aged adult) research how a cochlear implant really
               works, making a sound wave in the environment into an electrical signal

  - 8. Hard) Write up a easy/intuitive explanation of how the illusion of the Hermann grid is created
             using WalnutiQ MARK I model:
             + https://www.dropbox.com/s/03a9sujz36xj5vw/2013-06-17%2010.40.44.jpg
             + http://www.nku.edu/~issues/illusions/HermannGrid.htm

  - 9. Medium) How does brian code for different shades of grey. Different greys must produce different level of cell activity.
               How do the photoreceptors do so? Red Rectangluar. Connection with No overlap, Green..., Blue... How to implement Retine.java?

  - 10. Very Hard) Write up biologically accurate explanation of ebbinghaus illusion, ponzo illusion,
                   Interesting facts: moon illusion disappears when you inverse input. Possible explanation: convergence micropasia

<h2>Programming Tasks</h2>
  - 1. Medium) Get WalnutiQ building successfully on free remote Cloud 9 server. Getting this working is very important
       + http://c9.io/quinnliu/walnutiq

  - 2. Medium) Running Java in ubuntu remote server Amazon ec2 t1.micro instance using gradle [quinnliu@vt.edu]
               + http://stackoverflow.com/questions/21226096/how-to-install-gradle-into-a-cloud-9-ide/21227171?noredirect=1#21227171

  - 3. Very Hard) Implement TemporalPooler class and make a release for MARK I. Requires a lot of understanding of output of
                  SpatialPooler class [quinnliu@vt.edu]
                  + http://www.youtube.com/watch?v=QLDlBRAlSAM

  - 4. Medium) initialization of permanenceValue for all Synapses should be randomized around CONNECTED_PERMANENCE_VALUE to
               allow complex features to be learned by Neurons.

  - 5. Medium) inhibitionRadius of active Columns is not working correctly [quinnliu@vt.edu]
               + see printout of computeActiveColumns
               + Remove all System.out.println used in Test_SpatialPooler and change to assertEquals(...)

  - 6. ???) Learn Swarming algorithm and see if it is worth implementing for WalnutiQ 
            + https://github.com/numenta/nupic/wiki/Swarming-Algorithm

  - 7. Easy) Enforce global inhibition for performSpatialPooling(int percentOutput)
	
  - 8. Medium) Iterate through a directory during training instead of one image file. 
	             + http://stackoverflow.com/questions/14407040/iterating-through-a-directory-in-java

  - 9. Easy) Throw exceptions for TemporalPooler.java class when done implementation

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