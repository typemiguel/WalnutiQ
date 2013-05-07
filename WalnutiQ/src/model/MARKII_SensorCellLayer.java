package model;

import java.io.Serializable;

/**
 * A data structure that represents a layer of input bits from an external sensor
 * source.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version MARK II | April 4, 2013
 */
public abstract class MARKII_SensorCellLayer implements Serializable {
    public MARKII_SensorCell[][] sensorCellLayer;

}
