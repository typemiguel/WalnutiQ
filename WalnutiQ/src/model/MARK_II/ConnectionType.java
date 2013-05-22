package model.MARK_II;

/**
 * Methods to be implemented that allow regions to connect to each other.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version MARK II | April 4, 2013
 */
public interface ConnectionType {

  public abstract void connect(Region parentRegion, Region childRegion);
}
