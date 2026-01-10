# Neural Network Training Visualizer

This is the Java source code for a 2024 solo class project.
The motivation was to learn a bit more about how neural networks worked under the hood
and be able to visualize the learning process easily.

To this end, I programmed a basic feed-forward style network, without any fancy optimizers like ADAM.
I used JavaFX to create a simple 3D UI where one can view the training process and modify the network parameters.
My hope is that this helps other people better intuitively understand how different parameters 
effect the efficacy of network training.

Future improvements could include adding different types of layer options (this only includes dense layers),
like dropout or normalization, and adding different optimizers as a modifiable UI field 
(this only includes loss and activation functions).

To download the Jar execuatable, visit [olivergreer.com](https://olivergreer.com/).