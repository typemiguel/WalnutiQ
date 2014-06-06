class Cell
  protected @isActive
  protected @wasActive

  # self makes it a class Cell method
  def public self.getActiveState?
  	return @isActive
  end

  def public self.setActiveState=(value)
  	@isActive = value
  end

  def public self.getPreviousActiveState?
  	return @wasActive
  end

  def public self.setPreviousActiveState=(value)
  	@wasActive = value
  end
end