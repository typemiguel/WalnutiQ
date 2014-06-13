'''
Provides the minimal behaviors for a Cell.

@author: Quinn Liu (quinnliu@vt.edu)
@author: Michael Cogswell (cogswell@vt.edu)
@version: July 22, 2013
'''
class Cell(object):

	def __init__(self, isActive=None, wasActive=None):
		self.isActive = False # biologically equivalent to 
			# generating 1 or more spikes
		self.wasActive = False

	def
	