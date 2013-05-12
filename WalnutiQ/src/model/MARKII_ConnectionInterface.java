package model;

/**
 * Methods to be implemented that allow regions to connect to each other.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version MARK II | April 4, 2013
 */
public interface MARKII_ConnectionInterface {

  public abstract void connect(MARKII_Region parentRegion, MARKII_Region childRegion);
}
