# WalnutiQ in Javascript

Helpful links used to understand the best way to implement
WalnutiQ as an object oriented Javascript program.

## Setup in Linux/Mac/Windows with Node.js

1. Go to http://nodejs.org/ and install Node.js

2. After you are done you should be able to check your
version and get something like this:

```sh
prompt> node -v
v0.10.22 # it's okay if your number if bigger than mine :)
```

## How to Contribute

### The order to implement classes is
[x] Cell
[ ] SensorCell
[ ] VisionCell
[ ] AudioCell
[ ] Synapse
[ ] Segment
[ ] ProximalSegment
[ ] DistalSegment
[ ] Neuron
[ ] ColumnPosition
[ ] Column
[ ] Region
[ ] Pooler
[ ] SpatialPooler
[ ] connectTypes/ # this is a folder
[ ] SegmentUpdate
[ ] SegmentUpdateList
[ ] TemporalPooler
[ ] Neocortex

## What each file/folder in this repository is for:

- MARK_II = The core logic for the partial brain model. 
            Includes abstract data types for basic brain 
            structures and learning algorithms that simulate how the brain learns.
- .gitignore = contains names of files/folders not to add to this
               repository but keep in your local folder
- README.md = the file you are reading right now
- package.json = define which npm modules are needed for the code 
                 in this repository to run
