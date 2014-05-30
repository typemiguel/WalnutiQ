function Cell(isActive, wasActive) {
    this.isActive = isActive || null;
    this.wasActive = wasActive || null;
}

RiddleInfo.prototype.getActiveState = function() {
    return this.isActive;
}

RiddleInfo.prototype.setActiveState = function(activeState) {
    this.isActive = activeState;
}

RiddleInfo.prototype.getPreviousActiveState = function() {
    return this.wasActive;
}

RiddleInfo.prototype.setPreviousActiveState = function(previousActiveState) {
    this.wasActive = previousActiveState;
}
  
RiddleInfo.prototype.fill = function(newCellProperties) {
    for (var cellProperty in newCellProperties) {
        if (this.hasOwnProperty(cellProperty) && newCellProperties.hasOwnProperty(cellProperty)) {
            if (this[cellProperty] !== 'undefined') {
                this[cellProperty] = newCellProperties[cellProperty];
            }
        }
    }
}; 

module.exports = Cell;